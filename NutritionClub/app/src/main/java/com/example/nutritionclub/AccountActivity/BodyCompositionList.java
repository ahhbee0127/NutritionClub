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

public class BodyCompositionList extends ArrayAdapter<BodyComposition> {

    private Activity context;
    private List<BodyComposition> bodyCompositionList;

    public BodyCompositionList(Activity context, List<BodyComposition> bodyCompositionList) {

        super(context, R.layout.body_list_layout, bodyCompositionList);
        this.context = context;
        this.bodyCompositionList = bodyCompositionList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.body_list_layout, null, true);

        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.dateV);
        TextView textViewWeight = (TextView) listViewItem.findViewById(R.id.weightV);

        BodyComposition bodyComposition = bodyCompositionList.get(position);

        textViewDate.setText(bodyComposition.getTodayDate());

        String weight = Double.toString(bodyComposition.getWeight());
        textViewWeight.setText(weight + " kg");

        return listViewItem;
    }
}
