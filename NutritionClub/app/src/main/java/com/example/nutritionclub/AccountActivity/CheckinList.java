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

public class CheckinList extends ArrayAdapter<Checkin> {

    private Activity context;
    private List<Checkin> checkinList;

    public CheckinList(Activity context, List<Checkin> checkinList) {

        super(context, R.layout.client_log_layout, checkinList);
        this.context = context;
        this.checkinList = checkinList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.client_log_layout, null, true);

        TextView nameV = (TextView) listViewItem.findViewById(R.id.nameV);
        TextView timeV = (TextView) listViewItem.findViewById(R.id.timeV);

        Checkin checkin = checkinList.get(position);

        nameV.setText(checkin.getName());
        timeV.setText(checkin.getTime());


        return listViewItem;
    }
}
