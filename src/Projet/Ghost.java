/**
 * @author Benjamin Cleon, Alan Grenet, Paul Rayer
 */

package Projet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Vector3d;

public class Ghost extends Robot
{
	private Pacman  pacHunt ;	//Le Pacman a chasser
	private Cherry  nextNode;	//la prochaine Cherry
	
	private String targetOrigin;	//L'origine de la cible
	
	protected int cpt; //Compteur
	
	/**
	 * Constructeur de la classe Chost
	 * @param pos position de depart 
	 * @param name nom du fantome
	 * @param ed environnement
	 * @param pacHunt Pacman a chasser
	 */
	public Ghost(Vector3d pos, String name, Environnement ed, Pacman pacHunt)
	{
		super(pos, name, ed);
		this.pacHunt = pacHunt;
		this.targetOrigin = this.target;
	}
	/**
	 * Retourne le Pacman a chasser
	 * @return le Pacman a chasser
	 */
	protected Pacman getPacHunt() { return this.pacHunt; }

	/**
	 * Emmene le fantome au niveau du Pacman
	 * @param chPacman la Cherry ou se trouve Pacman
	 */
	protected void goTo(Cherry chPacman)
	{
		for ( Cherry n : this.myEnv.getCherries() )n.setSelection(false);
		
		List<Cherry> chemins = this.determinerChemin(chPacman);
		
		Cherry c;
		if ( chemins.size() > 1 )c = chemins.get(chemins.size()-2);
		else                     c = chemins.get(chemins.size()-1);
		
		this.setCurrentCherry();
		this.setDeplacement(this.currentCherry);
		
		if ( c.getCol()-14 == (int)Math.round(this.getX()) )
		{
			if ( c.getLig()-15 < (int)Math.round(this.getZ())) this.setAngle("TOP"  );  
			else                                               this.setAngle("DOWN" );  
		}
		else
		{
			if ( c.getCol()-14 < (int)Math.round(this.getX()) ) this.setAngle("LEFT"  ); 
			else                                                this.setAngle("RIGHT" );  
		}
	}
	
	/**
	 * Retourne la liste des Cherry composant le chemin le plus court
	 * pour arriver a Pacman
	 * @param chDestination
	 * @return la liste des Cherry composant le chemin le plus court
	 */
	private List<Cherry> determinerChemin(Cherry chDestination)
	{
		List<Cherry> chemins = new ArrayList<Cherry>();
		Cherry cherry;
		Cherry endNode = null;
		boolean bOk = false;
		
		/*------------------------------*/
		/*    Utilisation de Dijkstra   */
		/*------------------------------*/
		
		nextNode = this.determinerNextNode(); // cherry la plus proche du fantome
		
		// initialisation
		for ( Cherry n : this.myEnv.getCherries() )n.setValeur(100000);
		nextNode.setValeur(0);
		
		List<Cherry> cherries = new ArrayList<>(this.myEnv.getCherries());

		HashMap<Cherry, Integer> voisins;
		
		while ( !bOk )
		{
			cherry = this.trouveMin(cherries);
			
			voisins = cherry.getVoisins();
			cherry.setSelection(true)    ;
			for ( Cherry n : voisins.keySet() )
				if ( !n.getSelection() )this.majDistances(cherry, n );
			
			bOk = true;
			
			if ( !chDestination.getSelection() ) bOk = false;
		}
		
		endNode = chDestination;
		while ( !endNode.equals(nextNode) )
		{
			chemins.add(endNode);
			endNode = endNode.getPredecesseur();
		}
		
		chemins.add(endNode);
		return chemins;
	}
	
	/**
	 * Trouve le minimum entre les Cherry
	 * @param cherries
	 * @return le minimum entre les Cherry
	 */
	private Cherry trouveMin(List<Cherry> cherries)
	{
		int  mini   = 100000;
		Cherry sommet = null  ;
		
		for ( Cherry n : cherries )
		{
			if ( !n.getSelection() && n.getValeur() < mini )
			{
				mini   = n.getValeur();
				sommet = n;
			}
		}
		
		return sommet;
	}
	
	/**
	 * Retourne la distance entre les deux Cherry
	 * @param c1 Premiere Cherry a comparer
	 * @param c2 Seconde Cherry a comparer
	 * @return la distance entre les deux Cherry
	 */
	private int poids(Cherry c1, Cherry c2)
	{
		HashMap<Cherry, Integer> cherries = c1.getVoisins();
		
		for ( Cherry c : cherries.keySet() )
			if ( c.equals(c2) )return cherries.get(c);
		
		return 0;
	}
	/**
	 * Met a jour la distance entre deux Cherry
	 * @param n1 Premiere Cherry a regarder
	 * @param n2 Seconde Cherry a regarder
	 */
	private void majDistances(Cherry n1, Cherry n2)
	{
		if ( n2.getValeur() > ( n1.getValeur() + poids(n1,n2) ) )
		{
			n2.setValeur(n1.getValeur() + poids(n1,n2));
			n2.setPredecesseur(n1);
		}
	}
	
	/**
	 * Retourne la prochaine Cherry
	 * @return la prochaine Cherry
	 */
	private Cherry determinerNextNode()
	{
		char[][] plateau = this.myEnv.getPlateau();
		int xIntoTab = (int)Math.round(this.getX())+14;
		int zIntoTab = (int)Math.round(this.getZ())+15;
		
		if ( xIntoTab >= 28 )xIntoTab = 27;
		
		switch ( this.target )
		{
			case "LEFT":
			{
				this.targetOrigin = this.target;
				for ( int x=xIntoTab;x>=0;x-- )
					if ( plateau[zIntoTab][x] == 'C' )
					{
						xIntoTab = x;
						break;
					}
					else if ( plateau[zIntoTab][x] == 'B' )
					{
						if ( this.targetOrigin.equals("DOWN") )this.target = "RIGHT";
						else                                   this.target = "TOP";
						return this.determinerNextNode();
					}
				break;
			}
			case "RIGHT":
			{
				this.targetOrigin = this.target;
				for ( int x=xIntoTab;x<Environnement.NB_COL;x++ )
					if ( plateau[zIntoTab][x] == 'C' )
					{
						xIntoTab = x;
						break;
					}
					else if ( plateau[zIntoTab][x] == 'B' )
					{
						if ( this.targetOrigin.equals("TOP") )this.target = "LEFT";
						else                                  this.target = "DOWN";
						return this.determinerNextNode();
					}
				break;
			}
			case "DOWN":
			{
				this.targetOrigin = this.target;
				for ( int z=zIntoTab;z>=0;z-- )
					if ( plateau[z][xIntoTab] == 'C' )
					{
						zIntoTab = z;
						break;
					}
					else if ( plateau[z][xIntoTab] == 'B' )
					{
						if ( this.targetOrigin.equals("RIGHT") )this.target = "TOP";
						else                                    this.target = "LEFT";
						return this.determinerNextNode();
					}
				break;
			}
			case "TOP":
			{
				this.targetOrigin = this.target;
				for ( int z=zIntoTab;z<Environnement.NB_LIG;z++ )
					if ( plateau[z][xIntoTab] == 'C' )
					{
						zIntoTab = z;
						break;
					}
					else if ( plateau[z][xIntoTab] == 'B' )
					{
						if ( this.targetOrigin.equals("LEFT") )this.target = "DOWN";
						else                                   this.target = "RIGHT";
						return this.determinerNextNode();
					}
				break;
			}	
		}
		
		for (Cherry n : this.myEnv.getCherries() )
			if ( n.getThis(zIntoTab, xIntoTab) != null )
				return n.getThis(zIntoTab, xIntoTab);
		
		return null;
	}
	
	/**
	 * Effectue les actions
	 */
	public void performBehavior()
	{

		if ( this.collisionDetected() )
			this.moveToPosition(this.currentCherry.getPosition().getX(), this.currentCherry.getPosition().getZ());
		
	}
}
