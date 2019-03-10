package provider.movie.com.ishank.engineer.notes.async;

import android.os.AsyncTask;

import provider.movie.com.ishank.engineer.notes.model.Notes;
import provider.movie.com.ishank.engineer.notes.persistance.NoteDao;

public class UpdateAsyncTask extends AsyncTask<Notes,Void,Void> {

    private NoteDao mNoteDao;

    public UpdateAsyncTask(NoteDao mNoteDao) {
        this.mNoteDao = mNoteDao;
    }

    @Override
    protected Void doInBackground(Notes... notes) {
        mNoteDao.updateNotes(notes);
        return null;
    }
}
