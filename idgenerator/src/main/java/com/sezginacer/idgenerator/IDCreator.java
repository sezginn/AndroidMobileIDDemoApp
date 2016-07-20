package com.sezginacer.idgenerator;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.binary.Hex;

/**
 * Created by Sezgin on 11.07.2016
 */

public class IDCreator {
    private Context context = null;  // activity

    public IDCreator(Context context){
        this.context = context;  // activity given as parameter to the constructor
    }

    public String getMacAddress(){
        /*
        This function always returns constant 02.00.00.00.00.00 if Android Release >= 6.0
        Because from that version on, Android gives no access to WiFi Mac Address
         */
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return manager.getConnectionInfo().getMacAddress();
    }

    public String getBluetoothHWaddr(){
        return android.provider.Settings.Secure.getString(context.getContentResolver(), "bluetooth_address");
    }

    public String getIMEI(){
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getDeviceId();
    }

    public String getSimSerial(){
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getSimSerialNumber();
    }

    public String getAndroidVersion(){
        return Build.VERSION.RELEASE;
    }

    public String getAndroidID(){
        return android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
    }

    public String getAndroidMobileID(){
        String ret = getAndroidID();
        ret += getAndroidVersion();
        ret += getSimSerial();
        ret += getIMEI();
        ret += getBluetoothHWaddr();
        ret += getMacAddress();
        return new String(Hex.encodeHex(DigestUtils.md5(ret)));
    }
}
