package game.engine.weapons;

import java.util.Iterator; 
import java.util.PriorityQueue;

import javax.xml.transform.Templates;

import game.engine.titans.Titan;

public class PiercingCannon extends Weapon {
	public final static int WEAPON_CODE = 1;

	public PiercingCannon(int baseDamage) {
		super(baseDamage);
	}

	@Override
	

	//Ammar Change Correct Double Checked
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		//
		if(laneTitans.isEmpty())
			return 0;

		int gatheredResources=0;

		Iterator<Titan> iterator = laneTitans.iterator();
	
		PriorityQueue<Titan> tempqueue = new PriorityQueue<Titan>();

		int FiveTitanCounter=0;
		while(iterator.hasNext() && FiveTitanCounter<5) {
			FiveTitanCounter++;

			Titan t  = laneTitans.poll();

			gatheredResources += attack(t);
			if(!t.isDefeated())
				tempqueue.add(t);

		}
		
		 laneTitans.addAll(tempqueue);
		
		return gatheredResources;
	}



}
