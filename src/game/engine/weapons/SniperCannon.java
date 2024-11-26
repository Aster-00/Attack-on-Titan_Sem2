package game.engine.weapons;

import java.util.PriorityQueue;

import game.engine.titans.Titan;

public class SniperCannon extends Weapon {
	public final static int WEAPON_CODE = 2;

	public SniperCannon(int baseDamage) {
		super(baseDamage);
	}

//	@Override
//	public int turnAttack(PriorityQueue<Titan> laneTitans) {
//		
//			if(laneTitans.isEmpty())
//				return 0;
//			
//			 int gatheredResources = laneTitans.peek().takeDamage(35);
//				//remove if defeated
//				if (laneTitans.peek().isDefeated())
//					laneTitans.poll();
//				
//
//			return gatheredResources;
//		
//		
//	}
	
	@Override
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		//Attacks the closest titan if it exists
			if(laneTitans.isEmpty())
				return 0;
				Titan t = laneTitans.poll();
			 int gatheredResources = attack(t);
				//remove if defeated
				if (!t.isDefeated())
					laneTitans.add(t);
				

			return gatheredResources;
		
		
	}

}
