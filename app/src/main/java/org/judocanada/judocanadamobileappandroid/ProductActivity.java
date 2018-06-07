package org.judocanada.judocanadamobileappandroid;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.judocanada.judocanadamobileappandroid.Model.Post;
import org.judocanada.judocanadamobileappandroid.Model.Product;

public class ProductActivity extends AppCompatActivity {
    public static final String PRODUCT = "org.judocanada.judocanadamobileappandroid.PostActivity.PRODUCT";
    private Product product;
    private TextView title;
    private TextView content;
    private ImageView imgPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_product);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        product = getIntent().getExtras().getParcelable(PRODUCT);
        if (product == null) return;

        title = (TextView) findViewById(R.id.txtTitle);
        if (title == null) return;

        content = (TextView) findViewById(R.id.txtContent);
        if(content == null) return;

        title.setText(product.getName());
        content.setText(product.getMetaDescription());

        imgPost = (ImageView) findViewById(R.id.imgPost);
        if (imgPost == null) return;

        Picasso.get().load(product.getImageUrl()).into(imgPost);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
