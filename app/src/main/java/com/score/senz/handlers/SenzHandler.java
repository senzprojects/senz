package com.score.senz.handlers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.score.senz.listeners.SenzResponseListener;
import com.score.senz.utils.PreferenceUtils;
import com.score.senz.utils.RSAUtils;
import com.score.senz.utils.SenzParser;
import com.score.senzc.pojos.Senz;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
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
    private static final HashMap<String, Senz> senzesTobeVerified = new HashMap<>();

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
                verifySenz(senzMessage, senz);
                broadcastSenz(senz, new Intent("com.score.senz.NEW_SENZ"));
                break;
            case GET:
                Log.d(TAG, "GET received");
                verifySenz(senzMessage, senz);
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
                verifySenz(senzMessage, senz);
                broadcastSenz(senz, new Intent("com.score.senz.NEW_SENZ"));
                break;
        }
    }

    private void verifySenz(String senzMessage, Senz senz) {
        // check public key of sender exists
        try {
            PublicKey publicKey = RSAUtils.getPublicKey(context, senz.getSender().getUsername());

            Log.d(TAG, "Key exists with username " + senz.getSender().getUsername());

            // verify signature
            String signedPayload = senzMessage.substring(0, senzMessage.lastIndexOf(" ")).trim();
            RSAUtils.verifyDigitalSignature(signedPayload, senz.getSignature(), publicKey);

            Log.d(TAG, "Signature verified ");
        } catch (InvalidKeySpecException | NoSuchProviderException | NoSuchAlgorithmException e) {
            // no key
            e.printStackTrace();

            // add senz as tobe verified senz
            senzesTobeVerified.put(senz.getSender().getUsername(), senz);

            // get public key of sender
            listener.onResponse(senz.getSender().getUsername());
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    private void handleData(Senz senz) {
        // handle DATA with pubkey
        if (senz.getAttributes().containsKey("pubkey")) {
            // received public key of a senzie
            String publicKey = senz.getAttributes().get("pubkey");
            String username = senz.getAttributes().get("name");

            Log.d(TAG, "Received key" + publicKey + " of " + username);

            // save public key
            PreferenceUtils.saveRsaKey(context, publicKey, username);

            // verify corresponding senz with received public key
            Senz senzTobeVerified = senzesTobeVerified.remove(senz.getSender().getUsername());
            //RSAUtils.verifyDigitalSignature(senz.getId(), senz.getSignature(), publicKey);
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
