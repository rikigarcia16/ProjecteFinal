package com.example.proiect3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String NUME = "nume_user";
    private static final String CHECK = "check";
    public static final String CHECK_NUME = "check_nume";
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Boolean checked = true;
    private TextView tv_check;
    private MenuItem menuItem;

    private SharedPreferences preferences;

    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences(CHECK_NUME, MODE_PRIVATE);;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        incarcaPreferinte();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        if(savedInstanceState == null) {
            Bundle b = new Bundle();
            int user_id = getIntent().getIntExtra("user_id", 0);
            b.putInt("user_id_app", user_id);
            AutoFragment fragment = new AutoFragment();
            fragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            navigationView.setCheckedItem(R.id.nav_auto);
        }

        if(getIntent().hasExtra("user_id")) {
            userId = getIntent().getIntExtra("user_id", 0);
        }

        checkCategorie();


    }

    public void checkCategorie() {
        if(getIntent().hasExtra("categorie")) {
            String categorie = getIntent().getStringExtra("categorie");
            if (categorie.equals("Imobiliare/Terenuri")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ImobiliareFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_imobiliare);
            } else if (categorie.equals("Hobby/Sport/Timp liber")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SportFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_sports);
            } else if (categorie.equals("Electronice si Electrocasnice")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ElectroniceFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_electronice);
            } else if (categorie.equals("Telefoane/Tablete/PC")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TelefoaneFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_telefoane);
            } else if (categorie.equals("Echipamente industriale/Utilaje")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UtilajeFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_echipamente);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_auto: {
                Bundle b = new Bundle();
                int user_id = getIntent().getIntExtra("user_id", 0);
                b.putInt("user_id_app", user_id);
                AutoFragment fragment = new AutoFragment();
                fragment.setArguments(b);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }

            case R.id.nav_imobiliare: {
                Bundle b = new Bundle();
                int user_id = getIntent().getIntExtra("user_id", 0);
                b.putInt("user_id_app", user_id);
                ImobiliareFragment fragment = new ImobiliareFragment();
                fragment.setArguments(b);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }

            case R.id.nav_sports: {
                Bundle b = new Bundle();
                int user_id = getIntent().getIntExtra("user_id", 0);
                b.putInt("user_id_app", user_id);
                SportFragment fragment = new SportFragment();
                fragment.setArguments(b);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }

            case R.id.nav_electronice: {
                Bundle b = new Bundle();
                int user_id = getIntent().getIntExtra("user_id", 0);
                b.putInt("user_id_app", user_id);
                ElectroniceFragment fragment = new ElectroniceFragment();
                fragment.setArguments(b);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }

            case R.id.nav_telefoane: {
                Bundle b = new Bundle();
                int user_id = getIntent().getIntExtra("user_id", 0);
                b.putInt("user_id_app", user_id);
                TelefoaneFragment fragment = new TelefoaneFragment();
                fragment.setArguments(b);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }

            case R.id.nav_echipamente: {
                Bundle b = new Bundle();
                int user_id = getIntent().getIntExtra("user_id", 0);
                b.putInt("user_id_app", user_id);
                UtilajeFragment fragment = new UtilajeFragment();
                fragment.setArguments(b);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }

            case R.id.nav_licitatie: {
                userId = getIntent().getIntExtra("user_id",0);
                Intent intent = new Intent(this, LicitatieNoua.class);
                intent.putExtra("user_id",userId);
                startActivity(intent);

                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }

            case R.id.nav_licitatii: {
                userId = getIntent().getIntExtra("user_id",0);
                Log.v("user_main", String.valueOf(userId));
                LicitatiileMeleFragment fragm = new LicitatiileMeleFragment();
                Bundle b = new Bundle();
                b.putInt("user_id",userId);
                fragm.setArguments(b);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragm).commit();
                navigationView.setCheckedItem(R.id.nav_licitatii);

                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }

            case R.id.nav_profil:{
                userId = getIntent().getIntExtra("user_id",0);
                Log.v("user_main", String.valueOf(userId));
                ProfilFragment fragm = new ProfilFragment();
                Bundle b = new Bundle();
                b.putInt("user_id4",userId);
                fragm.setArguments(b);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragm).commit();
                navigationView.setCheckedItem(R.id.nav_profil);

                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }

            case R.id.nav_cb: {
                CompoundButton button = (CompoundButton) item.getActionView();
                if(checked) {
                    button.setChecked(true);
                    preferences = getSharedPreferences(CHECK_NUME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(NUME, getIntent().getStringExtra("nume_user"));
                    editor.putBoolean(CHECK, checked);
                    editor.apply();
                    checked = false;
                    break;
                } else {
                    button.setChecked(false);
                    preferences.edit().clear().apply();
                    checked = true;
                    break;
                }
            }

            case R.id.nav_contact:{
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DespreFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_contact);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
        }
//        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void incarcaPreferinte() {
        preferences = getSharedPreferences(CHECK_NUME, MODE_PRIVATE);
        if(preferences.contains(NUME)) {
            View headerView = navigationView.getHeaderView(0);
            tv_check = headerView.findViewById(R.id.tv_check_nume);
            String nume = preferences.getString(NUME, "");
            tv_check.setText(nume);
        }
        if(preferences.contains(CHECK)) {
            Boolean ck = preferences.getBoolean(CHECK, false);
            menuItem = navigationView.getMenu().findItem(R.id.nav_cb);
            CompoundButton button = (CompoundButton) menuItem.getActionView();
            button.setChecked(ck);
        }
    }
}