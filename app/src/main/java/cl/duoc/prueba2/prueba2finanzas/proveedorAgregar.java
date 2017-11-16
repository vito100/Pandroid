package cl.duoc.prueba2.prueba2finanzas;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class proveedorAgregar extends Fragment {

    EditText edtRut,edtRazonSoci,edtDireccion,edtCiudad,edtComuna,edtLatitud,edtLongitud;
    Spinner spnCategoria;
    Button btnRegistrar;

    public proveedorAgregar() {
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {


        edtRut = (EditText) getView().findViewById(R.id.edRutAgregarProveedor);
        edtRazonSoci = (EditText) getView().findViewById(R.id.edRazonSocialAgregarProveedor);
        spnCategoria = (Spinner) getView().findViewById(R.id.spnCategoriaAgregarProveedor);
        edtDireccion = (EditText) getView().findViewById(R.id.edDireccionAgregarProveedor);
        edtCiudad = (EditText) getView().findViewById(R.id.edCiudadAgregarProveedor);
        edtComuna = (EditText) getView().findViewById(R.id.edComunaAgregarProveedor);
        edtLatitud = (EditText) getView().findViewById(R.id.edLatitudAgregarProveedor);
        edtLongitud = (EditText) getView().findViewById(R.id.edLongitudAgregarProveedor);

        limpiar();
        btnRegistrar = (Button) getView().findViewById(R.id.btnAgregarProveedor);
        btnRegistrar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                insertar();
            }
        });
        super.onActivityCreated(savedInstanceState);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_proveedor_agregar, container, false);
    }

    public void insertar(){

        String rut = edtRut.getText().toString();
        String razonSoci = edtRazonSoci.getText().toString();
        String categoria = spnCategoria.getSelectedItem().toString();
        String direccion = edtDireccion.getText().toString();
        String ciudad = edtCiudad.getText().toString();
        String comuna = edtComuna.getText().toString();
        String latitud = edtLatitud.getText().toString();
        String longitud = edtLongitud.getText().toString();


        if (!rut.trim().equals("") && !razonSoci.trim().equals("")&& !direccion.trim().equals("")&&
                !ciudad.trim().equals("")&& !comuna.trim().equals("")&& !latitud.trim().equals("")&& !longitud.trim().equals("")) {

            BD conexion = new BD (getContext(), "BD_Finanzas",null,1);

            SQLiteDatabase database = conexion.getWritableDatabase();

            if (database != null) {
                ContentValues parametros = new ContentValues();
                parametros.put("rut", rut.trim());
                parametros.put("razon_social", razonSoci.trim());
                parametros.put("categoria", categoria.trim());
                parametros.put("direccion", direccion.trim());
                parametros.put("ciudad", ciudad.trim());
                parametros.put("comuna", comuna.trim());
                parametros.put("latitud", latitud.trim());
                parametros.put("longitud", longitud.trim());
                long i = database.insert("PROVEEDORES", null, parametros);
                if (i > 0){
                    limpiar();
                    Toast.makeText(getContext(), "Proveedor Insertado", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                database.close();
            }
        }else
            Toast.makeText(getContext(),"no se permiten campos vacios",Toast.LENGTH_SHORT).show();

    }

    private void cargarDatosSpinnerCategoria() {

        BD conexion = new BD(getContext(), "BD_Finanzas", null, 1);
        SQLiteDatabase database = conexion.getReadableDatabase();
        ArrayList<String> listaCategoria = new ArrayList<String>();
        listaCategoria.add("Seleccione Categoria");

        if (database != null) {
            Cursor cursor = database.rawQuery("SELECT id FROM CATEGORIAS", null);
            if (cursor.moveToFirst()) {
                do {
                    listaCategoria.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        }
        spnCategoria.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listaCategoria));
    }

    public void limpiar(){
        edtRut.setText("");
        edtRazonSoci.setText("");
        edtDireccion.setText("");
        edtCiudad.setText("");
        edtComuna.setText("");
        edtLatitud.setText("");
        edtLongitud.setText("");
        cargarDatosSpinnerCategoria();
    }
}
