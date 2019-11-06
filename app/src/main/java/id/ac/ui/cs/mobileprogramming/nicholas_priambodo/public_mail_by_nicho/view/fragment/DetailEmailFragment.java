package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.view.fragment;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.R;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.email.Email;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.viewmodel.InboxViewModel;

public class DetailEmailFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedOInstanceState) {
        return inflater.inflate(
                R.layout.detail_email_fragment,
                container,
                false
        );
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       ViewModelProviders
               .of(getActivity())
               .get(InboxViewModel.class)
               .getEmailLiveData()
               .observe(
                       this,
                       new Observer<Email>() {
                           @Override
                           public void onChanged(Email email) {
                               ((TextView) getView().findViewById(R.id.subject_email)).setText(email.subject);
                               ((TextView) getView().findViewById(R.id.sender)).setText(email.sender_email);
                               ((TextView) getView().findViewById(R.id.content_email)).setText(email.content);
                               ((TextView) getView().findViewById(R.id.content_email)).setMovementMethod(new ScrollingMovementMethod());
                           }
                       }
               );
    }
}
