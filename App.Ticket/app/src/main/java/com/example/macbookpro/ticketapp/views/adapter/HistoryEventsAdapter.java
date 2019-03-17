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
import com.example.macbookpro.ticketapp.databinding.HistoryEventRowItemBinding;
import com.example.macbookpro.ticketapp.models.Event;
import com.example.macbookpro.ticketapp.models.TempEvent;

import java.util.List;

/**
 * Created by Hoang Hai on 3/18/19.
 */
public class HistoryEventsAdapter extends RecyclerView.Adapter<HistoryEventsAdapter.HistoryEventViewHolder> {

    private List<TempEvent> events;
    private LayoutInflater layoutInflater;
    private HistoryEventListened listened;

    @NonNull
    @Override
    public HistoryEventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        HistoryEventRowItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.history_event_row_item, viewGroup, false);
        return new HistoryEventViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryEventViewHolder historyEventViewHolder, final int i) {
        historyEventViewHolder.binding.setEvent(events.get(i));
        historyEventViewHolder.binding.eventItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listened.onEventTapped(events.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class HistoryEventViewHolder extends RecyclerView.ViewHolder {

        private final HistoryEventRowItemBinding binding;

        public HistoryEventViewHolder(HistoryEventRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public HistoryEventsAdapter(List<TempEvent> events, HistoryEventListened listened) {
        this.events = events;
        this.listened = listened;
    }

    public interface HistoryEventListened {
        void onEventTapped(TempEvent event);
    }


}
