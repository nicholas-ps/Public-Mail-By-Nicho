package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.AppDatabase;

public class SendEmailViewModel extends AndroidViewModel {
    private AppDatabase app;

    public SendEmailViewModel(Application application) {
        super(application);
        this.app = AppDatabase.getDatabase(application);
    }

    public String getUsername() {
        return this.app.userDao().getUser().username;
    }
}
