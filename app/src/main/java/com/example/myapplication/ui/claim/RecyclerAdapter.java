package com.example.myapplication.ui.claim;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.DummyModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    private static final String TAG = "RecyclerAdapter";
    private List<DummyModel> dummyModelList = new ArrayList<>();

    public interface Listener {
        void onItemClickedListener();
    }

    private Listener listener;
    private Context context;
    public RecyclerAdapter(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(dummyModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return dummyModelList.size();
    }

    public void setDummyModelList(List<DummyModel> dummyModelList) {
        this.dummyModelList = dummyModelList;
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String TAG = "ItemViewHolder";
        TextView item;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
        }

        public void bind(DummyModel dummyModel) {
            item.setText(dummyModel.title);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
