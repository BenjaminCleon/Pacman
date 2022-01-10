package Projet;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GameplayWindow extends JInternalFrame
{
	public GameplayWindow()
	{
		super("Gameplay");
		this.initialize();
	}
	
	private void initialize() 
	{
		JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JTextArea txtGameplay = new JTextArea(this.getGameplay());
		txtGameplay.setEditable(false);
        panel.add(txtGameplay);
        setContentPane(panel);
        setSize(255, 100);
        setResizable(false);
        
        this.setVisible(true);
	}
	
	public String getGameplay()
	{
		String s=   "Déplacez vous grace à ZQSD";
		s += "\n" + "Évitez les fantomes  et"   ;
		s += "\n" + "Mangez toutes les cerises" ;
		s += "\n" + "pour gagner la partie"     ;
		

		return s;
	}

}
