package sarayutwiangchai.catbook;

/**
 * Created by Sarayut Wiangchai on 19/6/2558.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sarayut Wiangchai on 1/6/2558.
 */
public class ConnectDB extends SQLiteOpenHelper {

    private final String TAG = getClass().getSimpleName();
    private SQLiteDatabase sqLiteDatabase;
    private Drawable dbDrawable;

    public ConnectDB(Context context) {
        super(context, myCatData.DATABASE_NAME, null, myCatData.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

/////////////////////////////////////////cat////////////////////////////////////////////////////////
        String CREATE_CAT_DATA_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s VARCHAR(15), %s CHAR, %s VARCHAR(45), %s DATE, %s INTEGER ,%s TEXT)",
                myCatData.TABLE,
                myCatData.Column.ID,
                myCatData.Column.NAME,
                myCatData.Column.GENDER,
                myCatData.Column.BREED,
                myCatData.Column.BIRTH,
                myCatData.Column.WEIGHT,
                myCatData.Column.PICTURE
        );

        Log.i(TAG, CREATE_CAT_DATA_TABLE);

        // create cat data table
        db.execSQL(CREATE_CAT_DATA_TABLE);
//////////////////////////////////////////////vaccine///////////////////////////////////////////////
        String CREATE_VACCINE_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s VARCHAR(15), %s VARCHAR(45), %s DATE, %s VARCHAR(45))",
                vaccineData.TABLE,
                vaccineData.Column.ID,
                vaccineData.Column.CAT,
                vaccineData.Column.NAME,
                vaccineData.Column.DATE,
                vaccineData.Column.INJECT);

        Log.i(TAG, CREATE_VACCINE_TABLE);

        // create cat data table
        db.execSQL(CREATE_VACCINE_TABLE);
///////////////////////////////////////////////expenses/////////////////////////////////////////////
        String CREATE_EXPENSES_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s VARCHAR(45), %s INTEGER, %s TEXT ,%s DATE , %s VARCHAR(45))",
                expensesData.TABLE,
                expensesData.Column.ID,
                expensesData.Column.NAME,
                expensesData.Column.AMOUNT,
                expensesData.Column.DETAIL,
                expensesData.Column.DATE,
                expensesData.Column.TYPE);

        Log.i(TAG, CREATE_EXPENSES_TABLE);

        // create cat data table
        db.execSQL(CREATE_EXPENSES_TABLE);


    }

    public List<String> CatList() {
        List<String> cats = new ArrayList<String>();

        sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query
                (myCatData.TABLE, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            cats.add(cursor.getLong(0) + " " +
                            cursor.getString(1)
            );

            cursor.moveToNext();
        }

        sqLiteDatabase.close();

        return cats;
    }


    public void addCat(myCatData cat) {
        sqLiteDatabase = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        //values.put(Friend.Column.ID, friend.getId());
        values.put(myCatData.Column.NAME, cat.getName());
        values.put(myCatData.Column.GENDER, cat.getGender());
        values.put(myCatData.Column.BREED, cat.getBreed());
        values.put(myCatData.Column.BIRTH, cat.getBirth());
        values.put(myCatData.Column.WEIGHT, cat.getWeight());
        values.put(myCatData.Column.PICTURE,cat.getImageByteArray());

        sqLiteDatabase.insert(myCatData.TABLE, null, values);

        sqLiteDatabase.close();
    }

    public myCatData getCat(String id) {

        sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(myCatData.TABLE,
                null,
                myCatData.Column.ID + " = ? ",
                new String[]{id},
                null,
                null,
                null,
                null);


        if (cursor != null) {
            cursor.moveToFirst();
        }

        myCatData cat = new myCatData();

        cat.setId((int) cursor.getLong(0));
        cat.setName(cursor.getString(1));
        cat.setGender(cursor.getString(2));
        cat.setBreed(cursor.getString(3));
        cat.setBirth(cursor.getString(4));
        cat.setWeight(cursor.getString(5));
        cat.setImageByteArray(cursor.getBlob(6));

        return cat;
    }

    public void updateCatData(myCatData cat) {

        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(myCatData.Column.ID, cat.getId());
        values.put(myCatData.Column.NAME, cat.getName());
        values.put(myCatData.Column.GENDER, cat.getGender());
        values.put(myCatData.Column.BREED, cat.getBreed());
        values.put(myCatData.Column.BIRTH, cat.getBirth());
        values.put(myCatData.Column.WEIGHT, cat.getWeight());
        values.put(myCatData.Column.PICTURE, cat.getImageByteArray());

        int row = sqLiteDatabase.update(myCatData.TABLE,
                values,
                myCatData.Column.ID + " = ? ",
                new String[]{String.valueOf(cat.getId())});

        sqLiteDatabase.close();
    }

    public void deleteCat(String id) {

        sqLiteDatabase = this.getWritableDatabase();

    /*sqLiteDatabase.delete(Friend.TABLE, Friend.Column.ID + " = ? ",
            new String[] { String.valueOf(friend.getId()) });*/
        sqLiteDatabase.delete(myCatData.TABLE, myCatData.Column.ID + " = " + id, null);

        sqLiteDatabase.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //////////////////////Vaccine///////////////////////////////////////////////////////////////////
    public void addVaccine(vaccineData vaccine) {

        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //values.put(Friend.Column.ID, friend.getId());
        values.put(vaccineData.Column.NAME, vaccine.getName());
        values.put(vaccineData.Column.CAT, vaccine.getCat());
        values.put(vaccineData.Column.DATE, vaccine.getDate());
        values.put(vaccineData.Column.INJECT, vaccine.getInject());

        sqLiteDatabase.insert(vaccineData.TABLE, null, values);

        sqLiteDatabase.close();


    }

    public List<String> getAllCat() {

        List<String> cats = new ArrayList<String>();

        sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query
                (myCatData.TABLE, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            cats.add(
                    cursor.getString(1)
            );

            cursor.moveToNext();
        }

        sqLiteDatabase.close();

        return cats;
    }

    ////////////////////////////////////////////Expenses///////////////////////////////////////////
    public void addExpenses(expensesData expenses) {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(Friend.Column.ID, friend.getId());
        values.put(expensesData.Column.NAME, expenses.getName());
        values.put(expensesData.Column.AMOUNT, expenses.getAmount());
        values.put(expensesData.Column.DETAIL, expenses.getDetail());
        values.put(expensesData.Column.DATE, expenses.getDate());
        values.put(expensesData.Column.TYPE, expenses.getType());

        sqLiteDatabase.insert(expensesData.TABLE, null, values);

        sqLiteDatabase.close();
    }

    public List<String> ExpensesList() {
        List<String> expenses = new ArrayList<String>();

        sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query
                (expensesData.TABLE, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            expenses.add(cursor.getLong(0) + " " +
                            cursor.getString(1)
            );

            cursor.moveToNext();
        }

        sqLiteDatabase.close();

        return expenses;
    }

    public void updateExpense(expensesData expenses) {

        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(expensesData.Column.ID, expenses.getId());
        values.put(expensesData.Column.NAME, expenses.getName());
        values.put(expensesData.Column.AMOUNT, expenses.getAmount());
        values.put(expensesData.Column.DETAIL, expenses.getDetail());
        values.put(expensesData.Column.DATE, expenses.getDate());
        values.put(expensesData.Column.TYPE, expenses.getType());

        int row = sqLiteDatabase.update(expensesData.TABLE,
                values,
                expensesData.Column.ID + " = ? ",
                new String[]{String.valueOf(expenses.getId())});

        sqLiteDatabase.close();
    }

    public expensesData getExpenses(String id) {

        sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(expensesData.TABLE,
                null,
                expensesData.Column.ID + " = ? ",
                new String[]{id},
                null,
                null,
                null,
                null);


        if (cursor != null) {
            cursor.moveToFirst();
        }

        expensesData expenses = new expensesData();

        expenses.setId((int) cursor.getLong(0));
        expenses.setName(cursor.getString(1));
        expenses.setAmount(cursor.getString(2));
        expenses.setDetail(cursor.getString(3));
        expenses.setDate(cursor.getString(4));
        expenses.setType(cursor.getString(5));

        return expenses;
    }

    public void deleteExpenses(String id) {

        sqLiteDatabase = this.getWritableDatabase();

    /*sqLiteDatabase.delete(Friend.TABLE, Friend.Column.ID + " = ? ",
            new String[] { String.valueOf(friend.getId()) });*/
        sqLiteDatabase.delete(expensesData.TABLE, expensesData.Column.ID + " = " + id, null);

        sqLiteDatabase.close();
    }



}