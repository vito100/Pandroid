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

public class proveedorModificar extends Fragment {

    EditText edtRazonSoci,edtDireccion,edtCiudad,edtComuna,edtLatitud,edtLongitud;
    Spinner spnRut,spnCategoria;
    Button btnModificar;

    public proveedorModificar() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        spnRut = (Spinner) getView().findViewById(R.id.spRutModificarProveedor);
        edtRazonSoci = (EditText) getView().findViewById(R.id.edRazonSocialModificarProveedor);
        spnCategoria = (Spinner) getView().findViewById(R.id.spnCategoriaModificarProveedor);
        edtDireccion = (EditText) getView().findViewById(R.id.edDireccionModificarProveedor);
        edtCiudad = (EditText) getView().findViewById(R.id.edCiudadModificarProveedor);
        edtComuna = (EditText) getView().findViewById(R.id.edComunaModificarProveedor);
        edtLatitud = (EditText) getView().findViewById(R.id.edLatitudModificarProveedor);
        edtLongitud = (EditText) getView().findViewById(R.id.edLongitudModificarProveedor);
        limpiar();

        btnModificar = (Button) getView().findViewById(R.id.btnModificarProveedor);
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificar();
            }
        });

        spnRut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if (spnRut.getSelectedItem().equals("Seleccione")) {
                    Log.v("depuracion", "NO Entro en consulta BD RUT");
                    limpiar();
                }
                if (!spnRut.getSelectedItem().equals("Seleccione")) {
                    BD conexion = new BD(getContext(), "BD_Finanzas", null, 1);
                    SQLiteDatabase database = conexion.getReadableDatabase();
                    Log.v("depuracion", "Entro en consulta BD RUT");


                    if (database != null) {
                        Log.v("depuracion", "Entro en consulta 2 BD RUT");
                        Cursor cursor = database.rawQuery("SELECT * FROM PROVEEDORES where rut=?", new String[]{spnRut.getSelectedItem().toString()});
                        if (cursor.moveToFirst()) {


                            edtRazonSoci.setEnabled(true);
                            edtRazonSoci.setText(cursor.getString(1));
                            spnCategoria.setEnabled(true);
                            spnCategoria.setId(cursor.getInt(2));
                            edtDireccion.setEnabled(true);
                            edtDireccion.setText(cursor.getString(3));
                            edtCiudad.setEnabled(true);
                            edtCiudad.setText(cursor.getString(4));
                            edtComuna.setEnabled(true);
                            edtComuna.setText(cursor.getString(5));
                            edtLatitud.setEnabled(true);
                            edtLatitud.setText(cursor.getString(6));
                            edtLongitud.setEnabled(true);
                            edtLongitud.setText(cursor.getString(7));
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_proveedor_modificar, container, false);
    }

    private void modificar() {

        if (!spnRut.getSelectedItem().toString().equals("Seleccione")) {


            if (!edtRazonSoci.getText().toString().trim().equals("")&& !spnCategoria.getSelectedItem().toString().trim().equals("")&& !edtDireccion.getText().toString().trim().equals("")&& !edtCiudad.getText().toString().trim().equals("") &&
                    !edtComuna.getText().toString().trim().equals("")&& !edtLatitud.getText().toString().trim().equals("")&& !edtLongitud.getText().toString().trim().equals("")) {
                BD conexion = new BD(getContext(), "BD_Finanzas", null, 1);

                SQLiteDatabase bd = conexion.getReadableDatabase();

                String[] parametros = {spnRut.getSelectedItem().toString()};
                try {
                    ContentValues campos = new ContentValues();
                    campos.put("razon_social", edtRazonSoci.getText().toString().trim());
                    campos.put("categoria", spnCategoria.getSelectedItem().toString().trim());
                    campos.put("direccion", edtDireccion.getText().toString().trim());
                    campos.put("ciudad", edtCiudad.getText().toString().trim());
                    campos.put("comuna", edtComuna.getText().toString().trim());
                    campos.put("latitud", edtLatitud.getText().toString().trim());
                    campos.put("longitud", edtLongitud.getText().toString().trim());

                    long i = bd.update("PROVEEDORES", campos, "rut = ?", parametros);
                    if (i > 0){
                        Toast.makeText(getContext(), "Proveedor Modificado", Toast.LENGTH_SHORT).show();
                        limpiar();
                    } else
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

                    bd.close();
                } catch (Exception ex) {
                    Toast.makeText(getContext(), ex.toString(), Toast.LENGTH_SHORT).show();
                }
            } else
                Toast.makeText(getContext(), "No se Permiten Campos vacios", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getContext(), "Elija un Proveedor", Toast.LENGTH_SHORT).show();
    }

    private void limpiar() {

        edtRazonSoci.setText("");
        edtRazonSoci.setEnabled(false);
        spnCategoria.setSelection(1);
        spnCategoria.setEnabled(false);
        edtDireccion.setText("");
        edtDireccion.setEnabled(false);
        edtCiudad.setText("");
        edtCiudad.setEnabled(false);
        edtComuna.setText("");
        edtComuna.setEnabled(false);
        edtLatitud.setText("");
        edtLatitud.setEnabled(false);
        edtLongitud.setText("");
        edtLongitud.setEnabled(false);
        cargarDatosSpinnerRut();
        cargarDatosSpinnerCategoria();
    }

    private void cargarDatosSpinnerRut() {

        BD conexion = new BD(getContext(), "BD_Finanzas", null, 1);
        SQLiteDatabase database = conexion.getReadableDatabase();
        ArrayList<String> listaClientes = new ArrayList<String>();
        listaClientes.add("Seleccione");

        if (database != null) {
            Cursor cursor = database.rawQuery("SELECT rut FROM PROVEEDORES", null);
            if (cursor.moveToFirst()) {
                do {
                    listaClientes.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        }
        spnRut.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listaClientes));
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

}
