package fr.bomberman.mathias.modele;
public class SimpleGame extends Game{


	public SimpleGame(int maxturn) {
		super(maxturn);
	}

	public void initializeGame() {
		
	}
	
	public void takeTurn() {
		System.out.println("Tour " + _turn + " du jeu en cours");
		setMesures(_turn+1);
	}


	public void gameOver() {
		System.out.println("le jeu est fini !");
		
	}

	
	public boolean gameContinue() {
		if(_maxturn == _turn) 
			return false;
		else 
			return true;
	}
	
}
