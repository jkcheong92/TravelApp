package sg.edu.nus.mytravelapp;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class DrawerActivity extends ActionBarActivity {

    private Toolbar toolbar;

    String TITLES[] = {"Home","Money Converter","Budget","Expenses","Map","Budget Advice","What's Around?"};
    int ICONS[] = {R.drawable.ic_home_black_24dp,R.drawable.ic_public_black_24dp, R.drawable.ic_local_atm_black_24dp, R.drawable.ic_local_grocery_store_black_24dp,
            R.drawable.ic_my_location_black_24dp,
            R.drawable.ic_info_black_24dp,
            R.drawable.ic_help_black_24dp};



    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;

    ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        onCreateDrawer();

    }

    protected void onCreateDrawer() {

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MyAdapter(TITLES,ICONS,this);

        mRecyclerView.setAdapter(mAdapter);




        final GestureDetector mGestureDetector = new GestureDetector(DrawerActivity.this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });


        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());


                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    Drawer.closeDrawers();

                    int position = recyclerView.getChildPosition(child);


                    if(position==0)
                    {
                        Intent myIntent = new Intent(child.getContext(), MainActivity.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(myIntent);
                    }
                    else if(position == 1) {
                        Intent myIntent = new Intent(child.getContext(), CurrencyConverter.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(myIntent);
                    }
                    else if(position == 2) {
                        Intent myIntent = new Intent(child.getContext(), Budget.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(myIntent);
                    }
                    else if(position == 3) {
                        Intent myIntent = new Intent(child.getContext(), Expenses.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(myIntent);
                    }
                    else if(position == 4) {
                        float batteryPercent = checkBattery();

                        final Intent myIntent = new Intent(child.getContext(), Map.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        // Warn user of Map Battery usage
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(DrawerActivity.this);
                        builder1.setMessage("Map drains your phone battery quickly. Your device battery level is now: "  + batteryPercent + "%. Are you sure you want to continue?");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        startActivity(myIntent);
                                    }
                                });
                        builder1.setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                    else if(position ==5){
                        Intent myIntent = new Intent(child.getContext(), Advice.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(myIntent);
                    }
                    else if(position ==6){
                        Intent myIntent = new Intent(child.getContext(), Highlight.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(myIntent);
                    }
                    return true;

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);


        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }



        };
        Drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // TODO: Put this in Map Activity
    public float checkBattery() {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        Intent batteryStatus = this.registerReceiver(null, intentFilter);

        // Is battery charging or full?
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;

        // How are we charging the battery?
        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

        // Check battery level
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPercent = level/(float)scale * 100;

        // Toast message if battery level is low and user is not charging phone
        if (batteryPercent < 50 && isCharging == false)
            Toast.makeText(DrawerActivity.this, "Your device battery level is low now: " + batteryPercent, Toast.LENGTH_SHORT).show();

        return batteryPercent;
    }

}
