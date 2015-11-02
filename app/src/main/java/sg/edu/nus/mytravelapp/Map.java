package sg.edu.nus.mytravelapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class Map extends DrawerActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        super.onCreateDrawer();

        WebView.setWebContentsDebuggingEnabled(true);
        WebView map = (WebView) findViewById(R.id.mapWebView);
        map.getSettings().setJavaScriptEnabled(true);
        map.getSettings().setBuiltInZoomControls(true);
        map.getSettings().setGeolocationEnabled(true);

        map.setWebChromeClient(new GeoWebChromeClient());
        map.addJavascriptInterface(new WeatherInterface(this), "AndroidWeather");
        //map.addJavascriptInterface(new AdviceInterface(this),"AndroidAdvice");

        map.loadUrl("file:///android_asset/map.html");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        WebView map = (WebView) findViewById(R.id.mapWebView);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_weather) {
            map.loadUrl("javascript:getWeather()");
        }
        else if(id == R.id.action_locate){
            map.loadUrl("javascript:locateMe()");

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        // checkBattery();
    }

    public class GeoWebChromeClient extends WebChromeClient {
        @Override
        public void onGeolocationPermissionsShowPrompt(String origin,
                                                       GeolocationPermissions.Callback callback) {
            // Always grant permission since the app itself requires location
            // permission and the user has therefore already granted it
            callback.invoke(origin, true, false);
        }

    }

    public class WeatherInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WeatherInterface(Context c) {
            mContext = c;
        }


        @JavascriptInterface
        public void showWeather(String result) {
            Intent myIntent = new Intent(mContext, WeatherDialog.class);
            myIntent.putExtra("result",result);
            startActivity(myIntent);
        }
    }

//    public class AdviceInterface{
//        Context mContext;
//
//        AdviceInterface(Context c)
//        {
//            mContext=c;
//        }
//
//        @JavascriptInterface
//        public void showAccomd(String result){
//            Intent myIntent = new Intent
//        }
//    }
}
