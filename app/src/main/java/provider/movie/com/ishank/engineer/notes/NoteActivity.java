package provider.movie.com.ishank.engineer.notes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import provider.movie.com.ishank.engineer.notes.model.Notes;
import provider.movie.com.ishank.engineer.notes.persistance.NoteRepository;
import provider.movie.com.ishank.engineer.notes.util.Utility;

public class NoteActivity extends AppCompatActivity implements View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener,
        TextWatcher {

    public static final String TAG = "NoteActivity";
    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;

    //Ui Components
    private LinedEditText mLinedEditText;
    private EditText mEditText;
    private TextView mTextView;
    private RelativeLayout arrow_container, check_container;
    private ImageButton back_arrow_button, check_arrow_button;

    //Vars
    private Boolean mIsNewNote;
    private Notes mInitialNotes;
    private GestureDetector mGestureDetector;
    private int mMode;
    private NoteRepository mNoteRepository;
    private Notes mNoteFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mLinedEditText = findViewById(R.id.note_text);
        mEditText = findViewById(R.id.note_edit_text_title);
        mTextView = findViewById(R.id.note_text_title);
        arrow_container = findViewById(R.id.back_arrow_container);
        check_container = findViewById(R.id.check_arrow_container);

        back_arrow_button = findViewById(R.id.toolbar_back_arrow);
        check_arrow_button = findViewById(R.id.check_arrow);

        mNoteRepository = new NoteRepository(this);

        setListeners();

        if (getIncomingIntent()){
            //this is edit mode
            setNewNoteProperty();
            editMode();
        }
        else {
            //this is a view mode
            setNoteProperty();
            disableContentInteraction();
        }

    }

    // Observation method for (view mode and edit mode)
    private Boolean getIncomingIntent(){
        if (getIntent().hasExtra("selected_note")){
            mInitialNotes = getIntent().getParcelableExtra("selected_note");
            mNoteFinal = new Notes();
            mNoteFinal.setTitle(mInitialNotes.getTitle());
            mNoteFinal.setContent(mInitialNotes.getContent());
            mNoteFinal.setTimeStamp(mInitialNotes.getTimeStamp());
            mNoteFinal.setId(mInitialNotes.getId());
         //   Log.d(TAG, "getIncomingIntent: "+incomingNote.toString());

            mMode = EDIT_MODE_DISABLED;
            mIsNewNote = false;
            return false;
        }
        mMode = EDIT_MODE_ENABLED;
        mIsNewNote = true;
        return true;
    }

    private void saveChanges(){
        if (mIsNewNote){
            saveNewNote();
        }
        else{
            updateNote();
        }
    }

    private void updateNote(){
        mNoteRepository.updateNoteTask(mNoteFinal);
    }
    private void saveNewNote(){
        mNoteRepository.insertNoteTask(mNoteFinal);
    }

    //Edit Mode
    private void editMode(){
        arrow_container.setVisibility(View.GONE);
        check_container.setVisibility(View.VISIBLE);

        mTextView.setVisibility(View.GONE);
        mEditText.setVisibility(View.VISIBLE);

        mMode = EDIT_MODE_ENABLED;
        enableContentInteraction();
    }

    //View MOde

    private void viewMode(){
        arrow_container.setVisibility(View.VISIBLE);
        check_container.setVisibility(View.GONE);

        mTextView.setVisibility(View.VISIBLE);
        mEditText.setVisibility(View.GONE);

        mMode = EDIT_MODE_DISABLED;
        disableContentInteraction();

        String temp = mLinedEditText.getText().toString() ;

        temp = temp.replace("\n", "");

        temp = temp.replace(" ", "");

        if(temp.length() > 0) {
            mNoteFinal.setTitle(mEditText.getText().toString());
            mNoteFinal.setContent(mLinedEditText.getText().toString());
          //  mTextView.setText(mEditText.getText().toString());
            String timestamp = Utility.getCurrentTimeStamp();
            mNoteFinal.setTimeStamp(timestamp);

            // If the note was altered, save it.
            if (!mNoteFinal.getContent().equals(mInitialNotes.getContent())
                    || !mNoteFinal.getTitle().equals(mInitialNotes.getTitle())) {
                saveChanges();
            }
        }
    }

    // Listeners for Click Events, DoubleTap Events, Touch Events
    private void setListeners(){
        mLinedEditText.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this,this);
        mTextView.setOnClickListener(this);
        check_arrow_button.setOnClickListener(this);
        back_arrow_button.setOnClickListener(this);
        mEditText.addTextChangedListener(this);
    }

    //Note Property For Already Written Notes
    private void setNoteProperty(){
        mTextView.setText(mInitialNotes.getTitle());
        mEditText.setText(mInitialNotes.getTitle());
        mLinedEditText.setText(mInitialNotes.getContent());
    }

    //Note property for new notes
    private void setNewNoteProperty(){
        mTextView.setText(getResources().getString(R.string.default_title));
        mEditText.setText(getResources().getString(R.string.default_title));
        mNoteFinal = new Notes();
        mInitialNotes = new Notes();
        mInitialNotes.setTitle(getResources().getString(R.string.default_title));
        mNoteFinal.setTitle(getResources().getString(R.string.default_title));
    }

    // Disabling Content Interaction

    private void disableContentInteraction(){
        mLinedEditText.setKeyListener(null);
        mLinedEditText.setFocusable(false);
        mLinedEditText.setFocusableInTouchMode(false);
        mLinedEditText.setCursorVisible(false);
        mLinedEditText.clearFocus();
    }

    private void enableContentInteraction(){
        mLinedEditText.setKeyListener(new EditText(this).getKeyListener());
        mLinedEditText.setFocusable(true);
        mLinedEditText.setFocusableInTouchMode(true);
        mLinedEditText.setCursorVisible(true);
        mLinedEditText.requestFocus();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        editMode();
        Log.d(TAG, "onDoubleTap: Clicked");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.check_arrow:
                viewMode();
               // hideSoftKeyboard();
                break;

            case R.id.note_text_title:
                editMode();
                mEditText.requestFocus();
                mEditText.setSelection(mEditText.length());
                break;

            case R.id.toolbar_back_arrow:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mMode == EDIT_MODE_ENABLED){
            onClick(check_arrow_button);
        }
        else super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mode", mMode);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMode = savedInstanceState.getInt("mode");
        if(mMode == EDIT_MODE_ENABLED){
            editMode();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mTextView.setText(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
