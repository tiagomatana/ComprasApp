package com.edina.compras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.edina.compras.dao.UserDAO;
import com.edina.compras.model.User;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button btLogar;
    private UserDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dao = new UserDAO(this);

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginSenha);
        btLogar = findViewById(R.id.btLogar);

        btLogar.setOnClickListener(view -> {
            User login = new User();
            login.setEmail(email.getText().toString());
            login.setPassword(password.getText().toString());
            User usuario = dao.login(login);
            if (usuario.isLogged()){
                Intent intent = new Intent(this, InicioActivity.class);
                intent.putExtra("USUARIO", usuario);
                startActivity(intent);
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(myIntent);
        return true;
    }
}