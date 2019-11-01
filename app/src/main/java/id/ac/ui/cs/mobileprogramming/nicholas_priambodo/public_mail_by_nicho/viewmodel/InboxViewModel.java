package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.AppDatabase;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.WebServicePublicMailByNicho;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.email.Email;

public class InboxViewModel extends AndroidViewModel {
    private AppDatabase db;
    private WebServicePublicMailByNicho webService;
    private MutableLiveData<List<Email>> live_list_email;

    public InboxViewModel(Application application) {
        super(application);
        this.db = AppDatabase.getDatabase(application);
        this.webService = new WebServicePublicMailByNicho(getApplication());
    }

    public MutableLiveData<List<Email>> getLiveListEmail() {
        if (this.live_list_email == null) {
            this.live_list_email = new MutableLiveData<>();
        }

        return this.live_list_email;
    }

    public void deleteAllData() {
        this.db.clearAllTables();
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

    public List<HashMap<String, String>> getListEmailInListHash() {
        List<HashMap<String, String>> list = new ArrayList<>();

        for (Email email : this.live_list_email.getValue()) {
            HashMap<String, String> hash = new HashMap<>();

            hash.put("sender", email.sender_email);
            hash.put("subject", email.subject);
            hash.put("content_preview", email.content);

            list.add(hash);
        }

        return list;
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
