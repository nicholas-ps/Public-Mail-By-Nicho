package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Query(
            "SELECT * " +
            "FROM User " +
            "WHERE username = :username " +
            "LIMIT 1"
    )
    User getUserByUsername(String username);
}
