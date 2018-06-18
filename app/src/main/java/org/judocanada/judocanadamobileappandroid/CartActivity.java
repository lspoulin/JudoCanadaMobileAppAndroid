package org.judocanada.judocanadamobileappandroid;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.judocanada.judocanadamobileappandroid.Model.Cart;
import org.judocanada.judocanadamobileappandroid.Model.Product;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {

    private Button checkout;
    private ListView mainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_cart);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mainList = (ListView) findViewById(R.id.mainListView);
        checkout = (Button) findViewById(R.id.checkout);
        CustomAdapter adapter = new CustomAdapter();
        mainList.setAdapter(adapter);


    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Cart.getInstance().getCount();
        }

        @Override
        public Object getItem(int i) {
            return Cart.getInstance().getProducts(i);
        }

        @Override
        public long getItemId(int i) {
            return Cart.getInstance().getProducts(i).product.getEntityId();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.product_cart_row, null);
            TextView name = (TextView) view.findViewById(R.id.txtName);
            TextView description = (TextView) view.findViewById(R.id.txtDescription);
            TextView qte = (TextView) view.findViewById(R.id.txtQte);
            ImageView image = (ImageView) view.findViewById(R.id.imgProduct);


            final Cart.Products products = Cart.getInstance().getProducts(i);
            name.setText(products.product.getName());
            description.setText(products.product.getShortDescription());

            qte.setText(String.valueOf(products.quantity));
            Picasso.get().load(products.product.getImageUrl()).into(image);


            return view;
        }
    }
}
