package sg.edu.nus.mytravelapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Asus on 9/27/2015.
 */
public class PersistentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("msg");
        Toast.makeText(context, "WARNING: " + message, Toast.LENGTH_SHORT).show();
    }
}
