package Projet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Vector3d;

public class Ghost extends Robot
{
	private Pacman pacHunt;
	private Cherry  nextNode;
	
	public Ghost(Vector3d pos, String name, Environnement ed, Pacman pacHunt)
	{
		super(pos, name, ed);
		
		this.pacHunt = pacHunt;
	}
	
	protected Pacman getPacHunt() { return this.pacHunt; }

	protected void goTo(int x, int z)
	{
		for ( Cherry n : this.myEnv.getNodes() )n.setSelection(false);
		
		List<Cherry> chemins = this.determinerChemin(x,z);
		
		System.out.println();
		for ( Cherry n : chemins)System.out.print(n+ "|");
	}
	
	private List<Cherry> determinerChemin(int x, int z)
	{
		List<Cherry> chemins = new ArrayList<Cherry>();
		Cherry node;
		Cherry endNode = null;
		boolean bOk = false;
		
		/*------------------------------*/
		/*    Utilisation de Dijkstra   */
		/*------------------------------*/
		
		nextNode = this.determinerNextNode(); // node la plus proche du fantome
		
		// initialisation
		for ( Cherry n : this.myEnv.getNodes() )n.setValeur(100000);
		nextNode.setValeur(0);
		
		List<Cherry> nodes = new ArrayList<>(this.myEnv.getNodes());

		HashMap<Cherry, Integer> voisins;
		
		while ( !bOk )
		{
			node = this.trouveMin(nodes);
			
			voisins = node.getVoisins();
			endNode = node             ;
			node.setSelection(true)    ;
			for ( Cherry n : voisins.keySet() )
				if ( !n.getSelection() )this.majDistances(node, n );
			
			bOk = true;
			for ( Cherry n : nodes )if ( !n.getSelection() )bOk = false;
		}

		System.out.println(nextNode + " " + endNode);
		
		while ( !endNode.equals(nextNode) )
		{
			chemins.add(endNode);
			endNode = endNode.getPredecesseur();
		}
		
		chemins.add(endNode);
		return chemins;
	}
	
	private Cherry trouveMin(List<Cherry> nodes)
	{
		int  mini   = 100000;
		Cherry sommet = null  ;
		
		for ( Cherry n : nodes )
		{
			if ( !n.getSelection() && n.getValeur() < mini )
			{
				mini   = n.getValeur();
				sommet = n;
			}
		}
		
		return sommet;
	}
	
	private int poids(Cherry n1, Cherry n2)
	{
		HashMap<Cherry, Integer> nodes = n1.getVoisins();
		
		for ( Cherry n : nodes.keySet() )
			if ( n.equals(n2) )return nodes.get(n);
		
		return 0;
	}
	
	private void majDistances(Cherry n1, Cherry n2)
	{
		if ( n2.getValeur() > ( n1.getValeur() + poids(n1,n2) ) )
		{
			n2.setValeur(n1.getValeur() + poids(n1,n2));
			n2.setPredecesseur(n1);
		}
	}
	
	private Cherry determinerNextNode()
	{
		char[][] plateau = this.myEnv.getPlateau();
		int xIntoTab = (int)Math.round(this.getX())+14;
		int zIntoTab = (int)Math.round(this.getZ())+15;
		
		switch ( this.target )
		{
			case "LEFT":
			{
				for ( int x=xIntoTab;x>=0;x-- )
					if ( plateau[zIntoTab][x] == 'N' )
					{
						xIntoTab = x;
						break;
					}
				break;
			}
			case "RIGHT":
			{
				for ( int x=xIntoTab;x<Environnement.NB_COL;x++ )
					if ( plateau[zIntoTab][x] == 'N' )
					{
						xIntoTab = x;
						break;
					}
				break;
			}
			case "DOWN":
			{
				for ( int z=zIntoTab;z>=0;z-- )
					if ( plateau[z][xIntoTab] == 'N' )
					{
						zIntoTab = z;
						break;
					}
				break;
			}
			case "TOP":
			{
				for ( int z=zIntoTab;z<Environnement.NB_LIG;z++ )
					if ( plateau[z][xIntoTab] == 'N' )
					{
						zIntoTab = z;
						break;
					}
				break;
			}	
		}
		
		for (Cherry n : this.myEnv.getNodes() )
			if ( n.getThis(zIntoTab, xIntoTab) != null )
				return n.getThis(zIntoTab, xIntoTab);
		
		return null;
	}
}
