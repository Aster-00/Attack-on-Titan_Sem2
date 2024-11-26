package game.engine.titans;

import game.engine.interfaces.Attackee;


public class AbnormalTitan extends Titan {
	
	
	public final static int TITAN_CODE = 2;

	public AbnormalTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel) {
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);
	}

	@Override
	public int attack(Attackee target) {
		int gatheredResources =0;
		
		gatheredResources+= target.takeDamage(getDamage());
		if (!target.isDefeated()) {
			gatheredResources+= target.takeDamage(getDamage());
		}
		return gatheredResources;
	
	}


}
