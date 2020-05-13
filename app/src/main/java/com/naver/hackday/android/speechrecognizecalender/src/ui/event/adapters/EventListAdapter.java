package com.naver.hackday.android.speechrecognizecalender.src.ui.event.adapters;

import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.naver.hackday.android.speechrecognizecalender.databinding.ItemEventListBinding;
import com.naver.hackday.android.speechrecognizecalender.src.db.temp.models.Event;
import com.naver.hackday.android.speechrecognizecalender.src.ui.event.viewModels.EventListViewModel;
import com.naver.hackday.android.speechrecognizecalender.src.ui.event.viewModels.EventViewModel;

public class EventListAdapter extends ListAdapter<Event, EventListAdapter.VisaListViewHolder> {

    private EventViewModel mEventViewModel;

    public EventListAdapter(EventViewModel eventViewModel) {
        super(DIFF_CALLBACK);
        this.mEventViewModel = eventViewModel;
    }

    private static DiffUtil.ItemCallback<Event> DIFF_CALLBACK = new DiffUtil.ItemCallback<Event>() {
        @Override
        public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.getNo() == newItem.getNo();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.getSummary().equals(newItem.getSummary())
                    && oldItem.getDescription().equals(newItem.getDescription())
                    && oldItem.getStartTime().equals(newItem.getStartTime())
                    && oldItem.getEndTime().equals(newItem.getEndTime());
        }
    };

    @NonNull
    @Override
    public VisaListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VisaListViewHolder(ItemEventListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VisaListViewHolder holder, int position) {
        Event event = getItem(position);
        if (event != null) {
            holder.bindTo(event);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new VisaListItemDecoration());
    }

    public class VisaListViewHolder extends RecyclerView.ViewHolder {

        ItemEventListBinding binding;

        public VisaListViewHolder(ItemEventListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(Event item) {
            binding.setViewModel(new EventListViewModel(item, new EventListViewModel.ItemClickListener() {
                @Override
                public void itemClick(String calenderId) {
                    Log.d("아이템", "클릭 " + calenderId);
                }
            }));
        }
    }

    public class VisaListItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            if (parent.getChildAdapterPosition(view) % 2 == 0) {
                outRect.right = 5;
            } else {
                outRect.left = 5;
            }
            outRect.bottom = 10;
        }
    }
}
