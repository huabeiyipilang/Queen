package cn.kli.queen.updater;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.kli.queen.R;

public class AppListView extends LinearLayout {
	private Context mContext;
	
	//views
	private ListView mListView;
	
	//data
	private AppAdapter mAdapter;
	private List<UpdateAppInfo> mList;
	
	public AppListView(Context context) {
		super(context);
		mContext = context;
		initViews();
	}
	
	public AppListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initViews();
	}

	private void initViews(){
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View root = inflater.inflate(R.layout.app_list_view, this);
		mListView = (ListView)root.findViewById(R.id.update_app_list);
		mList = getLocalAppInfo();
		mAdapter = new AppAdapter(mList);
		mListView.setAdapter(mAdapter);
	}

	public void setRemoteList(List<UpdateInfo> list){
		
	}
	
	private List<UpdateAppInfo> getLocalAppInfo(){
		PackageManager pm = mContext.getPackageManager();
		List<PackageInfo> all = pm.getInstalledPackages(PackageManager.GET_ACTIVITIES);
		List<UpdateAppInfo> local = new ArrayList<UpdateAppInfo>();
		UpdateAppInfo tmp;
		for(PackageInfo info : all){
			if(info.packageName.startsWith("cn.kli.queen.")){
				tmp = new UpdateAppInfo();
				tmp.setPkgInfo(info);
				local.add(tmp);
			}
		}
		return local;
	}
	
	private class UpdateAppInfo{
		public final static int TYPE_NO 		= 1;
		public final static int TYPE_UPDATE 	= 2;
		public final static int TYPE_NEW 		= 3;
		public final static int TYPE_UNINSTALL 	= 4;
		private UpdateInfo updateInfo;
		private PackageInfo pkgInfo;
		
		public void setUpdateInfo(UpdateInfo updateInfo) {
			this.updateInfo = updateInfo;
		}
		
		public void setPkgInfo(PackageInfo pkgInfo) {
			this.pkgInfo = pkgInfo;
		}
		
		public int getUpdateType(){
			if(updateInfo == null){
				return TYPE_UNINSTALL;
			}else if(pkgInfo == null){
				return TYPE_NEW;
			}else if(Integer.valueOf(updateInfo.version_code) >
							Integer.valueOf(pkgInfo.versionCode)){
				return TYPE_UPDATE;
			}
			return TYPE_NO;
		}
		
		public Drawable getIcon(){
			Drawable icon = null;
			if(pkgInfo != null){

				PackageManager pm = mContext.getPackageManager();
				try {
					icon = pm.getApplicationIcon(pkgInfo.packageName);
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
			}else if(updateInfo != null){
				
			}
			return icon;
		}
		
		public String getName(){
			String name = null;
			if(pkgInfo != null){
				name = pkgInfo.packageName;
			}
			return name;
		}
		
		public String getCurrentVersion(){
			if(pkgInfo != null){
				return pkgInfo.versionName;
			}
			return "";
		}
	}
	
	private class AppAdapter extends BaseAdapter{
		private List<UpdateAppInfo> infoList;
		public AppAdapter(List<UpdateAppInfo> list){
			infoList = list;
		}
		@Override
		public int getCount() {
			return infoList.size();
		}

		@Override
		public Object getItem(int pos) {
			return infoList.get(pos);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int pos, View arg1, ViewGroup arg2) {
			return new AppUpdateView(mContext, infoList.get(pos));
		}
	}
	
	private class AppUpdateView extends LinearLayout{
		private LayoutInflater inflater;
		//views
		private ImageView icon;
		private TextView name;
		private TextView version;
		private ImageButton button;
		
		public AppUpdateView(Context context, UpdateAppInfo info) {
			super(context);
			inflater = LayoutInflater.from(context);
			View root = inflater.inflate(R.layout.app_list_item, this);
			icon = (ImageView)root.findViewById(R.id.app_icon);
			name = (TextView)root.findViewById(R.id.app_name);
			version = (TextView)root.findViewById(R.id.app_current_version);
			button = (ImageButton)root.findViewById(R.id.app_upload);

			try {
				icon.setImageDrawable(info.getIcon());
			} catch (Exception e) {
				e.printStackTrace();
			}
			name.setText(info.getName());
			version.setText(info.getCurrentVersion());
		}
		
	}
}
