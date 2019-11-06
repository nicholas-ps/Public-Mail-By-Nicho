package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.R;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.email.Email;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.service.WebService;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.view.fragment.DetailEmailFragment;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.view.fragment.InboxFragment;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.viewmodel.InboxViewModel;

public class InboxActivity extends AppCompatActivity {
    private InboxViewModel inboxViewModel;
    private ListView listView;
    private List<Email> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbox_activity);

        this.inboxViewModel = ViewModelProviders.of(this).get(InboxViewModel.class);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            this.listView = findViewById(R.id.inbox);
            this.listView.setOnItemClickListener(new OnItemClickListener());

            this.inboxViewModel.getListEmailLiveData().observe(
                    this,
                    new Observer<List<Email>>() {
                        @Override
                        public void onChanged(List<Email> list_email) {
                            list = list_email;
                            updateListViewInbox(list_email);
                        }
                    }
            );
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(
                            R.id.list_view,
                            new InboxFragment()
                    )
                    .replace(
                            R.id.detail_view,
                            new DetailEmailFragment()
                    )
                    .commit();
        }

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

    public void onClickSendEmail(View view) {
        startActivity(new Intent(this, SendEmailActivity.class));
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

    private class OnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Email email = list.get(i);

            inboxViewModel.getEmailLiveData().setValue(email);

            Intent intent = new Intent(InboxActivity.this, DetailEmailActivity.class);

            intent.putExtra("subject", email.subject);
            intent.putExtra("sender_email", email.sender_email);
            intent.putExtra("content", email.content);

            startActivity(intent);
        }
    }
}
