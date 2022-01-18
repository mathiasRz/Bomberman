package fr.bomberman.mathias.modele;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Random;

import fr.bomberman.mathias.modele.agent.Agent;
import fr.bomberman.mathias.modele.agent.Bomberman;
import fr.bomberman.mathias.modele.agent.BombermanFactory;
import fr.bomberman.mathias.modele.agent.EnnemyAgent;
import fr.bomberman.mathias.modele.agent.EnnemyFactory;
import fr.bomberman.mathias.modele.agent.StrategyBirdClosestBomberman;
import fr.bomberman.mathias.modele.agent.StrategyBombermanClever;
import fr.bomberman.mathias.modele.agent.StrategyBombermanPlayer;
import fr.bomberman.mathias.modele.agent.StrategyBombermanRandom;
import fr.bomberman.mathias.modele.agent.StrategyEnnemyClosestBomberman;
import fr.bomberman.mathias.modele.agent.StrategyEnnemyRandom;
import fr.bomberman.mathias.utils.AgentAction;
import fr.bomberman.mathias.utils.ColorAgent;
import fr.bomberman.mathias.utils.InfoAgent;
import fr.bomberman.mathias.utils.InfoBomb;
import fr.bomberman.mathias.utils.InfoItem;
import fr.bomberman.mathias.utils.ItemType;
import fr.bomberman.mathias.utils.StateBomb;

public class BombermanGame extends Game{
	
	/* attributs de la classe */
	private InputMap _map;
	boolean[][] _breakable_walls;
	private ArrayList<Bomberman> _bombermanList = new ArrayList<>();
	private ArrayList<EnnemyAgent> _ennemyList = new ArrayList<>();
	private ArrayList<InfoItem> _listInfoItems;
	private ArrayList<InfoBomb> _listInfoBombs;
	private ArrayList<Integer> _casesUnderExplosion = new ArrayList<>();
	private int _rayonExplosion;
	private char _actionJoueur1;//utile pour recuperer l'action jouer par le joueur humain a l'aide du clavier
	
	
	/* constructeur, getteurs/setteurs */
	
	public BombermanGame (int maxTurn,InputMap map) {
		super(maxTurn);
		this._map = map;
	}
	
	public ArrayList<EnnemyAgent> GetEnnemyList (){
		return _ennemyList;
	}
	
	public ArrayList<InfoItem> GetListInfoItems (){
		return _listInfoItems;
	}
	
	public ArrayList<InfoBomb> GetListInfoBombs () {
		return _listInfoBombs;
	}
	
	public boolean[][] GetBreakableWalls (){
		return _breakable_walls;
	}
	
	public InputMap getMap() {
		return _map;
	}
	
	public void setActionJoueur1(char actionJoueur1) {
		_actionJoueur1 = actionJoueur1;
	}
	
	public char getActionJoueur1() {
		return _actionJoueur1;
	}
	
	/* implementation pour l'ecoute de l'interface du jeu sur la classe du jeu */
	private final PropertyChangeSupport support = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        support.addPropertyChangeListener( property, listener);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    
    public void setMesures(int turn) {
    	ArrayList<InfoAgent> agents = new ArrayList<>();
    	for (Bomberman bomberman : _bombermanList) {
    		agents.add(new InfoAgent(bomberman.getX(),bomberman.getY(),bomberman.getAction(),bomberman.getType(),bomberman.getColor(),bomberman.getIsInvincible(),bomberman.getIsSick()));
    	}
    	for (EnnemyAgent ennemy : _ennemyList) {
    		agents.add(new InfoAgent(ennemy.getX(),ennemy.getY(),ennemy.getAction(),ennemy.getType(),ennemy.getColor(),ennemy.getIsInvincible(),ennemy.getIsSick()));
    	}
    	support.firePropertyChange("_turn", _turn, turn);
    	support.firePropertyChange("liste murs cassables", null, _breakable_walls);
		support.firePropertyChange("liste agents", null, agents);
		support.firePropertyChange("liste items", null, _listInfoItems);
		support.firePropertyChange("liste bombes", null, _listInfoBombs);
		this._turn = turn;
	}
	
	
    /* chargements des agents de la map en debut de partie dans la classe du jeu */
	public void addAgent (InfoAgent infoAgent) {
		BombermanFactory bombermans = new BombermanFactory();
		EnnemyFactory ennemys = new EnnemyFactory();
		if (infoAgent.getType()=='B') {
			Bomberman bomberman = (Bomberman) bombermans.createAgent(infoAgent.getX(),infoAgent.getY(),infoAgent.getAgentAction(),infoAgent.getType(),infoAgent.getColor(),infoAgent.isInvincible(),infoAgent.isSick());
			if (bomberman.getId()==0)
				bomberman.setStrategyAgent(new StrategyBombermanPlayer('P'));
			else 
				bomberman.setStrategyAgent(new StrategyBombermanRandom());
			_bombermanList.add(bomberman);
		}
		else {
			EnnemyAgent ennemy = (EnnemyAgent) ennemys.createAgent(infoAgent.getX(),infoAgent.getY(),infoAgent.getAgentAction(),infoAgent.getType(),infoAgent.getColor(),infoAgent.isInvincible(),infoAgent.isSick());
			if (ennemy.getType()=='V')
				ennemy.setStrategyAgent(new StrategyBirdClosestBomberman(_bombermanList,ennemy.getX(),ennemy.getY()));
			else {
				ennemy.setStrategyAgent(new StrategyEnnemyRandom());
			}
			_ennemyList.add(ennemy);
		}
	}
	

	@Override
	public void initializeGame() {
		// TODO Auto-generated method stub
		_bombermanList.clear();
		_ennemyList.clear();
		Bomberman._cpt_id = 0;
		for (InfoAgent agent : _map.getStart_agents()) {
			addAgent(agent);
		}
		this._breakable_walls = new boolean [_map.getStart_breakable_walls().length][_map.getStart_breakable_walls()[0].length];
		for (int i=0;i<_map.getStart_breakable_walls().length;++i) {
			for (int j=0;j<_map.getStart_breakable_walls()[i].length;++j) {
				this._breakable_walls[i][j]=_map.getStart_breakable_walls()[i][j];
			}
		}
		this._listInfoBombs = new ArrayList<>();
		this._listInfoItems = new ArrayList<>();
		this._rayonExplosion = 2;
		setMesures(0);
	}

	@Override
	public boolean gameContinue() {
		if(_maxturn == _turn || _bombermanList.size()==1) 
			return false;
		else 
			return true;
	}

	@Override
	public void takeTurn() {
		// TODO Auto-generated method stub
		/* a chaque début de tour on met a jour les états des bombes sur la map et les murs détruits */
		updateStateBombs();
		destroyBreakableWalls(0.95);
		/* on fait jouer les ennemis en premier */
		for (EnnemyAgent ennemy : _ennemyList) {
			boolean coupLegal = false;
			//cas pour l'ennemi bird
			if (ennemy.getType()=='V') {
				while (!coupLegal) {
					/* on fixe la probablilté de l'ennemi bird de faire un coup intelligent a 60% sur chaque tour */
					Random random = new Random();
					int rand = random.nextInt(100 + 0) + 0;
					if (rand<60) {
						ennemy.setStrategyAgent(new StrategyBirdClosestBomberman(_bombermanList,ennemy.getX(),ennemy.getY()));
						//c'est forcement un coup légal, bird peut passer au dessus des murs
						coupLegal=true;
						ennemy.executeAction();
						moveAgent(ennemy);
					}
					else { 
						ennemy.setStrategyAgent(new StrategyEnnemyRandom());
						ennemy.executeAction();
						if(isLegalMove(ennemy)) {
							moveAgent(ennemy);
							coupLegal=true;
						}
					}
				}
			}
			// cas pour les autres types d'ennemis
			else {
				while (!coupLegal) {
					/* on fixe la probablilté de l'ennemi de faire un coup intelligent a 60% sur chaque tour */
					Random random = new Random();
					int rand = random.nextInt(100 + 0) + 0;
					if (rand<60) {
						ennemy.setStrategyAgent(new StrategyEnnemyClosestBomberman(_bombermanList,ennemy.getX(),ennemy.getY()));
						ennemy.executeAction();
						if(isLegalMove(ennemy)) {
							moveAgent(ennemy);
							coupLegal=true;
						}
					}
					else { 
						ennemy.setStrategyAgent(new StrategyEnnemyRandom());
						ennemy.executeAction();
						if(isLegalMove(ennemy)) {
							moveAgent(ennemy);
							coupLegal=true;
						}
					}
				}
			}
		}
		/* on fait maintenant jouer les bombermans */
		for (Bomberman bomberman : _bombermanList) {
			/* on fait jouer le joueur humain en premier */
			if (bomberman.getId()==0) {
				bomberman.setStrategyAgent(new StrategyBombermanPlayer(_actionJoueur1));
				bomberman.executeAction();
				if(isLegalMove(bomberman)) {
					if (bomberman.getAction()==AgentAction.PUT_BOMB) {
						putBomb(bomberman);
					}
					else {
						moveAgent(bomberman);
					}
				}
				/* si l'action choisit par le joueur humain n'est pas possible, il ne bouge pas */
				else {
					_actionJoueur1='P';
					bomberman.setStrategyAgent(new StrategyBombermanPlayer(_actionJoueur1));
					bomberman.executeAction();
				}
				_actionJoueur1='P';
				bomberman.setStrategyAgent(new StrategyBombermanPlayer(_actionJoueur1));
			}
			/*cas pour autres bombermans PNJ*/
			else {
				Random random = new Random();
				int rand = random.nextInt(100 + 0) + 0;
				/* si l'agent est invincible, on le rend plus aggressif : 1 chance sur 2 de poser une bombe */
				if (bomberman.getIsInvincible() && bomberman.getNbTurnsSpecialState()<16 && rand <50) {
					putBomb(bomberman);
				}
				else {
					boolean coupLegal=false;
					while (!coupLegal) {
						bomberman.setStrategyAgent(new StrategyBombermanClever(this,bomberman));
						bomberman.executeAction();
						if (isLegalMove(bomberman)) {
							coupLegal=true;
							if (bomberman.getAction()==AgentAction.PUT_BOMB) {
								putBomb(bomberman);
							}
							else {
								moveAgent(bomberman);
							}
						}
					}
				}
			}
		}
		/* on verifie si des agents sont morts pendant le tour, et si ils ont ramm */
		agentsKilled();
		for (Bomberman bomberman : _bombermanList) {
			updateItemBomberman(bomberman);
		}
		setMesures(_turn+1);
		_casesUnderExplosion.clear();	
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub
		_listInfoBombs.clear();
		if (_maxturn == _turn) {
			setMesures(-7);
		}
		else {
			switch (_bombermanList.get(0).getColor()) {
			case BLANC:
				setMesures(-1);
				break;
			case BLEU:
				setMesures(-2);
				break;
			case ROUGE:
				setMesures(-3);
				break;
			case JAUNE:
				setMesures(-4);
				break;
			case VERT:
				setMesures(-5);
				break;
			case DEFAULT:
				setMesures(-6);
				break;
				
			}
		}
	}
	
	public boolean isLegalMove(Agent agent) {
		switch (agent.getAction()) {
			case MOVE_UP:
				if (!_map.get_walls()[agent.getX()][agent.getY()-1] && !_breakable_walls[agent.getX()][agent.getY()-1])
					return true;
				else 
					return false;
			case MOVE_DOWN:
				if (!_map.get_walls()[agent.getX()][agent.getY()+1] && !_breakable_walls[agent.getX()][agent.getY()+1])
					return true;
				else 
					return false;
			case MOVE_LEFT:
				if (!_map.get_walls()[agent.getX()-1][agent.getY()] && !_breakable_walls[agent.getX()-1][agent.getY()])
					return true;
				else 
					return false;
			case MOVE_RIGHT:
				if (!_map.get_walls()[agent.getX()+1][agent.getY()] && !_breakable_walls[agent.getX()+1][agent.getY()])
					return true;
				else 
					return false;
			case STOP:
				return true;
			case PUT_BOMB:
				return true;
		}
		return false;
	}
	
	public void moveAgent(Agent agent) {
		switch (agent.getAction()) {
		case MOVE_UP:
			agent.setY(agent.getY()-1);
			break;
		case MOVE_DOWN:
			agent.setY(agent.getY()+1);
			break;
		case MOVE_LEFT:
			agent.setX(agent.getX()-1);
			break;
		case MOVE_RIGHT:
			agent.setX(agent.getX()+1);
			break;
		default:
			break;
		}
	}
	
	public void putBomb(Agent agent) {
		_listInfoBombs.add(new InfoBomb(agent.getX(),agent.getY(),_rayonExplosion,StateBomb.Step0));
	}
	
	public void updateStateBombs () {
		ArrayList<InfoBomb> listInfoBombs = new ArrayList<>();
		for (InfoBomb bombe : _listInfoBombs) {
			boolean delete = false;
			switch (bombe.getStateBomb()) {
			case Step0:
				bombe.setStateBomb(StateBomb.Step1);
				break;
			case Step1:
				bombe.setStateBomb(StateBomb.Step2);
				break;
			case Step2:
				bombe.setStateBomb(StateBomb.Step3);
				break;
			case Step3:
				bombe.setStateBomb(StateBomb.Boom);
				_casesUnderExplosion.add(bombe.getX());
				_casesUnderExplosion.add(bombe.getY());
				for (int i = 1 ; i<= bombe.getRange();++i) {
					_casesUnderExplosion.add(bombe.getX()+i);
					_casesUnderExplosion.add(bombe.getY());
					
					_casesUnderExplosion.add(bombe.getX()-i);
					_casesUnderExplosion.add(bombe.getY());
				
					_casesUnderExplosion.add(bombe.getX());
					_casesUnderExplosion.add(bombe.getY()+i);
					
					_casesUnderExplosion.add(bombe.getX());
					_casesUnderExplosion.add(bombe.getY()-i);
				}
				break;
			case Boom:
				delete = true;
				break;
			}
			if (!delete) {
				listInfoBombs.add(bombe);
			}
		}
		_listInfoBombs = listInfoBombs;
	}
	
	
	public void destroyBreakableWalls (double proba) {
		for (int i = 0; i<_casesUnderExplosion.size();i+=2) {
			if (_casesUnderExplosion.get(i)>=0 && _casesUnderExplosion.get(i)<_map.getSizeX() 
				&& _casesUnderExplosion.get(i+1)>=0 && _casesUnderExplosion.get(i+1)<_map.getSizeY()) {
				if (_breakable_walls[_casesUnderExplosion.get(i)][_casesUnderExplosion.get(i+1)]) {
					_breakable_walls[_casesUnderExplosion.get(i)][_casesUnderExplosion.get(i+1)]=false;
					// le mur est cassé, on génère un nombre aléatoire pour voir si on fait apparaitre un item suivant une probabilité donnée en paramêtre
					Random random = new Random();
					int rand = random.nextInt(100 + 0) + 0;
					// si le nombre généré est dans la probablilité donnée on fait apparaitre un item, avec pour chaques items la chance égale de tomber
					if (rand<=proba*100) {
						int probaItem = random.nextInt(3 + 1) + 1;
						switch (probaItem) {
						case 1:
							_listInfoItems.add(new InfoItem(_casesUnderExplosion.get(i),_casesUnderExplosion.get(i+1),ItemType.FIRE_UP));
							break;
						case 2:
							_listInfoItems.add(new InfoItem(_casesUnderExplosion.get(i),_casesUnderExplosion.get(i+1),ItemType.FIRE_DOWN));
							break;
						case 3:
							_listInfoItems.add(new InfoItem(_casesUnderExplosion.get(i),_casesUnderExplosion.get(i+1),ItemType.FIRE_SUIT));
							break;
						}
					}
				}
			}
		}
		
	}
	
	
	// verifie si des agents sont morts à la fin du tour
	public void agentsKilled () {
		ArrayList<Bomberman> bombermanList = new ArrayList<>();
		for (Bomberman bomberman : _bombermanList) {
			boolean killed = false;
			if (!bomberman.getIsInvincible()) {
				for (EnnemyAgent ennemy : _ennemyList) {
					if (bomberman.getX()==ennemy.getX() && bomberman.getY()==ennemy.getY()) {
						killed = true;
					}
				}
				if (!killed) {
					for (int i = 0;i<_casesUnderExplosion.size();i+=2) {
						if (bomberman.getX()==_casesUnderExplosion.get(i) && bomberman.getY()==_casesUnderExplosion.get(i+1)) {
							killed = true;
						}
					}
				}
			}
			if (!killed) {
				bombermanList.add(bomberman);
			}
			else {
				_listInfoItems.add(new InfoItem(bomberman.getX(),bomberman.getY(),ItemType.SKULL));
			}
		}
		_bombermanList = bombermanList;
		ArrayList<EnnemyAgent> ennemyList = new ArrayList<>();
		for (EnnemyAgent ennemy : _ennemyList) {
			boolean killed = false;
			for (int i = 0;i<_casesUnderExplosion.size();i+=2) {
				if (ennemy.getX()==_casesUnderExplosion.get(i) && ennemy.getY()==_casesUnderExplosion.get(i+1)) {
					killed = true;
				}
			}
			if (!killed) {
				ennemyList.add(ennemy);
			}
			else {
				_listInfoItems.add(new InfoItem(ennemy.getX(),ennemy.getY(),ItemType.SKULL));
			}
		}
		_ennemyList = ennemyList;
	}
	
	public void updateItemBomberman (Bomberman bomberman) {
		//on verifie si l'agent est sous un etat special, si oui un diminue le temps restant d'un tour
		if (bomberman.getIsInvincible()) {
			int turns = bomberman.getNbTurnsSpecialState()-1;
			bomberman.setNbTurnsSpecialState(turns);
			//si l'effet est arrivé au bout on le désactive
			if (bomberman.getNbTurnsSpecialState() == 0) {
				bomberman.setIsInvivncible(false);
			}
		}
		// on verifie si il ramasse un item et si oui, on l'active
		ArrayList<InfoItem> listInfoItems = new ArrayList<>();
		for (InfoItem item : _listInfoItems) {
			boolean delete = false;
			if (bomberman.getX()==item.getX() && bomberman.getY()==item.getY()) {
				switch (item.getType()) {
				case FIRE_UP:
					if (_rayonExplosion<10) {
						_rayonExplosion++;
						for (InfoBomb bombe : _listInfoBombs) {
							bombe.setRange(_rayonExplosion);
						}
					}
					delete = true;
					break;
				case FIRE_DOWN:
					if (_rayonExplosion>1) {
						_rayonExplosion--;
						for (InfoBomb bombe : _listInfoBombs) {
							bombe.setRange(_rayonExplosion);
						}
					}
					delete = true;
					break;
				case FIRE_SUIT:
					bomberman.setIsInvivncible(true);
					bomberman.setNbTurnsSpecialState(20);
					delete = true;
					break;
				default:
					break;
				}
			}
			if (!delete){
				listInfoItems.add(item);
			}
		}
		_listInfoItems=listInfoItems;
	}
	
}
	
