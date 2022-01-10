/**
 * @author Benjamin Cleon, Alan Grenet, Paul Rayer
 */

package Projet;

import java.util.HashMap;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import simbad.sim.CherryAgent;
/**
 * Classe des Cherry (Cerise)
 * @author Benjamin Cleon, Alan Grenet, Paul Rayer
 *
 */
public class Cherry extends CherryAgent
{
	private int lig;	// Position de la ligne de la cerise
	private int col;	// Position de la colonne de la cerise
	
	private Environnement myEnv; 	// Environnement
	
	private HashMap<Cherry, Integer> voisins; // distance avec la node voisine
	private Cherry    predecesseur ;	//prédecesseur pour l'algorithme de dijkstra moore
	private boolean dejaSelectionne;	// booleen si la cerise est deja selectionnee
	
	private boolean ceuillis;			// boolean si la cerise est deja cueillie
	
	private Point3d position;	// Position de la cerise
	
	private int valeur; 	//valeur de la cerise
	
	/**
	 * Constructeur de la classe Cherry
	 * @param pos position de depart 
	 * @param name nom du fantome
	 * @param radius rayon
	 * @param lig ligne de la cerise
	 * @param col colonne de la cerise
	 * @param ed environnement	
	 */
	public Cherry(Vector3d pos, String name, float radius, int lig, int col, Environnement ed)
	{
		super(pos, name, radius, lig, col);
		
		this.lig = lig;
		this.col = col;
		this.myEnv = ed;
		
		this.ceuillis = false;
		
		this.valeur  = 0;
		this.position = new Point3d(pos);
		this.voisins = new HashMap<Cherry, Integer>();
	}
	
	/**
	 * Retourne le predecesseur de la cerise
	 * @return le predecesseur de la cerise
	 */
	public Cherry getPredecesseur()      { return this.predecesseur; }
	
	/**
	 * définit le predecesseur
	 * @param n predecesseur pour definir
	 */
	public void setPredecesseur(Cherry n){ this.predecesseur = n   ; } 
	
	/**
	 * Definit la valeur
	 * @param valeur valeur a definir
	 */
	public void setValeur(int valeur) { this.valeur = valeur; }
	
	/**
	 * Retourne la valeur de la Cherry
	 * @return la valeur de la Cherry
	 */
	public int  getValeur()           { return this.valeur  ; }
	
	/**
	 * Retourne la Cherry aux coordonnées en parametre
	 * @param lig numero de la ligne
	 * @param col numero de la colonne
	 * @return la Cherry aux coordonnees en parametre
	 */
	public Cherry getThis(int lig, int col)
	{
		if ( lig == this.lig && col == this.col )return this;
		
		return null;
	}
	
	/**
	 * Active l'attribut permettant de savoir si la Cherry est cueillie
	 * La fait disparaitre du plateau
	 */
	public void ceuillir() 
	{
		this.ceuillis = true;
		this.detach();
	}
	
	/**
	 * Retourne si la cerise est cueillie ou non
	 * @return si la cerise est cueillie ou non
	 */
	public boolean getCeuillis() {return this.ceuillis;}
	
	/**
	 * Retourne les voisins de la cerise
	 * @return les voisins de la cerise
	 */
	public HashMap<Cherry, Integer> getVoisins() { return this.voisins; }
	
	/**
	 * Definit un voisin
	 * @param c voisin a ajouter
	 */
	public void setVoisins(Cherry c) {this.voisins.put(c,  1);}
	
	/**
	 * definit l'ensemble des voisins de la Cherry
	 */
	public void setVoisins()
	{
		char[][] plateau = this.myEnv.getPlateau();
		boolean bOk = false;
		
		// voisins au dessus
		for (int z=this.lig-1;z>=0;z--)
		{
			if ( plateau[z][this.col] == 'C' )
				for ( Cherry n : this.myEnv.getCherries() )
					if ( n.getCol() == this.col && z == n.getLig() )
					{
						this.voisins.put(n, this.lig-z);
						bOk = true;
					}
			if ( plateau[z][this.col] == 'B' || bOk )break;
		}
		
		bOk = false;
		// voisins en dessous
		for (int z=this.lig+1;z<Environnement.NB_LIG;z++)
		{
			if ( plateau[z][this.col] == 'C' )
				for ( Cherry n : this.myEnv.getCherries() )
				{
					if ( n.getCol() == this.col && z == n.getLig() )
					{
						this.voisins.put(n, z-this.lig);
						bOk = true;
					}
				}
			if ( plateau[z][this.col] == 'B' || bOk )break;
		}
		
		bOk = false;
		// voisins Ã  droite
		for (int x=this.col+1;x<Environnement.NB_COL;x++)
		{
			if ( plateau[this.lig][x] == 'C' )
				for ( Cherry n : this.myEnv.getCherries() )
					if ( n.getLig() == this.lig && x == n.getCol() )
					{
						this.voisins.put(n, x-this.col);
						bOk = true;
					}
			if ( plateau[this.lig][x] == 'B' || bOk )break;
		}
		
		bOk = false;
		// voisins Ã  gauche
		for (int x=this.col-1;x>=0;x--)
		{
			if ( plateau[this.lig][x] == 'C' )
				for ( Cherry n : this.myEnv.getCherries() )
					if ( n.getLig() == this.lig && x == n.getCol() )
					{
						this.voisins.put(n, this.col-x);
						bOk = true;
					}
			if ( plateau[this.lig][x] == 'B' || bOk )break;
		}
		
		System.out.println(this + " " +  voisins);
	}
	
	/**
	 * Retourne la position de la Cherry
	 * @return la position de la Cherry
	 */
	public Point3d getPosition()
	{
		return this.position;
	}
	
	/**
	 * Retourne vrai si la Cherry est a la meme position qu'une autre
	 * @param autre Differente Cherry a comparer
	 * @return vrai si la Cherry est a la meme position qu'une autre
	 */
	public boolean equals(Cherry autre)
	{
		return this.lig == autre.lig && this.col== autre.col;
	}
	
	/**
	 * Retourne vrai si la cerise est deja selectionnee
	 * @return vrai si la cerise est deja selectionnee
	 */
	public boolean getSelection() { return this.dejaSelectionne; }
	
	/**
	 * Definit si la cerise est deja selectionnee
	 * @param b boolean
	 */
	public void setSelection(boolean b)	{ this.dejaSelectionne = b;	}
	
	@Override
	public void performBehavior()
	{
		this.getCoords(this.position);
	}
	
	/** Retourne la valeur et les coordonnées de la Cherry
	 * @return la valeur et les coordonnées de la Cherry
	 */
	public String toString()
	{
		return "[" + this.valeur + "](" + this.getLig() + " " + this.getCol() + ")";
	}
}
