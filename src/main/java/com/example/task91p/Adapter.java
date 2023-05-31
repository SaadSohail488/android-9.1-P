package com.example.task91p;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task91p.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.LostAndFoundViewHolder> {

    private ArrayList<DATA> list = new ArrayList<>();
    private final SetOnItemClickListener setOnItemClickListener;

    public Adapter(SetOnItemClickListener setOnItemClickListener) {
        this.setOnItemClickListener = setOnItemClickListener;
    }

    public void submit(ArrayList<DATA> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public static class LostAndFoundViewHolder extends RecyclerView.ViewHolder {
        private TextView heading;
        private TextView detail;

        public LostAndFoundViewHolder(View view) {
            super(view);
            heading = view.findViewById(R.id.heading);
            detail = view.findViewById(R.id.detail);
        }

        @SuppressLint("SetTextI18n")
        public void bind(final DATA DATA, final SetOnItemClickListener setOnItemClickListener) {
            heading.setText(DATA.getIsLostOrFound() + ": " + DATA.getName());
            detail.setText(new StringBuilder()
                    .append(DATA.getDate())
                    .append("\n")
                    .append(DATA.getLocation())
                    .append("\n")
                    .append(DATA.getPhone())
                    .append("\n")
                    .append(DATA.getDescription())
                    .toString());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setOnItemClickListener.onItemClickListener(DATA);
                }
            });
        }
    }

    @Override
    public LostAndFoundViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view, parent, false);
        return new LostAndFoundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LostAndFoundViewHolder holder, int position) {
        holder.bind(list.get(position), setOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

interface SetOnItemClickListener {
    void onItemClickListener(DATA lostAndFoundModel);
}