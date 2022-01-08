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
	
	private   Environnement myEnv;	
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
	
	public void setAngle(String direction)
	{
		if ( !this.deplacementsAutorise.contains(direction) )return;
		
		while( this.angle != 0 )
		{
			this.angle -= Math.PI/2;
			if ( this.angle > -0.1 && this.angle < 0.1 )this.angle = 0;
			this.rotateY(-Math.PI/2);
		}

		if ( direction.equals("TOP"  ) ) { this.angle=  Math.PI/2; }
		if ( direction.equals("DOWN" ) ) { this.angle=3*Math.PI/2; }
		if ( direction.equals("LEFT" ) ) { this.angle=  Math.PI  ; }
		
		this.rotateY(this.angle);
	}
	
	protected void setDeplacement(CherryAgent cherry)
	{
		Point3d pt = new Point3d();
		cherry.getCoords(pt)      ;
		char[][] plateau = this.myEnv.getPlateau();
		int x = (int)Math.round(pt.getX());
		int z = (int)Math.round(pt.getZ());

		if ( Math.abs(Math.abs(x)-Math.abs(this.position.getX())) < 0.11 && Math.abs(Math.abs(z)-Math.abs(this.position.getZ())) < 0.11 )
		{
			x+=14;
			z+=15;
			
			this.deplacementsAutorise.add("RIGHT");
			this.deplacementsAutorise.add("LEFT" );
			this.deplacementsAutorise.add("DOWN" );
			this.deplacementsAutorise.add("TOP"  );
			
			// gestion du top
			for (int i=z;i>-1;i--)
			{
				if ( plateau[i][x] == 'N' )break;
				if ( plateau[i][x] == 'B' )this.deplacementsAutorise.remove("TOP"  );
			}
			
			// gestion de down
			for (int i=z;i<Environnement.NB_LIG;i++)
			{
				if ( plateau[i][x] == 'N' )break;
				if ( plateau[i][x] == 'B' )this.deplacementsAutorise.remove("DOWN" );
			}
			
			// gestion de left
			for (int i=x;i>-1;i--)
			{
				if ( plateau[z][i] == 'N' )break;
				if ( plateau[z][i] == 'B' )this.deplacementsAutorise.remove("LEFT" );
			}
			
			// gestion de right
			for (int i=x;i<Environnement.NB_COL;i++)
			{
				if ( plateau[z][i] == 'N' )break;
				if ( plateau[z][i] == 'B' )this.deplacementsAutorise.remove("RIGHT");
			}
			
			this.setTranslationalVelocity(0);
		}
	}
}
