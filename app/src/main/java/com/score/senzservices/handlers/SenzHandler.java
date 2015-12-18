package com.score.senzservices.handlers;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import com.score.senzservices.R;
//import com.score.senz.db.SenzorsDbSource;
import com.score.senzservices.exceptions.NoUserException;
import com.score.senzservices.listeners.ShareSenzListener;

import com.score.senzservices.utils.NotificationUtils;
import com.score.senzservices.utils.PreferenceUtils;
import com.score.senzservices.utils.SenzParser;
import com.score.senzc.enums.SenzTypeEnum;
import com.score.senzc.pojos.Senz;
import com.score.senzc.pojos.User;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;

/**
 * Handle All senz messages from here
 */
public class SenzHandler {
    private static final String TAG = SenzHandler.class.getName();

    private static Context context;
    private static ShareSenzListener listener;

    private static SenzHandler instance;

    private SenzHandler() {
    }

    public static SenzHandler getInstance(Context context, ShareSenzListener listener) {
        if (instance == null) {
            instance = new SenzHandler();
            SenzHandler.context = context;
            SenzHandler.listener = listener;
        }
        return instance;
    }

    public void handleSenz(String senzMessage) {//ToDo broadcast all senzes
        try {
            // parse and verify senz
            Senz senz = SenzParser.parse(senzMessage);
            verifySenz(senz);
            switch (senz.getSenzType()) {
                case PING:
                    Log.d(TAG, "PING received");
                    break;
                case SHARE:
                    Log.d(TAG, "SHARE received");
                    handleShareSenz(senz);
                    break;
                case GET:
                    Log.d(TAG, "GET received");
                    handleGetSenz(senz);
                    break;
                case DATA:
                    Log.d(TAG, "DATA received");
                    handleDataSenz(senz);
                    break;
                case PUT:
                    Log.d(TAG, "PUT received");
                    handleDataSenz(senz);
                    break;
            }
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
    }

    private static void verifySenz(Senz senz) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        senz.getSender();

        // TODO get public key of sender
        // TODO verify signature of the senz
        //RSAUtils.verifyDigitalSignature(senz.getPayload(), senz.getSignature(), null);
    }

    private void handleShareSenz(Senz senz) {

            Intent newSenzIntent = new Intent("com.score.senzc.NEW_SENZ");
            newSenzIntent.putExtra("SENZ", senz);
            context.sendBroadcast(newSenzIntent);
        }
    private void handleGetSenz(Senz senz) {

        Intent newSenzIntent = new Intent("com.score.senzc.NEW_SENZ");
        newSenzIntent.putExtra("SENZ", senz);
        context.sendBroadcast(newSenzIntent);
    }

    private void handlePutSenz(Senz senz) {

        Intent newSenzIntent = new Intent("com.score.senzc.NEW_SENZ");
        newSenzIntent.putExtra("SENZ", senz);
        context.sendBroadcast(newSenzIntent);
    }

    private void handleDataSenz(Senz senz) {
        // sync data with db data
        ///SenzorsDbSource dbSource = new SenzorsDbSource(context); ToDo db
        ///User sender = dbSource.getOrCreateUser(senz.getSender().getUsername());
        ///senz.setSender(sender);

        // we broadcast data senz
//        Intent intent = new Intent("DATA");
//        intent.putExtra("SENZ", senz);
//        context.sendBroadcast(intent);

        // broadcast received senz
        Intent newSenzIntent = new Intent("com.score.senzc.DATA");
        newSenzIntent.putExtra("SENZ", senz);
        context.sendBroadcast(newSenzIntent);
    }



}
