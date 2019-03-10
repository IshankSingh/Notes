package provider.movie.com.ishank.engineer.notes;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

import provider.movie.com.ishank.engineer.notes.adapters.NotesAdapter;
import provider.movie.com.ishank.engineer.notes.model.Notes;
import provider.movie.com.ishank.engineer.notes.persistance.NoteRepository;
import provider.movie.com.ishank.engineer.notes.util.VerticalSpacingItemDecorator;

public class MainActivity extends AppCompatActivity implements NotesAdapter.OnNoteListener,
        View.OnClickListener {

    //private static final String TAG = "MainActivity";

    // Ui Components
    private RecyclerView mRecyclerView;


    // Vars
    private ArrayList<Notes> mNotes = new ArrayList<>();
    private NotesAdapter mNotesAdapter;
    private NoteRepository mNoteRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);

        setSupportActionBar((Toolbar)findViewById(R.id.notes_toolbar));
        setTitle(getString(R.string.notes_toolbar));

        findViewById(R.id.fab).setOnClickListener(this);

        mNoteRepository = new NoteRepository(this);
        initRecycle();
        retrieveNotes();

    }


    private void retrieveNotes(){
        mNoteRepository.retrieveNoteTask().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                if (mNotes.size() > 0){
                    mNotes.clear();
                }
                if (mNotes != null){
                    mNotes.addAll(notes);
                }
                mNotesAdapter.notifyDataSetChanged();
            }
        });
    }



    public void initRecycle(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mNotesAdapter = new NotesAdapter(mNotes,this);
        mRecyclerView.setAdapter(mNotesAdapter);
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this,NoteActivity.class);
        intent.putExtra("selected_note",mNotes.get(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(MainActivity.this,NoteActivity.class));
    }

    private void deleteNote(Notes note) {
        mNotes.remove(note);
        mNotesAdapter.notifyDataSetChanged();

        mNoteRepository.deleteNoteTask(note);
    }

    /*
    Item touch helper is used to implement functionality for recycler view using methods like simpleCallback, Callback etc
    which provide functional like swiping, moving and dragging an recycler view element from one position to other.
     */
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@Nullable RecyclerView recyclerView,@Nullable RecyclerView.ViewHolder viewHolder,@Nullable RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            deleteNote(mNotes.get(viewHolder.getAdapterPosition()));
        }
    };
}
