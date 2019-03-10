package provider.movie.com.ishank.engineer.notes.async;

import android.os.AsyncTask;

import provider.movie.com.ishank.engineer.notes.model.Notes;
import provider.movie.com.ishank.engineer.notes.persistance.NoteDao;

public class InsertAsyncTask extends AsyncTask<Notes,Void,Void> {

    private NoteDao mNoteDao;

    public InsertAsyncTask(NoteDao mNoteDao) {
        this.mNoteDao = mNoteDao;
    }

    @Override
    protected Void doInBackground(Notes... notes) {
        mNoteDao.insertNotes(notes);
        return null;
    }
}
