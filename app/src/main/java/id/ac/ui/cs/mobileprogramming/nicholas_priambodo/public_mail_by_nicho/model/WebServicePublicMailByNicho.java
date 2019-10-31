package id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.R;
import id.ac.ui.cs.mobileprogramming.nicholas_priambodo.public_mail_by_nicho.viewmodel.CallBackResponse;

public class WebServicePublicMailByNicho {
    private RequestQueue requestQueue;
    private String url_inbox;

    public WebServicePublicMailByNicho(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
        this.url_inbox = context.getResources().getString(R.string.url_inbox);
    }

    public void getInbox(String username, final CallBackResponse call) {
        String url_inbox_query = this.url_inbox + "?username=" + username;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url_inbox_query,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        call.callBack(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(null, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        this.requestQueue.add(jsonArrayRequest);
    }
}
