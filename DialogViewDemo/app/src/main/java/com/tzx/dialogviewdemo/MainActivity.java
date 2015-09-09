package com.tzx.dialogviewdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements DialogView.DialogViewTounchEventListener {
    private Button btn;
    private DialogView dialogView;
    private String[] title = new String[]{"拍照", "相册"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        dialogView = new DialogView(this);
        dialogView.addItems(title);
        dialogView.setDialogViewTounchEventListener(this);
        dialogView.setCanceledOnTouchOutside(false);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.show();
            }
        });
    }

    @Override
    public void onCancelClick() {
        Toast.makeText(this,"back",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogItemClick(int postion) {
        Toast.makeText(this,"" + title[postion],Toast.LENGTH_SHORT).show();
    }
}
