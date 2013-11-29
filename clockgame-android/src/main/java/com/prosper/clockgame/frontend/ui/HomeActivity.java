package com.prosper.clockgame.frontend.ui;

import com.actionbarsherlock.app.SherlockActivity;
import com.prosper.clockgame.frontend.R;
import com.prosper.clockgame.frontend.common.DefaultHandler;
import com.prosper.clockgame.frontend.common.DefaultResponse;
import com.prosper.clockgame.frontend.restful.UserRestClient;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class HomeActivity extends SherlockActivity {
	
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
    }
    

    /**
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    */
    
    public void login(View view) {
    	final Intent intent = new Intent(this, DisplayMessageActivity.class);
    	String email = ((EditText) findViewById(R.id.edit_email)).getText().toString();
    	String password = ((EditText) findViewById(R.id.edit_password)).getText().toString();
    	
    	try {
			UserRestClient.login(email, password, new DefaultHandler() {
				@Override
				public void doMessage (DefaultResponse response) {
					AlertDialog.Builder builder = new Builder(HomeActivity.this); 
					builder.setPositiveButton("确定", new OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							intent.putExtra(EXTRA_MESSAGE, "登陆成功");
					    	startActivity(intent);
						}
					}); 
					builder.setIcon(android.R.drawable.ic_dialog_info); 
					if (response.getOpCode() == 0) {
						builder.setMessage("登陆成功"); 
						builder.show();
					} else {
						builder.setMessage("登陆失败");
						builder.show();
					}
		        }  
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void register(View view) {
    	String email = ((EditText) findViewById(R.id.edit_email)).getText().toString();
    	String password = ((EditText) findViewById(R.id.edit_password)).getText().toString();
    	
    	try {
			UserRestClient.register(email, password, new DefaultHandler() {
				@Override
				public void doMessage (DefaultResponse response) {
					AlertDialog.Builder builder = new Builder(HomeActivity.this); 
					builder.setPositiveButton("确定",null); 
					builder.setIcon(android.R.drawable.ic_dialog_info); 
					if (response.getOpCode() == 0) {
						builder.setMessage("注册成功"); 
					} else if(response.getOpCode() == 102) {
						builder.setMessage("数据已存在"); 
					} else {
						builder.setMessage("注册失败");
					}
					builder.show();
		        }  
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
}
