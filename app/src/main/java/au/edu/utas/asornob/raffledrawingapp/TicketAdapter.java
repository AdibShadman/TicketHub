package au.edu.utas.asornob.raffledrawingapp;

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

public class TicketAdapter extends ArrayAdapter<Ticket> {
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

        TextView txtPhone = (TextView) convertView.findViewById(R.id.txtCustomer);
        txtPhone.setText(ticket.getCustomer().getName());

        return convertView;
    }
}