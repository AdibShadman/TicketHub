package au.edu.utas.asornob.raffledrawingapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import au.edu.utas.asornob.raffledrawingapp.Lists.RaffleList;
import au.edu.utas.asornob.raffledrawingapp.Lists.TicketList;
import au.edu.utas.asornob.raffledrawingapp.Lists.WinnerList;
import au.edu.utas.asornob.raffledrawingapp.Tables.RaffleTable;

public class RaffleSelected extends AppCompatActivity
{
    public static final String KEY_RAFFLE_ID = "raffleId";
    public static final int REQUEST_SALE = 0;
    public static final int REQUEST_MARGIN = 1;

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
    Button btnSelectWinner;
    String startDateString2;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_raffle);

        Database databaseConnection = new Database(this);
        final SQLiteDatabase database = databaseConnection.open();

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
                    AlertDialog.Builder alert = new AlertDialog.Builder(RaffleSelected.this);
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

        //sorry
        final Raffle raffle = RaffleTable.selectRaffle(database, raffleId);

        String StringTicketPrice = Double.toString(ticketPriceDouble);
        ticketPrice.setText(StringTicketPrice);

        String startDateString = getIntent().getStringExtra("start_date");
        startDateString2 = new SimpleDateFormat("yyyy-MM-dd").format(new Date(startDateString));
        raffleStartDate.setText(startDateString2);

        btnListTickets = (Button) findViewById(R.id.sell_tickets);
        if(raffle.getLastTicket() < raffle.getTotalTickets() && raffle.getDrawn() == 0) {
            btnListTickets.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(RaffleSelected.this, TicketSale.class);
                    i.putExtra(KEY_RAFFLE_ID, raffleId);
                    startActivityForResult(i, REQUEST_SALE);
                }
            });
        }
        else {
            btnListTickets.setEnabled(false);
        }

        btnListTickets = (Button) findViewById(R.id.list_tickets);
        btnListTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RaffleSelected.this, TicketList.class);
                i.putExtra(KEY_RAFFLE_ID, raffleId);
                startActivity(i);
            }
        });

        btnSelectWinner = (Button) findViewById(R.id.select_winner);
        if(raffle.getDrawn() == 1) {
            btnSelectWinner.setText("VIEW WINNER(s) [" + raffle.getWinners() + "]");
        }
        else {
            btnSelectWinner.setText("SELECT WINNER(s) [" + raffle.getWinners() + "]");
        }
        btnSelectWinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(raffle.getDrawn() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RaffleSelected.this);
                    builder.setTitle("Draw Raffle");
                    builder.setMessage("Are you sure you want to draw the raffle?");

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int num) {
                            if (getIntent().getStringExtra("raffle_type").compareToIgnoreCase("normal") == 0) {
                                Log.d("State: ", "drawing raffle");
                                Winner.drawWinners(database, raffle);
                                Intent i = new Intent(RaffleSelected.this, WinnerList.class);
                                i.putExtra(KEY_RAFFLE_ID, raffleId);
                                startActivity(i);
                            } else if (getIntent().getStringExtra("raffle_type").compareToIgnoreCase("marginal") == 0) {
                                Intent i = new Intent(RaffleSelected.this, DrawMargin.class);
                                i.putExtra(KEY_RAFFLE_ID, raffleId);
                                startActivityForResult(i, REQUEST_MARGIN);
                            }
                        }
                    });

                    builder.setCancelable(true);

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
                else if (raffle.getDrawn() == 1)
                {
                    Intent i = new Intent(RaffleSelected.this, WinnerList.class);
                    i.putExtra(KEY_RAFFLE_ID, raffleId);
                    startActivity(i);
                }
                /*LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_draw_confirmation, null);

                int width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
                int height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
                final PopupWindow confirmationPopup = new PopupWindow(popupView, width, height, true);
                confirmationPopup.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                Button yesButton = (Button) findViewById(R.id.btnDrawPopupYes);
                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(SelectedRaffle.this, WinnerList.class);
                        i.putExtra(KEY_RAFFLE_ID, raffleId);
                        startActivity(i);
                    }
                });*/

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

                       Database databaseConnection = new Database(RaffleSelected.this);
                        SQLiteDatabase database = databaseConnection.open();

                        RaffleTable raffle = new RaffleTable();
                        raffle.deleteById(database, deletedRaffle);
                        startActivity(new Intent(RaffleSelected.this, RaffleList.class));
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
        Intent intent = new Intent(RaffleSelected.this, RaffleEdit.class);
        intent.putExtra("id",raffleId);
        intent.putExtra("name", raffleName.getText().toString());
        intent.putExtra("description", raffleDescription.getText().toString());
        intent.putExtra("ticket_price", ticketPrice.getText().toString());
        intent.putExtra("start_date", raffleStartDate.getText().toString());
        intent.putExtra("raffle_type", raffleType.getText().toString());

        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Database databaseConnection = new Database(this);
        final SQLiteDatabase database = databaseConnection.open();

        if(requestCode == REQUEST_MARGIN)
        {
            switch(resultCode) {
                case DrawMargin.DRAWN:
                    Intent i = new Intent(RaffleSelected.this, WinnerList.class);
                    i.putExtra(KEY_RAFFLE_ID, raffleId);
                    startActivity(i);
                    break;
            }
        }
    }
}