import fr.bomberman.mathias.controlleur.ControllerBombermanGame;
import fr.bomberman.mathias.controlleur.ControllerSimpleGame;
import fr.bomberman.mathias.modele.BombermanGame;
import fr.bomberman.mathias.modele.InputMap;
import fr.bomberman.mathias.modele.SimpleGame;

public class Test {

	public static void main(String[] args) {
		
		try {
			
			InputMap map = new InputMap("./layouts-20211021/niveau2.lay");
			BombermanGame bg = new BombermanGame(50000,map);
			ControllerBombermanGame bomberman = new ControllerBombermanGame(bg);
			/*
			SimpleGame sg = new SimpleGame(15);
			ControllerSimpleGame s = new ControllerSimpleGame(sg);
			sg.launch();
			*/
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
