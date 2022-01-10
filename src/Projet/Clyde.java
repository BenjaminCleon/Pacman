package Projet;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

public class Clyde extends Ghost
{

	public Clyde(Vector3d pos, String name, Environnement ed, Pacman pacHunt)
	{
		super(pos, name, ed, pacHunt);
		this.setColor(new Color3f(0,255,0));
		this.target = "RIGHT";

	}
	
	public void performBehavior()
	{		
		this.cpt++;
		this.setTranslationalVelocity(4);
		this.getCoords(this.position);
		this.TP();
		if(this.cpt < 500)
		{
			Cherry chPacman = this.getPacHunt().getCurrentCherry();
			
			// calcul de la distance entre pacman et clyde
			double distance = Math.sqrt(Math.pow(this.getX()-this.getPacHunt().getX(), 2) + Math.pow(this.getZ()-this.getPacHunt().getZ(), 2));
			
			 // S'il n'est pas dans un rayon de 4 autour de pacman alors il va vers lui
			if (  distance > 8 && chPacman != null )this.goTo(chPacman);
			// sinon il va dans le coin inférieur gauche
			else                                    this.goTo(this.myEnv.getChery(28, 1));
		}
		else
		{
			if(this.cpt > 650)this.cpt = 0;
			else this.goTo(this.myEnv.getChery(28, 1));
		}
	}

}
