package au.edu.utas.asornob.raffledrawingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class RaffleAdapter extends ArrayAdapter<Raffle>
{
    private int mLayoutResourceID;
    public RaffleAdapter(Context context, int resource, List<Raffle> objects)
    {
        super(context, resource, objects);
        this.mLayoutResourceID = resource;
    }
    @NonNull
     @Override
    public View getView( int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(mLayoutResourceID, parent, false);

        Raffle raffle = this.getItem(position);

        TextView raffleName = row.findViewById(R.id.raffle_name);
        raffleName.setText(raffle.getName());
        TextView raffleDescription = row.findViewById(R.id.raffle_description);
        raffleDescription.setText(raffle.getDescription());

       
        return row;

    }

}
