package sg.edu.nus.mytravelapp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;

public class CurrencyConverter extends DrawerActivity implements ItemFragment.OnFragmentInteractionListener{

    private FragmentManager fragmentManager = getFragmentManager();
    private FragmentTransaction fragmentTransaction;

    private String[] countries = {"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe"};
    private String[] currencies = {"AFN", "ARS", "AWG", "AUD", "AZN", "BSD", "BBD", "BDT", "BYR", "BZD", "BMD", "BOB", "BAM", "BWP", "BGN", "BRL", "BND", "KHR", "CAD", "KYD", "CLP", "CNY", "COP", "CRC", "HRK", "CUP", "CZK", "DKK", "DOP", "XCD", "EGP", "SVC", "EEK", "EUR", "FKP", "FJD", "GHC", "GIP", "GTQ", "GGP", "GYD", "HNL", "HKD", "HUF", "ISK", "INR", "IDR", "IRR", "IMP", "ILS", "JMD", "JPY", "JEP", "KZT", "KPW", "KRW", "KGS", "LAK", "LVL", "LBP", "LRD", "LTL", "MKD", "MYR", "MUR", "MXN", "MNT", "MZN", "NAD", "NPR", "ANG", "NZD", "NIO", "NGN", "NOK", "OMR", "PKR", "PAB", "PYG", "PEN", "PHP", "PLN", "QAR", "RON", "RUB", "SHP", "SAR", "RSD", "SCR", "SGD", "SBD", "SOS", "ZAR", "LKR", "SEK", "CHF", "SRD", "SYP", "TWD", "THB", "TTD", "TRY", "TRL", "TVD", "UAH", "GBP", "UGX", "USD", "UYU", "UZS", "VEF", "VND", "YER", "ZWD"};

    private Boolean inFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_converter);
        super.onCreateDrawer();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // Dynamically attach ItemFragment
        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment popularCurr = new ItemFragment();
        fragmentTransaction.add(R.id.listFragment, popularCurr);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_currency_converter, menu);

        AutoCompleteTextView baseCurr = (AutoCompleteTextView) findViewById(R.id.actv_baseCurrency);
        AutoCompleteTextView convertCurr = (AutoCompleteTextView) findViewById(R.id.actv_convertCurrency);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, currencies);
        baseCurr.setAdapter(adapter);
        convertCurr.setAdapter(adapter);

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

    public void onClick_addDestination(View view) {
        final ItemFragment fragmentList = (ItemFragment) fragmentManager.findFragmentById(R.id.listFragment);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Add Favorites");

        // Convert scale first to set padding
        int paddingPixel = 20;
        float density = this.getResources().getDisplayMetrics().density;
        int paddingDp = (int)(paddingPixel * density);

        // Adding layout of dialogue box programatically
        final AutoCompleteTextView countries_actv = new AutoCompleteTextView(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,countries);
        countries_actv.setAdapter(adapter);
        countries_actv.setHint("Country");
        countries_actv.setPadding(paddingDp, paddingDp, 0, paddingDp);

        final AutoCompleteTextView currencies_actv = new AutoCompleteTextView(this);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, currencies);
        currencies_actv.setAdapter(adapter);
        currencies_actv.setHint("Currency");
        currencies_actv.setPadding(paddingDp, paddingDp, 0, paddingDp);


        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(countries_actv);
        layout.addView(currencies_actv);
        alert.setView(layout);

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String country = countries_actv.getText().toString().toUpperCase();
                String currency = currencies_actv.getText().toString().toUpperCase();
                fragmentList.addItem(currency, country);
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }

    @Override
    public void onFragmentInteraction(String id) {
        inFragment = true;
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
            AutoCompleteTextView convertCurr = (AutoCompleteTextView) findViewById(R.id.actv_convertCurrency);
            EditText amountCurr = (EditText) findViewById(R.id.editText_amountCurrency);

            String convertCurrency = convertCurr.getEditableText().toString().toUpperCase();
            String amountCurrency = amountCurr.getEditableText().toString();

            if(inFragment == true) {
                amountCurrency = "1";
                convertCurrency = "SGD";
            }
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
        AutoCompleteTextView baseCurr = (AutoCompleteTextView) findViewById(R.id.actv_baseCurrency);
        String baseCurrency = baseCurr.getEditableText().toString().toUpperCase();

        inFragment = false;

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
