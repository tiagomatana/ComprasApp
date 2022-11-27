package com.edina.compras;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edina.compras.adapter.ItemAdapter;
import com.edina.compras.dao.ItemDAO;
import com.edina.compras.dao.UserDAO;
import com.edina.compras.model.Item;
import com.edina.compras.model.User;

import java.util.ArrayList;
import java.util.List;

public class InicioActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemDAO itemDAO;
    private List<User> usuariosFiltrados = new ArrayList<>();
    private AlertDialog alerta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        User logado = (User) getIntent().getSerializableExtra("USUARIO");
        if (!logado.isLogged()) {
            startActivity(new Intent(this, MainActivity.class));
        }
        getSupportActionBar().setTitle("Bem-vindo " + logado.getNome().toUpperCase());
        recyclerView = findViewById(R.id.recyclerView);
        itemDAO = new ItemDAO(this);
        ArrayList<Item> items = itemDAO.load(logado);

        ItemAdapter adapter = new ItemAdapter(items);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);

        findViewById(R.id.btAddItem).setOnClickListener(view -> {
            LayoutInflater li = getLayoutInflater();
            View v = li.inflate(R.layout.activity_adicionar_item, null);
            v.findViewById(R.id.btAdicionar).setOnClickListener(viewDialog -> {
                Toast.makeText(this, "TESTE", Toast.LENGTH_SHORT).show();
            });
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Adicionar Item");
            builder.setView(v);
            alerta = builder.create();
            alerta.show();

        });





    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(myIntent);
        return true;
    }
}