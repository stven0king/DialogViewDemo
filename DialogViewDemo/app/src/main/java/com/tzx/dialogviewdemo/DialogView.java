package com.tzx.dialogviewdemo;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by stven0king on 2015/9/9.
 */
public class DialogView implements View.OnClickListener{

    private Context context;
    /**
     * 内部的Dialog
     */
    private AlertDialog alertDialog;
    /**
     * 要展示的选项
     */
    private String[] title;
    /**
     * 整个界面的布局
     */
    private LinearLayout contentView;
    /**
     * 整个界面的布局参数
     */
    private LinearLayout.LayoutParams params;
    /**
     * 显示屏幕的宽、高
     */
    private int width;
    private int height;
    /**
     * 取消按钮
     */
    private TextView cancelTextView;
    /**
     * 是否显示最底部的取消按钮
     */
    private boolean isShowCancel;
    /**
     * 每一个显示的item布局参数
     */
    private LinearLayout.LayoutParams Itemparams;
    /**
     * 每个一item的高度
     */
    private int Itemheight;
    /**
     * 每一个item底部的外边距
     */
    private int ItemBottomheight;
    /**
     * 每一个Item的顶部外边距
     */
    private int ItemTopheight;
    /**
     * 取消按钮顶部的外边距
     */
    private int CancelTopheight;
    /**
     * 取消按钮底部的外边距
     */
    private int CancelBottomheight;
    private DialogViewTounchEventListener dialogViewTounchEventListener;
    private String cancelName;
    public DialogView(Context context) {
        this.context = context;
        initValue();
        intiViewParams();
    }

    private void initValue() {
        isShowCancel = true;
        Itemheight = 40;
        ItemBottomheight = 5;
        ItemTopheight = 0;
        CancelTopheight = 12;
        CancelBottomheight = 5;
        cancelName = "取消";
    }

    private void intiViewParams() {
        alertDialog = new AlertDialog.Builder(context).create();
        title = new String[]{};
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        width = d.widthPixels;
        height = d.heightPixels;
        params = new LinearLayout.LayoutParams(width, width);
        Itemparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,DensityUtil.dip2px(context,Itemheight));
        contentView = new LinearLayout(context);
        contentView.setLayoutParams(params);
        contentView.setOrientation(LinearLayout.VERTICAL);
        initCancelItem();
    }


    public interface DialogViewTounchEventListener {
        /**
         * 监听取消按钮事件
         */
        void onCancelClick();

        /**
         * 监听点击列表数据事件
         * @param postion
         */
        void onDialogItemClick(int postion);
    }

    /**
     * 初始化取消按钮
     */
    public void initCancelItem() {
        cancelTextView = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,DensityUtil.dip2px(context,Itemheight));
        layoutParams.topMargin = DensityUtil.dip2px(context,CancelTopheight);
        layoutParams.bottomMargin = DensityUtil.dip2px(context,CancelBottomheight);
        cancelTextView.setText(cancelName + "");
        cancelTextView.setGravity(Gravity.CENTER);
        cancelTextView.setBackgroundResource(R.drawable.dialog_view_item);
        cancelTextView.setLayoutParams(layoutParams);
    }

    public void setDialogViewTounchEventListener(DialogViewTounchEventListener dialogViewTounchEventListener) {
        this.dialogViewTounchEventListener = dialogViewTounchEventListener;
    }

    public void setCancelBottomheight(int cancelBottomheight) {
        CancelBottomheight = cancelBottomheight;
    }

    public void setCancelTopheight(int cancelTopheight) {
        CancelTopheight = cancelTopheight;
    }

    public void setItemTopheight(int itemTopheight) {
        ItemTopheight = itemTopheight;
    }

    public void setItemBottomheight(int itemBottomheight) {
        ItemBottomheight = itemBottomheight;
    }

    public void setIsShowCancel(boolean isShowCancel) {
        this.isShowCancel = isShowCancel;
    }

    public void setCancelName(String cancelName) {
        this.cancelName = cancelName;
    }

    public void setItemheight(int itemheight) {
        Itemheight = itemheight;
    }

    /**
     * 初始化数据
     * @param title
     * @return
     */
    public DialogView addItems(String[] title) {
        this.title = title;
        int size = title.length;
        for(int i = 0; i < size; i++) {
            TextView textView = new TextView(context);
            textView.setId(i);
            textView.setOnClickListener(this);
            Itemparams.bottomMargin = DensityUtil.dip2px(context,ItemBottomheight);
            Itemparams.topMargin = DensityUtil.dip2px(context,ItemTopheight);
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(Itemparams);
            textView.setText(title[i]);
            textView.setBackgroundResource(R.drawable.dialog_view_item);
            contentView.addView(textView);
        }
        if (isShowCancel) {
            cancelTextView.setOnClickListener(new CancelOnClickListener());
            contentView.addView(cancelTextView);
        }
        return this;
    }

    public class CancelOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            alertDialog.dismiss();
            if (dialogViewTounchEventListener != null) {
                dialogViewTounchEventListener.onCancelClick();
            }
        }
    }

    public void show() {
        alertDialog.show();
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.y = height;
        lp.x = 0;
        window.setAttributes(lp);
        window.setContentView(contentView);
    }

    @Override
    public void onClick(View view) {
        int postion = view.getId();
        alertDialog.dismiss();
        if (dialogViewTounchEventListener != null) {
            dialogViewTounchEventListener.onDialogItemClick(postion);
        }

    }

    /**
     * 点击外边区域是否取消Dialog
     * @param cancel
     */
    public void setCanceledOnTouchOutside(boolean cancel){
        alertDialog.setCanceledOnTouchOutside(cancel);
    }
}
