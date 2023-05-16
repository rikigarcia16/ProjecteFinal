package com.example.proiect3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Objects;

public class SignupActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_email;
    private EditText et_password;
    private EditText et_retype_password;
    private Button et_btn_signup;
    private Boolean hasExtra = false;

//    private DatabaseReference reff;
    private User user; // e mandatory - o folosim pentru FIREBASE


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //Objects.requireNonNull(getSupportActionBar()).setTitle("Sign-up page");
        //myDb = new DatabaseHelper(this);

        user = new User();
//        reff = FirebaseDatabase.getInstance().getReference();

        init();

        if(getIntent().hasExtra("id_user")){
            hasExtra = true;
            int id_user = getIntent().getIntExtra("id_user", 0);
            User user = AuctionDB.getInstance(this).userDAO().getById(id_user);
            updateUser(user);
            et_btn_signup.setText("Update user");
        }

        et_btn_signup.setOnClickListener((view -> {
            if (!hasExtra) {
                if (validateContent()) {
                    String username = et_username.getText().toString();
                    String email = et_email.getText().toString();
                    String pass1 = et_password.getText().toString();
                    user.setUsername(username);
                    user.setPassword(pass1);
                    user.setEmail(email);
                    List<User> usersByUsername = AuctionDB.getInstance(this).userDAO().getAllByName(username);
                    if (usersByUsername.size() == 0) {
                        AuctionDB.getInstance(this).insertUser(user, new IDbCallback() {
                            @Override
                            public void onSuccess() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        List<User> users = AuctionDB.getInstance(SignupActivity.this).userDAO().getAllUsers();
                                        Log.v("users_from_db", users.toString());
                                        Toast.makeText(SignupActivity.this, "Signup successful!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Throwable error) {
                                Log.v("insert_user", error.getLocalizedMessage());
                            }
                        });

                    } else {
                        Toast.makeText(SignupActivity.this, "Username already exists", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                User user = AuctionDB.getInstance(this).userDAO().getById(getIntent().getIntExtra("id_user", 0));
                user.setUsername(et_username.getText().toString());
                user.setEmail(et_email.getText().toString());
                user.setPassword(et_password.getText().toString());
                AuctionDB.getInstance(this).updateUser(user, new IDbCallback() {
                    @Override
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(SignupActivity.this, ProfilFragment.class);
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Throwable error) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.v("update_user", error.getLocalizedMessage());
                            }
                        });
                    }
                });
            }


//                reff.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if(!snapshot.exists()){
//                            user.setUsername(username);
//                            user.setPassword(pass1);
//                            user.setEmail(email);
//
//
//                            //reff.child("User "+String.valueOf(maxId+1)).setValue(user);
//                            reff.child(et_username.getText().toString()).setValue(user);
//                            Toast.makeText(Signup.this, "Signup successful!", Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(Signup.this, MainActivity.class);
//                            startActivity(intent);
//                        }
//                        else{
//                            Toast.makeText(Signup.this, "Username already exists", Toast.LENGTH_LONG).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });

            //myDb.insertData(et_username.getText().toString(), et_email.getText().toString(),
            // et_password.getText().toString()); // insert values in database
        }));
    }

    private void updateUser (User user){
        et_username.setText(user.getUsername());
        et_email.setText(user.getEmail());
        et_password.setText(user.getPassword());
        et_retype_password.setText(user.getPassword());
    }

    private void init(){
        et_username = findViewById(R.id.et_username);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_retype_password = findViewById(R.id.et_retype_password);
        et_btn_signup = findViewById(R.id.sign_up_btn);
    }


    private boolean validateContent(){
        String username = et_username.getText().toString();
        String email = et_email.getText().toString();
        String pass1 = et_password.getText().toString();
        String pass2 = et_retype_password.getText().toString();

        if(username.isEmpty()){
            Toast.makeText(this, "Username must not be empty", Toast.LENGTH_LONG).show();
            //et_username.setError("Username must not be empty");
            return false;
        }

        if(email.isEmpty()){
            Toast.makeText(this, "Email must not be empty", Toast.LENGTH_LONG).show();
            return false;
        }

        if(pass1.isEmpty() || pass1.length() < 8){
            Toast.makeText(this, "Password must not be empty and must be 8+ characters", Toast.LENGTH_LONG).show();
            return false;
        }

        if(pass2.isEmpty()){
            Toast.makeText(this, "Password must not be empty", Toast.LENGTH_LONG).show();
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Email format invalid", Toast.LENGTH_LONG).show();
            return false;
        }

        if(!pass1.equals(pass2)){
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
