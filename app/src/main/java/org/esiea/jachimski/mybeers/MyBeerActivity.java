package org.esiea.jachimski.mybeers;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MyBeerActivity extends AppCompatActivity {

    ImageView result;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Button click;

    private AlertDialog.Builder alertDBuild= null;
    private AlertDialog alertD= null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybeer);

        Button click = (Button) findViewById(R.id.camera);
        result = (ImageView) findViewById(R.id.imageView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView button = (TextView) findViewById(R.id.btn_dialog);

        alertDBuild = new AlertDialog.Builder(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertD.show();
            }
        });

        alertDBuild = new AlertDialog.Builder(this).setTitle(R.string.dialog_ok).setMessage(R.string.dialog_sure).setPositiveButton("yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Toast.makeText(getApplicationContext(),getString(R.string.dialog_box),Toast.LENGTH_LONG).show();
                intentFonction();
            }
            //Si l'on reponds "non"
        }).setNegativeButton("No",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Toast.makeText(getApplicationContext(), getString(R.string.dialog_cancel), Toast.LENGTH_LONG).show();
            }
        }).setIcon(android.R.drawable.ic_dialog_alert);
        final FrameLayout frameView = new FrameLayout(this);
        alertDBuild.setView(frameView);
        alertD = alertDBuild.create();
        LayoutInflater inflater = alertD.getLayoutInflater();

    }

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            result.setImageBitmap(imageBitmap);
        }
    }

    void intentFonction() {
        Intent intent = new Intent(this, BarmanActivity.class);
        startActivity(intent);
    }
}