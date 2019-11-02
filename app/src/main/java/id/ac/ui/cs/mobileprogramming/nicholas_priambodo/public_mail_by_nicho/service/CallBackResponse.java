package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.service;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.email.Email;

public interface CallBackResponse {
    void execute(List<Email> list_email);
}
