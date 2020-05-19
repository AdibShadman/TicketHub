package au.edu.utas.asornob.raffledrawingapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TicketList extends AppCompatActivity {
    public final static String KEY_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);

        Database databaseConnection = new Database(this);
        final SQLiteDatabase database = databaseConnection.open();

        Bundle extras = getIntent().getExtras();
        final int id = extras.getInt(SelectedRaffle.KEY_RAFFLE_ID, -1);
        //Raffle raffle = RaffleTable.selectRaffle(database, id);

        ArrayList<Ticket> tickets = TicketTable.raffleTickets(database, id);

        TicketAdapter ticketAdapter = new TicketAdapter(
                this,
                tickets
        );

        ListView listCustomer = findViewById(R.id.listTickets);
        listCustomer.setAdapter(ticketAdapter);
    }
}