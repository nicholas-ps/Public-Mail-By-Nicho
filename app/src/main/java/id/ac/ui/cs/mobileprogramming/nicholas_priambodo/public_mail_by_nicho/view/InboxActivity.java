package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.R;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.email.Email;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.service.WebService;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.viewmodel.InboxViewModel;

public class InboxActivity extends AppCompatActivity {
    private InboxViewModel inboxViewModel;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbox_activity);

        this.listView = findViewById(R.id.inbox);
        this.inboxViewModel = ViewModelProviders.of(this).get(InboxViewModel.class);

        this.inboxViewModel.getEmailLiveData().observe(
                this,
                new Observer<List<Email>>() {
                    @Override
                    public void onChanged(List<Email> list_email) {
                        updateListViewInbox(list_email);
                    }
                }
        );

        stopService(new Intent(this, WebService.class));
        startService(new Intent(this, WebService.class));
    }


    private void updateListViewInbox(List<Email> list_email) {
        String[] from = {
                "sender",
                "subject",
                "content_preview"
        };

        int[] to = {
                R.id.sender,
                R.id.subject_email,
                R.id.content_preview
        };

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                this,
                this.inboxViewModel.convertToListHash(list_email),
                R.layout.list_email,
                from,
                to
        );

        this.listView.setAdapter(simpleAdapter);
    }

    public void onClickExitIcon(View view) {
        stopService(new Intent(this, WebService.class));
        ((NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
        new AsyncTaskDeleteAllData().execute();
    }

    private class AsyncTaskDeleteAllData extends AsyncTask<Void, Void, Void> {
        @Override
        public Void doInBackground(Void... v) {
            inboxViewModel.deleteAllData();
            return null;
        }

        @Override
        public void onPostExecute(Void v) {
            startActivity(
                    new Intent(InboxActivity.this, LoginActivity.class)
            );
            finish();
        }
    }
}
