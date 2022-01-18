package fr.bomberman.mathias.vue;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import fr.bomberman.mathias.modele.BombermanGame;
import fr.bomberman.mathias.utils.InfoAgent;
import fr.bomberman.mathias.utils.InfoBomb;
import fr.bomberman.mathias.utils.InfoItem;

public class ViewBombermanGame implements PropertyChangeListener,KeyListener{
	
	private static int indice_elts_update = 0;
	private boolean[][] _breakable_walls;
	private ArrayList<InfoAgent> _agents;
	private ArrayList<InfoItem> _items;
	private ArrayList<InfoBomb> _bombes;
	
	private BombermanGame _bg;
	
	PanelBomberman panel;
	JFrame jFrame = new JFrame();
	
	public ViewBombermanGame (PanelBomberman panelBG,BombermanGame bg) {
		this._bg = bg;
		_bg.addPropertyChangeListener("liste murs cassables",this);
		_bg.addPropertyChangeListener("liste agents",this);
		_bg.addPropertyChangeListener("liste items", this);
		_bg.addPropertyChangeListener("liste bombes", this);
		this.panel=panelBG;
		jFrame.addKeyListener(this);
		jFrame.setContentPane(panel);
		jFrame.setTitle("Bomberman game");
		jFrame.setSize(new Dimension(panel.getSizeX()*50, panel.getSizeY()*50));
		Dimension windowSize=jFrame.getSize();
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint=ge.getCenterPoint();
		int dx=centerPoint.x - windowSize.width/2+(panel.getX()/2);
		int dy=centerPoint.y - windowSize.height/2-(panel.getY()/2);
		jFrame.setLocation(dx,dy);
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		switch (indice_elts_update) {
			case 0:
				_breakable_walls = (boolean[][]) evt.getNewValue();
				++indice_elts_update;
				break;
			case 1:
				_agents = (ArrayList<InfoAgent>) evt.getNewValue();
				++indice_elts_update;
				break;
			case 2:
				_items = (ArrayList<InfoItem>) evt.getNewValue();
				++indice_elts_update;
				break;
			case 3:
				_bombes = (ArrayList<InfoBomb>) evt.getNewValue();
				panel.updateInfoGame(_breakable_walls, _agents, _items, _bombes);
				indice_elts_update = 0;
				jFrame.repaint();
				break;
		}
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		_bg.setActionJoueur1((char) e.getKeyCode());
			
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
