package au.edu.utas.asornob.raffledrawingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateRaffle extends AppCompatActivity
{
    private Button addRaffleButtonInRaffleForm;

    private EditText raffleName;
    private EditText raffleDescription;
    private EditText raffleTotalTickets;
    private EditText raffleTicketPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_raffle);

        Database databaseConnection = new Database(this);
        final SQLiteDatabase database = databaseConnection.open();

        addRaffleButtonInRaffleForm = (Button) findViewById(R.id.add_raffle_button);
        addRaffleButtonInRaffleForm.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                raffleName = (EditText) findViewById(R.id.raffle_entry_name);
                raffleDescription = (EditText) findViewById(R.id.raffle_entry_description);
                raffleTotalTickets  = (EditText) findViewById(R.id.raffle_entry_total_tickets);
                raffleTicketPrice = (EditText) findViewById(R.id.raffle_ticket_price);

                String stringRaffleName = raffleName.getText().toString();
                String stringRaffleDescription = raffleDescription.getText().toString();
                int integerTotalTickets = Integer.parseInt(raffleTotalTickets.getText().toString());
                Double doubleTicketPrice = Double.parseDouble( raffleTicketPrice.getText().toString());

                Raffle raffle = new Raffle();
                raffle.setName(stringRaffleName);
                raffle.setDescription(stringRaffleDescription);
                raffle.setTotalTickets(integerTotalTickets);
                raffle.setTicketPrice(doubleTicketPrice);

                RaffleTable.insert(database, raffle);

                Toast.makeText(CreateRaffle.this, "New Raffle inserted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreateRaffle.this, ActivityRaffleList.class);
                startActivity(intent);

            }
        });

    }
}