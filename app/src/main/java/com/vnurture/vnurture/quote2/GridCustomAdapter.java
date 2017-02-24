package com.vnurture.vnurture.quote2;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by VNurtureTechnologies on 20/02/17.
 */

public class GridCustomAdapter extends BaseAdapter {
    ArrayList<QuotesCatagoryModel> quotesCatagoryModelArrayList;
    Context contextGrid;
    LayoutInflater inflater;
    int[] images;
    ListViewfragment listViewFragment;

    GridCustomAdapter(Context contextGrid,ArrayList<QuotesCatagoryModel> quotesCatagoryModelArrayList,int[] images){

        this.contextGrid=contextGrid;
        this.quotesCatagoryModelArrayList=quotesCatagoryModelArrayList;
        this.images=images;

    }

    @Override
    public int getCount() {
        return quotesCatagoryModelArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return quotesCatagoryModelArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class ViewHolder{
        TextView tvGridView;
        ImageView ivGridView;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            inflater = (LayoutInflater) contextGrid.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.single_row_grid_view, viewGroup, false);



            holder.tvGridView=(TextView) view.findViewById(R.id.tv_grid_view);
            holder.tvGridView.setSelected(true);
            holder.tvGridView.requestFocus();

            holder.ivGridView=(ImageView) view.findViewById(R.id.grid_image);

            view.setTag(holder);
        }

        else{
            holder = (ViewHolder) view.getTag();
        }


        holder.tvGridView.setText(quotesCatagoryModelArrayList.get(i).getCatagory());
        holder.tvGridView.setSelected(true);
        holder.ivGridView.setImageResource(images[i]);

        holder.tvGridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listViewFragment = new ListViewfragment();

                Bundle bundleGrid = new Bundle();
                bundleGrid.putInt("QuotesList",quotesCatagoryModelArrayList.get(i).getId());
                listViewFragment.setArguments(bundleGrid);

                Activity activityGrid = (Activity) contextGrid;

                FragmentManager fragmentManager = activityGrid.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_activity_layout,listViewFragment);
                fragmentTransaction.addToBackStack("");
                fragmentTransaction.commit();

            }
        });

        holder.ivGridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listViewFragment = new ListViewfragment();

                Bundle bundleGrid = new Bundle();
                bundleGrid.putInt("QuotesList",quotesCatagoryModelArrayList.get(i).getId());
                listViewFragment.setArguments(bundleGrid);


                Activity activityGrid = (Activity) contextGrid;

                FragmentManager fragmentManager = activityGrid.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_activity_layout,listViewFragment);
                fragmentTransaction.addToBackStack("");
                fragmentTransaction.commit();

            }
        });

        return view;
    }
}
