package cn.kli.launcher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import cn.kli.queen.R;

public class TagGridView extends LinearLayout {
	private Context mContext;
	
	//views
	private GridView mGridView; 
	
	private TagViewAdapter mAdapter = new TagViewAdapter();

	public TagGridView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View root = inflater.inflate(R.layout.launcher_tag_gridview, this);
		mGridView = (GridView)root.findViewById(R.id.gridview);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				position = 0;
				((TagView)view).onClick();
			}
			
		});
	}
	
	public void addTagView(TagView view){
		mAdapter.addTagView(view);
	}
	
	
}
