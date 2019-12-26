package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.R;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.email.Email;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.viewmodel.SettingViewModel;

public class SettingActivity extends AppCompatActivity {
    private final int TEN_SECONDS = 10000;
    private final int THIRTY_SECONDS = 30000;
    private final int SIXTY_SECONDS = 60000;
    private final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private RadioGroup radioGroup;
    private SettingViewModel settingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);

        this.settingViewModel = ViewModelProviders.of(this).get(SettingViewModel.class);
        this.radioGroup = findViewById(R.id.setting_radio_group);

        new AsyncTaskCheckRadioButton().execute();

        this.radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener());
    }

    private class AsyncTaskCheckRadioButton extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... v) {
            return settingViewModel.getRefreshTime();
        }

        @Override
        protected void onPostExecute(Integer refresh_time) {
            if (refresh_time == TEN_SECONDS) {
                radioGroup.check(R.id.ten_seconds);
            }

            if (refresh_time == THIRTY_SECONDS) {
                radioGroup.check(R.id.thirty_seconds);
            }

            if (refresh_time == SIXTY_SECONDS) {
                radioGroup.check(R.id.sixty_seconds);
            }
        }
    }

    private class OnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            int refresh_time = TEN_SECONDS;

            if (i == R.id.ten_seconds) {
                refresh_time = TEN_SECONDS;
            }
            else if (i == R.id.thirty_seconds) {
                refresh_time = THIRTY_SECONDS;
            }
            else if (i == R.id.sixty_seconds) {
                refresh_time = SIXTY_SECONDS;
            }

            new AsyncTaskUpdateSetting().execute(refresh_time);
        }
    }

    private class AsyncTaskUpdateSetting extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... i) {
            int refresh_time = i[0];

            settingViewModel.updateRefreshTime(refresh_time);

            return null;
        }
    }

    public void onClickSave(View view) {
        if (write_external_storage_is_allowed()) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                new AsyncTaskSaveInbox().execute();
            } else {
                Toast.makeText(this, getResources().getString(R.string.external_storage_not_mounted_warning), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean write_external_storage_is_allowed() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.write_external_storage_permission_explanation_title))
                    .setMessage(getResources().getString(R.string.write_external_storage_permission_explanation_message))
                    .setPositiveButton(
                            android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    requestWriteExternalStoragePermission();
                                }
                            }
                    )
                    .create()
                    .show();
        } else {
            requestWriteExternalStoragePermission();
        }
        return false;
    }

    private void requestWriteExternalStoragePermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onClickSave(null);
            } else {
                Toast.makeText(this, getResources().getString(R.string.write_external_storage_permission_not_allow), Toast.LENGTH_LONG).show();
            }
        }
    }

    private class AsyncTaskSaveInbox extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... v) {
            String path = "";
            try {
                FileOutputStream writer = new FileOutputStream(
                        new File(
                                Environment.getExternalStorageDirectory().getAbsolutePath(),
                                "Public Mail By Nicho - Inbox.txt"
                        )
                );

                for (Email email : settingViewModel.getAllEmail()) {
                    writer.write(
                            String.format(
                                    "%s\n%s\n%s\n\n",
                                    email.sender_email,
                                    email.subject,
                                    email.content
                            ).getBytes()
                    );
                }

                writer.close();

                path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Public Mail By Nicho - Inbox.txt";
            } catch (IOException e) {
                Log.e("IOException", e.toString());
            }
            return path;
        }

        @Override
        protected void onPostExecute(String path) {
            Toast.makeText(SettingActivity.this, getResources().getString(R.string.path_of_inbox_file) + " " + path, Toast.LENGTH_LONG).show();
        }
    }
}
