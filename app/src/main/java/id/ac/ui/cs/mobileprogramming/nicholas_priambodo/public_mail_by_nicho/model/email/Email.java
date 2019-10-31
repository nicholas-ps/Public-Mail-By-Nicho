package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.email;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Email")
public class Email {
    @PrimaryKey
    public int eid;

    @ColumnInfo(name = "sender_email")
    public String sender_email;

    @ColumnInfo(name = "subject")
    public String subject;

    @ColumnInfo(name = "content")
    public String content;
}
