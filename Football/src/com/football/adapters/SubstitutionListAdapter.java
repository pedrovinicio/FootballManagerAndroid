package com.football.adapters;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.football.R;
import com.football.basic.Player;

public class SubstitutionListAdapter extends ArrayAdapter<Player> {

	private List<Player> listItems;

	private int selection;

	public SubstitutionListAdapter(Context context, int layoutId, List<Player> listItems) {
		super(context, layoutId, listItems);
		this.listItems = listItems;
		this.selection = -1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		v = LayoutInflater.from(parent.getContext()).inflate(R.layout.substitution_item, null);

		Player player = (Player) this.listItems.get(position);
		if (player != null) {

			TextView textViewPlayerPosition = (TextView) v.findViewById(R.id.textViewPlayerPosition);
			TextView textViewPlayerName = (TextView) v.findViewById(R.id.textViewPlayerName);
			TextView textViewPlayerStrenght = (TextView) v.findViewById(R.id.textViewPlayerStrenght);

			if (this.selection == position) {
				textViewPlayerPosition.setTextColor(0xFF000000);
				textViewPlayerPosition.setTypeface(null, Typeface.BOLD);
				textViewPlayerName.setTextColor(0xFF000000);
				textViewPlayerName.setTypeface(null, Typeface.BOLD);
				textViewPlayerStrenght.setTextColor(0xFF000000);
				textViewPlayerStrenght.setTypeface(null, Typeface.BOLD);
			}

			String shortPosition = this.getContext().getResources().getString(player.getPositionAsStringId());
			shortPosition = String.valueOf(shortPosition.charAt(0));
			textViewPlayerPosition.setText(shortPosition);

			if (this.selection == position) {
				if (parent.getId() == R.id.listViewSquadTitular) {
					v.setBackgroundColor(0xFFC83C14);
				} else {
					v.setBackgroundColor(0xFF33CCFF);
				}
			}
			textViewPlayerName.setText(player.getName());

			textViewPlayerStrenght.setText(String.valueOf(player.getStrenght()));
		}
		return v;
	}

	public void setSelection(int position) {
		this.selection = position;
		this.notifyDataSetChanged();
	}

	public Player getSelectedPlayer() {
		return this.listItems.get(this.selection);
	}

	public boolean hasSelection() {
		return this.selection != -1;
	}

	public void resetSelection() {
		this.selection = -1;
		this.notifyDataSetChanged();
	}

	@Override
	public void remove(Player object) {
		super.remove(object);
		this.mergeSort();
	}

	@Override
	public void add(Player object) {
		super.add(object);
		this.mergeSort();
	}

	public void mergeSort() {
		Player aux;
		for (int i = 0; i < this.listItems.size(); i++) {
			for (int j = 0; j < this.listItems.size() - 1; j++) {
				if (this.listItems.get(j).getPosition().ordinal() > this.listItems.get(j + 1).getPosition().ordinal()) {
					aux = this.listItems.set(j, this.listItems.get(j + 1));
					this.listItems.set(j + 1, aux);
				}
			}
		}
	}
}
