package com.abdulaziz.ezz.myaccounts.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import com.abdulaziz.ezz.myaccounts.AccountsListAdapter;
import com.abdulaziz.ezz.myaccounts.R;
import com.abdulaziz.ezz.myaccounts.database.DataBaseHelper;

import static com.abdulaziz.ezz.myaccounts.database.DatabaseContract.DataBaseEntry.*;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

	private SQLiteDatabase mDb;
	private DataBaseHelper dbHelper;
	private AccountsListAdapter adapter;
	private RecyclerView recyclerView;
	private EditText mAccountCode;
	private EditText mAccountName;

	private final static String LOG_TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				addToWaitlist(view);
			}
		});

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
				R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		recyclerView = findViewById(R.id.recyclerView);
		mAccountCode = findViewById(R.id.tvCode);
		mAccountName = findViewById(R.id.tvName);

		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		// Create a DB helper (this will create the DB if run for the first time)
		dbHelper = new DataBaseHelper(this);

		// Keep a reference to the mDb until paused or killed. Get a writable database
		// because you will be adding restaurant customers
		mDb = dbHelper.getWritableDatabase();

		// Get all guest info from the database and save in a cursor
		Cursor cursor = getAllGuests();

		// Create an adapter for that cursor to display the data
		adapter = new AccountsListAdapter(this, cursor);

		// Link the adapter to the RecyclerView
		recyclerView.setAdapter(adapter);

		// COMPLETED (3) Create a new ItemTouchHelper with a SimpleCallback that handles both LEFT and RIGHT swipe directions
		// Create an item touch helper to handle swiping items off the list
		new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
		{

			// COMPLETED (4) Override onMove and simply return false inside
			@Override
			public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
			{
				//do nothing, we only care about swiping
				return false;
			}

			// COMPLETED (5) Override onSwiped
			@Override
			public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir)
			{
				// COMPLETED (8) Inside, get the viewHolder's itemView's tag and store in a long variable id
				//get the id of the item being swiped
				long id = (long) viewHolder.itemView.getTag();
				// COMPLETED (9) call removeGuest and pass through that id
				//remove from DB
				removeGuest(id);
				// COMPLETED (10) call swapCursor on mAdapter passing in getAllGuests() as the argument
				//update the list
				adapter.swapCursor(getAllGuests());
			}

			//COMPLETED (11) attach the ItemTouchHelper to the waitlistRecyclerView
		}).attachToRecyclerView(recyclerView);
	}

	public void addToWaitlist(View view)
	{
		if (mAccountCode.getText().length() == 0 || mAccountName.getText().length() == 0)
		{
			return;
		}
		//default party size to 1
		int partySize = 1;
		try
		{
			//mNewPartyCountEditText inputType="number", so this should always work
			partySize = Integer.parseInt(mAccountName.getText().toString());
		}
		catch (NumberFormatException ex)
		{
			Log.e(LOG_TAG, "Failed to parse party size text to number: " + ex.getMessage());
		}

		// Add guest info to mDb
		addNewGuest(mAccountCode.getText().toString(), partySize);

		// Update the cursor in the adapter to trigger UI to display the new list
		adapter.swapCursor(getAllGuests());

		//clear UI text fields
		mAccountName.clearFocus();
		mAccountCode.getText().clear();
		mAccountName.getText().clear();
	}

	/**
	 * Query the mDb and get all guests from the waitlist table
	 *
	 * @return Cursor containing the list of guests
	 */
	private Cursor getAllGuests()
	{
		return mDb.query(TBL_ACCOUNT, null, null, null, null, null, CLN_TIMESTAMP);
	}

	/**
	 * Adds a new guest to the mDb including the party count and the current timestamp
	 *
	 * @param name      Guest's name
	 * @param partySize Number in party
	 * @return id of new record added
	 */
	private long addNewGuest(String name, int partySize)
	{
		ContentValues cv = new ContentValues();
		cv.put(CLN_CODE, name);
		cv.put(CLN_NAME1, partySize);
		return mDb.insert(TBL_ACCOUNT, null, cv);
	}

	// COMPLETED (1) Create a new function called removeGuest that takes long id as input and returns a boolean

	/**
	 * Removes the record with the specified id
	 *
	 * @param id the DB id to be removed
	 * @return True: if removed successfully, False: if failed
	 */
	private boolean removeGuest(long id)
	{
		// COMPLETED (2) Inside, call mDb.delete to pass in the TABLE_NAME and the condition that WaitlistEntry._ID equals id
		return mDb.delete(TBL_ACCOUNT, _ID + "=" + id, null) > 0;
	}

	///////////////
	@Override
	public void onBackPressed()
	{
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START))
		{
			drawer.closeDrawer(GravityCompat.START);
		}
		else
		{
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings)
		{
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item)
	{
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.nav_camera)
		{
			// Handle the camera action
		}
		else if (id == R.id.nav_gallery)
		{

		}
		else if (id == R.id.nav_slideshow)
		{

		}
		else if (id == R.id.nav_manage)
		{

		}
		else if (id == R.id.nav_share)
		{

		}
		else if (id == R.id.nav_send)
		{

		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
