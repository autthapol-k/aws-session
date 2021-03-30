import UIKit
import Flutter
import AWSCognitoIdentityProvider

@UIApplicationMain
@objc class AppDelegate: FlutterAppDelegate {
    override func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
    ) -> Bool {
        
        let controller: FlutterViewController = window?.rootViewController as! FlutterViewController
        let channel = FlutterMethodChannel(name: "aws_session", binaryMessenger: controller.binaryMessenger)
        
        channel.setMethodCallHandler { (call: FlutterMethodCall, result: @escaping FlutterResult) in
            guard call.method == "clearSession" else {
                result(FlutterMethodNotImplemented)
                return
            }
            
            self.clearSession(result: result)
        }
        
        GeneratedPluginRegistrant.register(with: self)
        return super.application(application, didFinishLaunchingWithOptions: launchOptions)
    }
    
    private func clearSession(result: FlutterResult) {
        AWSCognitoIdentityUserPool.default().currentUser()?.clearSession()
        result(Bool(true))
    }
}
