package game.engine.weapons.factory;
//import game.engine.weapons.WeaponRegistry;
import java.io.IOException;
import java.util.HashMap;

import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.weapons.Weapon;
import game.engine.weapons.WeaponRegistry;

public class WeaponFactory {

	private final HashMap<Integer, WeaponRegistry> weaponShop;

	public WeaponFactory() throws IOException {
		weaponShop = DataLoader.readWeaponRegistry();
	}

	public HashMap<Integer, WeaponRegistry> getWeaponShop() {
		return weaponShop;
	}





	public FactoryResponse buyWeapon(int resources, int weaponCode) throws InsufficientResourcesException
	{/*A method that takes available resources and weapon code as inputs to purchase the specified
weapon from the weaponShop indicated by the code if there are available resources. In case of
a successful purchase, it returns a FactoryResponse containing the weapon and the remaining
resources after deducting the weapon’s price.*/
		int weaponPrice = getWeaponShop().get(weaponCode).getPrice();


		if (resources <= weaponPrice) {
			throw new InsufficientResourcesException("Not enough resources to buy this weapon",resources);
		}
		else {
			resources-=weaponPrice;
			return new FactoryResponse( weaponShop.get(weaponCode).buildWeapon(), resources);
		}
	}



	public void addWeaponToShop(int code, int price) {

		// Create a new WeaponRegistry object with the given parameters
		WeaponRegistry weaponRegistry = new WeaponRegistry(code, price);

		// Add the WeaponRegistry object to the weaponShop HashMap
		weaponShop.put(code, weaponRegistry);
	}

	public void addWeaponToShop(int code, int price, int damage, String name) {
		// Create a new WeaponRegistry object with the given parameters
		WeaponRegistry weaponRegistry = new WeaponRegistry(code, price, damage, name);

		// Add the WeaponRegistry object to the weaponShop HashMap
		weaponShop.put(code, weaponRegistry);
	}


	public void addWeaponToShop(int code, int price, int damage, String name, int minRange, int maxRange) {
		// Create a new WeaponRegistry object with the given parameters
		WeaponRegistry weaponRegistry = new WeaponRegistry(code, price, damage, name, minRange, maxRange);

		// Add the WeaponRegistry object to the weaponShop HashMap
		weaponShop.put(code, weaponRegistry);
	}


}

