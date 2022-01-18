package fr.bomberman.mathias.controlleur;

public class EtatRestart implements EtatController{
	AbstractController _controller;
	
	public EtatRestart (AbstractController controller) {
		this._controller = controller;
		_controller._game.init();
	}
	
	@Override
	public void restart() {
		
	}

	@Override
	public void step() {
		this._controller._game.step();
		_controller.setEtat(new EtatStep(_controller));
		
	}

	@Override
	public void play() {
		_controller._game.launch();
		_controller.setEtat(new EtatPlay(_controller));
		
	}

	@Override
	public void pause() {
		
	}

}
