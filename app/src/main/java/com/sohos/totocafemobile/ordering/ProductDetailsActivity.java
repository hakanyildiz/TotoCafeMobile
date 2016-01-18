package com.sohos.totocafemobile.ordering;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.sohos.totocafemobile.R;
import com.sohos.totocafemobile.pojo.Constant;
import com.sohos.totocafemobile.pojo.Product;

/**
 * Created by okano on 18.01.2016.
 */
public class ProductDetailsActivity extends AppCompatActivity{
    private static final String TAG = "HAKKE";

    public Toolbar mToolbar;
    public Context context;
    TextView tvProductName,tvProductDetail;
    Button btnOrder;
    Spinner spQuantity;

    Product currentProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        context = this;
        setupToolbar();

        //reading product parcelable
        Bundle data = getIntent().getExtras();
        currentProduct = (Product) data.getParcelable("product");

        Log.d("HAKKE", "Current Product: " + currentProduct.getProductName() );

        //Log.d(TAG, "Product hashCode: " + currentProduct.hashCode());
        //Retrieve views
        retrieveViews();

        //Set product properties
        setProductProperties();

        //Initialize quantity
        initializeQuantity();

        //On ordering of product
        onOrderProduct();


    }


    public void setupToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        //mContainerToolbar = (ViewGroup) findViewById(R.id.container_app_bar);
        //set the Toolbar as ActionBar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void retrieveViews() {
        tvProductName = (TextView) findViewById(R.id.tvProductName);
        tvProductDetail = (TextView) findViewById(R.id.tvProductDetail);
        spQuantity = (Spinner) findViewById(R.id.spQuantity);
        btnOrder = (Button) findViewById(R.id.btnOrder);
    }

    private void setProductProperties() {
        tvProductName.setText(currentProduct.getProductName());
        tvProductDetail.setText(currentProduct.getDetail());
    }

    private void initializeQuantity() {
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, Constant.QUANTITY_LIST);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spQuantity.setAdapter(dataAdapter);
    }

    private void onOrderProduct() {
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = CartHelper.getCart();
                Log.d(TAG, "Adding product: " + currentProduct.getProductName());
                cart.add(currentProduct, Integer.valueOf(spQuantity.getSelectedItem().toString()));
                Intent intent = new Intent(ProductDetailsActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
            }
        });
    }



}
