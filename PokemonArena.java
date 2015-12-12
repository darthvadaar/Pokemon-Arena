//ArenaGame.java
//Sid Bedekar
//This is the main file for the text based pokemon game.
import java.util.*;
import java.io.*;
public class PokemonArena{
	
	private static int POKE_NUM = 0; 
	
	public static void main(String[]args) throws IOException{
		POKE_NUM = getPokeNum();
		ArrayList<Pokemon> pokeList = createPokemon(); 
		Pokemon[]chosen = selectPokemon(pokeList);
		pokeList = removeChosen(chosen, pokeList);
		Collections.shuffle(pokeList);
		
		

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
				else if((i - 3) % 4 == 0){//attack costs at index (7,11,15)
					cost = Integer.parseInt(word[i]);
				}
				else if ((i - 4) % 4 == 0){//attack damages at index(8,12,16)
					damage = Integer.parseInt(word[i]);
				}
				else if ((i - 5) % 4 == 0){//attack specials at index(9,13,17)
					special = word[i];
					Attack newAttack = new Attack(name, cost, damage, special); //special is the last thing to be added to the list so the attack is created here
					attacks.add(newAttack);
				}
				
			}
			Pokemon newPokemon = new Pokemon(word[0], Double.parseDouble(word[1]),word[2], 
				word[3], word[4], Integer.parseInt(word[5]), attacks);
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