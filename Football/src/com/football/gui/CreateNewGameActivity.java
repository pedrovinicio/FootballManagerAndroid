package com.football.gui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.football.FootballController;
import com.football.R;
import com.football.adapters.ChampionshipsListAdapter;
import com.football.adapters.TeamsListAdapter;
import com.football.basic.Championship;
import com.football.basic.Manager;
import com.football.basic.Team;

public class CreateNewGameActivity extends Activity implements OnClickListener, OnItemClickListener {

	private ArrayList<Championship> listChampionships;
	private ArrayList<Team> listTeams;

	private ListView listViewChampioship;
	private ListView listViewTeams;

	private EditText editTextManagerName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.create_new_game);

		this.listChampionships = FootballController.getInstance().getArrayListChampionships();

		((Button) this.findViewById(R.id.buttonPlay)).setOnClickListener(this);

		this.listViewChampioship = (ListView) this.findViewById(R.id.listViewChampionship);
		this.listViewChampioship.setAdapter(new ChampionshipsListAdapter(this, R.layout.championship_list_item, listChampionships));
		this.listViewChampioship.setOnItemClickListener(this);

		this.listViewTeams = (ListView) this.findViewById(R.id.listViewTeams);
		this.listViewTeams.setOnItemClickListener(this);

		this.editTextManagerName = (EditText) this.findViewById(R.id.editTextManagerName);
	}

	public void onClick(View v) {
		String managerName = this.editTextManagerName.getText().toString();

		if (managerName != null && managerName.length() > 0) {

			Team selectedTeam = FootballController.getInstance().getCurrentTeam();

			if (selectedTeam != null) {
				Manager manager = new Manager(managerName);
				selectedTeam.setManager(manager);
				startActivity(new Intent(this, ManageTeamActivity.class));
				this.finish();
			} else {
				//TODO: precisa selecionar um time
			}
		} else {
			//TODO: precisa colar um nome de treinador
		}
	}

	public void onItemClick(AdapterView<?> adapter, View parent, int position, long id) {
		int viewId = adapter.getId();

		switch (viewId) {
			case R.id.listViewChampionship:
				Championship championship = this.listChampionships.get(position);

				//update current championship on controller.
				FootballController.getInstance().setCurrentChampionship(championship);
				//get teams loaded from the specific championship
				this.listTeams = FootballController.getInstance().getArrayListTeams();

				this.listViewTeams.setAdapter(new TeamsListAdapter(this, R.layout.team_list_item, listTeams));

				this.listViewChampioship.invalidateViews();

				break;

			case R.id.listViewTeams:
				FootballController.getInstance().setCurrentTeam(this.listTeams.get(position));

				this.listViewTeams.invalidateViews();
				break;
		}

	}
}
