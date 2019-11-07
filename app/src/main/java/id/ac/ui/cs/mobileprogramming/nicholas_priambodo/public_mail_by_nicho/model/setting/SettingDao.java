package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.setting;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface SettingDao {
    @Insert
    void insertSetting(Setting setting);

    @Query(
            "UPDATE setting " +
            "SET refresh_time = :refresh_time"
    )
    void updateSetting(int refresh_time);

    @Query(
            "SELECT * " +
                    "FROM setting " +
                    "LIMIT 1"
    )
    Setting getSetting();

    @Query(
            "SELECT * " +
            "FROM setting " +
            "LIMIT 1"
    )
    LiveData<Setting> getLiveDataSetting();
}
