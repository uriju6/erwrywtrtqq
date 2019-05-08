package io.flutter.androidembedding.plugins;

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

public class BasicPluginActivity extends AppCompatActivity {
  private static final String TAG = "BasicPluginActivity";

  private FlutterEngine flutterEngine;
  private boolean isPluginConnected = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_basic_plugin);

    FlutterMain.ensureInitializationComplete(getApplicationContext(), new String[]{});

    flutterEngine = new FlutterEngine(this);
    flutterEngine.getDartExecutor().executeDartEntrypoint(
        new DartExecutor.DartEntrypoint(
            getAssets(),
            FlutterMain.findAppBundlePath(getApplicationContext()),
            "basicPluginScreen"
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

          flutterEngine.getPlugins().add(new BasicPlugin());
        }
      }
    });

    findViewById(R.id.button_disconnect_plugin).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (isPluginConnected) {
          Log.d(TAG, "Disconnecting plugin.");
          flutterEngine.getPlugins().remove(BasicPlugin.class);

          isPluginConnected = false;
        }
      }
    });
  }

  private static class BasicPlugin implements FlutterPlugin {
    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
      Log.d(TAG, "onAttachedToEngine() - Binding: " + binding);
      Log.d(TAG, " - Context: " + binding.getApplicationContext());
      Log.d(TAG, " - FlutterEngine: " + binding.getFlutterEngine());
      Log.d(TAG, " - Lifecycle: " + binding.getLifecycle());

      MethodChannel channel = new MethodChannel(
          binding.getFlutterEngine().getDartExecutor(),
          "basic_plugin"
      );
      channel.invokeMethod("this is a message", null);
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
      Log.d(TAG, "onDetachedFromEngine() - Binding: " + binding);
      Log.d(TAG, " - Context: " + binding.getApplicationContext());
      Log.d(TAG, " - FlutterEngine: " + binding.getFlutterEngine());
      Log.d(TAG, " - Lifecycle: " + binding.getLifecycle());
    }
  }
}
