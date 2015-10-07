package sg.edu.nus.mytravelapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherDialog extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather_dialog);


        String result = getIntent().getStringExtra("result");
        try {
            JSONObject weatherResult = new JSONObject(result);
            JSONArray weatherArray = weatherResult.getJSONArray("weather");
            JSONObject wtObject = weatherArray.getJSONObject(0);
            String weatherType = wtObject.getString("main");

            JSONObject tempObject = weatherResult.getJSONObject("main");
            String temp = tempObject.getString("temp");
            String tempMax = tempObject.getString("temp_max");
            String tempMin = tempObject.getString("temp_min");
            String humidity = tempObject.getString("humidity");

            JSONObject sysObject = weatherResult.getJSONObject("sys");
            String cc = sysObject.getString("country");


            String locationName = weatherResult.getString("name");

            TextView countryText = (TextView)findViewById(R.id.countryName);
            countryText.setText(locationName);

            TextView headerText = (TextView) findViewById(R.id.headerText);
            headerText.setText(weatherType);

            TextView tempText = (TextView)findViewById(R.id.temp);
            tempText.setText(temp);

            TextView tempMinText = (TextView)findViewById(R.id.tempMin);
            tempMinText.setText(tempMin);

            TextView tempMaxText = (TextView)findViewById(R.id.tempMax);
            tempMaxText.setText(tempMax);

            TextView humidText = (TextView)findViewById(R.id.humid);
            humidText.setText(humidity+"%");



            ImageView img = (ImageView)findViewById(R.id.headerImage);
            switch (weatherType) {
                case "Clear":
                    img.setImageResource(R.drawable.sunny);
                    break;
                case "Clouds":
                    img.setImageResource(R.drawable.clouds);
                    break;
                case "Thunderstorm":
                    img.setImageResource(R.drawable.thunderstorm);
                    break;
                case ("Drizzle"):
                    img.setImageResource(R.drawable.drizzle);
                    break;
                case("Rain"):
                    img.setImageResource(R.drawable.rainy);
                    break;
                case("Atmosphere"):
                    img.setImageResource(R.drawable.atmosphere);
                    break;
                default:
                    img.setImageResource(R.drawable.other);
                    break;
            }



        } catch (Exception e) {
            Log.e("jsonprob",e.toString());
        }

    }
}
