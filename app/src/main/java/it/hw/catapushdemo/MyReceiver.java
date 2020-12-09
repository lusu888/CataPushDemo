package it.hw.catapushdemo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.catapush.library.CatapushTwoWayReceiver;
import com.catapush.library.exceptions.CatapushAuthenticationError;
import com.catapush.library.exceptions.PushServicesException;
import com.catapush.library.messages.CatapushMessage;

public class MyReceiver extends CatapushTwoWayReceiver {

    @Override
    public void onConnected(@NonNull Context context) {
        Log.d("MyApp", "Connected");
    }

    @Override
    public void onMessageReceived(@NonNull CatapushMessage msg, @NonNull Context context) {
        Log.d("MyApp", "Received Message: " + msg.toString());
    }

    @Override
    public void onMessageOpened(@NonNull CatapushMessage msg, @NonNull Context context) {
        Log.d("MyApp", "Opened Message: " + msg.toString());
    }

    @Override
    public void onNotificationClicked(@NonNull CatapushMessage message, @NonNull Context context) {
        Log.d("MyApp", "Notification clicked: " + message.toString());
    }

    @Override
    public void onRegistrationFailed(@NonNull CatapushAuthenticationError error, @NonNull Context context) {
        Log.e("MyApp", "Error Message: " + error.getMessage());
    }

    @Override
    public void onDisconnected(int errorCode, @NonNull Context context) {
        Log.d("MyApp", "Disconnected: " + errorCode);
    }

    @Override
    public void onConnecting(@NonNull Context context) {
        Log.d("MyApp", "Connecting...");
    }

    @Override
    public void onPushServicesError(@NonNull PushServicesException error, @NonNull Context context) {
    }

    @Override
    public void onMessageSent(@NonNull CatapushMessage message, @NonNull Context context) {
        Log.d("MyApp", "Message marked as sent: " + message.toString());
    }

    @Override
    public void onMessageSentConfirmed(@NonNull CatapushMessage message, @NonNull Context context) {
        Log.d("MyApp", "Message sent and delivered: " + message.toString());
    }

}