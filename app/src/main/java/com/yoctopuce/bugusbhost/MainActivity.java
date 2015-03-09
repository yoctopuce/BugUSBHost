package com.yoctopuce.bugusbhost;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.usb.UsbConfiguration;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;


public class MainActivity extends ActionBarActivity
{

    private static final String TAG = "USB_HOST_BUG";
    private TextView _textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _textView = (TextView) findViewById(R.id.textView);
        doEnum(null);
    }


    void log(String line)
    {
        Log.i(TAG, line);
        _textView.append(line + "\n");
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void doEnum(View view)
    {
        _textView.setText("");
        UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        if (usbManager == null) {
            log("Unable to access to USB manager");
            return;
        }

        HashMap<String, UsbDevice> connectedDevices = usbManager.getDeviceList();
        if (connectedDevices == null || connectedDevices.size()==0) {
            log("No USB devices detected");
            return;
        }
        for (UsbDevice usbdevice : connectedDevices.values()) {
            int deviceId = usbdevice.getProductId();
            int vendorId = usbdevice.getVendorId();
            int interfaceCount = usbdevice.getInterfaceCount();
            int configurationCount = 0;
            String tmp_serial = "unknown";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tmp_serial = usbdevice.getSerialNumber();
                configurationCount = usbdevice.getConfigurationCount();
            }
            log("====================================");
            log(String.format("Detected %x:%x device (serial=%s)", vendorId, deviceId, tmp_serial));
            if (interfaceCount < 1) {
                log(String.format("drop device because it has no interfaces %x:%x:%s %d interfaces", vendorId, deviceId, tmp_serial, interfaceCount));
            }
            String line = String.format("%d configuration and %d interface reported by UsbDevice object", configurationCount, interfaceCount);
            if (interfaceCount < 1) {
                line = "!!!! " + line +" !!!!";
            }
            log(line);
            for (int j = 0; j < configurationCount; j++) {
                UsbConfiguration configuration = usbdevice.getConfiguration(j);
                int config_interfaces = configuration.getInterfaceCount();
                log(String.format("   configuration %d report %d interfaces", j, config_interfaces));
            }


        }

    }
}
