package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.R;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.service.PublicMailByNichoAPI;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.viewmodel.SendEmailViewModel;

public class SendEmailActivity extends AppCompatActivity {
    PublicMailByNichoAPI api;
    EditText receiver_edit_text;
    EditText subject_edit_text;
    EditText content_edit_text;
    SendEmailViewModel sendEmailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_email_activity);
        this.receiver_edit_text = findViewById(R.id.receiver_edit_text);
        this.subject_edit_text = findViewById(R.id.subject_edit_text);
        this.content_edit_text = findViewById(R.id.content_edit_text);
        this.api = new PublicMailByNichoAPI(this);
        this.sendEmailViewModel = ViewModelProviders.of(this).get(SendEmailViewModel.class);
    }

    public void onClickSend(View view) {
        boolean isEmpty = false;

        if (this.receiver_edit_text.getText().toString().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.receiver_empty_warning), Toast.LENGTH_SHORT).show();
            isEmpty = true;
        }

        if (this.subject_edit_text.getText().toString().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.subject_empty_warning), Toast.LENGTH_SHORT).show();
            isEmpty = true;
        }

        if (this.content_edit_text.getText().toString().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.content_empty_warning), Toast.LENGTH_SHORT).show();
            isEmpty = true;
        }

        if (!isEmpty) {
            new AsyncTaskSendEmail().execute();
        }
    }

    private class AsyncTaskSendEmail extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground (Void... v) {
            return sendEmailViewModel.getUsername();
        }

        @Override
        protected void onPostExecute(String username) {
            api.sendEmail(
                    username,
                    receiver_edit_text.getText().toString(),
                    subject_edit_text.getText().toString(),
                    content_edit_text.getText().toString()
            );

            SendEmailActivity.this.finish();
        }
    }
}
