package sg.edu.nus.mytravelapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class Highlight extends DrawerActivity {

    ArrayList<String> description_array = new ArrayList<String>();
    ArrayList<String> price_array = new ArrayList<String>();
    ListView list;
    HighlightAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highlight);
        list = (ListView) findViewById(R.id.highlightList);
        super.onCreateDrawer();
    }

    public void getHighlight(View view) {
        HighlightAsync highlightAsync = new HighlightAsync();
        LocationAsync locationAsync = new LocationAsync();
        EditText inputField = (EditText) findViewById(R.id.highlightInput);
        String input = inputField.getText().toString();
        String inputAfter = "";
        String currencyDisplayed = "";
        String returned;
        String choice = "";

        TextView info = (TextView) findViewById(R.id.infoBar);

        try {
            returned = locationAsync.execute("http://www.budgetyourtrip.com/api/v3/search/locationdata/" + input).get();
            String[] temp = returned.split(";");
            inputAfter = temp[0];
            currencyDisplayed = temp[1];
            Log.e("hi", inputAfter);
            choice = "http://www.budgetyourtrip.com/api/v3/costs/highlights/" + inputAfter;
            highlightAsync.execute(choice);
            String tbd = "Displaying highlights for " ;
            input = WordUtils.capitalizeFully(input);

            tbd += input;
            tbd += " in " +currencyDisplayed;
            info.setText(tbd);
        } catch (Exception e) {
            info.setText(R.string.location_error);
            e.printStackTrace();
        }



    }

    public class HighlightAsync extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return getJSON(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            description_array.clear();
            price_array.clear();

            try {
                JSONObject result = new JSONObject(s);
                JSONArray data = result.getJSONArray("data");
                for (int i = 0, count = data.length(); i < count; i++) {
                    try {
                        JSONObject jsonObject = data.getJSONObject(i);
                        description_array.add(Html.fromHtml(jsonObject.getString("description")).toString());
                        price_array.add(jsonObject.getString("cost"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                adapter = new HighlightAdapter(Highlight.this, description_array,price_array);
                list.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

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
                if(httpURLConnection!=null)
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
                String returning = "";
                returning += country.getString("geonameid");
                returning += ";";
                returning += country.getString("currency_code");
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
