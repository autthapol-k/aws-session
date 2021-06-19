import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'method_channel_aws_session_platform.dart';

abstract class AwsSessionPlatform extends PlatformInterface {
  AwsSessionPlatform() : super(token: _token);

  static final Object _token = Object();

  static AwsSessionPlatform _instance = MethodChannelAwsSession();

  static AwsSessionPlatform get instance => _instance;

  static set instance(AwsSessionPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<bool> clearSession() {
    throw UnimplementedError('clearSession() has not been implemented.');
  }
}
