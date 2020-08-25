package SSJPrajwol.com;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class IntroScreen extends AppCompatActivity {

    private TextView app_title;
    private ImageView app_logo;
    private Animation animFade;
    public static List<String> catList = new ArrayList<>();
    private FirebaseFirestore firestore;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);

        app_title = findViewById(R.id.title);
        Typeface typeface = getResources().getFont(R.font.aliseo);
        app_title.setTypeface(typeface);

        app_logo =findViewById(R.id.logo);
        animFade = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        app_logo.setAnimation(animFade);

        Animation title_animation = AnimationUtils.loadAnimation(this, R.anim.title);
        app_title.setAnimation(title_animation);

        firestore = FirebaseFirestore.getInstance();

        new Thread(new Runnable() {
            @Override
            public void run() {
                    //sleep(2000); //sleep not needed anymore as loading data would take some time.

                    loadData();

            }
        }).start();
    }

    private void loadData() {
        catList.clear(); //clearing the list as a norm :P
        firestore.collection("SamanyaGyan").document("Categories")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            //The api provided by firebase is asynchronous i.e. it wont wait for fetching to get complete as it takes some time. So it will just move forward without waiting for fetching tto complete. Adding addOnCompleteListener will call after fetching get complete. It will call in both case: success or fail to fetch data.

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();

                    if(doc.exists()){
                        long count = (long) doc.get("COUNT");

                        for(int i = 1; i <= count; i++){
                            String catName = doc.getString("CAT" + i);
                            catList.add(catName);
                        }


                        Intent intent = new Intent(IntroScreen.this, MainActivity.class);
                        startActivity(intent);
                        IntroScreen.this.finish();
                    }
                    else{
                        Toast.makeText(IntroScreen.this, "No Category Found!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                else{
                    Toast.makeText(IntroScreen.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                }
            }
        });
                //The api provided by firebase is asynchronous i.e. it wont wait for fetching to get complete as it takes some time. So it will just move forward without waiting for fetching tto complete. Adding addOnCompleteListener will call after fetching get complete. It will call in both case: success or fail to fetch data.
    }
}