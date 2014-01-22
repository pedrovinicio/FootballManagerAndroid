package com.football.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.football.FootballController;
import com.football.R;
import com.football.adapters.ChampionshipTableAdapter;
import com.football.basic.Championship;
import com.football.basic.Team;

public class ChampionshipTableActivity extends Activity {

	private ListView championshipTable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.championship_table);

		((TextView) this.findViewById(R.id.textViewChampionshipName)).setText(FootballController.getInstance().getCurrentChampionship()
				.getName());

		this.championshipTable = (ListView) this.findViewById(R.id.listViewChampionshipTable);
		this.championshipTable.setAdapter(new ChampionshipTableAdapter(this, R.layout.championship_table_item, this
				.mergeSort(FootballController.getInstance().getCurrentChampionship().getTeams())));
		//this.championshipTable.addHeaderView(view);

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				//ChampionshipTableActivity.this.finish();
			}
		}, 5000);
	}

	private List<Team> mergeSort(ArrayList<Team> teams) {
		Championship championship = FootballController.getInstance().getCurrentChampionship();

		ArrayList<Team> teamsClone = new ArrayList<Team>(teams);

		Team aux;
		for (int i = 0; i < teamsClone.size(); i++) {
			for (int j = 0; j < teamsClone.size() - 1; j++) {
				boolean change = false;
				//check if team 1 has more points
				if (championship.getTeamPoints(teamsClone.get(j)) < championship.getTeamPoints(teamsClone.get(j + 1))) {
					change = true;
				}
				//points draw case
				else if (championship.getTeamPoints(teamsClone.get(j)) == championship.getTeamPoints(teamsClone.get(j + 1))) {
					//check number of victories
					if (championship.getTeamVictories(teamsClone.get(j)) < championship.getTeamVictories(teamsClone.get(j + 1))) {
						change = true;
					}
					//in case of draw
					else if (championship.getTeamVictories(teamsClone.get(j)) == championship.getTeamVictories(teamsClone.get(j + 1))) {
						//check team goals balance (Pro - Against)
						if (championship.getTeamGoalsBalance(teamsClone.get(j)) < championship.getTeamGoalsBalance(teamsClone.get(j + 1))) {
							change = true;
						}
						//in case of draw
						else if (championship.getTeamGoalsBalance(teamsClone.get(j)) == championship.getTeamGoalsBalance(teamsClone
								.get(j + 1))) {
							//check goals pro
							if (championship.getTeamGoalsPro(teamsClone.get(j)) < championship.getTeamGoalsPro(teamsClone.get(j + 1))) {
								change = true;
							}
							//in case of draw
							else if (championship.getTeamGoalsPro(teamsClone.get(j)) == championship.getTeamGoalsPro(teamsClone.get(j + 1))) {
								//alphabetical order.
								if (teamsClone.get(j).getName().compareTo(teamsClone.get(j + 1).getName()) > 0) {
									change = true;
								}
							}
						}
					}
				}
				if (change) {
					aux = teamsClone.set(j, teamsClone.get(j + 1));
					teamsClone.set(j + 1, aux);
				}
			}
		}
		return teamsClone;
	}

}
