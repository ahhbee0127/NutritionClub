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

public class CheckinDateList extends ArrayAdapter<CheckinDate> {

    private Activity context;
    private List<CheckinDate> checkinDateList;

    public CheckinDateList(Activity context, List<CheckinDate> checkinDateList) {

        super(context, R.layout.log_layout, checkinDateList);
        this.context = context;
        this.checkinDateList = checkinDateList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.log_layout, null, true);

        TextView dateV = (TextView) listViewItem.findViewById(R.id.dateV);

        CheckinDate checkinDate = checkinDateList.get(position);

        dateV.setText(checkinDate.getDate());

        return listViewItem;
    }
}
