package com.edina.compras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.edina.compras.dao.UserDAO;
import com.edina.compras.model.User;

import java.util.ArrayList;
import java.util.List;

public class InicioActivity extends AppCompatActivity {

    private ListView listView;
    private UserDAO dao;
    private List<User> usuarios;
    private List<User> usuariosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Listas");
        listView = findViewById(R.id.listagem);
        dao = new UserDAO(this);
        usuarios = dao.load();
        usuariosFiltrados.addAll(usuarios);
        ArrayAdapter<User> adapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, usuarios);
        listView.setAdapter(adapter);


    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(myIntent);
        return true;
    }
}