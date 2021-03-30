import 'package:flutter/services.dart';

import '../aws_session_platform_interface.dart';

class MethodChannelAwsSession extends AwsSessionPlatform {
  final MethodChannel channel = MethodChannel('aws_session');

  @override
  Future<bool> clearSession() async {
    return (await channel.invokeMethod<bool>('clearSession'))!;
  }
}
