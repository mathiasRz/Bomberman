package fr.bomberman.mathias.modele.agent;

import fr.bomberman.mathias.utils.AgentAction;
import fr.bomberman.mathias.utils.ColorAgent;

public class BombermanFactory implements AgentFactory {
	
	@Override
	public Agent createAgent(int x, int y, AgentAction agentAction, char type, ColorAgent color, boolean isInvincible, boolean isSick) {
		
		Agent agent = null;
    	agent = new Bomberman(x, y,agentAction,type,color,isInvincible,isSick);
        
        return agent;
            
	}
        
        
}
