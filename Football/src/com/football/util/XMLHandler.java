package com.football.util;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.football.FootballController;
import com.football.basic.Manager;
import com.football.basic.Player;
import com.football.basic.Team;
import com.football.basic.Player.Position;

public class XMLHandler extends DefaultHandler{

	private Team currentTeam;
	private ArrayList<Team> arrayListTeam;

	private Player currentPlayer;
	private ArrayList<Player> arrayListPlayer;

	private Manager currentTeamManager;

	private StringBuilder chars;
	private int ignoredTag = 0;

	public final static String UTF8 = "UTF-8";

	public static void loadTeams(String encoding, InputStream inputStream) {
		// instantiate our handler
		XMLHandler xmlHandler = new XMLHandler();
		try {
			if (encoding == null || "".equals(encoding.trim())) {
				encoding = UTF8;
			}
			InputSource is = new InputSource(inputStream);
			is.setEncoding(encoding);
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader xmlreader = parser.getXMLReader();

			xmlreader.setContentHandler(xmlHandler);
			xmlreader.parse(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		this.chars = new StringBuilder();

		if("teams".equalsIgnoreCase(localName)){
			arrayListTeam = new ArrayList<Team>();
		} else if ("team".equalsIgnoreCase(localName)){
			this.currentTeam = new Team();
		} else if ("id".equalsIgnoreCase(localName)) {
		} else if ("teamname".equalsIgnoreCase(localName)) {
		} else if ("manager".equalsIgnoreCase(localName)) {
			this.currentTeamManager = new Manager();
		} else if ("strenght".equalsIgnoreCase(localName)) {
		} else if ("cash".equalsIgnoreCase(localName)) {
		} else if ("players".equalsIgnoreCase(localName)) {
			arrayListPlayer = new ArrayList<Player>();
		} else if ("player".equalsIgnoreCase(localName)) {
			this.currentPlayer = new Player();
		} else if ("playername".equalsIgnoreCase(localName)) {
		} else if ("position".equalsIgnoreCase(localName)) {
		} else if ("playerstrenght".equalsIgnoreCase(localName)) {
		} else if ("stadium".equalsIgnoreCase(localName)) {
		} else {
			this.ignoredTag++;
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		if (this.ignoredTag == 0) {
			if ("teams".equalsIgnoreCase(localName)) {
				FootballController.getInstance().setArrayListTeams(this.arrayListTeam);
			} else if ("team".equalsIgnoreCase(localName)) {
				this.arrayListTeam.add(this.currentTeam);
			} else if ("id".equalsIgnoreCase(localName)) {
				this.currentTeam.setId(Integer.valueOf(this.chars.toString()));
				this.currentTeam.setFlag(Integer.valueOf(this.chars.toString()));
			} else if ("teamname".equalsIgnoreCase(localName)) {
				this.currentTeam.setName(this.chars.toString());
			} else if ("manager".equalsIgnoreCase(localName)) {
				this.currentTeamManager.setName(this.chars.toString());
				this.currentTeam.setManager(this.currentTeamManager);
			} else if ("strenght".equalsIgnoreCase(localName)) {
				this.currentTeam.setStrenght(Integer.valueOf(this.chars.toString()));
			} else if ("cash".equalsIgnoreCase(localName)) {
				this.currentTeam.setCash(Integer.valueOf(this.chars.toString()));
			} else if ("players".equalsIgnoreCase(localName)) {
				this.currentTeam.setArrayListPlayers(this.arrayListPlayer);
			} else if ("stadium".equalsIgnoreCase(localName)) {
				this.currentTeam.setStadium(chars.toString());
			} else if ("player".equalsIgnoreCase(localName)) {
				this.arrayListPlayer.add(this.currentPlayer);
			} else if ("playername".equalsIgnoreCase(localName)) {
				this.currentPlayer.setName(chars.toString());
			} else if ("position".equalsIgnoreCase(localName)) {
				String position = chars.toString();
				if(position.equalsIgnoreCase("goalkeeper")){
					this.currentPlayer.setPosition(Position.GOALKEEPER);
				}
				else if(position.equalsIgnoreCase("defender")){
					this.currentPlayer.setPosition(Position.DEFENDER);			
				}
				else if(position.equalsIgnoreCase("midfielder")){
					this.currentPlayer.setPosition(Position.MIDFIELD);
				}
				else if(position.equalsIgnoreCase("foward")){
					this.currentPlayer.setPosition(Position.FORWARD);
				}
			} 
			else if ("playerstrenght".equalsIgnoreCase(localName)) {
				this.currentPlayer.setStrenght(Integer.valueOf(chars.toString()));
			}
		} else {
			this.ignoredTag--;
		}
		this.chars = null;
	}

	@Override
	public void characters(char ch[], int start, int length) {
		if (this.chars != null) {
			this.chars.append(ch, start, length);
		}
	}

}
