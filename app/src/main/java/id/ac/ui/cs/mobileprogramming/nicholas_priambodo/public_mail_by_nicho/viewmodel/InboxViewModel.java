package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;

import org.json.JSONArray;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.AppDatabase;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.WebServicePublicMailByNicho;

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
                    public void callBack(JSONArray response) {
                        Toast.makeText(getApplication(), response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            );
    }
}
