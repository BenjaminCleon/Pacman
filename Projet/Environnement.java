package Projet;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import simbad.gui.Simbad;
import simbad.sim.Agent;
import simbad.sim.Arch;
import simbad.sim.BallAgent;
import simbad.sim.Box;
import simbad.sim.EnvironmentDescription;
import simbad.sim.Wall;
import simbad.sim.World;

public class Environnement extends EnvironmentDescription
{
	public Environnement()
	{
		this.worldSize = 32;
		this.worldViewPoint = World.VIEW_FROM_TOP;
		this.boxColor = new Color3f(0, 0, 255);
		
		Agent ag = new Agent(new Vector3d(0, 0, -2.75), "Pac");
		ag.setColor(new Color3f(125, 125, 0));
		this.add(ag);
		
		// Ajout des bord en haut et en bas
		this.addWall(-7.5, 0.0, -16.0, 13.0, 0);
		this.addWall( 7.5, 0.0, -16.0, 13.0, 0);
		this.addWall( 0.0, 0.0,  16.0, 28.0, 0);
		
		
		// Ajout des bord sur les côtés
		this.addWall(  14.0, 0.0, -11, 10.0, 1);
		this.addWall( -14.0, 0.0, -11, 10.0, 1);
		this.addWall(  14.0, 0.0,  8 ,  4.0, 1);
		this.addWall( -14.0, 0.0,  8 ,  4.0, 1);
		this.addWall(  14.0, 0.0,  14,  4.0, 1);
		this.addWall( -14.0, 0.0,  14,  4.0, 1);
		
		// symétrie bordure droite gauche haut U retourné
		this.addWall(  11.5, 0, -6   , 6  , 0);
		this.addWall( -11.5, 0, -6   , 6  , 0);
		this.addWall(  8.5,  0, -3.75, 4.5, 1);
		this.addWall( -8.5,  0, -3.75, 4.5, 1);
		this.addWall(  11.5, 0, -1.5 , 6  , 0);
		this.addWall( -11.5, 0, -1.5 , 6  , 0);
		
		// symétrie bordure droite gauche haut U retourné
		this.addWall(  11.5, 0,  6   , 6  , 0);
		this.addWall( -11.5, 0,  6   , 6  , 0);
		this.addWall(  8.5,  0,  3.75, 4.5, 1);
		this.addWall( -8.5,  0,  3.75, 4.5, 1);
		this.addWall(  11.5, 0,  1.5 , 6  , 0);
		this.addWall( -11.5, 0,  1.5 , 6  , 0);
		
		// fermeture du terrain
		this.addWall(  1 ,0 , -13.5, 5 ,1 );
		this.addWall( -1 ,0 , -13.5, 5 ,1 );
		this.addWall(  0 ,0 , -11 , 2 ,0 );
		
		//this.addWall( 12.5 ,0 , 10, 3 ,0 );
		this.addWall( 12   ,0 , 11, 2 ,0 );
		//this.addWall( 12.5 ,0 , 12, 3 ,0 );
		
		this.addWall( -12.5 ,0 , 10.5, 3  ,0 );
		this.addWall( -11   ,0 , 11  , 1.4,1 );
		this.addWall( -12.5 ,0 , 11.5, 3  ,0 );
		
		// ajout des 4 boxs en haut
		this.addBox(  10, 0, -13, 3.75, 1, 3);
		this.addBox( -10, 0, -13, 3.75, 1, 3);
		this.addBox(  4.5, 0, -13, 4.25, 1, 3);
		this.addBox( -4.5, 0, -13, 4.25, 1, 3);
		
		// ajout des deux boxes horizontales en dessous des précédentes
		this.addBox(  10, 0, -8.5, 3.75, 1, 2);
		this.addBox( -10, 0, -8.5, 3.75, 1, 2);
		
		// ajout du T centrale en haut
		this.addBox( 0, 0, -8.5, 6, 1, 2);
		this.addBox( 0, 0, -6  , 2, 1, 5);
		
		// ajout des T encadrant le T en haut
		this.addBox( -5.5, 0, -5.25, 2, 1, 8.5);
		this.addBox( -4, 0, -4.5, 3, 1, 2);
		this.addBox( 5.5, 0, -5.25, 2, 1, 8.5);
		this.addBox( 4, 0, -4.5, 3, 1, 2);
		
		// ajout de la cage à ghost
		this.addBox( 0, 0, 0, 6, 1, 4);
		
		// ajout des 2 T du dessous
		this.addBox( 0, 0, 4.5, 6, 1, 2);
		this.addBox( 0, 0, 7, 2, 1, 5);
		this.addBox( 0, 0, 12, 6, 1, 2);
		this.addBox( 0, 0, 13.5, 2, 1, 2);
		
		// ajout des barres en faces des U du bas
		this.addBox( -5.5, 0, 3.625, 2, 1, 4.5);
		this.addBox(  5.5, 0, 3.625, 2, 1, 4.5);
		
		// ajout des barres horizontales à coté du T entre les 2 T
		this.addBox(  4.5, 0, 8.5, 4.25, 1, 2);
		this.addBox( -4.5, 0, 8.5, 4.25, 1, 2);
		
		// ajout des L retournés bas droite gauche
		this.addBox(  -10.5, 0, 8.5, 3.75, 1, 2);
		this.addBox(   10.5, 0, 8.5, 3.75, 1, 2);
	}
	
	private void addWall(double d, double e, double f, double g, int rota)
	{
		Wall w = new Wall(new Vector3d((float)d, (float)e, (float)f), (float)g , 1, this);
		w.rotate90(rota);
		this.add(w);
	}
	
	private void addBox(double x, double y, double z, double a, double b, double c)
	{
		Box box = new Box(new Vector3d(x, y, z), new Vector3f((float)a, (float)b, (float)c), this);
		this.add(box);
	}
	
	public static void main(String[] args)
	{
		new Simbad(new Environnement(), false);
	}

}
