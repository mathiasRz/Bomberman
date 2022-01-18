package fr.bomberman.mathias.modele.agent;

import fr.bomberman.mathias.utils.AgentAction;

public class StrategyEnnemyRandom implements StrategyAgent{
	
	public AgentAction executeMove() {
		int r = (int) (Math.random()*(6-1)) + 1;
    	switch (r) {
    		case 1:
    			return AgentAction.MOVE_DOWN;
    		case 2:
    			return AgentAction.MOVE_LEFT;
    		case 3:
    			return AgentAction.MOVE_UP;
    		case 4:
    			return  AgentAction.MOVE_RIGHT;
    		case 5:
    			return AgentAction.STOP;
    	}
    	return AgentAction.STOP;
	}
}

