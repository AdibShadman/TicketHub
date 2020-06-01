package au.edu.utas.asornob.raffledrawingapp.Lists;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import au.edu.utas.asornob.raffledrawingapp.Adapters.TicketAdapter;
import au.edu.utas.asornob.raffledrawingapp.CustomerDetails;
import au.edu.utas.asornob.raffledrawingapp.CustomerEdit;
import au.edu.utas.asornob.raffledrawingapp.Database;
import au.edu.utas.asornob.raffledrawingapp.R;
import au.edu.utas.asornob.raffledrawingapp.RaffleSelected;
import au.edu.utas.asornob.raffledrawingapp.Tables.TicketTable;
import au.edu.utas.asornob.raffledrawingapp.Ticket;
import au.edu.utas.asornob.raffledrawingapp.TicketDetails;

public class TicketList extends AppCompatActivity {
    public static final int REQUEST_DETAILS = 0;
    public final static String KEY_CUSTOMER_ID = "customerId";
    public final static String KEY_TICKET_ID = "ticketId";
    public SearchView sv;
    public TicketAdapter ticketAdapter;
    public ArrayList<Ticket> tickets;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_list);

        Database databaseConnection = new Database(this);
        final SQLiteDatabase database = databaseConnection.open();

        Bundle extras = getIntent().getExtras();
        final int id = extras.getInt(RaffleSelected.KEY_RAFFLE_ID, -1);
        //Raffle raffle = RaffleTable.selectRaffle(database, id);

         tickets = TicketTable.raffleTickets(database, id);

         ticketAdapter = new TicketAdapter(
                this,
                tickets
        );

        ListView listTickets = findViewById(R.id.listTickets);
        listTickets.setAdapter(ticketAdapter);

        listTickets.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l)
            {

                // View customer details
                Intent i = new Intent(TicketList.this, TicketDetails.class);
                i.putExtra(KEY_CUSTOMER_ID, tickets.get(position).getCustomer().getId());
                i.putExtra(KEY_TICKET_ID, tickets.get(position).getId());
                startActivityForResult(i, REQUEST_DETAILS);
                // |-> Edit details
            }
        });
       sv = (SearchView) findViewById(R.id.ticket_search_bar);
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
                    tickets.clear();
                    tickets = TicketTable.raffleTickets(database, id);
                    ticketAdapter.clear();
                    ticketAdapter.addAll(tickets);
                    ticketAdapter.notifyDataSetChanged();

                }
                else
                {
                    tickets.clear();
                    tickets = Ticket.tickets(database, newText, id);
                    ticketAdapter.clear();
                    ticketAdapter.addAll(tickets);
                    ticketAdapter.notifyDataSetChanged();
                }


                Log.i("Testing", "Searching: " + newText);
                for(Ticket ticket : tickets)
                {
                    Log.i("Testing", ticket.getCustomer().getName() + "\n");
                }

                return false;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Database databaseConnection = new Database(this);
        final SQLiteDatabase database = databaseConnection.open();

        if(requestCode == REQUEST_DETAILS)
        {
            switch(resultCode) {
                case CustomerEdit.CHANGED:
                    //int ticketId = data.getIntExtra(TicketList.KEY_TICKET_ID, -1);
                    // Reload list
                    Intent i = getIntent();
                    startActivity(i);
                    finish();
                    //reload details
//                    Intent details = new Intent(TicketList.this, TicketDetails.class);
//                    details.putExtra(KEY_TICKET_ID, ticketId);
//                    startActivityForResult(details, REQUEST_DETAILS);
                    break;
            }
        }
    }
}