package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.RadioGroup;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.R;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.viewmodel.SettingViewModel;

public class SettingActivity extends AppCompatActivity {
    private final int TEN_SECONDS = 10000;
    private final int THIRTY_SECONDS = 30000;
    private final int SIXTY_SECONDS = 60000;
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
}
