package game.engine.titans;

public class ColossalTitan extends Titan {
	public final static int TITAN_CODE = 4;

	public ColossalTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel) {
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);
	}

	@Override
	public boolean move() {
		
		if(hasReachedTarget())
			return true;
		//Ammar change. 1 failure 2al
		setDistance(getDistance()-getSpeed());
		setSpeed(getSpeed()+1);
		return hasReachedTarget();
	}

	
}
