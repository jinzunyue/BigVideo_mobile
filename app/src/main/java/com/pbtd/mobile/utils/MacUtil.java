package com.pbtd.mobile.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MacUtil {

	@SuppressLint("NewApi") public static String getLocalEthernetMacAddress() {
        String mac=null;
        try {  
            Enumeration localEnumeration = NetworkInterface.getNetworkInterfaces();
  
            while (localEnumeration.hasMoreElements()) {  
                NetworkInterface localNetworkInterface=(NetworkInterface) localEnumeration.nextElement();
                String interfaceName=localNetworkInterface.getDisplayName();
  
                if (interfaceName==null) {  
                    continue;  
                }  
  
                if (interfaceName.equals("eth0")) {  
                    // MACAddr = convertMac(localNetworkInterface  
                    // .getHardwareAddress());  
                    mac = convertToMac(localNetworkInterface.getHardwareAddress()); 
//                    mac = "00:24:68:cb:10:1d";
                    if (mac!=null&&mac.startsWith("0:")) {  
                        mac="0"+mac;  
                    }  
                    break;  
                }  
  
                // byte[] address =  
                // localNetworkInterface.getHardwareAddress();  
                // Log.i(TAG, "mac=" + address.toString());  
                // for (int i = 0; (address != null && i < address.length);  
                // i++)  
                // {  
                // Log.i("Debug", String.format("  : %x", address[i]));  
                // }  
            }  
        } catch (SocketException e) {
            e.printStackTrace();  
        }  
        return mac;  
    }  
  
    private static String convertToMac(byte[] mac) {
        StringBuilder sb=new StringBuilder();
        for (int i=0; i<mac.length; i++) {  
            byte b=mac[i];  
            int value=0;  
            if (b>=0&&b<16) {  
                value=b;  
                sb.append("0"+ Integer.toHexString(value));
            } else if (b>=16) {  
                value=b;  
                sb.append(Integer.toHexString(value));
            } else {  
                value=256+b;  
                sb.append(Integer.toHexString(value));
            }  
            if (i!=mac.length-1) {  
                sb.append(":");  
            }  
        }  
        return sb.toString();  
    }  
    
    public static String getMacAddress(Context context) {
        String macAddress_save = SharedPreferencesUtil.getMacAddress(context);
        if (!TextUtils.isEmpty(macAddress_save)) return macAddress_save;

        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String macAddress = info.getMacAddress();
        if (!TextUtils.isEmpty(macAddress))
            SharedPreferencesUtil.saveMacAddress(context, macAddress);
        return macAddress;
    }
}
