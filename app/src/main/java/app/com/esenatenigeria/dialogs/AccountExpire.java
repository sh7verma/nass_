package app.com.esenatenigeria.dialogs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;


import java.io.File;

import app.com.esenatenigeria.R;
import app.com.esenatenigeria.activities.SplashActivity;
import app.com.esenatenigeria.database.Db;
import app.com.esenatenigeria.utils.Utils;

public class AccountExpire extends AppCompatActivity {
    Utils util;
    Db db;
    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        util = new Utils(getApplicationContext());
        db = new Db(getApplicationContext());

        if (getIntent().hasExtra("message"))
            message = getIntent().getExtras().getString("message");

        util.clear_shf();
        AlertDialog.Builder build = new AlertDialog.Builder(AccountExpire.this);
        build.setCancelable(false);
        if (TextUtils.isEmpty(message)) {
            build.setTitle(getApplicationContext().getResources().getString(R.string.account_expired));
            build.setMessage(getApplicationContext().getResources().getString(R.string.someone_logedin_some_other_device));
        } else {
            build.setMessage(getApplicationContext().getResources().getString(R.string.someone_register_some_other_device));
        }
        build.setPositiveButton(getApplicationContext().getResources().getString(R.string.ok),
                new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            clearApplicationData();
                            util.clear_shf();
                            Intent in = new Intent(AccountExpire.this,
                                    SplashActivity.class);
                            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(in);
                            finish();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
        build.show();
    }

    public void clearApplicationData() {
        db.delete_records();
        deleteDatabase(Db.DATABASE);
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.i("TAG", "************** File /data/data/APP_PACKAGE/"
                            + s + " DELETED *****************");
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }
}