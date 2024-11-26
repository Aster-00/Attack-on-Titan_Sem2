package game.engine.interfaces;

public interface Attacker {
	int getDamage();
	
	default int attack(Attackee target) {
		/* a method that inflict damage to the target and should return the value of
		 * gained resoures in case attacke was defeated
		 */
		   return target.takeDamage(getDamage());
		
	}

}
