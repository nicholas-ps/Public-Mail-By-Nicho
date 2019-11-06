package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.setting;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "setting")
public class Setting {
    @PrimaryKey
    public int refresh_time;
}
