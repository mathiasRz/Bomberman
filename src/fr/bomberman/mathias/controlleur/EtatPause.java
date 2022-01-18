package fr.bomberman.mathias.controlleur;

public class EtatPause implements EtatController{
	AbstractController _controller;
	
	public EtatPause (AbstractController controller) {
		this._controller = controller;
	}
	
	@Override
	public void restart() {
		_controller.setEtat(new EtatRestart(_controller));
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