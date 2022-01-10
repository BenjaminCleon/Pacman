package Projet;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Font;
import java.awt.GridLayout;

public class FrameFinDePartie extends JFrame implements ActionListener
{
	
	private JButton btnRestart;
	
	public FrameFinDePartie(boolean aGagne)
	{
		if(aGagne)
			this.setTitle("Victoire");
		else
			this.setTitle("Défaite");
		this.setLocation(50,50);
		this.setSize(400, 150);
		this.setLayout(new GridLayout(2,1,2,2));
		this.setResizable(false);
		
		Font font = new Font("Monospaced", Font.BOLD, 25);
		
		/*  Création des composants*/
		String message;
		if(aGagne)message = "Victoire !";
		else      message = "Défaite :(";
		this.btnRestart = new JButton("Recommencer");
		JLabel lab = new JLabel(message, JLabel.CENTER);
		lab.setFont(font);
		
		/*     Ajout des composants*/
		this.add(lab);
		this.add(btnRestart);
		
		
		/*Activation des composants*/
		this.btnRestart.addActionListener(this);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		//simbad.gui.Simbad.fermer();
		this.dispose();
		Environnement.main(null);
		
	}

}
