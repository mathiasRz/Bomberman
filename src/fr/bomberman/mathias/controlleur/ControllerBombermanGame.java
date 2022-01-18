package fr.bomberman.mathias.controlleur;
import java.util.ArrayList;

import fr.bomberman.mathias.modele.BombermanGame;
import fr.bomberman.mathias.modele.Game;
import fr.bomberman.mathias.modele.InputMap;
import fr.bomberman.mathias.utils.InfoAgent;
import fr.bomberman.mathias.vue.PanelBomberman;
import fr.bomberman.mathias.vue.ViewBombermanGame;
import fr.bomberman.mathias.vue.ViewCommand;

public class ControllerBombermanGame extends AbstractController {
	
	
	
	public ControllerBombermanGame (BombermanGame game) throws Exception {
		
		super(game);
		
		int sizeX = game.getMap().getSizeX();
		int sizeY = game.getMap().getSizeY();
		boolean[][] start_breakable_walls = game.getMap().getStart_breakable_walls();
		boolean[][] walls = game.getMap().get_walls();
		ArrayList<InfoAgent> start_agents = game.getMap().getStart_agents();
		
		PanelBomberman panel = new PanelBomberman (sizeX, sizeY, walls, start_breakable_walls, start_agents);
		
		ViewBombermanGame vue = new ViewBombermanGame(panel,game);
		ViewCommand viewCommand = new ViewCommand(this);
		
	}
}
