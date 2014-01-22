package com.football;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;

import com.football.basic.Championship;
import com.football.basic.Player;
import com.football.basic.Player.Position;
import com.football.basic.Team;
import com.football.util.Util;
import com.football.util.XMLHandler;

public class FootballController {

	private static FootballController controller;
	private static Context context;

	private ArrayList<Championship> arrayListChampionships;

	private Championship currentChampionship;
	private Team currentTeam;

	private Date currentDate;

	public static FootballController getInstance() {
		if (controller == null) {
			controller = new FootballController();
		}
		return controller;
	}

	public void init(final FootballActivity activity) {
		context = activity;
		this.currentDate = new Date(System.currentTimeMillis());
		loadChampionships();
	}

	public static Context getContext() {
		return context;
	}

	private void loadChampionships() {
		String[] championshipNames = context.getResources().getStringArray(R.array.championships);
		Arrays.asList(championshipNames);
		if (championshipNames != null && championshipNames.length > 0) {
			this.arrayListChampionships = new ArrayList<Championship>();
			for (int i = 0; i < championshipNames.length; i++) {
				Championship championship = new Championship();
				championship.setId(i + 1);
				championship.setName(championshipNames[i]);
				championship.setFlagId(Util.getInstance().getChampionshipFlag(championship.getId()));
				this.arrayListChampionships.add(championship);
			}
		} else {
			//TODO: throw Exception
		}
	}

	public void loadTeams(int championshipId) {
		InputStream is;
		try {
			AssetManager assetManager = context.getAssets();
			is = assetManager.open(Util.getInstance().getChampionshipXML(championshipId));
			XMLHandler.loadTeams(XMLHandler.UTF8, is);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Championship> getArrayListChampionships() {
		return arrayListChampionships;
	}

	public void setArrayListChampionships(ArrayList<Championship> arrayListChampionships) {
		this.arrayListChampionships = arrayListChampionships;
	}

	public ArrayList<Team> getArrayListTeams() {
		return this.currentChampionship.getTeams();
	}

	public void setArrayListTeams(ArrayList<Team> arrayListTeams) {
		this.currentChampionship.setTeams(arrayListTeams);
	}

	public Championship getCurrentChampionship() {
		return currentChampionship;
	}

	public void setCurrentChampionship(Championship currentChampionship) {
		if ((this.currentChampionship == null) || (this.currentChampionship.getId() != currentChampionship.getId())) {
			this.currentChampionship = currentChampionship;
			//load teams from specific championship
			this.loadTeams(this.currentChampionship.getId());
		}
	}

	public Team getCurrentTeam() {
		return currentTeam;
	}

	public void setCurrentTeam(Team currentTeam) {
		this.currentTeam = currentTeam;
	}

	public int getCurrentYear() {
		return this.currentDate.getYear() + 1900;
	}

	public void incrementCurrentDateYear() {
		this.currentDate.setYear(this.currentDate.getYear() + 1);
	}

	public void selectSquad(String tactic, Team team) {
		ArrayList<Player> goalkeepers = this.getPlayersForPosition(Player.Position.GOALKEEPER, team);
		ArrayList<Player> defenders = this.getPlayersForPosition(Player.Position.DEFENDER, team);
		ArrayList<Player> midfielders = this.getPlayersForPosition(Player.Position.MIDFIELD, team);
		ArrayList<Player> fowarders = this.getPlayersForPosition(Player.Position.FORWARD, team);

		int reservesCount = 0;

		//select best goalkeeper
		this.mergeSort(goalkeepers);

		for (int i = 0; i < goalkeepers.size(); i++) {
			if (i == 0) {
				goalkeepers.get(i).setState(Player.State.SELECTED);
			} else if (i == 1) {
				reservesCount++;
				goalkeepers.get(i).setState(Player.State.RESERVE);
			} else {
				goalkeepers.get(i).setState(Player.State.OUT);
			}
		}

		if (!tactic.equals("Melhores")) {

			this.mergeSort(defenders);
			this.mergeSort(midfielders);
			this.mergeSort(fowarders);

			int numberOfDefenders = Integer.valueOf(tactic.substring(0, 1));
			int numberOfMidfielders = Integer.valueOf(tactic.substring(2, 3));
			int numberOfFowarders = Integer.valueOf(tactic.substring(4, 5));

			for (int i = 0; i < defenders.size(); i++) {
				if (i < numberOfDefenders) {
					defenders.get(i).setState(Player.State.SELECTED);
				} else if (i < (numberOfDefenders + 1)) {
					reservesCount++;
					defenders.get(i).setState(Player.State.RESERVE);
				} else {
					defenders.get(i).setState(Player.State.OUT);
				}
			}
			for (int i = 0; i < midfielders.size(); i++) {
				if (i < numberOfMidfielders) {
					midfielders.get(i).setState(Player.State.SELECTED);
				} else if (i < (numberOfMidfielders + 1)) {
					reservesCount++;
					midfielders.get(i).setState(Player.State.RESERVE);
				} else {
					midfielders.get(i).setState(Player.State.OUT);
				}
			}
			for (int i = 0; i < fowarders.size(); i++) {
				if (i < numberOfFowarders) {
					fowarders.get(i).setState(Player.State.SELECTED);
				} else if (i < (numberOfFowarders + 1)) {
					reservesCount++;
					fowarders.get(i).setState(Player.State.RESERVE);
				} else {
					fowarders.get(i).setState(Player.State.OUT);
				}
			}

			if (team.getOutPlayers().size() > 0) {
				List<Player> outPlayers = team.getOutPlayers();
				this.mergeSort(outPlayers);

				while (team.getOutPlayers().size() > 0 && reservesCount < 5) {
					outPlayers.get(0).setState(Player.State.RESERVE);
					outPlayers.remove(0);
					reservesCount++;
				}
			}
		} else {
			ArrayList<Player> nonGoalkeepers = new ArrayList<Player>();
			nonGoalkeepers.addAll(defenders);
			nonGoalkeepers.addAll(midfielders);
			nonGoalkeepers.addAll(fowarders);

			this.mergeSort(nonGoalkeepers);

			for (int i = 0; i < nonGoalkeepers.size(); i++) {
				if (i < 11) {
					nonGoalkeepers.get(i).setState(Player.State.SELECTED);
				} else if (i < 16) {
					nonGoalkeepers.get(i).setState(Player.State.RESERVE);
				} else {
					nonGoalkeepers.get(i).setState(Player.State.OUT);
				}
			}
		}

	}

	public ArrayList<String> getValidTactics(String[] tactics, Team team) {

		ArrayList<String> validTactics = new ArrayList<String>();

		for (String tactic : tactics) {
			if (!tactic.equals("Melhores")) {
				int numberOfDefenders = Integer.valueOf(tactic.substring(0, 1));
				int numberOfMidfielders = Integer.valueOf(tactic.substring(2, 3));
				int numberOfFowarders = Integer.valueOf(tactic.substring(4, 5));

				if (hasPlayersForPosition(Player.Position.DEFENDER, numberOfDefenders, team)) {
					if (hasPlayersForPosition(Player.Position.MIDFIELD, numberOfMidfielders, team)) {
						if (hasPlayersForPosition(Player.Position.FORWARD, numberOfFowarders, team)) {
							validTactics.add(tactic);
						}
					}
				}
			} else {
				validTactics.add(tactic); //adding "Melhores"
			}
		}
		return validTactics;
	}

	private ArrayList<Player> getPlayersForPosition(Position position, Team team) {
		ArrayList<Player> squad = team.getArrayListPlayers();
		ArrayList<Player> playersForPosition = new ArrayList<Player>();

		for (Player player : squad) {
			if (player.getPosition() == position) {
				playersForPosition.add(player);
			}
		}

		return playersForPosition;
	}

	private boolean hasPlayersForPosition(Position position, int number, Team team) {
		int count = 0;

		for (Player player : team.getArrayListPlayers()) {
			if (count < number) {
				if (player.getPosition() == position) {
					count++;
				}
			} else {
				break;
			}
		}

		return count == number;
	}

	public void mergeSort(List<Player> squad) {
		Player aux;
		for (int i = 0; i < squad.size(); i++) {
			for (int j = 0; j < squad.size() - 1; j++) {
				if (squad.get(j).getStrenght() < squad.get(j + 1).getStrenght()) {
					aux = squad.set(j, squad.get(j + 1));
					squad.set(j + 1, aux);
				}
			}
		}
	}

	public void destroy() {
		this.arrayListChampionships = null;
		this.currentChampionship = null;
		this.currentTeam = null;
	}

}
