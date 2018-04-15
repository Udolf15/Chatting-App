package com.asifhashmi.whatsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class Chat extends AppCompatActivity {

    Intent intent;
    EditText messageT;
    ListView chatList;
    List<String> messages = new ArrayList<String>();
    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messageT = (EditText) findViewById(R.id.chat);
        intent = getIntent();
        chatList = (ListView) findViewById(R.id.chatList);
        setTitle(intent.getStringExtra("userTapped"));
        Toast.makeText(this, intent.getStringExtra("userTapped"), Toast.LENGTH_SHORT).show();

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, messages);
        chatList.setAdapter(arrayAdapter);

        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();

        ParseQuery<ParseObject> query1 = new ParseQuery<ParseObject>("Message");
        ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("Message");

        query1.whereEqualTo("sender", ParseUser.getCurrentUser().getUsername());
        query1.whereEqualTo("receipt", intent.getStringExtra("userTapped"));
        query2.whereEqualTo("sender", intent.getStringExtra(intent.getStringExtra("userTapped")));
        query2.whereEqualTo("receipt", ParseUser.getCurrentUser().getUsername());

        queries.add(query1);
        queries.add(query2);

        ParseQuery<ParseObject> query = ParseQuery.or(queries);
        query.orderByAscending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseObject chatD : objects) {
                            String message = "";
                            if (!chatD.getString("sender").equals(ParseUser.getCurrentUser().getUsername())) {
                                message = "> : " + chatD.getString("message");
                                messages.add(message);
                            }else{
                                messages.add(chatD.getString("message"));
                            }
                        }
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void sentText(View view) {
        ParseObject sent = new ParseObject("Message");
        final String message=messageT.getText().toString();

        messageT.setText("");

        sent.put("sender", ParseUser.getCurrentUser().getUsername());
        sent.put("receipt", intent.getStringExtra("userTapped"));
        sent.put("message", message);

        sent.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    messages.add(message);
                    arrayAdapter.notifyDataSetChanged();

                    Log.i("Sent", "True");
                } else {
                    Log.i("Sent", "false");
                }
            }
        });
    }


}
