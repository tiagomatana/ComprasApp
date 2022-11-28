package com.edina.compras.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edina.compras.connection.Conexao;
import com.edina.compras.model.User;


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
        return banco.insert("usuarios", null, values);
    }

    public User login(User user) {
        User logged = new User();
        String query = "SELECT email,name,id FROM usuarios WHERE email = '" + user.getEmail() + "' and password = '"+ user.getPassword()+"'";
        Cursor cursor = banco.rawQuery(query, null);
        while (cursor.moveToNext()){
            if (user.getEmail().equals(cursor.getString(0))){
                logged.setEmail(cursor.getString(0));
                logged.setNome(cursor.getString(1));
                logged.setId(cursor.getInt(2));
            }
        }

        return logged;
    }

    public long update(User user){
        ContentValues values = new ContentValues();
        values.put("name", user.getNome());
        values.put("password", user.getPassword());
        return banco.update("usuarios", values, "email = ?", new String[]{user.getEmail()});
    }
}
