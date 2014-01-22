package com.football.basic;

import java.util.ArrayList;

public class Round {

	private int number;
	
	private ArrayList<Match> listMatches;
	
	public Round(int number) {
		this.number = number;
		this.listMatches = new ArrayList<Match>();
	}
	
	public Round(int number, ArrayList<Match> matches) {
		this.number = number;
		this.listMatches = matches;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public ArrayList<Match> getListMatches() {
		return listMatches;
	}

	public void setListMatches(ArrayList<Match> listMatches) {
		this.listMatches = listMatches;
	}
	
	public void addMatch(Match match){
		this.listMatches.add(match);
	}
	
	public void play(){
		for (Match match : this.listMatches) {
			match.updateResult();
		}
	}

	public Match getMatch(Team team) {
		Match teamMatch = null;
		for (Match match : this.listMatches) {
			if(match.getHomeTeam().getId() == team.getId() ||
					match.getAwayTeam().getId() == team.getId()){
				teamMatch = match;
			}
		}
		return teamMatch;
	}
	@Override
	public String toString() {
		String s = "";
		for (Match m : this.listMatches) {
			s += m.toString();
			s += " ";
		}
		return s;
	}
}
