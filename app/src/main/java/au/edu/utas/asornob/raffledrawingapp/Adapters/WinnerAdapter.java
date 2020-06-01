package au.edu.utas.asornob.raffledrawingapp.Adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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
import au.edu.utas.asornob.raffledrawingapp.Winner;

public class WinnerAdapter extends ArrayAdapter<Winner> {
    private int mLayoutResourceID;
    public WinnerAdapter(Context context, List<Winner> winners)
    {
        super(context, 0, winners);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_winner, parent, false);
        }

        Winner winner = getItem(position);

        String place = "none";
        int placeNum = winner.getPlace() + 1;
        String[] suffix = new String[] {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        switch (placeNum%100) {
            case 11:
            case 12:
            case 13:
                place = placeNum + suffix[0];
                break;
            default:
                place = placeNum + suffix[placeNum%10];
        }

        TextView txtPlace = (TextView) convertView.findViewById(R.id.txtPlace);
        txtPlace.setText(place);
        TextView txtWinnerName = (TextView) convertView.findViewById(R.id.txtWinnerName);
        txtWinnerName.setText(winner.getCustomer().getName());

        return convertView;
    }
}
