package sg.edu.nus.mytravelapp;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {
    EditText food, travel, accomodation, play, shopping;


    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        food = (EditText) view.findViewById(R.id.editText_food);
        travel = (EditText) view.findViewById(R.id.editText_travel);
        accomodation = (EditText) view.findViewById(R.id.editText_accomodation);
        play = (EditText) view.findViewById(R.id.editText_play);
        shopping = (EditText) view.findViewById(R.id.editText_shopping);
        return view;
    }

    public double getFoodCost() {
        Double foodCost = Double.parseDouble(food.getEditableText().toString());
        return foodCost;
    }

    public double getTravelCost() {
        Double travelCost = Double.parseDouble(travel.getEditableText().toString());
        return travelCost;
    }

    public double getAccomodationCost() {
        Double accomodationCost = Double.parseDouble(accomodation.getEditableText().toString());
        return accomodationCost;
    }

    public double getPlayCost() {
        Double playCost = Double.parseDouble(play.getEditableText().toString());
        return playCost;
    }

    public double getShoppingCost() {
        Double shoppingCost = Double.parseDouble(shopping.getEditableText().toString());
        return shoppingCost;
    }

    public void setBudgetView(String foodBudget, String travelBudget, String accomodationBudget, String playBudget, String shoppingBudget) {
        food.setText(foodBudget);
        travel.setText(travelBudget);
        accomodation.setText(accomodationBudget);
        play.setText(playBudget);
        shopping.setText(shoppingBudget);
    }
}
