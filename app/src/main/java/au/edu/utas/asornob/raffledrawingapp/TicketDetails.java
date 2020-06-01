package au.edu.utas.asornob.raffledrawingapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import au.edu.utas.asornob.raffledrawingapp.Lists.TicketList;
import au.edu.utas.asornob.raffledrawingapp.Tables.CustomerTable;
import au.edu.utas.asornob.raffledrawingapp.Tables.RaffleTable;
import au.edu.utas.asornob.raffledrawingapp.Tables.TicketTable;

public class TicketDetails extends AppCompatActivity {
    public static final int REQUEST_EDIT = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_details);

        Database databaseConnection = new Database(this);
        final SQLiteDatabase database = databaseConnection.open();

        Bundle extras = getIntent().getExtras();
        final int ticketId = extras.getInt(TicketList.KEY_TICKET_ID, -1);
        final Ticket ticket = TicketTable.selectTicket(database, ticketId);
        final Customer customer = ticket.getCustomer();


        // Display all ticket data
        TextView txtTitle = (TextView) findViewById(R.id.txtTickTitle);
        txtTitle.setText("Ticket " + ticket.getTicketNo());
        TextView txtName = (TextView) findViewById(R.id.txtTickDetailName);
        txtName.setText(customer.getName());
        TextView txtEmail = (TextView) findViewById(R.id.txtDetailTickPrice);
        txtEmail.setText("" + ticket.getPrice());
        TextView txtPhone = (TextView) findViewById(R.id.txtDetailTickTime);
        txtPhone.setText("" + ticket.getPurchaseTime());

        Button edit = (Button) findViewById(R.id.btnTickCustEdit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TicketDetails.this, CustomerEdit.class);
                i.putExtra(TicketList.KEY_CUSTOMER_ID, customer.getId());
                startActivityForResult(i, REQUEST_EDIT);
            }
        });

        Button shareTicket = (Button) findViewById(R.id.btnShareTicket);

        shareTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, customer.getName() + ", Ticket " + ticket.getTicketNo() +
                        ", $" + ticket.getPrice() + ", Purchased " + ticket.getPurchaseTime());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share via..."));
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Database databaseConnection = new Database(this);
        final SQLiteDatabase database = databaseConnection.open();

        if(requestCode == REQUEST_EDIT)
        {
            switch(resultCode) {
                case CustomerEdit.CHANGED:
                    // Reload list
                    int ticketId = data.getIntExtra(TicketList.KEY_TICKET_ID, -1);
                    Intent i = getIntent();
                    i.putExtra(TicketList.KEY_TICKET_ID, ticketId);
                    setResult(CustomerEdit.CHANGED, i);
                    finish();
                    break;
            }
        }
    }
}
