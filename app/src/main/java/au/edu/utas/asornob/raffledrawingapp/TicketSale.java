package au.edu.utas.asornob.raffledrawingapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketSale extends AppCompatActivity {
    public static final int REQUEST_CUSTOMER = 0;

    private Button btnSellTicket;
    private Button btnSetCustomer;
    private EditText fieldQuantity;
    private Integer quantity = 1;

    private Double price = -1.0;
    private Customer customer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_sale);

        Database databaseConnection = new Database(this);
        final SQLiteDatabase database = databaseConnection.open();

        /*Raffle insRaffle = new Raffle();
        insRaffle.setName("wo");
        insRaffle.setDescription("wolo");
        insRaffle.setTotalTickets(4);
        insRaffle.setTicketPrice(1.5);
        RaffleTable.insert(database, insRaffle);*/

        List<Raffle> raffles = RaffleTable.selectAll(database);

        Bundle extras = getIntent().getExtras();
        final int id = extras.getInt(SelectedRaffle.KEY_RAFFLE_ID, -1);
        Raffle raffle = RaffleTable.selectRaffle(database, id);

        //testing ticket insertion
        ArrayList<Ticket> tickets = TicketTable.selectAll(database);
            for(int i = 0; i < tickets.size(); i++) {
                Ticket ticket = tickets.get(i);
                Log.d("Ticket: ", "raffle- " + ticket.getRaffleId() + " ticket- " + ticket.getCustomer().getId());
        }

        fieldQuantity = (EditText) findViewById(R.id.txtQuant);
        final TextView txtCost = findViewById(R.id.txtCost);

        if(raffle != null) {
            TextView txtTicketPrice = findViewById(R.id.txtTicketPrice);
            price = raffle.getTicketPrice();
            txtTicketPrice.setText("$" + price);
            txtCost.setText("$" + ((Double) (quantity * price)).toString());
        }

        fieldQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String quantityString = fieldQuantity.getText().toString();
                if(quantityString.equals("")) {
                    txtCost.setText("$0.00");
                }
                else
                {
                    quantity = Integer.parseInt(quantityString);
                    txtCost.setText(("$" + (Double) (quantity * price)).toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSetCustomer = (Button) findViewById(R.id.btnChangeCust);
        btnSetCustomer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //placeholder
                customer = new Customer();
                customer.setName("Balley");
                customer.setEmail("Balley@all.com");
                customer.setPhone("0444 444 444");

                CustomerTable.insert(database, customer);
                Intent i = new Intent(TicketSale.this, CustomerSelect.class);
                startActivityForResult(i, REQUEST_CUSTOMER);
            }


        });



        btnSellTicket = (Button) findViewById(R.id.btnNext);
        btnSellTicket.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantityString = fieldQuantity.getText().toString();
                if(price == -1 || customer ==null || id == -1 || quantityString.equals("")) {
                    Log.d("Error: ", "Submit failed due to missing value");
                }
                else {
                    Ticket ticket = new Ticket();

                    ticket.setPrice(price);
                    ticket.setPurchaseTime(new Date());
                    ticket.setCustomer(customer);
                    ticket.setRaffleId(id);

                    quantity = Integer.parseInt(fieldQuantity.getText().toString());
                    for (int i = 0; i < quantity; i++) {
                        TicketTable.insert(database, ticket);
                    }

                    Toast.makeText(TicketSale.this, (quantity + "Ticket(s) sold"), Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(TicketSale.this, ActivityRaffleList.class);
                    //startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Database databaseConnection = new Database(this);
        final SQLiteDatabase database = databaseConnection.open();

        if(requestCode == REQUEST_CUSTOMER)
        {
            switch(resultCode) {
                case CustomerSelect.VALID_CUSTOMER:
                    int customerId = data.getIntExtra(CustomerSelect.KEY_ID, -1);
                    customer = CustomerTable.selectCustomer(database, customerId);

                    TextView txtCustomer = findViewById(R.id.txtCustomer);
                    txtCustomer.setText(customer.getName());
                    break;
            }
        }
    }
}