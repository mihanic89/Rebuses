package xyz.yapapa.rebuses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Set;

/** Pages of the rebus detail screen: one rebus per page, answer hidden until asked for. */
public class RebusPagerAdapter extends RecyclerView.Adapter<RebusPagerAdapter.PageHolder> {

    private final List<Rebus> rebuses;
    // Indices of rebuses whose answer the user has revealed; owned by the activity.
    private final Set<Integer> revealed;

    public RebusPagerAdapter(List<Rebus> rebuses, Set<Integer> revealed) {
        this.rebuses = rebuses;
        this.revealed = revealed;
    }

    @NonNull
    @Override
    public PageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rebus_page, parent, false);
        PageHolder holder = new PageHolder(view);
        holder.answerButton.setOnClickListener(v -> {
            int position = holder.getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                revealed.add(position);
                holder.showAnswer(true);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PageHolder holder, int position) {
        Rebus rebus = rebuses.get(position);
        holder.imageView.setImageResource(rebus.imageRes());
        holder.answerView.setText(rebus.answer());
        holder.showAnswer(revealed.contains(position));
    }

    @Override
    public int getItemCount() {
        return rebuses.size();
    }

    static class PageHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView answerView;
        final Button answerButton;

        PageHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            answerView = view.findViewById(R.id.textView);
            answerButton = view.findViewById(R.id.buttonAnswer);
        }

        void showAnswer(boolean shown) {
            answerView.setVisibility(shown ? View.VISIBLE : View.INVISIBLE);
            answerButton.setVisibility(shown ? View.INVISIBLE : View.VISIBLE);
        }
    }
}
