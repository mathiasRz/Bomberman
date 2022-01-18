package fr.bomberman.mathias.modele.agent;

import java.util.ArrayList;

import fr.bomberman.mathias.utils.AgentAction;

/* 
 * implementation "intelligente" pour les PNJ ennemies bird : cible le bomberman le plus proche
 * sur un rayon de 5 cases et se rapproche de sa position pour le tuer
 */
public class StrategyBirdClosestBomberman implements StrategyAgent{
	private ArrayList<Bomberman> _bombermanList = new ArrayList<>();
	private int _x;
	private int _y;
	
	
	public StrategyBirdClosestBomberman (ArrayList<Bomberman> bombermanList,int x,int y) {
		this._bombermanList=bombermanList;
		this._x = x;
		this._y = y;
	}
	
	
	public AgentAction executeMove() {
		
		
		Bomberman cible = closestBomberman();
			if (cible==null)
				return AgentAction.STOP;
			else {
				if (Math.abs(cible.getX()-_x)>(Math.abs(cible.getY()-_y))) {
					if (cible.getX()<_x)
						return AgentAction.MOVE_LEFT;
					else
						return  AgentAction.MOVE_RIGHT;
				}
				else {
					if (cible.getY()<_y)
						return AgentAction.MOVE_UP;
					else
						return  AgentAction.MOVE_DOWN;
				}
			}
	}
	
	public Bomberman closestBomberman () {
		int minX = 5;
		int minY = 5;
		Bomberman closestBomberman = null;
		for (Bomberman bomberman : _bombermanList) {
			if (Math.abs(bomberman.getX()-_x)<minX && Math.abs(bomberman.getY()-_y)<minY) {
				minX=Math.abs(bomberman.getX()-_x);
				minY=Math.abs(bomberman.getY()-_y);
				closestBomberman = bomberman;
			}
		}
		
		return closestBomberman;
	}
}
