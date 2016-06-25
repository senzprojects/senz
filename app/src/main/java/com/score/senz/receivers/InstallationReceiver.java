package com.score.senz.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.score.senz.exceptions.RsaKeyException;
import com.score.senz.utils.RSAUtils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by root on 12/16/15.
 */
public class InstallationReceiver extends BroadcastReceiver {
    private static final String TAG = InstallationReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
            // here i wrote the code of  delete device id in server side

            Log.e(TAG, "DDDDDDDDDDDDDDDDDDDDDDDDD Uninstall receiver Recceived DDDDDDDDDDDDDDDDDDDDDDDDD");


            // sample encryption /decrytption
//            String msg = "eranga";
//            try {
//                PublicKey publicKey = RSAUtils.getPublicKey(context);
//                PrivateKey privateKey = RSAUtils.getPrivateKey(context);
//
//                // encrypt
//                String encryptedMsg = RSAUtils.encryptMessage(msg, publicKey);
//                System.out.println("Encrypted " + encryptedMsg);
//
//                String originalMsg = RSAUtils.decryptMessage(encryptedMsg, privateKey);
//                System.out.println("Decrypted === " + originalMsg);
//            } catch (InvalidKeySpecException e) {
//                e.printStackTrace();
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            } catch (NoSuchProviderException e) {
//                e.printStackTrace();
//            } catch (NoSuchPaddingException e) {
//                e.printStackTrace();
//            } catch (BadPaddingException e) {
//                e.printStackTrace();
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            } catch (IllegalBlockSizeException e) {
//                e.printStackTrace();
//            } catch (RsaKeyException e) {
//                e.printStackTrace();
//            } catch (InvalidKeyException e) {
//                e.printStackTrace();
//            }

        }
    }
}
