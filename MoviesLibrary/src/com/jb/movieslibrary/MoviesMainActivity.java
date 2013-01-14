package com.jb.movieslibrary;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.*;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MoviesMainActivity extends Activity implements OnClickListener, OnItemClickListener, OnItemLongClickListener {

	private ArrayList<Movie> movies = new ArrayList<Movie>();
	private MoviesDBHandler db = new MoviesDBHandler(this);
	ArrayAdapter<Movie> moviesAdapter = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movies_main);
		
//		movies = db.getAllMoviesNames();
//		moviesAdapter = new ArrayAdapter<String>(this, 
//				android.R.layout.simple_list_item_1, movies);
//		moviesAdapter.notifyDataSetChanged();
//
//		ListView listView = (ListView)findViewById(R.id.listView1);
//		listView.setAdapter(moviesAdapter);
//		listView.setOnClickListener(this);
//		listView.setOnItemLongClickListener(this);
		
		Button btnPlus = (Button)findViewById(R.id.button0);
		btnPlus.setOnClickListener(this);
		
		Button btnSettings = (Button)findViewById(R.id.button1);
		btnSettings.setOnClickListener(this);
		
		showMovies();
    }
    
    public void showMovies() {
    	movies = db.getAllMovies();
		moviesAdapter = new ArrayAdapter<Movie>(this, 
				android.R.layout.simple_list_item_1, movies);
		moviesAdapter.notifyDataSetChanged();

		ListView listView = (ListView)findViewById(R.id.listView1);
		listView.setAdapter(moviesAdapter);
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
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
			result = db.removeAll();
			showMovies();
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
		// TODO Auto-generated method stub
		
		Movie movie = (Movie)parent.getItemAtPosition(position);
		Intent i = new Intent(MoviesMainActivity.this, EditMovie.class);
		i.putExtra("edit", true);
		i.putExtra("movie", movie);
//		i.putParcelableArrayListExtra(name, value);
		startActivity(i);
		
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View v, int position,
			long id) {
		// TODO Auto-generated method stub
//		getMenuInflater().inflate(R.menu.movies_main_context_menu, menu);
		int i = 0;
		i = 1;
		return false;
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
	}
	
}
