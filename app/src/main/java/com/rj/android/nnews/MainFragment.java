package com.rj.android.nnews;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.rj.android.nnews.data.Contract;
import com.rj.android.nnews.sync.SyncAdapter;
import com.rj.android.nnews.view.SlidingTabLayout;
import com.rj.android.nnews.view.SmartFragmentStatePagerAdapter;

public class MainFragment extends Fragment  {

    /**
     * A custom {@link ViewPager} title strip which looks much like Tabs present in Android v4.0 and
     * above, but is designed to give continuous feedback to the user when scrolling.
     */
    private SlidingTabLayout mSlidingTabLayout;

    /**
     * A {@link ViewPager} which will be used in conjunction with the {@link SlidingTabLayout} above.
     */
    private ViewPager mViewPager;


    private static final String LOG_TAG = MainFragment.class.getSimpleName();

    public static final int COL_ARTICLE_ID = 0;
    public static final int COL_ARTICLE_KEY_ID = 1;
    public static final int COL_ARTICLE_TITLE = 2;
    public static final int COL_ARTICLE_URL = 3;
    public static final int COL_ARTICLE_ABSTRACT = 4;
    public static final int COL_ARTICLE_SOURCE = 5;
    public static final int COL_ARTICLE_PHOTO_HEADING = 6;
    public static final int COL_ARTICLE_PHOTO_URL_HIGH = 7;
    public static final int COL_ARTICLE_PUBLISH_DATE = 8;
    public static final int COL_ARTICLE_PHOTO_URL = 9;
    private static final int FORECAST_LOADER = 0;

    boolean mTwoPane;

    ArticleListAdapter madapter;

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setTwoPane(boolean twoPane)
    {
        mTwoPane = twoPane;
        if(madapter!=null)
        {
            madapter.setUseMainLayout(mTwoPane);
        }
    }

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(com.rj.android.nnews.R.menu.main_fragment_menu, menu);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {

            updateArticle();
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateArticle()
    {
        SyncAdapter.syncImmediately(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // BEGIN_INCLUDE (setup_viewpager)
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter(getChildFragmentManager()));

        // END_INCLUDE (setup_viewpager)

        // BEGIN_INCLUDE (setup_slidingtablayout)
        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.holo_white);
            }
        });


        mSlidingTabLayout.setViewPager(mViewPager);
        // END_INCLUDE (setup_slidingtablayout)
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(com.rj.android.nnews.R.layout.fragment_main, container, false);
        return rootView;
    }

     void setCurrentTab(int pos) {
        mViewPager.setCurrentItem(pos);
    }



    class SamplePagerAdapter extends SmartFragmentStatePagerAdapter {



        private  int NUM_ITEMS = 4;
        String str[]={"HOME","Top Stories","Newswire","Movie Reviews"};
        public SamplePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return NestedHome.newInstance();
                case 1: // Fragment # 0 - This will show FirstFragment
                    return NestedFragment.newInstance();
                case 2: // Fragment # 0 - This will show FirstFragment different title
                    return NestedFragment2.newInstance();
                case 3: // Fragment # 1 - This will show SecondFragment
                    return NestedFragment3.newInstance();
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return str[position];
        }

    }
}
