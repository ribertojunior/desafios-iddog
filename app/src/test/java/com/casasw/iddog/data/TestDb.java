package com.casasw.iddog.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.HashSet;

import androidx.test.core.app.ApplicationProvider;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class TestDb {

    public static final String LOG_TAG = TestDb.class.getSimpleName();
    private Context mContext;
    @Before
    public void setup(){
        mContext = ApplicationProvider.getApplicationContext();
        deleteTheDatabase();
    }

    
    private void deleteTheDatabase() {
         mContext.deleteDatabase(DogDbHelper.DATABASE_NAME);
    }

    


    @Test
    public void testCreateDb() throws Throwable {

        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(DogContract.DogEntry.TABLE_NAME);


        mContext.deleteDatabase(DogDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new DogDbHelper(
                this.mContext).getWritableDatabase();
        assertTrue(db.isOpen());


        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());


        do {
            tableNameHashSet.remove(c.getString(0));
        } while( c.moveToNext() );



        assertTrue("Error: Your database was created without both entries",
                tableNameHashSet.isEmpty());


        c = db.rawQuery("PRAGMA table_info(" + DogContract.DogEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        final HashSet<String> dogColumnHashSet = new HashSet<String>();
        dogColumnHashSet.add(DogContract.DogEntry._ID);
        dogColumnHashSet.add(DogContract.DogEntry.COLUMN_DOG_BREED);
        dogColumnHashSet.add(DogContract.DogEntry.COLUMN_DOG_IMAGE_URL);


        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            dogColumnHashSet.remove(columnName);
        } while(c.moveToNext());

    
        assertTrue("Error: The database doesn't contain all of the required dog entry columns",
                dogColumnHashSet.isEmpty());
        db.close();
    }

    @Test
    public void testDogTable() {

        long locationRowId = insertDog();


        SQLiteDatabase db = new DogDbHelper(
                this.mContext).getWritableDatabase();

        ContentValues cv = TestUtils.createDogValues("husky");

        long in = db.insert(DogContract.DogEntry.TABLE_NAME,null, cv);
        assertTrue("Error: Insertion in dog table has fail.", in != -1 );

        Cursor c = db.query(DogContract.DogEntry.TABLE_NAME, null,
                null, null, null, null, null);

        assertTrue("Error: Dog table select has fail.",
                c.moveToFirst());

        TestUtils.validateCurrentRecord("Error: The returning cursor is not equals to ContentValues inserted.", c, cv);
        long up = db.update(DogContract.DogEntry.TABLE_NAME, cv, DogProvider.sDogBreedSelection,new String[]{"husky"});
        assertTrue("Error: Update in dog table has fail.", up == 1);
        long del = db.delete(DogContract.DogEntry.TABLE_NAME, DogProvider.sDogBreedSelection, new String[]{"husky"});
        assertTrue("Error: Delete in dog table has fail.", del == 1);


        c.close();
        db.close();
    }



    public long insertDog() {
        String testBreed = "husky";
        String testUrl = "https://dog.ceo/api/img/husky/n02110185_10047.jpg";


        SQLiteDatabase db = new DogDbHelper(
                this.mContext).getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DogContract.DogEntry.COLUMN_DOG_BREED, testBreed);
        cv.put(DogContract.DogEntry.COLUMN_DOG_IMAGE_URL, testUrl);



        long in = db.insert(DogContract.DogEntry.TABLE_NAME,null, cv);
        assertTrue("Error: Insertion in dog table has fail.", in != -1 );

        Cursor c = db.query(DogContract.DogEntry.TABLE_NAME, null,
                null, null, null, null, null);

        assertTrue("Error: Location table select has fail.",
                c.moveToFirst());


        TestUtils.validateCurrentRecord("Error: The returning cursor is not equals to ContentValues inserted.", c, cv);
        // (you can use the validateCurrentRecord function in TestUtilities to validate the
        // query if you like)
        assertFalse( "Error: More than one record returned from location query",
                c.moveToNext() );

        // Finally, close the cursor and database
        c.close();
        db.close();

        return in;
    }
}

