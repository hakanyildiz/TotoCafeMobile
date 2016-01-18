package com.sohos.totocafemobile.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.sohos.totocafemobile.R;
import com.sohos.totocafemobile.anim.SortListener;
import com.sohos.totocafemobile.fragments.FragmentDrawer;
import com.sohos.totocafemobile.fragments.FragmentHome;
import com.sohos.totocafemobile.fragments.FragmentMenu;
import com.sohos.totocafemobile.fragments.FragmentProfile;
import com.sohos.totocafemobile.ordering.ShoppingCartActivity;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/*
        Bundle bundle = getIntent().getExtras();
        String email = "";
        if(bundle != null){
            email = bundle.getString("email");
        }
        mDrawerFragment.setEmailOfUser(email);
*/
/*
* ******BUNDLE KULLANIMI **********
* Android programlamada activiyler arası data geçişi için bundle kullanılır.
 Bundle yardımıyla bir activitye
de mevcut olan data başka bir activitye gönderilebilir.

//////////////MAIN ACTIVITY
Intent i = new Intent(MainActivity.this, SecondActivity.class);

Bundle toMain = new Bundle();
toMain.putString("info", "Please subscribe me");
i.putExtras(toMain);

startActivity(i);

////////SECOND ACTIVITY
Bundle fromMain = getIntent().getExtras();
String myValue = fromMain.getString("info");
*
* */
public class MainActivity extends AppCompatActivity implements MaterialTabListener, View.OnClickListener {

    //int representing our 0th tab corresponding to the Fragment where search results are dispalyed
    public static final int TAB_HOME = 0;
    //int corresponding to our 1st tab corresponding to the Fragment where box office hits are dispalyed
    public static final int TAB_MENU = 1;
    //int corresponding to our 2nd tab corresponding to the Fragment where upcoming movies are displayed
    public static final int TAB_USER = 2;
    //int corresponding to the number of tabs in our Activity
    public static final int TAB_COUNT = 3;
    //int corresponding to the id of our JobSchedulerService
    private static final int JOB_ID = 100;
    //tag associated with the FAB menu button that sorts by name
    private static final String TAG_SORT_NAME = "sortName";
    //tag associated with the FAB menu button that sorts by date
    private static final String TAG_SORT_DATE = "sortDate";
    //tag associated with the FAB menu button that sorts by ratings
    private static final String TAG_SORT_RATINGS = "sortRatings";
    //Run the JobSchedulerService every 2 minutes
    private static final long POLL_FREQUENCY = 28800000;
    private Toolbar mToolbar;
    //a layout grouping the toolbar and the tabs together
    private ViewGroup mContainerToolbar;
    private MaterialTabHost mTabHost;
    private ViewPager mPager;
    private ViewPagerAdapter mAdapter;
    private FragmentDrawer mDrawerFragment;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupTabs();
        setupDrawer();

        /*
        Bundle bundle = getIntent().getExtras();
        String email = "";
        if(bundle != null){
            email = bundle.getString("email");
        }
        mDrawerFragment.setEmailOfUser(email);
*/
    }


    private void setupDrawer() {
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        mContainerToolbar = (ViewGroup) findViewById(R.id.container_app_bar);
        //set the Toolbar as ActionBar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //setup the NavigationDrawer
        mDrawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        mDrawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
    }


    public void onDrawerItemClicked(int index) {
        mPager.setCurrentItem(index);
    }

    public View getContainerToolbar() {
        return mContainerToolbar;
    }

    private void setupTabs() {
        mTabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        mPager = (ViewPager) findViewById(R.id.viewPager);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        //when the page changes in the ViewPager, update the Tabs accordingly
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mTabHost.setSelectedNavigationItem(position);

            }
        });
        //Add all the Tabs to the TabHost
        for (int i = 0; i < mAdapter.getCount(); i++) {
            mTabHost.addTab(
                    mTabHost.newTab()
                            .setIcon(mAdapter.getIcon(i))
                            .setTabListener(this));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(this, "Hey you just hit + " + item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.navigate) {
            startActivity(new Intent(this, ShoppingCartActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        mPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }

    @Override
    public void onClick(View v) {
        //call instantiate item since getItem may return null depending on whether the PagerAdapter is of type FragmentPagerAdapter or FragmentStatePagerAdapter
        Fragment fragment = (Fragment) mAdapter.instantiateItem(mPager, mPager.getCurrentItem());
        if (fragment instanceof SortListener) {

            if (v.getTag().equals(TAG_SORT_NAME)) {
                //call the sort by name method on any Fragment that implements sortlistener
                //     ((SortListener) fragment).onSortByName();
            }
            if (v.getTag().equals(TAG_SORT_DATE)) {
                //call the sort by date method on any Fragment that implements sortlistener
                //      ((SortListener) fragment).onSortByDate();
            }
            if (v.getTag().equals(TAG_SORT_RATINGS)) {
                //call the sort by ratings method on any Fragment that implements sortlistener
                //        ((SortListener) fragment).onSortByRating();
            }
        }

    }


    /* CREATING PAGER ADAPTER FOR TABS */
    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        int icons[] = {R.drawable.ic_action_home_white,
                R.drawable.ic_action_menu_white,
                R.drawable.ic_action_user_white};

        FragmentManager fragmentManager;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentManager = fm;
        }

        public Fragment getItem(int num) {
            Fragment fragment = null;
//            L.m("getItem called for " + num);
            switch (num) {
                case TAB_HOME:
                    fragment = FragmentHome.newInstance("", "");
                    break;
                case TAB_MENU:
                    fragment = FragmentMenu.newInstance("", "");
                    break;
                case TAB_USER:
                    fragment = FragmentProfile.newInstance("", "");
                    break;
            }
            return fragment;

        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.tabs)[position];
        }

        private Drawable getIcon(int position) {
            return getResources().getDrawable(icons[position]);
        }
    }

}
