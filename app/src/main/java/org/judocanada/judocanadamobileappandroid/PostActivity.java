package org.judocanada.judocanadamobileappandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class PostActivity extends AppCompatActivity {
    public static final String POST = "org.judocanada.judocanadamobileappandroid.PostActivity.POST";
    private Post post;
    private TextView title;
    private TextView content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        post = getIntent().getExtras().getParcelable(POST);
        if (post == null) return;

        title = (TextView) findViewById(R.id.textTitle);
        if (title == null) return;

        content = (TextView) findViewById(R.id.textContent);
        if(content == null) return;

        title.setText(post.getTitle());
        content.setText(Html.fromHtml(post.getContent()));

    }
}
