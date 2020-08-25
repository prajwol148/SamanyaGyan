package ssjprajwol.com;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Adapter_Grid extends BaseAdapter {
    private int sets;

    public Adapter_Grid(int sets) {

        this.sets = sets;
    }

    @Override
    public int getCount() {
        return sets;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        View view;
        if(convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_item,parent,false);
        }
        else{
            view = convertView;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(),Questions.class);
                intent.putExtra("SETNO", position+1);
                parent.getContext().startActivity(intent);
            }
        });

/*        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(),Sets.class);
                intent.putExtra("title",titles.get(position))
            }
        });*/

        ((TextView)view.findViewById(R.id.textview)).setText(String.valueOf(position+1));
        return view;

    }
}
