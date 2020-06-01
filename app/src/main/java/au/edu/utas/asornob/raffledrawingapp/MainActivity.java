package au.edu.utas.asornob.raffledrawingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import au.edu.utas.asornob.raffledrawingapp.Lists.RaffleList;
import au.edu.utas.asornob.raffledrawingapp.Tables.RaffleTable;

public class MainActivity extends AppCompatActivity 
{
    private Button addButton;
    private Button listViewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        finish();

        Intent intent = new Intent(this, RaffleList.class);
        startActivity(intent);
    }
    public void openCreateRaffleActivity()
    {
        Intent intent = new Intent(this, RaffleCreate.class);
        startActivity(intent);
    }

        public void openActivityRaffleList()
        {
            Intent intent = new Intent(this, RaffleList.class);
            startActivity(intent);
        }

    /*private void deleteRaffles() {
        Database databaseConnection = new Database(this);
        final SQLiteDatabase db = databaseConnection.open();

        RaffleTable.delete(db);
    }*/
}
