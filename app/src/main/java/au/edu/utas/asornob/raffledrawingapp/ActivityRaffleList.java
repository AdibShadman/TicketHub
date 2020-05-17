package au.edu.utas.asornob.raffledrawingapp;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ActivityRaffleList extends AppCompatActivity
{
    private ListView raffleListView;
    public static String PRICE_KEY = "PRICE"; //for ticket price

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raffle_list);
        raffleListView = (ListView) findViewById(R.id.raffle_list);

        Database databaseConnection = new Database(this);
        final SQLiteDatabase database = databaseConnection.open();

        final ArrayList<Raffle> raffles = RaffleTable.selectAll(database);
        final RaffleAdapter raffleListAdapter = new RaffleAdapter(ActivityRaffleList.this,
                R.layout.my_list_item, raffles);
        raffleListView.setAdapter(raffleListAdapter);


    }

}
