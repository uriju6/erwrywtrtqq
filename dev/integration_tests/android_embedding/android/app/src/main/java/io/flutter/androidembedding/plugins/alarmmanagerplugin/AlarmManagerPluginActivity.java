package io.flutter.androidembedding.plugins.alarmmanagerplugin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.flutter.androidembedding.R;
import io.flutter.embedding.android.FlutterView;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.view.FlutterMain;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public class AlarmManagerPluginActivity extends AppCompatActivity {
  private static final String TAG = "AlarmManagerPluginActivity";

  private FlutterEngine flutterEngine;
  private boolean isPluginConnected = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_alarm_manager);

    FlutterMain.ensureInitializationComplete(getApplicationContext(), new String[]{});

    flutterEngine = new FlutterEngine(this);
    flutterEngine.getPlugins().add(new AndroidAlarmManagerPlugin.NewPlugin());
    flutterEngine.getDartExecutor().executeDartEntrypoint(
        new DartExecutor.DartEntrypoint(
            getAssets(),
            FlutterMain.findAppBundlePath(getApplicationContext()),
            "alarmManagerForegroundMain"
        )
    );

    FlutterView flutterView = new FlutterView(this);
    flutterView.attachToFlutterEngine(flutterEngine);
    FrameLayout frameLayout = findViewById(R.id.framelayout);
    frameLayout.addView(flutterView);

    findViewById(R.id.button_connect_plugin).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!isPluginConnected) {
          Log.d(TAG, "Connecting plugin.");
          isPluginConnected = true;

          flutterEngine.getPlugins().add(new AndroidAlarmManagerPlugin.NewPlugin());
        }
      }
    });

    findViewById(R.id.button_disconnect_plugin).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (isPluginConnected) {
          Log.d(TAG, "Disconnecting plugin.");
          flutterEngine.getPlugins().remove(AndroidAlarmManagerPlugin.NewPlugin.class);

          isPluginConnected = false;
        }
      }
    });
  }
}
