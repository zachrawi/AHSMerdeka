package com.zachrawi.ahsmerdeka;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AHSDBHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "ahs.db";
    private static int DB_VERSION = 1;

    public AHSDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_CUSTOMERS = "CREATE TABLE " + CustomerContract.CustomerTable.TABLE_NAME +
                " (" +
                CustomerContract.CustomerTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CustomerContract.CustomerTable.COLUMN_NAME + " TEXT, " +
                CustomerContract.CustomerTable.COLUMN_ADDRESS + " TEXT, " +
                CustomerContract.CustomerTable.COLUMN_PHONE + " TEXT" +
                ") ";

        db.execSQL(CREATE_TABLE_CUSTOMERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addCustomer(Customer customer) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(CustomerContract.CustomerTable.COLUMN_NAME, customer.getName());
        cv.put(CustomerContract.CustomerTable.COLUMN_ADDRESS, customer.getAddress());
        cv.put(CustomerContract.CustomerTable.COLUMN_PHONE, customer.getPhone());

        db.insert(CustomerContract.CustomerTable.TABLE_NAME, null, cv);
    }

    public ArrayList<Customer> getCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String[] columns = {
                CustomerContract.CustomerTable.COLUMN_NAME,
                CustomerContract.CustomerTable.COLUMN_ADDRESS,
                CustomerContract.CustomerTable.COLUMN_PHONE
        };

        String selection = "";

        Cursor cursor = db.query(
                CustomerContract.CustomerTable.TABLE_NAME,
                columns,
                selection,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(CustomerContract.CustomerTable.COLUMN_NAME));
                String address = cursor.getString(cursor.getColumnIndex(CustomerContract.CustomerTable.COLUMN_ADDRESS));
                String phone = cursor.getString(cursor.getColumnIndex(CustomerContract.CustomerTable.COLUMN_PHONE));

                Customer customer = new Customer();
                customer.setName(name);
                customer.setAddress(address);
                customer.setPhone(phone);

                customers.add(customer);
            } while (cursor.moveToNext());
        }

        return customers;
    }
}
