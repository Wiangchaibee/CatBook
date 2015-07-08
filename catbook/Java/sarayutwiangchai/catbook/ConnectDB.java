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
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_CAT_DATA_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s VARCHAR(15), %s CHAR, %s VARCHAR(45), %s DATE, %s INTEGER ,%s TEXT )",
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
        //////////////////////////////////////////Veterinarian//////////////////////////////////////////////
        String CREATE_VETERINARIAN_TABLE = String.format("CREATE TABLE %s" +
                        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s VARCHAR(20), %s VARCHAR(20) , %s VARCHAR(45), %s VARCHAR(15) , %s VARCHAR(45)," +
                        " FOREIGN KEY (%s) REFERENCES %s)",
                veterinarianData.TABLE,
                veterinarianData.Column.ID,
                veterinarianData.Column.Name,
                veterinarianData.Column.Last_name,
                veterinarianData.Column.Address,
                veterinarianData.Column.Phone_Number,
                veterinarianData.Column.Email,
                myCatData.Column.ID,
                myCatData.Column.REFERENCES_MYCATDATA_ID


        );
        Log.i(TAG, CREATE_VETERINARIAN_TABLE);
        // create cat data table
        db.execSQL(CREATE_VETERINARIAN_TABLE);

/////////////////////////////////////////cat////////////////////////////////////////////////////////
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


////////////////////////////////////addVaccine_Name/////////////////////////////////////////////////
        String CREATE_ADD_VACCINE_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s VARCHAR(45))",
                addVaccineData.TABLE,
                addVaccineData.Column.ID,
                addVaccineData.Column.NAME);

        Log.i(TAG, CREATE_ADD_VACCINE_TABLE);

        // create cat data table
        db.execSQL(CREATE_ADD_VACCINE_TABLE);
        String sql = "INSERT or replace INTO " + addVaccineData.TABLE + "( " + addVaccineData.Column.NAME +
                ") VALUES ('วัคซีนพิษสุนัขบ้า'),('วัคซีนไข้หัด'),('วัคซีนลิวคีเมีย'),('FIP (เยื่อบุช่องท้องอักเสบ)'),('FIV (เอดส์แมว)'),('อื่นๆ')";

        db.execSQL(sql);

////////////////////////////////////////////////////////////////////////////////////////////////////
        String CREATE_ADD_EXPENSE_TYPE_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s VARCHAR(45))",
                addExpenseType.TABLE,
                addExpenseType.Column.ID,
                addExpenseType.Column.NAME);

        Log.i(TAG, CREATE_ADD_EXPENSE_TYPE_TABLE);

        // create cat data table
        db.execSQL(CREATE_ADD_EXPENSE_TYPE_TABLE);

        String sql2 ="INSERT or replace INTO " + addExpenseType.TABLE + "( " + addExpenseType.Column.NAME +
                ") VALUES ('อาหาร'),('รักษาพยาบาล'),('อุปกรณ์'),('อื่นๆ')";

        db.execSQL(sql2);

////////////////////////////////////////////////////////////////////////////////////////////////////

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

            cats.add( cursor.getLong(0)+" "+
                      cursor.getString(1)
            );

            cursor.moveToNext();
        }

        sqLiteDatabase.close();

        return cats;
    }

    public List<String> CatList_veterinarian() {
        List<String> cat_veterinarian = new ArrayList<String>();

        sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query
                (myCatData.TABLE, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            cat_veterinarian.add( cursor.getLong(0)+" "+
                            cursor.getString(1)
            );

            cursor.moveToNext();
        }

        sqLiteDatabase.close();

        return cat_veterinarian;
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

    ////////////////////////////////////Veterinarian////////////////////////////////////////////////

    public void addVeterinarian(veterinarianData veterinarian) {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(veterinarianData.Column.Name, veterinarian.getName());
        values.put(veterinarianData.Column.Last_name, veterinarian.getLast_name());
        values.put(veterinarianData.Column.Address, veterinarian.getAddress());
        values.put(veterinarianData.Column.Phone_Number, veterinarian.getPhone_Number());
        values.put(veterinarianData.Column.Email, veterinarian.getEmail());

        sqLiteDatabase.insert(veterinarianData.TABLE, null, values);

        sqLiteDatabase.close();
    }

    public void updateVeterinarian(veterinarianData Veterinarian) {

        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(veterinarianData.Column.ID, Veterinarian.getId());
        values.put(veterinarianData.Column.Name, Veterinarian.getName());
        values.put(veterinarianData.Column.Last_name, Veterinarian.getLast_name());
        values.put(veterinarianData.Column.Address, Veterinarian.getAddress());
        values.put(veterinarianData.Column.Phone_Number, Veterinarian.getPhone_Number());
        values.put(veterinarianData.Column.Email, Veterinarian.getEmail());

            int row = sqLiteDatabase.update(veterinarianData.TABLE,
                values,
                veterinarianData.Column.ID + " = ? ",
                new String[]{String.valueOf(Veterinarian.getId())});

        sqLiteDatabase.close();

    }

    public List<String> VeterinarianList() {
        List<String> veterinarians = new ArrayList<String>();

        sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query
                (veterinarianData.TABLE, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            veterinarians.add(cursor.getLong(0) + " " +
                            cursor.getString(1)
            );

            cursor.moveToNext();
        }

        sqLiteDatabase.close();

        return veterinarians;
    }

    public veterinarianData getVeterinarian(String id) {

        sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(veterinarianData.TABLE,
                null,
                veterinarianData.Column.ID + " = ? ",
                new String[]{id},
                null,
                null,
                null,
                null);


        if (cursor != null) {
            cursor.moveToFirst();
        }

        veterinarianData veterinarian = new veterinarianData();

        veterinarian.setId((int) cursor.getLong(0));
        veterinarian.setName(cursor.getString(1));
        veterinarian.setLast_name(cursor.getString(2));
        veterinarian.setAddress(cursor.getString(3));
        veterinarian.setPhone_Number(cursor.getString(4));
        veterinarian.setEmail(cursor.getString(5));

        return veterinarian;
    }
    public void deleteVeterinarian(String id) {

        sqLiteDatabase = this.getWritableDatabase();

    /*sqLiteDatabase.delete(Friend.TABLE, Friend.Column.ID + " = ? ",
            new String[] { String.valueOf(friend.getId()) });*/
        sqLiteDatabase.delete(veterinarianData.TABLE, veterinarianData.Column.ID + " = " + id, null);

        sqLiteDatabase.close();
    }

///////////////////////////////////////addVaccine_Name//////////////////////////////////////////////

    public List<String> getAllVaccine() {

        List<String> vaccine = new ArrayList<String>();

        sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query
                (addVaccineData.TABLE, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            vaccine.add(
                    cursor.getString(1)
            );

            cursor.moveToNext();
        }

        sqLiteDatabase.close();

        return vaccine;
    }

        public void addVaccineData(addVaccineData vaccine) {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(addVaccineData.Column.NAME, vaccine.getName());


        sqLiteDatabase.insert(addVaccineData.TABLE, null, values);

        sqLiteDatabase.close();
    }


////////////////////////////////////////addType/////////////////////////////////////////////////////

    public List<String> getAllType() {

        List<String> type = new ArrayList<String>();

        sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query
                (addExpenseType.TABLE, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            type.add(
                    cursor.getString(1)
            );

            cursor.moveToNext();
        }

        sqLiteDatabase.close();

        return type;
    }

    public void addType(addExpenseType type) {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(addExpenseType.Column.NAME, type.getName());


        sqLiteDatabase.insert(addExpenseType.TABLE, null, values);

        sqLiteDatabase.close();
    }



////////////////////////////////////////////////////////////////////////////////////////////////////



}