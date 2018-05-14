package org.judocanada.judocanadamobileappandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PostActivity extends AppCompatActivity {
    public static final String POST = "org.judocanada.judocanadamobileappandroid.PostActivity.POST";
    private Post post;
    private TextView title;
    private TextView content;
    private ImageView imgPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

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

}
