package Projet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.media.j3d.Transform3D;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import simbad.sim.Agent;
import simbad.sim.CherryAgent;

public class Robot extends Agent
{
	protected double  angle   ;
	protected Point3d position;
	protected String    target;
	private int counter;
	
	protected Environnement myEnv;	
	protected List<String> deplacementsAutorise;
	protected Cherry currentCherry;
	
	public Robot(Vector3d pos, String name, Environnement ed)
	{
		super(pos, name)   ;
		this.angle    = 0.0;
		this.position = new Point3d();
		this.myEnv    = ed;
		this.counter = 0;
		
		this.deplacementsAutorise = new ArrayList<String>();
		this.deplacementsAutorise.add("RIGTH");
		this.deplacementsAutorise.add("LEFT" );
		
		this.target = "RIGHT";
		this.setCurrentCherry();
	}
	
	public double getX() { return this.position.getX(); }
	public double getZ() { return this.position.getZ(); }
	
	public void setAngle(String direction)
	{
		Cherry chPacman = this.myEnv.getPacman().getCurrentCherry();
		
		if ( !this.deplacementsAutorise.contains(direction) )return;
		
		while( this.angle != 0 )
		{
			this.angle -= Math.PI/2;
			if ( this.angle > -0.1 && this.angle < 0.1 )this.angle = 0;
			this.rotateY(-Math.PI/2);
		}

		this.target = "RIGHT";
		if ( direction.equals("TOP"  ) ) { this.angle=  Math.PI/2; this.target = "TOP"  ; }
		if ( direction.equals("DOWN" ) ) { this.angle=3*Math.PI/2; this.target = "DOWN" ; }
		if ( direction.equals("LEFT" ) ) { this.angle=  Math.PI  ; this.target = "LEFT" ; }
		
		this.rotateY(this.angle);
	}
	
	protected void setCurrentCherry()
	{
		//this.currentCherry = null;
		int x = (int)Math.round(this.getX());
		int z = (int)Math.round(this.getZ());
		
		if ( x < -14  )x =  -14;
		if ( x > 14   )x = 14;
		
		for ( Cherry c : this.myEnv.getCherries() )
		{
			if (x == (int)Math.round(c.getPosition().getX()) &&
				z == (int)Math.round(c.getPosition().getZ()) )
			{
				this.currentCherry = c;
				break;
			}
		}
	}
	
	protected void TP()
	{
		Point3d pt = null;
		
		Cherry left  = this.myEnv.getChery(13, 0);
		Cherry right = this.myEnv.getChery(13, 27);
		
		if ( this.currentCherry != null && left != null && this.currentCherry.equals(left) && this.target.equals("LEFT") )
			pt = right.getPosition();
			
		if ( this.currentCherry != null && right != null && this.currentCherry.equals(right) && this.target.equals("RIGHT") )
			pt = left.getPosition();
		
		if ( pt != null )this.moveToPosition(pt.getX(), pt.getZ());
	}
	
	protected void setDeplacement(Cherry cherry)
	{
		if ( this.myEnv.getPacman().getCurrentCherry().equals(cherry) ) return;
		
		
		char[][] plateau = this.myEnv.getPlateau();
		double x = cherry.getPosition().getX();
		double z = cherry.getPosition().getZ();
		HashMap<Cherry, Integer> voisins;
		double deltaX = Math.abs(Math.abs(this.position.getX())-Math.abs(x));
		double deltaZ = Math.abs(Math.abs(this.position.getZ())-Math.abs(z));
		
		if ( deltaX < 0.000001 && deltaZ < 0.000001  )
		{
			x+=14;
			z+=15;
			
			this.deplacementsAutorise.clear();
			
			voisins = cherry.getVoisins();
			for ( Cherry c : voisins.keySet() )
			{
				if ( c.getCol() == cherry.getCol() )
				{
					if ( c.getLig() > cherry.getLig() )this.deplacementsAutorise.add("DOWN");
					else                               this.deplacementsAutorise.add("TOP" );
				}
				else
				{
					if ( c.getCol() > cherry.getCol() )this.deplacementsAutorise.add("RIGHT");
					else                               this.deplacementsAutorise.add("LEFT" );
				}
			}
		}
	}
}
