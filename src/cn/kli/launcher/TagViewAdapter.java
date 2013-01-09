package cn.kli.launcher;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TagViewAdapter extends BaseAdapter {
	private List<TagView> mViewList = new ArrayList<TagView>();

	@Override
	public int getCount() {
		return mViewList.size();
	}

	@Override
	public Object getItem(int location) {
		return mViewList.get(location);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int location, View arg1, ViewGroup arg2) {
		return mViewList.get(location);
	}
	
	public void addTagView(TagView view){
		mViewList.add(view);
		notifyDataSetChanged();
	}

}
