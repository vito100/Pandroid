package cl.duoc.prueba2.prueba2finanzas;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.Date;

public class clienteModificar extends Fragment {

    EditText edtNombre,edtFechaNacimiento,edtDireccion,edtCiudad,edtComuna,edtLatitud,edtLongitud;
    Spinner spnRut;
    Button btnModificar;

    public clienteModificar() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        spnRut = (Spinner) getView().findViewById(R.id.spRutModificarCliente);
        edtNombre = (EditText) getView().findViewById(R.id.edNombreModificarCliente);
        edtFechaNacimiento = (EditText) getView().findViewById(R.id.dtFechaNacimientoModificarCliente);
        edtDireccion = (EditText) getView().findViewById(R.id.edDireccionModificarCliente);
        edtCiudad = (EditText) getView().findViewById(R.id.edCiudadModificarCliente);
        edtComuna = (EditText) getView().findViewById(R.id.edComunaModificarCliente);
        edtLatitud = (EditText) getView().findViewById(R.id.edLatitudModificarCliente);
        edtLongitud = (EditText) getView().findViewById(R.id.edLongitudModificarCliente);
        limpiar();

        btnModificar = (Button) getView().findViewById(R.id.btnModificarCliente);
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
                    limpiar();
                }
                if (!spnRut.getSelectedItem().equals("Seleccione")) {
                    BD conexion = new BD(getContext(), "BD_Finanzas", null, 1);
                    SQLiteDatabase database = conexion.getReadableDatabase();

                    if (database != null) {
                        Cursor cursor = database.rawQuery("SELECT * FROM CLIENTES where rut=?", new String[]{spnRut.getSelectedItem().toString()});
                        if (cursor.moveToFirst()) {

                            edtNombre.setEnabled(true);
                            edtNombre.setText(cursor.getString(1));
                            edtFechaNacimiento.setEnabled(true);
                            edtFechaNacimiento.setText(cursor.getString(2));
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
        return inflater.inflate(R.layout.activity_cliente_modificar, container, false);
    }

    private void modificar() {

        if (!spnRut.getSelectedItem().toString().equals("Seleccione")) {


            if (!edtNombre.getText().toString().trim().equals("")&& !edtFechaNacimiento.getText().toString().trim().equals("")&& !edtDireccion.getText().toString().trim().equals("")&& !edtCiudad.getText().toString().trim().equals("") &&
                    !edtComuna.getText().toString().trim().equals("")&& !edtLatitud.getText().toString().trim().equals("")&& !edtLongitud.getText().toString().trim().equals("")) {
                BD conexion = new BD(getContext(), "BD_Finanzas", null, 1);

                SQLiteDatabase bd = conexion.getReadableDatabase();

                String[] parametros = {spnRut.getSelectedItem().toString()};
                try {
                    ContentValues campos = new ContentValues();
                    campos.put("nombre", edtNombre.getText().toString().trim());
                    campos.put("fecha_nacimiento", edtFechaNacimiento.getText().toString().trim());
                    campos.put("direccion", edtDireccion.getText().toString().trim());
                    campos.put("ciudad", edtCiudad.getText().toString().trim());
                    campos.put("comuna", edtComuna.getText().toString().trim());
                    campos.put("latitud", edtLatitud.getText().toString().trim());
                    campos.put("longitud", edtLongitud.getText().toString().trim());

                    long i = bd.update("CLIENTES", campos, "rut = ?", parametros);
                    if (i > 0){
                        Toast.makeText(getContext(), "Cliente Modificado", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), "Elije un Cliente", Toast.LENGTH_SHORT).show();
    }

    private void limpiar() {

        edtNombre.setText("");
        edtNombre.setEnabled(false);
        edtFechaNacimiento.setText("");
        edtFechaNacimiento.setEnabled(false);
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
        cargarDatosSpinner();
    }

    private void cargarDatosSpinner() {

        BD conexion = new BD(getContext(), "BD_Finanzas", null, 1);
        SQLiteDatabase database = conexion.getReadableDatabase();
        ArrayList<String> listaClientes = new ArrayList<String>();
        listaClientes.add("Seleccione");

        if (database != null) {
            Cursor cursor = database.rawQuery("SELECT rut FROM CLIENTES", null);
            if (cursor.moveToFirst()) {
                do {
                    listaClientes.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        }
        spnRut.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listaClientes));
    }
}
