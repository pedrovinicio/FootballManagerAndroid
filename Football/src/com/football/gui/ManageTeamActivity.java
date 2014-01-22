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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.football.FootballController;
import com.football.R;
import com.football.adapters.SquadListAdapter;
import com.football.basic.Championship;
import com.football.basic.Match;
import com.football.basic.Player;
import com.football.basic.Round;
import com.football.components.ExpandableButton;
import com.football.components.ExpandableButton.ExpandableButtonListener;
import com.football.util.Util;

public class ManageTeamActivity extends Activity implements OnItemClickListener, ExpandableButtonListener, OnClickListener {

	private ExpandableButton expandableButtonTactics;
	private ListView listViewTeamSquad;

	private ArrayList<String> tactics;
	private int selectedTactic;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.manage_team);

		this.tactics = FootballController.getInstance().getValidTactics(this.getResources().getStringArray(R.array.tatics),
				FootballController.getInstance().getCurrentTeam());

		this.selectedTactic = 0;//TODO: load last valid formation

		FootballController.getInstance().selectSquad(this.tactics.get(this.selectedTactic),
				FootballController.getInstance().getCurrentTeam());

	}

	@Override
	protected void onResume() {
		super.onResume();

		initLayout();
	}

	private void initLayout() {
		this.expandableButtonTactics = (ExpandableButton) this.findViewById(R.id.expandableButtonTactics);
		this.expandableButtonTactics.setListener(this);
		this.expandableButtonTactics.setButtonText(this.tactics.get(selectedTactic));
		this.expandableButtonTactics.setListContent(this.tactics);

		this.listViewTeamSquad = (ListView) this.findViewById(R.id.listViewSquad);
		this.listViewTeamSquad.setScrollbarFadingEnabled(false);
		this.listViewTeamSquad.setAdapter(new SquadListAdapter(this, R.layout.squad_list_item, FootballController.getInstance()
				.getCurrentTeam().getArrayListPlayers()));
		this.listViewTeamSquad.setOnItemClickListener(this);

		//set team flag
		((ImageView) this.findViewById(R.id.imageViewTeamPortrait)).setImageResource(Util.getInstance().getTeamFlag(
				FootballController.getInstance().getCurrentTeam().getId()));

		//set team name
		((TextView) this.findViewById(R.id.textViewTeamName)).setText(FootballController.getInstance().getCurrentTeam().getName());

		//set current year.
		((TextView) this.findViewById(R.id.textViewCurrentYear)).setText(String.valueOf(FootballController.getInstance().getCurrentYear()));

		//set current year.
		((TextView) this.findViewById(R.id.textViewTeamCash)).setText(String.valueOf(FootballController.getInstance().getCurrentTeam()
				.getCash()
				+ " mil"));

		//set manager name
		((TextView) this.findViewById(R.id.textViewManagerName)).setText(FootballController.getInstance().getCurrentTeam().getManager()
				.getName());

		//next game information
		Championship championship = FootballController.getInstance().getCurrentChampionship();
		Match match = championship.getCurrentRoundMatch(FootballController.getInstance().getCurrentTeam());

		//championship name
		((TextView) this.findViewById(R.id.textViewNextGameChampionship)).setText(championship.getName());

		//home team flag
		((ImageView) this.findViewById(R.id.imageViewNextGameHomeTeamPortrait)).setImageResource(Util.getInstance().getTeamFlag(
				match.getHomeTeam().getFlag()));

		//away team flag
		((ImageView) this.findViewById(R.id.imageViewNextGameAwayTeamPortrait)).setImageResource(Util.getInstance().getTeamFlag(
				match.getAwayTeam().getFlag()));

		//string HOME or AWAY
		if (FootballController.getInstance().getCurrentTeam().getId() == match.getHomeTeam().getId()) {
			((TextView) this.findViewById(R.id.textViewNextGameHomeAwayLabel)).setText(this.getResources().getString(R.string.home));
		} else {
			((TextView) this.findViewById(R.id.textViewNextGameHomeAwayLabel)).setText(this.getResources().getString(R.string.away));
		}

		//Stadium name
		((TextView) this.findViewById(R.id.textViewNextGameStadium)).setText(match.getHomeTeam().getStadium());

		//round name
		((TextView) this.findViewById(R.id.textViewNextGameRoundNumber)).setText(championship.getCurrentRoundNumber() + "�");

		//TODO: manager reputation

		this.findViewById(R.id.buttonPlay).setOnClickListener(this);
		this.findViewById(R.id.relativeLayoutNextGameLine).setOnClickListener(this);
		this.findViewById(R.id.relativeLayoutNextGameLineBottom).setOnClickListener(this);
	}

	//public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		Player player = (Player) arg0.getAdapter().getItem(arg2);

		if (player.getState() == Player.State.OUT) {
			if (!FootballController.getInstance().getCurrentTeam().isSelectedPlayersComplete()) {
				player.setState(Player.State.SELECTED);
				arg1.findViewById(R.id.imageViewSelection).setBackgroundResource(R.drawable.green_ball);
			} else if (!FootballController.getInstance().getCurrentTeam().isReservesPlayersComplete()) {
				player.setState(Player.State.RESERVE);
				arg1.findViewById(R.id.imageViewSelection).setBackgroundResource(R.drawable.blue_ball);
			}
		} else if (player.getState() == Player.State.SELECTED) {
			if (!FootballController.getInstance().getCurrentTeam().isReservesPlayersComplete()) {
				player.setState(Player.State.RESERVE);
				arg1.findViewById(R.id.imageViewSelection).setBackgroundResource(R.drawable.blue_ball);
			} else {
				player.setState(Player.State.OUT);
				arg1.findViewById(R.id.imageViewSelection).setBackgroundResource(R.drawable.red_ball);
			}
		} else if (player.getState() == Player.State.RESERVE) {
			player.setState(Player.State.OUT);
			arg1.findViewById(R.id.imageViewSelection).setBackgroundResource(R.drawable.red_ball);
		}
	}

	@SuppressWarnings("unchecked")
	public void onExpandableItemClicked(String item) {
		FootballController.getInstance().selectSquad(item, FootballController.getInstance().getCurrentTeam());
		((ArrayAdapter<Player>) this.listViewTeamSquad.getAdapter()).notifyDataSetChanged();
	}

	public void onClick(View v) {
		int id = v.getId();

		switch (id) {
			case R.id.buttonPlay:
				Championship championship = FootballController.getInstance().getCurrentChampionship();
				Round round = championship.getCurrentRound();

				for (Match match : round.getListMatches()) {
					if (match.getAwayTeam().getId() != FootballController.getInstance().getCurrentTeam().getId()) {
						ArrayList<String> tactics = FootballController.getInstance().getValidTactics(
								this.getResources().getStringArray(R.array.tatics), match.getHomeTeam());

						FootballController.getInstance().selectSquad(tactics.get(tactics.size() / 2), match.getAwayTeam());
					}
					if (match.getHomeTeam().getId() != FootballController.getInstance().getCurrentTeam().getId()) {
						ArrayList<String> tactics = FootballController.getInstance().getValidTactics(
								this.getResources().getStringArray(R.array.tatics), match.getHomeTeam());

						FootballController.getInstance().selectSquad(tactics.get(tactics.size() / 2), match.getHomeTeam());
					}
					match.initializeMatch();
				}

				startActivity(new Intent(this, MatchActivity.class));
				break;
			case R.id.relativeLayoutNextGameLine:
				this.findViewById(R.id.relativeLayoutBottomPanel).setVisibility(View.GONE);
				this.findViewById(R.id.relativeLayoutNextGameLineBottom).setVisibility(View.VISIBLE);
				break;
			case R.id.relativeLayoutNextGameLineBottom:
				this.findViewById(R.id.relativeLayoutBottomPanel).setVisibility(View.VISIBLE);
				this.findViewById(R.id.relativeLayoutNextGameLineBottom).setVisibility(View.GONE);
				break;
		}
	}
}
