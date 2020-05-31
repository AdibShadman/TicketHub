package au.edu.utas.asornob.raffledrawingapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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

    public static int insert(SQLiteDatabase database, Customer customer) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, customer.getName());
        values.put(KEY_EMAIL, customer.getEmail());
        values.put(KEY_PHONE, customer.getPhone());
        return (int) database.insert(TABLE_NAME, null, values);
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

    public static Customer selectCustomer(SQLiteDatabase db, int id )
    {
        Customer result;
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + "=" + id , null);
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

    public static ArrayList<Customer> selectByNameFilter(SQLiteDatabase db, String text)
    {
        ArrayList<Customer> customers = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME, null, KEY_NAME + " LIKE " + "'" + text + "%'", null, null, null, null);
        if(c != null)
        {
            ((Cursor) c).moveToFirst();

            while(!c.isAfterLast())
            {
                Customer customer = createFromCursor(c);
                customers.add(customer);
                c.moveToNext();
            }
        }
        return customers;

    }

    public static void updateCustomer(SQLiteDatabase db, int id, String name, String email, String phone)
    {
        String setName = "", setEmail = "", setPhone = "";
        boolean checkName, checkEmail, checkPhone;
        checkName = name != null;
        checkEmail = email != null;
        checkPhone = phone != null;
        if(checkName) {
            setName = " " + KEY_NAME + " = '" + name + "'";
            if(checkEmail || checkPhone) {
                setName += ",";
            }
        }
        if(checkEmail) {
            setEmail = " " + KEY_EMAIL + " = '" + email + "'";
            if(checkPhone) {
                setEmail += ",";
            }
        }
        if(checkPhone) {
            setPhone = " " + KEY_PHONE + " = '" + phone + "'";
        }
        db.execSQL("UPDATE " + TABLE_NAME + " " +
                "SET" + setName + setEmail + setPhone + " " +
                "WHERE "+ KEY_ID + " = " + id + ";");

    }





}
