package com.football.util;

import java.util.HashMap;

import com.football.R;

public class Util {

	private static Util instance;
	private static final HashMap<Integer, Integer> mapTeamFlags = new HashMap<Integer, Integer>();
	private static final HashMap<Integer, Integer> mapChampionshipFlags = new HashMap<Integer, Integer>();
	private static final HashMap<Integer, String> mapChampionshipsXMLs = new HashMap<Integer, String>();

	public static Util getInstance() {
		if (instance == null) {
			instance = new Util();
			instance.init();
		}
		return instance;
	}

	private void init() {
		initMapFlags();
		initChampionshipFlags();
		initMapChampionshipXMLs();
	}

	private void initChampionshipFlags() {
		mapChampionshipFlags.put(1, R.drawable.campeonato_pernambucano);
	}

	private void initMapFlags() {
		mapTeamFlags.put(1, R.drawable.santa_cruz_pe);
		mapTeamFlags.put(2, R.drawable.sport_recife);
		mapTeamFlags.put(3, R.drawable.porto_pe);
		mapTeamFlags.put(4, R.drawable.america_pe);
	}

	private void initMapChampionshipXMLs() {
		mapChampionshipsXMLs.put(1, "campeonato_pernambucano.xml");
	}

	public int getTeamFlag(int teamId) {
		return mapTeamFlags.get(teamId);
	}

	public int getChampionshipFlag(int championshipId) {
		return mapChampionshipFlags.get(championshipId);
	}

	public String getChampionshipXML(int championshipId) {
		return mapChampionshipsXMLs.get(championshipId);
	}
}
