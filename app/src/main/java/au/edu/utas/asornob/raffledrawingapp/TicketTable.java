package au.edu.utas.asornob.raffledrawingapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class TicketTable {
    public static final String TABLE_NAME = "ticket";
    public static final String KEY_ID = "id";
    public static final String KEY_PURCHASE_TIME = "purchaseTime";
    public static final String KEY_PRICE = "price";
    public static final String RAFFLE_ID = "FK_raffle";
    public static final String CUSTOMER_ID = "FK_customer";
    public static final String TICKET_NO = "TicketNo";

    public static final String CREATE_STATEMENT = //"DROP TABLE "
            //+ TABLE_NAME + "; " +
            "CREATE TABLE "
            + TABLE_NAME
            + " (" + KEY_ID + " integer primary key autoincrement, "
            //+ KEY_PURCHASE_TIME + " datetime not null, "
            + KEY_PRICE + " double not null, "
            + RAFFLE_ID + " integer not null, " //not null
            + CUSTOMER_ID + " integer not null, " //not null
            + TICKET_NO + " integer not null"
            + ");";

    public static void insert(SQLiteDatabase database, Ticket ticket) {
        ContentValues values = new ContentValues();
        //values.put(KEY_PURCHASE_TIME, ticket.getPurchaseTime().toString());
        values.put(KEY_PRICE, ticket.getPrice());
        values.put(RAFFLE_ID, ticket.getRaffleId());
        values.put(CUSTOMER_ID, ticket.getCustomer().getId());
        values.put(TICKET_NO, ticket.getTicketNo());
        database.insert(TABLE_NAME, null, values);
    }

    public static Ticket createFromCursor(Cursor c, SQLiteDatabase db)
    {
        if (c == null || c.isAfterLast() || c.isBeforeFirst())
        {
            return null;
        }
        else
        {
            Ticket ticket = new Ticket();

            int customerId = c.getInt(c.getColumnIndex(CUSTOMER_ID));
            Customer customer = CustomerTable.selectCustomer(db, customerId);
            Log.d("customer: ", customer.getName());

            ticket.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            //ticket.setPurchaseTime(c.getType(c.getColumnIndex(KEY_ID)));
            ticket.setPrice(c.getDouble(c.getColumnIndex(KEY_PRICE)));
            ticket.setCustomer(customer);
            ticket.setRaffleId(c.getInt(c.getColumnIndex(RAFFLE_ID)));
            return ticket;
        }
    }

    public static ArrayList<Ticket> selectAll(SQLiteDatabase db) {
        ArrayList<Ticket> results = new ArrayList<Ticket>();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (c != null) {
            //make sure the cursor is at the start of the list
            c.moveToFirst();
            //loop through until we are at the end of the list
            while (!c.isAfterLast()) {
                Ticket ticket = createFromCursor(c, db);
                results.add(ticket);
                //increment the cursor
                c.moveToNext();
            }
        }

        return results;
    }

    public static ArrayList<Ticket> raffleTickets(SQLiteDatabase db, int id)
    {
        ArrayList<Ticket> results = new ArrayList<Ticket>();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+ RAFFLE_ID + "=" + id, null);
        if(c != null)
        {
            c.moveToFirst();
            while(!c.isAfterLast()) {
                Ticket ticket = createFromCursor(c, db);
                results.add(ticket);
                c.moveToNext();
            }
        }

        return results;
    }

    public static Customer ticketOwner(SQLiteDatabase db, int id) {
    Ticket ticket;
    Customer result;
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + "=" + id, null);
        if (c != null) {
            c.moveToFirst();
            ticket = createFromCursor(c, db);
            result = ticket.getCustomer();
            return result;
        }

        return null;
    }

}


