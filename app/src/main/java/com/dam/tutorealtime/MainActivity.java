package com.dam.tutorealtime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    /**
     * Appel à Realtime Database
     **/
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    /**
     * Rechercher de la référence message
     **/
    DatabaseReference myRef = database.getReference("message");

    /**
     * Définition des variables globales
     **/
    private Button btn_Message;
    private EditText et_Message;
    private TextView tv_Message;

    /**
     * Lien design et code
     **/
    private void initUI() {
        btn_Message = (Button) findViewById(R.id.btn_send);
        et_Message = (EditText) findViewById(R.id.et_message);
        tv_Message = (TextView)findViewById(R.id.tv_message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /** Appel des méthodes **/
        initUI();
        sendMessage();
        readMessage();
    }

    /**
     * Méthode pour ajouter un listener sur le bouton envoyer
     **/
    public void sendMessage() {
        btn_Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** Affectation de la valeur de l'editText à message **/
                myRef.setValue(et_Message.getText().toString());
            }
        });
    }

    /**
     * Méthode pour lire les données de la base
     **/
    private String TAG = "TAG";
    public void readMessage() {
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                /** Affectation de message au TextView **/
                tv_Message.setText(value);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}