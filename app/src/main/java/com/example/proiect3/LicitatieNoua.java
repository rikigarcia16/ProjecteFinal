package com.example.proiect3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class LicitatieNoua extends AppCompatActivity {

    private EditText etNume;
    private EditText etDescriere;
    public Spinner spinner;
    private Button btn_add;
    private Button btn_add_image;
    private ImageView imageView;
    public static Bitmap bitmap;
    private EditText etPret;
    private EditText etTelefon;
    private EditText etOras;

    private boolean updateOk=false; //inca nu s a facut update


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licitatie_noua);
        initViews();
        if(getIntent().hasExtra("id_lic")){
            Auction auction = AuctionDB.getInstance(LicitatieNoua.this).auctionDAO().getAuctionById(getIntent().getIntExtra("id_lic",0));
            Log.v("update", String.valueOf(auction.getUserId()));
            Log.v("update", auction.toString());
            updateAuction(auction);
            updateOk=true;
        }



        btn_add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(LicitatieNoua.this);
                imageView.buildDrawingCache();
                bitmap = imageView.getDrawingCache();
            }
        });


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!updateOk) {
                    if (validateContent() ) {
                        Auction auction = new Auction();

                        auction.setNumeProdus(etNume.getText().toString());
                        auction.setDescriereProdus(etDescriere.getText().toString());
                        String categorie = spinner.getSelectedItem().toString();
                        auction.setCategorie(categorie);
                        auction.setPretPornire(etPret.getText().toString());
                        auction.setNumarTelefon(etTelefon.getText().toString());
                        auction.setOras(etOras.getText().toString());
                        auction.setUserId(getIntent().getIntExtra("user_id", 0));

                        int userId = auction.getUserId();

                        //introducere in sqllite
                        AuctionDB.getInstance(view.getContext()).insertLicitatie(auction,new IDbCallback(){

                            @Override
                            public void onSuccess() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.v("licitatie",auction.toString());
                                        Intent intent = new Intent(LicitatieNoua.this, MainActivity.class);
                                        intent.putExtra("categorie", categorie);
                                        intent.putExtra("user_id", userId);
                                        startActivity(intent);
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Throwable error) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.v("licitatie",error.getLocalizedMessage());
                                    }
                                });
                            }
                        });
//                        List<Auction> auctions = AuctionDB.getInstance(view.getContext()).auctionDAO().getAllAuctions();
//                        Log.v("auctions_from_db", auctions.toString());
                        //Log.v("categorie", categorie);

                    }
                }
                else {
                    Auction auction = AuctionDB.getInstance(LicitatieNoua.this).auctionDAO().getAuctionById(getIntent().getIntExtra("id_lic",0));
                    //                    Log.v("update", String.valueOf(auction.getUserId()));
//                    Log.v("update", auction.toString());
//                    updateAuction(auction);
                    //auction.setUserId(getIntent().getIntExtra("user_id3",0));

                    auction.setNumeProdus(etNume.getText().toString());
                    auction.setDescriereProdus(etDescriere.getText().toString());
                    String categorie = spinner.getSelectedItem().toString();
                    auction.setCategorie(categorie);
                    auction.setPretPornire(etPret.getText().toString());
                    auction.setNumarTelefon(etTelefon.getText().toString());
                    auction.setOras(etOras.getText().toString());
                    auction.setId(getIntent().getIntExtra("id_lic",0));
                    Log.v("update", auction.toString());
                    AuctionDB.getInstance(LicitatieNoua.this).updateLicitatie(auction,new IDbCallback(){

                        @Override
                        public void onSuccess() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.v("update",auction.toString());
                                    Intent intent = new Intent(LicitatieNoua.this, MainActivity.class);
                                    intent.putExtra("categorie", categorie);
                                    intent.putExtra("user_id", auction.getUserId());
                                    startActivity(intent);
                                }
                            });


                        }

                        @Override
                        public void onFailure(Throwable error) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.v("update",error.getLocalizedMessage());
                                }
                            });
                        }
                    });
                }

            }
        });

    }

    private void initViews(){
        etNume=findViewById(R.id.et_numeProdus);
        etDescriere=findViewById(R.id.et_descriereProdus);
        imageView = findViewById(R.id.imagine);
        btn_add_image = findViewById(R.id.add_image);
        spinner = findViewById(R.id.sp_categorie);
        etPret=findViewById(R.id.et_pret);
        etTelefon=findViewById(R.id.et_nr_tel);
        etOras=findViewById(R.id.et_oras);
        btn_add=findViewById(R.id.btn_add);
    }

    private boolean validateContent(){
        if(etNume.getText().toString().isEmpty()){
            Toast.makeText(this,"Numele trebuie completat",Toast.LENGTH_LONG).show();
            return false;
        }

        if(etPret.getText().toString().isEmpty()){
            Toast.makeText(this,"Pretul de pornire trebuie completat",Toast.LENGTH_LONG).show();
            return false;
        }

        if(etTelefon.getText().toString().isEmpty()){
            Toast.makeText(this,"Numarul de telefon trebuie completat",Toast.LENGTH_LONG).show();
            return false;
        }

        if(etOras.getText().toString().isEmpty()){
            Toast.makeText(this,"Orasul trebuie completat",Toast.LENGTH_LONG).show();
            return false;
        }

        return  true;

    }

    private void updateAuction(Auction auction){
        etNume.setText(auction.getNumeProdus());
        etDescriere.setText(auction.getDescriereProdus());
        etPret.setText(auction.getPretPornire());
        etTelefon.setText(auction.getNumarTelefon());
        etOras.setText(auction.getOras());
    }


    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Alege imagine");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }
}