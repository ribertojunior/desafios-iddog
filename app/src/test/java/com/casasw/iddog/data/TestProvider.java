package com.casasw.iddog.data;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import androidx.test.core.app.ApplicationProvider;

import static junit.framework.TestCase.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class TestProvider {
    private static final String[] DOG_COLUMNS = {
            DogContract.DogEntry.TABLE_NAME + "." + DogContract.DogEntry._ID,
            DogContract.DogEntry.COLUMN_DOG_BREED,
            DogContract.DogEntry.COLUMN_DOG_IMAGE_URL
    };
    private static final int COL_ID = 0;
    private static final int COL_BREED = 1;
    private static final int COL_URL = 2;
    @Before
    public void setup() {
        ApplicationProvider.getApplicationContext().deleteDatabase(DogDbHelper.DATABASE_NAME);
    }


    @Test
    public void testCreate(){
        ApplicationProvider.getApplicationContext().deleteDatabase(DogDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new DogDbHelper(
                ApplicationProvider.getApplicationContext()).getWritableDatabase();
        assertTrue(db.isOpen());

        ContentResolver resolver = ApplicationProvider.getApplicationContext().getContentResolver();
        Assert.assertNotNull(resolver);
        Uri uri = resolver.insert(DogContract.DogEntry.CONTENT_URI, TestUtils.createDogValues("husky"));
        System.out.println(uri);
        Assert.assertEquals("content://com.casasw.iddog.app/dog/1", uri.toString());

        String sortOrder = DogContract.DogEntry.COLUMN_DOG_BREED + " ASC";
        Cursor c = resolver.query(DogContract.DogEntry.CONTENT_URI,
                DOG_COLUMNS,
                DogProvider.sDogBreedSelection,
                new String[]{"husky"},
                sortOrder);
        Assert.assertNotNull(c);
        Assert.assertTrue(c.getCount()>0);
        while (c.moveToNext()) {
            System.out.println(c.getString(COL_ID) + " - " +c.getString(COL_BREED) + " - " + c.getString(COL_URL));
        }
        c.close();
        int up = resolver.update(DogContract.DogEntry.CONTENT_URI, TestUtils.createDogValues("labrador"),DogProvider.sDogBreedSelection, new String[]{"husky"});
        Assert.assertEquals(1, up);
        int del = resolver.delete(DogContract.DogEntry.CONTENT_URI, DogProvider.sDogBreedSelection, new String[]{"labrador"});
        Assert.assertEquals(1, del);
        int bulk = resolver.bulkInsert(DogContract.DogEntry.CONTENT_URI, TestUtils.createDogValues());
        Assert.assertEquals(100, bulk);
        c = resolver.query(DogContract.DogEntry.CONTENT_URI,
                DOG_COLUMNS,
                null,
                null,
                sortOrder);
        Assert.assertNotNull(c);
        Assert.assertTrue(c.getCount()>0);
        while (c.moveToNext()) {
            System.out.println(c.getString(COL_ID) + " - " +c.getString(COL_BREED) + " - " + c.getString(COL_URL));
        }
        c.close();




        db.close();
    }
}
