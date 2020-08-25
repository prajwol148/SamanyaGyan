package ssjprajwol.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import static ssjprajwol.com.IntroScreen.catList;

public class Category extends AppCompatActivity {
    List<String> titles, catlist;
    List<Integer> images;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView dataList = findViewById(R.id.dataList);

        titles = new ArrayList<>();
        images =new ArrayList<>();
        catlist = new ArrayList<>();



        images.add(R.drawable.sport);
        images.add(R.drawable.science);
        images.add((R.drawable.movie));
        images.add((R.drawable.music));
        images.add((R.drawable.tv));
        images.add((R.drawable.memes));


        adapter = new Adapter(this,catList,images);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2,GridLayoutManager.VERTICAL,false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);
    }


    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}