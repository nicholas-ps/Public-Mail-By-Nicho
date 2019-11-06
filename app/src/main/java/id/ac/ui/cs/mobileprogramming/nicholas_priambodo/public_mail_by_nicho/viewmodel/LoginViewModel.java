package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.AppDatabase;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.setting.Setting;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.user.User;

public class LoginViewModel extends AndroidViewModel {
    private AppDatabase db;

    public LoginViewModel(Application application) {
        super(application);
        this.db = AppDatabase.getDatabase(application);
    }

    public void createUser(String username) {
        User user = new User();
        user.username = username;

        this.db.userDao().insertUser(user);

        Setting setting = new Setting();
        setting.refresh_time = 10000; //default 10 seconds

        this.db.settingDao().insertSetting(setting);
    }

    public boolean isUserExists() {
        return this.db.userDao().getUser() != null;
    }
}
