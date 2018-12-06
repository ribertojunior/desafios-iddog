package com.casasw.iddog.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class DogContract {

    public static final String CONTENT_AUTHORITY = "com.casasw.iddog.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_DOG = "dog";
    public static final String PATH_USER = "user";

    public static final class DogEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DOG).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DOG;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DOG;

        public static final String TABLE_NAME = "dog";

        public static final String COLUMN_DOG_BREED = "breed";
        public static final String COLUMN_DOG_IMAGE_URL = "image_url";

        public static Uri buildDogUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildDogWithBreed(String breed) {
            return CONTENT_URI.buildUpon().appendPath(breed).build();
        }

        public static String getBreedFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }



}
