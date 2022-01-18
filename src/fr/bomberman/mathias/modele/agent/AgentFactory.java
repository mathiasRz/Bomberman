package fr.bomberman.mathias.modele.agent;

import fr.bomberman.mathias.utils.AgentAction;
import fr.bomberman.mathias.utils.ColorAgent;

public interface AgentFactory {

    public Agent createAgent(int x, int y, AgentAction agentAction, char type, ColorAgent color, boolean isInvincible, boolean isSick);

}