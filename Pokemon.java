//Pokemon.java
//Sid Bedekar
//The pokemon class allows for the creation of a Pokemon object. It focuses on storing pokemon stats.
import java.util.*;

public class Pokemon{
	private double hp, maxHp, energy;
	private int attackNum;
	public String name, type, resistance, weakness;
	private ArrayList<Attack>attacks;
	
	public Pokemon(String name, double hp, String type, String resistance, String weakness, int attackNum, ArrayList<Attack>attacks){
		this.name = name;
		this.hp = hp;
		this.maxHp = hp;
		this.energy = 50;
		this.type = type;
		this.resistance = resistance;
		this.weakness = weakness;
		this.attackNum = attackNum;
		
		this.attacks = attacks;
	}
	
	public static Pokemon switchPokemon(Pokemon[]chosen){
		//allows the user to switch the pokemon on the arena
		Scanner kb = new Scanner(System.in);
		int newIndex = -1;
		while (true){
			System.out.println("Select a new Pokemon to send onto the Arena...");
			Pokemon.getStats(chosen);
			newIndex = kb.nextInt();
			if (newIndex >=0 && newIndex < 4){//!!!!!!!!!!!!!!!!!!!!!!!!!add more exceptions like if p is dead or something
				break;
			}	
		}
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
			"%-2s|%-10s|%5.1f|%-10s|%-10s|%-10s|\n",pList.indexOf(poke),
			 poke.name, poke.hp, poke.type, poke.resistance, poke.weakness);		
		}
		System.out.println("_____________________________________________________");
	}
	
	public static void getStats(Pokemon enemy, Pokemon ally){
		//presents the Pokemon's stats in a nice table
		System.out.println("_______________________________________________________________");
		System.out.println("|*|           ENEMY           |*|            YOU            |*|");
		System.out.printf(
		"|*|%-10s|%-5s|%-10s|*|%-10s|%-5s|%-10s|*|\n",
		 "NAME", "HP", "TYPE","NAME", "HP", "TYPE");	
		System.out.printf("|*|%-10s|%-5s|%-10s|*|%-10s|%-5s|%-10s|*|\n",
		enemy.name, enemy.hp, enemy.type, ally.name, ally.hp, ally.type);
		System.out.println("_______________________________________________________________");
	}
	
	public static void getStats(Pokemon[]pList){
		//presents the list of Pokemon in a nice table
		System.out.println("_____________________________________________________");
		System.out.printf(
			"%-2s|%-10s|%-5s|%-10s|%-10s|%-10s|\n",
			 "#","NAME", "HP", "TYPE", "RESISTANCE", "WEAKNESS");
		for (Pokemon poke : pList){
			System.out.printf(
			"%-2s|%-10s|%5.1f|%-10s|%-10s|%-10s|\n",Arrays.asList(pList).indexOf(poke),
			 poke.name, poke.hp, poke.type, poke.resistance, poke.weakness);		
		}
		System.out.println("_____________________________________________________");
	}
	
	
	
	
	
	
	
	
	
}
