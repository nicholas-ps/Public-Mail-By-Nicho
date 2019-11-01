package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.R;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private EditText username_edit_text;
    private LoginViewModel loginActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.loginActivityViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        new AsyncTaskCheckUser().execute();
    }

    private void startInboxActivity() {
        Intent intent = new Intent(LoginActivity.this, InboxActivity.class);
        startActivity(intent);
        finish();
    }

    private class AsyncTaskCheckUser extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... v) {
            return loginActivityViewModel.isUserExists();
        }

        @Override
        protected void onPostExecute(Boolean exists) {
            if (!exists) {
                setContentView(R.layout.login_activity);
                username_edit_text = findViewById(R.id.username_edit_text);
            }
            else {
                startInboxActivity();
            }
        }
    }

    public void onClickCheckInbox(View view) {
        String text = this.username_edit_text.getText().toString();

        if (text.equals("")) {
            String warning_empty_text = getResources().getString(R.string.warning_empty_text);
            Toast.makeText(this, warning_empty_text, Toast.LENGTH_SHORT).show();
        }
        else {
            new AsyncTaskLogin().execute(text);
        }
    }

    private class AsyncTaskLogin extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... s) {
            loginActivityViewModel.createUser(s[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            startInboxActivity();
        }
    }
}
