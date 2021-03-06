package com.casasw.iddog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.casasw.iddog.data.DogContract;

public class DogFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemSelectedListener {

    public static String TAG = DogFragment.class.getSimpleName();
    private static final int LOADER_ID = 14;


    private DogRecyclerViewAdapter mAdapter;


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

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(getContext(),
                        R.array.dog_breed_array_C, R.layout.spinner_dog_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewHolder.mSpinner.setAdapter(adapter);
        viewHolder.mSpinner.setOnItemSelectedListener(this);

        DogRecyclerViewAdapter.DogAdapterOnClickHandler handler = new DogRecyclerViewAdapter.DogAdapterOnClickHandler() {
            @Override
            public void onClick(String url, DogRecyclerViewAdapter.AdapterViewHolder vh) {
                Intent intent  = new Intent(getContext(), FullscreenActivity.class);
                intent.putExtra("EXTRA_URL", url);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);

            }
        };
        mAdapter = new DogRecyclerViewAdapter(getActivity(), handler, viewHolder.mEmptyView);


        viewHolder.mRecyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        viewHolder.mRecyclerView.setHasFixedSize(true);
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        viewHolder.mRecyclerView.setLayoutManager(layoutManager);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //String item = (String) parent.getItemAtPosition(position);
        if (isInternetAvailable()) {
            DogActivity activity = ((DogActivity) getActivity());
            String item = getActivity().getResources().getStringArray(R.array.dog_breed_array)[position];
            saveBreed(item);

            activity.mDogModel = new DogModel(activity.mDogModel.getToken(), item);
            activity.output.fetchDogActivityData(new DogRequest(activity.mDogModel));
            getLoaderManager().restartLoader(LOADER_ID, null, this);
        } else {
            Toast.makeText(getContext(), "No internet connection.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    static class ViewHolder {
        final RecyclerView mRecyclerView;
        final TextView mEmptyView;
        final Spinner mSpinner;
        ViewHolder(View view) {
            mRecyclerView = (RecyclerView) view.findViewById(R.id.dog_recyclerview);
            mEmptyView = (TextView) view.findViewById(R.id.view_empty);
            mSpinner = (Spinner) view.findViewById(R.id.dog_spinner);
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

        void onItemSelected(String url, DogRecyclerViewAdapter.AdapterViewHolder vh);
    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return  activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
