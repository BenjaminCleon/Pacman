package Projet;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

public class Pinky extends Ghost
{

	public Pinky(Vector3d pos, String name, Environnement ed, Pacman pacHunt)
	{
		super(pos, name, ed, pacHunt);
		this.setColor(new Color3f(0,255,0));
		this.target = "RIGHT";
	}
	
	public void performBehavior()
	{
		this.setTranslationalVelocity(4);
		this.getCoords(this.position);
		
		this.setCurrentCherry();		
		Cherry chPacman = this.myEnv.getPacman().getCurrentCherry();
		Cherry chTarget = null;
		
		System.out.println(this.getPacHunt().target);
		switch ( this.getPacHunt().target )
		{
			case "RIGHT":
			{
				for ( int i=4;i>=0;i--)
				{
					chTarget = this.myEnv.getChery(chPacman.getLig(), chPacman.getCol()+i);
					if (  chTarget != null )break;
				}
				break;
			}
			case "LEFT":
			{
				for ( int i=4;i>=0;i--)
				{
					chTarget = this.myEnv.getChery(chPacman.getLig(), chPacman.getCol()-i);
					if (  chTarget != null )break;
				}
				break;
			}
			case "DOWN":
			{
				for ( int i=4;i>=0;i--)
				{
					chTarget = this.myEnv.getChery(chPacman.getLig()+i, chPacman.getCol());
					if (  chTarget != null )break;
				}
				break;
			}
			case "TOP":
			{
				for ( int i=4;i>=0;i--)
				{
					chTarget = this.myEnv.getChery(chPacman.getLig()-i, chPacman.getCol());
					if (  chTarget != null )break;
				}
				break;
			}
		}
		

		System.out.println(chTarget);
		
		if (  chPacman != null )
		{
			if ( chTarget != null )this.goTo(chTarget);
			else                   this.goTo(chPacman);
		}
	}

}
