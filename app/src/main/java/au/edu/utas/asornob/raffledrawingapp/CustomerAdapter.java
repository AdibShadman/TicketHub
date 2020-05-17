package au.edu.utas.asornob.raffledrawingapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomerAdapter extends ArrayAdapter<Customer> {
    private int mLayoutResourceID;
    public CustomerAdapter(Context context, int resource, List<Customer> objects)
    {
        super(context, resource, objects);
        this.mLayoutResourceID = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(mLayoutResourceID, parent, false);

        if(convertView != null)
        {

        }

        Customer customer = this.getItem(position);

        if(customer != null) {
            // TextView txtcustomerName = View.findViewById(R.id.txtCustomerName);
            //txtCustomerName.setText(customer.getName());

        }
        return row;
    }
}
