package fr.bomberman.mathias.modele.agent;

import fr.bomberman.mathias.utils.AgentAction;
import fr.bomberman.mathias.utils.ColorAgent;

public class EnnemyType1 extends EnnemyAgent{

	public EnnemyType1(int x, int y, AgentAction agentAction, char type, ColorAgent color, boolean isInvincible, boolean isSick) {
		super(x, y,agentAction,type,color,isInvincible,isSick);
	}

	public void executeAction() {
		super.executeAction();
	}
	
}