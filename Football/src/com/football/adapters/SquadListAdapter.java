package com.football.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.football.R;
import com.football.basic.Player;

public class SquadListAdapter extends ArrayAdapter<Player> {

	private List<Player> listItems;

	public SquadListAdapter(Context context, int layoutId, List<Player> listItems) {
		super(context, layoutId, listItems);
		this.listItems = listItems;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			v = LayoutInflater.from(parent.getContext()).inflate(R.layout.squad_list_item, null);
		}

		Player player = (Player) this.listItems.get(position);
		if (player != null) {
			ImageView imageViewSelection = (ImageView) v.findViewById(R.id.imageViewSelection);

			if (player.getState() == Player.State.OUT) {
				imageViewSelection.setBackgroundResource(R.drawable.red_ball);
			} else if (player.getState() == Player.State.SELECTED) {
				imageViewSelection.setBackgroundResource(R.drawable.green_ball);
			} else if (player.getState() == Player.State.RESERVE) {
				imageViewSelection.setBackgroundResource(R.drawable.blue_ball);
			}

			TextView textViewPlayerName = (TextView) v.findViewById(R.id.textViewPlayerName);
			textViewPlayerName.setText(player.getName());

			TextView textViewPlayerPosition = (TextView) v.findViewById(R.id.textViewPosition);
			textViewPlayerPosition.setText(player.getPositionAsStringId());

			TextView textViewPlayerStrenght = (TextView) v.findViewById(R.id.textViewStrenght);
			textViewPlayerStrenght.setText(String.valueOf(player.getStrenght()));
		}
		return v;
	}

}
