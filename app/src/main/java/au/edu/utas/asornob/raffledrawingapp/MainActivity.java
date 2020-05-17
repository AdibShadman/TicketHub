package au.edu.utas.asornob.raffledrawingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity 
{
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        deleteRaffles();

        addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateRaffleActivity();

            }

        });
    }
    public void openCreateRaffleActivity()
    {
        //Intent intent = new Intent(MainActivity.this, TempSell.class);
        Intent intent = new Intent(this, TempSell.class);
        startActivity(intent);
    }
    
    private void  deleteRaffles()
    {
        Database databaseConnection = new Database(this);
        final SQLiteDatabase db = databaseConnection.open();


        RaffleTable.delete(db);
    }
}
