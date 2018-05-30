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

public class PostActivity extends AppCompatActivity {
    public static final String POST = "org.judocanada.judocanadamobileappandroid.PostActivity.POST";
    private Post post;
    private TextView title;
    private TextView content;
    private ImageView imgPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_post);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        post = getIntent().getExtras().getParcelable(POST);
        if (post == null) return;

        title = (TextView) findViewById(R.id.txtTitle);
        if (title == null) return;

        content = (TextView) findViewById(R.id.txtContent);
        if(content == null) return;

        title.setText(post.getTitle());
        content.setText(Html.fromHtml(post.getContent()));

        imgPost = (ImageView) findViewById(R.id.imgPost);
        if (imgPost == null) return;

        if(post.getImageList()!=null && post.getImageList().size()>0)
            Picasso.get().load(post.getImageList().get(0)).into(imgPost);
        else
            imgPost.setVisibility(View.GONE);

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
