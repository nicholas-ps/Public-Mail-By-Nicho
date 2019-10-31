package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.email;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EmailDao {
    @Insert
    void insertEmail(Email email);

    @Query(
            "SELECT *" +
            "FROM Email"
    )
    List<Email> loadAllEmail();
}
