package com.example.nutritionclub.AccountActivity;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nutritionclub.R;

import java.util.List;

/**
 * Created by AhhBee on 1/8/2018.
 */

public class EventList extends ArrayAdapter<Event> {

    private android.app.Activity context;
    private List<Event> eventList;

    public EventList(android.app.Activity context, List<Event> eventList) {

        super(context, R.layout.event_list_layout, eventList);
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.event_list_layout, null, true);

        TextView textViewEventName = (TextView) listViewItem.findViewById(R.id.eventNameV);
        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.dateV);

        Event event = eventList.get(position);

        textViewDate.setText(event.getDate());
        textViewEventName.setText(event.getEventName());

        return listViewItem;
    }
}

