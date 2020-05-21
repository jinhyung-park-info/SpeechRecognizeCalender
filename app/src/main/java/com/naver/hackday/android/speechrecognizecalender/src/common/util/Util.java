package com.naver.hackday.android.speechrecognizecalender.src.common.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Util {

    private static String getSystemProperty(String name) throws Exception {
        Class systemPropertyClazz = Class.forName("android.os.SystemProperties");
        return (String) systemPropertyClazz.getMethod("get", new Class[]{String.class}).invoke(systemPropertyClazz, new Object[]{name});
    }

    public static boolean isEmulator() throws Exception {
        boolean goldfish = getSystemProperty("ro.hardware").contains("ranchu");
//        boolean emu = getSystemProperty("ro.kernel.qemu").length() > 0;
        boolean sdk = getSystemProperty("ro.product.model").contains("SDK");
        Log.d("isEmulator", getSystemProperty("ro.hardware") + " / " + getSystemProperty("ro.kernel.qemu")+ " / " + getSystemProperty("ro.product.model"));
        return goldfish  || sdk;
    }

    public static void permissionCheck(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

}
