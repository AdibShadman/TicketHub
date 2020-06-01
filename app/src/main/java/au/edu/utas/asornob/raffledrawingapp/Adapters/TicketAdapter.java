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

import au.edu.utas.asornob.raffledrawingapp.R;
import au.edu.utas.asornob.raffledrawingapp.Ticket;

public class TicketAdapter extends ArrayAdapter<Ticket>
{
    private int mLayoutResourceID;
    public TicketAdapter(Context context, List<Ticket> tickets)
    {
        super(context, 0, tickets);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_ticket, parent, false);
        }

        Ticket ticket = getItem(position);

        TextView txtName = (TextView) convertView.findViewById(R.id.txtCustomer);
        txtName.setText(ticket.getCustomer().getName());
        TextView txtPhone = (TextView)convertView.findViewById(R.id.txtQuant);
        txtPhone.setText(ticket.getCustomer().getPhone());

        return convertView;
    }
}
