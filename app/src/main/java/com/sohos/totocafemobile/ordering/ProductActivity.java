package com.sohos.totocafemobile.ordering;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sohos.totocafemobile.R;
import com.sohos.totocafemobile.adapters.AdapterProduct;
import com.sohos.totocafemobile.adapters.CustomItemClickListener;
import com.sohos.totocafemobile.pojo.Product;
import com.sohos.totocafemobile.restful.JSONParser;
import com.sohos.totocafemobile.restful.RestAPI;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by okano on 17.01.2016.
 */
public class ProductActivity extends AppCompatActivity {
    Context context;
    Toolbar mToolbar;
    RecyclerView recyclerViewProduct;
    AdapterProduct adapterProduct;
    ArrayList<Product> productList = new ArrayList<Product>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        context = this;

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        //mContainerToolbar = (ViewGroup) findViewById(R.id.container_app_bar);
        //set the Toolbar as ActionBar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();
        int categoryID = -1;
        if(bundle != null){
            categoryID = bundle.getInt("categoryID");

            getProductsOfCategory(categoryID);

        }

        recyclerViewProduct = (RecyclerView) findViewById(R.id.recyclerViewProduct);
        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(this));

        adapterProduct = new AdapterProduct(this, productList, new CustomItemClickListener() {
            @Override
            public void onItemClicked(View v, int position) {
                String productName = productList.get(position).getProductName();
                //Log.d("HAKKE", "clicked categoryID " + categoryID);
                Product currentProduct = productList.get(position);
                Log.d("HAKKE", "selected product: " + productList.get(position).getProductName());

                Intent i = new Intent(ProductActivity.this, ProductDetailsActivity.class);
                Bundle bundle = new Bundle();
                i.putExtra("product" , currentProduct);
                
                startActivity(i);

                //Intent i = new Intent(context, ProductActivity.class);
                // bundle.putInt("categoryID" , categoryID);
                //i.putExtra("product", productList.get(position));
                //startActivity(i);


                //reading product parcelable
                //Bundle data = getIntent().getExtras();
                //Product currentProduct = (Product) data.getParcelable("product");

            }
        });
        recyclerViewProduct.setAdapter(adapterProduct);



    }

    private void getProductsOfCategory(int categoryID) {
        new AsyncLoadProducts().execute(categoryID);
    }

    protected class AsyncLoadProducts extends AsyncTask<Integer, JSONObject, ArrayList<Product>> {
        ArrayList<Product> productTable = new ArrayList<Product>();

        @Override
        protected ArrayList<Product> doInBackground(Integer... params) {
            // TODO Auto-generated method stub

            RestAPI api = new RestAPI();
            try {
                JSONObject jsonObj = api.getProductViaCategory(params[0]);
                JSONParser parser = new JSONParser();
                productTable = parser.parseProduct(jsonObj);
                Log.d("HAKKE",  "first product Name: " +  productTable.get(0).getProductName());

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadDeptDetails", e.getMessage());

            }

            return productTable;
        }

        @Override
        protected void onPostExecute(ArrayList<Product> result) {
            // TODO Auto-generated method stub
            Log.d("HAKKE", "result.size : "  + result.toString());

            for (int i = 0; i < result.size(); i++) {
                //data.add(result.get(i).getCategoryID() + " " + result.get(i).getCategoryName());

                Product myProduct = new Product();

                Log.d("HAKKE", "My Product NAME: " + result.get(i).getProductName());

                myProduct.setProductID(result.get(i).getProductID());
                myProduct.setProductName(result.get(i).getProductName());
                myProduct.setDetail(result.get(i).getDetail());
                myProduct.setCredit(result.get(i).getCredit());
                myProduct.setPrice(result.get(i).getPrice());
                myProduct.setCategoryID(result.get(i).getCategoryID());
                myProduct.setAvailabilityID(result.get(i).getAvailabilityID());

                productList.add(myProduct);
            }
            //send to current categoryList to adapterCategory Class
            adapterProduct.setProductList(productList);
            //adapterCategory.notifyDataSetChanged();
            //adapter.notifyDataSetChanged();
            Toast.makeText(context, "Getting Products Completed", Toast.LENGTH_SHORT).show();
        }

    }




}
