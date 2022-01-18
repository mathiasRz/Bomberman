package fr.bomberman.mathias.controlleur;
import fr.bomberman.mathias.modele.Game;
import fr.bomberman.mathias.vue.ViewCommand;
import fr.bomberman.mathias.vue.ViewSimpleGame;

public class ControllerSimpleGame extends AbstractController{
	
	public ControllerSimpleGame (Game game){
		super(game);
		ViewSimpleGame viewSimpleGame = new ViewSimpleGame(this.getGame());
		ViewCommand viewCommand = new ViewCommand(this);	
	}
}
