package cl.duoc.prueba2.prueba2finanzas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by LC1300XXXX on 08/11/2017.
 */

public class BD extends SQLiteOpenHelper{

    public BD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i(this.getClass().toString(), "Creando base de datos");

        //Tablas

        db.execSQL( "CREATE TABLE CLIENTES(" +
                " rut TEXT PRIMARY KEY," +
                " nombre TEXT NOT NULL, " +
                " fecha_nacimiento TEXT NOT NULL, " +
                " direccion TEXT NOT NULL," +
                " ciudad TEXT NOT NULL," +
                " comuna TEXT NOT NULL," +
                " latitud TEXT," +
                " longitud TEXT)" );

        Log.i(this.getClass().toString(), "Tabla CLIENTES creada");

        db.execSQL( "CREATE TABLE CATEGORIAS(" +
                " id TEXT PRIMARY KEY," +
                " categoria TEXT NOT NULL)" );

        Log.i(this.getClass().toString(), "Tabla CATEGORIAS creada");

        db.execSQL("CREATE TABLE PROVEEDORES(" +
                " rut TEXT PRIMARY KEY," +
                " razon_social TEXT NOT NULL, " +
                " categoria TEXT NOT NULL, " +
                " direccion TEXT NOT NULL," +
                " ciudad TEXT NOT NULL," +
                " comuna TEXT NOT NULL," +
                " latitud TEXT," +
                " longitud TEXT, " +
                " FOREIGN KEY(categoria) REFERENCES CATEGORIAS(id))" );

        Log.i(this.getClass().toString(), "Tabla PROVEEDORES creada");

        db.execSQL( "CREATE TABLE INGRESOS(" +
                " id TEXT PRIMARY KEY," +
                " monto TEXT NOT NULL, " +
                " cliente TEXT NOT NULL, " +
                " categoria TEXT NOT NULL," +
                " forma_pago TEXT NOT NULL, " +
                " FOREIGN KEY(categoria) REFERENCES CATEGORIAS(id), " +
                " FOREIGN KEY(cliente) REFERENCES CLIENTES(rut))" );

        Log.i(this.getClass().toString(), "Tabla INGRESOS creada");

        db.execSQL( "CREATE TABLE EGRESOS(" +
                " id TEXT PRIMARY KEY," +
                " monto TEXT NOT NULL, " +
                " cliente TEXT NOT NULL, " +
                " categoria TEXT NOT NULL," +
                " forma_pago TEXT NOT NULL," +
                " FOREIGN KEY(categoria) REFERENCES CATEGORIAS(id), " +
                " FOREIGN KEY(cliente) REFERENCES CLIENTES(rut))" );

        Log.i(this.getClass().toString(), "Tabla EGRESOS creada");

        //Datos

        db.execSQL("INSERT INTO CLIENTES(rut, nombre, fecha_nacimiento, direccion, ciudad, comuna) VALUES('11111111-1','Pepe','15/02/1988','Marin #014','Santiago','Puente Alto')");
        db.execSQL("INSERT INTO CLIENTES(rut, nombre, fecha_nacimiento, direccion, ciudad, comuna) VALUES('22222222-2','Alberto','26/07/1995','Concha y Toro #666','Santiago','La Pintana')");
        db.execSQL("INSERT INTO CLIENTES(rut, nombre, fecha_nacimiento, direccion, ciudad, comuna) VALUES('33333333-3','Llajaira','16/09/1998','Nose #123','Santiago','San Beca')");

        Log.i(this.getClass().toString(), "Datos iniciales CLIENTES insertados");

        db.execSQL("INSERT INTO CATEGORIAS(id, categoria) VALUES('1','Prostibulo')");
        db.execSQL("INSERT INTO CATEGORIAS(id, categoria) VALUES('2','Dulceria')");
        db.execSQL("INSERT INTO CATEGORIAS(id, categoria) VALUES('3','Banco')");

        Log.i(this.getClass().toString(), "Datos iniciales CATEGORIAS insertados");

        db.execSQL("INSERT INTO PROVEEDORES(rut, razon_social, categoria, direccion, ciudad, comuna) VALUES('11111111-1','Chicholina S.A','Prostibulo','Marin #014','Santiago','Puente Alto')");
        db.execSQL("INSERT INTO PROVEEDORES(rut, razon_social, categoria, direccion, ciudad, comuna) VALUES('22222222-2','Popin S.A','Dulceria','Concha y Toro #666','Santiago','La Pintana')");
        db.execSQL("INSERT INTO PROVEEDORES(rut, razon_social, categoria, direccion, ciudad, comuna) VALUES('33333333-3','Pepe S.A','Banco','Nose #123','Santiago','San Beca')");

        Log.i(this.getClass().toString(), "Datos iniciales PROVEEDORES insertados");

        db.execSQL("INSERT INTO INGRESOS(id, monto, cliente, categoria, forma_pago) VALUES('1','10.000','11111111-1','1','Efectivo')");
        db.execSQL("INSERT INTO INGRESOS(id, monto, cliente, categoria, forma_pago) VALUES('2','200.000','22222222-2','2','Tarjeta')");
        db.execSQL("INSERT INTO INGRESOS(id, monto, cliente, categoria, forma_pago) VALUES('3','1.000','33333333-3','3','Efectivo')");

        Log.i(this.getClass().toString(), "Datos iniciales INGRESOS insertados");

        db.execSQL("INSERT INTO EGRESOS(id, monto, cliente, categoria, forma_pago) VALUES('1','10.000','11111111-1','1','Efectivo')");
        db.execSQL("INSERT INTO EGRESOS(id, monto, cliente, categoria, forma_pago) VALUES('2','200.000','22222222-2','2','Tarjeta')");
        db.execSQL("INSERT INTO EGRESOS(id, monto, cliente, categoria, forma_pago) VALUES('3','1.000','33333333-3','3','Efectivo')");

        Log.i(this.getClass().toString(), "Datos iniciales EGRESOS insertados");

        Log.i(this.getClass().toString(), "Base de datos creada");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
