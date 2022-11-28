package com.edina.compras.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edina.compras.InicioActivity;
import com.edina.compras.R;
import com.edina.compras.dao.ItemDAO;
import com.edina.compras.model.Item;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private ArrayList<Item> itemsData;
    private ItemDAO itemDAO;
    public Callable edit;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        parent.getContext().getClass()
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getDescriptionView().setText(itemsData.get(position).getDescricao());
        holder.getDescriptionView().setChecked(itemsData.get(position).getStatus());
        holder.getQuantidadeView().setText(""+ itemsData.get(position).getQuantidade());
        holder.getDescriptionView().setOnCheckedChangeListener((compoundButton, b) -> {
            itemDAO = new ItemDAO(compoundButton.getContext());
            itemsData.get(position).setStatus(b == Boolean.TRUE ? 1 : 0);
            itemDAO.update(itemsData.get(position));
        });

        holder.itemView.findViewById(R.id.editButton).setOnClickListener(view -> {
//            this.edit.;
        });
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    public ItemAdapter(ArrayList<Item> items) {
        this.itemsData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox descriptionView;
        private final TextView quantidadeView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            descriptionView = itemView.findViewById(R.id.itemDecription);
            quantidadeView = itemView.findViewById(R.id.qntView);

        }

        public TextView getQuantidadeView() {
            return quantidadeView;
        }

        public CheckBox getDescriptionView() {
            return descriptionView;
        }
    }
}
