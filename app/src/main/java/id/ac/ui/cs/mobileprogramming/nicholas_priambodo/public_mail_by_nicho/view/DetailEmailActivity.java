package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.R;

public class DetailEmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_email_activity);

        Bundle bundle = getIntent().getExtras();

        ((TextView) findViewById(R.id.subject_email)).setText(bundle.getString("subject"));
        ((TextView) findViewById(R.id.sender)).setText(bundle.getString("sender_email"));
        ((TextView) findViewById(R.id.content_email)).setText(bundle.getString("content"));
        ((TextView) findViewById(R.id.content_email)).setMovementMethod(new ScrollingMovementMethod());
    }
}
