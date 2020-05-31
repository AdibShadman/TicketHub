package au.edu.utas.asornob.raffledrawingapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.view.ViewDebug;

import java.util.ArrayList;
import java.util.Date;

import au.edu.utas.asornob.raffledrawingapp.Raffle;

public class RaffleTable
{
    public static final String TABLE_NAME = "raffle";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_TOTAL_TICKETS = "total_tickets";
    public static final String KEY_TICKET_PRICE = "ticket_price";
    public static final String KEY_START_DATE = "start_date";
    public static final String KEY_TYPE = "type";
    public static final String KEY_PHOTO = "photo";
    public static final String KEY_LAST_TICKET = "last_ticket";
    public static final String KEY_WINNERS = "winners";
    public static final String KEY_DRAWN = "drawn";


    public static final String CREATE_STATEMENT = //"DROP TABLE "
            //+ TABLE_NAME + "; " +
            "CREATE TABLE "
            + TABLE_NAME
            + " (" + KEY_ID + " integer primary key autoincrement, "
            + KEY_NAME + " string not null, "
            + KEY_DESCRIPTION + " string not null, "
            + KEY_TOTAL_TICKETS + " int not null, "
            + KEY_TICKET_PRICE + " double not null, "
            + KEY_START_DATE + " long not null, "
            + KEY_TYPE + " string not null, "
            + KEY_PHOTO + " blob, "
            + KEY_LAST_TICKET + " int default 0, "
            + KEY_WINNERS + " int default 3, "
            + KEY_DRAWN + " int default 0"
            +");";

    public static Raffle createFromCursor(Cursor c)
    {
        if (c == null || c.isAfterLast() || c.isBeforeFirst())
        {
            return null;
        }
        else
        {
            Raffle raffle = new Raffle();
            raffle.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            raffle.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            raffle.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
            raffle.setTotalTickets(c.getInt(c.getColumnIndex(KEY_TOTAL_TICKETS)));
            raffle.setTicketPrice(c.getDouble(c.getColumnIndex(KEY_TICKET_PRICE)));
            raffle.setStartDate(new Date(c.getLong(c.getColumnIndex(KEY_START_DATE))));

            raffle.setRaffleType(c.getString(c.getColumnIndex(KEY_TYPE)));
            raffle.setLastTicket(c.getInt(c.getColumnIndex(KEY_LAST_TICKET)));
            raffle.setWinners(c.getInt(c.getColumnIndex(KEY_WINNERS)));
            raffle.setDrawn(c.getInt(c.getColumnIndex(KEY_DRAWN)));

            String uri;

            if(c.getString(c.getColumnIndex(KEY_PHOTO)) != null)
            {
                uri = c.getString(c.getColumnIndex(KEY_PHOTO));
                Uri photo = Uri.parse(uri);
                raffle.setPhoto(photo);
            }
            return raffle;
        }
    }

    public static void insert(SQLiteDatabase database, Raffle raffle)
    {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, raffle.getName());
        values.put(KEY_DESCRIPTION, raffle.getDescription());
        values.put(KEY_TOTAL_TICKETS, raffle.getTotalTickets());
        values.put(KEY_TICKET_PRICE, raffle.getTicketPrice());
        values.put(KEY_START_DATE, raffle.getStartDate().getTime());

        values.put(KEY_TYPE, raffle.getRaffleType());
        values.put(KEY_LAST_TICKET, raffle.getLastTicket());
        //values.put(KEY_WINNERS, raffle.getWinners());
        if(raffle.getPhoto() != null)
        {
            values.put(KEY_PHOTO,raffle.getPhoto().toString());
        }
        database.insert(TABLE_NAME, null, values);
    }

    public static ArrayList<Raffle> selectAll(SQLiteDatabase db)
    {
        ArrayList<Raffle> results = new ArrayList<Raffle>();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (c != null) {
            //make sure the cursor is at the start of the list
            c.moveToFirst();
            //loop through until we are at the end of the list
            while (!c.isAfterLast()) {
                Log.d("Run: ", "again");
                Raffle raffle = createFromCursor(c);
                results.add(raffle);
                //increment the cursor
                c.moveToNext();
            }
        }

        return results;
    }

    public static Raffle selectRaffle(SQLiteDatabase db, int id)
    {
        Raffle result;
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+ KEY_ID + "=" + id, null);
        if(c != null)
        {
            c.moveToFirst();
            if(c != null)
            {
                result = createFromCursor(c);
                return result;
            }
        }

        return null;
    }

    public static void deleteById(SQLiteDatabase database, Raffle raffle)
    {
        database.delete(TABLE_NAME, KEY_ID +"=" + raffle.getId(), null);
    }

    public static void update(SQLiteDatabase database, Raffle raffle)
    {
        ContentValues values = new ContentValues();

        values.put(KEY_ID, raffle.getId());
        values.put(KEY_NAME, raffle.getName());
        values.put(KEY_DESCRIPTION, raffle.getDescription());
        values.put(KEY_TICKET_PRICE, raffle.getTicketPrice());
        values.put(KEY_TYPE, raffle.getRaffleType());
        values.put(KEY_START_DATE, raffle.getStartDate().getTime());
        values.put(KEY_LAST_TICKET, raffle.getLastTicket());
        //values.put(KEY_WINNERS, raffle.getWinners());
        values.put(KEY_DRAWN, raffle.getDrawn());
        database.update(TABLE_NAME, values, KEY_ID +"= ?",new String[] {""+raffle.getId()});
    }

    public static void setLastTicket(SQLiteDatabase db, int id, int quantity) {
        ContentValues values = new ContentValues();

        values.put(KEY_LAST_TICKET, quantity);
        /*values.put(KEY_ID, raffle.getId());
        values.put(KEY_NAME, raffle.getName());
        values.put(KEY_DESCRIPTION, raffle.getDescription());
        values.put(KEY_TICKET_PRICE, raffle.getTicketPrice());
        values.put(KEY_TYPE, raffle.getRaffleType());
        values.put(KEY_START_DATE, raffle.getStartDate().getTime());*/

        db.update(TABLE_NAME, values, KEY_ID +"= ?", new String[] {""+ id});
    }

    public static void setDrawn(SQLiteDatabase db, int id, int drawn) {
        ContentValues values = new ContentValues();

        values.put(KEY_DRAWN, drawn);
        /*values.put(KEY_ID, raffle.getId());
        values.put(KEY_NAME, raffle.getName());
        values.put(KEY_DESCRIPTION, raffle.getDescription());
        values.put(KEY_TICKET_PRICE, raffle.getTicketPrice());
        values.put(KEY_TYPE, raffle.getRaffleType());
        values.put(KEY_START_DATE, raffle.getStartDate().getTime());*/

        db.update(TABLE_NAME, values, KEY_ID +"= ?", new String[] {""+ id});
    }

    public static ArrayList<Raffle> selectByNameFilter(SQLiteDatabase db, String text)
    {
        ArrayList<Raffle> raffles = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME, null, KEY_NAME + " LIKE " + "'" + text + "%'", null, null, null, null);
        if(c != null)
        {
            ((Cursor) c).moveToFirst();

            while(!c.isAfterLast())
            {
                Raffle raffle = createFromCursor(c);
                raffles.add(raffle);
                c.moveToNext();
            }
        }
        return raffles;

    }
    static void delete(SQLiteDatabase database)
    {
        database.delete(TABLE_NAME,null, null);
    }
}


