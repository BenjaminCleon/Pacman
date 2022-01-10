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
	
	protected void setDeplacement(Cherry cherry)
	{
		char[][] plateau = this.myEnv.getPlateau();
		double x = cherry.getPosition().getX();
		double z = cherry.getPosition().getZ();
		HashMap<Cherry, Integer> voisins;
		double deltaX = Math.abs(Math.abs(this.position.getX())-Math.abs(x));
		double deltaZ = Math.abs(Math.abs(this.position.getZ())-Math.abs(z));
		
		if ( deltaX < 0.1 && deltaZ < 0.1  )
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
