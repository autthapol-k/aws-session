package com.autthapol.aws_session

import android.content.Context
import androidx.annotation.NonNull
import com.amazonaws.internal.keyvaluestore.AWSKeyValueStore
import com.amazonaws.mobile.client.AWSMobileClient
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
            result.success(clearSession())
        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }


    private fun clearSession(): Boolean {
        val client = AWSMobileClient.getInstance()
        val awsKeyValueStore = AWSKeyValueStore(context, "CognitoIdentityProviderCache", true)
        val clientId = client.configuration.optJsonObject("CognitoUserPool").getString("AppClientId")
        val userId = client.username

        val csiIdTokenKey = String.format("CognitoIdentityProvider.%s.%s.idToken", clientId, userId)
        val csiAccessTokenKey = String.format("CognitoIdentityProvider.%s.%s.accessToken", clientId, userId)
        awsKeyValueStore.put(csiIdTokenKey, "")
        awsKeyValueStore.put(csiAccessTokenKey, "")

        return true
    }
}