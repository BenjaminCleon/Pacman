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
	private List<Node> lstNode;
	
	public Environnement()
	{
		this.worldSize = 32;
		this.worldViewPoint = World.VIEW_FROM_TOP;
		this.boxColor = new Color3f(0, 0, 255);
		
		this.lstNode = new ArrayList<Node>();
		
		Pacman pac = new Pacman(new Vector3d(0, 0, -2.75), "Pacman");
		pac.setColor(new Color3f(125, 125, 0));
		this.add(pac);
		
		String   line;
		String[] lineSplit;
		
		try
		{
			Scanner sc = new Scanner(new FileInputStream("./data/config.txt"), "UTF8");
			
			while(sc.hasNextLine())
			{
				line      = sc.nextLine()  ;
				lineSplit = (line.split(";"))[0].split(",");
				
				switch(lineSplit[0])
				{
					case "B": { this.addBox(Double.parseDouble(lineSplit[1]),
							                Double.parseDouble(lineSplit[2]),
							                Double.parseDouble(lineSplit[3]),
							                Double.parseDouble(lineSplit[4]),
							                Double.parseDouble(lineSplit[5]),
							                Double.parseDouble(lineSplit[6]));
					             break;
					           }
					case "W": { this.addWall(Double.parseDouble(lineSplit[1]),
                			                 Double.parseDouble(lineSplit[2]),
                			                 Double.parseDouble(lineSplit[3]),
 							                 Double.parseDouble(lineSplit[4]),
 							                 Integer.parseInt  (lineSplit[5]));
					             break;
					          }
					case "N": { this.addNode(Double.parseDouble(lineSplit[1]),
                							 Double.parseDouble(lineSplit[2]),
                							 Double.parseDouble(lineSplit[3]),
                							                    lineSplit[4] ,
                							 Float.parseFloat(lineSplit[5]  ),
                							 ((line.split(";"))[1]).split(","));
						         break;
						       }
				}
			}
			
		}catch(Exception e) { e.printStackTrace(); }
	
		this.chargerNodeVoisin();
	}
	
	/**
	 * Permet d'ajouter un mur dans l'environnement
	 * @param x coordonnée en x
	 * @param y coordonnée en y
	 * @param z coordonnée en z
	 * @param length longueur du mur
	 * @param rota nombre de rotation à 90 degrès
	 */
	private void addWall(double x, double y, double z, double length, int rota)
	{
		Wall w = new Wall(new Vector3d((float)x, (float)y, (float)z), (float)length , 1, this);
		w.rotate90(rota);
		this.add(w);
	}
	
	/**
	 * Permet d'ajouter une box dans l'environnement
	 * @param x coordonnée en x  
	 * @param y coordonnée en y  
	 * @param z coordonnée en z  
	 * @param a la longueur
	 * @param b la hauteur
	 * @param c la profondeur
	 */
	private void addBox(double x, double y, double z, double a, double b, double c)
	{
		Box box = new Box(new Vector3d(x, y, z), new Vector3f((float)a, (float)b, (float)c), this);
		this.add(box);
	}
	
	/**
	 * Permet d'ajouter une node dans l'environnement et dans la liste de Node
	 * @param x coordonnées en x
	 * @param y coordonnées en y
	 * @param z coordonnées en z
	 * @param name nom
	 * @param radius rayon
	 * @param contour Tableau de String contenant les numéros des nodes voisines
	 */
	private void addNode(double x, double y, double z, String name, float radius, String... contour)
	{
		Node node = new Node(new Vector3d(x, y, z), name, radius, contour);
		this.lstNode.add(node);
		this.add(node);
	}
	
	/**
	 * permet d'ajouter les voisins des nodes entre elles
	 */
	private void chargerNodeVoisin()
	{
		for ( Node n : this.lstNode )
		{
			for ( String s : n.getVoisinsTab() )
			{
				int position = Integer.parseInt(s);
				n.addVoisin(position, this.lstNode.get(position-1));
			}
		}
	}
	
	public static void main(String[] args)
	{
		new Simbad(new Environnement(), false);
	}
}
