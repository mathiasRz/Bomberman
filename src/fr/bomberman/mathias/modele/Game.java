package fr.bomberman.mathias.modele;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class Game implements Runnable {
	
	
	//implementation de l'API PropertyChange pour l'ecoute des interfaces graphique sur le jeu
	
	protected final PropertyChangeSupport support = new PropertyChangeSupport(this);
    
	public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        support.addPropertyChangeListener( property, listener);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(String property, PropertyChangeListener listener) {
        support.removePropertyChangeListener(property, listener);
    }
	
    public void setMesures(int turn) {
		support.firePropertyChange("_turn", this._turn, turn);
		this._turn = turn;
	}
    
	//gere la vitesse de deroulement du jeu
	protected Thread _thread;
	
	//temps entre chaque tour en ms
	private static long _turnTime = 500;
	
	//comptage du nombre de tours
	protected int _turn;
	
	//maximum de tour possible
	protected int _maxturn;
	
	//savoir si le jeu est en pause
	protected boolean _isRunning;
	
	public Game(int maxturn){
		_maxturn = maxturn;
		_turn = 0;
	}
	
	public int getTurn () {
		return _turn;
	}
	
	public void setIsRunning(boolean isRunning) {
		_isRunning = isRunning;
	}
	
	//initialise le jeu
	public final void init() {
		_isRunning = true;
		initializeGame();
	}
	
	//fait evoluer les parametres du jeu à chaques tours
	public final void step() {
		if (gameContinue()) {
			takeTurn();
		}
		else {
			_isRunning =false;
			gameOver();
		}
	}
	
	//met le jeu en pause
	public final void pause() {
		_isRunning=false;
	}
	
	//fait tourner le jeu
	public final void run () {
		while (_isRunning) {
			step();
			try {
				Thread.sleep(_turnTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public final void launch() {
		_isRunning = true;
		_thread = new Thread(this);
		_thread.start();
	}
	
	//initialise le jeu specifiquement
	public abstract void initializeGame();
	
	//permet de savoir si le jeu est finit
	public abstract boolean gameContinue();
	
	//deroulement d'un tour
	public abstract void takeTurn();
	
	//parametrage de la fin de partie
	public abstract void gameOver();
	
	static public void setTurnTIme (int time) {
		_turnTime = time;
	}
	
}
