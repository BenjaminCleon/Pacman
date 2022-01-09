package Projet;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import simbad.sim.CherryAgent;

public class Pacman extends Robot
{
	private Cherry currentCherry;
	
	public Pacman(Vector3d pos, String name, Environnement ed)
	{
		super(pos, name, ed);
		this.setColor(new Color3f(255,255,0));
		this.setCurrentCherry();
	}
	
	private void setCurrentCherry()
	{
		this.currentCherry = null;
		for ( Cherry c : this.myEnv.getCherries() )
		{
			if ( (int)Math.round(this.getX()) == (int)Math.round(c.getPosition().getX()) &&
				 (int)Math.round(this.getZ()) == (int)Math.round(c.getPosition().getZ()) )
			{
				this.currentCherry = c;
				break;
			}
		}
	}
	
	public void performBehavior()
	{		
		this.getCoords(this.position);
		this.setCurrentCherry();
		
		if ( this.anOtherAgentIsVeryNear() && this.getVeryNearAgent() instanceof Cherry )
			this.getVeryNearAgent().detach();
		
		this.setTranslationalVelocity(4);

		if ( this.currentCherry != null )this.setDeplacement(this.currentCherry);
	}
}
