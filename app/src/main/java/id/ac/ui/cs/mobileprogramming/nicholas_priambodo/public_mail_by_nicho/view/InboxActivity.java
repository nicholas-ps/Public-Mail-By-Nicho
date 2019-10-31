package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.AsyncTask;
import android.os.Bundle;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.R;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.email.Email;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.viewmodel.InboxViewModel;

public class InboxActivity extends AppCompatActivity {
    private InboxViewModel inboxViewModel;
    private MutableLiveData<List<Email>> live_list_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbox_activity);

        this.inboxViewModel = ViewModelProviders.of(this).get(InboxViewModel.class);
        this.live_list_email = this.inboxViewModel.getLiveListEmail();

        this.live_list_email.observe(
                this,
                new Observer<List<Email>>() {
                    @Override
                    public void onChanged(List<Email> list_email) {
                        updateInbox();
                    }
                }
        );

        new AsyncTaskInbox().execute();
    }

    private void updateInbox() {
        //TODO implement here
    }

    private class AsyncTaskInbox extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... v) {
            inboxViewModel.getInboxFromWebService();
            return null;
        }
    }
}
