package au.edu.utas.asornob.raffledrawingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TempSell extends AppCompatActivity {
    public final static String KEY_RAFFLE_ID = "RaffleID";
    final private static int VAL_PLACEHOLDER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_sell);

        Button btnSellTicket = (Button) findViewById(R.id.btnSellTicket);

        btnSellTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TempSell.this, TicketSale.class);
                i.putExtra(KEY_RAFFLE_ID, VAL_PLACEHOLDER);
                startActivity(i);
            }
        });
    }
}
