//Attack.java
//Sid Bedekar
//Create Pokemon Attack objects.

public class Attack{
	private int damage, cost;
	private String name, special;
	
	public Attack(String name, int cost, int damage, String special){
		this.name = name;
		this.damage = damage;
		this.cost = cost;
		this.special = special;
	}
	
	public String getName(){return this.name;}
	public int getCost(){return this.cost;}
	public int getDamage(){return this.damage;}
	public void setDamage(int n){
		this.damage = n;
	}
	public String getSpecial(){return this.special;}



}