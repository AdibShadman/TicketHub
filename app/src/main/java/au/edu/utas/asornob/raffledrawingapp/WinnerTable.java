package au.edu.utas.asornob.raffledrawingapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class WinnerTable {
    public static final String TABLE_NAME = "winner";
    public static final String KEY_ID = "id";
    public static final String KEY_RAFFLE_ID = "FK_raffle_id";
    public static final String KEY_TICKET_ID = "FK_ticket_id";
    public static final String KEY_PLACE = "place";

    public static final String CREATE_STATEMENT = //"DROP TABLE "
            //+ TABLE_NAME + "; "
            "CREATE TABLE "
                    + TABLE_NAME
                    + " (" + KEY_ID + " integer primary key autoincrement, "
                    + KEY_RAFFLE_ID + " int not null, "
                    + KEY_TICKET_ID + " int not null, "
                    + KEY_PLACE + " int not null"
                    +" );";

    public static int insert(SQLiteDatabase database, Winner winner) {
        ContentValues values = new ContentValues();
        values.put(KEY_RAFFLE_ID, winner.getRaffleId());
        values.put(KEY_TICKET_ID, winner.getTicketId());
        values.put(KEY_PLACE, winner.getPlace());
        return (int) database.insert(TABLE_NAME, null, values);
    }

    public static Winner createFromCursor(Cursor c, SQLiteDatabase db)
    {
        if (c == null || c.isAfterLast() || c.isBeforeFirst())
        {
            return null;
        }
        else
        {
            Winner winner = new Winner();
            winner.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            winner.setRaffleId(c.getInt(c.getColumnIndex(KEY_RAFFLE_ID)));
            winner.setTicketId(c.getInt(c.getColumnIndex(KEY_TICKET_ID)));
            winner.setPlace(c.getInt(c.getColumnIndex(KEY_PLACE)));
            Log.d("Winner: ", "" + winner.getId());
            Log.d("Ticket: ", "" + winner.getTicketId());
            winner.setCustomer(TicketTable.ticketOwner(db, winner.getTicketId()));
            return winner;
        }
    }

    public static Winner selectWinner(SQLiteDatabase db, int id) {
        Winner result;
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + "=" + id, null);
        if (c != null) {
            c.moveToFirst();
            result = createFromCursor(c, db);
            return result;
        }

        return null;
    }

    public static ArrayList<Winner> selectRaffleWinners(SQLiteDatabase db, int id) {
        ArrayList<Winner> results = new ArrayList<Winner>();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_RAFFLE_ID + "=" + id, null);

        if (c != null) {
            c.moveToFirst();
            while(!c.isAfterLast()) {
                results.add(createFromCursor(c, db));
                c.moveToNext();
            }
        }

        return results;
    }

    public static ArrayList<Winner> selectAll(SQLiteDatabase db) {
        ArrayList<Winner> results= new ArrayList<Winner>();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            while(!c.isAfterLast()) {
                results.add(createFromCursor(c, db));
                c.moveToNext();
            }
        }

        return results;
    }

    public static void deleteRaffleWinners(SQLiteDatabase database, int id)
    {
        database.delete(TABLE_NAME, KEY_RAFFLE_ID +"=" + id, null);
    }
}
