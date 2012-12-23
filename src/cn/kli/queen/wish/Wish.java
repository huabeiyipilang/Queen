package cn.kli.queen.wish;

import java.text.SimpleDateFormat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import cn.kli.queen.DbHelper;
import cn.kli.queen.Global;
import cn.kli.queen.R;

public class Wish extends Activity implements OnClickListener {
	private ListView mWishList;
	private ViewGroup mWishListScreen, mAddWishScreen;
	private EditText mWishEdit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.wish_activity);
	    mWishList = (ListView)findViewById(R.id.wish_list);
	    mWishListScreen = (ViewGroup)findViewById(R.id.wish_list_screen);
	    mAddWishScreen = (ViewGroup)findViewById(R.id.add_wish_screen);
	    freshList();
	    mWishEdit = (EditText)findViewById(R.id.wish_edit_text);
	    
	    findViewById(R.id.wish_add).setOnClickListener(this);
	    findViewById(R.id.wish_commit).setOnClickListener(this);
	}
	
	private void freshList(){
	    Cursor cursor = Global.getInstance(this).getWish();
	    WishAdapter adapter = new WishAdapter(this, cursor);
		mWishList.setAdapter(adapter);
	}

	private class WishAdapter extends CursorAdapter{
		LayoutInflater inflater;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		public WishAdapter(Context context, Cursor c) {
			super(context, c);
			inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return inflater.inflate(R.layout.wish_item, null);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			TextView content = (TextView)view.findViewById(R.id.wish_content);
			String content_text = cursor.getString(cursor.getColumnIndex(DbHelper.WISH_CONTENT));
			content.setText(content_text);
		}
	}

    private Interpolator accelerator = new AccelerateInterpolator();
    private Interpolator decelerator = new DecelerateInterpolator();
    private void flipit() {
        final ViewGroup visibleList;
        final ViewGroup invisibleList;
        if (mWishListScreen.getVisibility() == View.GONE) {
            visibleList = mAddWishScreen;
            invisibleList = mWishListScreen;
        } else {
            invisibleList = mAddWishScreen;
            visibleList = mWishListScreen;
        }
        ObjectAnimator visToInvis = ObjectAnimator.ofFloat(visibleList, "rotationY", 0f, 90f);
        visToInvis.setDuration(500);
        visToInvis.setInterpolator(accelerator);
        final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(invisibleList, "rotationY",
                -90f, 0f);
        invisToVis.setDuration(500);
        invisToVis.setInterpolator(decelerator);
        invisToVis.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator anim) {
            	if(mWishListScreen.getVisibility() == View.GONE){
                	showKeyBoard();
            	}
            }
        });
        visToInvis.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator anim) {
                visibleList.setVisibility(View.GONE);
                invisToVis.start();
                invisibleList.setVisibility(View.VISIBLE);
            }
        });
        visToInvis.start();
    }


	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.wish_commit:
			String wish = mWishEdit.getText().toString();
			if(!TextUtils.isEmpty(wish)){
				Global.getInstance(this).addWish(wish);
			}
			freshList();
			flipit();
			break;
		case R.id.wish_add:
			mWishEdit.getText().clear();
			flipit();
			break;
		}
	}
	
	private void showKeyBoard() {
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.showSoftInput(mWishEdit, 0); // 显示软键盘
//		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); // 显示软键盘
	}
}
