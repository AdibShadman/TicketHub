package au.edu.utas.asornob.raffledrawingapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CustomerDetails extends AppCompatActivity {
    public static final int REQUEST_EDIT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        Database databaseConnection = new Database(this);
        final SQLiteDatabase database = databaseConnection.open();

        Bundle extras = getIntent().getExtras();
        final int id = extras.getInt(TicketList.KEY_ID, -1);
        final Customer customer = CustomerTable.selectCustomer(database, id);

        TextView txtName = (TextView) findViewById(R.id.txtDetailName);
        txtName.setText(customer.getName());
        TextView txtEmail = (TextView) findViewById(R.id.txtDetailEmail);
        txtEmail.setText(customer.getEmail());
        TextView txtPhone = (TextView) findViewById(R.id.txtDetailPhone);
        txtPhone.setText(customer.getPhone());


        Button edit = (Button) findViewById(R.id.btnCustEdit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerDetails.this, CustomerEdit.class);
                i.putExtra(TicketList.KEY_ID, customer.getId());
                startActivityForResult(i, REQUEST_EDIT);
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
                    int customerId = data.getIntExtra(TicketList.KEY_ID, -1);
                    //Customer customer = CustomerTable.selectCustomer(database, customerId);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(TicketList.KEY_ID, customerId);

                    setResult(CustomerEdit.CHANGED, returnIntent);
                    finish();
                    break;
            }
        }
    }
}
