//Pokemon.java
//Sid Bedekar
//The pokemon class allows for the creation of a Pokemon object. It focuses on storing pokemon stats.
import java.util.*;

public class Pokemon{
	private int attackNum, hp, maxHp, energy;
	private String name, type, resistance, weakness, affect;
	private ArrayList<Attack>attacks;
	
	public Pokemon(String name, int hp, String type, String resistance, String weakness, int attackNum, String affect, ArrayList<Attack>attacks){
		this.name = name;
		this.hp = hp;
		this.maxHp = hp;
		this.energy = 50;
		this.type = type;
		this.resistance = resistance;
		this.weakness = weakness;
		this.attackNum = attackNum;
		this.affect = affect;
		
		this.attacks = attacks;
	}
	
	//________________________________________________________get/set methods_____________________________________________
	
	public String getAffect(){return this.affect;}
	public void resetAffect(){this.affect = "none";}
	
	public boolean checkEnemyDeath(){
		if (this.hp <= 0){
			System.out.printf("%-10s has fainted! You have advanced to the next match!\n", this.name);
			return true;
		}
		else{
			return false;
		}	
	}
	
	public boolean checkPlayerDeath(){
		if (this.hp <= 0){
			System.out.printf("%-10s has fainted! That's too bad!\n", this.name);
			return true;
		}
		else{
			return false;
		}	
	}
	
	public void recharge(){
		//recharges the pokemon's hp and energy
		//also makes sure hp & energy doesn't exceed max
		if (this.hp > 0){
			this.hp += 20;
			this.energy += 10;
		}
		if (this.hp > this.maxHp){
			this.hp = this.maxHp;
		}
		if (this.energy > 50){
			this.energy = 50;
		}
		
	}
	
	public static Pokemon switchPokemon(Pokemon[]chosen){
		//allows the user to switch the pokemon on the arena
		Scanner kb = new Scanner(System.in);
		int newIndex = -1;
		while (true){
			System.out.println("Select a new Pokemon to send onto the Arena. Enter '-1' to go back to menu.");
			Pokemon.getStats(chosen);
			newIndex = kb.nextInt();
			PokemonArena.goBack(newIndex);
			if (newIndex >=0 && newIndex < 4){
				if(chosen[newIndex].hp > 0){
					break;
				}
				else{
					System.out.println("That pokemon is 'unconcious'. Try again.");
				}
			}
			else{
				System.out.println("Invalid command. Try again.");
			}
		}
		System.out.printf("%s, I choose you! \n", chosen[newIndex].name);
		return chosen[newIndex];
	}
	
	//________________________________________________________Enemy AI methods_____________________________________________
	
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
			this.doDamage(onArena, canUse.get(0));
			this.energy -= canUse.get(0).getCost();
			System.out.printf("%-10s attacked! %-10s took %2d damage. .\n",this.name, onArena.name, canUse.get(0).getDamage());
		}
		else{
			System.out.println(">The enemy has passed.");
		}
	}
	
	//________________________________________________________Attack methods_____________________________________________
	public void doDamage(Pokemon onto, Attack atk){
		if (atk.getSpecial().equals(" ")){	//no affect
			basicDamage(onto, atk);
		}
		else if(atk.getSpecial().equals("stun")){
			Random rand = new Random();
			int chance = rand.nextInt(2); // 0 = no stun, 1 = stun
			if (chance == 1){
				basicDamage(onto, atk);
				onto.affect = "stun";
			}
			else{ //basic attack
				basicDamage(onto, atk);
			}
		}
		
		
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
			System.out.println("Which Attack would you like to use? Enter '-1' to go back to menu.");
			this.showAttacks();
			int atkIndex = kb.nextInt();
			if(atkIndex < this.attacks.size() && atkIndex >= 0){
				newAttack = this.attacks.get(atkIndex);
				if (newAttack.getCost() <= this.energy){
					return newAttack;
				}
				else{
				System.out.println("Not enough energy for this attack. Try again.");
				}
			}
			else{
				System.out.println("Invalid command. Try again.");
			}
		}
	}
	
	//________________________________________________________Statistics methods_____________________________________________
	
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
		System.out.println("_________________________________________________________________________________________________________");
		System.out.println("|*|                      ENEMY                     |*|                       YOU                      |*|");
		System.out.printf(
		"|*|%-10s|%-7s|%-7s|%-10s|%-10s|*|%-10s|%-7s|%-7s|%-10s|%-10s|*|\n",
		 "NAME", "HP", "ENERGY", "TYPE", "AFFECT","NAME", "HP","ENERGY", "TYPE", "AFFECT");	
		System.out.printf("|*|%-10s|%-3d/%-3d|%-3d/%-3d|%-10s|%-10s|*|%-10s|%-3d/%-3d|%-3d/%-3d|%-10s|%-10s|*|\n",
		enemy.name, enemy.hp, enemy.maxHp, enemy.energy, 50, enemy.type, enemy.affect, ally.name, ally.hp, ally.maxHp, ally.energy, 50, ally.type, ally.affect);
		System.out.println("_________________________________________________________________________________________________________");
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
