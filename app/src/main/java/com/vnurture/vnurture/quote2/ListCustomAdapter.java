package com.vnurture.vnurture.quote2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by VNurtureTechnologies on 20/02/17.
 */

public class ListCustomAdapter extends BaseAdapter {
    ArrayList<QuoteModel> quotesModelArrayList;
    LayoutInflater layoutInflater;
    Context context;

    ListCustomAdapter(Context context,ArrayList<QuoteModel> quotesModelArrayList){
        this.context=context;
        this.quotesModelArrayList=quotesModelArrayList;
    }

    @Override
    public int getCount() {
        return quotesModelArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return quotesModelArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class ViewHolderList{

        TextView tvQuotesListView;

    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        ViewHolderList holderList;

        if (view == null) {
            holderList = new ViewHolderList();
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.single_row_list_view, viewGroup, false);



            holderList.tvQuotesListView=(TextView) view.findViewById(R.id.tv_list_view);

            view.setTag(holderList);
        }

        else{
            holderList=(ViewHolderList) view.getTag();
        }

        /*holder.tvIdListView.setText("Id: "+String.valueOf(quotesModelArrayList.get(i).getId()));
        holder.tvCatIdListView.setText("Name: "+ quotesModelArrayList.get(i).getCatId());*/
        holderList.tvQuotesListView.setText(quotesModelArrayList.get(i).getQuotes());
        holderList.tvQuotesListView.setSelected(true);
        holderList.tvQuotesListView.requestFocus();

        holderList.tvQuotesListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentList = new Intent(context,FullPageQuoteFragment.class);
                intentList.putExtra("Quotes",quotesModelArrayList.get(i).getQuotes());
                context.startActivity(intentList);
            }
        });

        return view;
    }
}
