package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.AsyncTask;
import android.os.Bundle;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.R;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.viewmodel.InboxViewModel;

public class InboxActivity extends AppCompatActivity {
    private InboxViewModel inboxViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbox_activity);
        this.inboxViewModel = ViewModelProviders.of(this).get(InboxViewModel.class);
        new AsyncTaskInbox().execute();
    }

    private class AsyncTaskInbox extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... v) {
            inboxViewModel.getInboxFromWebService();
            return null;
        }
    }
}
