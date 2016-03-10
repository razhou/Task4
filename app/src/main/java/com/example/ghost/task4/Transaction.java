package com.example.ghost.task4;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Transaction extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    EditText desc_exp, amt_exp,desc_inc,amt_inc;

    Button btnexp,btninc;
    DBController DB;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DB = new DBController(this);

        desc_exp=(EditText)findViewById(R.id.edtNewExp);
        amt_exp=(EditText)findViewById(R.id.edtAmntExp);
        desc_inc=(EditText)findViewById(R.id.edtNewInc);
        amt_inc=(EditText)findViewById(R.id.edtAmnInc);
        btnexp=(Button)findViewById(R.id.btnExp);
        btninc=(Button)findViewById(R.id.btnInc);

        btnexp.setOnClickListener(this);
        btninc.setOnClickListener(this);



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
        getMenuInflater().inflate(R.menu.transaction, menu);
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
            Intent i = new Intent(Transaction.this,MainActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_trans) {
            Intent i = new Intent(Transaction.this, Transaction.class);
            startActivity(i);

        } else if (id == R.id.nav_sync) {
            Intent i = new Intent(Transaction.this,Sinkron.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void onClick(View v){

        switch(v.getId()){
            case R.id.btnExp:
                int amtExp = Integer.parseInt(amt_exp.getText().toString());
               // Cursor Expenses =DB.totalAmount();

                    boolean result_exp = DB.save_expense(desc_exp.getText().toString(),amtExp);

                    if (result_exp) {
                        Toast.makeText(Transaction.this, "Succes add expense", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Transaction.this, "Failed add expense", Toast.LENGTH_LONG).show();
                    }


                ///if(Expenses.getCount()==0){
                    ///alert_message("Message","No data amount exp");
              ///  }

                //StringBuffer buffer = new StringBuffer();

               // while (Expenses.moveToNext()) {



                   // buffer.append("Amount: " + Expenses.getString(0) + "\n");



               // }




                break;
            case R.id.btnInc:

                int amtInc = Integer.parseInt(amt_inc.getText().toString());
                // Cursor Expenses =DB.totalAmount();

                boolean result_inc = DB.save_income(desc_inc.getText().toString(),amtInc);

                if (result_inc) {
                    Toast.makeText(Transaction.this, "Succes add Income", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Transaction.this, "Failed add Income", Toast.LENGTH_LONG).show();
                }

                break;

    }



}
    public void alert_message(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);

        builder.setTitle(title);

        builder.setMessage(message);

        builder.show();

    }


}
