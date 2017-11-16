package cl.duoc.prueba2.prueba2finanzas;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class clienteAgregar extends Fragment {

    EditText edtRut,edtNombre,edtFechaNacimiento,edtDireccion,edtCiudad,edtComuna,edtLatitud,edtLongitud;
    Button btnRegistrar;

    public clienteAgregar() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        edtRut = (EditText) getView().findViewById(R.id.edRutAgregarCliente);
        edtNombre = (EditText) getView().findViewById(R.id.edNombreAgregarCliente);
        edtFechaNacimiento = (EditText) getView().findViewById(R.id.dtFechaNacimientoAgregarCliente);
        edtDireccion = (EditText) getView().findViewById(R.id.edDireccionAgregarCliente);
        edtCiudad = (EditText) getView().findViewById(R.id.edCiudadAgregarCliente);
        edtComuna = (EditText) getView().findViewById(R.id.edComunaAgregarCliente);
        edtLatitud = (EditText) getView().findViewById(R.id.edLatitudAgregarCliente);
        edtLongitud = (EditText) getView().findViewById(R.id.edLongitudAgregarCliente);

        btnRegistrar = (Button) getView().findViewById(R.id.btnAgregarCliente);
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
        return inflater.inflate(R.layout.activity_cliente_agregar, container, false);
    }

    public void insertar(){

        String rut = edtRut.getText().toString();
        String nombre = edtNombre.getText().toString();
        String fechaNac = edtFechaNacimiento.getText().toString();
        String direccion = edtDireccion.getText().toString();
        String ciudad = edtCiudad.getText().toString();
        String comuna = edtComuna.getText().toString();
        String latitud = edtLatitud.getText().toString();
        String longitud = edtLongitud.getText().toString();


        if (!rut.trim().equals("") && !nombre.trim().equals("")&& !fechaNac.trim().equals("")&& !direccion.trim().equals("")&&
                !ciudad.trim().equals("")&& !comuna.trim().equals("")&& !latitud.trim().equals("")&& !longitud.trim().equals("")) {

            BD conexion = new BD (getContext(), "BD_Finanzas",null,1);

            SQLiteDatabase database = conexion.getWritableDatabase();

            if (database != null) {
                ContentValues parametros = new ContentValues();
                parametros.put("rut", rut.trim());
                parametros.put("nombre", nombre.trim());
                parametros.put("fecha_nacimiento", fechaNac.trim());
                parametros.put("direccion", direccion.trim());
                parametros.put("ciudad", ciudad.trim());
                parametros.put("comuna", comuna.trim());
                parametros.put("latitud", latitud.trim());
                parametros.put("longitud", longitud.trim());
                long i = database.insert("CLIENTES", null, parametros);
                if (i > 0){
                    limpiar();
                    Toast.makeText(getContext(), "Cliente Insertado", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                database.close();
            }
        }else
            Toast.makeText(getContext(),"no se permiten campos vacios",Toast.LENGTH_SHORT).show();

    }

    public void limpiar(){
        edtRut.setText("");
        edtNombre.setText("");
        edtFechaNacimiento.setText("");
        edtDireccion.setText("");
        edtCiudad.setText("");
        edtComuna.setText("");
        edtLatitud.setText("");
        edtLongitud.setText("");
    }
}
