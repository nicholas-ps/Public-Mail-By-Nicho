package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.setting;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface SettingDao {
    @Insert
    void insertSetting(Setting setting);

    @Query("DELETE FROM setting")
    void deleteSetting();

    @Query(
            "SELECT * " +
            "FROM setting " +
            "LIMIT 1"
    )
    LiveData<Setting> getSetting();
}
