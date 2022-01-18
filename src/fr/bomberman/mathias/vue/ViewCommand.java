package fr.bomberman.mathias.vue;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.bomberman.mathias.controlleur.AbstractController;
import fr.bomberman.mathias.controlleur.EtatPause;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ViewCommand implements PropertyChangeListener,ChangeListener{
	
	private AbstractController controller;
	
	JFrame jFrame = new JFrame();
	JPanel panel = new JPanel();
	
	JPanel panelTop = new JPanel();
	Icon restartIcon= new ImageIcon ("icons/icon_restart.png");
	JButton restartButton= new JButton(restartIcon);
	Icon playIcon= new ImageIcon ("icons/icon_play.png");
	JButton playButton= new JButton(playIcon);
	Icon stepIcon= new ImageIcon ("icons/icon_step.png");
	JButton stepButton= new JButton(stepIcon);
	Icon pauseIcon= new ImageIcon ("icons/icon_pause.png");
	JButton pauseButton= new JButton(pauseIcon);
	
	JPanel panelBtm = new JPanel();
	JLabel turn = new JLabel("Turn : 0",JLabel.CENTER); 
	JPanel panelBtmA = new JPanel();
	JLabel label = new JLabel("Number of turns per second", JLabel.CENTER);
	JSlider slider = new JSlider(0, 10, 1);
	
	public ViewCommand (AbstractController controller) {
		this.controller = controller;
		controller.getGame().addPropertyChangeListener("_turn", this);
		restartButton.setEnabled(false);
		stepButton.setEnabled(true);
		playButton.setEnabled(true);
		pauseButton.setEnabled(false);
		restartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				restartButton.setEnabled(false);
				stepButton.setEnabled(true);
				playButton.setEnabled(true);
				pauseButton.setEnabled(false);
				controller.restart();
			}
		});
		
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				restartButton.setEnabled(false);
				stepButton.setEnabled(false);
				playButton.setEnabled(false);
				pauseButton.setEnabled(true);
				controller.play();
			}
		});
		
		stepButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				pauseButton.setEnabled(false);
				controller.step();
			}
		});
		
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				restartButton.setEnabled(true);
				stepButton.setEnabled(true);
				playButton.setEnabled(true);
				pauseButton.setEnabled(false);
				controller.pause();
			}
		});
		
		jFrame.setTitle("Game");
		jFrame.setSize(new Dimension(700, 700));
		Dimension windowSize=jFrame.getSize();
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		jFrame.setLocation(100,100);
		
	    jFrame.setContentPane(panel);
	    panel.setLayout(new GridLayout(2,1));
	    
	    panelTop.setLayout(new GridLayout(1,4));
		
		panelTop.add(restartButton);
		panelTop.add(playButton);
		panelTop.add(stepButton);
		panelTop.add(pauseButton);
		
		panelBtm.setLayout(new GridLayout(1,2));
		
		panelBtmA.setLayout(new GridLayout(2,1));
		 
        slider.setPaintTrack(true); 
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        // Définir l'espacement
        slider.setMajorTickSpacing(1); 
        slider.setMinorTickSpacing(1); 
        
        slider.addChangeListener(this);
  
		
		panelBtmA.add(label);
		panelBtmA.add(slider);
		
		panelBtm.add(panelBtmA);
		panelBtm.add(turn);
		
		panel.add(panelTop);
		panel.add(panelBtm);
		
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void stateChanged(ChangeEvent e) 
    { 
        controller.getGame().setTurnTIme((10-slider.getValue())*100);
    }
	
	
	public void propertyChange(PropertyChangeEvent evt) {
		switch ((int)evt.getNewValue()) {
			case -1 :
				turn.setText("Partie terminée ! Le joueur BLANC a gagné !");
				restartButton.setEnabled(true);
				stepButton.setEnabled(false);
				playButton.setEnabled(false);
				pauseButton.setEnabled(false);
				this.controller.setEtat(new EtatPause(controller));
				break;
			case -2 :
				turn.setText("Partie terminée ! Le joueur BLEU a gagné !");
				restartButton.setEnabled(true);
				stepButton.setEnabled(false);
				playButton.setEnabled(false);
				pauseButton.setEnabled(false);
				this.controller.setEtat(new EtatPause(controller));
				break;
			case -3 :
				turn.setText("Partie terminée ! Le joueur ROUGE a gagné !");
				restartButton.setEnabled(true);
				stepButton.setEnabled(false);
				playButton.setEnabled(false);
				pauseButton.setEnabled(false);
				this.controller.setEtat(new EtatPause(controller));
				break;
			case -4 :
				turn.setText("Partie terminée ! Le joueur JAUNE a gagné !");
				restartButton.setEnabled(true);
				stepButton.setEnabled(false);
				playButton.setEnabled(false);
				pauseButton.setEnabled(false);
				this.controller.setEtat(new EtatPause(controller));
				break;
			case -5 :
				turn.setText("Partie terminée ! Le joueur VERT a gagné !");
				restartButton.setEnabled(true);
				stepButton.setEnabled(false);
				playButton.setEnabled(false);
				pauseButton.setEnabled(false);
				this.controller.setEtat(new EtatPause(controller));
				break;
			case -6 :
				turn.setText("Partie terminée ! Le joueur COULEUR PAR DEFAULT a gagné !");
				restartButton.setEnabled(true);
				stepButton.setEnabled(false);
				playButton.setEnabled(false);
				pauseButton.setEnabled(false);
				this.controller.setEtat(new EtatPause(controller));
				break;
			case -7 :
				turn.setText("Partie terminée ! Limite de tour maximum atteinte, pas de gagnant !");
				restartButton.setEnabled(true);
				stepButton.setEnabled(false);
				playButton.setEnabled(false);
				pauseButton.setEnabled(false);
				this.controller.setEtat(new EtatPause(controller));
				break;
			default:
				turn.setText("Turn : " + Integer.toString((int) evt.getNewValue()));
				break;
		}
	}
}
