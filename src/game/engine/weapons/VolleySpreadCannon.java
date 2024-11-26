package game.engine.weapons;

import java.util.Iterator;
import java.util.PriorityQueue;

import game.engine.titans.Titan;

public class VolleySpreadCannon extends Weapon {
	public final static int WEAPON_CODE = 3;
	private final int minRange;
	private final int maxRange;

	public int getMinRange() {
		return minRange;
	}

	public int getMaxRange() {
		return maxRange;
	}

	public VolleySpreadCannon(int baseDamage, int minRange, int maxRange) {
		super(baseDamage);
		this.maxRange = maxRange;
		this.minRange = minRange;
	}



	@Override
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		
		if(laneTitans.isEmpty())
			return 0;
		
		
		int gatheredResources=0;
		
		
		Iterator<Titan> iterator = laneTitans.iterator();
		
		while(iterator.hasNext()) {
			Titan t  = iterator.next();
			
			
			if(t.getDistance()<=maxRange && t.getDistance()>=minRange)
			gatheredResources += t.takeDamage(getDamage());
		
			if(t.isDefeated()) {
				iterator.remove();
			}
			
		}
		return gatheredResources;
		
	}



}

