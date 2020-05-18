package au.edu.utas.asornob.raffledrawingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class SelectedRaffle extends AppCompatActivity
{

    int raffleId;
    double ticketPriceDouble;
    String startDateString2;

    TextView raffleName;
    TextView raffleDescription;
    TextView raffleStartDate;
    TextView ticketPrice;
    TextView raffleType;


    Button editRaffle;
    Button deleteRaffle;
    Button listTickets;
    Button sellTickets;
    Button selectWinner;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_raffle);



        editRaffle = (Button) findViewById(R.id.edit_raffle);
        editRaffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editRaffle();
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

        final String startDateString = getIntent().getStringExtra("start_date");
        Log.d("tag", startDateString);
        startDateString2 = new SimpleDateFormat("yyyy-MM-dd").format(new Date(startDateString));
        raffleStartDate.setText(startDateString2);

        deleteRaffle = (Button) findViewById(R.id.delete_raffle);
        deleteRaffle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date deletedStartDate = new Date();
                try
                {
                    deletedStartDate = newDateFormat.parse(startDateString2);
                }
                catch(ParseException pe)
                {


                }
               Date now = new Date(System.currentTimeMillis());
                if((deletedStartDate).after(now))
                {
                    deleteRaffle();
                }
                else
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(SelectedRaffle.this);
                    alert.setTitle("Can't Delete Raffle");
                    alert.setMessage("This raffle has already been started");
                    alert.setPositiveButton("OK",null);
                    alert.show();

                }

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

    private void editRaffle()
    {
        Intent intent = new Intent(SelectedRaffle.this, EditRaffle.class);
        intent.putExtra("id",raffleId);
        intent.putExtra("name", raffleName.getText().toString());
        intent.putExtra("description", raffleDescription.getText().toString());
        intent.putExtra("ticket_price", ticketPrice.getText().toString());
        intent.putExtra("start_date", raffleStartDate.getText().toString());
        intent.putExtra("raffle_type", raffleType.getText().toString());

        startActivity(intent);
    }

}
