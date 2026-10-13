package xyz.yapapa.rebuses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RebusRecyclerViewAdapter extends RecyclerView.Adapter<RebusRecyclerViewAdapter.ViewHolder> {

    public interface OnRebusClickListener {
        void onRebusClick(int index);
    }

    private final List<Rebus> rebuses;
    private final OnRebusClickListener listener;
    // Accessibility labels ("Ребус N"), formatted once per position instead of on every bind.
    private final String[] descriptions;

    public RebusRecyclerViewAdapter(List<Rebus> rebuses, OnRebusClickListener listener) {
        this.rebuses = rebuses;
        this.listener = listener;
        this.descriptions = new String[rebuses.size()];
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rebus_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> {
            // NO_POSITION while the item is being removed or before layout has run.
            int position = holder.getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                listener.onRebusClick(position);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(rebuses.get(position).imageRes());
        if (descriptions[position] == null) {
            descriptions[position] =
                    holder.itemView.getContext().getString(R.string.rebus_number, position + 1);
        }
        holder.imageView.setContentDescription(descriptions[position]);
    }

    @Override
    public int getItemCount() {
        return rebuses.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;

        ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.rebus_image);
        }
    }
}
