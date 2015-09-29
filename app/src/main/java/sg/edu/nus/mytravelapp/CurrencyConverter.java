package sg.edu.nus.mytravelapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class CurrencyConverter extends AppCompatActivity implements ItemFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_converter);

        // Dynamically attach ItemFragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment popularCurr = new ItemFragment();
        fragmentTransaction.add(R.id.listFragment, popularCurr);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_currency_converter, menu);
        return true;
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

    @Override
    public void onFragmentInteraction(String id) {
        Toast.makeText(getApplicationContext(), "Converting 1 " + id + " to SGD ...", Toast.LENGTH_SHORT).show();

        // Convert 1 foreign currency to SGD currency
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute("http://api.fixer.io/latest?base=" + id);
    }

    public class MyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return getJSON(params[0]);
        }

        public void onPostExecute (String result) {
            EditText convertCurr = (EditText) findViewById(R.id.editText_convertCurrency);
            EditText amountCurr = (EditText) findViewById(R.id.editText_amountCurrency);

            String convertCurrency = convertCurr.getEditableText().toString().toUpperCase();
            String amountCurrency = amountCurr.getEditableText().toString();
            if (amountCurrency.equals(""))
                amountCurrency = "1";
            if (convertCurrency.equals(""))
                convertCurrency = "SGD";

            try {
                // Convert currency using JSON
                JSONObject myJSONObject = new JSONObject(result);
                JSONObject ratesObject = myJSONObject.getJSONObject("rates");

                String rate = ratesObject.getString(convertCurrency);
                double convertedAmount = Double.parseDouble(rate) * Double.parseDouble(amountCurrency);
                String convertedAmount_str = String.valueOf(convertedAmount);

                // Set TextView on result
                TextView convertAmount = (TextView) findViewById(R.id.textView_convertAmount);
                convertAmount.setText(convertedAmount_str);

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onClick_convertAmount(View view) {
        // Convert currency to uppercase
        EditText baseCurr = (EditText) findViewById(R.id.editText_baseCurrency);
        String baseCurrency = baseCurr.getEditableText().toString().toUpperCase();

        // Start async task
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute("http://api.fixer.io/latest?base=" + baseCurrency);
    }

    // the following code convert string to url
    private URL ConvertToUrl(String urlStr) {
        try {
            URL url = new URL(urlStr);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(),
                    url.getHost(), url.getPort(), url.getPath(),
                    url.getQuery(), url.getRef());
            url = uri.toURL();
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getJSON(String inputStr) {
        URL url = ConvertToUrl(inputStr);
        HttpURLConnection httpURLConnection = null;
        int responseCode = -1;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            responseCode = httpURLConnection.getResponseCode();
            if (responseCode == httpURLConnection.HTTP_OK) {
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();

        } finally {
            httpURLConnection.disconnect();
        }

        return stringBuilder.toString();
    }
}
