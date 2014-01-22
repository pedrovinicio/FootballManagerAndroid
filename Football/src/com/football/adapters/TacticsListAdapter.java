package com.football.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.football.R;

public class TacticsListAdapter extends ArrayAdapter<String> {

	private List<String> listItems;

	public TacticsListAdapter(Context context, int layoutId, List<String> listItems) {
		super(context, layoutId, listItems);
		this.listItems = listItems;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_button_item, null);
		}

		String tactic = this.listItems.get(position);
		if (tactic != null) {
			TextView textView = (TextView) v.findViewById(R.id.textView);
			textView.setText(tactic);
		}
		return v;
	}

}
