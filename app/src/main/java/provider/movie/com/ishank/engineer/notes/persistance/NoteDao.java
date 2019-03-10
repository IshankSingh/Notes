package provider.movie.com.ishank.engineer.notes.persistance;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import provider.movie.com.ishank.engineer.notes.model.Notes;

@Dao
public interface NoteDao {

    @Insert
    long[] insertNotes(Notes... notes);

    @Query("SELECT * FROM notes")
    LiveData<List<Notes>> getNotes();

    @Delete
    int delete(Notes... notes);

    @Update
    int updateNotes(Notes... notes);
}
