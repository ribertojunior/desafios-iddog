package com.casasw.iddog.data;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestUtils {

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    public static ContentValues createDogValues(String breed) {
        ContentValues cv = new ContentValues();
        cv.put(DogContract.DogEntry.COLUMN_DOG_BREED, breed);
        cv.put(DogContract.DogEntry.COLUMN_DOG_IMAGE_URL,
                "https://dog.ceo/api/img/husky/n02110185_10047.jpg");
        return cv;
    }

    public static ContentValues[] createDogValues(){
        Vector<ContentValues> contentValuesVector = new Vector<>(100);
        for (int i = 0; i < 100; i++) {
            ContentValues cv = new ContentValues();
            cv.put(DogContract.DogEntry.COLUMN_DOG_BREED, "breed-"+ (new Random()).nextInt());
            cv.put(DogContract.DogEntry.COLUMN_DOG_IMAGE_URL, "str"+ (new Random()).nextInt());
            contentValuesVector.add(cv);
        }
        ContentValues[] cvArray = new ContentValues[contentValuesVector.size()];
        contentValuesVector.toArray(cvArray);
        return cvArray;
    }
}
