package cn.kli.queen;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import cn.kli.launcher.TagView;

public class AppTagView extends TagView {
	private Context mContext;
	private ResolveInfo mResolveInfo;

	public AppTagView(Context context, ResolveInfo info) {
		super(context);
		mContext = context;
		mResolveInfo = info;
		bindApp(mResolveInfo);
	}


	private void bindApp(ResolveInfo info){
		PackageManager pm = mContext.getPackageManager();
		String name = info.loadLabel(pm).toString();
		setTagName(name);
		Drawable icon = info.loadIcon(pm);
		setTagIcon(icon);
	}
	
	@Override
	public void onClick(){
		launchApp(mResolveInfo);
	}
	
	private void launchApp(ResolveInfo reInfo){
		String packageName = reInfo.activityInfo.packageName;
		String name = reInfo.activityInfo.name;
		ComponentName cn = new ComponentName(packageName,name);
		Intent intent = new Intent();
		intent.setComponent(cn);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(intent);
	}
}
