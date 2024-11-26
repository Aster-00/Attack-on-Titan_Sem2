package game.engine.weapons;

public class WeaponRegistry {

	private final int code;
	private String name;
	private int price;
	private int damage;
	private int minRange;
	private int maxRange;

	public int getCode() {
		return code;
	}

	public int getPrice() {
		return price;
	}

	public int getDamage() {
		return damage;
	}

	public String getName() {
		return name;
	}

	public int getMinRange() {
		return minRange;
	}

	public int getMaxRange() {
		return maxRange;
	}

	public WeaponRegistry(int code, int price) {
		this.code = code;
		this.price = price;

	}

	public WeaponRegistry(int code, int price, int damage, String name) {
		this.code = code;
		this.price = price;
		this.damage = damage;
		this.name = name;

	}

	public WeaponRegistry(int code, int price, int damage, String name, int minRange, int maxRange) {
		this.code = code;
		this.price = price;
		this.damage = damage;
		this.name = name;
		this.minRange = minRange;
		this.maxRange = maxRange;
	}
	public Weapon buildWeapon() {		
		/* A method that returns an object of the relevant type of weapon based
			on the registry code attribute.
			return object beta3 Constructor men hena wala men el relevant weapon  object*/

		switch (this.code) {
		case 1:
			return new PiercingCannon(damage);
		case 2:
			return new SniperCannon(damage) ;
		case 3:
			return new VolleySpreadCannon(damage, minRange, maxRange);
		case 4:
			return new WallTrap(damage);
		default:
			// check if this is the right way to handle the default 
            return null;
		}
	}

}

