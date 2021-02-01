package fr.Jack.bateaux;

import fr.Jack.Bateau;

public class BateauTorpilleur extends Bateau{

	public BateauTorpilleur(String name, int ID) {
		super(name, ID);
	}

	@Override
	public Integer getTaille() {
		return 2;
	}
	
	@Override
	public Integer getID() {
		return this.ID;
	}
	
	@Override
	public Boolean aCoule() {
		return this.vies == 0;
	}
	
	@Override
	public Integer getVies() {
		return this.vies;
	}
	
	@Override	
	public Boolean isVertical() {
		return this.vertical;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	

}
