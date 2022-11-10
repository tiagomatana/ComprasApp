package com.edina.compras.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edina.compras.connection.Conexao;
import com.edina.compras.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Conexao conexao;
    private SQLiteDatabase banco;

    public UserDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long save(User user){
        ContentValues values = new ContentValues();
        values.put("name", user.getNome());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        return banco.insert("usuario", null, values);
    }

    public List<User> load() {
        List<User> users = new ArrayList<>();
        Cursor cursor = banco.query("usuario", new String[]{"id", "name", "email", "password"},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            User user = new User();
            user.setId(cursor.getInt(0));
            user.setNome(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            users.add(user);
        }

        return users;
    }
}
