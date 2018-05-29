package org.judocanada.judocanadamobileappandroid;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static ProgressBar progressBar;
    private HashMap<ImageButton, Menuitem> menubar;
    private int WHITE, GRAY;
    public static final String PREFERENCES = "JudoCanadaApplicationPreferences";
    public static final String PREFERENCES_USERNAME = "JudoCanadaApplicationPreferenceUserName";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WHITE = ContextCompat.getColor(MainActivity.this, R.color.white);
        GRAY = ContextCompat.getColor(MainActivity.this, R.color.darker_gray);

        menubar = new HashMap<ImageButton, Menuitem>();
        menubar.put((ImageButton) findViewById(R.id.btnNews),
                new Menuitem((TextView) findViewById(R.id.txtNews),
                        new PostsFragment(),
                        R.drawable.news,
                        R.drawable.news_grey));
        menubar.put((ImageButton) findViewById(R.id.btnCalendar),
                new Menuitem((TextView) findViewById(R.id.txtCalendar),
                        new EventFragment(),
                        R.drawable.calendar,
                        R.drawable.calendar_grey));
        menubar.put((ImageButton) findViewById(R.id.btnVideo),
                new Menuitem((TextView) findViewById(R.id.txtVideo),
                        new VideoFragment(),
                        R.drawable.videojudo,
                        R.drawable.videojudo_grey));
        menubar.put((ImageButton) findViewById(R.id.btnShop),
                new Menuitem((TextView) findViewById(R.id.txtShop),
                        new StatsFragment(),
                        R.drawable.boutique_icon,
                        R.drawable.boutique_icon_grey));

        for (ImageButton imageButton : menubar.keySet()) {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectButton((ImageButton) view);
                }
            });
        }
        selectButton((ImageButton) findViewById(R.id.btnNews));

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        ImageButton logout = (ImageButton) findViewById(R.id.btnUser);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserManager.getInstance().deleteUserToPreferences(getApplicationContext());
                Intent result;
                result = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(result);
                finish();
            }
        });


    }

    private void selectButton(ImageButton button) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        for (ImageButton key : menubar.keySet()) {
            Menuitem item = menubar.get(key);
            if (key == button) {
                item.label.setTextColor(WHITE);
                key.setImageResource(item.drawableSelected);
                transaction.replace(R.id.mainFragment, item.fragment);
            } else {
                item.label.setTextColor(GRAY);
                key.setImageResource(item.drawableUnselected);
            }
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    public static void showProgressBar(boolean visible) {
        if (progressBar == null) return;
        progressBar.setVisibility((visible) ? View.VISIBLE : View.GONE);
    }

    private class Menuitem {
        int drawableSelected, drawableUnselected;
        TextView label;
        Fragment fragment;

        Menuitem(TextView label, Fragment fragment, int drawableSelected, int drawableUnselected) {
            this.label = label;
            this.drawableSelected = drawableSelected;
            this.drawableUnselected = drawableUnselected;
            this.fragment = fragment;
        }
    }
}
