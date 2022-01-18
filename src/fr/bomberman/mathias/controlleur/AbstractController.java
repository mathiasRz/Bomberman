package fr.bomberman.mathias.controlleur;
import fr.bomberman.mathias.modele.Game;

public abstract class AbstractController {

	protected Game _game;
	private EtatController _etat;
	
	public AbstractController (Game game) {
		_game = game;
		_etat = new EtatRestart(this);
	}
	
	public void setEtat (EtatController etat) {
		_etat = etat;
	}
	
	public void restart() {
		_etat.restart();
	}
	
	public void step() {
		_etat.step();
	}
	
	public void play() {
		_etat.play();
	}
	
	public void pause() {
		_etat.pause();
	}
	
	public void setSpeed(double speed) {
		
	}
	
	public Game getGame() {
		return _game;
	}
	

}
