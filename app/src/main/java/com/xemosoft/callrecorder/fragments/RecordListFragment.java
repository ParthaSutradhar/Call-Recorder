package com.xemosoft.callrecorder.fragments;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xemosoft.callrecorder.R;
import com.xemosoft.callrecorder.adapter.RecorderListAdapter;
import com.xemosoft.callrecorder.database.AppDatabase;
import com.xemosoft.callrecorder.database.Call;

import java.util.ArrayList;
import java.util.List;

public class RecordListFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recorder_list_fragment,container,false);
        AppDatabase db = Room.databaseBuilder(getContext(),AppDatabase.class,"appdeve").allowMainThreadQueries().build();

        List<Call> callArrayList =  db.getCallDao().getCallList();

        recyclerView = view.findViewById(R.id.record_recycler_list);
        recyclerView.setAdapter(new RecorderListAdapter(getContext(),callArrayList));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}
