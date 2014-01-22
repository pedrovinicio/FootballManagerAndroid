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
import com.football.basic.Team;
import com.football.util.Util;

public class ChampionshipTableAdapter extends ArrayAdapter<Team> {
	private List<Team> listItems;

	public ChampionshipTableAdapter(Context context, int layoutId, List<Team> listItems) {
		super(context, layoutId, listItems);
		this.listItems = listItems;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			v = LayoutInflater.from(parent.getContext()).inflate(R.layout.championship_table_item, null);
		}

		Team team = (Team) this.listItems.get(position);
		if (team != null) {
			Championship championship = FootballController.getInstance().getCurrentChampionship();

			ImageView imageViewFlag = (ImageView) v.findViewById(R.id.imageViewFlag);
			imageViewFlag.setBackgroundResource(Util.getInstance().getTeamFlag(team.getFlag()));

			TextView textViewTeamName = (TextView) v.findViewById(R.id.textViewName);
			textViewTeamName.setText(team.getName());

			TextView textViewTeamVictories = (TextView) v.findViewById(R.id.textViewVictories);
			textViewTeamVictories.setText(String.valueOf(championship.getTeamVictories(team)));

			TextView textViewTeamDraws = (TextView) v.findViewById(R.id.textViewDraws);
			textViewTeamDraws.setText(String.valueOf(championship.getTeamDraws(team)));

			TextView textViewTeamLoses = (TextView) v.findViewById(R.id.textViewLoses);
			textViewTeamLoses.setText(String.valueOf(championship.getTeamLoses(team)));

			TextView textViewTeamGoalsPro = (TextView) v.findViewById(R.id.textViewGoalsPro);
			textViewTeamGoalsPro.setText(String.valueOf(championship.getTeamGoalsPro(team)));

			TextView textViewTeamGoalsAgainst = (TextView) v.findViewById(R.id.textViewGoalsAgainst);
			textViewTeamGoalsAgainst.setText(String.valueOf(championship.getTeamGoalsAgainst(team)));

			TextView textViewTeamPoints = (TextView) v.findViewById(R.id.textViewPoints);
			textViewTeamPoints.setText(String.valueOf(championship.getTeamPoints(team)));
		}
		return v;
	}
}
