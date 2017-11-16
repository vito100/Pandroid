package cl.duoc.prueba2.prueba2finanzas;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
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

public class categoriaModificar extends Fragment {

    EditText edtCategoria;
    Spinner spnId;
    Button btnModificar;

    public categoriaModificar() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        spnId = (Spinner) getView().findViewById(R.id.spnIdCategoriaModificar);
        edtCategoria = (EditText) getView().findViewById(R.id.edtCategoriaModificarCategoria);
        limpiar();

        btnModificar = (Button) getView().findViewById(R.id.btnModificarModificarCategoria);
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificar();

            }
        });

        spnId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if (spnId.getSelectedItem().equals("Seleccione")) {
                    limpiar();
                }
                if (!spnId.getSelectedItem().equals("Seleccione")) {
                    BD conexion = new BD(getContext(), "BD_Finanzas", null, 1);
                    SQLiteDatabase database = conexion.getReadableDatabase();

                    if (database != null) {
                        Cursor cursor = database.rawQuery("SELECT * FROM CATEGORIAS where id=" + spnId.getSelectedItem().toString(), null);
                        if (cursor.moveToFirst()) {

                            edtCategoria.setEnabled(true);
                            edtCategoria.setText(cursor.getString(1));
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
        return inflater.inflate(R.layout.activity_categoria_modificar, container, false);
    }

    private void modificar() {

        if (!spnId.getSelectedItem().toString().equals("Seleccione")) {


            if (!edtCategoria.getText().toString().trim().equals("")) {
                BD conexion = new BD(getContext(), "BD_Finanzas", null, 1);

                SQLiteDatabase bd = conexion.getReadableDatabase();

                String[] parametros = {spnId.getSelectedItem().toString()};
                try {
                    ContentValues campos = new ContentValues();
                    campos.put("categoria", edtCategoria.getText().toString().trim());

                    long i = bd.update("CATEGORIAS", campos, "id = ?", parametros);
                    if (i > 0){
                        Toast.makeText(getContext(), "Categoria Modificada", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), "Elije Categoria", Toast.LENGTH_SHORT).show();
    }

    private void limpiar() {

        edtCategoria.setText("");
        edtCategoria.setEnabled(false);
        cargarDatosSpinner();
    }

    private void cargarDatosSpinner() {

        BD conexion = new BD(getContext(), "BD_Finanzas", null, 1);
        SQLiteDatabase database = conexion.getReadableDatabase();
        ArrayList<String> listaCategorias = new ArrayList<String>();
        listaCategorias.add("Seleccione");

        if (database != null) {
            Cursor cursor = database.rawQuery("SELECT id FROM CATEGORIAS", null);
            if (cursor.moveToFirst()) {
                do {
                    listaCategorias.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        }
        spnId.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listaCategorias));
    }
}
