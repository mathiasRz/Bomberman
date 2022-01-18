package fr.bomberman.mathias.controlleur;

public class EtatPlay implements EtatController{
	AbstractController _controller;
	
	public EtatPlay (AbstractController controller) {
		this._controller = controller;
	}
	
	@Override
	public void restart() {
		
	}

	@Override
	public void step() {
		
	}

	@Override
	public void play() {
		
	}

	@Override
	public void pause() {
		_controller._game.pause();
		_controller.setEtat(new EtatPause(_controller));
	}

}
