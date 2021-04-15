package in.example.notesappusingfirestore;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private ArrayList<Notes> notesArrayList;
    private Context context;

    public NotesAdapter(ArrayList<Notes> notesArrayList, Context context) {
        this.notesArrayList = notesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_note,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {

        Notes notes = notesArrayList.get(position);
        holder.titleTV.setText(notes.gettitle());
        holder.descriptionTV.setText(notes.getDescription());
    }

    @Override
    public int getItemCount() {
        return notesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTV;
        private final TextView descriptionTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.TVtitle);
            descriptionTV = itemView.findViewById(R.id.TVDescription);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Notes notes = notesArrayList.get(getAdapterPosition());
                    Intent intent = new Intent(context, UpdateNotes.class);
                    intent.putExtra("notes",notes);
                    context.startActivity(intent);
                }
            });
        }
    }
}
