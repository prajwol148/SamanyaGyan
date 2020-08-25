package SSJPrajwol.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Sets extends AppCompatActivity {
    private GridView gridView;
    private FirebaseFirestore firestore;
    public static int category_id;
    private Dialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        category_id = getIntent().getIntExtra("CATEGORY_ID", 1);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

        gridView = findViewById(R.id.gridview);
        loading = new Dialog(Sets.this);
        loading.setContentView(R.layout.loading_bar);
        loading.setCancelable(false);
        loading.getWindow().setBackgroundDrawableResource(R.drawable.background_progressbar);
        loading.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loading.show();

        firestore= FirebaseFirestore.getInstance();
        loadSets();




//        Intent intent = getIntent();
//        String title = intent.getStringExtra("title");




    }

    private void loadSets() {

        firestore.collection("SamanyaGyan").document("CAT"+String.valueOf(category_id))
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            //The api provided by firebase is asynchronous i.e. it wont wait for fetching to get complete as it takes some time. So it will just move forward without waiting for fetching tto complete. Adding addOnCompleteListener will call after fetching get complete. It will call in both case: success or fail to fetch data.

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();

                    if (doc.exists()) {
                        long sets = (long) doc.get("SETS");

                        Adapter_Grid adapter = new Adapter_Grid((int) sets);
                        gridView.setAdapter(adapter);


                    } else {
                        Toast.makeText(Sets.this, "No Set document Found!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(Sets.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }

                loading.cancel();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}