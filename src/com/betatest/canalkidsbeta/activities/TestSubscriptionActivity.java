package com.betatest.canalkidsbeta.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.betatest.canalkidsbeta.R;
import com.betatest.canalkidsbeta.billing.util.IabHelper;
import com.betatest.canalkidsbeta.billing.util.IabResult;
import com.betatest.canalkidsbeta.billing.util.Inventory;
import com.betatest.canalkidsbeta.billing.util.Purchase;

public class TestSubscriptionActivity extends Activity{

    // Debug tag, for logging
    static final String TAG = "CanalKidsBeta";

    // Does the user have an active subscription to the infinite gas plan?
    boolean mSubscribedToChannels = false;

    // SKU for our subscription (infinite gas)
    static final String SKU_SUBSCHANNEL = "test_purchase_canal_kids_beta";

    // (arbitrary) request code for the purchase flow
    static final int RC_REQUEST = 10001;

    // The helper object
    IabHelper mHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* base64EncodedPublicKey should be YOUR APPLICATION'S PUBLIC KEY
         * (that you got from the Google Play developer console). This is not your
         * developer public key, it's the *app-specific* public key.
         *
         * Instead of just storing the entire literal string here embedded in the
         * program,  construct the key at runtime from pieces or
         * use bit manipulation (for example, XOR with some other string) to hide
         * the actual key.  The key itself is not secret information, but we don't
         * want to make it easy for an attacker to replace the public key with one
         * of their own and then fake messages from the server.
         */
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoi+pXV8KwvdfzDiHuOGRtvpc2RTyvR3dAR1u/2F1cyY7B6hFvWMWtpYAcNUtK9Ehz9cooXNnykOkhiSYzq9KEwq+I1zI1Nac1fnOPkPpWg1rUYXtwZSiKhQxoaIlMwv8BRYUCXLPSu5TjT2h3UyXSecKrdRdalfcwl6HWQh2oI3FRpd60aBfoRigIbe4KVGQ0id8O+WSWqMtBTXjN3/fR+Q1/KJuUqssPjrGgsuuLVYKSnvA5xTV1gwLyZcplHtZzvTy9nfTXg8SFFxIGCf2LO0q6kRdCH9WrUUdxuapuFWKnSdQy2IU4zsVUS8882tryEkC/R/tghRPqIWKhuzgjwIDAQAB";

        // Create the helper, passing it our context and the public key to verify signatures with
        Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(this, base64EncodedPublicKey);
        
        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(true);

        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    complain("Problem setting up in-app billing: " + result);
                    return;
                }

                // Hooray, IAB is fully set up. Now, let's get an inventory of stuff we own.
                Log.d(TAG, "Setup successful. Querying inventory.");
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
    }

    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");
            if (result.isFailure()) {
                complain("Failed to query inventory: " + result);
                return;
            }

            Log.d(TAG, "Query inventory was successful.");
            
            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */

            // Do we have the infinite gas plan?
            Purchase channelPurchase = inventory.getPurchase(SKU_SUBSCHANNEL);
            mSubscribedToChannels = (channelPurchase!= null && 
                    verifyDeveloperPayload(channelPurchase));
            Log.d(TAG, "User " + (mSubscribedToChannels ? "HAS" : "DOES NOT HAVE") 
                        + " infinite gas subscription.");
            if (mSubscribedToChannels) 
            	//TODO ALLOW ALL VIDEOS

            Log.d(TAG, "Initial inventory query finished; enabling main UI.");
        }
    };

       
    // "Subscribe to infinite gas" button clicked. Explain to user, then start purchase
    // flow for subscription.
    public void onChannelSubscribeButtonClicked(View arg0) {
        if (!mHelper.subscriptionsSupported()) {
            complain("Subscriptions not supported on your device yet. Sorry!");
            return;
        }
        
        /* TODO: for security, generate your payload here for verification. See the comments on 
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use 
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = ""; 
        Log.d(TAG, "Launching purchase flow for infinite gas subscription.");
        mHelper.launchPurchaseFlow(this,
                SKU_SUBSCHANNEL, IabHelper.ITEM_TYPE_SUBS, 
                RC_REQUEST, mPurchaseFinishedListener, payload);        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }
    
    /** Verifies the developer payload of a purchase. */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        
        /*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         * 
         * WARNING: Locally generating a random string when starting a purchase and 
         * verifying it here might seem like a good approach, but this will fail in the 
         * case where the user purchases an item on one device and then uses your app on 
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         * 
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         * 
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on 
         *    one device work on other devices owned by the user).
         * 
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */
        
        return true;
    }

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);
            if (result.isFailure()) {
                complain("Error purchasing: " + result);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                complain("Error purchasing. Authenticity verification failed.");
                return;
            }

            Log.d(TAG, "Purchase successful.");

            if (purchase.getSku().equals(SKU_SUBSCHANNEL)) {
                // bought the infinite gas subscription
                Log.d(TAG, "Infinite gas subscription purchased.");
                alert("Thank you for subscribing to infinite gas!");
                mSubscribedToChannels = true;
            }
        }
    };
    
    // We're being destroyed. It's important to dispose of the helper here!
    @Override
    public void onDestroy() {
        super.onDestroy();
        
        // very important:
        Log.d(TAG, "Destroying helper.");
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }

    void complain(String message) {
        Log.e(TAG, "**** TrivialDrive Error: " + message);
        alert("Error: " + message);
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

}