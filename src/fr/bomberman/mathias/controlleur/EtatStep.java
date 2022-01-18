package fr.bomberman.mathias.controlleur;

public class EtatStep implements EtatController{
	AbstractController _controller;
	
	public EtatStep (AbstractController controller) {
		this._controller = controller;
	}
	
	@Override
	public void restart() {
		_controller.setEtat(new EtatRestart(_controller));
	}

	@Override
	public void step() {
		_controller._game.step();
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
