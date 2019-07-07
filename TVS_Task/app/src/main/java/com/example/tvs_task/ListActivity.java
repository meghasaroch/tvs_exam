package com.example.tvs_task;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class ListActivity extends AppCompatActivity {


    ArrayList<User> all_users, all_users_org;
    EditText et_search;
    RecyclerView rcv;
    MyRecyclerAdapter myad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        all_users = new ArrayList<>();
        all_users_org = new ArrayList<>();
        et_search = (EditText) (findViewById(R.id.et_search));
        rcv = (RecyclerView) (findViewById(R.id.rcv));

        myad = new MyRecyclerAdapter();

        rcv.setAdapter(myad);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(linearLayoutManager);

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

                Log.d("MYMSG",editable.toString());
                if(editable.toString().equals(""))
                {
                    all_users.clear();
                    all_users.addAll(all_users_org);
                }
                else
                {
                    all_users.clear();
                    for(User u : all_users_org)
                    {
                        if(u.username.contains(editable.toString()))
                        {
                            all_users.add(u);
                        }
                    }

                    Log.d("MYMSG",all_users.size()+" dfdsf "+all_users_org.size());

                }

                myad.notifyDataSetChanged();

            }
        });


        fetchData();

    }


    public void fetchData() {
        String JSON_URL = "http://tvsfit.mytvs.in/reporting/vrm/api/test_new/int/gettabledata.php";

        try
        {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", "test");
            jsonBody.put("password", "123456");
            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                JSONObject js = new JSONObject( jsonObject.get("TABLE_DATA").toString());
                                JSONArray jsa = (JSONArray) js.get("data");

                                for(int i=0;i<jsa.length();i++)
                                {
                                    JSONArray jsonArray = (JSONArray) jsa.get(i);
                                    all_users.add(new User(jsonArray.get(0).toString(),jsonArray.get(1).toString(),jsonArray.get(2).toString(),Integer.parseInt(jsonArray.get(3).toString()),jsonArray.get(4).toString(),jsonArray.get(5).toString()));
                                }

                                all_users_org.addAll(all_users);

                                myad.notifyDataSetChanged();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //displaying the error in toast if occurrs
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        Log.d("MYMSG","error");
                        return null;
                    }
                }


            };

            //creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>
    {

        // Define ur own View Holder (Refers to Single Row)
        class MyViewHolder extends RecyclerView.ViewHolder
        {
            CardView singlecardview;
            // We have Changed View (which represent single row) to CardView in whole code
            public MyViewHolder(CardView itemView) {
                super(itemView);
                singlecardview = (itemView);
            }
        }

        // Inflate ur Single Row / CardView from XML here
        @Override
        public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater  = LayoutInflater.from(parent.getContext());

            View viewthatcontainscardview = inflater.inflate(R.layout.single_row,parent,false);

            CardView cardView = (CardView) (viewthatcontainscardview.findViewById(R.id.cardview3));

            // This will call Constructor of MyViewHolder, which will further copy its reference
            // to customview (instance variable name) to make its usable in all other methods of class
            Log.d("MYMESSAGE","On CreateView Holder Done");
            return new MyViewHolder(cardView);
        }

        @Override
        public void onBindViewHolder(MyRecyclerAdapter.MyViewHolder holder, final int position) {

            CardView localcardview = holder.singlecardview;

            localcardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(getApplicationContext(),DetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user_info",all_users.get(position));
                    in.putExtras(bundle);
                    startActivity(in);
                }
            });
            TextView tv_username,tv_designation,tv_location,tv_id,tv_salary,tv_date;

            tv_username=(TextView)(localcardview.findViewById(R.id.tv_username));
            tv_designation=(TextView)(localcardview.findViewById(R.id.tv_designation));
            tv_location=(TextView)(localcardview.findViewById(R.id.tv_location));
            tv_id=(TextView)(localcardview.findViewById(R.id.tv_id));
            tv_salary=(TextView)(localcardview.findViewById(R.id.tv_salary));
            tv_date=(TextView)(localcardview.findViewById(R.id.tv_date));

            User u=all_users.get(position);

            tv_username.setText(u.username);
            tv_designation.setText(u.designation);
            tv_location.setText(u.location);
            tv_id.setText(u.id+"");
            tv_date.setText(u.date);
            tv_salary.setText(u.salary);

            Log.d("MYMESSAGE","On Bind Of View Holder Called");
        }

        @Override
        public int getItemCount() {

            Log.d("MYMESSAGE","get Item Count Called");

            return all_users.size();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.item1)
        {
            Intent in = new Intent(this,LocationsOnMapActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("values",all_users_org);
            in.putExtras(bundle);
            startActivity(in);
        }
        else if(item.getItemId()==R.id.item2)
        {
            Intent in = new Intent(this,SalariesOnBarGraph.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("values",all_users_org);
            in.putExtras(bundle);
            startActivity(in);

        }

        return true;
    }
}
