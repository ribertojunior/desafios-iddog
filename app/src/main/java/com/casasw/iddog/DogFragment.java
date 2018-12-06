package com.casasw.iddog;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.casasw.iddog.data.DogContract;

public class DogFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static String TAG = DogFragment.class.getSimpleName();
    private static final int LOADER_ID = 14;


    private RecyclerViewAdapter mAdapter;


    private static final String[] DOG_COLUMNS = {
            DogContract.DogEntry.TABLE_NAME + "." + DogContract.DogEntry._ID,
            DogContract.DogEntry.COLUMN_DOG_BREED,
            DogContract.DogEntry.COLUMN_DOG_IMAGE_URL
    };
    private static final int COL_ID = 0;
    private static final int COL_BREED = 1;
    private static final int COL_URL = 2;

    private String getBreed(){
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences
                .getString(getString(R.string.breed_key), getString(R.string.breed_key_default));
    }

    private void saveBreed(String breed){
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(getString(R.string.breed_key), breed).apply();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dog, container, false);
        ViewHolder viewHolder = new ViewHolder(rootView);
        RecyclerViewAdapter.DogAdapterOnClickHandler handler = new RecyclerViewAdapter.DogAdapterOnClickHandler() {
            @Override
            public void onClick(String breed, RecyclerViewAdapter.AdapterViewHolder vh) {
                ((Callback) getActivity()).onItemSelected(DogContract.DogEntry.buildDogWithBreed(breed), vh);
            }
        };
        mAdapter = new RecyclerViewAdapter(getActivity(), handler, viewHolder.mEmptyView);

        viewHolder.mRecyclerView.setHasFixedSize(true);
        viewHolder.mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        viewHolder.mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));        //viewHolder.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewHolder.mRecyclerView.setAdapter(mAdapter);
        getLoaderManager().initLoader(LOADER_ID, null, this);
        rootView.setTag(viewHolder);
        return rootView;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        String sortOrder = DogContract.DogEntry.COLUMN_DOG_BREED + " ASC";
        Uri dogBreedUri = DogContract.DogEntry.buildDogWithBreed(getBreed());
        return new CursorLoader(getActivity(), dogBreedUri, DOG_COLUMNS,
                null, null, sortOrder);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            saveBreed(cursor.getString(COL_BREED));

        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }



    static class ViewHolder {
        final RecyclerView mRecyclerView;
        final TextView mEmptyView;
        ViewHolder(View view) {
            mRecyclerView = (RecyclerView) view.findViewById(R.id.dog_recyclerview);
            mEmptyView = (TextView) view.findViewById(R.id.view_empty);

        }
    }
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = space;
            } else {
                outRect.top = 0;
            }
        }
    }
    public interface Callback {

        void onItemSelected(Uri movieUri, RecyclerViewAdapter.AdapterViewHolder vh);
    }
}
