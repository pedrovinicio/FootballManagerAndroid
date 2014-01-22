package com.football.basic;

import java.util.ArrayList;
import java.util.List;

import com.football.basic.Player.State;

public class Team {

	private final int maxSelectedPlayers = 11;
	private final int maxReservesPlayers = 5;
	
	private int id;
	private String name;
	private int strenght;
	private int cash;

	private int flag;

	private Manager manager;
	private ArrayList<Player> arrayListPlayers;
	private String stadium;
	

	public Team() {
		this.arrayListPlayers = new ArrayList<Player>();
	}

	public Team(String name, int flag, ArrayList<Player> players,int cash) {
		this(name, flag, players, cash, null,null);
	}

	public Team(String name, int flag, ArrayList<Player> players, int cash, Manager manager, String stadium) {
		this.setName(name);
		this.setFlag(flag);
		this.arrayListPlayers = players;
		this.manager = manager;
		this.cash = cash;
		this.stadium = stadium;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public ArrayList<Player> getArrayListPlayers() {
		return arrayListPlayers;
	}

	public void setArrayListPlayers(ArrayList<Player> arrayListPlayers) {
		this.arrayListPlayers = arrayListPlayers;
	}

	public void addPlayer(Player player){
		this.arrayListPlayers.add(player);
	}

	public List<Player> getSelectedPlayers(){
		return this.getPlayers(Player.State.SELECTED);
	}

	public List<Player> getReservePlayers(){
		return this.getPlayers(Player.State.RESERVE);
	}

	public List<Player> getOutPlayers(){
		return this.getPlayers(Player.State.OUT);
	}
	
	private List<Player> getPlayers(State state){
		List<Player> players = new ArrayList<Player>();

		for (Player player : this.arrayListPlayers) {
			if(player.getState() == state){
				players.add(player);
			}
		}

		return players;
	}

	public boolean isSelectedPlayersComplete(){
		return this.getPlayers(Player.State.SELECTED).size() == maxSelectedPlayers;
	}

	public boolean isReservesPlayersComplete(){
		return this.getPlayers(Player.State.RESERVE).size() == maxReservesPlayers;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStrenght() {
		return strenght;
	}

	public void setStrenght(int strenght) {
		this.strenght = strenght;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Team){
			Team other = (Team) o;
			if(this.getId() == other.getId()){
				return true;
			}
		}
		return false;
	}

	public void setStadium(String string) {
		this.stadium = string;
	}

	public String getStadium(){
		return this.stadium;
	}
}
