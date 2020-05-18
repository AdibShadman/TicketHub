package au.edu.utas.asornob.raffledrawingapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import au.edu.utas.asornob.raffledrawingapp.Customer;

public class CustomerTable {
    public static final String TABLE_NAME = "customer";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";

    public static final String CREATE_STATEMENT = //"DROP TABLE "
            //+ TABLE_NAME + "; "
            "CREATE TABLE "
            + TABLE_NAME
            + " (" + KEY_ID + " integer primary key autoincrement, "
            + KEY_NAME + " string not null, "
            + KEY_EMAIL + " string not null, "
            + KEY_PHONE + " string not null"
            +" );";

    public static void insert(SQLiteDatabase database, Customer customer) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, customer.getName());
        values.put(KEY_EMAIL, customer.getEmail());
        values.put(KEY_PHONE, customer.getPhone());
        database.insert(TABLE_NAME, null, values);
    }

    public static Customer createFromCursor(Cursor c)
    {
        if (c == null || c.isAfterLast() || c.isBeforeFirst())
        {
            return null;
        }
        else
        {
            Customer customer = new Customer();
            customer.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            customer.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            customer.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
            customer.setPhone(c.getString(c.getColumnIndex(KEY_PHONE)));
            return customer;
        }
    }

    public static Customer selectCustomer(SQLiteDatabase db, int id) {
        Customer result;
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + "=" + id, null);
        if (c != null) {
            c.moveToFirst();
            result = createFromCursor(c);
            return result;
    }

        return null;
    }

    public static ArrayList<Customer> selectAll(SQLiteDatabase db) {
        ArrayList<Customer> results= new ArrayList<Customer>();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            while(!c.isAfterLast()) {
                results.add(createFromCursor(c));
                c.moveToNext();
            }
        }

        return results;
    }
}