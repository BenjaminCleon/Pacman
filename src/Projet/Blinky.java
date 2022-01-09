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
		this.setTranslationalVelocity(4);
		this.getCoords(this.position);
		//this.goTo((int)Math.round(this.getPacHunt().getX()), (int)Math.round(this.getPacHunt().getZ()));
	}

}
