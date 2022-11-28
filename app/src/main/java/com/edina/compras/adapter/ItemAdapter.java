package com.edina.compras.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edina.compras.Callback;
import com.edina.compras.InicioActivity;
import com.edina.compras.R;
import com.edina.compras.dao.ItemDAO;
import com.edina.compras.model.Item;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private ArrayList<Item> itemsData;
    private ItemDAO itemDAO;
    public AlertDialog edit;
    Callback callback;


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
        holder.getDescriptionView().setChecked(itemsData.get(position).getStatus());
        holder.getQuantidadeView().setText(""+ itemsData.get(position).getQuantidade());
        holder.getDescriptionView().setOnCheckedChangeListener((compoundButton, b) -> {
            itemDAO = new ItemDAO(compoundButton.getContext());
            Item item = itemsData.get(position);
            item.setStatus(compoundButton.isChecked());
            itemDAO.update(item);
        });

        holder.itemView.findViewById(R.id.editButton).setOnClickListener(view -> {
            Item item = itemsData.get(position);
            callback.editarItem(item);
        });

        holder.itemView.findViewById(R.id.deleteButton).setOnClickListener(view -> {
            Item item = itemsData.get(position);
            callback.removerItem(item);
        });
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    public ItemAdapter(ArrayList<Item> items, Callback callback) {
        this.itemsData = items;
        this.callback = callback;
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
