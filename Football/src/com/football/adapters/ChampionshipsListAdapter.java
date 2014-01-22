package com.football.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.football.FootballController;
import com.football.R;
import com.football.basic.Championship;

public class ChampionshipsListAdapter extends ArrayAdapter<Championship> {

	private List<Championship> listItems;

	public ChampionshipsListAdapter(Context context, int layoutId, List<Championship> listItems) {
		super(context, layoutId, listItems);
		this.listItems = listItems;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			v = LayoutInflater.from(parent.getContext()).inflate(R.layout.championship_list_item, null);
		}

		Championship championship = (Championship) this.listItems.get(position);
		if (championship != null) {
			ImageView imageViewFlag = (ImageView) v.findViewById(R.id.imageViewFlag);
			imageViewFlag.setBackgroundResource(championship.getFlagId());

			TextView textViewChampionshipName = (TextView) v.findViewById(R.id.textViewName);
			textViewChampionshipName.setText(championship.getName());

			ImageView imageViewChampionshipSelected = (ImageView) v.findViewById(R.id.imageViewSelected);

			if (championship.equals(FootballController.getInstance().getCurrentChampionship())) {
				imageViewChampionshipSelected.setVisibility(View.VISIBLE);
			} else {
				imageViewChampionshipSelected.setVisibility(View.INVISIBLE);
			}
		}
		return v;
	}

}
