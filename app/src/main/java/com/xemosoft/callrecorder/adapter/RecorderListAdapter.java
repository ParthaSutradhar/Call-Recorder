package com.xemosoft.callrecorder.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xemosoft.callrecorder.R;
import com.xemosoft.callrecorder.database.Call;

import java.util.ArrayList;
import java.util.List;

public class RecorderListAdapter extends RecyclerView.Adapter<RecorderListAdapter.RecorderViewHolder> {

    private List<Call> callList;
    private Context context;
    private LayoutInflater inflater;

    public RecorderListAdapter(Context context, List<Call> calls) {
        this.callList = calls;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecorderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_list_item,parent,false);
        return new RecorderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecorderViewHolder holder, int position) {

    }

    public void refresh(){
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return callList.size();
    }

    public class RecorderViewHolder extends RecyclerView.ViewHolder {

        public RecorderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
