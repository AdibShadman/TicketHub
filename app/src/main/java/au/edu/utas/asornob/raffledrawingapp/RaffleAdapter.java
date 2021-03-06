package au.edu.utas.asornob.raffledrawingapp;

import android.app.Service;
import android.content.Context;
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
import au.edu.utas.asornob.raffledrawingapp.Raffle;

public class RaffleAdapter extends ArrayAdapter<Raffle>
{
    private int mLayoutResourceID;
    public RaffleAdapter (Context context, int resource, List<Raffle> objects)
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

        Raffle raffle = this.getItem(position);

        TextView raffleName = row.findViewById(R.id.raffle_name);
        raffleName.setText(raffle.getName());
        TextView raffleDescription = row.findViewById(R.id.raffle_description);
        raffleDescription.setText(raffle.getDescription());

        Button sellTickets = row.findViewById(R.id.sell_tickets);
        Button  listTickets = row.findViewById((R.id.list_tickets));
        Button editRaffle = row.findViewById(R.id.edit_raffle);
        Button deleteRaffle = row.findViewById((R.id.delete_raffle));
        Button selectWinner = row.findViewById(R.id.select_winner);

        return row;

    }

}
