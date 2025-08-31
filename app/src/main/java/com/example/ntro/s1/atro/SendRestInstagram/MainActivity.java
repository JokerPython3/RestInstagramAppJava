package com.example.ntro.s1.atro.SendRestInstagram;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText t = findViewById(R.id.editTextTextEmailAddress);
        Button b = findViewById(R.id.button);

        b.setOnClickListener(v -> {
            String s = t.getText().toString();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://atrontros1.pythonanywhere.com/rest?email=" + s)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() ->
                            Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG).show()
                    );
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String res = response.body().string();
                        try {
                            JSONObject a = new JSONObject(res);
                            String sta = a.getString("message");
                            runOnUiThread(() -> {
                                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                                i.putExtra("status", sta);
                                startActivity(i);
                            });
                        } catch (JSONException e) {
                            runOnUiThread(() ->
                                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG).show()
                            );
                        }
                    } else {
                        runOnUiThread(() ->
                                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG).show()
                        );
                    }
                }
            });
        });
    }
}
