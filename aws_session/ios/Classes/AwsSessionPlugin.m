#import "AwsSessionPlugin.h"
#if __has_include(<aws_session/aws_session-Swift.h>)
#import <aws_session/aws_session-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "aws_session-Swift.h"
#endif

@implementation AwsSessionPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftAwsSessionPlugin registerWithRegistrar:registrar];
}
@end
