package au.edu.utas.asornob.raffledrawingapp;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class WinnerList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_list);

        Database databaseConnection = new Database(this);
        final SQLiteDatabase database = databaseConnection.open();

        Bundle extras = getIntent().getExtras();
        final int id = extras.getInt(SelectedRaffle.KEY_RAFFLE_ID, -1);
        Raffle raffle = RaffleTable.selectRaffle(database, id);

        ArrayList<Winner> winners = WinnerTable.selectRaffleWinners(database, id);

        // if not drawn, draw
        //if(winners.isEmpty() == true) {
            //winners = Winner.drawWinners(database, raffle);
        //}

        WinnerAdapter winnerAdapter = new WinnerAdapter(
                this,
                winners
        );

        ListView winnerList = findViewById(R.id.listWinners);
        winnerList.setAdapter(winnerAdapter);

        for(int i=0; i < winners.size(); i++)
        {
            Log.d("Winner: ", winners.get(i).getRaffleId() + ", " + winners.get(i).getTicketId());
        }

        //display



        //final ArrayList<Ticket> tickets = TicketTable.raffleTickets(database, id);

    }
}