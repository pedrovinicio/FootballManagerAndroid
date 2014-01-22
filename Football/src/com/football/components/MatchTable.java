package com.football.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.football.basic.Match;
import com.football.components.MatchLine.MatchListener;

public class MatchTable extends LinearLayout{
	
	public MatchTable(Context context) {
		this(context, null);
	}
	
	public MatchTable(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void addMatch(Match match){
		MatchLine matchLine = new MatchLine(this.getContext());
		matchLine.addMatch(match);
		this.addView(matchLine);
	}
	
	public void updateScore(){
		for (int i = 0; i < this.getChildCount(); i++) {
			MatchLine matchLine = (MatchLine) this.getChildAt(i);
			matchLine.updateScore();
		}
	}
	
	public void setListener(MatchListener listener){
		for (int i = 0; i < this.getChildCount(); i++) {
			MatchLine matchLine = (MatchLine) this.getChildAt(i);
			matchLine.setListener(listener);
		}
	}
}
