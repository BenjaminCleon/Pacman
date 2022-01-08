package Projet;

import javax.vecmath.Vector3d;

import simbad.sim.Agent;

public class Robot extends Agent
{
	private double angle;
	
	public Robot(Vector3d pos, String name)
	{
		super(pos, name);
		this.angle = 0;
	}
	
	public void setAngle(String direction)
	{
		this.angle%=2*Math.PI;
		switch ( direction )
		{
			case "TOP"   : { while(this.angle!=  Math.PI/2){ this.angle+=Math.PI/2;this.rotateY(Math.PI/2);} break; }
			case "DOWN"  : { while(this.angle!=3*Math.PI/2){ this.angle+=Math.PI/2;this.rotateY(Math.PI/2);} break; }
			case "LEFT"  : { while(this.angle!=2*Math.PI/2){ this.angle+=Math.PI/2;this.rotateY(Math.PI/2);} break; }
			case "RIGHT" : { while(this.angle!=4*Math.PI/2){ this.angle+=Math.PI/2;this.rotateY(Math.PI/2);} break; }
		}
	}
	
	@Override
	public void performBehavior()
	{
		this.setTranslationalVelocity(5);
	}
}
