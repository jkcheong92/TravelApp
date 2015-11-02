package sg.edu.nus.mytravelapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;

public class Advice extends DrawerActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        super.onCreateDrawer();

    }

    public void performSearch(View view) {
        AdviceAsync adviceAsync = new AdviceAsync();
        LocationAsync locationAsync = new LocationAsync();

        String choice = "";
        EditText inputField = (EditText) findViewById(R.id.adviceInput);
        String input = inputField.getText().toString();
        String inputAfter = "";
        String currencyDisplayed = "";
        String returned;


        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

            try {
                returned = locationAsync.execute("http://www.budgetyourtrip.com/api/v3/search/locationdata/" + input).get();
                String[] temp = returned.split(";");
                inputAfter = temp[0];
                currencyDisplayed = temp[1];
                Log.e("hi", inputAfter);
                choice = "http://www.budgetyourtrip.com/api/v3/costs/location/" + inputAfter;
            } catch (Exception e) {
                e.printStackTrace();
            }

        try {
            String result = adviceAsync.execute(choice).get();
            JSONObject foodResult;
            JSONObject travelResult;
            JSONObject stayResult;
            JSONObject playResult;
            JSONObject shopResult;
            JSONObject avgResult;
            String set;

            try {
                JSONObject jsonResult = new JSONObject(result);
                JSONArray data = jsonResult.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject current = data.getJSONObject(i);
                    int type = current.getInt("category_id");
                    switch (type) {
                        case 1:
                            stayResult = current;
                            TextView stay = (TextView) findViewById(R.id.advice_stay);
                            set = df.format(Double.parseDouble(stayResult.getString("value_budget"))) + "  "+currencyDisplayed;
                            stay.setText(set);
                            break;
                        case 3:
                            travelResult = current;
                            TextView travel = (TextView) findViewById(R.id.advice_travel);
                            set = df.format(Double.parseDouble(travelResult.getString("value_budget"))) + "  "+currencyDisplayed;
                            travel.setText(set);
                            break;
                        case 4:
                            foodResult = current;
                            TextView food = (TextView) findViewById(R.id.advice_food);
                            set = df.format(Double.parseDouble(foodResult.getString("value_budget"))) + "  "+currencyDisplayed;
                            food.setText(set);
                            break;
                        case 6:
                            playResult = current;
                            TextView play = (TextView) findViewById(R.id.advice_play);
                            set = df.format(Double.parseDouble(playResult.getString("value_budget"))) + "  "+currencyDisplayed;
                            play.setText(set);
                            break;
                        case 7:
                            shopResult = current;
                            TextView shop = (TextView) findViewById(R.id.advice_shop);
                            set = df.format(Double.parseDouble(shopResult.getString("value_budget"))) + "  "+currencyDisplayed;
                            shop.setText(set);
                            break;
                        case 0:
                            avgResult = current;
                            TextView avg = (TextView) findViewById(R.id.advice_avg);
                            set = df.format(Double.parseDouble(avgResult.getString("value_budget"))) + "  "+currencyDisplayed;
                            avg.setText(set);
                            break;
                        default:
                            break;

                    }
                }
                TextView info = (TextView) findViewById(R.id.infoBar);
                String tbd = "Displaying estimated daily budgets for " ;
                input = WordUtils.capitalizeFully(input);

                tbd += input;
                info.setText(tbd);


            } catch (Exception e) {
                TextView stay = (TextView) findViewById(R.id.advice_stay);
                stay.setText("");
                TextView travel = (TextView) findViewById(R.id.advice_travel);
                travel.setText("");
                TextView food = (TextView) findViewById(R.id.advice_food);
                food.setText("");
                TextView play = (TextView) findViewById(R.id.advice_play);
                play.setText("");
                TextView shop = (TextView) findViewById(R.id.advice_shop);
                shop.setText("");
                TextView avg = (TextView) findViewById(R.id.advice_avg);
                avg.setText("");
                TextView info = (TextView) findViewById(R.id.infoBar);
                info.setText(R.string.location_error);

                        e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public class AdviceAsync extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return getJSON(params[0]);
        }


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
                httpURLConnection.setRequestProperty("X-API-KEY", "syz1992@gmail.com");
                httpURLConnection.connect();

                responseCode = httpURLConnection.getResponseCode();
                //Log.e("responseCode",Integer.toString(responseCode));
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
                if(httpURLConnection != null)
                httpURLConnection.disconnect();
            }

            return stringBuilder.toString();
        }
    }


    public class LocationAsync extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            try {
                JSONObject jsonResult = new JSONObject(getJSON(params[0]));
                JSONArray data = jsonResult.getJSONArray("data");
                JSONObject country = data.getJSONObject(0);
                String returning="";
                returning+=country.getString("geonameid");
                returning+=";";
                returning+=country.getString("currency_code");
                return returning;


            } catch (JSONException e) {

                e.printStackTrace();
            }

            return "No Result!";
        }


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
                httpURLConnection.setRequestProperty("X-API-KEY", "syz1992@gmail.com");
                httpURLConnection.connect();

                responseCode = httpURLConnection.getResponseCode();
                //Log.e("responseCode",Integer.toString(responseCode));
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

    public class CountryAsync extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            try {
                JSONObject jsonResult = new JSONObject(getJSON(params[0]));
                JSONArray data = jsonResult.getJSONArray("data");
                JSONObject country = data.getJSONObject(0);
                String returning="";
                returning+=country.getString("country_code");
                returning+=";";
                returning+=country.getString("currency_code");
                return returning;


            } catch (JSONException e) {

                e.printStackTrace();
            }

            return "No Result!";
        }


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
                httpURLConnection.setRequestProperty("X-API-KEY", "syz1992@gmail.com");
                httpURLConnection.connect();

                responseCode = httpURLConnection.getResponseCode();
                //Log.e("responseCode",Integer.toString(responseCode));
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


}
