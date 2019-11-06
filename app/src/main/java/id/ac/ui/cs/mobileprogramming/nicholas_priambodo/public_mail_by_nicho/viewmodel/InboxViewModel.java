package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.AppDatabase;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.email.Email;

public class InboxViewModel extends AndroidViewModel {
    private AppDatabase db;
    private MutableLiveData<Email> email_live_data;

    public InboxViewModel(Application application) {
        super(application);
        this.db = AppDatabase.getDatabase(application);
    }

    public MutableLiveData<Email> getEmailLiveData() {
        if (this.email_live_data == null) {
            this.email_live_data = new MutableLiveData<>();
        }

        return this.email_live_data;
    }

    public List<HashMap<String, String>> convertToListHash(List<Email> list_email) {
        List<HashMap<String, String>> list = new ArrayList<>();

        for (Email email : list_email) {
            HashMap<String, String> hashMap = new HashMap<>();

            hashMap.put("sender", email.sender_email);
            hashMap.put("subject", email.subject);

            String content_preview = email.content.split("\n")[0];

            if (content_preview.length() > 100) {
                hashMap.put("content_preview", content_preview.substring(0, 100) + "...");
            }
            else {
                hashMap.put("content_preview", content_preview);

            }

            list.add(hashMap);
        }

        return list;
    }

    public void deleteAllData() {
        this.db.clearAllTables();
    }

    public LiveData<List<Email>> getListEmailLiveData() {
        return this.db.emailDao().loadAllEmail();
    }
}
