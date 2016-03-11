package com.example.ghost.task4;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout mLinearListView;
    private ArrayList<listExp> mArrayListData;

    DBController DB = new DBController(this);







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<HashMap<String, String>> ListExp =  DB.getAllUsersEXP();
        ArrayList<HashMap<String, String>> ListInc =  DB.getAllUsersInc();
        HashMap<String, String> Te = DB.totalEXP();
        HashMap<String, String> Ti= DB.totalInc();
        HashMap<String, Integer> Bal =  DB.balance();

        if(ListExp.size()!=0){

            ListAdapter adapter = new SimpleAdapter( MainActivity.this,ListExp, R.layout.listviewexp, new String[] { "DESC_EXP","AMT_EXP"}, new int[] {R.id.desc, R.id.amt});
            ListView myList=(ListView)findViewById(R.id.lv_Exp);
            myList.setAdapter(adapter);


        }

        if(ListInc.size()!=0){

            ListAdapter adapter = new SimpleAdapter( MainActivity.this,ListInc, R.layout.listviewexp, new String[] { "DESC_INC","AMT_INC"}, new int[] {R.id.desc, R.id.amt});
            ListView myList=(ListView)findViewById(R.id.lv_Inc);
            myList.setAdapter(adapter);


        }

        TextView totalExp = (TextView)findViewById(R.id.txtTotAmount);

        String toE = Te.get("total_exp");

        totalExp.setText(toE);

        TextView totalInc = (TextView)findViewById(R.id.txtTotAmntInc);
        String toI = Ti.get("total_inc");
        totalInc.setText(toI);

        TextView balance = (TextView)findViewById(R.id.txtBalance);
        int blnc = Bal.get("balance");
        balance.setText(String.valueOf(blnc));




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);






    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dash) {
            // Handle the camera action
            Intent i = new Intent(MainActivity.this,MainActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_trans) {
            Intent i = new Intent(MainActivity.this, Transaction.class);
            startActivity(i);

        } else if (id == R.id.nav_sync) {
            Intent i = new Intent(MainActivity.this,Sinkron.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
