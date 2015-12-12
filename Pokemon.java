//Pokemon.java
//Sid Bedekar
//The pokemon class allows for the creation of a Pokemon object. It focuses on storing pokemon stats.
import java.util.*;

public class Pokemon{
	private double hp, maxHp, energy;
	private int attackNum;
	public String name, type, resistance, weakness;
	public ArrayList<Attack>attacks;
	
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
	
	public static void getStats(ArrayList<Pokemon>pList){
		//presents the list of Pokemon in a nice table
		System.out.printf(
			"%-2s|%-10s|%-5s|%-10s|%-10s|%-10s|\n",
			 "#","NAME", "HP", "TYPE", "RESISTANCE", "WEAKNESS");
		for (Pokemon poke : pList){		
			if (poke.attacks.size() == 1){
				System.out.printf(
				"%-2s|%-10s|%5.1f|%-10s|%-10s|%-10s|\n",pList.indexOf(poke), poke.name, poke.hp, poke.type, poke.resistance, poke.weakness);		
			}
			else if (poke.attacks.size() == 2){
				System.out.printf(
				"%-2s|%-10s|%5.1f|%-10s|%-10s|%-10s|\n",pList.indexOf(poke), poke.name, poke.hp, poke.type, poke.resistance, poke.weakness);
			}
			else if(poke.attacks.size() == 3){
				System.out.printf(
				"%-2s|%-10s|%5.1f|%-10s|%-10s|%-10s|\n",pList.indexOf(poke), poke.name, poke.hp, poke.type, poke.resistance, poke.weakness);			
			}
		}	
	}
	
	
	
	
	
	
	
}
