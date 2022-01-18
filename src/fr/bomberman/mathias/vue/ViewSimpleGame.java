package fr.bomberman.mathias.vue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import fr.bomberman.mathias.modele.Game;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ViewSimpleGame implements PropertyChangeListener {
	
	Game _game;
	
	JFrame jFrame = new JFrame();
	JLabel label = new JLabel("Tour : 0", JLabel.CENTER);
	
	public ViewSimpleGame (Game game) {
		game.addPropertyChangeListener( this);
		_game = game;
		
		jFrame.setTitle("Game");
		jFrame.setSize(new Dimension(700, 700));
		Dimension windowSize=jFrame.getSize();
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint=ge.getCenterPoint();
		int dx=centerPoint.x - windowSize.width/2+350 ;
		int dy=centerPoint.y - windowSize.height/2-350;
		jFrame.setLocation(dx,dy);
		
        //Ajouter l'étiquette au frame
        jFrame.add(label);
        
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	
	public void propertyChange(PropertyChangeEvent evt) {
		label.setText("Turn : " + Integer.toString((int) evt.getNewValue()));
	}
}
