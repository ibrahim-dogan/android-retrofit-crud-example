package com.example.retrofitexample.services;

import com.example.retrofitexample.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("user/")
    Call<List<User>> getAllUsers();

    @PUT("/user/{id}/")
    Call<User> setUserById(@Path("id") int id, @Body User user);

    @DELETE("/user/{id}/")
    Call<User> deleteUserById(@Path("id") int id);

    @POST("/user/")
    Call<User> deleteUserById();
}
