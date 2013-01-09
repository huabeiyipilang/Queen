package cn.kli.launcher;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.kli.queen.R;

public abstract class TagView extends LinearLayout {
	private Context mContext;
	private ImageView mIcon;
	private TextView mName;

	public TagView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View root = inflater.inflate(R.layout.launcher_tag_view, this);
		mName = (TextView)root.findViewById(R.id.tag_name);
		mIcon = (ImageView)root.findViewById(R.id.tag_icon);
	}
	
	public void setTagName(String name){
		mName.setText(name);
	}
	
	public void setTagIcon(Drawable icon){
		mIcon.setImageDrawable(icon);
	}
	
	public abstract void onClick();

}
