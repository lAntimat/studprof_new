package ru.lantimat.studprof;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import ru.lantimat.studprof.Feeds.FeedActivity;

public class MainActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public Spinner spinner;
    AccountHeader headerResult;
    public Drawer result;
    FrameLayout frameLayout;
    Intent drawerIntent = null;
    boolean dontFinish = false;
    SecondaryDrawerItem sign_exit;
    int color;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        frameLayout = (FrameLayout)findViewById(R.id.content_frame);

        spinner = (Spinner) findViewById(R.id.spinner_nav);
        spinner.setVisibility(View.INVISIBLE);

        color = ContextCompat.getColor(getApplicationContext(), R.color.accent);

        getSupportActionBar().setTitle("");


        initAccountHeader();
        setupNavigationDrawer();

    }

    public void updateDrawer() {
        StringHolder stringHolder;
        //if(CheckAuth.isAuth()) stringHolder = new StringHolder(R.string.drawer_item_exit);
        //else stringHolder = new StringHolder(R.string.drawer_item_sign_in);
        stringHolder = new StringHolder(getString(R.string.drawer_item_sign_in));
        result.updateName(6, stringHolder);
    }

    @Override
    public void onBackPressed() {
        if(result!=null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else super.onBackPressed();
    }

    private void initAccountHeader() {
        String fullName = "";
        String group = "";
        ProfileDrawerItem profileDrawerItem = null;

        profileDrawerItem = new ProfileDrawerItem()
                        .withName(fullName)
                        .withIcon(R.mipmap.ic_launcher);

        if(profileDrawerItem!=null) {
            headerResult = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withHeaderBackground(R.drawable.drawer_bg)
                    //.withCompactStyle(true)
                    .addProfiles(profileDrawerItem)
                    .build();
        } else {
            headerResult = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withHeaderBackground(R.drawable.drawer_bg)
                    //.withCompactStyle(true)
                    .build();
        }
    }

    public void setupNavigationDrawer() {

        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("feeds").withIconColor(color);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("feeds").withIconColor(color);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("feeds").withIconColor(color);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName("feeds").withIconColor(color);
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(5).withName("feeds").withIconColor(color);
        PrimaryDrawerItem item6 = new PrimaryDrawerItem().withIdentifier(6).withName("feeds").withIconColor(color);
        sign_exit = new SecondaryDrawerItem().withIdentifier(7).withName("feeds").withIconColor(color);

        //create the drawer and remember the `Drawer` result object
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggle(true)
                .addDrawerItems(
                        item1,
                        item2,
                        item4,
                        new DividerDrawerItem(),
                        sign_exit
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {
                            case 1:
                                break;
                            case 2:
                                drawerIntent = new Intent(MainActivity.this, FeedActivity.class);
                                break;
                            case 3:
                                break;
                            case 4:
                                break;
                        }
                        result.closeDrawer();
                        return true;
                    }
                })
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {

                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        if(drawerIntent!=null) {
                            startActivity(drawerIntent);
                            overridePendingTransition(0, 0);
                            drawerIntent = null;
                            if(!dontFinish) finish();
                            dontFinish = false;
                        }
                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }
                })
                .build();
    }
}
