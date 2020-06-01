package au.edu.utas.asornob.raffledrawingapp.Lists;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import au.edu.utas.asornob.raffledrawingapp.Adapters.WinnerAdapter;
import au.edu.utas.asornob.raffledrawingapp.CustomerEdit;
import au.edu.utas.asornob.raffledrawingapp.Database;
import au.edu.utas.asornob.raffledrawingapp.R;
import au.edu.utas.asornob.raffledrawingapp.Raffle;
import au.edu.utas.asornob.raffledrawingapp.RaffleSelected;
import au.edu.utas.asornob.raffledrawingapp.Tables.RaffleTable;
import au.edu.utas.asornob.raffledrawingapp.Tables.WinnerTable;
import au.edu.utas.asornob.raffledrawingapp.TicketDetails;
import au.edu.utas.asornob.raffledrawingapp.Winner;

public class WinnerList extends AppCompatActivity {
    public static final int REQUEST_WINNER_DETAILS = 1;
    public final static String KEY_TICKET_ID = "ticketId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winner_list);

        Database databaseConnection = new Database(this);
        final SQLiteDatabase database = databaseConnection.open();

        Bundle extras = getIntent().getExtras();
        final int id = extras.getInt(RaffleSelected.KEY_RAFFLE_ID, -1);
        Raffle raffle = RaffleTable.selectRaffle(database, id);

        final ArrayList<Winner> winners = WinnerTable.selectRaffleWinners(database, id);

        WinnerAdapter winnerAdapter = new WinnerAdapter(
                this,
                winners
        );


        ListView winnerList = findViewById(R.id.listWinners);
        winnerList.setAdapter(winnerAdapter);

        winnerList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l)
            {

                // View customer details
                Intent i = new Intent(WinnerList.this, TicketDetails.class);
                i.putExtra(KEY_TICKET_ID, winners.get(position).getId());
                startActivityForResult(i, REQUEST_WINNER_DETAILS);
                // |-> Edit details
            }
        });

        for(int i=0; i < winners.size(); i++)
        {
            Log.d("Winner: ", winners.get(i).getRaffleId() + ", " + winners.get(i).getTicketId());
        }
        Log.d("Raffle: ", Integer.toString(raffle.getWinners()));
        //display



        //final ArrayList<Ticket> tickets = TicketTable.raffleTickets(database, id);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Database databaseConnection = new Database(this);
        final SQLiteDatabase database = databaseConnection.open();

        if(requestCode == REQUEST_WINNER_DETAILS)
        {
            switch(resultCode) {
                case CustomerEdit.CHANGED:
                    //int customerId = data.getIntExtra(TicketList.KEY_CUSTOMER_ID, -1);
                    // Reload list
                    Intent i = getIntent();
                    finish();
                    startActivity(i);
                    break;
            }
        }
    }
}