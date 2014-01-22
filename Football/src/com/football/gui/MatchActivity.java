package com.football.gui;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.football.FootballController;
import com.football.R;
import com.football.adapters.SubstitutionListAdapter;
import com.football.basic.Championship;
import com.football.basic.Match;
import com.football.basic.Player;
import com.football.basic.Round;
import com.football.basic.Team;
import com.football.components.MatchLine.MatchListener;
import com.football.components.MatchTable;

public class MatchActivity extends Activity implements MatchListener, OnClickListener, OnItemClickListener {

	private ProgressBar progressBar;

	private Timer timer;

	private Time currentTime;

	private MatchTable matchTable;

	private boolean isPaused;

	private SubstitutionListAdapter titularAdapter;
	private SubstitutionListAdapter reserveAdapter;

	private int substitutionCount = 0;

	private enum Time {
		FIRST, SECOND;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.match);

		this.initLayout();

		this.matchTable.setListener(this);

		this.currentTime = Time.FIRST;

		this.progressBar = (ProgressBar) this.findViewById(R.id.progressBarTime);
		this.progressBar.setProgress(0);

		this.runGame();
	}

	private void runGame() {
		this.timer = new Timer();
		this.timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (!isPaused) {
					if (MatchActivity.this.progressBar.getProgress() == MatchActivity.this.progressBar.getMax()) {

						if (MatchActivity.this.currentTime == Time.FIRST) {
							MatchActivity.this.currentTime = Time.SECOND;
							MatchActivity.this.progressBar.setProgress(0);

							MatchActivity.this.runOnUiThread(new Runnable() {
								public void run() {
									((TextView) MatchActivity.this.findViewById(R.id.textViewTime)).setText(MatchActivity.this
											.getResources().getString(R.string.second_time));
								}
							});
						} else {
							//TODO: refresh previous screen
							FootballController.getInstance().getCurrentChampionship().incrementRound();

							MatchActivity.this.timer.schedule(new TimerTask() {
								@Override
								public void run() {
									MatchActivity.this.startActivity(new Intent(MatchActivity.this, ChampionshipTableActivity.class));
									MatchActivity.this.finish();
								}
							}, 1000);

							this.cancel();
						}
					} else {
						MatchActivity.this.progressBar.incrementProgressBy(3);
						FootballController.getInstance().getCurrentChampionship().playRound();

						MatchActivity.this.runOnUiThread(new Runnable() {
							public void run() {
								MatchActivity.this.updateScore();
							}
						});
					}

				}
			}
		}, 1000, 300);
	}

	private void updateScore() {
		this.matchTable.updateScore();
	}

	private void initLayout() {
		this.matchTable = (MatchTable) this.findViewById(R.id.matchTable);

		Championship championship = FootballController.getInstance().getCurrentChampionship();
		Round round = championship.getCurrentRound();

		for (Match match : round.getListMatches()) {
			matchTable.addMatch(match);
		}
	}

	public void onTeamClicked(Team team) {
		this.isPaused = true;

		View view = this.findViewById(R.id.layoutSubstitution);

		TextView textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
		textViewTitle.setText(team.getName());

		this.titularAdapter = new SubstitutionListAdapter(this, R.layout.substitution_item, team.getSelectedPlayers());

		ListView listTitular = (ListView) view.findViewById(R.id.listViewSquadTitular);
		listTitular.setAdapter(this.titularAdapter);

		List<Player> reserves = team.getReservePlayers();
		this.reserveAdapter = new SubstitutionListAdapter(this, R.layout.substitution_item, reserves);

		ListView listReserve = (ListView) view.findViewById(R.id.listViewSquadReserve);
		listReserve.setAdapter(this.reserveAdapter);

		this.findViewById(R.id.buttonSubstitute).setVisibility(View.INVISIBLE);

		if (team.equals(FootballController.getInstance().getCurrentTeam())) {
			listTitular.setOnItemClickListener(this);
			listReserve.setOnItemClickListener(this);
			this.findViewById(R.id.buttonSubstitute).setOnClickListener(this);
		} else {
			listTitular.setOnItemClickListener(null);
			listReserve.setOnItemClickListener(null);
			this.findViewById(R.id.buttonSubstitute).setOnClickListener(null);
		}

		this.findViewById(R.id.buttonCancel).setOnClickListener(this);

		view.setVisibility(View.VISIBLE);
	}

	@Override
	public void onBackPressed() {
		View view = this.findViewById(R.id.layoutSubstitution);
		if (view.getVisibility() == View.VISIBLE) {
			view.setVisibility(View.GONE);
			this.isPaused = false;
		}
	}

	@Override
	protected void onPause() {
		this.isPaused = true;
		super.onPause();
	}

	@Override
	protected void onResume() {
		View view = this.findViewById(R.id.layoutSubstitution);
		if (view.getVisibility() == View.GONE) {
			this.isPaused = false;
		}
		super.onResume();
	}

	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buttonSubstitute:
				Player titular = this.titularAdapter.getSelectedPlayer();
				titular.setState(Player.State.OUT);
				Player reserve = this.reserveAdapter.getSelectedPlayer();
				reserve.setState(Player.State.SELECTED);

				this.titularAdapter.remove(titular);
				this.titularAdapter.add(reserve);
				this.reserveAdapter.remove(reserve);

				this.titularAdapter.resetSelection();
				this.reserveAdapter.resetSelection();

				this.findViewById(R.id.buttonSubstitute).setVisibility(View.INVISIBLE);
				this.substitutionCount++;
				break;
			case R.id.buttonCancel:
				View view = this.findViewById(R.id.layoutSubstitution);
				view.setVisibility(View.GONE);
				this.isPaused = false;
				break;
		}

	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch (parent.getId()) {
			case R.id.listViewSquadTitular:
				this.titularAdapter.setSelection(position);

				//if reserve adapter has selection, enable the sub button
				if (this.reserveAdapter.hasSelection() && this.substitutionCount < 3) {
					this.findViewById(R.id.buttonSubstitute).setVisibility(View.VISIBLE);
				}

				break;

			case R.id.listViewSquadReserve:
				this.reserveAdapter.setSelection(position);

				//if titular adapter has selection, enable the sub button
				if (this.titularAdapter.hasSelection() && this.substitutionCount < 3) {
					this.findViewById(R.id.buttonSubstitute).setVisibility(View.VISIBLE);
				}

				break;
		}
	}
}
