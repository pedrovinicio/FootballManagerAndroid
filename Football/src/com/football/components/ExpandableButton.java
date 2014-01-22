package com.football.components;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.football.R;
import com.football.adapters.TacticsListAdapter;

public class ExpandableButton extends FrameLayout implements OnClickListener, OnItemClickListener {

	private Button button;
	private ListView listView;

	private ExpandableButtonListener listener;

	public interface ExpandableButtonListener {
		void onExpandableItemClicked(String item);
	}

	public ExpandableButton(Context context) {
		this(context, null);
	}

	public ExpandableButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.expandable_button, this, true);

		this.button = (Button) this.findViewById(R.id.button);
		this.listView = (ListView) this.findViewById(R.id.listView);

		this.button.setOnClickListener(this);
		this.listView.setOnItemClickListener(this);
	}

	public void setButtonText(String text) {
		this.button.setText(text);
	}

	public void setListContent(String[] content) {
		this.setListContent(Arrays.asList(content));
	}

	public void setListContent(List<String> content) {
		TacticsListAdapter adapter = new TacticsListAdapter(this.getContext(), android.R.layout.simple_list_item_1, content);
		this.listView.setAdapter(adapter);
	}

	public void onClick(View v) {
		//button click -> expand/hide
		if (this.button.getId() == v.getId()) {
			if (this.listView.getVisibility() == View.VISIBLE) {
				this.listView.setVisibility(View.GONE);
			} else {
				this.listView.setVisibility(View.VISIBLE);
				this.bringToFront();
				this.requestLayout();
			}
		}
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String tactic = (String) this.listView.getItemAtPosition(position);
		this.listView.setVisibility(View.GONE);
		this.button.setText(tactic);

		if (this.listener != null) {
			this.listener.onExpandableItemClicked(tactic);
		}
	}

	public ExpandableButtonListener getListener() {
		return listener;
	}

	public void setListener(ExpandableButtonListener listener) {
		this.listener = listener;
	}
}
