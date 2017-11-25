package com.example.amank.credsnapdeal;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();

    private GridView gv_products_list;
    private LinearLayout ll_home;
    private TextView tv_error;
    private Menu menu;

    private static ProductssListAdapter adapter;
    private static ArrayList<ProductModel> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "Activity created");
        init();
        initUI();
        initUIActions();
        onNewIntent(getIntent());
    }

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }

    private void init() {
        products = new ArrayList<>();
        adapter = new ProductssListAdapter(this, products);
    }

    private void initUI() {
        gv_products_list = findViewById(R.id.gv_products_list);
        ll_home = findViewById(R.id.ll_home);
        tv_error = findViewById(R.id.tv_error);
    }

    private void initUIActions() {
        gv_products_list.setAdapter(adapter);
    }

    private void getData(String query) {
        LoadData data = new LoadData();
        data.execute(query);
    }

    class LoadData extends AsyncTask<String, String, ArrayList<ProductModel>> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected ArrayList<ProductModel> doInBackground(String... params) {
            Object obj = RequestHandler.getData(params[0]);
            if (obj != null) {
                products.addAll((ArrayList) obj);
            }
            for (ProductModel model : products) {
                Log.e(TAG, model.getTitle() + " -----------------------------------------" + model.getUrl());
            }
            return products;
        }

        @Override
        protected void onPostExecute(ArrayList<ProductModel> productModels) {
            super.onPostExecute(productModels);
            if (productModels != null) {
                if (productModels.size() != 0) {
                    ll_home.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                } else {
                    tv_error.setText(getResources().getString(R.string.no_product_find_error));
                    ll_home.setVisibility(View.VISIBLE);
                }
            }
            dialog.cancel();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            if (Utils.isNetworkAvailable(this)) {
                getData(intent.getStringExtra(SearchManager.QUERY));
            } else {
                Toast.makeText(this, "Please check your internet connection and try again!", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        this.menu = menu;
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView sv_products_search = (SearchView) menu.findItem(R.id.menu_products_search).getActionView();
        sv_products_search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_products_search).getActionView();
        searchView.setIconified(false);
    }
}

