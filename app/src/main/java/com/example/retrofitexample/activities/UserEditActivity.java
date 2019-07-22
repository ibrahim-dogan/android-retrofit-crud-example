package com.example.retrofitexample.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retrofitexample.R;
import com.example.retrofitexample.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserEditActivity extends AppCompatActivity {

    EditText name;
    Button edit, delete;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);
        initialize();
        onClickListeners();
    }

    private void onClickListeners() {
        edit = findViewById(R.id.edit);
        delete = findViewById(R.id.delete);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = name.getText().toString();
                MainActivity.users.get(position).setName(newName);

                Call<User> userCall = MainActivity.api.setUserById(MainActivity.users.get(position).getId(), MainActivity.users.get(position));
                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Toast.makeText(getApplicationContext(), "onResponse", Toast.LENGTH_SHORT);
                        MainActivity.listViewAdapter.notifyDataSetChanged();
                        UserEditActivity.this.finish();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<User> call = MainActivity.api.deleteUserById(MainActivity.users.get(position).getId());
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        MainActivity.users.remove(position);
                        Toast.makeText(getApplicationContext(), "onResponse", Toast.LENGTH_SHORT);
                        MainActivity.listViewAdapter.notifyDataSetChanged();
                        UserEditActivity.this.finish();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
        });


    }

    private void initialize() {
        name = findViewById(R.id.user_name_edit);

        Intent i = getIntent();
        position = i.getIntExtra("position", 0);

        name.setText(MainActivity.users.get(position).getName());
    }
}
