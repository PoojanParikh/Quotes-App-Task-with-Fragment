package com.vnurture.vnurture.quote2;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by VNurtureTechnologies on 20/02/17.
 */

public class ListViewfragment extends Fragment {

    ListView listView;


    int position;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view_fragment,container,false);

        listView=(ListView) view.findViewById(R.id.list_view);

        position = getArguments().getInt("QuotesList");

        new MyListClass().execute("http://rapidans.esy.es/test/getquotes.php?cat_id=" +position);


        return view;
    }

    class MyListClass extends AsyncTask<String, Void, String> {

        ProgressDialog dialog;
        ArrayList<QuoteModel> quotesModelArrayList;
        ListCustomAdapter listCustomAdapter;
        Exception exceptionList;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection;
            try {
                URL url = new URL(params[0]);
                try {
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    InputStream stream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();
                    String line = "";

                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                    String bufferString = buffer.toString();
                    return bufferString;


                } catch (Exception e) {
                    this.exceptionList = e;

                }
            } catch (Exception e) {
                this.exceptionList = e;

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            quotesModelArrayList = new ArrayList<>();

            try {

                JSONObject rootObject = new JSONObject(s);


                JSONArray dataArray = rootObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject jsonObject = dataArray.getJSONObject(i);


                    QuoteModel p = new QuoteModel();

                    p.setId(jsonObject.getInt("id"));
                    p.setCatId(jsonObject.getInt("cat_id"));
                    p.setQuotes(jsonObject.getString("quotes"));

                    quotesModelArrayList.add(p);
                }



            } catch (Exception e) {
                this.exceptionList = e;
                Toast.makeText(getActivity(), "Requires Internet Connection", Toast.LENGTH_SHORT).show();
            }

            listCustomAdapter = new ListCustomAdapter(getActivity(),quotesModelArrayList);

            listView.setAdapter(listCustomAdapter);
        }

    }

}
