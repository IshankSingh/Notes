package provider.movie.com.ishank.engineer.notes.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import provider.movie.com.ishank.engineer.notes.R;
import provider.movie.com.ishank.engineer.notes.model.Notes;
import provider.movie.com.ishank.engineer.notes.util.Utility;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private ArrayList<Notes> mNotes;
    private OnNoteListener mOnNoteListener;
    private static final String TAG = "NotesAdapter";

    public NotesAdapter(ArrayList<Notes> notes, OnNoteListener onNoteListener) {
        this.mNotes = notes;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_notes_list_item,viewGroup,false);
        return new NotesViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        try{
            String month = mNotes.get(position).getTimeStamp().substring(0, 2);
            month = Utility.getMonthFromNumber(month);
            String year = mNotes.get(position).getTimeStamp().substring(3);
            String timestamp = month + " " + year;
            holder.timeStamp.setText(timestamp);
            holder.title.setText(mNotes.get(position).getTitle());
        }catch (NullPointerException e){
            Log.d(TAG, "onBindViewHolder: Exception Found");
        }
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, timeStamp;
        OnNoteListener onNoteListener;

        private NotesViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            title = itemView.findViewById(R.id.textView_title);
            timeStamp = itemView.findViewById(R.id.textView_timeStamp);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteListener {
        void onNoteClick(int position);
    }
}
