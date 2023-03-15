package com.example.proyectoilulu2_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.proyectoilulu2_0.Encriptación.Sha1;
import com.example.proyectoilulu2_0.Json.Info;
import com.example.proyectoilulu2_0.Json.Json;
import com.example.proyectoilulu2_0.BD.BD_info;
import com.example.proyectoilulu2_0.BD.BD_api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MyPaginaWeb";
    EditText Name, disc, userName, Mail, Age, Number, Password, location;
    Button btn_registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn_registro = (Button)findViewById(R.id.btn_registro);
        btn_registro.setOnClickListener(this);
    }



    public void Volver (View v){
        Intent intent = new Intent (Register.this, Login.class);
        startActivity( intent );
    }

    @Override
    public void onClick(View view) {
        Name = (EditText) findViewById(R.id.editTextRName);
        location = (EditText) findViewById(R.id.editTextRfirstName);
        disc = (EditText) findViewById(R.id.editTextRlastName);
        userName = (EditText) findViewById(R.id.editTextRuserName);
        Mail = (EditText) findViewById(R.id.editTextRMail);
        Age = (EditText) findViewById(R.id.editTextRAge);
        Number = (EditText) findViewById(R.id.editTextRNumber);
        RadioButton Gender1 = (RadioButton) findViewById(R.id.radioButtonEL1);
        RadioButton Gender2 = (RadioButton) findViewById(R.id.radioButtonEL2);
        RadioButton Type1 = (RadioButton) findViewById(R.id.radioButtonRType1);
        RadioButton Type2 = (RadioButton) findViewById(R.id.radioButtonRType2);
        Password = (EditText) findViewById(R.id.editTextRPassword);

        String Name_ = Name.getText().toString();
        String UName = userName.getText().toString();
        String location_ = location.getText().toString();
        String disc_ = disc.getText().toString();
        String Mail_ = Mail.getText().toString();
        String Age_ = Age.getText().toString();
        String Number_ = Number.getText().toString();
        String Password_ = Password.getText().toString();

        String mensaje = "";

        if(Name_.isEmpty() && UName.isEmpty())
        {
            mensaje = "Falta un Parametro";
        }
        else {
            boolean TipoCorreo = false;
            String Correo = "";
            for(int x = 0 ; x < Mail_.length(); x++){
                if(Mail_.charAt(x) == '@'){
                    for(int y = x ; y < Mail_.length(); y++){
                        Correo = Correo + Mail_.charAt(y);
                    }
                    if("@gmail.com".equals(Correo) || "@hotmail.com".equals(Correo) || "@outlook.com".equals(Correo)){
                        TipoCorreo = true;
                    }
                    break;
                }
            }
            if(Name_.length() > 22 || location_.length() > 15 || disc_.length() > 15 || UName.length() > 20 ||
                    TipoCorreo == false || Mail_.length() > 25 || Age_.length() > 2 || Number_.length() != 10 || Password_.length() > 30){
                mensaje = "Parametro Erroneo";
                if(Name_.length() > 22){mensaje = "Nombre Muy Largo";}
                if(location_.length() > 15){mensaje = "Locazión Muy Larga";}
                if(UName.length() > 20){mensaje = "Nombre de Usuario Muy Largo";}
                if(TipoCorreo == false){mensaje = "Correo Invalido, Intente con los dominios @gmail.com, @hotmail.com, @outlook.com";}
                if(Mail_.length() > 25){mensaje = "Correo Muy Largo";}
                if(Age_.length() > 2){mensaje = "Edad Invalida, Intente con una edad mas corta";}
                if(Number_.length() != 10){mensaje = "Numero Invalido, Intente con un numero de 10 digitos";}
                if(Password_.length() > 30){mensaje = "Contraseña Muy Larga";}
            }else{
                try {

                    /*Sha1 digest = new Sha1();
                    byte[] txtByte = digest.createSha1(UName + Password_.toString());
                    String Sha1Password = digest.bytesToHex(txtByte);*/

                    String ValorName = Name_;
                    String ValorfirstName = location_;
                    String ValorlastName = disc_;
                    String ValoruserName = UName;
                    String ValorMail = Mail_;
                    int ValorAge = Integer.parseInt(Age_);
                    int ValorNumber = Integer.parseInt(Number_);
                    boolean ValorGender = Gender1.isChecked();
                    boolean ValorType = Type1.isChecked();
                    String ValorPassword = Password_;

                    Json json = new Json();
                    String textoJson = json.crearJson(ValorName, ValorfirstName, ValorlastName, ValoruserName, ValorMail,
                            ValorAge, ValorNumber, ValorGender, ValorType, ValorPassword);

                    boolean BucleArchivo = true;
                    int x = 1;
                    while (BucleArchivo) {
                        BD_info dbInfo = new BD_info(Register.this);
                        if (dbInfo.comprobarInfo(x)) {
                            String completoTexto = dbInfo.verInfo(x);

                            Info datos = json.leerJson(completoTexto);
                            String ValoruserName2 = datos.getUserName();
                            String ValorMail2 = datos.getMail();
                            long ValorNumber2 = datos.getNumber();

                            if (ValoruserName.equals(ValoruserName2) || ValorMail.equals(ValorMail2) || ValorNumber == ValorNumber2) {
                                if (ValorMail.equals(ValorMail2)) {
                                    mensaje = "Correo Ya Registrado";
                                }
                                if (ValorNumber == ValorNumber2) {
                                    mensaje = "Numero Ya Registrado";
                                }
                                if (ValoruserName.equals(ValoruserName2)) {
                                    mensaje = "Usuario Ya Existente";
                                }
                                BucleArchivo = false;
                            } else {
                                x = x + 1;
                            }
                        } else {
                            long status = dbInfo.insertarInfo(x, textoJson);
                            if (status > 0) {
                                mensaje = "Usuario Registrado";
                                Name.setText("");
                                location.setText("");
                                disc.setText("");
                                userName.setText("");
                                Mail.setText("");
                                Age.setText("");
                                Number.setText("");
                                Gender1.setChecked(false);
                                Gender2.setChecked(false);
                                Type1.setChecked(false);
                                Type2.setChecked(false);
                                Password.setText("");
                                BucleArchivo = false;
                            } else {
                                mensaje = "Error al Hacer Registro, again";
                            }
                        }
                    }

                }catch (Exception e){
                    mensaje= "Error 2";
                }
            }
        }
        Toast.makeText(Register.this, mensaje, Toast.LENGTH_SHORT).show();

    }
}