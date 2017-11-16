package cl.duoc.prueba2.prueba2finanzas;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class clienteEliminar extends Fragment {

    public clienteEliminar() {

    }

    Spinner spnRut;
    Button btnEliminar;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        spnRut = (Spinner) getView().findViewById(R.id.spnRutClienteEliminar);
        cargarDatosSpinner();


        btnEliminar = (Button) getView().findViewById(R.id.btnEliminarCliente);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_menu_principal, new clienteEliminar()).commit();
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_cliente_eliminar, container, false);
    }

    private void cargarDatosSpinner() {

        BD conexion = new BD(getContext(),"BD_Finanzas",null,1);
        SQLiteDatabase database = conexion.getReadableDatabase();

        ArrayList<String> listaClientes=new ArrayList<String>();
        listaClientes.add("Seleccione");

        if(database!=null){
            Cursor cursor = database.rawQuery("SELECT rut FROM CLIENTES",null);

            if(cursor.moveToFirst()){
                do {
                    listaClientes.add(cursor.getString(0));
                }while (cursor.moveToNext());
            }
        }
        spnRut.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listaClientes));
    }

    public void eliminar(){

        if (!spnRut.getSelectedItem().toString().equals("Seleccione"))
        {
            BD conexion = new BD(getContext(),"BD_Finanzas",null,1);
            SQLiteDatabase db = conexion.getReadableDatabase();
            String[] parametros = {spnRut.getSelectedItem().toString()};

            try {
                db.delete("CLIENTES","rut = ?",parametros);
                Toast.makeText(getContext(),"Cliente Eliminado!!",Toast.LENGTH_SHORT).show();
                db.close();
                cargarDatosSpinner();
            } catch ( Exception e ) {
                Toast.makeText(getContext(),"El cliente no Existe",Toast.LENGTH_SHORT).show();

            }
        }else
            Toast.makeText(getContext(),"Seleccione Cliente",Toast.LENGTH_SHORT).show();

    }

    public void limpiar(){
        cargarDatosSpinner();

    }
}
