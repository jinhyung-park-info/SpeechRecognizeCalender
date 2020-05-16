package com.naver.hackday.android.speechrecognizecalender.src.ui.event.viewModels;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;
import com.naver.hackday.android.speechrecognizecalender.src.db.temp.models.Event;

import java.util.Date;


public class EventListViewModel extends ViewModel {

    private Event mItem;
    private ItemClickListener mItemClickListener = null;

    //xml과 연결되는 변수 (Data Binding)
    public ObservableField<String> summary;
    public ObservableField<String> description;
    public ObservableField<Date> startDay;

    public interface ItemClickListener{
        void itemClick(String calenderId);
    }

    /* Constructor */
    public EventListViewModel(Event mItem, ItemClickListener mItemClickListener) {
        this.mItem = mItem;
        this.mItemClickListener = mItemClickListener;
        summary = new ObservableField<String>(mItem.getSummary());
        description = new ObservableField<String>(mItem.getDescription());
        startDay = new ObservableField<Date>(mItem.getStartTime());
    }

    public void itemClicked() {
        mItemClickListener.itemClick(mItem.getCalenderId());
    }
}
