package com.example.easycalendar;

import android.content.Context;
import android.content.res.TypedArray;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    ArrayList<MyEvent> events;
    Context context;

    public EventAdapter(Context context, ArrayList<MyEvent> events) {
        this.events = events;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_card,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.eventName.setText(events.get(position).getEventName());

        TypedArray categories = context.getResources().obtainTypedArray(R.array.categoryOptions_array);
        String category  = categories.getString(events.get(position).getIndex_category());
        holder.eventCategory.setText(category);

        String date = events.get(position).getStartDate().toString();
        holder.startDate.setText(date);

        holder.startTime.setText(events.get(position).getStartTime().toString());
        holder.eventColor.setBackgroundColor(events.get(position).getColor());

    }


    @Override
    public int getItemCount() {
        return events.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView eventCategory;
        TextView eventName;
        TextView startDate;
        TextView weekDay;
        TextView startTime;
        ImageView eventColor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.event_card_eventName);
            eventCategory = itemView.findViewById( R.id.event_card_eventCategory );
            startDate = itemView.findViewById( R.id.event_card_startDate );
            weekDay = itemView.findViewById( R.id.event_card_weekDay );
            startTime = itemView.findViewById( R.id.event_card_startTime );
            eventColor = itemView.findViewById(R.id.event_card_eventColor);
        }
    }


}
