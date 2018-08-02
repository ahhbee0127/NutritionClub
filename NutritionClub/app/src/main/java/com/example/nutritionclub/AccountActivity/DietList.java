package com.example.nutritionclub.AccountActivity;

        import android.app.Activity;
        import android.os.TestLooperManager;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.TextView;

        import com.example.nutritionclub.R;

        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by AhhBee on 23/7/2018.
 */

public class DietList extends ArrayAdapter<Diet> {

    private Activity context;
    private List<Diet> dietList;

    public DietList(Activity context, List<Diet> dietList) {

        super(context, R.layout.diet_list_layout, dietList);
        this.context = context;
        this.dietList = dietList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.diet_list_layout, null, true);

        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.dateV);
        TextView textViewMeal = (TextView) listViewItem.findViewById(R.id.mealV);

        Diet diet = dietList.get(position);

        textViewDate.setText(diet.getDate());

        textViewMeal.setText(diet.getMeal());

        return listViewItem;
    }
}
