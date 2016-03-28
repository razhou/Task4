package com.example.ghost.task4;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
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
import android.widget.Toast;

import com.example.ghost.task4.models.SynTransaction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class Sinkron extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DBController controller = new DBController(this);

    Retrofit retrofit;
    Gson gson;
    TransApi TA;
    Button syn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinkron);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        syn = (Button) findViewById(R.id.btnSin);

        syn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    send_data("http://private-69e82-andro.apiary-mock.com/");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });





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
        getMenuInflater().inflate(R.menu.sinkron, menu);
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
            Intent i = new Intent(Sinkron.this,MainActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_trans) {
            Intent i = new Intent(Sinkron.this, Transaction.class);
            startActivity(i);

        } else if (id == R.id.nav_sync) {
            Intent i = new Intent(Sinkron.this,Sinkron.class);
            startActivity(i);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void send_data( String url_target)throws JSONException{
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        retrofit = new Retrofit.Builder().baseUrl(url_target)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        TA = retrofit.create(TransApi.class);

        SynTransaction ST = new SynTransaction();

        boolean status=false;


        Cursor expenses = controller.list_expenses();
        Cursor incomes = controller.list_income();

        final ProgressDialog prgDialog = new ProgressDialog(Sinkron.this);

        prgDialog.setIndeterminate(true);
        prgDialog.setMessage("Processing...");
        prgDialog.show();

        JSONArray listTransc = new JSONArray();

        try {
            while (expenses.moveToNext()) {

                JSONObject trans_json = new JSONObject();

                trans_json.put("id", expenses.getInt(expenses.getColumnIndex("id")));

                trans_json.put("description", expenses.getString(expenses.getColumnIndex("DESC_EXP")));

                trans_json.put("amount", expenses.getString(expenses.getColumnIndex("AMT_EXP")));



                listTransc.put(trans_json);

            }

            while (incomes.moveToNext()) {
                JSONObject trans_json = new JSONObject();

                trans_json.put("id", incomes.getInt(incomes.getColumnIndex("id")));

                trans_json.put("description", incomes.getString(incomes.getColumnIndex("DESC_INC")));

                trans_json.put("amount", incomes.getString(incomes.getColumnIndex("AMT_INC")));

                listTransc.put(trans_json);
            }

        } catch (JSONException e) {
            Log.i("info", String.valueOf(e));
        }


        for(int i=0;i<listTransc.length();i++) {

            JSONObject transaction = listTransc.getJSONObject(i);

            if (transaction.get("id")==""&&transaction.get("description")==""&&transaction.get("amount")==""){
                status=false;

            }else{

                status=true;
            }
        }

        Call<SynTransaction> call = TA.synTransaction(ST);

        if(status==false){

            if (prgDialog.isShowing())
                prgDialog.dismiss();
        }else {
            call.enqueue(new Callback<SynTransaction>() {
                @Override
                public void onResponse(Response<SynTransaction> response, Retrofit retrofit) {
                    int responCode = response.code();
                    try {
                        String statusResponse = response.body().getStatus();
                        String messageResponse = response.body().getMessage();
                        if (statusResponse.equals("200")) {
                            Toast.makeText(Sinkron.this, messageResponse, Toast.LENGTH_SHORT).show();
                        } else if (statusResponse.equals("400")) {
                            Toast.makeText(Sinkron.this, messageResponse, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.d("Response exception ", e.toString());
                    }
                    if (prgDialog.isShowing())
                        prgDialog.dismiss();
                }

                @Override
                public void onFailure(Throwable t) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(Sinkron.this);

                    if (prgDialog.isShowing())
                        prgDialog.dismiss();

                    alert.setCancelable(false).setTitle("Synchronize").setMessage("Fails Synchronize.")
                            .setPositiveButton("Skip", new DialogInterface.OnClickListener() {

                                @Override

                                public void onClick(DialogInterface dialog, int which) {

                                    Toast.makeText(Sinkron.this, "Skip.", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }

                            })


                            .setNegativeButton("Retry", new DialogInterface.OnClickListener() {

                                @Override

                                public void onClick(DialogInterface dialog, int which) {

                                    Toast.makeText(Sinkron.this, "No Internet Connection.", Toast.LENGTH_SHORT).show();
                                }

                            });

                    alert.show();
                }
            });
        }
        controller.close();
    }
}
