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
	
	public boolean checkDeath(){
		if (this.hp <= 0){
			System.out.printf("%15s has fainted! You have advanced to the next match!\n", this.name);
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public void doDamage(Pokemon onto, Attack atk ){
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
		
		//call special method
	}
	
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!make a method for the specials
	
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
			}
		}
	}
		
	public void recharge(){
		//recharges the pokemon's hp and energy
		if (this.hp > 0){
			this.hp += 20;
			this.energy += 10;
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
					
			}	
		}
		System.out.printf("%s, I choose you! \n", chosen[newIndex].name);
		return chosen[newIndex];
	}
	
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
		System.out.println("___________________________________________________________________________________");
		System.out.println("|*|                ENEMY                |*|                 YOU                 |*|");
		System.out.printf(
		"|*|%-10s|%-7s|%-7s|%-10s|*|%-10s|%-7s|%-7s|%-10s|*|\n",
		 "NAME", "HP", "ENERGY", "TYPE","NAME", "HP","ENERGY", "TYPE");	
		System.out.printf("|*|%-10s|%-3d/%-3d|%-3d/%-3d|%-10s|*|%-10s|%-3d/%-3d|%-3d/%-3d|%-10s|*|\n",
		enemy.name, enemy.hp, enemy.maxHp, enemy.energy, 50, enemy.type, ally.name, ally.hp, ally.maxHp, ally.energy, 50, ally.type);
		System.out.println("___________________________________________________________________________________");
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
