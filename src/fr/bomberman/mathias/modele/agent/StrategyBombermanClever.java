package fr.bomberman.mathias.modele.agent;

import java.util.ArrayList;

import fr.bomberman.mathias.modele.BombermanGame;
import fr.bomberman.mathias.utils.AgentAction;
import fr.bomberman.mathias.utils.InfoBomb;
import fr.bomberman.mathias.utils.InfoItem;
import fr.bomberman.mathias.utils.ItemType;
import fr.bomberman.mathias.utils.StateBomb;


/* 
 * implementation "intelligente" pour les PNJ bombermans : evite les ennemis et les bombes tant qu'il le peut 
 * et cherche à s'équiper d'un gilet d'invivncibilité
 */

public class StrategyBombermanClever implements StrategyAgent {
	ArrayList<InfoBomb> _listInfoBombs;
	private ArrayList<EnnemyAgent> _ennemyList;
	private ArrayList<InfoItem> _listInfoItems;
	boolean[][] _breakableWalls;
	private int _x;
	private int _y;
	private int _sizeMapX;
	private int _sizeMapY;
	
	public StrategyBombermanClever (BombermanGame bg,Bomberman bomberman) {
		this._listInfoBombs=bg.GetListInfoBombs();
		this._ennemyList=bg.GetEnnemyList();
		this._listInfoItems=bg.GetListInfoItems();
		this._breakableWalls=bg.GetBreakableWalls();
		this._x=bomberman.getX();
		this._y=bomberman.getY();
		this._sizeMapX=bg.getMap().getSizeX();
		this._sizeMapY=bg.getMap().getSizeY();
	}
	
	
	public AgentAction executeMove() {
		boolean[][] deadCases = deadCases();
		AgentAction action = null;
		boolean actionSuicidaire = true;
		int coupsPermis=0;
		InfoItem fireSuite = takeFireSuit();
		if (fireSuite!=null) {
			if (Math.abs(fireSuite.getX()-_x)>(Math.abs(fireSuite.getY()-_y))) {
				if (fireSuite.getX()<_x) {
					if (!deadCases[_x-1][_y] && !_breakableWalls[_x-1][_y]) {
	    				action=AgentAction.MOVE_LEFT;
	    				actionSuicidaire=false;
	    			}
				}
				else {
					if (!deadCases[_x+1][_y] && !_breakableWalls[_x+1][_y]) {
	    				action=AgentAction.MOVE_RIGHT;
	    				actionSuicidaire=false;
	    			}
				}
			}
			else {
				if (fireSuite.getY()<_y) {
					if (!deadCases[_x][_y-1] && !_breakableWalls[_x][_y-1]) {
	    				action=AgentAction.MOVE_UP;
	    				actionSuicidaire=false;
	    			}
				}
				else {
					if (!deadCases[_x][_y+1] && !_breakableWalls[_x][_y+1]) {
	    				action=AgentAction.MOVE_DOWN;
	    				actionSuicidaire=false;
	    			}
				}
			}
		}
		if (actionSuicidaire) {
			while (actionSuicidaire && coupsPermis<3) {
				int r = (int) (Math.random()*(7-1)) + 1;
		    	switch (r) {
		    		case 1:
		    			if (!deadCases[_x][_y+1]) {
		    				action=AgentAction.MOVE_DOWN;
		    				actionSuicidaire=false;
		    			}
		    			break;
		    		case 2:
		    			if (!deadCases[_x-1][_y]) {
		    				action=AgentAction.MOVE_LEFT;
		    				actionSuicidaire=false;
		    			}
		    			break;
		    		case 3:
		    			if (!deadCases[_x][_y-1]) {
		    				action=AgentAction.MOVE_UP;
		    				actionSuicidaire=false;
		    			}
		    			break;
		    		case 4:
		    			if (!deadCases[_x+1][_y]) {
		    				action=AgentAction.MOVE_RIGHT;
		    				actionSuicidaire=false;
		    			}
		    			break;
		    		case 5:
		    			if (!deadCases[_x][_y]) {
		    				action=AgentAction.STOP;
		    				actionSuicidaire=false;
		    			}
		    			break;
		    		case 6:
		    			if (!deadCases[_x][_y]) {
		    				action=AgentAction.PUT_BOMB;
		    				actionSuicidaire=false;
		    			}
		    			break;
		    	}
		    	coupsPermis++;
			}
		}
		if (coupsPermis==3)
			action=AgentAction.STOP;
    	return action;
	}
	
	
	public boolean[][] deadCases (){
		boolean [][] deadCases = new boolean [_sizeMapX][_sizeMapY];
		for (InfoBomb bombe : _listInfoBombs) {
			//ne pas etre sur la bombe un tour avant l'explosion sionon l'agent meurt forcément
			if (bombe.getStateBomb()==StateBomb.Step2) {
				deadCases[bombe.getX()][bombe.getY()] = true;
			}
			if (bombe.getStateBomb()==StateBomb.Step3 || bombe.getStateBomb()==StateBomb.Boom) {
				deadCases[bombe.getX()][bombe.getY()] = true;
				for (int i = 1 ; i<= bombe.getRange();++i) {
					if (bombe.getX()+i<_sizeMapX)
						deadCases[bombe.getX()+i][bombe.getY()] = true;
					if (bombe.getX()-i>=0)
						deadCases[bombe.getX()-i][bombe.getY()] = true;
					if (bombe.getY()+i<_sizeMapY)
						deadCases[bombe.getX()][bombe.getY()+i] = true;
					if (bombe.getY()-i>=0)
						deadCases[bombe.getX()][bombe.getY()-i] = true;
				}
			}
		}
		for (EnnemyAgent ennemy : _ennemyList) {
			deadCases[ennemy.getX()][ennemy.getY()] = true;
		}
		return deadCases;
	}
	
	public InfoItem takeFireSuit () {
		int minX = 100;
		int minY = 100;
		InfoItem closestFireSuit = null;
		for (InfoItem fireSuit : _listInfoItems) {
			if (fireSuit.getType()==ItemType.FIRE_SUIT) {
				if (Math.abs(fireSuit.getX()-_x)<minX && Math.abs(fireSuit.getY()-_y)<minY) {
					minX=Math.abs(fireSuit.getX()-_x);
					minY=Math.abs(fireSuit.getY()-_y);
					closestFireSuit = fireSuit;
				}
			}
		}
		return closestFireSuit;
	}
	
}
