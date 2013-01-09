package cn.kli.queen;

import java.util.List;

import android.app.Activity;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import cn.kli.launcher.TagGridView;
import cn.kli.launcher.TagView;

public class MainActivity extends Activity {
	private final static int TAG_COUNT = 4;
	private final static int MSG_ADD_VIEW = 1; 
	private LinearLayout mContainer;
	private TagGridView mGridView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContainer = (LinearLayout)findViewById(R.id.container);
		mGridView = new TagGridView(this);
		mContainer.addView(mGridView);
		initIcons();
	}
	
	private void initIcons(){
		List<ResolveInfo> appsList = QueenAppsManager.getInstance(this).getQueenApps();
		for(ResolveInfo info : appsList){
			mGridView.addTagView(new AppTagView(this, info));
		}
	}
	

	@Override
	protected void onResume() {
		super.onResume();
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
