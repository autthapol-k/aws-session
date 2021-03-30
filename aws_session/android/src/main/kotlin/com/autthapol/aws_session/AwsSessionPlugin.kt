package com.autthapol.aws_session

import android.content.Context
import androidx.annotation.NonNull
import com.amazonaws.internal.keyvaluestore.AWSKeyValueStore
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** AwsSessionPlugin */
class AwsSessionPlugin : FlutterPlugin, MethodCallHandler {
    private val CHANNEL_NAME = "aws_session"

    private lateinit var context: Context
    private lateinit var channel: MethodChannel

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        context = flutterPluginBinding.applicationContext
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, CHANNEL_NAME)
        channel.setMethodCallHandler(this)
    }


    @Suppress("UNCHECKED_CAST")
    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        if (call.method == "clearSession") {
            val arguments = call.arguments as? Map<String, String>
            val clientId = arguments?.get("clientId")
            val userId = arguments?.get("userId")

            if (clientId != null && userId != null) {
                result.success(clearSession(clientId, userId))
            } else {
                result.error("Invalid Parameter", "clientId and userId cannot be null.", null)
            }

        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }


    private fun clearSession(clientId: String, userId: String): Boolean {
        val awsKeyValueStore = AWSKeyValueStore(context, "CognitoIdentityProviderCache", true)
        val csiIdTokenKey = String.format("CognitoIdentityProvider.%s.%s.idToken", clientId, userId)
        val csiAccessTokenKey = String.format("CognitoIdentityProvider.%s.%s.accessToken", clientId, userId)

        awsKeyValueStore.put(csiIdTokenKey, "")
        awsKeyValueStore.put(csiAccessTokenKey, "")

        return true
    }
}