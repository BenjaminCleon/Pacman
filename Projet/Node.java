package Projet;

import java.util.HashMap;

import javax.vecmath.Vector3d;

import simbad.sim.CherryAgent;

public class Node extends CherryAgent
{
	private String[] voisinTab;
	private HashMap<Integer ,Node> voisins;
	
	public Node(Vector3d pos, String name, float radius, String... voisin)
	{
		super(pos, name, radius);
		this.voisins = new HashMap<Integer, Node>();
		this.voisinTab = voisin;
	}
	
	public String[] getVoisinsTab()
	{
		return this.voisinTab;
	}
	
	public void addVoisin(int ind, Node n)
	{
		this.voisins.put(ind, n);
	}
}
