package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.AppDatabase;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.User;

public class LoginViewModel extends AndroidViewModel {
    private AppDatabase db;

    public LoginViewModel(Application application) {
        super(application);
        this.db = AppDatabase.getDatabase(application);
    }

    public void resetDatabase() {
        this.db.clearAllTables();
    }

    public void createUser(String username) {
        resetDatabase();

        User user = new User();
        user.username = username;

        this.db.userDao().insertUser(user);
    }
}
