//ArenaGame.java
//Sid Bedekar
//This is the main class for the text based pokemon game. It controls the battle system and stats of the pokemon during the 
//battles in Pokemon Arena.

import java.util.*;
import java.io.*;
public class PokemonArena{
	
	private static int POKE_NUM = 0; //number of pokemon in the data file
	private static Pokemon onArena; //the user Pokemon that is currently playing
	private static Pokemon arenaEnemy;//enemy pokemon that is currently playing
	
	//turn variables
	private static int turn; 
	private static final int USER = 0; 
	private static final int ENEMY = 1;
	private static int rechargeTurn;
	
	public static void main(String[]args) throws IOException{
		Random rand = new Random();
		POKE_NUM = getPokeNum();
		ArrayList<Pokemon> pokeList = createPokemon(); //create pokemon objects
		Pokemon[]chosen = selectPokemon(pokeList);
		pokeList = removeChosen(chosen, pokeList);
		Collections.shuffle(pokeList);  //pokeList is now just the list of enemies
		switchPokemon(chosen); //initial user pokemon selection
		turn = rand.nextInt(2);
		if (turn == 0){ //determine which turn energy is recharged on
			rechargeTurn = 1;
		}
		else{
			rechargeTurn = 0;
		}
		
		arenaEnemy = newEnemy(pokeList);
		while(true){
			if (PokemonArena.checkChosenDeath(chosen) || pokeList.size() == 0 ){
				break;
			}
			if (turn == USER){
				Pokemon.getStats(arenaEnemy, onArena);
				if (onArena.getStun()){
						System.out.println(">Your Pokemon is stunned. Your turn has been skipped.");
						onArena.setStun(false);	
				}
				else{
					int act = action();
					if (act == 1){ //attack
						Attack atk = onArena.chooseAttack();
						onArena.doDamage(arenaEnemy, atk);
					}
					else if(act == 2){ //retreat
						switchPokemon(chosen);
					}
				}
			}
			else{ //AI actions
				if (arenaEnemy.getStun()){
					System.out.println(">The enemy is stunned. Enemy turn skipped.");
					arenaEnemy.setStun(false);
				}
				else{
					arenaEnemy.enemyAction(onArena);
				}	
			}
			if (turn == rechargeTurn){
				rechargeEnergy(chosen);
				arenaEnemy.rechargeEnergy();
			}
			if (PokemonArena.checkChosenDeath(chosen) || pokeList.size() == 0 ){
				break;
			}
			checkEndMatch(pokeList, chosen);
			turn = endTurn();
		}
		
		if (pokeList.size() == 0){
			System.out.println(">Congratulations! You have been crowned TRAINER SUPREME!");
			System.out.println(">----------------------GAME OVER------------------------");
		}
		else{
			System.out.println(">All of your pokemon are unconcious.");
			System.out.println(">------------GAME OVER--------------");
		}
		
	}

	public static int action(){
		//asks the user if they want to retreat, attack or pass and returns an integer value based on choice
		//1 = attack, 2 = retreat, 3 = pass
		Scanner kb = new Scanner(System.in);
		int val;
		while (true){
			System.out.println("__________________________");
			System.out.println("|What would you like to do?|");
			System.out.println("|1| Attack                 |");
			System.out.println("|2| Retreat                |");
			System.out.println("|3| Pass                   |");
			System.out.println("|__________________________|");
			val = kb.nextInt();
			if (val > 0 && val < 4){
				if (val == 1){
					if (onArena.canAttack()){
						break;	
					}
					else{
						System.out.println(">You do not have enough energy for any attacks.");
					}
				}
				else{
					break;
				}			
			}
			else{
				System.out.println(">Invalid command. Try again.");
			}
		}
		return val;
	}
	
	public static void rechargeHp(Pokemon[]chosen){
		//recharges the hp of all player pokemon
		for (Pokemon p : chosen){
				p.rechargeHp();
				p.limitRecharge();
		}
	}
	
	public static void rechargeEnergy(Pokemon[]chosen){
		//recharges the energy of all player pokemon
		for (Pokemon p : chosen){
				p.rechargeEnergy();
				p.limitRecharge();
		}
	}
	
	public static int endTurn(){
		//Toggles turns between player and enemy
		int newTurn;
		if (turn == ENEMY){
			newTurn = USER;
		}
		else{
			System.out.println(">Enemy Turn:");
			newTurn = ENEMY;
		}
		return newTurn;
	}
	
	public static void checkEndMatch(ArrayList<Pokemon>pokeList, Pokemon[]chosen){
		//end battle procedures such as recharge and new enemy/switchPokemon
		if (arenaEnemy.checkDeath()){
			System.out.printf(">%-7s has fainted! New round begins now!\n", arenaEnemy.getName());
			arenaEnemy = newEnemy(pokeList);
			rechargeHp(chosen);
			
		}
		if (onArena.checkDeath()){
			System.out.printf("%-10s has fainted! That's too bad!\n", onArena.getName());
			switchPokemon(chosen);
			rechargeHp(chosen);
		}
		onArena.setDisable(false); //disable and stun get reset at the end of a battle
	}
	
	public static boolean checkChosenDeath(Pokemon[]chosen){
		//checks if all of the player pokemon are dead
		if (chosen[0].checkDeath() && chosen[1].checkDeath() && chosen[2].checkDeath() && chosen[3].checkDeath()){
			return true;
		}
		else{
			return false;
		}	
	}
	
	public static Pokemon newEnemy(ArrayList<Pokemon>pokeList){
		//returns a new enemy from the parameter array list
		pokeList.remove(0);
		if (pokeList.size() == 0){
			return null;
			
		}
		else{
			return pokeList.get(0);
		}
	}
	
	public static void switchPokemon(Pokemon[] chosen){
		//allows the user to change their arena pokemon
		onArena = Pokemon.switchPokemon(chosen);
	}
	
	public static ArrayList<Pokemon> createPokemon() throws IOException{
		//Reads the datafile, creates Pokemon objects and returns them in a primitive array
		Scanner inFile = new Scanner(new BufferedReader(new FileReader("pokemon.txt")));
		ArrayList<Pokemon> pokeList = new ArrayList<Pokemon>();
		inFile.nextLine(); //skipping first line in the data file
		while(inFile.hasNextLine()){
			String ln = inFile.nextLine();
			String[]word = ln.split(",");
			ArrayList<Attack> attacks = new ArrayList<Attack>(); //Arraylist of attacks
			String name = "";
			String special = "";
			int damage = 0;
			int cost = 0;
			for (int i = 6; i < word.length; i++){
				if ((i - 2) % 4 == 0){ //elements at index 6, 10, 14...(adding four starting at 6) are names
					name = word[i];
				}
				else if((i - 3) % 4 == 0){//attack costs at index (7,11,15..)
					cost = Integer.parseInt(word[i]);
				}
				else if ((i - 4) % 4 == 0){//attack damages at index(8,12,16..)
					damage = Integer.parseInt(word[i]);
				}
				else if ((i - 5) % 4 == 0){//attack specials at index(9,13,17..)
					special = word[i];
					Attack newAttack = new Attack(name, cost, damage, special); //special is the last thing to be added to the list so the attack is created here
					attacks.add(newAttack);
				}
				
			}
			Pokemon newPokemon = new Pokemon(word[0], Integer.parseInt(word[1]),word[2], 
				word[3], word[4], Integer.parseInt(word[5]), attacks);
			pokeList.add(newPokemon);
		}
		inFile.close();
		return pokeList;
	}
	
	public static Pokemon[] selectPokemon(ArrayList<Pokemon>pList){
		/*displays a list of Pokemon and asks the user to select 4. 
		Returns the selected pokemon on a primitive array*/
		Scanner kb = new Scanner(System.in);
		Pokemon.getStats(pList);
		int choice = 0;
		Pokemon[]chosen = new Pokemon[4];
		for (int n = 4; n > 0; n--){
			while (true){ //keep asking if duplicate
				System.out.printf("Select a Pokemon by number and press Enter. (%d)",n);
				choice = kb.nextInt();
				if (choice >= 0 && choice <= POKE_NUM && Arrays.asList(chosen).indexOf(pList.get(choice)) == -1){
					break;	//break if not duplicate				
				}
			}
			chosen[4 - n] = pList.get(choice);
		}		
		return chosen;
	}
	
	public static int getPokeNum() throws IOException{
		//returns the number of pokemon in the data file.
		Scanner inFile = new Scanner(new BufferedReader(new FileReader("pokemon.txt")));
		int n = Integer.parseInt(inFile.nextLine());
		return 	n;
	}
	
	public static ArrayList<Pokemon> removeChosen(Pokemon[]chosen, ArrayList<Pokemon>pList){
		//removes the chosen Pokemons from the main pokemon list
		for (Pokemon i: chosen){
			pList.remove(i);
		}
		return pList;
	}
			
}