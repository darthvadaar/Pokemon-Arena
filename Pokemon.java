//Pokemon.java
//Sid Bedekar
//The pokemon class allows for the creation of a Pokemon objects. It focuses on storing, displaying and changing Pokemon stats.
import java.util.*;

public class Pokemon{
	private int attackNum, hp, maxHp, energy;
	private String name, type, resistance, weakness, affect;
	private boolean stun, disable;
	private ArrayList<Attack>attacks;
	
	public Pokemon(String name, int hp, String type, String resistance, String weakness, int attackNum, ArrayList<Attack>attacks){
		this.name = name;
		this.hp = hp;
		this.maxHp = hp;
		this.energy = 50;
		this.type = type;
		this.resistance = resistance;
		this.weakness = weakness;
		this.attackNum = attackNum;

		this.attacks = attacks;
		this.stun = false;
		this.disable = false;
	}
	
	public boolean getStun(){return this.stun;} 
	public void setStun(boolean a){this.stun = a;}
	
	public boolean getDisable(){return this.disable;}
	public void setDisable(boolean ans){
		this.disable = false;
	}
	
	public void setEnergy(int n){
		this.energy = n;
	}
	
	public String getName(){return this.name;}
	
	public boolean checkDeath(){
		//checks if pokemon is dead or alive
		if (this.hp <= 0){
			return true;
		}
		else{
			return false;
		}	
	}
	
	public void rechargeHp(){
		//recharges the pokemon's hp
		if (this.hp > 0){
			this.hp += 20;
		}
		this.limitRecharge();
	}
	
	public void rechargeEnergy(){
		//recharges the pokemon's energy
		if (this.energy < 50){
			this.energy += 10;
		}
		this.limitRecharge();
	}
	
	public void limitRecharge(){
		//makes sure hp & energy doesn't exceed max
		if (this.hp > this.maxHp){
			this.hp = this.maxHp;
		}
		else if(this.hp < 0){
			this.hp = 0;			
		}
		if (this.energy > 50){
			this.energy = 50;
		}
		else if(this.energy < 0){
			this.energy = 0;
		}
	}
	
	
	public static Pokemon switchPokemon(Pokemon[]chosen){
		//allows the user to switch the pokemon on the arena
		Scanner kb = new Scanner(System.in);
		int newIndex = -1;
		while (true){
			System.out.println(">Select a new Pokemon to send onto the Arena.");
			Pokemon.getStats(chosen);
			newIndex = kb.nextInt();
			if (newIndex >=0 && newIndex < 4){
				if(chosen[newIndex].hp > 0){
					break;
				}
				else{
					System.out.println(">That pokemon is 'unconcious'. Try again.");
				}
			}
			else{
				System.out.println(">Invalid command. Try again.");
			}
		}
		System.out.printf(">%s, I choose you! \n", chosen[newIndex].name);
		return chosen[newIndex];
	}
	
	public void enemyAction(Pokemon onArena){
		Random rand = new Random();
		ArrayList<Attack> canUse = new ArrayList<Attack>();
		for (Attack a : this.attacks){
			if (a.getCost() < this.energy){ //arraylist of possible attacks (cost wise)
				canUse.add(a);
			}
		}
		if (canUse.size() > 0){
			Collections.shuffle(canUse);
			System.out.printf(">Enemy %-5s used %-5s!.\n",this.name, canUse.get(0).getName());
			this.doDamage(onArena, canUse.get(0));
		}
		else{
			System.out.println(">The enemy has passed.");
		}
	}
	
	public void doDamage(Pokemon onto, Attack atk){ //this = attacker, onto = being attacked
		if (atk.getSpecial().equals(" ")){	//no affect
			this.basicDamage(onto, atk);
		}
		else if(atk.getSpecial().equals("stun")){
			this.stun(onto, atk);
		}
		else if(atk.getSpecial().equals("disable")){
			this.disable(onto, atk);
		}
		else if(atk.getSpecial().equals("recharge")){
			this.setEnergy(this.energy + 20);
			this.limitRecharge();
		}
		else if(atk.getSpecial().equals("wild card")){
			this.wildCard(onto, atk);
		}
		else if(atk.getSpecial().equals("wild storm")){
			this.wildStorm(onto, atk);
		}	
	}
	
	public void stun(Pokemon onto, Attack atk){
		//applies the stun effect on the pokemon
		Random rand = new Random();
		basicDamage(onto, atk);
		if (rand.nextBoolean()){
			onto.stun = true;
			System.out.println(">STUN HIT!");
		}
		else{
			System.out.println(">MISS!");
		}
	}
	
	public void disable(Pokemon onto, Attack atk){
		//applies the disable effect on the pokemon
		basicDamage(onto, atk);
		onto.disable = true;
		for(Attack a : onto.attacks){
			if (a.getDamage() > 9){
				a.setDamage(a.getDamage() - 10);
			}
		}
	}
	public void wildCard(Pokemon onto, Attack atk){
		//carries out the wildcard attack
		Random rand = new Random();
		if (rand.nextBoolean()){
			basicDamage(onto, atk);
			System.out.println(">HIT!");
		}
		else{
			System.out.println(">MISS!");
		}
	}
	
	public void wildStorm(Pokemon onto, Attack atk){
		//carries out the wildstorm attack
		Random rand = new Random();
		if (rand.nextBoolean()){
			System.out.println(">HIT!");
			basicDamage(onto, atk);
			this.energy += atk.getCost();	//cost is subtracted in basicDamage(), this resets it back
			this.wildStorm(onto,atk);
		}
		else{
			System.out.println(">MISS!");
		}
		this.energy -= atk.getCost(); //subtracts cost ONCE at the end
	}
	
	public void basicDamage(Pokemon onto, Attack atk){
		//does the basic damage (still takes weaknesses and resistances into account)
		if (this.type.equals(onto.weakness)){
			onto.hp -= atk.getDamage()*2;
		}
		else if (this.type.equals(onto.resistance)){
			onto.hp -= atk.getDamage()/2;
		}
		else{
			onto.hp -= atk.getDamage();
		}
		this.energy -= atk.getCost();
	}
	
	public boolean canAttack(){
		//checks whether pokemon is able to carry out any attacks
		for (Attack a : this.attacks){
			if (a.getCost() <= this.energy ){
				return true;
			}			
		}
		return false;
	}
	
	public void showAttacks(){
		//presents the attacks of the Pokemon in a nice table
		System.out.println("__________________________________________");
		System.out.printf(
			"%-2s|%-15s|%-5s|%-10s|%-10s|\n",
			 "#","NAME", "COST", "DAMAGE", "SPECIAL");
		for (Attack atk : this.attacks){
			System.out.printf(
				"%-2d|%-15s|%-5d|%-10d|%-10s|\n",this.attacks.indexOf(atk),
			 	atk.getName(), atk.getCost(), atk.getDamage(), atk.getSpecial());
		}
		System.out.println("__________________________________________");
	}
	
	public Attack chooseAttack(){
		//allows the user to choose an attack and returns it.
		Scanner kb = new Scanner(System.in);
		Attack newAttack = null;
		while(true){
			System.out.println(">Which Attack would you like to use?");
			this.showAttacks();
			int atkIndex = kb.nextInt();
			if(atkIndex < this.attacks.size() && atkIndex >= 0){
				newAttack = this.attacks.get(atkIndex);
				if (newAttack.getCost() <= this.energy){
					return newAttack;
				}
				else{
				System.out.println(">Not enough energy for this attack. Try again.");
				}
			}
			else{
				System.out.println(">Invalid command. Try again.");
			}
		}
	}
	
	//________________________________________________________Get Statistics methods_____________________________________________
	
	public static void getStats(ArrayList<Pokemon>pList){
		//presents the list of Pokemon in a nice table
		System.out.println("_____________________________________________________");
		System.out.printf(
			"%-2s|%-10s|%-5s|%-10s|%-10s|%-10s|\n",
			 "#","NAME", "HP", "TYPE", "RESISTANCE", "WEAKNESS");
		for (Pokemon poke : pList){		
			System.out.printf(
			"%-2s|%-10s|%-5d|%-10s|%-10s|%-10s|\n",pList.indexOf(poke),
			 poke.name, poke.hp, poke.type, poke.resistance, poke.weakness);		
		}
		System.out.println("_____________________________________________________");
	}
	
	public static void getStats(Pokemon enemy, Pokemon ally){
		//presents the Pokemon's stats in a nice table
		String enemyStatus = enemy.updateStatus();
		String allyStatus = ally.updateStatus();
		System.out.println("_________________________________________________________________________________________________________");
		System.out.println("|*|                      ENEMY                     |*|                       YOU                      |*|");
		System.out.printf(
		"|*|%-10s|%-7s|%-7s|%-10s|%-10s|*|%-10s|%-7s|%-7s|%-10s|%-10s|*|\n",
		 "NAME", "HP", "ENERGY", "TYPE", "AFFECT","NAME", "HP","ENERGY", "TYPE", "STATUS");	
		System.out.printf("|*|%-10s|%-3d/%-3d|%-3d/%-3d|%-10s|%-10s|*|%-10s|%-3d/%-3d|%-3d/%-3d|%-10s|%-10s|*|\n",
		enemy.name, enemy.hp, enemy.maxHp, enemy.energy, 50, enemy.type, enemyStatus, ally.name, ally.hp, ally.maxHp, ally.energy, 50, ally.type, allyStatus);
		System.out.println("_________________________________________________________________________________________________________");
	}
	
	public String updateStatus(){
		//chnges the status to be displayed in getStats()
		String nstat;
		if(this.disable){
			nstat = "Disabled";
		}
		else if(this.stun){
			nstat = "Stunned";			
		}
		else{
			nstat = "Normal";
		}
		return nstat;
	}
	
	public static void getStats(Pokemon[]pList){
		//presents the list of Pokemon in a nice table
		System.out.println("_____________________________________________________");
		System.out.printf(
			"%-2s|%-10s|%-5s|%-10s|%-10s|%-10s|\n",
			 "#","NAME", "HP", "TYPE", "RESISTANCE", "WEAKNESS");
		for (Pokemon poke : pList){
			System.out.printf(
			"%-2s|%-10s|%-5d|%-10s|%-10s|%-10s|\n",Arrays.asList(pList).indexOf(poke),
			 poke.name, poke.hp, poke.type, poke.resistance, poke.weakness);		
		}
		System.out.println("_____________________________________________________");
	}
	
}
