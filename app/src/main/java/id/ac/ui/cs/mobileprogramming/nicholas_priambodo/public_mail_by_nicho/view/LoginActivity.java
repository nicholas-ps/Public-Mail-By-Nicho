package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.R;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.viewmodel.LoginActivityViewModel;

public class LoginActivity extends AppCompatActivity {
    private EditText username_edit_text;
    private LoginActivityViewModel loginActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        this.username_edit_text = findViewById(R.id.username_edit_text);
        this.loginActivityViewModel = ViewModelProviders.of(this).get(LoginActivityViewModel.class);
    }

    public void onClickCheckInbox(View view) {
        String text = this.username_edit_text.getText().toString();

        if (text.equals("")) {
            String warning_empty_text = getResources().getString(R.string.warning_empty_text);
            Toast.makeText(this, warning_empty_text, Toast.LENGTH_SHORT).show();
        } else {
            new AsyncTaskLogin().execute(text);
        }
    }

    private class AsyncTaskLogin extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... s) {
            String username = s[0];
            String domain_email = getResources().getString(R.string.domain_email);

            loginActivityViewModel.createUser(username, domain_email);
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            //TODO start new activity: list email
        }
    }
}
