package com.ashfanndm.covid19srilankanews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.novoda.merlin.Connectable;
import com.novoda.merlin.Merlin;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView reported, treatment, healed, suspicious, death, updatedTime, globalTotal, globalRecovered, globalDeath;

    private SwipeRefreshLayout swipeContainer;

    ProgressDialog nDialog;

    Merlin merlin;
    Context context;
    String getReported,getTreatment,getHealed,getSuspicious,getDeath,getTime,getGlobalTotal,getGlobalRecovered,getGlobalDeath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        merlin = new Merlin.Builder().withConnectableCallbacks().build(context);


        reported = findViewById(R.id.reported);
        treatment = findViewById(R.id.treatment);
        healed = findViewById(R.id.healed);
        suspicious = findViewById(R.id.suspicious);
        death = findViewById(R.id.death);
        updatedTime = findViewById(R.id.time);

        globalTotal = findViewById(R.id.global_reported);
        globalRecovered =findViewById(R.id.global_healed);
        globalDeath = findViewById(R.id.global_death);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);


        coronaInfo();

        merlin.registerConnectable(new Connectable() {
            @Override
            public void onConnect() {
                // Do something you haz internet!
                coronaInfo();
            }
        });



        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isNetworkConnected()){
                    //nDialog.dismiss();
                    swipeContainer.setRefreshing(false);
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    return;
                }

                coronaInfo();
            }
        });
    }


    @Override
    protected void onPause() {
        merlin.unbind();
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //coronaInfo();
    }

    public void coronaInfo(){

        if (!isNetworkConnected()){
            //nDialog.dismiss();
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();

            SharedPreferences sh = getSharedPreferences("MySharedPref",Context.MODE_PRIVATE);
            getReported = sh.getString("getReported", "");
            getTreatment = sh.getString("getTreatment", "");
            getHealed = sh.getString("getHealed", "");
            getSuspicious = sh.getString("getSuspicious", "");
            getDeath = sh.getString("getDeath", "");
            getTime = sh.getString("getTime", "");
            getGlobalTotal = sh.getString("getGlobalTotal", "");
            getGlobalRecovered = sh.getString("getGlobalRecovered", "");
            getGlobalDeath = sh.getString("getGlobalDeath", "");

            reported.setText(getReported);
            treatment.setText(getTreatment);
            healed.setText(getHealed);
            suspicious.setText(getSuspicious);
            death.setText(getDeath);
            updatedTime.setText(getTime);
            globalTotal.setText(getGlobalTotal);
            globalRecovered.setText(getGlobalRecovered);
            globalDeath.setText(getGlobalDeath);
            return;
        }


        nDialog = new ProgressDialog(MainActivity.this);
        nDialog.setMessage("Loading....");
        nDialog.setTitle("Getting Data");
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hpb.health.gov.lk/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<People> call = api.getPerson();

        call.enqueue(new Callback<People>() {
            @Override
            public void onResponse(Call<People> call, Response<People> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "code"+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                People peoples = response.body();

                String a = peoples.getmData().getReported()+"";
                String b = peoples.getmData().getTreatment()+"";
                String c = peoples.getmData().getHealed()+"";
                String d = peoples.getmData().getSuspicious()+"";
                String e = peoples.getmData().getDeath()+"";
                String time = "Updated Time : "+peoples.getmData().getTime()+"";
                String gR = peoples.getmData().getGlobalTotal()+"";
                String gH = peoples.getmData().getGlobalRecovered()+"";
                String gD = peoples.getmData().getGlobalDeath()+"";



                // Storing data into SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",Context.MODE_PRIVATE);
                // Creating an Editor object
                // to edit(write to the file)
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                // Storing the key and its value
                // as the data fetched from edittext
                myEdit.putString("getReported",a);
                myEdit.putString("getTreatment",b);
                myEdit.putString("getHealed",c);
                myEdit.putString("getSuspicious",d);
                myEdit.putString("getDeath",e);
                myEdit.putString("getTime",time);
                myEdit.putString("getGlobalTotal",gR);
                myEdit.putString("getGlobalRecovered",gH);
                myEdit.putString("getGlobalDeath",gD);
                // Once the changes have been made,
                // we need to commit to apply those changes made,
                // otherwise, it will throw an error
                myEdit.apply();


/*                SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                getReported = sh.getString("getReported", "");
                getTreatment = sh.getString("getTreatment", "");
                getHealed = sh.getString("getHealed", "");
                getSuspicious = sh.getString("getSuspicious", "");
                getDeath = sh.getString("getDeath", "");
                getTime = sh.getString("getTime", "");
                getGlobalTotal = sh.getString("getGlobalTotal", "");
                getGlobalRecovered = sh.getString("getGlobalRecovered", "");
                getGlobalDeath = sh.getString("getGlobalDeath", "");

                reported.setText(getReported);
                treatment.setText(getTreatment);
                healed.setText(getHealed);
                suspicious.setText(getSuspicious);
                death.setText(getDeath);
                updatedTime.setText(getTime);
                globalTotal.setText(getGlobalTotal);
                globalRecovered.setText(getGlobalRecovered);
                globalDeath.setText(getGlobalDeath);*/


                reported.setText(a);
                treatment.setText(b);
                healed.setText(c);
                suspicious.setText(d);
                death.setText(e);
                updatedTime.setText(time);
                globalTotal.setText(gR);
                globalRecovered.setText(gH);
                globalDeath.setText(gD);

                nDialog.dismiss();

                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<People> call, Throwable t) {

            }
        });
    }

    public boolean isNetworkConnected(){
        try {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;

            if (manager!=null){
                networkInfo = manager.getActiveNetworkInfo();
            }
            return  networkInfo !=null && networkInfo.isConnected();
        } catch (NullPointerException e){
            return false;
        }
    }


}
