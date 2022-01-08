package Projet;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import simbad.sim.CherryAgent;

public class Node extends CherryAgent
{
	private int lig;
	private int col;
	
	public Node(Vector3d pos, String name, float radius, int lig, int col)
	{
		super(pos, name, radius, lig, col);
		
		this.lig = lig;
		this.col = col;
	}
}
