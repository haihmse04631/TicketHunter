package com.example.macbookpro.ticketapp.views.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.AddedEventRowItemBinding;
import com.example.macbookpro.ticketapp.models.TempEvent;
import com.example.macbookpro.ticketapp.viewmodels.fragments.AddedEventFragmentVM;

import java.util.List;

/**
 * Created by Hoang Hai on 3/19/19.
 */
public class AddedEventAdapter extends RecyclerView.Adapter<AddedEventAdapter.AddedEventViewHolder> {

    private List<TempEvent> events;
    private LayoutInflater layoutInflater;
    private AddedEventAdapterListened listened;

    @NonNull
    @Override
    public AddedEventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        AddedEventRowItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.added_event_row_item, viewGroup, false);
        return new AddedEventViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final AddedEventViewHolder addedEventViewHolder, final int i) {
        addedEventViewHolder.binding.setEvent(events.get(i));
        addedEventViewHolder.binding.contentContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                addedEventViewHolder.binding.optionDialogContainer.setVisibility(View.VISIBLE);
                return true;
            }
        });
        addedEventViewHolder.binding.closeOptionContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addedEventViewHolder.binding.optionDialogContainer.setVisibility(View.GONE);
            }
        });
        addedEventViewHolder.binding.updateOptionContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listened.onUpdateOptionTapped(events.get(i));
            }
        });
        addedEventViewHolder.binding.deleteOptionContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listened.onDeleteOptionTapped(events.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class AddedEventViewHolder extends RecyclerView.ViewHolder {
        private final AddedEventRowItemBinding binding;

        public AddedEventViewHolder(AddedEventRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public AddedEventAdapter(List<TempEvent> events, AddedEventAdapterListened listened) {
        this.events = events;
        this.listened = listened;
    }

    public interface AddedEventAdapterListened {
        void onUpdateOptionTapped(TempEvent event);
        void onDeleteOptionTapped(TempEvent event);
    }
}
