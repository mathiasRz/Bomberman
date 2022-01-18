package fr.bomberman.mathias.modele.agent;

import fr.bomberman.mathias.utils.AgentAction;
import fr.bomberman.mathias.utils.ColorAgent;

public class Bomberman extends Agent{

	
	private int _nbTurnsSpecialState;
	private int _id;
	
	public static int _cpt_id = 0;
	
	public Bomberman(int x, int y, AgentAction agentAction, char type, ColorAgent color, boolean isInvincible, boolean isSick) {
		super(x, y,agentAction,type,color,isInvincible,isSick);
		_nbTurnsSpecialState= 10;
		_id= _cpt_id;
		
		_cpt_id++;
	}
	
	
	public int getId() {
		return _id;
	}
	public void executeAction() {
		super.executeAction();
	}
	
	public int getNbTurnsSpecialState() {
		return _nbTurnsSpecialState;
	}
	
	public void setNbTurnsSpecialState(int turns) {
		_nbTurnsSpecialState = turns;
	}


}