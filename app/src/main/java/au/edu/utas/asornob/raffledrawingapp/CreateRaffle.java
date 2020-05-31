package au.edu.utas.asornob.raffledrawingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateRaffle extends AppCompatActivity
{
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Button addRaffleButtonInRaffleForm;

    private EditText raffleName;
    private EditText raffleDescription;
    private EditText raffleTotalTickets;
    private EditText raffleTicketPrice;
    private TextView startDate;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;
    private EditText raffleType;
    private ImageView image;
    private Uri currentPhotoUri;
    private String currentPhotoPath;
    private Button imageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_raffle);
        Database databaseConnection = new Database(this);
        final SQLiteDatabase database = databaseConnection.open();


        imageButton=(Button)findViewById(R.id.image_button);
        imageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                requestToTakeAPicture();
            }
        });

        image = (ImageView)findViewById(R.id.image_view);

        startDate = (TextView) findViewById(R.id.start_date_text_view);
        startDate.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year =  cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CreateRaffle.this,android.R.style.Theme_DeviceDefault_Dialog_MinWidth,mDateSetListener,year, month, day );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener()
        {

            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                String date;
                month = month + 1;
                if(month < 10 && dayOfMonth > 10)
                {

                    date = year + "-0" + month + "-" + dayOfMonth;

                }
                else if(month > 10 && dayOfMonth < 10 )
                {
                     date = year + "-" + month + "-0" + dayOfMonth;
                }
                else if(month < 10 && dayOfMonth < 10)
                {
                    date = year + "-0" + month + "-0" + dayOfMonth;
                }
                else
                {
                     date = year + "-" + month + "-" + dayOfMonth;
                }

                Log.d("Testing","onDateSET: yyyy-MM-dd: " + month + "/" + dayOfMonth + "/" + year);

                startDate.setText(date);

            }
        };






                addRaffleButtonInRaffleForm = (Button) findViewById(R.id.add_raffle_button);
                addRaffleButtonInRaffleForm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                raffleName = (EditText) findViewById(R.id.raffle_entry_name);
                raffleDescription = (EditText) findViewById(R.id.raffle_entry_description);
                raffleTotalTickets = (EditText) findViewById(R.id.raffle_entry_total_tickets);
                raffleTicketPrice = (EditText) findViewById(R.id.raffle_ticket_price);
                raffleType = (EditText) findViewById(R.id.raffle_type);




                if (!canSubmit())
                {
                    new AlertDialog.Builder(CreateRaffle.this).setTitle("Raffle Requires More Information")
                            .setMessage("Raffle form Must Include: Raffle Name, Description, TicketPrice,Total Tickets, Raffle Type, Start Date and End date")
                            .setPositiveButton("Okay", null).show();
                }
                else
                {



                        String stringRaffleName = raffleName.getText().toString();
                        String stringRaffleDescription = raffleDescription.getText().toString();
                        int integerTotalTickets = Integer.parseInt(raffleTotalTickets.getText().toString());
                        Double doubleTicketPrice = Double.parseDouble(raffleTicketPrice.getText().toString());
                        String stringRaffleType = raffleType.getText().toString();



                        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date newStartDate = new Date();


                        try
                        {
                            newStartDate = newDateFormat.parse(startDate.getText().toString());


                        }
                        catch(ParseException pe)
                        {
                            //oops
                        }

                    if(stringRaffleType.equalsIgnoreCase("Marginal") || stringRaffleType.equalsIgnoreCase("Normal")) {
                        Raffle raffle = new Raffle();
                        raffle.setName(stringRaffleName);
                        raffle.setDescription(stringRaffleDescription);
                        raffle.setTotalTickets(integerTotalTickets);
                        raffle.setTicketPrice(doubleTicketPrice);
                        raffle.setStartDate(newStartDate);

                        raffle.setRaffleType(stringRaffleType);
                        raffle.setPhoto(currentPhotoUri);

                        RaffleTable.insert(database, raffle);

                        Toast.makeText(CreateRaffle.this, "New Raffle inserted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CreateRaffle.this, ActivityRaffleList.class);
                        startActivity(intent);
                    }

                    else
                    {
                        new AlertDialog.Builder(CreateRaffle.this).setTitle("Mismatch Raffle Type")
                                .setMessage("Raffle type should be either Normal or Marginal")
                                .setPositiveButton("Okay", null).show();
                    }

                }



            }
        });
    }

 private boolean canSubmit()
 {
     boolean canSubmit =  true;
     if(TextUtils.isEmpty(raffleName.getText()))
     {
         canSubmit = false;

     }
     if(TextUtils.isEmpty(raffleDescription.getText()))
     {
         canSubmit = false;

     }
     if(TextUtils.isEmpty(raffleTotalTickets.getText()))
     {
         canSubmit = false;

     }
     if(TextUtils.isEmpty(raffleTicketPrice.getText()))
     {
         canSubmit = false;

     }
     if(TextUtils.isEmpty(raffleType.getText()))
     {
         canSubmit = false;

     }
     if(TextUtils.isEmpty(startDate.getText()))
     {
         canSubmit = false;

     }


     return canSubmit;

 }
 private void  requestToTakeAPicture()
 {
     ActivityCompat.requestPermissions(
             CreateRaffle.this,
             new String[]{Manifest.permission.CAMERA},
             REQUEST_IMAGE_CAPTURE);
 }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permission[], int[] grantResults)
 {
    switch(requestCode)
    {
        case REQUEST_IMAGE_CAPTURE:
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                takeAPicture();
            }
            else
            {
                Log.i("Testing", "Requesting to take a picture error");
            }
            break;
    }
 }

 private void takeAPicture()
 {
     Intent takeAPictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

     if(takeAPictureIntent.resolveActivity(getPackageManager()) != null)
     {
        try
            {
                File photoFile = createImageFile();
                Uri photoURI = FileProvider.getUriForFile(this, "au.edu.utas.asornob.raffledrawingapp", photoFile);
                currentPhotoUri = FileProvider.getUriForFile(this, "au.edu.utas.asornob.raffledrawingapp", photoFile);
                takeAPictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takeAPictureIntent, REQUEST_IMAGE_CAPTURE);

            }
        catch(IOException ex)
         {
             Log.i("Testing", "Error creating Image file for Camera");
         }
     }

 }

    private File createImageFile() throws IOException
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());
        String imageFileName = "myImage_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();

        return image;
    }


@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
        setPic(image, currentPhotoPath);
    }
}
    private void setPic(ImageView entryMedia, String path)
    {
        int targetW = entryMedia.getWidth();
        int targetH = entryMedia.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
        image.setImageBitmap(bitmap);
    }



}