package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.R;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model.email.Email;

public class PublicMailByNichoAPI {
    private RequestQueue requestQueue;
    private String url_inbox;

    public PublicMailByNichoAPI(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
        this.url_inbox = context.getResources().getString(R.string.url_inbox);
    }

    public void getInbox(String username, final CallBackResponse callBack) {
        String url_inbox_query = this.url_inbox + "?username=" + username;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url_inbox_query,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<Email> list_email = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                Email email = new Email();
                                email.sender_email = jsonObject.getJSONObject("sender").getString("email");
                                email.eid = jsonObject.getInt("id");
                                email.subject = jsonObject.getString("subject");
                                email.content = jsonObject.getString("content");

                                list_email.add(email);
                            }

                            callBack.execute(list_email);
                        }
                        catch (Exception e) {
                            Log.e("error", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", error.toString());
                    }
                }
        );

        this.requestQueue.add(jsonArrayRequest);
    }
}
