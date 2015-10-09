package sg.edu.nus.mytravelapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Expenses extends DrawerActivity {
    MyDB myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        super.onCreateDrawer();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment expenseFrag = new BlankFragment();
        fragmentTransaction.add(R.id.frameExpenses, expenseFrag);
        fragmentTransaction.commit();

        myDb = new MyDB(this);

        if (!getBudget().isEmpty()) {
            trackExpenses();
        } else {
            Toast.makeText(Expenses.this, "You need to set your budget first!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, Budget.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_expenses, menu);
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

    // Add expenses to database
    public void onClick_addExpenses(View view) {
        myDb.open();

        // Fetch budget from user
        BlankFragment expenseFrag = (BlankFragment) getFragmentManager().findFragmentById(R.id.frameExpenses);
        Double food = expenseFrag.getFoodCost();
        Double travel = expenseFrag.getTravelCost();
        Double accomodation = expenseFrag.getAccomodationCost();
        Double play = expenseFrag.getPlayCost();
        Double shopping = expenseFrag.getShoppingCost();

        // Store expenses into DB
        boolean isInserted = myDb.insertData(food, travel, accomodation, play, shopping, 0);
        if (isInserted) {
            Toast.makeText(Expenses.this, "Added expenses!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(Expenses.this, "Please try again", Toast.LENGTH_LONG).show();
        }

        trackExpenses();
        myDb.close();
    }

    public void trackExpenses() {
        String[] lastBudget = getBudget().get(getBudget().size() - 1);
        Double foodBudget = Double.parseDouble(lastBudget[1]);
        Double travelBudget = Double.parseDouble(lastBudget[2]);
        Double accomodationBudget = Double.parseDouble(lastBudget[3]);
        Double playBudget = Double.parseDouble(lastBudget[4]);
        Double shoppingBudget = Double.parseDouble(lastBudget[5]);
        Double totalBudget = foodBudget + travelBudget + accomodationBudget + playBudget + shoppingBudget;

        ArrayList<String[]> allExpenses = getExpenses();
        Double foodExpenses, travelExpenses, accomodationExpenses, playExpenses, shoppingExpenses;
        foodExpenses = travelExpenses = accomodationExpenses = playExpenses = shoppingExpenses = 0.0;
        for (int i = 0; i < allExpenses.size(); i++) {
            String[] totalExpenses = allExpenses.get(i);
            foodExpenses += Double.parseDouble(totalExpenses[1]);
            travelExpenses += Double.parseDouble(totalExpenses[2]);
            accomodationExpenses += Double.parseDouble(totalExpenses[3]);
            playExpenses += Double.parseDouble(totalExpenses[4]);
            shoppingExpenses += Double.parseDouble(totalExpenses[5]);
        }
        Double totalExpenses = foodExpenses + travelExpenses + accomodationExpenses + playExpenses + shoppingExpenses;

        // TODO: Show progress bar beside field
        TextView foodTxt = (TextView) findViewById(R.id.foodPercentage);
        Double foodPercentage = foodExpenses / foodBudget * 100;
        if (foodPercentage > 100) {
            foodTxt.setTextColor(Color.RED);
        }
        foodTxt.setText(String.format("%.2f", foodPercentage) + " %");
        TextView travelTxt = (TextView) findViewById(R.id.travelPercentage);
        Double travelPercentage = travelExpenses / travelBudget * 100;
        if (travelPercentage > 100) {
            travelTxt.setTextColor(Color.RED);
        }
        travelTxt.setText(String.format("%.2f", travelPercentage) + " %");
        TextView accomodationTxt = (TextView) findViewById(R.id.accomodationPercentage);
        Double accomodationPercentage = accomodationExpenses / accomodationBudget * 100;
        if (accomodationPercentage > 100) {
            accomodationTxt.setTextColor(Color.RED);
        }
        accomodationTxt.setText(String.format("%.2f", accomodationPercentage) + " %");
        TextView playTxt = (TextView) findViewById(R.id.playPercentage);
        Double playPercentage = playExpenses / playBudget * 100;
        if (playPercentage > 100) {
            playTxt.setTextColor(Color.RED);
        }
        playTxt.setText(String.format("%.2f", playPercentage) + " %");
        TextView shoppingTxt = (TextView) findViewById(R.id.shoppingPercentage);
        Double shoppingPercentage = shoppingExpenses / shoppingBudget * 100;
        if (shoppingPercentage > 100) {
            shoppingTxt.setTextColor(Color.RED);
        }
        shoppingTxt.setText(String.format("%.2f", shoppingPercentage) + " %");

        Double totalPercentage = totalExpenses / totalBudget * 100;
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setProgress(totalPercentage.intValue());
        pb.setScaleY(10f);      // Fatten the progress bar
        TextView pbTxt = (TextView) findViewById(R.id.totalPercentage);
        pbTxt.setText(String.format("%.2f", totalPercentage) + " % of total budget spent");
        if (totalPercentage > 100) {
            pb.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        }

        // TODO: Send broadcast message when exceeded budget
        if (foodExpenses > foodBudget)
            Toast.makeText(Expenses.this, "You have exceeded your food budget!", Toast.LENGTH_LONG).show();
        if (travelExpenses > travelBudget)
            Toast.makeText(Expenses.this, "You have exceeded your travel budget!", Toast.LENGTH_LONG).show();
        if (accomodationExpenses > accomodationBudget)
            Toast.makeText(Expenses.this, "You have exceeded your accomodation budget!", Toast.LENGTH_LONG).show();
        if (playExpenses > playBudget)
            Toast.makeText(Expenses.this, "You have exceeded your play budget!", Toast.LENGTH_LONG).show();
        if (shoppingExpenses > shoppingBudget)
            Toast.makeText(Expenses.this, "You have exceeded your shopping budget!", Toast.LENGTH_LONG).show();

        if (totalExpenses > totalBudget) {
            sendBroadcastMessage();
        }

    }

    // TODO: Send Broadcast Message when expenses is close to budget
    public void sendBroadcastMessage() {
        Intent i = new Intent("Travellers_Broadcast");
        i.putExtra("msg", "You have exceeded your budget!");
        sendBroadcast(i);
    }

    // Get budget from DB
    public ArrayList<String[]> getBudget() {
        myDb.open();

        Cursor c = myDb.getBudget();

        ArrayList<String[]> records = new ArrayList<String[]>();

        if (c.moveToFirst()) {
            do {
                String[] record = {c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6)};
                records.add(record);
            } while (c.moveToNext());
        }
        myDb.close();
        return records;
    }

    // Get expenses from DB
    public ArrayList<String[]> getExpenses() {
        myDb.open();

        Cursor c = myDb.getExpenses();

        ArrayList<String[]> records = new ArrayList<String[]>();

        if (c.moveToFirst()) {
            do {
                String[] record = {c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6)};
                records.add(record);
            } while (c.moveToNext());
        }
        myDb.close();
        return records;
    }
}
