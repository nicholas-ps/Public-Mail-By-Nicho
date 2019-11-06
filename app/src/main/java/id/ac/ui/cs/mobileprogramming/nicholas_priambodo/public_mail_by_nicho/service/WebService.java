package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.service;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.Observer;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.broadcastReceiver.NotificationBroadcastReceiver;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.AppDatabase;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.email.Email;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.setting.Setting;

public class WebService extends LifecycleService {
    int refresh_time;
    Intent notification_intent;
    Handler handler;
    Runnable runnable;
    AppDatabase db;
    PublicMailByNichoAPI api;
    LocalBroadcastManager localBroadcastManager;
    NotificationBroadcastReceiver notificationBroadcastReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        super.onStartCommand(intent, flags, startId);

        this.api = new PublicMailByNichoAPI(getApplicationContext());
        this.db = AppDatabase.getDatabase(getApplication());
        this.notificationBroadcastReceiver = new NotificationBroadcastReceiver(getApplicationContext());
        this.localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());

        this.localBroadcastManager.registerReceiver(
                this.notificationBroadcastReceiver,
                new IntentFilter("NOTIFICATION_INTENT")
        );
        this.notification_intent = new Intent();
        this.notification_intent.setAction("NOTIFICATION_INTENT");

        this.db.settingDao().getSetting().observe(
                this,
                new Observer<Setting>() {
                    @Override
                    public void onChanged(Setting setting) {
                        refresh_time = setting.refresh_time;
                    }
                }
        );

        this.handler = new Handler();

        this.runnable = new Runnable() {
            @Override
            public void run() {
                new AsyncTaskCallApiInbox().execute();

                handler.postDelayed(
                        this,
                        refresh_time
                );
            }
        };

        this.handler.post(this.runnable);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.handler.removeCallbacks(this.runnable);
        this.localBroadcastManager.unregisterReceiver(this.notificationBroadcastReceiver);
    }

    private class AsyncTaskCallApiInbox extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... v) {
            api.getInbox(
                    db.userDao().getUser().username,
                    new CallBackResponse() {
                        @Override
                        public void execute(List<Email> list_email) {
                            new AsyncTaskSaveEmailToDatabase().execute(list_email);
                        }
                    }
            );

            return null;
        }
    }

    private class AsyncTaskSaveEmailToDatabase extends AsyncTask<List<Email>, Void, Void> {
        @Override
        protected Void doInBackground(List<Email>... v) {
            List<Email> list_email = v[0];

            if (list_email.size() > db.emailDao().countAll()) {
                db.emailDao().deleteAllEmail();

                for (Email email : list_email) {
                    db.emailDao().insertEmail(email);
                }

                localBroadcastManager.sendBroadcast(notification_intent);
            }

            return null;
        }
    }
}
