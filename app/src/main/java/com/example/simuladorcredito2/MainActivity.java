package com.example.simuladorcredito2;

import static java.lang.Double.parseDouble;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //Arreglo que alimentará el adaptador para el Spinner
    String[] tprestamos = {"Vivienda", "Educación", "Libre Inversión"};
    String presSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Instanciar y recferenciar los IDs del archivo xml
        EditText nombre = findViewById(R.id.etnombre);
        EditText fecha = findViewById(R.id.etfecha);
        EditText monto = findViewById(R.id.etmonto);
        Spinner tipoCredito = findViewById(R.id.sptipo);
        RadioButton rb12 = findViewById(R.id.rb12);
        RadioButton rb24 = findViewById(R.id.rb24);
        RadioButton rb36 = findViewById(R.id.rb36);
        TextView valorCuota = findViewById(R.id.tvcuota);
        TextView totalDeuda = findViewById(R.id.tvdeuda);
        ImageButton calcular = findViewById(R.id.btncalcular);
        ImageButton limpiar = findViewById(R.id.btnclear);

        //Crear el adaptador que contendrá el arreglo tprestamos
        ArrayAdapter adpTprestamo = new ArrayAdapter(this, android.R.layout.simple_list_item_checked,tprestamos);

        //Asignar el adaptador al Spinner
        tipoCredito.setAdapter(adpTprestamo);

        //Chequear el tipo de crédito
        tipoCredito.setOnItemSelectedListener(this);

        //Evento click del boton limpiar
        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre.setText("");
                fecha.setText("");
                monto.setText("");
                valorCuota.setText("");
                totalDeuda.setText("");
                nombre.requestFocus(); //Se envia el foco al nombre
            }
        });

        //Evento click del botón calcular
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nombre.getText().toString().isEmpty()){
                    if (!fecha.getText().toString().isEmpty()){
                        if (!monto.getText().toString().isEmpty()){
                            if (parseDouble(monto.getText().toString()) >= 1000000 && parseDouble(monto.getText().toString()) <= 100000000){
                                //Chequear el tipo de crédito seleccionado
                                double interes = 0;
                                switch (presSelect){
                                    case "Vivienda":
                                        interes = 1.5/100;
                                        break;
                                    case "Educación":
                                        interes = 1/100;
                                        break;
                                    case "Libre Inversión":
                                        interes = 2/100;
                                        break;
                                }
                                double xmonto = parseDouble(monto.getText().toString());
                                double ncuotas = 0;
                                if (rb12.isChecked()){
                                    ncuotas = 12;
                                }
                                if (rb24.isChecked()){
                                    ncuotas = 24;
                                }
                                if (rb36.isChecked()){
                                    ncuotas = 36;
                                }
                                double xtotalDeuda = xmonto + (xmonto * ncuotas * interes);
                                double xvalorCuota = xmonto / ncuotas;
                                DecimalFormat oNumero = new DecimalFormat("###,###,###,###.#");
                                valorCuota.setText(String.valueOf(oNumero.format(xvalorCuota)));
                                totalDeuda.setText(String.valueOf(oNumero.format(xtotalDeuda)));
                            } else {
                                Toast.makeText(getApplicationContext(), "Monto debe estar entre 1 y 100 millones", Toast.LENGTH_SHORT).show();
                            }

                        } else{
                            Toast.makeText(getApplicationContext(), "Monto obligatorio", Toast.LENGTH_SHORT).show();
                        }

                    } else{
                        Toast.makeText(getApplicationContext(), "Fecha obligatoria", Toast.LENGTH_SHORT).show();
                    }

                } else{
                    Toast.makeText(getApplicationContext(), "Nombre obligatorio", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        presSelect = tprestamos[position]; //Tomar el valor de la opcion seleccionado Tipo de Prestamo
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}