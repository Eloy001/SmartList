package com.example.ejemploo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class DBcontactos extends DBase{

    Context context;

    public DBcontactos(@Nullable Context context) {
        super(context);
        this.context = context;
    }
    public long insertarContacto(String nombre, String contraseña, String apellidos, String edad) {

        long id = 0;
        try {
            DBase dbejemplo = new DBase(context);
            SQLiteDatabase db = dbejemplo.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("contraseña", contraseña);
            values.put("apellidos", apellidos);
            values.put("edad", edad);

            id = db.insert(TABLE_USUARIOS, null, values);
        } catch (Exception e) {
            e.toString();
        }
        return id;
    }

    public void Buscar(String nombre, String contraseña) {
        DBase dBejemplo = new DBase(context);
        SQLiteDatabase db = dBejemplo.getReadableDatabase();
        String consult = "SELECT * FROM " + TABLE_USUARIOS + " WHERE (nombre = '" + nombre + "')" + "AND (contraseña = '" + contraseña + "');";
        Cursor cursor = db.rawQuery(consult, null);
        String nombre2 = "";
        String contra2 = "";

        while (cursor.moveToNext()) {
            nombre2 = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
            contra2 = cursor.getString(cursor.getColumnIndexOrThrow("contraseña"));

        }
        if (nombre2.equals(nombre) && contra2.equals(contraseña)) {
            Toast.makeText(context, "Login Correcto", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Login Incorrecto", Toast.LENGTH_SHORT).show();
        }

    }


    public ArrayList<Usuarios> mostrarContactos() {

        DBase dbHelper = new DBase(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Usuarios> listaContactos = new ArrayList<>();
        Usuarios contacto;
        Cursor cursorContactos;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS, null);

        if (cursorContactos.moveToFirst()) {
            do {
                contacto = new Usuarios();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setNombre(cursorContactos.getString(1));
                contacto.setApellidos(cursorContactos.getString(3));
                contacto.setEdad(cursorContactos.getString(4));
                listaContactos.add(contacto);
            } while (cursorContactos.moveToNext());
        }

        cursorContactos.close();

        return listaContactos;
    }

    public Usuarios verContacto(int id) {

        DBase dbHelper = new DBase(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Usuarios contacto = null;
        Cursor cursorContactos;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS + " WHERE id = " + id + " LIMIT 1 ", null);

        if (cursorContactos.moveToFirst()) {
            contacto = new Usuarios();
            contacto.setId(cursorContactos.getInt(0));
            contacto.setNombre(cursorContactos.getString(1));
            contacto.setApellidos(cursorContactos.getString(3));
            contacto.setEdad(cursorContactos.getString(4));
        }

        cursorContactos.close();

        return contacto;
    }

    public boolean editarContacto(int id, String nombre, String apellidos, String edad) {

        boolean correcto = false;

        DBase dbejemplo = new DBase(context);
        SQLiteDatabase db = dbejemplo.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_USUARIOS + " SET nombre = '"+nombre+"' , apellidos = '"+apellidos+"', edad = '"+ edad +"' WHERE id= '"+ id +"' ");
            correcto = true;
        } catch (Exception e) {
            e.toString();
            correcto = false;
        }finally {
            db.close();
        }
        return correcto;
    }

    public boolean eliminarContacto(int id) {

        boolean correcto = false;

        DBase dbejemplo = new DBase(context);
        SQLiteDatabase db = dbejemplo.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_USUARIOS + " WHERE id = '"+id+"'");
            correcto = true;
        } catch (Exception e) {
            e.toString();
            correcto = false;
        }finally {
            db.close();
        }
        return correcto;
    }

}
