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
	
	public void performBehavior()
	{
		this.getCoords(this.position);
		
		if ( this.anOtherAgentIsVeryNear() &&
			 this.getVeryNearAgent() instanceof CherryAgent &&
			 !(this.getVeryNearAgent() instanceof Node) )
		{
			this.getVeryNearAgent().detach();
		}
		
		if ( this.anOtherAgentIsVeryNear() && this.getVeryNearAgent() instanceof Node )
		{
			this.setDeplacement((CherryAgent)this.getVeryNearAgent());
		}
		else
		{
			if ( this.angle == 0 || this.angle > Math.PI -0.1 && this.angle < Math.PI + 0.1 )
			{
				this.deplacementsAutorise.remove("DOWN");
				this.deplacementsAutorise.remove("TOP" );
			}
			
			if ( this.angle >   Math.PI/2 -0.1 && this.angle <   Math.PI/2 + 0.1 || 
				 this.angle > 3*Math.PI/2 -0.1 && this.angle < 3*Math.PI/2 + 0.1)
			{
				this.deplacementsAutorise.remove("RIGHT");
				this.deplacementsAutorise.remove("LEFT" );
			}
			
			this.setTranslationalVelocity(4);
		}
	}
}
