package sg.edu.nus.mytravelapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView bgImg = (ImageView) findViewById(R.id.bgView);
        bgImg.setColorFilter(Color.rgb(123, 123, 123), android.graphics.PorterDuff.Mode.MULTIPLY);

        final TextView subtitle = (TextView) findViewById(R.id.subtitle);
        final Animation animationFadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        animationFadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                subtitle.setAlpha(0);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        subtitle.startAnimation(animationFadeOut);

        TextView title = (TextView) findViewById(R.id.title);
        Animation animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        title.startAnimation(animationFadeIn);

        /*WebView wv = (WebView)findViewById(R.id.webView1);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebChromeClient(new WebChromeClient());
        wv.loadUrl("file:///android_asset/www/main.html");*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        /* Hide status bar and action bar */
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick_resetDB(View view) {
        MyDB myDb = new MyDB(this);
        myDb.open();
        myDb.deleteAllRecords();
        myDb.close();
        Intent i = new Intent(this, Budget.class);
        startActivity(i);
    }

    public void onClick_convert(View view) {
        Intent i = new Intent(this, CurrencyConverter.class);
        startActivity(i);
    }

    public void onClick_budget(View view) {
        Intent i = new Intent(this, Budget.class);
        startActivity(i);
    }

    public void onClick_addExpenses(View view) {
        Intent i = new Intent(this, Expenses.class);
        startActivity(i);
    }

    // TODO: Put this in Map Activity
    public void checkBattery() {
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
            Toast.makeText(MainActivity.this, "Your device battery level is low now: " + batteryPercent + "%! Are you sure you want to continue?", Toast.LENGTH_SHORT).show();

        if (usbCharge)
            Toast.makeText(MainActivity.this, "Charging phone by usb", Toast.LENGTH_SHORT).show();
        if (acCharge)
            Toast.makeText(MainActivity.this, "Charging phone by acCharge", Toast.LENGTH_SHORT).show();
    }

}
