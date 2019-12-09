package com.example.appoftheyear2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Addgame extends AppCompatActivity {

    private Button addGameButon;
    private EditText nameInput;
    private EditText genreInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgame);

        addGameButon = findViewById(R.id.addGameButton);
        nameInput = findViewById(R.id.editGameName);
        genreInput = findViewById(R.id.editGenre);


        addGameButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nameInput.getText().toString().equals("") && !genreInput.getText().toString().equals("")) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("nameInput", nameInput.getText().toString());
                    returnIntent.putExtra("genreInput", genreInput.getText().toString());
                    setResult(Activity.RESULT_OK, returnIntent);

                    finish();
                }
                else {
                    Toast.makeText(getBaseContext(), "Please Fill Out All Fields!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
