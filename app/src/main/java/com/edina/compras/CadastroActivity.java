package com.edina.compras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edina.compras.dao.UserDAO;
import com.edina.compras.model.User;

public class CadastroActivity extends AppCompatActivity {

    private UserDAO dao;
    private Button btSalvar;
    private EditText nome;
    private EditText email;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cadastro");
        dao = new UserDAO(this);

        nome = findViewById(R.id.campoNome);
        email = findViewById(R.id.campoEmail);
        password = findViewById(R.id.campoSenha);
        btSalvar = findViewById(R.id.btSalvar);

        clear();

        btSalvar.setOnClickListener(view -> {
            User user = new User();
            user.setNome(nome.getText().toString());
            user.setEmail(email.getText().toString());
            user.setPassword(password.getText().toString());
            if (user.validate()) {
                insert(user);
            } else {
                Toast.makeText(this, "Informe todos os campos.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void insert(User user){
        long id = dao.save(user);
        if (id > 0){
            Toast.makeText(this, "Cadastro realizado com sucesso.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, InicioActivity.class);
            intent.putExtra("USUARIO", user);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Houve um problema. Contate o administrador.", Toast.LENGTH_SHORT).show();
        }
    }

    public void clear() {
        nome.getText().clear();
        email.getText().clear();
        password.getText().clear();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(myIntent);
        return true;
    }

}