package game.engine.lanes;

import java.util.*;

import game.engine.base.Wall;
import game.engine.titans.ColossalTitan;
import game.engine.titans.Titan;
import game.engine.weapons.Weapon;

public class Lane implements Comparable<Lane> {

	private final PriorityQueue<Titan> titans;
	private final ArrayList<Weapon> weapons;
	private final Wall laneWall;
	private int dangerLevel;

	public Lane(Wall laneWall) {
		this.laneWall = laneWall;
		titans = new PriorityQueue<Titan>();
		weapons = new ArrayList<Weapon>();
		dangerLevel = 0;
	}


	public void moveLaneTitans() {
		/* A method that moves all the titans present in a lane each turn if they
		have not reached the base/wall yet.*/
		Iterator<Titan> iterator=  titans.iterator();
		PriorityQueue<Titan> temp = new PriorityQueue<Titan>();
		while(iterator.hasNext()) {

			Titan t  = iterator.next();
			if (!t.hasReachedTarget())
				t.move();

			/*Ammar: why this? because when colossal titans move their speed increases so they may surpass other titans
			 * and priority queues don't automatically update their priorities so we need to update it manually */
			 
				temp.add(t);
				iterator.remove();
			
		}
		
		titans.addAll(temp);

		
	}

	public int performLaneTitansAttacks() {
		/* A method that performs the attacks of all the titans present
		in the lane that has reached the base/wall and returns the number of resources gathered.
		 */
		int resourcesGathered=0;
		Iterator<Titan> iterator=  titans.iterator();

		while(iterator.hasNext()) {
			// titans attack but don't take damage 
			Titan t  = iterator.next();
			if (t.hasReachedTarget())
				resourcesGathered+=t.attack(laneWall);
		}
		return resourcesGathered;

	}

	public int performLaneWeaponsAttacks() {
		/*A method that performs the attack of all weapons present
		  in the lane to the titans present in the same lane and returns the number of resources gathered.
		 */
		int resourcesGathered=0;
		
		for(Weapon weapon : weapons) {
			resourcesGathered+= weapon.turnAttack(titans);
		}
		
		
		return resourcesGathered;
	}

	
	
	public boolean isLaneLost() {
		return laneWall.isDefeated();			
	}

	public void updateLaneDangerLevel() {
		/*A method that updates the danger level of the lane based on
		the number of titans present and their danger level.
		 */
		int newdangerL = 0;
		Iterator<Titan> iterator=  titans.iterator();

		while(iterator.hasNext()) {

			Titan t  = iterator.next();
			newdangerL+=t.getDangerLevel();
		}
		setDangerLevel(newdangerL);

	}

	public int getDangerLevel() {return dangerLevel;}

	public void setDangerLevel(int dangerLevel) {this.dangerLevel = dangerLevel;}

	public Wall getLaneWall() {return laneWall;}

	public PriorityQueue<Titan> getTitans() {return titans;}

	public ArrayList<Weapon> getWeapons() {return weapons;}

	@Override
	public int compareTo(Lane L) {return this.dangerLevel - L.dangerLevel;}
	//milesston 2 

	public void addTitan(Titan titan) {titans.add(titan);}

	public void addWeapon(Weapon weapon) {weapons.add(weapon);}




}
