package com.edina.compras;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edina.compras.adapter.ItemAdapter;
import com.edina.compras.dao.ItemDAO;
import com.edina.compras.model.Item;
import com.edina.compras.model.User;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class InicioActivity extends AppCompatActivity implements Callback {

    private RecyclerView recyclerView;
    private ItemDAO itemDAO;
    private AlertDialog adicionarItem;
    private AlertDialog editarItem;

    public void lista(User logado) {
        recyclerView = findViewById(R.id.recyclerView);
        itemDAO = new ItemDAO(this);
        ArrayList<Item> items = itemDAO.load(logado);

        ItemAdapter adapter = new ItemAdapter(items, this);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        User logado = (User) getIntent().getSerializableExtra("USUARIO");
        if (!logado.isLogged()) {
            startActivity(new Intent(this, MainActivity.class));
        }
        getSupportActionBar().setTitle("Bem-vindo");
        lista(logado);

        findViewById(R.id.btAddItem).setOnClickListener(view -> {
            this.adicionarItem(logado);
        });
    }

    public void adicionarItem(User logado) {
        LayoutInflater li = getLayoutInflater();
        View v = li.inflate(R.layout.activity_gerenciar_item, null);
        EditText descricao = v.findViewById(R.id.addDescricaoText);
        EditText quantidade = v.findViewById(R.id.addQuantidadeText);
        Item item = new Item();
        item.setEmail(logado.getEmail());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Adicionar Item");
        builder.setCancelable(Boolean.TRUE);
        builder.setView(v);
        builder.setPositiveButton("Salvar", (dialogInterface, i) -> {
            item.setQuantidade(Integer.parseInt(quantidade.getText().toString()));
            item.setDescricao(descricao.getText().toString());
            itemDAO.save(item);
            Toast.makeText(this, "Alterado com sucesso.", Toast.LENGTH_SHORT).show();
            logado.setEmail(item.getEmail());
            lista(logado);
        });
        builder.setNegativeButton("Cancelar", (dialogInterface, i) -> {
            lista(logado);
        });
        adicionarItem = builder.create();
        adicionarItem.show();
    }

    public void editarItem(Item item) {
        LayoutInflater li = getLayoutInflater();
        View v = li.inflate(R.layout.activity_gerenciar_item, null);
        EditText descricao = v.findViewById(R.id.addDescricaoText);
        EditText quantidade = v.findViewById(R.id.addQuantidadeText);
        descricao.setText(item.getDescricao());
        quantidade.setText(Integer.toString(item.getQuantidade()));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Adicionar Item");
        User logado = new User();
        builder.setPositiveButton("Atualizar", (dialogInterface, i) -> {
            item.setQuantidade(Integer.parseInt(quantidade.getText().toString()));
            item.setDescricao(descricao.getText().toString());
            itemDAO.update(item);
            Toast.makeText(this, "Alterado com sucesso.", Toast.LENGTH_SHORT).show();
            logado.setEmail(item.getEmail());
            lista(logado);
        });
        builder.setNegativeButton("Cancelar", (dialogInterface, i) -> {

        });
        builder.setView(v);
        adicionarItem = builder.create();
        adicionarItem.show();
    }

    public void removerItem(Item item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("REMOVER ITEM");
        builder.setMessage("Tem certeza que deseja remover o item " + item.getDescricao() +"?");
        User logado = new User();
        builder.setPositiveButton("Remover", (dialogInterface, i) -> {
            itemDAO.remove(item);
            Toast.makeText(this, "Removido com sucesso.", Toast.LENGTH_SHORT).show();
            logado.setEmail(item.getEmail());
            lista(logado);
        });
        builder.setNegativeButton("Cancelar", (dialogInterface, i) -> {

        });
        adicionarItem = builder.create();
        adicionarItem.show();
    }


    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(myIntent);
        return true;
    }
}