package com.hsw.classinvokeplugin.fix;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.hsw.classinvokeplugin.App;

/**
 * @author: hsw
 * @date: 2022/3/29
 * @desc:
 */
public class TestMethod {

    public static void toast(String message) {
        Toast.makeText(App.getInstance(), "messagexxxxx", Toast.LENGTH_SHORT).show();
    }
    
    public static void alert(Context context, String title, String message) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .create().show();
    }

    public static void snack(View view, String message) {
        long current = System.currentTimeMillis();
    }

    public static class Child {
        public void print() {
            System.out.println("inner class");
        }
    }
}
