package com.casasw.iddog.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

public class DogProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DogDbHelper mOpenHelper;

    private static final int DOG = 678;
    private static final int DOG_WITH_BREED = 998;
    private static final int USER = 97;
    private static final int USER_WITH_EMAIL = 3;



    protected static final String sDogBreedSelection =
            DogContract.DogEntry.TABLE_NAME +
                    "." + DogContract.DogEntry.COLUMN_DOG_BREED + " = ? ";


    private Cursor getDogByBreed(Uri uri, String[] projection, String sortOrder) {
        String breed = DogContract.DogEntry.getBreedFromUri(uri);
        String[] selectionArgs = new String[]{breed};

        return  mOpenHelper.getReadableDatabase().query(DogContract.DogEntry.TABLE_NAME,
                projection,
                sDogBreedSelection,
                selectionArgs,
                null,
                null,
                sortOrder);
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DogDbHelper(getContext());
        return true;
    }

    
    @Override
    public Cursor query( Uri uri,  String[] projection,  String selection,
                         String[] selectionArgs,  String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case DOG: {
                retCursor = mOpenHelper.getReadableDatabase().query(DogContract.DogEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, null, sortOrder);
                break;
            }
            case DOG_WITH_BREED: {
                retCursor = getDogByBreed(uri, projection, sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType( Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case DOG:
                return DogContract.DogEntry.CONTENT_TYPE;
            case DOG_WITH_BREED:
                return DogContract.DogEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

    }

    
    @Override
    public Uri insert( Uri uri,  ContentValues contentValues) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match){
            case DOG: {
                long _id = db.insert(DogContract.DogEntry.TABLE_NAME,
                        null, contentValues);
                if (_id > 0)
                    returnUri = DogContract.DogEntry.buildDogUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;

            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int del = 0;
        if (selection == null)
            selection = "1";

        switch (match){
            case DOG: {
                del = db.delete(DogContract.DogEntry.TABLE_NAME,
                        selection, selectionArgs);
                break;

            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (del != 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return del;
    }

    @Override
    public int update( Uri uri,  ContentValues contentValues,
                       String selection,  String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int ups = 0;

        switch (match){
            case DOG: {
                ups = db.update(DogContract.DogEntry.TABLE_NAME, contentValues,
                        selection, selectionArgs);
                break;

            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return ups;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri,@NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match){
            case DOG: {
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value :
                            values) {
                        long _id =
                                db.insert(DogContract.DogEntry.TABLE_NAME,
                                        null, value);
                        if (_id != -1)
                            returnCount++;
                    }
                    db.setTransactionSuccessful();;
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            }
            default:
                return super.bulkInsert(uri, values);
        }

    }

    static UriMatcher buildUriMatcher() {

        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(DogContract.CONTENT_AUTHORITY, DogContract.PATH_DOG, DOG);
        uriMatcher.addURI(DogContract.CONTENT_AUTHORITY,
                DogContract.PATH_DOG + "/*", DOG_WITH_BREED);
        uriMatcher.addURI(DogContract.CONTENT_AUTHORITY, DogContract.PATH_USER, USER);
        uriMatcher.addURI(DogContract.CONTENT_AUTHORITY,
                DogContract.PATH_USER + "/*", USER_WITH_EMAIL);



        return uriMatcher;
    }

}
