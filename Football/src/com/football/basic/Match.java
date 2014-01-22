package com.football.basic;

import com.football.R;

public class Match {

	private Team homeTeam;
	private Team awayTeam;

	private int homeTeamScore;
	private int awayTeamScore;

	private double homeTeamCoefficient;
	private double awayTeamCoefficient;

	private String shownText;
	private int imageInformation;

	public Match(Team homeTeam, Team awayTeam) {
		this.setHomeTeam(homeTeam);
		this.setAwayTeam(awayTeam);
		this.shownText = "";
	}

	public void initializeMatch() {
		this.homeTeamCoefficient = this.calculateCoefficient(this.homeTeam, this.homeTeamCoefficient);
		this.awayTeamCoefficient = this.calculateCoefficient(this.awayTeam, this.awayTeamCoefficient);

		this.homeTeamCoefficient = this.coefficientToPercentual(this.homeTeamCoefficient);
		this.awayTeamCoefficient = 1 - this.homeTeamCoefficient;

		this.homeTeamCoefficient = this.homeTeamCoefficient + 0.1;
		this.awayTeamCoefficient = this.awayTeamCoefficient - 0.1;
	}

	private double coefficientToPercentual(double coefficient) {
		return coefficient / (this.homeTeamCoefficient + this.awayTeamCoefficient);
	}

	private double calculateCoefficient(Team team, double coefficient) {
		coefficient = 0;
		for (Player player : team.getSelectedPlayers()) {
			coefficient += player.getStrenght();
		}
		coefficient = coefficient / team.getSelectedPlayers().size();
		coefficient = coefficient * team.getStrenght();
		return coefficient;
	}

	public Team getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}

	public Team getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(Team awayTeam) {
		this.awayTeam = awayTeam;
	}

	public int getHomeTeamScore() {
		return homeTeamScore;
	}

	public void setHomeTeamScore(int homeTeamScore) {
		this.homeTeamScore = homeTeamScore;
	}

	public int getAwayTeamScore() {
		return awayTeamScore;
	}

	public void setAwayTeamScore(int awayTeamScore) {
		this.awayTeamScore = awayTeamScore;
	}

	public void updateResult() {
		double random = Math.random();

		if (random < 0.05) { //means that something happened (5% of possibility)
			if (random < 0.03) {//means a goal (3% of possibility)
				random = Math.random();
				this.imageInformation = R.drawable.goal;
				if (random <= this.homeTeamCoefficient) {
					int index = ((int) (Math.random() * 10));
					this.homeTeamScore++;
					this.shownText = this.getHomeTeam().getSelectedPlayers().get(index).getName();
				} else {
					int index = ((int) (Math.random() * 10));
					this.awayTeamScore++;
					this.shownText = this.getAwayTeam().getSelectedPlayers().get(index).getName();
				}
			} else if (random < 0.048) { //means a card (1.8% of possibility)

				int index = ((int) (Math.random() * 10));
				if (Math.random() <= 0.5) {
					this.shownText = this.getHomeTeam().getSelectedPlayers().get(index).getName();
				} else {
					this.shownText = this.getAwayTeam().getSelectedPlayers().get(index).getName();
				}

				if (random < 0.045) {//yellow (1.5% of possibility)
					this.imageInformation = R.drawable.yellowcard;
				} else {//red card (0.3% of possibility)
					this.imageInformation = R.drawable.redcard;
				}
			} else { //means an injury (0.2% of possibility)
				int index = ((int) (Math.random() * 10));
				if (Math.random() <= 0.5) {
					this.shownText = this.getHomeTeam().getSelectedPlayers().get(index).getName();
				} else {
					this.shownText = this.getAwayTeam().getSelectedPlayers().get(index).getName();
				}
				this.imageInformation = R.drawable.injury;
			}
		}
	}

	@Override
	public String toString() {
		return this.homeTeam.getName() + " X " + this.awayTeam.getName();
	}

	public String getShownText() {
		return this.shownText;
	}

	public int getImageInformation() {
		return this.imageInformation;
	}
}
