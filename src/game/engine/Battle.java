package game.engine;

import java.io.IOException;  
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;

import game.engine.base.Wall;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;

import game.engine.lanes.Lane;
import game.engine.titans.ColossalTitan;
import game.engine.titans.Titan;
import game.engine.titans.TitanRegistry;
import game.engine.weapons.Weapon;
import game.engine.weapons.WeaponRegistry;
import game.engine.weapons.factory.FactoryResponse;
import game.engine.weapons.factory.WeaponFactory;

public class Battle {

	private final static int[][] PHASES_APPROACHING_TITANS = {  
			{1, 1, 1, 2, 1, 3, 4},
			{2, 2, 2, 1, 3, 3, 4},
			{4, 4, 4, 4, 4, 4, 4} };

	private final static int WALL_BASE_HEALTH = 10000;
	private final WeaponFactory weaponFactory;
	private final HashMap<Integer, TitanRegistry> titansArchives;
	private final ArrayList<Titan> approachingTitans;
	private final PriorityQueue<Lane> lanes;
	private final ArrayList<Lane> originalLanes; 
	private int numberOfTurns;
	private int resourcesGathered;
	private BattlePhase battlePhase;
	private int numberOfTitansPerTurn;
	private int score;
	private int titanSpawnDistance;
	

	public Battle(int numberOfTurns, int score, int titanSpawnDistance, int initialNumOfLanes, int initialResourcesPerLane) throws IOException {
		this.numberOfTurns = numberOfTurns;
		this.score = score;
		this.titanSpawnDistance = titanSpawnDistance;
		this.battlePhase = BattlePhase.EARLY;
		this.numberOfTitansPerTurn = 1;
		resourcesGathered = initialNumOfLanes * initialResourcesPerLane;
		titansArchives = DataLoader.readTitanRegistry();
		approachingTitans = new ArrayList<Titan>();
		lanes = new PriorityQueue<Lane>();
		originalLanes = new ArrayList<Lane>();
		weaponFactory = new WeaponFactory();
		initializeLanes(initialNumOfLanes);

	}

	private void initializeLanes(int numOfLanes) {

		for (int i = 0; i < numOfLanes; i++) {
			Lane l = new Lane(new Wall(WALL_BASE_HEALTH));
			originalLanes.add(l);
			lanes.add(l);

		}

	}



	//DONEE CHCEKED
	public void refillApproachingTitans() {

		for(int i = 0; i < PHASES_APPROACHING_TITANS[battlePhase.ordinal()].length ; i++) {
			approachingTitans.add(titansArchives.get(PHASES_APPROACHING_TITANS[battlePhase.ordinal()][i]).spawnTitan(getTitanSpawnDistance()));
		}
		
	}


	private void addTurnTitansToLane() {
		/*: A method that adds Titans from approachingTitans
			to the least dangerous active lane based on the the numberOfTitansPerTurn. If approachingTitans
			is empty, it should be refilled based on the phase.*/

		if (approachingTitans.isEmpty()) {
			refillApproachingTitans();
		}
		Lane L = findLeastDangerousLane();

		// Add titans to the least dangerous active lane
		for (int i = 0; i < numberOfTitansPerTurn && !approachingTitans.isEmpty(); i++) {

			L.addTitan(approachingTitans.remove(0)); 
			if (approachingTitans.isEmpty()) {
				refillApproachingTitans();
			}

		}
		if (approachingTitans.isEmpty()) {
			refillApproachingTitans();
		}
	}


	public void purchaseWeapon(int weaponCode, Lane lane) throws InsufficientResourcesException,InvalidLaneException{
		/*A method that deploys the specific weapon indicated by the input code
		and adds it to the input lane if it’s not lost (Weapons can only be added to active lanes). Resources
		should be updated after a successful purchase. The rest of the turn actions should be performed
		afterwards*/

		if(lane.isLaneLost() || !lanes.contains(lane)) {
			throw new InvalidLaneException("Cannot pruchase weapon as lane is lost");
		}
		else {
			FactoryResponse fr= weaponFactory.buyWeapon(resourcesGathered, weaponCode); 
			lane.addWeapon(fr.getWeapon());
			resourcesGathered = fr.getRemainingResources();
		}
		performTurn() ;
	}

	// 4
	// ----------------------------



	public Lane findLeastDangerousLane() {
		Lane leastDangerousLane = null;
		int lowestDangerLevel = Integer.MAX_VALUE; // Initialize with the maximum possible value

		// Iterate over all lanes
		for (Lane lane : lanes) {
			// Retrieve the current lane's danger level
			if(!lane.isLaneLost()) {
				int currentDangerLevel = lane.getDangerLevel();

				// Compare the current lane's danger level with the lowest recorded danger level
				if (currentDangerLevel < lowestDangerLevel) {
					// Update the least dangerous lane and its danger level
					leastDangerousLane = lane;
					lowestDangerLevel = currentDangerLevel;
				}
			}

		}
		return leastDangerousLane;
	}



	//DONE
	private void moveTitans() {
		for(Lane lane :lanes) {
			lane.moveLaneTitans();
			//logic in Lane CLass

		}
	}


	//DONE
	private int performWeaponsAttacks() {
		int accumulatedResources = 0;

		for (Lane lane : lanes) {
			if (!lane.isLaneLost()) { // Check if the lane is active
				// Perform weapon attacks for the current lane and get the resources gathered
				accumulatedResources += lane.performLaneWeaponsAttacks();
			}
		}
		score+= accumulatedResources;
		resourcesGathered+=accumulatedResources;
		return accumulatedResources;
	}

	//DONE decreased 5 failures 
	private int performTitansAttacks() {
		/* A method that performs titan attacks for all of the
	lanes present in the game. It also returns the attack’s result (Wall’s resource value in case of its
	destruction).*/

		int resources = 0;

		//	Iterator<Lane> iterator = lanes.iterator();
		PriorityQueue<Lane> temp = new PriorityQueue<Lane>() ;
		while(!lanes.isEmpty()) {
			Lane l = lanes.poll();
			resources += l.performLaneTitansAttacks();

			if(!l.isLaneLost()) {
				temp.add(l);
			}		

		}


		Iterator<Lane> it = temp.iterator();
		while(it.hasNext()) {
			lanes.add(it.next());
		}

		return resources;
	}




	//       -----------------------------------------------------------------------------------
	//DONE
	private void updateLanesDangerLevels() {
		// Iterate over all lanes
		Iterator<Lane> iterator = lanes.iterator();
		PriorityQueue<Lane> temp = new PriorityQueue<Lane>();
		while(iterator.hasNext()){
			Lane lane = lanes.poll();
			// Check if the lane is active (not lost)
			if (!lane.isLaneLost()) {
				// Update the danger level of the lane
				lane.updateLaneDangerLevel();
				temp.add(lane);
			}

		}

		Iterator<Lane> it = temp.iterator();
		while(it.hasNext()) {
			Lane l = it.next();
			lanes.add(l);
		}

	}

	private void finalizeTurns() {
		// Update the number of turns
		numberOfTurns++;

		// Switch phases based on the number of turns
		if (numberOfTurns < 15) {
			battlePhase = BattlePhase.EARLY;
		} else if (numberOfTurns < 30) {
			battlePhase = BattlePhase.INTENSE;
		} else {
			if (numberOfTurns > 30 && numberOfTurns % 5 == 0) {
				battlePhase = BattlePhase.GRUMBLING;
				numberOfTitansPerTurn *= 2;
			}

			else
				battlePhase = BattlePhase.GRUMBLING;
		}

	}


	public void passTurn() {
		moveTitans();

		// Perform weapons attacks in all lanes and gather resources
		resourcesGathered+= performWeaponsAttacks(); 

		// perform titan attack
		performTitansAttacks();

		// add aproaching
		addTurnTitansToLane();

		// Update danger levels of all lanes
		updateLanesDangerLevels();


		// Finalize turns to switch phases and adjust titans per turn
		finalizeTurns();
	}


	private void performTurn() {

		passTurn();



	}

	public boolean isGameOver() {
		// Iterate over all lanes
		for (Lane lane : lanes) {
			// If any lane is not lost, return false
			if (!lane.isLaneLost() ) {
				return false;
			}
		}
		// If all lanes are lost, return true
		return true;
	}

	public int getNumberOfTurns() {
		return numberOfTurns;
	}

	public void setNumberOfTurns(int numberOfTurns) {
		this.numberOfTurns = numberOfTurns;
	}

	public int getResourcesGathered() {
		return resourcesGathered;
	}

	public void setResourcesGathered(int resourcesGathered) {
		this.resourcesGathered = resourcesGathered;
	}

	public BattlePhase getBattlePhase() {
		return battlePhase;
	}

	public void setBattlePhase(BattlePhase battlePhase) {
		this.battlePhase = battlePhase;
	}

	public int getNumberOfTitansPerTurn() {
		return numberOfTitansPerTurn;
	}

	public void setNumberOfTitansPerTurn(int numberOfTitansPerTurn) {
		this.numberOfTitansPerTurn = numberOfTitansPerTurn;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getTitanSpawnDistance() {
		return titanSpawnDistance;
	}

	public void setTitanSpawnDistance(int titanSpawnDistance) {
		this.titanSpawnDistance = titanSpawnDistance;
	}

	public int[][] getPHASES_APPROACHING_TITANS() {
		return PHASES_APPROACHING_TITANS;
	}

	public int getWALL_BASE_HEALTH() {
		return WALL_BASE_HEALTH;
	}

	public WeaponFactory getWeaponFactory() {
		return weaponFactory;
	}

	public HashMap<Integer, TitanRegistry> getTitansArchives() {
		return titansArchives;
	}

	public ArrayList<Titan> getApproachingTitans() {
		return approachingTitans;
	}

	public PriorityQueue<Lane> getLanes() {
		return lanes;
	}

	public ArrayList<Lane> getOriginalLanes() {
		return originalLanes;
	}

	


}





