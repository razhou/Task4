package com.example.ghost.task4;

import com.example.ghost.task4.models.SynTransaction;
import com.example.ghost.task4.models.SynTransactions;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by ghost on 29/03/2016.
 */
public interface TransApi {
    @GET("/task4")
    Call<SynTransactions> getTransactions();


    @GET("/task4")

    Call<SynTransactions> getTransactions(@Path("id") String transaction_id);


    @PUT("/task4")

    Call<SynTransactions> updateTransactions(@Path("id") int transaction_id, @Body SynTransaction transaction);


    @POST("/task4")

    Call<SynTransaction> synTransaction(@Body SynTransaction transaction);

}
