package ru.shmakinv.android.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //noinspection ConstantConditions
        findViewById(R.id.btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn) {
            Toast.makeText(MainActivity.this, "CLICK HAPPENED!", Toast.LENGTH_SHORT).show();
        }
    }
}
