package sg.edu.nus.mytravelapp;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by Asus on 10/12/2015.
 */
public class PushNotification extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Parse Push Notification
        Parse.initialize(this, "oVG3EoFM44fWuucNWdeFciSycQiftucz072qUNtg", "87F67qdcaqQ1A9lIz9fYBrSHKTI8L1U1GI2V53pe");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}