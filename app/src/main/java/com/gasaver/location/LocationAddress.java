package com.gasaver.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationAddress {


    private static final String TAG = "LocationAddress";

    public static void getCityFromLocation(final double latitude, final double longitude,
                                           final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> addressList = geocoder.getFromLocation(
                            latitude, longitude, 1);
                    Log.e(TAG, "addressList: "+ new Gson().toJson(addressList));
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);

                        result = address.getLocality();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);


                    Bundle bundle = new Bundle();
                    bundle.putString("address", result);
                    Log.e(TAG, "address: "+ result);
                    message.sendToTarget();
                }
            }
        };

        thread.start();
    }


}
