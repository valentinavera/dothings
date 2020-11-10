package edu.unicauca.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.unicauca.main.patterns.observer.Observer;
import edu.unicauca.main.persistence.models.UserModel;
import edu.unicauca.main.session.SimpleSessionManager;

public class LoginActivity extends AppCompatActivity implements Observer {
    private EditText etEmail;
    private EditText etPassword;
    private Button btLogin;
    private UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        SimpleSessionManager.init(this);
        setContentView (R.layout.activity_login);
        etEmail = findViewById (R.id.editTextTextEmailAddress);
        etPassword = findViewById (R.id.editTextTextPassword);
        btLogin= findViewById (R.id.buttonLogin);
        btLogin.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText ().toString ();
                String password = etPassword.getText ().toString ();
                SimpleSessionManager.login (email, password,true, new Observer () {
                    @Override
                    public void notify(Object succesfull) {
                        if((boolean)succesfull){
                            final Toast tag = Toast.makeText(getApplicationContext (),"Sincronización exitosa",Toast.LENGTH_SHORT);
                            tag.show();

                        }else{
                            final Toast tag = Toast.makeText(getApplicationContext (),"Error al sincronizar datos, verifique los datos",Toast.LENGTH_SHORT);
                            tag.show();
                            Intent login = new Intent (getApplicationContext (), LoginActivity.class);
                            startActivity (login);
                        }
                    }

                });
                Intent menuActivity= new Intent(getApplicationContext (), MenuActivity.class);
                startActivity(menuActivity);

            }
        });

    }

    @Override
    public void notify(Object observed) {

    }
}