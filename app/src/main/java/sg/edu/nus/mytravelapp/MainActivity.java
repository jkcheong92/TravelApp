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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*ImageView bgImg = (ImageView) findViewById(R.id.bgView);
        bgImg.setColorFilter(Color.rgb(123, 123, 123), android.graphics.PorterDuff.Mode.MULTIPLY);*/

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
        animationFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                Button newTrip = (Button) findViewById(R.id.newTrip);
                newTrip.setVisibility(View.VISIBLE);
                Button currentTrip = (Button) findViewById(R.id.currentTrip);
                currentTrip.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        title.startAnimation(animationFadeIn);
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

    public void onClick_newTrip(View view) {
        Button expenses = (Button) findViewById(R.id.expenses);
        expenses.setVisibility(View.GONE);
        Button map = (Button) findViewById(R.id.map);
        map.setVisibility(View.GONE);

        Button budget = (Button) findViewById(R.id.budget);
        budget.setVisibility(View.VISIBLE);
        Button currency = (Button) findViewById(R.id.currency);
        currency.setVisibility(View.VISIBLE);
        /*Button newTrip = (Button) findViewById(R.id.newTrip);
        newTrip.setVisibility(View.GONE);
        Button currentTrip = (Button) findViewById(R.id.currentTrip);
        currentTrip.setVisibility(View.GONE);*/
        //onClick_convert(view);
    }

    public void onClick_currentTrip(View view) {
        Button budget = (Button) findViewById(R.id.budget);
        budget.setVisibility(View.GONE);
        Button currency = (Button) findViewById(R.id.currency);
        currency.setVisibility(View.GONE);

        Button expenses = (Button) findViewById(R.id.expenses);
        expenses.setVisibility(View.VISIBLE);
        Button map = (Button) findViewById(R.id.map);
        map.setVisibility(View.VISIBLE);
        /*Button newTrip = (Button) findViewById(R.id.newTrip);
        newTrip.setVisibility(View.GONE);
        Button currentTrip = (Button) findViewById(R.id.currentTrip);
        currentTrip.setVisibility(View.GONE);*/
        //onClick_addExpenses(view);
    }

    public void resetDB() {
        MyDB myDb = new MyDB(this);
        myDb.open();
        myDb.deleteAllRecords();
        myDb.close();
    }

    public void onClick_convert(View view) {
        Intent i = new Intent(this, CurrencyConverter.class);
        startActivity(i);
    }

    public void onClick_budget(View view) {
        resetDB();
        Intent i = new Intent(this, Budget.class);
        startActivity(i);
    }

    public void onClick_addExpenses(View view) {
        Intent i = new Intent(this, Expenses.class);
        startActivity(i);
    }

    public void onClick_map(View view) {
        Intent i = new Intent(this, Map.class);
        startActivity(i);
    }

}
