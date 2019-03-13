package com.example.macbookpro.ticketapp.views.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.EventRowItemBinding;
import com.example.macbookpro.ticketapp.models.Event;

import java.util.List;

/**
 * Created by Hoang Hai on 1/27/19.
 */
public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventListViewHolder> {

    private List<Event> events;
    private LayoutInflater layoutInflater;
    private EventListAdapterListened listened;

    public class EventListViewHolder extends RecyclerView.ViewHolder {

        private final EventRowItemBinding binding;

        public EventListViewHolder(EventRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public EventListAdapter(List<Event> events, EventListAdapterListened listened) {
        Log.e("eventListAdapter", events.size() + "");
        this.events = events;
        this.listened = listened;
    }

    @NonNull
    @Override
    public EventListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        EventRowItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.event_row_item, viewGroup, false);
        return new EventListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EventListViewHolder eventListViewHolder, final int i) {
        Log.e("eventData", events.get(i).getName());
        eventListViewHolder.binding.setEvent(events.get(i));
        eventListViewHolder.binding.eventItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listened.onEventTapped(events.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public interface EventListAdapterListened {
        void onEventTapped(Event event);
    }

}
