package com.example.proyectoilulu2_0;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoilulu2_0.BD.BD_info;
import com.example.proyectoilulu2_0.Encriptación.Sha1;
import com.example.proyectoilulu2_0.Json.Info;
import com.example.proyectoilulu2_0.Json.Json;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void IniciarSesion (View v){
        EditText userName = (EditText) findViewById(R.id.usserpas);
        EditText Password = (EditText) findViewById(R.id.passwrdpas);

        String mensaje = "";

        if("".equals(userName.getText().toString()) || "".equals(Password.getText().toString()))
        {
            mensaje = "Falta un Parametro";
        }else{
            if(userName.length() > 20 || Password.length() > 30){
                mensaje = "Parametro Erroneo";
                if(userName.length() > 20){mensaje = "Nombre de Usuario Muy Largo";}
                if(Password.length() > 30){mensaje = "Contraseña Muy Larga";}
            }else {
                try {

                    Sha1 digest = new Sha1();
                    byte[] txtByte = digest.createSha1(userName.getText().toString() + Password.getText().toString());
                    String Sha1Password1 = digest.bytesToHex(txtByte);

                    Json json = new Json();

                    boolean BucleArchivo = true;
                    int x = 1;
                    int numArchivo = 0;
                    while (BucleArchivo) {
                        BD_info dbInfo = new BD_info(Login.this);
                        if (dbInfo.comprobarInfo(x)) {
                            String completoTexto = dbInfo.verInfo(x);

                            Info datos = json.leerJson(completoTexto);
                            String Sha1Password2 = datos.getPassword();

                            if (Sha1Password1.equals(Sha1Password2)) {
                                mensaje = "Usuario Encontrado";
                                numArchivo = x;
                                BucleArchivo = false;
                            } else {
                                x = x + 1;
                            }
                        }else{
                            mensaje = "Usuario no Encontrado";
                            BucleArchivo = false;
                        }
                    }

                    if("Usuario Encontrado".equals(mensaje)){
                        Toast.makeText(Login.this, mensaje, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, Website.class);
                        intent.putExtra("numArchivo", numArchivo);
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    mensaje = "Error en el Archivo";
                }
            }
        }
        Toast.makeText(Login.this, mensaje, Toast.LENGTH_SHORT).show();
    }

    public void Registrarse (View v){
        Intent intent = new Intent (Login.this, Register.class);
        startActivity( intent );
    }

    public void OlvidarContra (View v){
        Intent intent = new Intent (Login.this, Forgotpass.class);
        startActivity( intent );
    }
}