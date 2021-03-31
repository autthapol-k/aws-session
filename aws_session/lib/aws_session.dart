import 'dart:async';

import 'package:aws_session_platform_interface/aws_session_platform_interface.dart';

class AwsSession {
  static Future<bool> get clearSession async =>
      await AwsSessionPlatform.instance.clearSession();
}
