package au.edu.utas.asornob.raffledrawingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomerCreate extends AppCompatActivity
{

    private EditText customerName;
    private EditText customerEmail;
    private EditText customerPhone;
    private Button addCustomerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_create);

        customerName = (EditText) findViewById(R.id.customer_name);
        customerEmail = (EditText) findViewById(R.id.customer_email);
        customerPhone = (EditText) findViewById(R.id.customer_phone);
        addCustomerButton = (Button) findViewById(R.id.add_customer_button);

        addCustomerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Database databaseConnection = new Database(CustomerCreate.this);
                final SQLiteDatabase database = databaseConnection.open();



                if (!canSubmit())
                {
                    new AlertDialog.Builder(CustomerCreate.this).setTitle("Customer Requires More Information")
                            .setMessage("Customer form Must Include: Customer Name, Email and Phone Number")
                            .setPositiveButton("Okay", null).show();
                }
                else
                {
                    String stringCustomerName = customerName.getText().toString();
                    String stringCustomerEmail = customerEmail.getText().toString();
                    String stringCustomerPhone = customerPhone.getText().toString();

                    Customer customer = new Customer();
                    customer.setName(stringCustomerName);
                    customer.setEmail(stringCustomerEmail);
                    customer.setPhone(stringCustomerPhone);

                    CustomerTable.insert(database, customer);
                    Toast.makeText(CustomerCreate.this, "New Customer inserted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CustomerCreate.this, CustomerSelect.class);
                    startActivity(intent);

                }

            }
        });



    }
    private boolean canSubmit()
    {
        boolean canSubmit =  true;
        if(TextUtils.isEmpty(customerName.getText()))
        {
            canSubmit = false;

        }
        if(TextUtils.isEmpty(customerEmail.getText()))
        {
            canSubmit = false;

        }
        if(TextUtils.isEmpty(customerPhone.getText()))
        {
            canSubmit = false;

        }


        return canSubmit;

    }
}


