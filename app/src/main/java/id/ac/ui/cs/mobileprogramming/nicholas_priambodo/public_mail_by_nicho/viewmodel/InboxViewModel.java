package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.AppDatabase;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.WebServicePublicMailByNicho;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.email.Email;

public class InboxViewModel extends AndroidViewModel {
    private AppDatabase db;
    private WebServicePublicMailByNicho webService;

    public InboxViewModel(Application application) {
        super(application);
        this.db = AppDatabase.getDatabase(application);
        this.webService = new WebServicePublicMailByNicho(getApplication());
    }

    public void getInboxFromWebService() {
        String username = this.db.userDao().getUser().username;
        this.webService.getInbox(
                username,
                new CallBackResponse() {
                    @Override
                    public void execute(List<Email> list_email) {
                        new AsyncTaskSaveEmail().execute(list_email);
                    }
                }
            );
    }

    private class AsyncTaskSaveEmail extends AsyncTask<List<Email>, Void, Void> {
        @Override
        protected Void doInBackground(List<Email>... l) {
            List<Email> list_email = l[0];

            for (Email email : list_email) {
                db.emailDao().insertEmail(email);
            }

            return null;
        }
    }
}
