package com.example.appoftheyear2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                Intent intent = new Intent(v.getContext(), ListFragment.class);
                intent.putExtra("nameInput", nameInput.getText().toString());
                intent.putExtra("genreInput", genreInput.getText().toString());
                startActivity(intent);
            }
        });
    }
}
