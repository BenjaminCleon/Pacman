package Projet;

import java.util.HashMap;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import simbad.sim.CherryAgent;

public class Cherry extends CherryAgent
{
	private int lig;
	private int col;
	
	private Environnement myEnv;
	
	private HashMap<Cherry, Integer> voisins; // distance avec la node voisine
	private Cherry    predecesseur ;
	private boolean dejaSelectionne;
	
	private boolean ceuillis;
	
	private Point3d position;
	
	private int valeur;
	
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
	
	public Cherry getPredecesseur()      { return this.predecesseur; }
	public void setPredecesseur(Cherry n){ this.predecesseur = n   ; } 
	
	public void setValeur(int valeur) { this.valeur = valeur; }
	public int  getValeur()           { return this.valeur  ; }
	
	public Cherry getThis(int lig, int col)
	{
		if ( lig == this.lig && col == this.col )return this;
		
		return null;
	}
	
	public void ceuillir() 
	{
		this.ceuillis = true;
		this.detach();
	}
	
	public boolean getCeuillis() {return this.ceuillis;}
	
	public HashMap<Cherry, Integer> getVoisins() { return this.voisins; }
	
	public void setVoisins(Cherry c) {this.voisins.put(c,  1);}
	
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
		// voisins à droite
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
		// voisins à gauche
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
	
	public Point3d getPosition()
	{
		return this.position;
	}
	
	public boolean equals(Cherry autre)
	{
		return this.lig == autre.lig && this.col== autre.col;
	}
	
	public boolean getSelection() { return this.dejaSelectionne; }
	public void setSelection(boolean b)	{ this.dejaSelectionne = b;	}
	
	@Override
	public void performBehavior()
	{
		this.getCoords(this.position);
	}
	
	public String toString()
	{
		return "[" + this.valeur + "](" + this.getLig() + " " + this.getCol() + ")";
	}
}
