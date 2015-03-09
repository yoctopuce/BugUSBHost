# BugUSBHost
Expose a bug in Android 5.0 in USB host support.

DESCRIPTION OF THE BUG:
=======================
In Android 5.0 (Lollipop) only the last plugged USB device is reported correctly. The getDeviceList() method of 
the UsbManager return all plugged devices but only one device has the right number of interface. If you call the 
method getInterfaceCount() on all detected devices only the last plugged device has the right correct number of 
interfaces, other devices report 0 interfaces. If you call the getInterfaceCount() on the UsbConfiguration object,
the same problem occur only the last plugged device as the right number of interfaces.


STEP TO REPODUCE:
=================
Plug two devices behind a USB hub and run the application. This bug has been reproduced on a Nexus 7 (2012) 
and a Nexus 5 that both run the latest official Lollipop image. The same Nexus 7 tablet was working fine with 
Android 4.4 before the update. Other tested Android devices that run Android 4.0, 4.1, 4.2, and 4.4 enumerate
correctly all devices. 


If you have any question please contact support@yoctopuce.com
