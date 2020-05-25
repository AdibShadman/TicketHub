package au.edu.utas.asornob.raffledrawingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    ImageView selectedImage;
    Uri imageUri;


    Button editRaffle;
    Button deleteRaffle;
    Button shareRaffle;
    private Button btnListTickets;
    private Button btnSellTickets;
    Button selectWinner;
    String startDateString2;


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
        raffleName = (TextView) findViewById(R.id.view_raffle_name);
        raffleDescription = (TextView) findViewById(R.id.raffle_description_view);
        ticketPrice = (TextView) findViewById(R.id.view_ticket_price);
        raffleStartDate = (TextView) findViewById(R.id.view_start_date);
        raffleType = (TextView) findViewById(R.id.view_ticket_type);
        selectedImage = (ImageView) findViewById(R.id.image_view);
        shareRaffle = (Button) findViewById(R.id.share_raffle);

        shareRaffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Start Date: " +raffleStartDate.getText().toString() + "\nRaffle Name: " +
                        raffleName.getText().toString() + " \nRaffle Description: " + raffleDescription.getText().toString() + " \nRaffle Type: " + raffleType.getText().toString());
                sendIntent.setType("text/plain");

                startActivity(Intent.createChooser(sendIntent, "Share via..."));
            }
        });


        raffleId = getIntent().getIntExtra("id", 0);
        raffleName.setText(getIntent().getStringExtra("name"));
        raffleDescription.setText(getIntent().getStringExtra("description"));
        raffleType.setText(getIntent().getStringExtra("raffle_type"));
        ticketPriceDouble = getIntent().getDoubleExtra("ticket_price",0.0);

        String StringTicketPrice = Double.toString(ticketPriceDouble);
        ticketPrice.setText(StringTicketPrice);

        String startDateString = getIntent().getStringExtra("start_date");
        startDateString2 = new SimpleDateFormat("yyyy-MM-dd").format(new Date(startDateString));
        raffleStartDate.setText(startDateString2);

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
        Bundle extras = getIntent().getExtras();
        if(extras.containsKey("raffle_uri"))
        {

          imageUri = Uri.parse(getIntent().getStringExtra("raffle_uri"));
          selectedImage.setImageURI(imageUri);
        }
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
