package au.edu.utas.asornob.raffledrawingapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class CustomerEdit extends AppCompatActivity {
    public static final int UNCHANGED = 0;
    public static final int CHANGED = 1;
    Button btnEditCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_edit);

        Database databaseConnection = new Database(this);
        final SQLiteDatabase database = databaseConnection.open();

        Bundle extras = getIntent().getExtras();
        final int id = extras.getInt(TicketList.KEY_ID, -1);
        final Customer customer = CustomerTable.selectCustomer(database, id);

        String name = customer.getName();
        String email = customer.getEmail();
        String phone = customer.getPhone();

        final EditText txtName = (EditText) findViewById(R.id.txtEditName);
        txtName.setText(customer.getName());
        final EditText txtEmail = (EditText) findViewById(R.id.txtEditEmail);
        txtEmail.setText(customer.getEmail());
        final EditText txtPhone = (EditText) findViewById(R.id.txtEditPhone);
        txtPhone.setText(customer.getPhone());

        btnEditCustomer = (Button) findViewById(R.id.btnCustEditConfirm);
        btnEditCustomer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameString = txtName.getText().toString();
                String emailString = txtEmail.getText().toString();
                String phoneString = txtPhone.getText().toString();
                boolean valid = false;
                if(!nameString.equals("") && !emailString.equals("") && !phoneString.equals("")) {
                }
                if(nameString.equals(customer.getName()))
                {
                    nameString = null;
                }
                else {valid = true;}
                if (emailString.equals(customer.getEmail()))
                {
                    emailString = null;
                }
                else {valid = true;}
                if (phoneString.equals(customer.getPhone()))
                {
                    phoneString = null;
                }
                else {valid = true;}
                if(!valid) {
                    Log.d("Error: ", "Submit failed due to missing value");
                }
                else
                {
                    CustomerTable.updateCustomer(database, id, nameString, emailString, phoneString);

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(TicketList.KEY_ID, id);
                    setResult(CustomerEdit.CHANGED, returnIntent);
                    //Toast.makeText(TicketSale.this, (quantity + "Ticket(s) sold"), Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(TicketSale.this, ActivityRaffleList.class);
                    //startActivity(intent);
                    finish();
                }
            }
        });
    }
}
