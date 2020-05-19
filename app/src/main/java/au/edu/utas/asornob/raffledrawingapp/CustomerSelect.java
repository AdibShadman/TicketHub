package au.edu.utas.asornob.raffledrawingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CustomerSelect extends AppCompatActivity {
    public static final int VALID_CUSTOMER = 1;
    public final static String KEY_ID = "id";
    Button newCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_select);

        Database databaseConnection = new Database(this);
        final SQLiteDatabase database = databaseConnection.open();

        ArrayList<Customer> customers = CustomerTable.selectAll(database);

        CustomerAdapter customerAdapter = new CustomerAdapter(
                this,
                customers
        );

        ListView listCustomer = findViewById(R.id.listCustomers);
        listCustomer.setAdapter(customerAdapter);
        newCustomer = (Button) findViewById(R.id.btnNewCustomer);

        newCustomer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerSelect.this, CustomerCreate.class);
                startActivity(i);
            }
        });
    }

    public void ReturnIntent(Customer customer)
    {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(KEY_ID, customer.getId());

        setResult(VALID_CUSTOMER, returnIntent);
        finish();
    }

}
