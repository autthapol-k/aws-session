import Flutter
import UIKit
import AWSCognitoIdentityProvider

public class SwiftAwsSessionPlugin: NSObject, FlutterPlugin {
    public static func register(with registrar: FlutterPluginRegistrar) {
        let channel = FlutterMethodChannel(name: "aws_session", binaryMessenger: registrar.messenger())
        let instance = SwiftAwsSessionPlugin()
        registrar.addMethodCallDelegate(instance, channel: channel)
    }
    
    public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
        guard call.method == "clearSession" else {
            result(FlutterMethodNotImplemented)
            return
        }
        
        self.clearSession(result: result)
    }
    
    private func clearSession(result: FlutterResult) {
        AWSCognitoIdentityUserPool.default().currentUser()?.clearSession()
        
        result(true)
    }
}
