package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model;

import androidx.room.Entity;

@Entity(
        tableName = "User",
        primaryKeys = {"username", "email"}
)
public class User {
    public String username;
    public String email;
}
