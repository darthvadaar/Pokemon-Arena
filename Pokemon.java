//Pokemon.java
//Sid Bedekar
//The pokemon class allows for the creation of a Pokemon object. It focuses on storing pokemon stats.
import java.util.*;

public class Pokemon{
	private double hp, maxHp, energy;
	private int attackNum;
	public String name, type, resistance, weakness;
	//arrays for attack information:
	private ArrayList<String>attack;
	private ArrayList<Double>cost;
	private ArrayList<Double>dmg;
	private ArrayList<String>special;
	
	public Pokemon(String name, double hp, String type, String resistance, String weakness, int attackNum,
		ArrayList<String>attack, ArrayList<Double>cost, ArrayList<Double>dmg, ArrayList<String>special){
		this.name = name;
		this.hp = hp;
		this.maxHp = hp;
		this.energy = 50;
		this.type = type;
		this.resistance = resistance;
		this.weakness = weakness;
		this.attackNum = attackNum;
		
		this.attack = attack;
		this.cost = cost;
		this.dmg = dmg;
		this.special = special;
	}
	
	public static void getStats(ArrayList<Pokemon>pList){
		//presents the list of Pokemon in a nice table
		System.out.printf(
			"%2s|%-10s|%5s|%-10s|%-10s|%-10s|%-20s|%-20s|%-20s\n",
			 "#","NAME", "HP", "TYPE", "RESISTANCE", "WEAKNESS", "ATTACK A","ATTACK B","ATTACK C"
		);
		for (Pokemon poke : pList){		
			if (poke.attack.size() == 1){
				System.out.printf(
				"%-2s|%-10s|%5.1f|%-10s|%-10s|%-10s|%-20s|%-20s|%-20s\n",pList.indexOf(poke), poke.name, poke.hp, poke.type, poke.resistance, poke.weakness, poke.attack.get(0)," "," "
				);		
			}
			else if (poke.attack.size() == 2){
				System.out.printf(
				"%-2s|%-10s|%5.1f|%-10s|%-10s|%-10s|%-20s|%-20s|%-20s\n",pList.indexOf(poke), poke.name, poke.hp, poke.type, poke.resistance, poke.weakness, poke.attack.get(0), poke.attack.get(1)," "
				);
			}
			else if(poke.attack.size() == 3){
				System.out.printf(
				"%-2s|%-10s|%5.1f|%-10s|%-10s|%-10s|%-20s|%-20s|%-20s\n",pList.indexOf(poke), poke.name, poke.hp, poke.type, poke.resistance, poke.weakness, poke.attack.get(0), poke.attack.get(1), poke.attack.get(2)
				);			
			}
		}	
	}
	
	
	
	
	//attack(Pokemon other): does damage to other pokemon, reduce cost
	
	
	
	
	
}
