package fr.bomberman.mathias.modele.agent;

import fr.bomberman.mathias.utils.AgentAction;

public class StrategyBombermanPlayer implements StrategyAgent{
	private char _mouvement;
	
	
	public StrategyBombermanPlayer (char mouvement) {
		this._mouvement=mouvement;
	}
	
	
	public AgentAction executeMove() {
		
    	switch (_mouvement) {
    		case 'S':
    			return AgentAction.MOVE_DOWN;
    		case 'Q':
    			return AgentAction.MOVE_LEFT;
    		case 'Z':
    			return AgentAction.MOVE_UP;
    		case 'D':
    			return  AgentAction.MOVE_RIGHT;
    		case 'B':
    			return AgentAction.PUT_BOMB;
    		default:
    			return AgentAction.STOP;
    	}
	}
}
