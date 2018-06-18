package org.judocanada.judocanadamobileappandroid;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.judocanada.judocanadamobileappandroid.Api.ApiHelper;
import org.judocanada.judocanadamobileappandroid.Api.Callback;
import org.judocanada.judocanadamobileappandroid.Model.Cart;
import org.judocanada.judocanadamobileappandroid.Model.Post;
import org.judocanada.judocanadamobileappandroid.Model.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductFragment extends Fragment {
    private ListView mainListView;
    private ArrayList<Product> products;
    private CustomAdapter customAdapter;
    private ApiHelper apiHelper;
    private ProgressBar progressBar;
    private Button checkout;

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        mainListView = (ListView) view.findViewById(R.id.mainListView);
        mainListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                intent  = new Intent(getActivity(), ProductActivity.class);

                intent.putExtra(ProductActivity.PRODUCT, products.get(position));
                startActivity(intent);
            }
        });
        products = new ArrayList<Product>();
        apiHelper = new ApiHelper(getActivity().getApplicationContext());
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mainListView = (ListView) view.findViewById(R.id.mainListView);
        checkout = (Button) view.findViewById(R.id.checkout);
        customAdapter = new  CustomAdapter();
        mainListView.setAdapter(customAdapter);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent  = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });

        checkout.setVisibility((Cart.getInstance().getCount()>0)?View.VISIBLE:View.GONE);

        MainActivity.showProgressBar(true);
        apiHelper.getProducts(getActivity(), new Callback() {
            @Override
            public void methodToCallBack(Object object) {
                MainActivity.showProgressBar(false);
                if(object == null) return;
                ArrayList<Product> tempsProduct = ((ArrayList<Product>) object);
                if (products == null) return;

                products = tempsProduct;
                customAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }


    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return products.size();
        }

        @Override
        public Object getItem(int i) {
            return products.get(i);
        }

        @Override
        public long getItemId(int i) {
            return products.get(i).getEntityId();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.product_row, null);
            TextView name = (TextView) view.findViewById(R.id.txtName);
            TextView description = (TextView) view.findViewById(R.id.txtDescription);
            TextView price = (TextView) view.findViewById(R.id.txtPrice);
            ImageView image = (ImageView) view.findViewById(R.id.imgProduct);
            ImageButton addToCart = (ImageButton) view.findViewById(R.id.buttonAddtoCart);


            final Product product = products.get(i);
            name.setText(product.getName());
            description.setText(product.getShortDescription());
            DecimalFormat prices = new DecimalFormat("$0.00");
            price.setText(prices.format(product.getRegularPriceWithoutTax()));
            Picasso.get().load(product.getImageUrl()).into(image);

            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cart.getInstance().addToCart(product, 1);
                    MainActivity.setCartProductCount();
                    checkout.setVisibility((Cart.getInstance().getCount()>0)?View.VISIBLE:View.GONE);
                }
            });

            return view;
        }
    }

}