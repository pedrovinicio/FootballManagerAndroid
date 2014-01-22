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
import com.football.basic.Team;
import com.football.util.Util;

public class TeamsListAdapter extends ArrayAdapter<Team> {

	private List<Team> listItems;

	public TeamsListAdapter(Context context, int layoutId, List<Team> teams) {
		super(context, layoutId, teams);
		this.listItems = teams;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			v = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_list_item, null);
		}

		Team team = (Team) this.listItems.get(position);
		if (team != null) {
			ImageView imageViewFlag = (ImageView) v.findViewById(R.id.imageViewFlag);
			imageViewFlag.setBackgroundResource(Util.getInstance().getTeamFlag(team.getFlag()));

			TextView textViewChampionshipName = (TextView) v.findViewById(R.id.textViewName);
			textViewChampionshipName.setText(team.getName());

			ImageView imageViewSelected = (ImageView) v.findViewById(R.id.imageViewSelected);

			if (team.equals(FootballController.getInstance().getCurrentTeam())) {
				imageViewSelected.setVisibility(View.VISIBLE);
			} else {
				imageViewSelected.setVisibility(View.INVISIBLE);
			}
		}
		return v;
	}
}
