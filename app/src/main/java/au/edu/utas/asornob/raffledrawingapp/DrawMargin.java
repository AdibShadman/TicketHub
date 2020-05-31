package au.edu.utas.asornob.raffledrawingapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DrawMargin extends AppCompatActivity {
    public static final int DRAWN = 1;

    private EditText marginNumber;
    private Button drawMargin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_draw_margin);

            Database databaseConnection = new Database(this);
            final SQLiteDatabase database = databaseConnection.open();

            Bundle extras = getIntent().getExtras();
            final int raffleId = extras.getInt(SelectedRaffle.KEY_RAFFLE_ID, -1);

            final Raffle raffle = RaffleTable.selectRaffle(database, raffleId);

            drawMargin = (Button) findViewById(R.id.btnMarginDraw);
            drawMargin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    marginNumber = findViewById(R.id.txtEditMargin);

                    if(marginNumber.getText().toString().compareTo("") == 0) {
                        //empty
                    }
                    else {
                        int margin = Integer.parseInt(marginNumber.getText().toString()) % raffle.getTotalTickets();

                        Bundle extras = getIntent().getExtras();
                        final int id = extras.getInt(SelectedRaffle.KEY_RAFFLE_ID, -1);
                        Raffle raffle = RaffleTable.selectRaffle(database, id);

                        Winner.drawMarginWinners(database, raffle, margin);

                        Intent returnIntent = new Intent();

                        setResult(DRAWN, returnIntent);
                        finish();
                    }
                }
            });
        }
    }
}
