package ssjprajwol.com;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    List<String> titles;
    List<Integer> images;
    Context context;
    LayoutInflater inflater;

    public Adapter(Context context, List<String> titles, List<Integer> images) {
        this.titles = titles;
        this.images = images;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(titles.get(position));
        holder.gridIcon.setImageResource(images.get(position));


    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView gridIcon;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView);
            gridIcon = itemView.findViewById(R.id.imageView);


            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(itemView.getContext(),Sets.class);
                    intent.putExtra("title",titles.get(getAdapterPosition()));
                    intent.putExtra("CATEGORY_ID",getAdapterPosition()+1);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext().startActivity(intent);
                }





/*        private void setData(String url, final String title){
            this.title.setText(title);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    Toast.makeText(view.getContext(),"Clicked", Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(itemView.getContext(),Sets.class);
                    intent.putExtra("title",title);
                    itemView.getContext().startActivity(intent);
                }
            });

        }

    }

}*/


            });

        }
    }
}

