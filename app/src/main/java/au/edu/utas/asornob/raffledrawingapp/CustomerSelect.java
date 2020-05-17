package au.edu.utas.asornob.raffledrawingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CustomerSelect extends AppCompatActivity {
    public static final int VALID_CUSTOMER = 1;
    public final static String KEY_ID = "id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_select);

        Database databaseConnection = new Database(this);
        final SQLiteDatabase database = databaseConnection.open();

        ArrayList<Customer> customers = CustomerTable.selectAll(database);
        /*ArrayList<String> customerNames = new ArrayList<String>();
        for(int i = 0; i< customers.size(); i++){
            customerNames.add(customers.get(i).getName());
        }*/
        CustomerAdapter myListAdapter = new CustomerAdapter(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                customers
        );

        ListView listCustomer = findViewById(R.id.listCustomers);
        listCustomer.setAdapter(myListAdapter);
    }
}
