package com.example.faraid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView mat1 = (TextView) findViewById(R.id.textViewMateri1);
        TextView mat2 = (TextView) findViewById(R.id.textViewMateri2);
        TextView mat3 = (TextView) findViewById(R.id.textViewMateri3);
        TextView mat4 = (TextView) findViewById(R.id.textViewMateri4);
        TextView mat6 = (TextView) findViewById(R.id.textViewMateri6);
        TextView mat7 = (TextView) findViewById(R.id.textViewMateri7);
        TextView mat8 = (TextView) findViewById(R.id.textViewMateri8);
        TextView mat9 = (TextView) findViewById(R.id.textViewMateri9);
    }
}
