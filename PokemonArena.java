//ArenaGame.java
//Sid Bedekar
//This is the main class for the text based pokemon game.

/*Known bugs
 *
 *goBack() doesn't go back!
 *
 *
 *
 */

import java.util.*;
import java.io.*;
public class PokemonArena{
	
	private static int POKE_NUM = 0; //number of pokemon in the data file
	private static Pokemon onArena; //the user Pokemon that is currently playing
	private static Pokemon arenaEnemy;
	//turn variables
	private static int turn; 
	private static final int USER = 0; 
	private static final int ENEMY = 1;
	
	public static void main(String[]args) throws IOException{
		Random rand = new Random();
		POKE_NUM = getPokeNum();
		ArrayList<Pokemon> pokeList = createPokemon();
		Pokemon[]chosen = selectPokemon(pokeList);
		pokeList = removeChosen(chosen, pokeList);
		Collections.shuffle(pokeList);  //pokeList is now just the list of enemies
		switchPokemon(chosen); //initial user pokemon selection
		turn = rand.nextInt(2);
		
		arenaEnemy = newEnemy(pokeList);
		while(pokeList.size() > 0){
			if (turn == USER){
				Pokemon.getStats(arenaEnemy, onArena);
				int act = action();
				if (act == 1){
					Attack atk = onArena.chooseAttack();
					onArena.doDamage(arenaEnemy, atk);
					turn = endTurn(pokeList, chosen);
					
				}
				else if(act == 2){
					switchPokemon(chosen);
					turn = endTurn(pokeList, chosen);
				}
				else if(act == 3){
					turn = endTurn(pokeList, chosen);
				}	
			}
			else{
				//AI action
				turn = endTurn(pokeList, chosen);
			}
		}
		System.out.println("Congratulations! You are indeed TRAINER SUPREME!");		
		
		
	}
	
	//method for AI action - randomly attack 
	
	public static void goBack(int n){
	//goes back to the action method
		if (n == -1){
			action();			
		}
	}	
	
	public static int action(){
		//asks the user if they want to retreat, attack or pass and returns an integer value based on choice
		//1 = attack, 2 = retreat, 3 = pass
		Scanner kb = new Scanner(System.in);
		System.out.println("__________________________");
		System.out.println("|What would you like to do?|");
		System.out.println("|1| Attack                 |");
		System.out.println("|2| Retreat                |");
		System.out.println("|3| Pass                   |");
		System.out.println("|__________________________|");
		return kb.nextInt();
	}

	public static int endTurn(ArrayList<Pokemon>pokeList, Pokemon[]chosen){
		//Toggles turns between player and enemy
		//also checks if match has ended every turn
		int newTurn;
		if (arenaEnemy.checkDeath()){
			arenaEnemy = newEnemy(pokeList);
			recharge(chosen);
		}
		if (turn == ENEMY){
			newTurn = USER;
		}
		else{
			newTurn = ENEMY;
		}
		return newTurn;
	}
	
	public static void recharge(Pokemon[]chosen){
		for (Pokemon p : chosen){
				p.recharge();
		}
	}
	
	public static Pokemon newEnemy(ArrayList<Pokemon>pokeList){
		if (arenaEnemy == null){
			return pokeList.get(0);
		}
		else{
			return pokeList.get(pokeList.indexOf(arenaEnemy) + 1);
		}
	}
	
	public static void switchPokemon(Pokemon[] chosen){
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
				word[3], word[4], Integer.parseInt(word[5]),"", attacks);
			pokeList.add(newPokemon);
		}
		inFile.close();
		return pokeList;
	}
	
	public static int getPokeNum() throws IOException{
		//returns the number of pokemon in the data file.
		Scanner inFile = new Scanner(new BufferedReader(new FileReader("pokemon.txt")));
		int n = Integer.parseInt(inFile.nextLine());
		return 	n;
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
	
	public static ArrayList<Pokemon> removeChosen(Pokemon[]chosen, ArrayList<Pokemon>pList){
		//removes the chosen Pokemons from the main pokemon list
		for (Pokemon i: chosen){
			pList.remove(i);
		}
		return pList;
	}
		
		
		
		
		
			
}