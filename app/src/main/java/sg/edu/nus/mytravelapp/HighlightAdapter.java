package sg.edu.nus.mytravelapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by WaiZack on 25/10/15.
 */
public class HighlightAdapter extends BaseAdapter{

    private Activity activity;
    // private ArrayList&lt;HashMap&lt;String, String&gt;&gt; data;
    private ArrayList description;
    private ArrayList price;
    private static LayoutInflater inflater = null;

    public HighlightAdapter(Activity a, ArrayList descr, ArrayList price) {
        activity = a;
        this.description = descr;
        this.price = price;

        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return description.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.highlight_row, null);

        TextView title2 = (TextView) vi.findViewById(R.id.highlight_description);
        String song = description.get(position).toString();
        title2.setText(song);


        TextView title22 = (TextView) vi.findViewById(R.id.highlight_price);
        String song2 = price.get(position).toString();
        title22.setText(song2);

        return vi;

    }
}
