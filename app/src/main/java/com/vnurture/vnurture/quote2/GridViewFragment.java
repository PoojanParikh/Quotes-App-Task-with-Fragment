package com.vnurture.vnurture.quote2;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
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

public class GridViewFragment extends Fragment {

    GridView gridView;
    ListViewfragment listViewfragment = new ListViewfragment();



    int[] image = new int[]{
            R.drawable.ic_life,
            R.drawable.ic_vue,
            R.drawable.ic_facebook_love,
            R.drawable.ic_fedora,
            R.drawable.ic_pinterest_circle,
            R.drawable.ic_yaoming_meme,
            R.drawable.ic_mega_icon
    };



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grid_view_fragment,container,false);



        gridView=(GridView) view.findViewById(R.id.fragment_grid);




        new MyGridClass().execute("http://rapidans.esy.es/test/getallcat.php");

        return view;
    }


    class MyGridClass extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        Exception exceptionGrid;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
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
                    this.exceptionGrid = e;

                }
            } catch (Exception e) {
                this.exceptionGrid=e;

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            ArrayList<QuotesCatagoryModel> quotesCatagoryModelArrayList = new ArrayList<>();

            try {

                JSONObject rootObject = new JSONObject(s);


                JSONArray dataArray = rootObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject jsonObject = dataArray.getJSONObject(i);


                    QuotesCatagoryModel p = new QuotesCatagoryModel();

                    p.setId(jsonObject.getInt("id"));
                    p.setCatagory(jsonObject.getString("name"));

                    quotesCatagoryModelArrayList.add(p);
                }



            } catch (Exception e) {
                this.exceptionGrid = e;
                Toast.makeText(getActivity(), "Requires Internet Connection", Toast.LENGTH_SHORT).show();
            }


            GridCustomAdapter customAdapterGrid = new GridCustomAdapter(getActivity(),quotesCatagoryModelArrayList,image);

            gridView.setAdapter(customAdapterGrid);
        }
    }

}
