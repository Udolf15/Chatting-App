package com.asifhashmi.whatsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class userlist extends AppCompatActivity {

    List<String > users;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);

        ListView listView=(ListView)findViewById(R.id.listView);

        users=new ArrayList<String>();

        users.clear();

        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,users);

        listView.setAdapter(arrayAdapter);

        ParseQuery<ParseUser> query=ParseUser.getQuery();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),Chat.class);
                intent.putExtra("userTapped",users.get(i));
                startActivity(intent);
            }
        });

        query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null) {
                    if(objects.size()>0) {
                        for (ParseUser user : objects) {
                            users.add(user.getUsername());
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                }else{
                    String message = e.getMessage();
                    if (message.toLowerCase().contains("java")) {
                        Toast.makeText(userlist.this, e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(userlist.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}
