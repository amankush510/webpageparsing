package com.example.amank.credsnapdeal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by amank on 25-11-2017.
 */

public class ProductssListAdapter extends BaseAdapter {
    private ArrayList<ProductModel> products;
    private Context context;

    public ProductssListAdapter(Context context, ArrayList<ProductModel> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        if(products != null){
            return products.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {
        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (v == null) {
            view = inflater.inflate(R.layout.product_list_item, null);
            TextView tv_title = view.findViewById(R.id.tv_title);
            ImageView iv_product = view.findViewById(R.id.iv_product);
            tv_title.setText(products.get(i).getTitle());
            Picasso.with(context).load(products.get(i).getUrl()).resize((int)context.getResources().getDimension(R.dimen.product_width), (int)context.getResources().getDimension(R.dimen.product_height)).into(iv_product);
        } else {
            view = v;
        }
        return view;
    }
}
