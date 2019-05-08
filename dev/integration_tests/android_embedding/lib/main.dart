import 'package:android_embedding/default_flutter_app.dart';
import 'package:android_embedding/fragment_flutter_app.dart';
import 'package:android_embedding/fullscreen_flutter_app.dart';
import 'package:android_embedding/login_screen_app.dart';
import 'package:android_embedding/profile_screen_app.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import 'android_alarm_manager.dart';

void main() => runApp(MyApp());

@pragma('vm:entry-point')
void fullscreenFlutter() => runApp(FullscreenFlutterApp());

@pragma('vm:entry-point')
void fragmentFlutter() => runApp(FragmentFlutterApp());

@pragma('vm:entry-point')
void loginScreen() => runApp(LoginScreenApp());

@pragma('vm:entry-point')
void profileScreen() => runApp(ProfileScreenApp());

@pragma('vm:entry-point')
void basicPluginScreen() {
  print('Hello?');
  final MethodChannel _channel = MethodChannel('basic_plugin')
    ..setMethodCallHandler((MethodCall call) {
      print('Received message: ${call.method}');
    });

  runApp(MyApp());
}

@pragma('vm:entry-point')
void alarmManagerForegroundMain() {
  AndroidAlarmManager.initialize();
}

@pragma('vm:entry-point')
void alarmManagerBackgroundMain() {
  print('Running alarmManagerBackgroundMain()');
}
