package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.AppDatabase;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.WebServicePublicMailByNicho;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.email.Email;

public class InboxViewModel extends AndroidViewModel {
    private AppDatabase db;
    private WebServicePublicMailByNicho webService;
    private MutableLiveData<List<Email>> live_list_email;

    public MutableLiveData<List<Email>> getLiveListEmail() {
        if (this.live_list_email == null) {
            this.live_list_email = new MutableLiveData<>();
        }

        return this.live_list_email;
    }

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

    private class AsyncTaskSaveEmail extends AsyncTask<List<Email>, Void, List<Email>> {
        @Override
        protected List<Email> doInBackground(List<Email>... l) {
            List<Email> list_email = l[0];

            for (Email email : list_email) {
                db.emailDao().insertEmail(email);
            }

            return list_email;
        }

        @Override
        protected void onPostExecute(List<Email> list_email) {
            live_list_email.setValue(list_email);
        }
    }
}
