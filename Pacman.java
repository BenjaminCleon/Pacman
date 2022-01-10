/**
 * @author Benjamin Cleon, Alan Grenet, Paul Rayer
 */

package Projet;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import simbad.sim.CherryAgent;

public class Pacman extends Robot
{
	/**
	 * Constructeur de la classe Pacman
	 * @param pos position de depart
	 * @param name Nom du Pacman
	 * @param ed Environnement
	 */
	public Pacman(Vector3d pos, String name, Environnement ed)
	{
		super(pos, name, ed);
		this.setColor(new Color3f(255,255,0));
		
		this.deplacementsAutorise.add("TOP");
		this.deplacementsAutorise.add("DOWN");
		this.deplacementsAutorise.add("LEFT");
		this.deplacementsAutorise.add("RIGHT");
	}
	
	/**
	 * Retourne la Cherry ou se trouve Pacman
	 * @return la Cherry ou se trouve Pacman
	 */
	public Cherry getCurrentCherry(){ return this.currentCherry;	}
	
	/**
	 * Effectue les actions
	 */
	public void performBehavior()
	{
		this.getCoords(this.position);
		this.setCurrentCherry();
	
		this.TP();
		
		if ( this.anOtherAgentIsVeryNear() && this.getVeryNearAgent() instanceof Cherry )
		{
			((Cherry)this.getVeryNearAgent()).ceuillir();
			this.verificationVictoire();
		}
		if ( this.anOtherAgentIsVeryNear() && this.getVeryNearAgent() instanceof Ghost )
		{
			this.finDePartie(false);
		}
		this.setTranslationalVelocity(4.75);

		if ( this.currentCherry != null )
		{
			this.setDeplacement(this.currentCherry);
		}
	}
	
	/**
	 * Verifie si toutes les cerises sont cueillie
	 */
	public void verificationVictoire()
	{
		boolean finis = true;
		for(Cherry c : this.myEnv.getCherries())
			if(!c.getCeuillis())
				finis = false;
		if(finis) {this.finDePartie(true);}
	}
	/**
	 * Genere l'ecran de fin de partie
	 * @param aGagne vrai si le joueur a gagne, faux si il a perdu
	 */
	public void finDePartie(boolean aGagne)
	{
		this.myEnv.getSimbad().dispose();
		new FrameFinDePartie(aGagne);
		myEnv.getSimbad().simulator.resetSimulation();
	}
	
}
