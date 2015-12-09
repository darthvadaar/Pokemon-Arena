//ArenaGame.java
//Sid Bedekar
//This is the main file for the text based pokemon game.
import java.util.*;
import java.io.*;
public class PokemonArena{
	
	private static final int pokeNum = getPokeNum(); 
	
	public static void main(String[]args) throws IOException{
		ArrayList<Pokemon> pokeList = createPokemon(); //main pokemon list
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
			ArrayList<String> attack = new ArrayList<String>();
			ArrayList<Double> cost = new ArrayList<Double>();
			ArrayList<Double> dmg = new ArrayList<Double>();
			ArrayList<String> special = new ArrayList<String>();
			
			for (int i = 6; i < word.length; i++){
				if ((i - 2) % 4 == 0){ //elements at index 6, 10, 14...(adding four starting at 6) are names
					attack.add(word[i]);					
				}
				else if((i - 3) % 4 == 0){//attack costs (7,11,15)
					cost.add(Double.parseDouble(word[i]));
				}
				else if ((i - 4) % 4 == 0){//attack damages(8,12,16)
					dmg.add(Double.parseDouble(word[i]));
				}
				else if ((i - 5) % 4 == 0){//attack specials(9,13,17)
					special.add(word[i]);
				}
			}
			Pokemon newP = new Pokemon(word[0], Double.parseDouble(word[1]),word[2], 
				word[3], word[4], Integer.parseInt(word[5]), attack, cost, dmg, special);
			pokeList.add(newP);
		}
		inFile.close();
		return pokeList;
	}
	
	public static int getPokeNum(){
		//number of pokemon in data file
		Scanner inFile = new Scanner(new BufferedReader(new FileReader("pokemon.txt")));
		int a = Integer.parseInt(inFile.nextLine());
		return 	a;
	}
	
	public static Pokemon[] selectPokemon(ArrayList<Pokemon>pList){
		//displays a list of Pokemon and asks the user to select 4.
		//returns an ArrayList of chosen Pokemon and an ArrayList of remaining Pokemon.
		Scanner kb = new Scanner(System.in);
		Pokemon.getStats(pList);
		int choice = 0;
		Pokemon[]chosen = new Pokemon[4];
		for (int n = 4; n > 0; n--){
			while (true){ //keep asking if duplicate
				System.out.printf("Select a Pokemon by number and press Enter. (%d)",n);
				choice = kb.nextInt();
				if (choice > 0 && choice <= pokeNum && Arrays.asList(chosen).indexOf(pList.get(choice)) == -1){
					break;	//break if not duplicate				
				}
			}
			chosen[4 - n] = pList.get(choice);
		}
		//call method that removes chosen from full pokelist		
		return chosen;
	}
	
	public static ArrayList<Pokemon> removeChosen(Pokemon[]chosen, ArrayList<Pokemon>pList){
		//removes the chosen Pokemon from the main pokemon list
		for (Pokemon i: chosen){
			pList.remove(i);
		}
		return pList;
	}		
		
		
			
}