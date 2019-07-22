package com.example.retrofitexample.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.retrofitexample.R;
import com.example.retrofitexample.adapters.CustomListViewAdapter;
import com.example.retrofitexample.model.User;
import com.example.retrofitexample.network.RetrofitClientInstance;
import com.example.retrofitexample.services.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    public static ApiInterface api;

    public static ArrayList<User> users;
    private ListView listView;
    public static CustomListViewAdapter listViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        fillArrayList();


    }

    private void initialize() {
        users = new ArrayList<>();
        listView = findViewById(R.id.user_list_view);
        listViewAdapter = new CustomListViewAdapter(MainActivity.this, users);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent i = new Intent(MainActivity.this, UserEditActivity.class);
                i.putExtra("position", position);
                startActivity(i);
            }
        });
    }

    private void fillArrayList() {

        api = RetrofitClientInstance.getClient().create(ApiInterface.class);
        Call<List<User>> listCall = api.getAllUsers();

        listCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> userList;
                userList = response.body();


                for (User u : userList) {
                    users.add(u);
                    System.out.println(u.toString());
                }

                listViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

    }


}
