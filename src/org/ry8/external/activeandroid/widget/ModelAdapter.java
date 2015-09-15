package org.ry8.external.activeandroid.widget;

import android.content.Context;
import android.widget.ArrayAdapter;
import org.ry8.external.activeandroid.Model;

import java.util.Collection;
import java.util.List;

public class ModelAdapter<T extends Model> extends ArrayAdapter<T> {
	public ModelAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public ModelAdapter(Context context, int resource, int textViewResourceId) {
		super(context, resource, textViewResourceId);
	}

	public ModelAdapter(Context context, int textViewResourceId, List<T> objects) {
		super(context, textViewResourceId, objects);
	}

	public ModelAdapter(Context context, int resource, int textViewResourceId, List<T> objects) {
		super(context, resource, textViewResourceId, objects);
	}

	/**
	 * Clears the adapter and, if data != null, fills if with new Items.
	 * 
	 * @param collection A Collection<? extends T> which members get added to the adapter.
	 */
	public void setData(Collection<? extends T> collection) {
		clear();

		if (collection != null) {
			for (T item : collection) {
				add(item);
			}
		}
	}


}