package com.jb.movieslibrary;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditMovie extends Activity implements OnClickListener{
	
	private String editables[] = {"subject", "body", "uri"};
	private EditText edits[] = new EditText[editables.length];
	private int editNames [] = {R.id.editText0, R.id.editText1, R.id.editText2};
	
	private String buttons[] = {"OK", "Cancel", "Show"};
	private Button btns [] = new Button [buttons.length];
	private int btnNames[] = {R.id.button0, R.id.button1, R.id.button2};
	
	private MoviesDBHandler db = new MoviesDBHandler(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_movie);
		
		for (int i = 0; i < editables.length; i++) {
			edits[i] = (EditText)findViewById(editNames[i]);
		}

		for (int i = 0; i < buttons.length; i++) {
			btns[i] = (Button)findViewById(btnNames[i]);
			btns[i].setOnClickListener(this);
		}
		
		Intent i = getIntent();
		boolean isEdit = i.getBooleanExtra("edit", false);
		if (isEdit) {
			Movie movie = i.getParcelableExtra("movie");
			//TODO
			edits[0].setText(movie.getSubject());
			edits[1].setText(movie.getBody());
			edits[2].setText(movie.getUri().toString());
		}
		edits[0].requestFocus();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_edit_movie, menu);
		return true;
	}
	
	public void onClick(View v) {
		Intent i;
		switch (v.getId()) {
		case R.id.button0:
			Movie movie = new Movie(edits[0].getEditableText().toString(),
					edits[1].getEditableText().toString(), Uri.parse(edits[2].getEditableText().toString()));
			db.addMovie(movie);
			i = new Intent(EditMovie.this, MoviesMainActivity.class);
			startActivity(i);
			break;
		case R.id.button1:
			i = new Intent(EditMovie.this, MoviesMainActivity.class);
			startActivity(i);
			break;
		case R.id.button2:
			//TODO
			
			
			break;
		}
	}

}
