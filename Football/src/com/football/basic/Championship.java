package com.football.basic;

import java.util.ArrayList;
import java.util.HashMap;

public class Championship {

	private int id;
	private int flagId;
	private int currentRound;
	private String name;

	private ArrayList<Team> arrayListTeams;
	private ArrayList<Round> arrayListRounds;
	
	private HashMap<Integer, Integer> championshipTablePoints;
	private HashMap<Integer, Integer> championshipTableVictory;
	private HashMap<Integer, Integer> championshipTableDraw;
	private HashMap<Integer, Integer> championshipTableLoses;
	private HashMap<Integer, Integer> championshipTableGoalsPro;
	private HashMap<Integer, Integer> championshipTableGoalsAgainst;

	public Championship() {
		this.arrayListTeams = new ArrayList<Team>();
		this.championshipTablePoints = new HashMap<Integer, Integer>();
		this.championshipTableVictory = new HashMap<Integer, Integer>();
		this.championshipTableDraw = new HashMap<Integer, Integer>();
		this.championshipTableLoses = new HashMap<Integer, Integer>();
		this.championshipTableGoalsPro = new HashMap<Integer, Integer>();
		this.championshipTableGoalsAgainst = new HashMap<Integer, Integer>();
	}

	public Championship(int flagId, String name, ArrayList<Team> teams) {
		this.setFlagId(flagId);
		this.setName(name);
		this.setTeams(teams);
		this.championshipTablePoints = new HashMap<Integer, Integer>();
		this.championshipTableVictory = new HashMap<Integer, Integer>();
		this.championshipTableDraw = new HashMap<Integer, Integer>();
		this.championshipTableLoses = new HashMap<Integer, Integer>();
		this.championshipTableGoalsPro = new HashMap<Integer, Integer>();
		this.championshipTableGoalsAgainst = new HashMap<Integer, Integer>();
	}

	public ArrayList<Team> getTeams() {
		return arrayListTeams;
	}
	public void setTeams(ArrayList<Team> teams) {
		this.arrayListTeams = teams;
		
		this.fillTable();
		this.createRounds();
	}
	private void fillTable() {
		for (Team team : this.arrayListTeams) {
			this.championshipTablePoints.put(team.getId(), 0);
			this.championshipTableVictory.put(team.getId(), 0);
			this.championshipTableDraw.put(team.getId(), 0);
			this.championshipTableLoses.put(team.getId(), 0);
			this.championshipTableGoalsPro.put(team.getId(), 0);
			this.championshipTableGoalsAgainst.put(team.getId(), 0);
		}
	}

	private void createRounds() {
		this.arrayListRounds = new ArrayList<Round>();

		//create all combinations
		ArrayList<Match> allMatches = new ArrayList<Match>();
		for (int i = 0; i < this.arrayListTeams.size(); i++) {
			for (int j = 0; j < this.arrayListTeams.size(); j++) {
				if(i != j){
					allMatches.add(new Match(this.arrayListTeams.get(i), this.arrayListTeams.get(j)));
				}
			}
		}

		int numberOfRound = 1;
		
		while(!allMatches.isEmpty()){
			//number of games per round
			Round round = new Round(numberOfRound++);
			
			for (int i = 0; i < (this.arrayListTeams.size()/2); i++) {
				Match match = this.getNonConflictingMatch(allMatches, round.getListMatches());
				
				if(match != null){
					round.addMatch(match);
					allMatches.remove(match);
				}
			}
			
			this.arrayListRounds.add(round);
		}
	}

	private Match getNonConflictingMatch(ArrayList<Match> allMatches, ArrayList<Match> roundMatches){
		
		for (Match match : allMatches) {
			
			boolean hasConflict = false;
			
			for (Match roundMatch : roundMatches) {
				if(!hasConflict){
					hasConflict = this.hasConflict(match, roundMatch);
				}
				else{
					break;
				}
			}
			
			if(!hasConflict){
				return match;
			}
		}
		return null;
	}
	
	private boolean hasConflict(Match match1, Match match2){
		int idHomeTeam = match1.getHomeTeam().getId();
		int idAwayTeam = match1.getAwayTeam().getId();
		
		boolean homeTeamOk = (idHomeTeam != match2.getHomeTeam().getId()) && 
								(idHomeTeam != match2.getAwayTeam().getId());
		
		boolean awayTeamOk = (idAwayTeam != match2.getHomeTeam().getId()) && 
								(idAwayTeam != match2.getAwayTeam().getId());
		
		return !homeTeamOk || !awayTeamOk;
	}
	
	public void addTeam(Team team) {
		this.arrayListTeams.add(team);
	}
	public int getFlagId() {
		return this.flagId;
	}
	public void setFlagId(int flagId) {
		this.flagId = flagId;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<Round> getRounds(){
		return this.arrayListRounds;
	}
	public void incrementRound(){
		Round round = this.arrayListRounds.get(this.currentRound);
		
		for (Match match : round.getListMatches()) {
			if(match.getHomeTeamScore() > match.getAwayTeamScore()){ //homeTeam won
				int homeTeamPoints = this.championshipTablePoints.get(match.getHomeTeam().getId());
				this.championshipTablePoints.put(match.getHomeTeam().getId(), homeTeamPoints + 3);
				
				int homeTeamVictories = this.championshipTableVictory.get(match.getHomeTeam().getId());
				this.championshipTableVictory.put(match.getHomeTeam().getId(), homeTeamVictories + 1);
				
				int awayTeamLoses = this.championshipTableLoses.get(match.getAwayTeam().getId());
				this.championshipTableLoses.put(match.getAwayTeam().getId(), awayTeamLoses + 1);
			}
			else if(match.getHomeTeamScore() < match.getAwayTeamScore()){ //homeTeam lost
				int awayTeamPoints = this.championshipTablePoints.get(match.getAwayTeam().getId());
				this.championshipTablePoints.put(match.getAwayTeam().getId(), awayTeamPoints + 3);
				
				int awayTeamVictories = this.championshipTableVictory.get(match.getAwayTeam().getId());
				this.championshipTableVictory.put(match.getAwayTeam().getId(), awayTeamVictories + 1);
				
				int homeTeamLoses = this.championshipTableLoses.get(match.getHomeTeam().getId());
				this.championshipTableLoses.put(match.getHomeTeam().getId(), homeTeamLoses + 1);
			}
			else{ //draw
				int homeTeamPoints = this.championshipTablePoints.get(match.getHomeTeam().getId());
				int awayTeamPoints = this.championshipTablePoints.get(match.getAwayTeam().getId());
				
				this.championshipTablePoints.put(match.getHomeTeam().getId(), homeTeamPoints + 1);
				this.championshipTablePoints.put(match.getAwayTeam().getId(), awayTeamPoints + 1);
				
				int awayTeamDraws = this.championshipTableDraw.get(match.getAwayTeam().getId());
				this.championshipTableDraw.put(match.getAwayTeam().getId(), awayTeamDraws + 1);
				
				int homeTeamDraws = this.championshipTableDraw.get(match.getHomeTeam().getId());
				this.championshipTableDraw.put(match.getHomeTeam().getId(), homeTeamDraws + 1);
				
			}
			
			int homeTeamGoalsPro = this.championshipTableGoalsPro.get(match.getHomeTeam().getId());
			this.championshipTableGoalsPro.put(match.getHomeTeam().getId(), homeTeamGoalsPro + match.getHomeTeamScore());
			
			int homeTeamGoalsAgainst = this.championshipTableGoalsAgainst.get(match.getHomeTeam().getId());
			this.championshipTableGoalsAgainst.put(match.getHomeTeam().getId(), homeTeamGoalsAgainst + match.getAwayTeamScore());
			
			int awayTeamGoalsPro = this.championshipTableGoalsPro.get(match.getAwayTeam().getId());
			this.championshipTableGoalsPro.put(match.getAwayTeam().getId(), awayTeamGoalsPro + match.getAwayTeamScore());
			
			int awayTeamGoalsAgainst = this.championshipTableGoalsAgainst.get(match.getAwayTeam().getId());
			this.championshipTableGoalsAgainst.put(match.getAwayTeam().getId(), awayTeamGoalsAgainst + match.getHomeTeamScore());
		}
		this.currentRound++;
	}
	public Round getCurrentRound(){
		return this.arrayListRounds.get(this.currentRound);
	}
	@Override
	public boolean equals(Object o) {
		if(o instanceof Championship){
			Championship other = (Championship) o;
			if(this.getId() == other.getId()){
				return true;
			}
		}
		return false;
	}
	
	public int getCurrentRoundNumber(){
		return this.arrayListRounds.get(this.currentRound).getNumber();
	}
	
	public Match getCurrentRoundMatch(Team team){
		Round round = this.arrayListRounds.get(this.currentRound);
		return round.getMatch(team);
	}
	
	public void playRound(){
		this.arrayListRounds.get(this.currentRound).play();
	}
	
	public int getTeamPoints(Team team){
		return this.championshipTablePoints.get(team.getId());
	}
	
	public int getTeamVictories(Team team){
		return this.championshipTableVictory.get(team.getId());
	}
	
	public int getTeamLoses(Team team){
		return this.championshipTableLoses.get(team.getId());
	}
	
	public int getTeamDraws(Team team){
		return this.championshipTableDraw.get(team.getId());
	}
	
	public int getTeamGoalsPro(Team team){
		return this.championshipTableGoalsPro.get(team.getId());
	}
	
	public int getTeamGoalsAgainst(Team team){
		return this.championshipTableGoalsAgainst.get(team.getId());
	}
	
	public int getTeamGoalsBalance(Team team){
		return this.getTeamGoalsPro(team) - this.getTeamGoalsAgainst(team);
	}
}
