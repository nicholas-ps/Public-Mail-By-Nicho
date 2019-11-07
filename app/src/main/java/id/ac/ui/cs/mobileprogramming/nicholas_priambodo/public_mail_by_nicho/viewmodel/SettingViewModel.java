package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.AppDatabase;

public class SettingViewModel extends AndroidViewModel {
    private AppDatabase app;

    public SettingViewModel(Application application) {
        super(application);
        this.app = AppDatabase.getDatabase(application);
    }

    public int getRefreshTime() {
        return this.app.settingDao().getSetting().refresh_time;
    }

    public void updateRefreshTime(int refresh_time) {
        this.app.settingDao().updateSetting(refresh_time);
    }
}
