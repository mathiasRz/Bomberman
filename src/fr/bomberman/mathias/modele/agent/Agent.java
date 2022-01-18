package fr.bomberman.mathias.modele.agent;

import fr.bomberman.mathias.utils.AgentAction;
import fr.bomberman.mathias.utils.ColorAgent;

public abstract class Agent {

	
	protected int _x;
	protected int _y;
	protected AgentAction _agentAction;
	protected char _type;
	protected ColorAgent _color;
	protected boolean _isInvincible;
	protected boolean _isSick;

	
	StrategyAgent _strategie;
			
	public Agent(int x, int y, AgentAction agentAction, char type, ColorAgent color, boolean isInvincible, boolean isSick) {
		this._x=x;
		this._y=y;
		this._agentAction = agentAction;
		this._type = type;
		this._color = color;
		this._isInvincible = isInvincible;
		this._isSick = isSick;
	}
	
    public void executeAction() {
    	_agentAction = _strategie.executeMove();
    	
    }
    
    public void setStrategyAgent(StrategyAgent strategie) {
    _strategie= strategie;
    }
    
    
    public int getX() {
    	return _x;
    }
    
    public int getY() {
    	return _y;
    }
    
    public void setX(int a) {
    	_x = a;
    }
    
    public void setY(int a) {
    	_y = a;
    }
    
    public char getType () {
    	return _type;
    }
    
    public AgentAction getAction () {
    	return _agentAction;
    }
    
    public ColorAgent getColor () {
    	return _color;
    }
    
    public boolean getIsInvincible () {
    	return _isInvincible;
    }
    
    public void setIsInvivncible(boolean isInvincible) {
    	_isInvincible = isInvincible;
    }
    
    public boolean getIsSick () {
    	return _isSick;
    }
    
    public void setIsSick(boolean isSick) {
    	_isSick = isSick;
    }
    
}
