package au.edu.utas.asornob.raffledrawingapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import au.edu.utas.asornob.raffledrawingapp.Customer;
import au.edu.utas.asornob.raffledrawingapp.Lists.CustomerSelect;
import au.edu.utas.asornob.raffledrawingapp.R;

public class CustomerAdapter extends ArrayAdapter<Customer> {
    private int mLayoutResourceID;
    public CustomerAdapter(Context context, List<Customer> customers)
    {
        super(context, 0, customers);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_customer, parent, false);
        }

        Customer customer = getItem(position);

        Button btnSelect = (Button) convertView.findViewById(R.id.btnSelect);
        btnSelect.setTag(customer);

        TextView txtCustomerName = (TextView) convertView.findViewById(R.id.txtCustomerName);
        txtCustomerName.setText(customer.getName());
        TextView txtPhone = (TextView) convertView.findViewById(R.id.txtPhone);
        txtPhone.setText(customer.getPhone());

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Log.d("wow ","wow1");
                Customer customer = (Customer) view.getTag();

                ((CustomerSelect) getContext()).ReturnIntent(customer);
            }
        });

        return convertView;
    }
}
