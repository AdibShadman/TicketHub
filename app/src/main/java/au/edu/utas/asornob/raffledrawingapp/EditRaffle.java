package au.edu.utas.asornob.raffledrawingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditRaffle extends AppCompatActivity
{
    int raffleId;
    double ticketPriceDouble;

    private TextView raffleName;
    private TextView raffleDescription;
    private TextView raffleStartDate;
    private TextView ticketPrice;
    private TextView raffleType;

    private Button modifyRaffle;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_raffle);


        raffleName = (TextView) findViewById(R.id.view_raffle_name);
        raffleDescription = (TextView) findViewById(R.id.raffle_description_view);
        raffleStartDate = (TextView) findViewById(R.id.view_start_date);
        ticketPrice = (TextView) findViewById(R.id.view_ticket_price);
        raffleType = (TextView) findViewById(R.id.view_ticket_type);
        modifyRaffle = (Button) findViewById(R.id.edit_raffle_confirm_button);


        raffleId = getIntent().getIntExtra("id", 0);

        raffleName.setText(getIntent().getStringExtra("name"));
        raffleDescription.setText(getIntent().getStringExtra("description"));
        raffleStartDate.setText(getIntent().getStringExtra("start_date"));
        //ticketPriceDouble = getIntent().getDoubleExtra("ticket_price",0.0);

        // String StringTicketPrice = Double.toString(ticketPriceDouble);
        ticketPrice.setText(getIntent().getStringExtra("ticket_price"));

        raffleType.setText(getIntent().getStringExtra("raffle_type"));

        raffleStartDate.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v)
            {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(EditRaffle.this, android.R.style.Theme_DeviceDefault_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date;
                month = month + 1;
                if (month < 10 && dayOfMonth > 10) {

                    date = year + "-0" + month + "-" + dayOfMonth;

                } else if (month > 10 && dayOfMonth < 10) {
                    date = year + "-" + month + "-0" + dayOfMonth;
                } else if (month < 10 && dayOfMonth < 10) {
                    date = year + "-0" + month + "-0" + dayOfMonth;
                } else {
                    date = year + "-" + month + "-" + dayOfMonth;
                }

                Log.d("Testing", "onDateSET: yyyy-MM-dd: " + month + "/" + dayOfMonth + "/" + year);

                raffleStartDate.setText(date);
            }
        };
        modifyRaffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRaffle();

            }
        });


    }

    private void updateRaffle()
    {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {
                    case DialogInterface.BUTTON_POSITIVE:
                        sendUpdate();
                        startActivity(new Intent(EditRaffle.this, ActivityRaffleList.class));
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };
        AlertDialog builder = new AlertDialog.Builder(EditRaffle.this).setTitle("Edit Raffle")
                .setMessage("Are you sure you want to modify this raffle?")
                .setPositiveButton("Confirm", dialogClickListener)
                .setNegativeButton("Cancel", null).show();

    }

    private void sendUpdate()
    {
      Raffle updatedRaffle = new Raffle();
      updatedRaffle.setId(raffleId);
      updatedRaffle.setName(raffleName.getText().toString());
      updatedRaffle.setDescription(raffleDescription.getText().toString());
      updatedRaffle.setRaffleType(raffleType.getText().toString());

      double doubleTicketPrice = Double.parseDouble((ticketPrice.getText().toString()));
      updatedRaffle.setTicketPrice(doubleTicketPrice);

      SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date updatedStartDate = new Date();
          try
            {
                updatedStartDate = newDateFormat.parse(raffleStartDate.getText().toString());
            }
            catch(ParseException pe)
            {
                //oops
            }
            updatedRaffle.setStartDate(updatedStartDate);

            Database databaseHandler = new Database(EditRaffle.this);
            SQLiteDatabase database = databaseHandler.open();

            RaffleTable raffles = new RaffleTable();
            raffles.update(database, updatedRaffle);
    }



}
