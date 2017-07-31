package com.popularmovies.ui.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import com.popularmovies.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Andi Insanudin on 15/08/2016.
 */
public class CustomProgressDialog {

    private static final List<ProgressDialog> sProgressDialogList = new ArrayList<>();

    public static void addDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progressdialog);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        sProgressDialogList.add(progressDialog);
    }

    public static void removeDialog(){
        if(sProgressDialogList.size() > 0){
            sProgressDialogList.get(sProgressDialogList.size() - 1).dismiss();
            sProgressDialogList.remove(sProgressDialogList.size() - 1);
        }
    }
}
