package com.abdulaziz.ezz.myaccounts.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import com.abdulaziz.ezz.myaccounts.R;

public class SplashActivity extends AppCompatActivity
{

	private static int SPLASH_TIME_OUT = 3000;
	private String adminPin;
	private ImageView mIVLogo;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

//		mIVLogo = findViewById(R.id.ivLogo);
//		Animation animation = AnimationUtils.loadAnimation(this,R.anim.logo_anim	);
//		mIVLogo.startAnimation(animation);

//		adminPin = CommonUtils.getStringPref(SplashActivity.this, SP_DI_ADMIN_PIN, null);

		new android.os.Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if ((adminPin == "" || TextUtils.isEmpty(adminPin)))
				{
					Intent intent = new Intent(SplashActivity.this, PinRequiredActivity.class);
					startActivity(intent);
					finish();
				}
				else
				{
					Intent intent = new Intent(SplashActivity.this, PinActivity.class);
					startActivity(intent);
					finish();
				}
			}
		}, SPLASH_TIME_OUT);
	}
}
