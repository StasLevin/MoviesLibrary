package com.jb.movieslibrary;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.*;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MoviesMainActivity extends Activity implements OnClickListener, OnItemClickListener {

	private ArrayList<Movie> movies = new ArrayList<Movie>();
	private MoviesDBHandler db = new MoviesDBHandler(this);
	ArrayAdapter<Movie> moviesAdapter = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movies_main);
		
		Button btnPlus = (Button)findViewById(R.id.button0);
		btnPlus.setOnClickListener(this);
		
		Button btnSettings = (Button)findViewById(R.id.button1);
		btnSettings.setOnClickListener(this);
		
		showMoviesList();
    }
    
    public void showMoviesList() {
    	movies = db.getAllMovies();
		moviesAdapter = new ArrayAdapter<Movie>(this, 
				android.R.layout.simple_list_item_1, movies);
		moviesAdapter.notifyDataSetChanged();

		ListView listView = (ListView)findViewById(R.id.listView1);
		listView.setAdapter(moviesAdapter);
		listView.setOnItemClickListener(this);
		registerForContextMenu(listView);
    }

	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result = false;
		switch(item.getItemId()) {
		case R.id.itemExit:
			Intent i = new Intent(Intent.ACTION_MAIN);
			i.addCategory(Intent.CATEGORY_HOME);
			startActivity(i);
			result = true;
			break;
		case R.id.itemRemoveAll:
			result = db.removeAllMovies();
			showMoviesList();
			result = true;
			break;
		}
		return result;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.button0:
			Intent i = new Intent(MoviesMainActivity.this, 
					EditMovie.class);
			startActivity(i);
			break;
		case R.id.button1:
			//TODO
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		
		Movie movie = (Movie)parent.getItemAtPosition(position);
		Intent i = new Intent(MoviesMainActivity.this, EditMovie.class);
		i.putExtra("edit", true);
		i.putExtra("movie", movie);
		startActivity(i);
		
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movies_main_options_menu, menu);
        return true;
    }
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.movies_main_context_menu, menu);
//		menu.setHeaderTitle("Select Option");
		menu.setHeaderTitle(R.string.contextMenuTitle);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		boolean result = false;
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int position = (int)info.position;
		Movie movie = moviesAdapter.getItem(position);
		switch (item.getItemId()) {
		case R.id.itemEditMovie:
			Intent i = new Intent(MoviesMainActivity.this, EditMovie.class);
			i.putExtra("edit", true);
			i.putExtra("movie", movie);
			startActivity(i);
			result = true;
			break;
		case R.id.itemDeleteMovie:
			result = db.deleteMovie(movie);
			moviesAdapter.notifyDataSetChanged();
			showMoviesList();
			break;
		default:
			return super.onContextItemSelected(item);
		}
		
		return result;
	}

	@Override
	public Dialog onCreateDialog(int id, Bundle args) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Add new movie");
		builder.setSingleChoiceItems(R.array.addMovieDialog, 1, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				int selection = R.array.addMovieDialog;
				
				
			}
		});
		return builder.create();
	}
}
