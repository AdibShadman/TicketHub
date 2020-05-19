package au.edu.utas.asornob.raffledrawingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class SelectedRaffle extends AppCompatActivity
{
    public static final String KEY_RAFFLE_ID = "raffleId";
    public static final int REQUEST_SALE = 0;

    int raffleId;
    double ticketPriceDouble;

    TextView raffleName;
    TextView raffleDescription;
    TextView raffleStartDate;
    TextView ticketPrice;
    TextView raffleType;


    Button editRaffle;
    Button deleteRaffle;
    private Button btnListTickets;
    private Button btnSellTickets;
    Button selectWinner;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_raffle);

        deleteRaffle = (Button) findViewById(R.id.delete_raffle);
        deleteRaffle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                deleteRaffle();
            }
        });
        raffleName = (TextView) findViewById(R.id.view_raffle_name);
        raffleDescription = (TextView) findViewById(R.id.raffle_description_view);
        ticketPrice = (TextView) findViewById(R.id.view_ticket_price);
        raffleStartDate = (TextView) findViewById(R.id.view_start_date);
        raffleType = (TextView) findViewById(R.id.view_ticket_type);


        raffleId = getIntent().getIntExtra("id", 0);
        raffleName.setText(getIntent().getStringExtra("name"));
        raffleDescription.setText(getIntent().getStringExtra("description"));
        raffleType.setText(getIntent().getStringExtra("raffle_type"));
        ticketPriceDouble = getIntent().getDoubleExtra("ticket_price",0.0);

        String StringTicketPrice = Double.toString(ticketPriceDouble);
        ticketPrice.setText(StringTicketPrice);

        String startDateString = getIntent().getStringExtra("start_date");
      // String startDateString2 = new SimpleDateFormat("yyyy-MM-dd").format(startDateString);
        raffleStartDate.setText(startDateString);

        btnListTickets = (Button) findViewById(R.id.sell_tickets);
        btnListTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectedRaffle.this, TicketSale.class);
                i.putExtra(KEY_RAFFLE_ID, raffleId);
                startActivityForResult(i, REQUEST_SALE);
            }
        });

        btnListTickets = (Button) findViewById(R.id.list_tickets);
        btnListTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectedRaffle.this, TicketList.class);
                i.putExtra(KEY_RAFFLE_ID, raffleId);
                startActivity(i);
            }
        });
    }



    private void deleteRaffle()
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
               switch(which)
               {

                   case DialogInterface.BUTTON_POSITIVE:
                       Raffle deletedRaffle = new Raffle();
                       deletedRaffle.setId(raffleId);

                       Database databaseConnection = new Database(SelectedRaffle.this);
                        SQLiteDatabase database = databaseConnection.open();

                        RaffleTable raffle = new RaffleTable();
                        raffle.deleteById(database, deletedRaffle);
                        startActivity(new Intent(SelectedRaffle.this, ActivityRaffleList.class));
                        break;

                        case DialogInterface.BUTTON_NEGATIVE:
                        break;
               }
            }

        };
        AlertDialog builder = new AlertDialog.Builder(this).setTitle("Delete Raffle")
                .setMessage("Are you sure you want to delete this raffle?")
                .setPositiveButton("Confirm", dialogClickListener)
                .setNegativeButton("Cancel", null).show();
    }
}
