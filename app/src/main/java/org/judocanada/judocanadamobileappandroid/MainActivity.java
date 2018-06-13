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

import org.judocanada.judocanadamobileappandroid.Model.Cart;
import org.judocanada.judocanadamobileappandroid.Model.UserManager;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static ProgressBar progressBar;
    private static TextView cartProductCount;
    private HashMap<ImageButton, Menuitem> menubar;
    private int WHITE, GRAY;
    private static final String SELECTED_FRAGMENT = "JudoCanadaApplication.selectedFragment";
    private int selectedFragment = -1;


    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        // Save the state of item position
        outState.putInt(SELECTED_FRAGMENT, selectedFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Read the state of item position
        selectedFragment = savedInstanceState.getInt(SELECTED_FRAGMENT);
        selectButton((ImageButton) findViewById(selectedFragment));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WHITE = ContextCompat.getColor(MainActivity.this, R.color.white);
        GRAY = ContextCompat.getColor(MainActivity.this, R.color.darker_gray);

        menubar = new HashMap<ImageButton, Menuitem>();
        menubar.put((ImageButton) findViewById(R.id.btnNews),
                new Menuitem((TextView) findViewById(R.id.txtNews),
                        PostsFragment.class,
                        R.drawable.news,
                        R.drawable.news_grey));
        menubar.put((ImageButton) findViewById(R.id.btnCalendar),
                new Menuitem((TextView) findViewById(R.id.txtCalendar),
                        EventFragment.class,
                        R.drawable.calendar,
                        R.drawable.calendar_grey));
        menubar.put((ImageButton) findViewById(R.id.btnVideo),
                new Menuitem((TextView) findViewById(R.id.txtVideo),
                        VideoFragment.class,
                        R.drawable.videojudo,
                        R.drawable.videojudo_grey));
        menubar.put((ImageButton) findViewById(R.id.btnShop),
                new Menuitem((TextView) findViewById(R.id.txtShop),
                        ProductFragment.class,
                        R.drawable.boutique_big,
                        R.drawable.boutique_big_gray));

        for (ImageButton imageButton : menubar.keySet()) {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectButton((ImageButton) view);
                }
            });
        }
        if (savedInstanceState == null)
            selectButton((ImageButton) findViewById(R.id.btnNews));

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        cartProductCount = (TextView) findViewById(R.id.cartProductCount);
        setCartProductCount();

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

    public static void setCartProductCount() {
        if (cartProductCount == null) return;
        if(Cart.getInstance().getCount()==0){
            cartProductCount.setVisibility(View.GONE);
        }
        else{
            cartProductCount.setVisibility(View.VISIBLE);
            cartProductCount.setText(Cart.getInstance().getCount()+"");
        }
    }

    private void selectButton(ImageButton button) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        selectedFragment = ((View)button).getId();

        for (ImageButton key : menubar.keySet()) {
            Menuitem item = menubar.get(key);
            if (key == button) {
                item.label.setTextColor(WHITE);
                key.setImageResource(item.drawableSelected);
                try {
                    transaction.replace(R.id.mainFragment, (Fragment) item.fragment.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
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
        Class fragment;

        Menuitem(TextView label, Class fragment, int drawableSelected, int drawableUnselected) {
            this.label = label;
            this.drawableSelected = drawableSelected;
            this.drawableUnselected = drawableUnselected;
            this.fragment = fragment;
        }
    }
}
