package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.broadcastReceiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.R;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.view.InboxActivity;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    Notification notification;
    NotificationManager notificationManager;

    public NotificationBroadcastReceiver(Context context) {
        super();

        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    "0",
                    "default",
                    NotificationManager.IMPORTANCE_HIGH
            );

            this.notificationManager.createNotificationChannel(notificationChannel);
        }

        PendingIntent pendingIntent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(InboxActivity.class);
            stackBuilder.addNextIntent(new Intent(context, InboxActivity.class));
            pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        this.notification = new NotificationCompat.Builder(context, "0")
                .setSmallIcon(R.drawable.public_mail_by_nicho_icon)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(context.getResources().getString(R.string.notification_message))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.notificationManager.notify(0, this.notification);
    }
}
