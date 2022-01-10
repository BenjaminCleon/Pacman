package Projet;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import simbad.sim.CherryAgent;

public class Pacman extends Robot
{
	
	public Pacman(Vector3d pos, String name, Environnement ed)
	{
		super(pos, name, ed);
		this.setColor(new Color3f(255,255,0));
	}
	
	public Cherry getCurrentCherry(){ return this.currentCherry;	}
	
	public void performBehavior()
	{
		this.getCoords(this.position);
		this.setCurrentCherry();
	
		if ( this.anOtherAgentIsVeryNear() && this.getVeryNearAgent() instanceof Cherry )
		{
			((Cherry)this.getVeryNearAgent()).ceuillir();
			this.verificationVictoire();
		}
		
		this.setTranslationalVelocity(4);

		if ( this.currentCherry != null )
		{
			this.setDeplacement(this.currentCherry);
		}
	}
	
	public void verificationVictoire()
	{
		boolean finis = true;
		for(Cherry c : this.myEnv.getCherries())
			if(!c.getCeuillis())
				finis = false;
		if(finis) {this.victoire();}
	}
	public void victoire()
	{
		myEnv.getSimbad().fermer();
		new FrameVictoire();
	}
}
