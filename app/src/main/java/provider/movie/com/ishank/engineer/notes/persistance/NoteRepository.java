package provider.movie.com.ishank.engineer.notes.persistance;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

import provider.movie.com.ishank.engineer.notes.async.DeleteAsyncTask;
import provider.movie.com.ishank.engineer.notes.async.InsertAsyncTask;
import provider.movie.com.ishank.engineer.notes.async.UpdateAsyncTask;
import provider.movie.com.ishank.engineer.notes.model.Notes;

public class NoteRepository {

    private NoteDatabase mNoteDatabase;

    public NoteRepository(Context context) {
        mNoteDatabase = NoteDatabase.getInstance(context);
    }

    public void insertNoteTask(Notes note){
        new InsertAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }

    public void updateNoteTask(Notes note){
        new UpdateAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }
    public LiveData<List<Notes>> retrieveNoteTask(){
        return mNoteDatabase.getNoteDao().getNotes();
    }

    public void deleteNoteTask(Notes note){
        new DeleteAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }
}