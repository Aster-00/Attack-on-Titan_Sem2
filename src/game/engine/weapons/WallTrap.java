package game.engine.weapons;

import java.util.Iterator;
import java.util.PriorityQueue;

import game.engine.titans.Titan;

public class WallTrap extends Weapon {
	public final static int WEAPON_CODE = 4;

	public WallTrap(int baseDamage) {
		super(baseDamage);
	}

	//	@Override
	//	public int turnAttack(PriorityQueue<Titan> laneTitans) {
	//		int gatheredResources = 0;
	//		if(laneTitans.isEmpty())
	//			return 0;
	//		if(laneTitans.peek().hasReachedTarget())
	//				 gatheredResources = attack(laneTitans.peek());
	//		
	//			//remove if defeated
	//			if (laneTitans.peek().isDefeated())
	//				laneTitans.poll();
	//			
	//
	//		return gatheredResources;
	//	
	//	
	//}
	@Override
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		int gatheredResources = 0;
		if(laneTitans.isEmpty() )
			return 0;



		Iterator<Titan> iterator = laneTitans.iterator();

		// Living titans shouldn't be removed form lane on attack
		PriorityQueue<Titan> tempqueue = new PriorityQueue<Titan>();


		while(iterator.hasNext() ) {


			Titan t  = laneTitans.poll();

			if(t.hasReachedTarget())
				gatheredResources += attack(t);
			if(!t.isDefeated())
				tempqueue.add(t);
		} 

		//addAll doesnt maintain correct order of titans
		 laneTitans.addAll(tempqueue);
		

		return gatheredResources;
	}

}
