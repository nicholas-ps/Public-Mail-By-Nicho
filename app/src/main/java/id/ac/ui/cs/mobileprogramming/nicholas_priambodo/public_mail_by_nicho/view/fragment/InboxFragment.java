package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.R;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.email.Email;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.viewmodel.InboxViewModel;

public class InboxFragment extends Fragment {
    private InboxViewModel inboxViewModel;
    private ListView listView;
    private List<Email> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedOInstanceState) {
        return inflater.inflate(
                R.layout.inbox_fragment,
                container,
                false
        );
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.inboxViewModel = ViewModelProviders.of(getActivity()).get(InboxViewModel.class);
        this.listView = getView().findViewById(R.id.inbox);
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
                getContext(),
                this.inboxViewModel.convertToListHash(list_email),
                R.layout.list_email,
                from,
                to
        );

        this.listView.setAdapter(simpleAdapter);
    }

    private class OnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Email email = list.get(i);
            inboxViewModel.getEmailLiveData().setValue(email);
        }
    }
}
