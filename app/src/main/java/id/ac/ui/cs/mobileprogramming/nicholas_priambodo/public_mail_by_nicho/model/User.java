package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(
        tableName = "User",
        primaryKeys = {"username", "email"}
)
public class User {
    @NonNull
    public String username;

    @NonNull
    public String email;
}
