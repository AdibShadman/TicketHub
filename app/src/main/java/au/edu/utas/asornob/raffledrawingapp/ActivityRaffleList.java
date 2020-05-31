package au.edu.utas.asornob.raffledrawingapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.SearchEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ActivityRaffleList extends AppCompatActivity
{
    private ListView raffleListView;
    private Button addButtonFromRaffleList;
    private SearchView sv;

    ArrayList<Raffle> raffles = new ArrayList();
    RaffleAdapter raffleListAdapter;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raffle_list);
        raffleListView = (ListView) findViewById(R.id.raffle_list);
        addButtonFromRaffleList = (Button) findViewById(R.id.add_raffle_from_listview);
        addButtonFromRaffleList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                openCreateRaffleActivity();

            }

        });

        Database databaseConnection = new Database(this);
        final SQLiteDatabase database = databaseConnection.open();

         raffles = RaffleTable.selectAll(database);
         raffleListAdapter = new RaffleAdapter(this,
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
        sv = (SearchView) findViewById(R.id.raffle_search_bar);
        sv.setQueryHint("Search by Name...");

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                if(newText.isEmpty())
                {
                    raffles.clear();
                    raffles = RaffleTable.selectAll(database);
                    raffleListAdapter.clear();
                    raffleListAdapter.addAll(raffles);
                    raffleListAdapter.notifyDataSetChanged();

                }
                else
                {
                    raffles.clear();
                    raffles = RaffleTable.selectByNameFilter(database, newText);
                    raffleListAdapter.clear();
                    raffleListAdapter.addAll(raffles);
                    raffleListAdapter.notifyDataSetChanged();
                }


                Log.i("Testing", "Searching: " + newText);
                for(Raffle raffle : raffles)
                {
                    Log.i("Testing", raffle.getName() + "\n");
                }

                return false;
            }
        });

    }

    public void openCreateRaffleActivity()
    {
        Intent intent = new Intent(ActivityRaffleList.this, CreateRaffle.class);
        startActivity(intent);
    }

}
