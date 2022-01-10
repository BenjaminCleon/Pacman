package Projet;

import java.io.FileInputStream;
import java.util.Scanner;
import java.util.List   ;
import java.util.ArrayList;
import java.util.HashMap;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import simbad.gui.Simbad;
import simbad.sim.Box;
import simbad.sim.CherryAgent;
import simbad.sim.EnvironmentDescription;
import simbad.sim.Wall;
import simbad.sim.World;

public class Environnement extends EnvironmentDescription
{
	static final int NB_LIG = 30;
	static final int NB_COL = 28;
	
	private char[][] plateau;
	private List<Cherry> nodes;
	
	private List<Cherry> cherries;
	
	private Pacman pacman;
	private Blinky blinky;
	private Pinky  pinky ;
	private Clyde  clyde ;
	private Inky   inky  ;
	
	public Environnement()
	{
		String file = "./data/configSansTP.txt";
		//String file = "./data/config2.txt";
		//String file = "./data/config.txt";
		
		this.boxColor         = new Color3f(0,0,255);
		this.worldViewPoint   = World.VIEW_FROM_TOP ;
		this.worldSize        = 32                  ;
		
		this.plateau  = new char[Environnement.NB_LIG][Environnement.NB_COL];
		this.cherries = new ArrayList<Cherry>();
		
		this.pacman = new Pacman(new Vector3d(-0.5,0, 7), "pacman", this);
		this.add(this.pacman);
		
		if ( !file.equals("./data/config2.txt") )
		{
			this.blinky = new Blinky(new Vector3d( 1,0,-5), "blinky", this, pacman);		
			this.pinky  = new Pinky (new Vector3d(-2,0,-5), "blinky", this, pacman);
			this.clyde  = new Clyde (new Vector3d(-5,0,-5), "blinky", this, pacman);
			this.inky   = new Inky  (new Vector3d( 4,0,-5), "blinky", this, pacman);
		
			this.add(this.blinky);
			this.add(this.pinky);
			this.add(this.clyde);
			this.add(this.inky );
		}
		
		String sLigneActuelle = "";
		int iZ = -15;
		
		try
		{
			Scanner sc = new Scanner(new FileInputStream(file), "UTF8");
			
			while((sLigneActuelle=sc.nextLine())!=null)
			{
				for ( int i = 0;i<sLigneActuelle.length();i++)
				{
					switch(sLigneActuelle.charAt(i)  )
					{
						case 'B' : this.add(new Box(new Vector3d(i-14, 0, iZ), new Vector3f(1, 1, 1), this )); break;
						case 'C' :
						{
							Cherry cher = new Cherry(new Vector3d(i-14, 0, iZ), "Cherry", 0.2f, iZ+15, i, this);
							cher.setColor(new Color3f(255,255,255));
							this.cherries.add(cher);
							this.add(cher);break;
						}
					}
					this.plateau[iZ+15][i] = sLigneActuelle.charAt(i);
				}
				
				iZ++;
			}

			sc.close();
		}catch(Exception e) {e.printStackTrace(); }
		
		for ( Cherry n : this.cherries )n.setVoisins();
		
		if ( file.equals("./data/config.txt") )
		{
			this.getChery(13, 27).setVoisins(this.getChery(13, 0));
			this.getChery(13,  0).setVoisins(this.getChery(13, 27));
		}
	}
	
	public char[][] getPlateau(){ return this.plateau; }
	public Cherry getChery(int x, int z)
	{
		Cherry chRet = null;
		
		if ( x >= 0 && x < Environnement.NB_LIG && z >= 0 && z <  Environnement.NB_COL && this.plateau[x][z] == 'C' )
			for ( Cherry c : this.cherries )
				if ( c.getCol() == z && c.getLig() == x )chRet = c;
		
		return chRet;
	}

	public List<Cherry> getCherries(){return this.cherries; }

	public boolean estGagner()
	{
		return false;
	}
	
	public static void main(String[] args)
	{
		new Simbad(new Environnement(), false);
	}

	public Pacman getPacman()
	{
		return this.pacman;
	}
}
