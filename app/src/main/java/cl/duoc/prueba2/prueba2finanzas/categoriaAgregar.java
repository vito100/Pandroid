package cl.duoc.prueba2.prueba2finanzas;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class categoriaAgregar extends Fragment {

    EditText edtId,edtCategoria;
    Button btnRegistrar;

    public categoriaAgregar() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        edtId = (EditText) getView().findViewById(R.id.etIdCategoriaAgregar);
        edtCategoria = (EditText) getView().findViewById(R.id.etCategoriaCategoriaAgregar);

        btnRegistrar = (Button) getView().findViewById(R.id.btnAgregarCategoriaAgregar);
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
        return inflater.inflate(R.layout.activity_categoria_agregar, container, false);
    }

    public void insertar(){

        String id = edtId.getText().toString();
        String categoria = edtCategoria.getText().toString();

        if (!id.trim().equals("") && !categoria.trim().equals("")) {

            BD conexion = new BD (getContext(), "BD_Finanzas",null,1);

            SQLiteDatabase database = conexion.getWritableDatabase();

            if (database != null) {
                ContentValues parametros = new ContentValues();
                parametros.put("id", id.trim());
                parametros.put("categoria", categoria.trim());
                long i = database.insert("CATEGORIAS", null, parametros);
                if (i > 0){
                    limpiar();
                    Toast.makeText(getContext(), "Categoria Insertada", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                database.close();
            }
        }else
            Toast.makeText(getContext(),"no se permiten campos vacios",Toast.LENGTH_SHORT).show();

    }

    public void limpiar(){
        edtId.setText("");
        edtCategoria.setText("");
    }
}
