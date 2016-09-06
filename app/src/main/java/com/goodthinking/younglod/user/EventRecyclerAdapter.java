package com.goodthinking.younglod.user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodthinking.younglod.user.model.Event;

/**
 * Created by user on 11/08/2016.
 */
public class EventRecyclerAdapter extends RecyclerView.Adapter {
    private Context context;
    String role;

    public EventRecyclerAdapter(Context context, String role) {
        this.context = context;
        this.role = role;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);
        SimpleItemViewHolder pvh = new SimpleItemViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SimpleItemViewHolder viewHolder = (SimpleItemViewHolder) holder;
        viewHolder.position = position;
        Event event = EventArraydata.getInstance().getEvents().get(position);
        ((SimpleItemViewHolder) holder).ItemEventHeadLine.setText(event.getEventName());
        ((SimpleItemViewHolder) holder).ItemEventSynopsys.setText(event.getEventSynopsys());
        ((SimpleItemViewHolder) holder).ItemEventDate.setText("ON: " + event.getEventDate() + " AT: " + event.getEventTime());

    }

    @Override
    public int getItemCount() {
        return EventArraydata.getInstance().getEvents().size();
    }

    public final class SimpleItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView ItemEventHeadLine, ItemEventSynopsys, ItemEventDate;
        public int position;

        public SimpleItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            context = itemView.getContext();
            ItemEventHeadLine = (TextView) itemView.findViewById(R.id.ItemEventHeadLine);
            ItemEventSynopsys = (TextView) itemView.findViewById(R.id.ItemEventSynopsys);
            ItemEventDate = (TextView) itemView.findViewById(R.id.ItemEventdate);

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, EventInformationActivity_Firebase.class);
            System.out.println("position=" + position + " key=" + EventArraydata.getInstance().getEvents().get(position).getKey());
            intent.putExtra("Eventkey", EventArraydata.getInstance().getEvents().get(position).getKey());
            System.out.println(getAdapterPosition());
            intent.putExtra("position", position);
            intent.putExtra("Role", role);
            context.startActivity(intent);

        }
    }
}
