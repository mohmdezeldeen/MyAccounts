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

		dbHelper = new DataBaseHelper(this);

		mDb = dbHelper.getWritableDatabase();

		Cursor cursor = getAllGuests();

		adapter = new AccountsListAdapter(this, cursor);

		recyclerView.setAdapter(adapter);

		new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
		{

			@Override
			public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
			{
				return false;
			}

			@Override
			public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir)
			{
				long id = (long) viewHolder.itemView.getTag();
				removeGuest(id);
				adapter.swapCursor(getAllGuests());
			}
		}).attachToRecyclerView(recyclerView);
	}

	public void addToWaitlist(View view)
	{
		if (mAccountCode.getText().length() == 0 || mAccountName.getText().length() == 0)
		{
			return;
		}
		addNewGuest(mAccountCode.getText().toString(), mAccountName.getText().toString());

		adapter.swapCursor(getAllGuests());

		mAccountName.clearFocus();
		mAccountCode.getText().clear();
		mAccountName.getText().clear();
	}

	private Cursor getAllGuests()
	{
		return mDb.query(TBL_ACCOUNT, null, null, null, null, null, CLN_TIMESTAMP);
	}

	private long addNewGuest(String code, String name)
	{
		ContentValues cv = new ContentValues();
		cv.put(CLN_ACCOUNT_CODE, code);
		cv.put(CLN_ACCOUNT_NAME, name);
		return mDb.insert(TBL_ACCOUNT, null, cv);
	}

	private boolean removeGuest(long id)
	{
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
