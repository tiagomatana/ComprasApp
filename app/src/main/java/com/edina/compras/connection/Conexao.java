package com.edina.compras.connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper {

    private static final String name = "bancodedados.db";
    private static final int version = 1;

    public Conexao(Context context){
        super(context, name, null, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table usuarios(id integer primary key autoincrement, email varchar(50) unique not null, " +
                "name varchar(50) not null, password varchar(50) not null)");
        db.execSQL("create table items(id integer primary key autoincrement, email varchar(50) not null, " +
                "descricao varchar(50) not null, quantidade int not null, status bool not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
