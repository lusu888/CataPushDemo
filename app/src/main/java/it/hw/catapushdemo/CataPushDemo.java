package it.hw.catapushdemo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.multidex.MultiDexApplication;

import com.catapush.library.Catapush;

import com.catapush.library.hms.CatapushHms;
import com.catapush.library.interfaces.Callback;
import com.catapush.library.notifications.NotificationTemplate;

import java.io.IOException;
import java.util.Collections;

import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.plugins.RxJavaPlugins;

public class CataPushDemo extends MultiDexApplication {
    private void setupRxErrorHandler() {
        RxJavaPlugins.setErrorHandler(e -> {
            if (e instanceof UndeliverableException) {
                // The exception was wrapped
                e = e.getCause();
            }
            if (e instanceof IOException) {
                // Fine, irrelevant network problem or API that throws on cancellation
                return;
            }
            if (e instanceof InterruptedException) {
                // Fine, some blocking code was interrupted by a dispose call
                return;
            }
            if ((e instanceof NullPointerException) || (e instanceof IllegalArgumentException)) {
                // That's likely a bug in the application
                Thread.currentThread().getUncaughtExceptionHandler()
                        .uncaughtException(Thread.currentThread(), e);
                return;
            }
            if (e instanceof IllegalStateException) {
                // That's a bug in RxJava or in a custom operator
                Thread.currentThread().getUncaughtExceptionHandler()
                        .uncaughtException(Thread.currentThread(), e);
                return;
            }
            // Log this to your analytics platform
            Log.e(Catapush.class.getCanonicalName(), e.getMessage());
        });
    }

    private static final String NOTIFICATION_CHANNEL_ID = "your.app.package.CHANNEL_ID";

    @Override
    public void onCreate() {
        super.onCreate();

        setupRxErrorHandler(); // Required: see section 'RxJava catch-all error handler' for the implementation

        // This is the Android system notification channel that will be used by the Catapush SDK
        // to notify the incoming messages since Android 8.0. It is important that the channel
        // is created before starting Catapush.
        // See https://developer.android.com/training/notify-user/channels
        NotificationManager nm = ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
        if (nm != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = getString(R.string.catapush_notification_channel_name);
            NotificationChannel channel = nm.getNotificationChannel(NOTIFICATION_CHANNEL_ID);
            if (channel == null) {
                channel = new NotificationChannel(
                        NOTIFICATION_CHANNEL_ID,
                        channelName,
                        NotificationManager.IMPORTANCE_HIGH);
                // Customize your notification appearance here (Android >= 8.0)
                // it's possible to customize a channel only on creation
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{100, 200, 100, 300});
                channel.enableLights(true);
                channel.setLightColor(ContextCompat.getColor(this, R.color.primary));
            } else if (!channelName.contentEquals(channel.getName())) {
                // Update channel name, useful when the user changes the system language
                channel.setName(channelName);
            }
            nm.createNotificationChannel(channel);
        }


        Catapush.getInstance().init(
                this,
                NOTIFICATION_CHANNEL_ID,
                Collections.singletonList(CatapushHms.INSTANCE),//修改處
                new Callback() {


                    @Override
                    public void failure(@NonNull Throwable t) {
                        Log.d("MyApp", "Catapush initialization error: " + t.getMessage());
                    }

                    @Override
                    public void success(Object response) {
                        Log.d("MyApp", "Catapush has been successfully initialized");

                        // This is the notification template that the Catapush SDK uses to build
                        // the status bar notification shown to the user.
                        // Some settings like vibration, lights, etc. are duplicated here because
                        // before Android introduced notification channels (Android < 8.0) the
                        // styling was made on a per-notification basis.
                        final NotificationTemplate template = NotificationTemplate.builder()
                                .swipeToDismissEnabled(false)
                                .title("Your notification title!")
                                .iconId(R.drawable.ic_stat_notify_default)
                                .vibrationEnabled(true)
                                .vibrationPattern(new long[]{100, 200, 100, 300})
                                .soundEnabled(true)
                                .soundResourceUri(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                                .circleColor(ContextCompat.getColor(CataPushDemo.this, R.color.primary))
                                .ledEnabled(true)
                                .ledColor(Color.BLUE)
                                .ledOnMS(2000)
                                .ledOffMS(1000)
                                .build();

                        Catapush.getInstance().setNotificationTemplate(template);
                    }
                }
        );





    }

}
