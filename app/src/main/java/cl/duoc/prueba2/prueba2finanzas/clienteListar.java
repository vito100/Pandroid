package cl.duoc.prueba2.prueba2finanzas;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class clienteListar extends Fragment {

    public clienteListar() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        cargar();
        super.onActivityCreated(savedInstanceState);
    }

    ListView listaCliente;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_listar_cliente, container, false);
    }

    public void cargar(){

        BD conexion = new BD(getContext(),"BD_Finanzas",null,1);
        SQLiteDatabase database = conexion.getReadableDatabase();

        if(database!=null){
            Cursor cursor = database.rawQuery("SELECT * FROM CLIENTES",null);
            int cantidad = cursor.getCount();
            String [] arregloClientes = new String[cantidad];
            int i = 0;

            if(cursor.moveToFirst()){
                do {
                    String datos = cursor.getInt(0) + " | " + cursor.getString(1) + " | " + cursor.getString(2) + " | " + cursor.getString(3)
                            + " | " + cursor.getString(4)+ " | " + cursor.getString(5)+ " | " + cursor.getString(6) + " | " + cursor.getString(7);
                    arregloClientes[i] = datos;
                    i++;
                }while (cursor.moveToNext());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,arregloClientes );

            listaCliente = (ListView) getView().findViewById(R.id.listaCliente);
            listaCliente.setAdapter(arrayAdapter);
        }
    }
}
