package cn.ecailan.esy.Grid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import net.tsz.afinal.FinalBitmap;

import java.util.HashMap;
import java.util.List;

import cn.ecailan.esy.R;


public class ImageAdapter extends BaseAdapter {
	private Context context;
	private  List<HashMap<String,Object>> paths;
	private boolean localtion;// true是本地图片 false 是网络图片
	private FinalBitmap finalBit;

	private AddImage add;

	public ImageAdapter(Context context, List<HashMap<String,Object>> paths, boolean localtion) {
		this.context = context;
		this.paths = paths;
		this.localtion = localtion;
		finalBit = FinalBitmap.create(context);
	}

	public void setAddImage(AddImage add) {
		this.add = add;
	}


	public void setData( List<HashMap<String,Object>> paths) {
		this.paths = paths;
	}

	@Override
	public int getCount() {
		return paths == null ? 0 : paths.size();
	}

	@Override
	public Object getItem(int position) {
		return paths == null ? null : paths.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = new Holder();
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.grid_friend_images, null);
			holder.imageView = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		finalBit.display(holder.imageView,paths.get(position).get("url").toString());
		return convertView;
	}

	private class Holder {
		public ImageView imageView;
	}

	/**
	 * 加载图片接口
	 *
	 */
	public interface AddImage {
		public void addImage(ImageView view, String path);

	}
}
