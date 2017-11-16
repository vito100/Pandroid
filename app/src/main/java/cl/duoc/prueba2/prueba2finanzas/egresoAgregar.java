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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class egresoAgregar extends Fragment {

    EditText edtId,edtMonto;
    Spinner spnCliente,spnCategoria,spnFormaPago;
    Button btnRegistrar;

    public egresoAgregar() {

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

    private void cargarDatosSpinnerCliente() {

        BD conexion = new BD(getContext(), "BD_Finanzas", null, 1);
        SQLiteDatabase database = conexion.getReadableDatabase();
        ArrayList<String> listaCliente = new ArrayList<String>();
        listaCliente.add("Seleccione Cliente");

        if (database != null) {
            Cursor cursor = database.rawQuery("SELECT rut FROM CLIENTES", null);
            if (cursor.moveToFirst()) {
                do {
                    listaCliente.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        }
        spnCliente.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listaCliente));
    }

    private void cargarDatosSpinnerFormaPago() {

        ArrayList<String> listaForma = new ArrayList<String>();
        listaForma.add("Tarjeta de Credito");
        listaForma.add("Cuenta Corriente");
        listaForma.add("Efectivo");
        spnFormaPago.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listaForma));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        edtId = (EditText) getView().findViewById(R.id.etIdEgresoAgregar);
        edtMonto = (EditText) getView().findViewById(R.id.etMontoEgresoAgregar);
        spnCliente = (Spinner) getView().findViewById(R.id.spnClienteEgresoAgregar);
        spnCategoria = (Spinner) getView().findViewById(R.id.spnCategoriaEgresoAgregar);
        spnFormaPago = (Spinner) getView().findViewById(R.id.spnFormaEgresoAgregar);
        limpiar();
        btnRegistrar = (Button) getView().findViewById(R.id.btnAgregarEgreso);
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
        return inflater.inflate(R.layout.activity_egreso_agregar, container, false);
    }

    public void insertar(){

        String id = edtId.getText().toString();
        String monto = edtMonto.getText().toString();
        String cliente = spnCliente.getSelectedItem().toString();
        String categoria = spnCategoria.getSelectedItem().toString();
        String forma_pago = spnFormaPago.getSelectedItem().toString();

        if (!id.trim().equals("") && !monto.trim().equals("") && !cliente.trim().equals("")
                && !categoria.trim().equals("") && !forma_pago.trim().equals("")) {

            BD conexion = new BD (getContext(), "BD_Finanzas",null,1);

            SQLiteDatabase database = conexion.getWritableDatabase();

            if (database != null) {
                ContentValues parametros = new ContentValues();
                parametros.put("id", id.trim());
                parametros.put("monto", categoria.trim());
                parametros.put("cliente", categoria.trim());
                parametros.put("categoria", categoria.trim());
                parametros.put("forma_pago", categoria.trim());
                long i = database.insert("EGRESOS", null, parametros);
                if (i > 0){
                    limpiar();
                    Toast.makeText(getContext(), "Egreso Insertado", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                database.close();
            }
        }else
            Toast.makeText(getContext(),"no se permiten campos vacios",Toast.LENGTH_SHORT).show();

    }

    public void limpiar(){
        edtId.setText("");
        edtMonto.setText("");
        cargarDatosSpinnerCliente();
        cargarDatosSpinnerCategoria();
        cargarDatosSpinnerFormaPago();
    }
}
