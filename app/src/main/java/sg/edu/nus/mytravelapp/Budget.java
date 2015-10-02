package sg.edu.nus.mytravelapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Budget extends AppCompatActivity {
    MyDB myDb;
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment expenseFrag = new BlankFragment();
        fragmentTransaction.add(R.id.frameExpenses, expenseFrag);
        fragmentTransaction.commit();

        myDb = new MyDB(this);
        sendBroadcastMessage();
        // getFormattedRecords();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_budget, menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
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

    /*// Get all records from DB in string format
    public void getFormattedRecords() {
        TextView budgetRecords = (TextView) findViewById(R.id.textView_database_content);

        myDb.open();
        Cursor c = myDb.getAllData();
        String output = "";
        if(c.moveToFirst()) {
            do {
                // TODO: Get from each column -> Update code when there is more columns in DB
                output += c.getString(0) + " ";
                output += c.getString(1) + " ";
                output += "\n";
            } while (c.moveToNext());
        }

        myDb.close();
        Toast.makeText(Budget.this, output, Toast.LENGTH_LONG).show();
        budgetRecords.setText(output);
    }*/

    // Add budget to database
    public void onClick_setBudget(View view) {
        myDb.open();

        // Fetch budget from user
        BlankFragment expenseFrag = (BlankFragment) getFragmentManager().findFragmentById(R.id.frameExpenses);
        Double food = expenseFrag.getFoodCost();
        Double travel = expenseFrag.getTravelCost();
        Double accomodation = expenseFrag.getAccomodationCost();
        Double play = expenseFrag.getPlayCost();
        Double shopping = expenseFrag.getShoppingCost();

        // Store budget cost into DB
        boolean isInserted = myDb.insertData(food, travel, accomodation, play, shopping);
        if (isInserted) {
            Toast.makeText(Budget.this, "Budget Set!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(Budget.this, "Budget NOT Set!", Toast.LENGTH_LONG).show();
        }

        myDb.close();
    }

    // TODO: Send Broadcast Message when expenses is close to budget
    public void sendBroadcastMessage() {
        Intent i = new Intent("Travellers_Broadcast");
        i.putExtra("msg", "You are close to hitting your budget!");
        sendBroadcast(i);
        Toast.makeText(Budget.this, "Send broadcast", Toast.LENGTH_SHORT).show();
    }
}
