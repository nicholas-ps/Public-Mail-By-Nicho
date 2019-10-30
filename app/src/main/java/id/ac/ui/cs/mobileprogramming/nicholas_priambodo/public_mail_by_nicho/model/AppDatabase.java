package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase db;
    public abstract UserDao userDao();

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
