package cl.duoc.prueba2.prueba2finanzas;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
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

public class categoriaEliminar extends Fragment {

    public categoriaEliminar() {
    }

    Spinner spnId;
    Button btnEliminar;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        spnId = (Spinner) getView().findViewById(R.id.spnIdCategoriaEliminar);
        cargarDatosSpinner();


        btnEliminar = (Button) getView().findViewById(R.id.btnEliminarCategoriaEliminar);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_menu_principal, new categoriaEliminar()).commit();
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_categoria_eliminar, container, false);
    }

    private void cargarDatosSpinner() {

        BD conexion = new BD(getContext(),"BD_Finanzas",null,1);
        SQLiteDatabase database = conexion.getReadableDatabase();

        ArrayList<String> listaCategorias=new ArrayList<String>();
        listaCategorias.add("Seleccione");

        if(database!=null){
            Cursor cursor = database.rawQuery("SELECT id FROM CATEGORIAS",null);

            if(cursor.moveToFirst()){
                do {
                    listaCategorias.add(cursor.getString(0));
                }while (cursor.moveToNext());
            }
        }
        spnId.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listaCategorias));
    }

    public void eliminar(){

        if (!spnId.getSelectedItem().toString().equals("Seleccione"))
        {
            BD conexion = new BD(getContext(),"BD_Finanzas",null,1);
            SQLiteDatabase db = conexion.getReadableDatabase();
            String[] parametros = {spnId.getSelectedItem().toString()};

            try {
                db.delete("CATEGORIAS","id = ?",parametros);
                Toast.makeText(getContext(),"Categoria Eliminada!!",Toast.LENGTH_SHORT).show();
                db.close();
                cargarDatosSpinner();
            } catch ( Exception e ) {
                Toast.makeText(getContext(),"La categoria no Existe",Toast.LENGTH_SHORT).show();

            }
        }else
            Toast.makeText(getContext(),"Seleccione Categoria",Toast.LENGTH_SHORT).show();

    }

    public void limpiar(){
        cargarDatosSpinner();

    }
}
