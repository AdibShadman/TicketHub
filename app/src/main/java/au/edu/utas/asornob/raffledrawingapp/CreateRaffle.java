package au.edu.utas.asornob.raffledrawingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateRaffle extends AppCompatActivity
{
    private Button addRaffleButtonInRaffleForm;

    private EditText raffleName;
    private EditText raffleDescription;
    private EditText raffleTotalTickets;
    private EditText raffleTicketPrice;
    private TextView startDate;
    private TextView endDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;
    private EditText raffleType;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_raffle);
        Database databaseConnection = new Database(this);
        final SQLiteDatabase database = databaseConnection.open();


        startDate = (TextView) findViewById(R.id.start_date_text_view);
        startDate.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year =  cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CreateRaffle.this,android.R.style.Theme_DeviceDefault_Dialog_MinWidth,mDateSetListener,year, month, day );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener()
        {

            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                String date;
                month = month + 1;
                if(month < 10 && dayOfMonth > 10)
                {

                    date = year + "-0" + month + "-" + dayOfMonth;

                }
                else if(month > 10 && dayOfMonth < 10 )
                {
                     date = year + "-" + month + "-0" + dayOfMonth;
                }
                else if(month < 10 && dayOfMonth < 10)
                {
                    date = year + "-0" + month + "-0" + dayOfMonth;
                }
                else
                {
                     date = year + "-" + month + "-" + dayOfMonth;
                }

                Log.d("Testing","onDateSET: yyyy-MM-dd: " + month + "/" + dayOfMonth + "/" + year);

                startDate.setText(date);

            }
        };

        endDate = (TextView) findViewById(R.id.end_date_text_view);

        endDate.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v) {
                Calendar cal2 = Calendar.getInstance();
                int year2 =  cal2.get(Calendar.YEAR);
                int month2 = cal2.get(Calendar.MONTH);
                int day2 = cal2.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CreateRaffle.this,android.R.style.Theme_DeviceDefault_Dialog_MinWidth,mDateSetListener2,year2, month2, day2 );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener2 = new DatePickerDialog.OnDateSetListener()
        {

            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                String date2;
                month = month + 1;
                if(month < 10 && dayOfMonth > 10)
                {

                    date2 = year + "-0" + month + "-" + dayOfMonth;

                }
                else if(month > 10 && dayOfMonth < 10 )
                {
                    date2 = year + "-" + month + "-0" + dayOfMonth;
                }
                else if(month < 10 && dayOfMonth < 10)
                {
                    date2 = year + "-0" + month + "-0" + dayOfMonth;
                }
                else
                {
                    date2 = year + "-" + month + "-" + dayOfMonth;
                }

                Log.d("Testing","onDateSET: yyyy-MM-dd: " + month + "/" + dayOfMonth + "/" + year);

                endDate.setText(date2);

            }
        };

                addRaffleButtonInRaffleForm = (Button) findViewById(R.id.add_raffle_button);
                addRaffleButtonInRaffleForm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                raffleName = (EditText) findViewById(R.id.raffle_entry_name);
                raffleDescription = (EditText) findViewById(R.id.raffle_entry_description);
                raffleTotalTickets = (EditText) findViewById(R.id.raffle_entry_total_tickets);
                raffleTicketPrice = (EditText) findViewById(R.id.raffle_ticket_price);
                raffleType = (EditText) findViewById(R.id.raffle_type);

                if (!canSubmit())
                {
                    new AlertDialog.Builder(CreateRaffle.this).setTitle("Raffle Requires More Information")
                            .setMessage("Raffle form Must Include: Raffle Name, Description, TicketPrice,Total Tickets, Raffle Type, Start Date and End date")
                            .setPositiveButton("Okay", null).show();
                }
                else
                {



                        String stringRaffleName = raffleName.getText().toString();
                        String stringRaffleDescription = raffleDescription.getText().toString();
                        int integerTotalTickets = Integer.parseInt(raffleTotalTickets.getText().toString());
                        Double doubleTicketPrice = Double.parseDouble(raffleTicketPrice.getText().toString());
                        String stringRaffleType = raffleType.getText().toString();



                        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date newStartDate = new Date();
                        Date newEndDate = new Date();

                        try
                        {
                            newStartDate = newDateFormat.parse(startDate.getText().toString());
                            newEndDate = newDateFormat.parse(endDate.getText().toString());

                        }
                        catch(ParseException pe)
                        {
                            //oops
                        }

                    if(stringRaffleType.equalsIgnoreCase("Marginal") || stringRaffleType.equalsIgnoreCase("Normal")) {
                        Raffle raffle = new Raffle();
                        raffle.setName(stringRaffleName);
                        raffle.setDescription(stringRaffleDescription);
                        raffle.setTotalTickets(integerTotalTickets);
                        raffle.setTicketPrice(doubleTicketPrice);
                        raffle.setStartDate(newStartDate);
                        raffle.setEndDate(newEndDate);
                        raffle.setRaffleType(stringRaffleType);

                        RaffleTable.insert(database, raffle);

                        Toast.makeText(CreateRaffle.this, "New Raffle inserted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CreateRaffle.this, ActivityRaffleList.class);
                        startActivity(intent);
                    }

                    else
                    {
                        new AlertDialog.Builder(CreateRaffle.this).setTitle("Mismatch Raffle Type")
                                .setMessage("Raffle type should be either Normal or Marginal")
                                .setPositiveButton("Okay", null).show();
                    }

                }



            }
        });
    }

 private boolean canSubmit()
 {
     boolean canSubmit =  true;
     if(TextUtils.isEmpty(raffleName.getText()))
     {
         canSubmit = false;

     }
     if(TextUtils.isEmpty(raffleDescription.getText()))
     {
         canSubmit = false;

     }
     if(TextUtils.isEmpty(raffleTotalTickets.getText()))
     {
         canSubmit = false;

     }
     if(TextUtils.isEmpty(raffleTicketPrice.getText()))
     {
         canSubmit = false;

     }
     if(TextUtils.isEmpty(raffleType.getText()))
     {
         canSubmit = false;

     }
     if(TextUtils.isEmpty(startDate.getText()))
     {
         canSubmit = false;

     }
     if(TextUtils.isEmpty(endDate.getText()))
     {
         canSubmit = false;

     }

     return canSubmit;

 }





}