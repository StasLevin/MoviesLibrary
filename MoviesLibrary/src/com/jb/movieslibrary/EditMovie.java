package com.jb.movieslibrary;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditMovie extends Activity implements OnClickListener{
	
//	private String editables[] = {"subject", "body", "uri"};
	private int editables [] = {R.id.editText0, R.id.editText1, R.id.editText2};
	private EditText edits[] = new EditText[editables.length];
	
	
//	private String buttons[] = {"OK", "Cancel", "Show"};
	private int buttons[] = {R.id.button0, R.id.button1, R.id.button2};
	private Button btns [] = new Button [buttons.length];
	
	
	private MoviesDBHandler db = new MoviesDBHandler(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_movie);
		
		for (int i = 0; i < editables.length; i++) {
			edits[i] = (EditText)findViewById(editables[i]);
		}

		for (int i = 0; i < buttons.length; i++) {
			btns[i] = (Button)findViewById(buttons[i]);
			btns[i].setOnClickListener(this);
		}
		
		Intent i = getIntent();
		boolean isEdit = i.getBooleanExtra("edit", false);
		if (isEdit) {
			Movie movie = i.getParcelableExtra("movie");
			edits[0].setText(movie.getSubject());
			edits[1].setText(movie.getBody());
			edits[2].setText(movie.getUri().toString());
		}
		edits[0].requestFocus();
	}

	public void onClick(View v) {
		Intent i;
		switch (v.getId()) {
		// "OK" button
		case R.id.button0:
			i = getIntent();
			boolean isEdit = i.getBooleanExtra("edit", false);
			if (isEdit) {
				db.updateMovie(new Movie( ((Movie)i.getParcelableExtra("movie")).get_id(), edits[0].getEditableText().toString(),
						edits[1].getEditableText().toString(), Uri.parse(edits[2].getEditableText().toString())));
			} else {
				db.addMovie(new Movie(edits[0].getEditableText().toString(),
					edits[1].getEditableText().toString(), Uri.parse(edits[2].getEditableText().toString())));
			}
			i = new Intent(EditMovie.this, MoviesMainActivity.class);
			startActivity(i);
			break;
		// "Cancel" button
		case R.id.button1:
			i = new Intent(EditMovie.this, MoviesMainActivity.class);
			startActivity(i);
			break;
		// "Show" button
		case R.id.button2:
			//TODO
			
			
			break;
		}
	}

}
