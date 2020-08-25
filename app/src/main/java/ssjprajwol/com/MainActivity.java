package ssjprajwol.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
        Button start_quiz = findViewById(R.id.start_quiz);

        Intent intent = new Intent(MainActivity.this, Category.class);
        startActivity(intent);
    }
}
