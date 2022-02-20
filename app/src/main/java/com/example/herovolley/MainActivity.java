package com.example.herovolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RequestQueue queue;
    ArrayList<Hero> heroes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnJson).setOnClickListener(view -> stringRequest());
        queue = Volley.newRequestQueue(this);
    }

    private void stringRequest() {
        // Instantiate the RequestQueue.

        String url ="https://simplifiedcoding.net/demos/view-flipper/heroes.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest =
                new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                heroes =  new ArrayList<>();
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    Log.d("json", jsonObject.getString("heroes"));
                                    JSONArray jsonArray =  new JSONArray(jsonObject.getString("heroes"));
                                    Log.d("json", jsonArray.toString());

                                    for(int i = 0; i < jsonArray.length(); i++){
                                        JSONObject tmpObject = jsonArray.getJSONObject(i);
                                        Hero tmpHero  = new Hero();
                                        tmpHero.setHeroName(tmpObject.getString("name"));
                                        tmpHero.setImgHero(tmpObject.getString("imageurl"));
                                        heroes.add(tmpHero);
                                        Log.d("json", "name: "+tmpHero.getHeroName()+" url"+ tmpHero.getImgHero());
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //txt.setText("That didn't work!" + error.toString());
                        Log.d("json", "That didn't work! " + error.toString());
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}

