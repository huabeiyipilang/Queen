package cn.kli.queen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import cn.kli.launcher.TagGridView;
import cn.kli.queen.updater.CheckUpdateActivity;

public class MainActivity extends Activity implements OnClickListener{
	private final static int TAG_COUNT = 4;
	private final static int MSG_ADD_VIEW = 1; 
	private LinearLayout mContainer;
	private TagGridView mGridView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		QueenAppsManager.removeChildrenIcon(this);
		mContainer = (LinearLayout)findViewById(R.id.container);
		mGridView = new TagGridView(this);
		mContainer.addView(mGridView);
		findViewById(R.id.updater).setOnClickListener(this);
	}
	
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	*/
	
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.updater:
			startActivity(new Intent(this, CheckUpdateActivity.class));
			break;
		}
	}

}
