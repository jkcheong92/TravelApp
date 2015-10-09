package sg.edu.nus.mytravelapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by WaiZack on 16/9/15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    private String mNavTitles[]; // String Array to store the passed titles Value from DrawerActivity.java
    private int mIcons[];       // Int Array to store the passed icons resource value from DrawerActivity.java
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;



        public ViewHolder(View itemView,Context c) {
            super(itemView);
            //ctx = c;
            //itemView.setClickable(true);
            //itemView.setOnClickListener(this);

                textView = (TextView) itemView.findViewById(R.id.rowText); // Creating TextView object with the id of textView from item_row.xml
                imageView = (ImageView) itemView.findViewById(R.id.rowIcon);// Creating ImageView object with the id of ImageView from item_row.xmlpe item row

        }

       /* @Override
        public void onClick(View v) {

           Intent myIntent= new Intent(v.getContext(),Budget.class);
            v.getContext().startActivity(myIntent);

        }*/

    }

    MyAdapter(String Titles [],int Icons[],Context passedContext){
        mNavTitles = Titles;
        mIcons = Icons;
        this.context = passedContext;
    }
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false); //Inflating the layout

            ViewHolder vhItem = new ViewHolder(v,context); //Creating ViewHolder and passing the object of type view

            return vhItem; // Returning the created object

    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
            holder.textView.setText(mNavTitles[position]); // Setting the Text with the array of our Titles
            holder.imageView.setImageResource(mIcons[position]);// Setting the image with array of our icon

    }

    // This method returns the number of items present in the list
    @Override
    public int getItemCount() {
        return mNavTitles.length;
    }


}
