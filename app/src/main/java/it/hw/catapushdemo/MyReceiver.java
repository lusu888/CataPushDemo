package it.hw.catapushdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

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

        Intent buttonIntent = new Intent(msg.data().get("buttonAction"));
        PendingIntent buttonPendingIntent = PendingIntent
                .getActivity(context, 0, buttonIntent, PendingIntent.FLAG_ONE_SHOT);

        Notification notification = new NotificationCompat.Builder(context, context.getResources().getString(R.string.catapush_notification_channel_id))
                .setSmallIcon(R.drawable.ic_stat_notify_default)
                .setContentTitle("Notification button example")
                .setContentText(msg.previewText())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg.body()))
                .addAction(new NotificationCompat.Action(
                        R.drawable.upsdk_third_download_bg,
                        msg.data().get("buttonLabel"),
                        buttonPendingIntent
                ))
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(msg.id().hashCode(), notification);

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