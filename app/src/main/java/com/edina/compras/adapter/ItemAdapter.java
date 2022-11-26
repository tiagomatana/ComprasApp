package com.edina.compras.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edina.compras.R;
import com.edina.compras.model.Item;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Item> itemsData;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getDescriptionView().setText(itemsData.get(position).getDescricao());
        holder.getDescriptionView().setChecked(itemsData.get(position).isStatus());
        holder.getQuantidadeView().setText(itemsData.get(position).getQuantidade());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public ItemAdapter(ArrayList<Item> items) {
        this.itemsData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox descriptionView;
        private final TextView quantidadeView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            descriptionView = (CheckBox) itemView.findViewById(R.id.itemDecription);
            quantidadeView = (TextView) itemView.findViewById(R.id.qntView);

        }

        public TextView getQuantidadeView() {
            return quantidadeView;
        }

        public CheckBox getDescriptionView() {
            return descriptionView;
        }
    }
}
