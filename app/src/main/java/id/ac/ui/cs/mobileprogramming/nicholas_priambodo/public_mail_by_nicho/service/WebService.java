package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.AppDatabase;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.email.Email;

public class WebService extends Service {
    Handler handler;
    Runnable runnable;
    AppDatabase db;
    PublicMailByNichoAPI api;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        this.api = new PublicMailByNichoAPI(getApplicationContext());
        this.db = AppDatabase.getDatabase(getApplication());
        this.handler = new Handler();

        this.runnable = new Runnable() {
            @Override
            public void run() {
                new AsyncTaskCallApiInbox().execute();

                handler.postDelayed(
                        this,
                        10000
                );
            }
        };

        this.handler.post(this.runnable);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        this.handler.removeCallbacks(this.runnable);
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
            }

            return null;
        }
    }
}
