package com.football.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.football.R;
import com.football.basic.Match;
import com.football.basic.Team;
import com.football.util.Util;

public class MatchLine extends RelativeLayout implements OnClickListener {

	private Match match;

	private MatchListener listener;

	public interface MatchListener {
		void onTeamClicked(Team team);
	}

	public MatchLine(Context context) {
		this(context, null, -1);
	}

	public MatchLine(Context context, AttributeSet attrs) {
		this(context, attrs, -1);
	}

	public MatchLine(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.match_line, this, true);

		view.findViewById(R.id.imageViewHome).setOnClickListener(this);
		view.findViewById(R.id.imageViewAway).setOnClickListener(this);
	}

	public void addMatch(Match match) {
		this.match = match;

		((ImageView) this.findViewById(R.id.imageViewHome)).setBackgroundResource(Util.getInstance().getTeamFlag(
				match.getHomeTeam().getFlag()));
		((ImageView) this.findViewById(R.id.imageViewAway)).setBackgroundResource(Util.getInstance().getTeamFlag(
				match.getAwayTeam().getFlag()));

		((TextView) this.findViewById(R.id.textViewHomeScore)).setText(String.valueOf(match.getHomeTeamScore()));
		((TextView) this.findViewById(R.id.textViewAwayScore)).setText(String.valueOf(match.getAwayTeamScore()));
	}

	public void updateScore() {
		((TextView) this.findViewById(R.id.textViewHomeScore)).setText(String.valueOf(match.getHomeTeamScore()));
		((TextView) this.findViewById(R.id.textViewAwayScore)).setText(String.valueOf(match.getAwayTeamScore()));
		((TextView) this.findViewById(R.id.textViewInformation)).setText(String.valueOf(match.getShownText()));
		if (match.getImageInformation() != 0) {
			((ImageView) this.findViewById(R.id.imageViewInformation)).setBackgroundResource(match.getImageInformation());
		}
	}

	public void setListener(MatchListener listener) {
		this.listener = listener;
	}

	public void onClick(View v) {
		if (this.listener != null) {
			if (v.getId() == R.id.imageViewHome) {
				this.listener.onTeamClicked(this.match.getHomeTeam());
			} else if (v.getId() == R.id.imageViewAway) {
				this.listener.onTeamClicked(this.match.getAwayTeam());
			}
		}
	}
}
