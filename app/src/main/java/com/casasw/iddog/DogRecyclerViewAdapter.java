package com.casasw.iddog;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.casasw.iddog.data.DogContract;

public class DogRecyclerViewAdapter extends RecyclerView.Adapter<DogRecyclerViewAdapter.AdapterViewHolder> {
    private final DogAdapterOnClickHandler onClickHandler;

    private Cursor mCursor;
    final private Context mContext;
    final private View mEmptyView;


    public DogRecyclerViewAdapter(Context context, DogAdapterOnClickHandler onClickListener, TextView emptyView) {
        this.mContext = context;
        this.onClickHandler = onClickListener;
        this.mEmptyView = emptyView;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_dog, viewGroup, false);
        v.setFocusable(true);
        return new AdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        //set cursor and db
        mCursor.moveToPosition(position);
        String url = mCursor.getString(mCursor.getColumnIndex(DogContract.DogEntry.COLUMN_DOG_IMAGE_URL));
        DogActivity.picassoWithCache
                .load(url)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.logo)
                .into(holder.dogImage);

    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

     class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView dogImage;

        AdapterViewHolder(View v) {
            super(v);
            dogImage = (ImageView) v.findViewById(R.id.dog_imageview);
            v.setOnClickListener(this);
        }

         @Override
         public void onClick(View view) {
            mCursor.moveToPosition(getAdapterPosition());
            int columnIndex = mCursor.getColumnIndex(DogContract.DogEntry.COLUMN_DOG_IMAGE_URL);
            onClickHandler.onClick(mCursor.getString(columnIndex), this);
         }
     }

    void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
        mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    public static interface  DogAdapterOnClickHandler {
        void onClick(String url, AdapterViewHolder vh);
    }

}
