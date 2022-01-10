package Projet;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

public class Blinky extends Ghost
{	
	public Blinky(Vector3d pos, String name, Environnement ed, Pacman pacHunt)
	{
		super(pos, name, ed, pacHunt);
		this.setColor(new Color3f(255,0,0));
		this.target = "RIGHT";
	}
	
	public void performBehavior()
	{
		super.performBehavior();
		this.cpt++;
		this.setTranslationalVelocity(4);
		this.getCoords(this.position);
		
		this.TP();
		
		if(this.cpt < 500)
		{
			Cherry chPacman = this.myEnv.getPacman().getCurrentCherry();
			if (  chPacman != null )this.goTo(chPacman);
		}
		else
		{
			if(this.cpt > 650)this.cpt = 0;
			else this.goTo(this.myEnv.getChery(1, 1));
		}
	}

}