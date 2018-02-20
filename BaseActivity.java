package com.example.usamanaseer.googlemap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.usamanaseer.googlemap.service.CurrentWeatherService;
import com.example.usamanaseer.googlemap.utils.Utils;

/**
 * @author dipenp
 *
 * This activity will add Navigation Drawer for our application and all the code related to navigation drawer.
 * We are going to extend all our other activites from this BaseActivity so that every activity will have Navigation Drawer in it.
 * This activity layout contain one frame layout in which we will add our child activity layout.
 */
public class BaseActivity extends AppCompatActivity{ //Activity

    static final int PICK_CITY = 1; //weather app
    private TextView mHeaderCity;
    private Toolbar mToolbar;
    /**
     *  Frame layout: Which is going to be used as parent layout for child activity layout.
     *  This layout is protected so that child activity can access this
     *  */
    protected FrameLayout frameLayout;

    /**
     * ListView to add navigation drawer item in it.
     * We have made it protected to access it in child class. We will just use it in child class to make item selected according to activity opened.
     */

    //protected ListView mDrawerList; //comment

    /**
     * List item array for navigation drawer items.
     * */
    protected String[] listArray = { "Nearby Places", "Weather Forecast", "Weather Forecast", "Image to Text", "Currency Converter" };

    /**
     * Static variable for selected item position. Which can be used in child activity to know which item is selected from the list.
     * */
    protected static int position;

    /**
     *  This flag is used just to check that launcher activity is called first time
     *  so that we can open appropriate Activity on launch and make list item position selected accordingly.
     * */
    private static boolean isLaunch = true;

    /**
     *  Base layout node of this Activity.
     * */
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;

    /**
     * Drawer listner class for drawer open, close etc.
     */
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* for  weather app
        * */
       /* ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/
        //weather app end
        setContentView(R.layout.navigation_drawer_base_layout);

        getToolbar(); //weather app

        frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //mDrawerList = (ListView) findViewById(R.id.left_drawer); //comment
        navigationView = (NavigationView) findViewById(R.id.nav_view);

//        configureNavView(); //weather app

        // set a custom shadow that overlays the main content when the drawer opens
        //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // set up the drawer's list view with items and click listener
        //mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, listArray)); //comment
        /*comment
        mDrawerList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                openActivity(position);
            }
        });*/
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(final MenuItem menuItem){

                int id = menuItem.getItemId();
                if (id == R.id.nav_map) {
                    //mDrawerLayout.closeDrawers();
                    Intent i = new Intent(BaseActivity.this, MapsActivity.class);
                    startActivity(i);
                    finish();


                }
                else if (id == R.id.nav_home){
                        //mDrawerLayout.closeDrawers();
                        Intent i = new Intent(BaseActivity.this, NearestPlacesActivity.class);
                        startActivity(i);
                        finish();

                }
                else if (id == R.id.nav_route)
                {
                    //Toast.makeText(MainActivity.this,"messages",Toast.LENGTH_SHORT).show();
                    //mDrawerLayout.closeDrawers();

                }
                else if (id == R.id.nav_weather)
                {
                    //mDrawerLayout.closeDrawers();
                    createBackStack(new Intent(BaseActivity.this,
                            MainWeatherActivity.class));
                    /*Intent i = new Intent(BaseActivity.this, MainWeatherActivity.class); //WeatherActivity
                    startActivity(i);
                    finish();*/

                }
                else if (id == R.id.nav_image)
                {
                    //mDrawerLayout.closeDrawers();
                    createBackStack(new Intent(BaseActivity.this,
                            Main2Activity.class));
                    /*Intent i = new Intent(BaseActivity.this, Main2Activity.class);
                    startActivity(i);
                    finish();*/

                }
                else if (id == R.id.nav_text)
                {
                    //mDrawerLayout.closeDrawers();
                    createBackStack(new Intent(BaseActivity.this,
                            MainActivity.class));
                    /*Intent i = new Intent(BaseActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();*/

                }
                else if (id == R.id.nav_currency)
                {
                    //mDrawerLayout.closeDrawers();
                    createBackStack(new Intent(BaseActivity.this,
                            CurrencyConverterActivity.class));
                    /*Intent i = new Intent(BaseActivity.this, CurrencyConverterActivity.class); //Main
                    startActivity(i);
                    finish();*/

                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        //END - Material design

        // enable ActionBar app icon to behave as action to toggle nav drawer
        /*getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);*/

        // ActionBarDrawerToggle ties together the the proper interactions between the sliding drawer and the action bar app icon
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,						/* host Activity */
                mDrawerLayout, 				/* DrawerLayout object */
                R.mipmap.ic_launcher,     /* nav drawer image to replace 'Up' caret */
                R.string.open_drawer,       /* "open drawer" description for accessibility */
                R.string.close_drawer)      /* "close drawer" description for accessibility */
        {
            @Override
            public void onDrawerClosed(View drawerView) {
//                getActionBar().setTitle(listArray[position]); //exception
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
//                getActionBar().setTitle(getString(R.string.app_name)); exception
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);


        /**
         * As we are calling BaseActivity from manifest file and this base activity is intended just to add navigation drawer in our app.
         * We have to open some activity with layout on launch. So we are checking if this BaseActivity is called first time then we are opening our first activity.
         * */
        if(isLaunch){
            /**
             *Setting this flag false so that next time it will not open our first activity.
             *We have to use this flag because we are using this BaseActivity as parent activity to our other activity.
             *In this case this base activity will always be call when any child activity will launch.
             */
            isLaunch = false;
            openActivity(0);
        }
    }

    /**
     * @param position
     *
     * Launching activity when any list item is clicked.
     */
    protected void openActivity(int position) {

        /**
         * We can set title & itemChecked here but as this BaseActivity is parent for other activity,
         * So whenever any activity is going to launch this BaseActivity is also going to be called and
         * it will reset this value because of initialization in onCreate method.
         * So that we are setting this in child activity.
         */
//		mDrawerList.setItemChecked(position, true);
//		setTitle(listArray[position]);
        //mDrawerLayout.closeDrawer(mDrawerList); //comment
        BaseActivity.position = position; //Setting currently selected position in this field so that it will be available in our child activities.

        switch (position) {
            case 0:
                startActivity(new Intent(this, NearestPlacesActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, WeatherActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, Main2Activity.class));
                break;
            case 4:
                startActivity(new Intent(this, CurrencyConverterActivity.class)); //currency converter Main
                break;

            default:
                break;
        }

        //Toast.makeText(this, "Selected Item Position:"+position, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.nav_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
//commented below portion
   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
       /* boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);*/
        return super.onPrepareOptionsMenu(menu);
    }

    /* We can override onBackPressed method to toggle navigation drawer*/
   /* @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(mDrawerList)){
            mDrawerLayout.closeDrawer(mDrawerList);
        }else {
            mDrawerLayout.openDrawer(mDrawerList);
        }
    }*/

    private void configureNavView() {
       /* NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(navigationViewListener);*/

        View headerLayout = navigationView.getHeaderView(0);
        mHeaderCity = (TextView) headerLayout.findViewById(R.id.nav_header_city);
        mHeaderCity.setText(Utils.getCityAndCountry(this));
    }

    protected Toolbar getToolbar() {
        if (mToolbar == null) {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (mToolbar != null) {
                setSupportActionBar(mToolbar);
            }
        }

        return mToolbar;
    }

    @NonNull
    protected ProgressDialog getProgressDialog() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.isIndeterminate();
        dialog.setMessage(getString(R.string.load_progress));
        dialog.setCancelable(false);
        return dialog;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_CITY:
                ConnectionDetector connectionDetector = new ConnectionDetector(this);
                if (resultCode == RESULT_OK) {
//                    mHeaderCity.setText(Utils.getCityAndCountry(this));

                    if (connectionDetector.isNetworkAvailableAndConnected()) {
                        startService(new Intent(this, CurrentWeatherService.class));
                    }
                }
                break;
        }
    }

    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDraw() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        if (isNavDrawerOpen()) {
            closeNavDraw();
        } else {
            super.onBackPressed();
        }
    }

    private void createBackStack(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            TaskStackBuilder builder = TaskStackBuilder.create(this);
            builder.addNextIntentWithParentStack(intent);
            builder.startActivities();
        } else {
            startActivity(intent);
            finish();
        }
    }

}