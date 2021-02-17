package com.reactnativepagoapp;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import java.util.HashMap;
import java.util.Map;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import lib.visanet.com.pe.visanetlib.VisaNet;
import lib.visanet.com.pe.visanetlib.data.custom.Channel;
import lib.visanet.com.pe.visanetlib.presentation.custom.VisaNetViewAuthorizationCustom;

public class NiubizModule extends ReactContextBaseJavaModule  {

    private Promise resultPromise;

    public NiubizModule(ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addActivityEventListener(activityEventListener);
    }

    @NonNull
    @Override
    public String getName() {
        return "NiubizModule";
    }

    @ReactMethod
    public void payWithNiubiz(final String token, final String amount, final String purchase, final Promise promise) {

        Map<String, Object> data = new HashMap<>();
        data.put(VisaNet.VISANET_CHANNEL, Channel.MOBILE);
        data.put(VisaNet.VISANET_COUNTABLE, true);
        data.put(VisaNet.VISANET_SECURITY_TOKEN, token);
        data.put(VisaNet.VISANET_MERCHANT, "522591303");
        data.put(VisaNet.VISANET_PURCHASE_NUMBER, purchase);
        data.put(VisaNet.VISANET_AMOUNT, Double.parseDouble(amount));
        data.put(VisaNet.VISANET_REGISTER_NAME, "KEVIN");
        data.put(VisaNet.VISANET_REGISTER_LASTNAME, "SANDON");
        data.put(VisaNet.VISANET_REGISTER_EMAIL, "integraciones.niubiz@necomplus.com");

        // Registro de MDD's. Consultar con integraciones.niubiz@necomplus.com
        HashMap<String, String> MDDdata = new HashMap<String, String>();
        MDDdata.put("19", "LIM");
        MDDdata.put("20", "AQP");
        MDDdata.put("21", "AFKI345");
        MDDdata.put("94", "ABC123DEF");
        data.put(VisaNet.VISANET_MDD, MDDdata);

        data.put(VisaNet.VISANET_ENDPOINT_URL, "https://apitestenv.vnforapps.com/");
        data.put(VisaNet.VISANET_CERTIFICATE_HOST, "apitestenv.vnforapps.com");
        data.put(VisaNet.VISANET_CERTIFICATE_PIN, "sha256/w+9oxEkAQVM2aZGzmUYiTP2L2VA0JnxqIvH2e/HPhV0=");

        VisaNetViewAuthorizationCustom custom = new VisaNetViewAuthorizationCustom();

        resultPromise = promise;

        try {
            VisaNet.authorization(getCurrentActivity(), data, custom);
        }
        catch (Exception e) {
            Log.e("NIUBIZ: ", e.getMessage());
            Toast.makeText(getReactApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private final ActivityEventListener activityEventListener = new ActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
            if (requestCode == VisaNet.VISANET_AUTHORIZATION) {
                if (data != null) {
                    if (resultCode == Activity.RESULT_OK) {
                        String response = data.getExtras().getString("keySuccess");
                        Toast.makeText(getReactApplicationContext(), "EXITO", Toast.LENGTH_SHORT).show();
                        resultPromise.resolve(response);
                    } else {
                        String response = data.getExtras().getString("keyError");
                        Toast.makeText(getReactApplicationContext(), "DENEGADO", Toast.LENGTH_SHORT).show();
                        resultPromise.resolve(response);
                    }
                }
            }
        }

        @Override
        public void onNewIntent(Intent intent) {

        }
    };

}


