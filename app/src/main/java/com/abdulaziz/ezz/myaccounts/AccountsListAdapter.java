package com.abdulaziz.ezz.myaccounts;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.abdulaziz.ezz.myaccounts.database.DatabaseContract;

/**
 * Created by EZZ on 2018-02-11.
 */

public class AccountsListAdapter extends RecyclerView.Adapter<AccountsListAdapter.AccountsViewHolder>
{

	private Cursor mCursor;
	private Context mContext;

	public AccountsListAdapter(Context mContext, Cursor mCursor)
	{
		this.mCursor = mCursor;
		this.mContext = mContext;
	}

	@Override
	public AccountsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.main_list_item, parent, false);
		return new AccountsViewHolder(view);
	}

	@Override
	public void onBindViewHolder(AccountsViewHolder holder, int position)
	{
		if (!mCursor.moveToPosition(position))
			return;
		long id = mCursor.getLong(mCursor.getColumnIndex(DatabaseContract.DataBaseEntry._ID));
		String code = mCursor.getString(mCursor.getColumnIndex(DatabaseContract.DataBaseEntry.CLN_CODE));
		String name1 = mCursor.getString(mCursor.getColumnIndex(DatabaseContract.DataBaseEntry.CLN_NAME1));
		holder.mtvCode.setTag(id);
		holder.mtvCode.setText(code);
		holder.mtvCode.setText(name1);
	}

	@Override
	public int getItemCount()
	{
		return mCursor.getCount();
	}

	public void swapCursor(Cursor newCursor)
	{
		// Always close the previous mCursor first
		if (mCursor != null)
			mCursor.close();
		mCursor = newCursor;
		if (newCursor != null)
		{
			// Force the RecyclerView to refresh
			this.notifyDataSetChanged();
		}
	}

	public class AccountsViewHolder extends RecyclerView.ViewHolder
	{
		TextView mtvCode;
		TextView mtvName;

		public AccountsViewHolder(View itemView)
		{
			super(itemView);
			mtvCode = itemView.findViewById(R.id.tvCode);
			mtvName = itemView.findViewById(R.id.tvName);
		}
	}
}
