package com.sohos.totocafemobile.ordering;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.sohos.totocafemobile.R;
import com.sohos.totocafemobile.activities.MainActivity;
import com.sohos.totocafemobile.adapters.CartItemAdapter;
import com.sohos.totocafemobile.pojo.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by okano on 18.01.2016.
 */
public class ShoppingCartActivity extends AppCompatActivity {
    private static final String TAG = "HAKKE";
    public Toolbar mToolbar;
    public Context context;


    ListView lvCartItems;
    Button bClear;
    Button bShop;
    TextView tvTotalPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        context = this;
//        setupToolbar();

        //reading product parcelable
        Bundle data = getIntent().getExtras();
//        currentProduct = (Product) data.getParcelable("product");

  //      Log.d(TAG, "Product hashCode: " + currentProduct.hashCode());
        //Retrieve views
        lvCartItems = (ListView) findViewById(R.id.lvCartItems);
        LayoutInflater layoutInflater = getLayoutInflater();

        final Cart cart = CartHelper.getCart();
        final TextView tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);
        tvTotalPrice.setText(String.valueOf(cart.getTotalPrice()));

        lvCartItems.addHeaderView(layoutInflater.inflate(R.layout.cart_header, lvCartItems, false));

        final CartItemAdapter cartItemAdapter = new CartItemAdapter(this);
        cartItemAdapter.updateCartItems(getCartItems(cart));

        lvCartItems.setAdapter(cartItemAdapter);

        bClear = (Button) findViewById(R.id.bClear);
        bShop = (Button) findViewById(R.id.bShop);

        bClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clearing the shopping cart");
                cart.clear();
                cartItemAdapter.updateCartItems(getCartItems(cart));
                cartItemAdapter.notifyDataSetChanged();
                tvTotalPrice.setText(String.valueOf(cart.getTotalPrice()));
            }
        });

        bShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingCartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<CartItem> getCartItems(Cart cart) {
        List<CartItem> cartItems = new ArrayList<CartItem>();
        Log.d(TAG, "Current shopping cart: " + cart);

        Map<Saleable, Integer> itemMap = (Map<Saleable, Integer>) cart.getItemWithQuantity();

        for (Map.Entry<Saleable, Integer> entry : itemMap.entrySet()) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct((Product) entry.getKey());
            cartItem.setQuantity(entry.getValue());
            cartItems.add(cartItem);
        }

        Log.d(TAG, "Cart item list: " + cartItems);
        return cartItems;
    }


    public void setupToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        //mContainerToolbar = (ViewGroup) findViewById(R.id.container_app_bar);
        //set the Toolbar as ActionBar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }



}
