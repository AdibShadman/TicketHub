package au.edu.utas.asornob.raffledrawingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ActivityRaffleList extends AppCompatActivity
{
    private ListView raffleListView;
    private Button addButtonFromRaffleList;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raffle_list);
        raffleListView = (ListView) findViewById(R.id.raffle_list);
        addButtonFromRaffleList = (Button) findViewById(R.id.add_raffle_from_listview);
        addButtonFromRaffleList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateRaffleActivity();

            }

        });

        Database databaseConnection = new Database(this);
        final SQLiteDatabase database = databaseConnection.open();

        final ArrayList<Raffle> raffles = RaffleTable.selectAll(database);
        final RaffleAdapter raffleListAdapter = new RaffleAdapter(ActivityRaffleList.this,
                R.layout.my_list_item, raffles);
        raffleListView.setAdapter(raffleListAdapter);

        raffleListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l)
            {
              Intent i = new Intent(ActivityRaffleList.this, SelectedRaffle.class);

              i.putExtra("id", raffles.get(position).getId());
              i.putExtra("name", raffles.get(position).getName());
              i.putExtra("description", raffles.get(position).getDescription());
              i.putExtra("ticket_price", raffles.get(position).getTicketPrice());
              i.putExtra("start_date", raffles.get(position).getStartDate().toString());
              i.putExtra("raffle_type", raffles.get(position).getRaffleType());

              if(raffles.get(position).getPhoto() != null)
              {
                  i.putExtra("raffle_uri", raffles.get(position).getPhoto().toString());
              }

              startActivity(i);

            }
        });





    }

    public void openCreateRaffleActivity()
    {
        Intent intent = new Intent(ActivityRaffleList.this, CreateRaffle.class);
        startActivity(intent);
    }

}
