package com.asifhashmi.whatsapp;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("dd7c4c4a797ca95fde838ce6bdc46a1b1ba74532")
                .clientKey("ec156b0ca99eb6b213fe739477e481c9649b3e4e")
                .server("http://52.15.169.150:80/parse/")
                .build()
        );

//        ParseObject object = new ParseObject("ExampleObject");
//        object.put("myNumber", "123");
//        object.put("myString", "rob");
//
//        object.saveInBackground(new SaveCallback () {
//            @Override
//            public void done(ParseException ex) {
//                if (ex == null) {
//                    Log.i("Parse Result", "Successful!");
//                } else {
//                    Log.i("Parse Result", "Failed" + ex.toString());
//                }
//            }
//        });


    //    ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}
