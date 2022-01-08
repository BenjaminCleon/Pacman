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
	
	private Pacman pacman;
	
	public Environnement()
	{
		this.boxColor         = new Color3f(0,0,255);
		this.worldViewPoint   = World.VIEW_FROM_TOP ;
		this.worldSize        = 32                  ;
		
		this.plateau = new char[Environnement.NB_LIG][Environnement.NB_COL];
		
		this.pacman = new Pacman(new Vector3d(-0.5,0,7), "pacman", this);

		this.add(this.pacman);
		
		String sLigneActuelle = "";
		int iZ = -15;
		
		try
		{
			Scanner sc = new Scanner(new FileInputStream("./data/config.txt"), "UTF8");
			
			while((sLigneActuelle=sc.nextLine())!=null)
			{
				for ( int i = 0;i<sLigneActuelle.length();i++)
				{
					switch(sLigneActuelle.charAt(i)  )
					{
						case 'B' : this.add(new Box(new Vector3d(i-14, 0, iZ), new Vector3f(1, 1, 1), this )); break;
						case 'C' :
						{
							CherryAgent cher = new CherryAgent(new Vector3d(i-14, 0, iZ), "Cherry", 0.2f, iZ+15, i);
							cher.setColor(new Color3f(255,255,255));
							this.add(cher);break;
						}
						case 'N' : this.add(new Node(new Vector3d(i-14, 0, iZ), "Cherry", 0.2f, iZ+15, i));break;
					}
					this.plateau[iZ+15][i] = sLigneActuelle.charAt(i);
				}
				
				iZ++;
			}

			sc.close();
		}catch(Exception e) {e.printStackTrace(); }
	}
	
	public char[][] getPlateau(){ return this.plateau; }

	public boolean estGagner()
	{
		return false;
	}
	
	public static void main(String[] args)
	{
		new Simbad(new Environnement(), false);
	}
}
