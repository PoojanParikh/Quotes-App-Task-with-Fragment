package com.vnurture.vnurture.quote2;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by VNurtureTechnologies on 20/02/17.
 */

public class FullPageQuoteFragment extends Fragment {

    String positionQuote;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.full_page_quote_fragment,container,false);
        setHasOptionsMenu(true);
        TextView quoteText = (TextView) view.findViewById(R.id.tv_quote);
        positionQuote = getArguments().getString("Quote");
        quoteText.setText(positionQuote);



        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.quote_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){

            case R.id.share_icon: {
                Intent intentShare = new Intent();
                intentShare.setType("text/plain");

                positionQuote = getArguments().getString("Quote");
                intentShare.getStringExtra("Quote");
                intentShare.putExtra(Intent.EXTRA_TEXT, positionQuote);
                startActivity(Intent.createChooser(intentShare, "Share Using"));

                return true;

            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
