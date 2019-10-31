package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.email.Email;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.email.EmailDao;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.user.User;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.user.UserDao;

@Database(entities = {User.class, Email.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase db;
    public abstract UserDao userDao();
    public abstract EmailDao emailDao();

    public static AppDatabase getDatabase(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "database"
            ).build();
        }
        return db;
    }
}
