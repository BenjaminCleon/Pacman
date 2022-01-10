/**
 * @author Benjamin Cleon, Alan Grenet, Paul Rayer
 */

package Projet;

import javax.vecmath.Vector3d;

/**
 * Classe Node
 * @author Benjamin Cleon, Alan Grenet, Paul Rayer
 *
 */
public class Node extends Cherry
{
	/**
	 * Constructeur de la classe Node
	 * @param pos position de départ
	 * @param name Nom de la Node
	 * @param radius rayon
	 * @param lig ligne
	 * @param col colonne
	 * @param ed environnement
	 */
	public Node(Vector3d pos, String name, float radius, int lig, int col, Environnement ed)
	{
		super(pos, name, radius, lig, col, ed);
		// TODO Auto-generated constructor stub
	}

}
