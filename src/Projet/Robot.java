package Projet;

import java.util.ArrayList;
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
	
	protected Environnement myEnv;	
	protected List<String> deplacementsAutorise;
	
	public Robot(Vector3d pos, String name, Environnement ed)
	{
		super(pos, name)   ;
		this.angle    = 0.0;
		this.position = new Point3d();
		this.myEnv    = ed;
		
		this.deplacementsAutorise = new ArrayList<String>();
		this.deplacementsAutorise.add("RIGTH");
		this.deplacementsAutorise.add("LEFT" );
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
	
	protected void setDeplacement(Cherry cherry)
	{
		Point3d pt = new Point3d();
		cherry.getCoords(pt)      ;
		char[][] plateau = this.myEnv.getPlateau();
		int x = (int)Math.round(pt.getX());
		int z = (int)Math.round(pt.getZ());
		//HashMap<Integer, >
		
		boolean bTop, bDown, bLeft, bRight;
		bTop = bDown = bLeft = bRight = false;

		if ( Math.abs(Math.abs(x)-Math.abs(this.position.getX())) < 0.1 && Math.abs(Math.abs(z)-Math.abs(this.position.getZ())) < 0.1 )
		{
			x+=14;
			z+=15;
			
			this.deplacementsAutorise.add("RIGHT");
			this.deplacementsAutorise.add("LEFT" );
			this.deplacementsAutorise.add("DOWN" );
			this.deplacementsAutorise.add("TOP"  );
			
			for ( Cherry c : cherry.getVoisins().keySet() )
			{
				;//if ( )
			}
			// gestion du top
			
			
			// gestion de down

			
			// gestion de left

			
			// gestion de right

			
		}
	}
}
