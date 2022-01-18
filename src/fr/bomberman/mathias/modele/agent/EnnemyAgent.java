package fr.bomberman.mathias.modele.agent;

import fr.bomberman.mathias.utils.AgentAction;
import fr.bomberman.mathias.utils.ColorAgent;

public abstract class EnnemyAgent extends Agent{

	public EnnemyAgent(int x, int y, AgentAction agentAction, char type, ColorAgent color, boolean isInvincible, boolean isSick) {
		super(x, y,agentAction,type,color,isInvincible,isSick);
	}

	
}
