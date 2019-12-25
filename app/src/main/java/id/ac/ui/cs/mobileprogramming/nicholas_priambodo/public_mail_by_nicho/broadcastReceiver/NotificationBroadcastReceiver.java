package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.notification.NotificationInbox;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    private NotificationInbox notificationInbox;

    public NotificationBroadcastReceiver(Context context) {
        super();

        this.notificationInbox = new NotificationInbox(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.notificationInbox.displayNotification();
    }
}
