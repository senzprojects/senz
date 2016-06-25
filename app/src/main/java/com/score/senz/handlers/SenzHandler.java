package com.score.senz.handlers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.score.senz.listeners.SenzResponseListener;
import com.score.senz.utils.SenzParser;
import com.score.senzc.pojos.Senz;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;

/**
 * Handle All senz messages from here
 */
public class SenzHandler {
    private static final String TAG = SenzHandler.class.getName();

    // context and lister to deal with the service
    private static Context context;
    private static SenzResponseListener listener;

    // implementing singleton
    private static SenzHandler instance;

    // keep senz that needs to be verified
    private static final HashMap<String, Senz> senzTobeVerified = new HashMap<>();

    private SenzHandler() {
    }

    public static SenzHandler getInstance(Context context, SenzResponseListener listener) {
        if (instance == null) {
            instance = new SenzHandler();
            SenzHandler.context = context;
            SenzHandler.listener = listener;
        }

        return instance;
    }

    public void handleSenz(String senzMessage) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        // parse and verify senz
        Senz senz = SenzParser.parse(senzMessage);
        switch (senz.getSenzType()) {
            case PING:
                Log.d(TAG, "PING received");
                break;
            case SHARE:
                Log.d(TAG, "SHARE received");
                verifySenz(senz);
                broadcastSenz(senz, new Intent("com.score.senz.NEW_SENZ"));
                break;
            case GET:
                Log.d(TAG, "GET received");
                verifySenz(senz);
                broadcastSenz(senz, new Intent("com.score.senz.NEW_SENZ"));
                break;
            case DATA:
                Log.d(TAG, "DATA received");
                //verifySenz(senz);
                handleData(senz);
                broadcastSenz(senz, new Intent("com.score.senz.DATA_SENZ"));
                break;
            case PUT:
                Log.d(TAG, "PUT received");
                verifySenz(senz);
                broadcastSenz(senz, new Intent("com.score.senz.NEW_SENZ"));
                break;
        }
    }

    private void verifySenz(Senz senz) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        // TODO check public key of receiver exists

        // add senz as tobe verified senz
        senzTobeVerified.put(senz.getSender().getUsername(), senz);

        // get public key of sender
        listener.onResponse(senz.getSender().getUsername());

        // TOD if key exists, verify signature of the senz
        //RSAUtils.verifyDigitalSignature(senz.getId(), senz.getSignature(), null);
    }

    private void handleData(Senz senz) {
        // handle DATA with pubKey
        if (senz.getAttributes().containsKey("#pubKey")) {
            // received public key of a senzie
            // extract pubKey and save it

            // verify corresponding senz with received public key
        } else {
            // received application specific data
            // broadcast it
            broadcastSenz(senz, new Intent("com.score.senz.DATA_SENZ"));
        }
    }

    private void broadcastSenz(Senz senz, Intent intent) {
        intent.putExtra("SENZ", senz);
        context.sendBroadcast(intent);
    }

}
