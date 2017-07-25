package xyz.yapapa.rebuses;

import android.content.Context;
import android.content.Intent;
import android.media.SoundPool;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Misha on 24.07.2017.
 */

public class RebusRecyclerViewAdapter extends RecyclerView.Adapter<RebusRecyclerViewAdapter.ViewHolder> {

    private List<ItemObjects> itemList;
    private Context context;



       public class ViewHolder extends RecyclerView.ViewHolder
       {

        private final ImageView imageView;
        private Context context;
        private SoundPool sp;

        public ViewHolder(View v) {
            super(v);


            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    int image;
                    image= itemList.get(getAdapterPosition()).getPhoto();
                    String answer =  new String();
                    answer = itemList.get(getAdapterPosition()).getAnswer();
                    intent.putExtra("image", image);
                    intent.putExtra("answer",answer);
                    context.startActivity(intent);

                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener() {

                public boolean onLongClick(View v) {

                return false;}
            });


            imageView = (ImageView) v.findViewById(R.id.rebus_image);
        }



        public ImageView getImageView() {
            return imageView;
        }

        public void setContext(Context context) {
            this.context = context;
        }

    }


    public RebusRecyclerViewAdapter(MainActivity mainActivity, List<ItemObjects> dataSet) {
        itemList = new ArrayList<>();
        dataSet.size();


        for (int i = 0; i < dataSet.size(); i++) {

            itemList.add(dataSet.get(i));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rebus_list, viewGroup, false);
        context = viewGroup.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        // Get element from your dataset at this position and replace the contents of the view
        // with that element

        viewHolder.setContext(context);

        viewHolder.getImageView().setImageResource(itemList.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}