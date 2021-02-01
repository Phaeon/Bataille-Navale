package fr.Jack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Cette classe s'occupe de la gestion d'un fichier.
 * 
 * @author jackhogg
 * @version 1.0
 */
public class FichierSauvegarde {

	private String fileName;

	public FichierSauvegarde(String fileName) {
		File f = new File(fileName);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				System.out.println("Cr\u00e9ation du fichier " + fileName + " impossible.");
			}
		}

		this.fileName = fileName;
	}

	/**
	 * Permet d'enregistrer des données dans un fichier.
	 * 
	 * @param grilles Liste associée de grilles
	 * @param game Jeu (interne)
	 * @param bataille Jeu (interface)
	 * @throws IOException Dans le cas d'une mauvaise gestion du fichier.
	 */
	public void sauver(HashMap<String, int[][]> grilles, GameAPI game, BatailleNavale bataille) throws IOException {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new FileWriter(this.fileName));

			out.print("Joueur");
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					out.print("," + grilles.get("Joueur")[i][j]);
				}
			}
			for (int i = 0; i < game.getUtilisables().size(); i++) {
				out.print("," + game.getUtilisables().get(i + 1));
			}
			out.println();

			out.print("Ordinateur");
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					out.print("," + grilles.get("Ordinateur")[i][j]);
				}
			}
			for (int i = 0; i < game.getUtilisablesAdv().size(); i++) {
				out.print("," + game.getUtilisablesAdv().get(i + 1));
			}
			out.println();
			out.print("Stats," + bataille.getGameTimer().getSeconds() + "," + bataille.getCoups() + "," + bataille.getMode());
			out.println();
			
			out.print("Cases");
			for (int i = 0; i < bataille.getCases().size(); i++) {
				out.print("," + bataille.getCases().get(i)[0] + " " + bataille.getCases().get(i)[1]);
			}
			out.println();
			
			out.print("ViesJoueur");
			for (int i = 0; i < game.getBateaux().size(); i++) {
				out.print("," + game.getBateaux().get(i).getVies());
			}
			out.println();
			
			out.print("ViesAdverse");
			for (int i = 0; i < game.getBateauxAdv().size(); i++) {
				out.print("," + game.getBateauxAdv().get(i).getVies());
			}
			out.println();

		} finally {
			if (out != null)
				out.close();
		}
	}

	/**
	 * Permet de lire les données d'un fichier.
	 * 
	 * @return Une liste d'objets, ajoutées lors de la lecture du fichier.
	 * @throws IOException Dans le cas d'une mauvaise gestion du fichier.
	 */
	public ArrayList<Object> lire() throws IOException {
		BufferedReader in = null;
		ArrayList<Object> objets = new ArrayList<Object>();
		HashMap<String, int[][]> grilles = new HashMap<String, int[][]>();
		HashMap<Integer, Boolean> bombes = new HashMap<Integer, Boolean>(), bombesAdv = new HashMap<Integer, Boolean>();
		ArrayList<int[]> listeCases = new ArrayList<int[]>();
		ArrayList<Integer> vies = new ArrayList<Integer>(), viesAdv = new ArrayList<Integer>();
		int temps = 0, coups = 0, mode = 0;

		try {
			in = new BufferedReader(new FileReader(this.fileName));

			String ligne = null;
			while ((ligne = in.readLine()) != null) {
				String[] l = ligne.split(",");

				if (l[0].equals("Joueur")) {
					int[][] grille = new int[10][10];

					for (int i = 0; i < 10; i++) {
						for (int j = 0; j < 10; j++) {
							grille[i][j] = Integer.parseInt(l[(i * 10 + j) + 1]);
						}
					}
					grilles.put(l[0], grille);

					for (int i = 0; i < 3; i++) {

						bombes.put(i + 1, Boolean.parseBoolean(l[101 + i]));
					}
				} else if (l[0].equals("Ordinateur")) {
					int[][] grille = new int[10][10];

					for (int i = 0; i < 10; i++) {
						for (int j = 0; j < 10; j++) {
							grille[i][j] = Integer.parseInt(l[(i * 10 + j) + 1]);
						}
					}
					grilles.put(l[0], grille);

					for (int i = 0; i < 3; i++) {
						bombesAdv.put(i + 1, Boolean.parseBoolean(l[101 + i]));
					}
				} else if (l[0].equals("Stats")) {
					temps = Integer.parseInt(l[1]);
					coups = Integer.parseInt(l[2]);
					mode = Integer.parseInt(l[3]);
				} else if (l[0].equals("Cases")){
					String[] l2;
					int[] ent;
					for(int i = 1; i < l.length; i++) {
						ent = new int[2];
						l2 = l[i].split(" ");
						ent[0] = Integer.parseInt(l2[0]);
						ent[1] = Integer.parseInt(l2[1]);
						listeCases.add(ent);
					}
				}else if (l[0].equals("ViesJoueur")) {
					for (int i = 1; i < l.length; i++) {
						vies.add(Integer.parseInt(l[i]));
					}
				}else {
					for (int i = 1; i < l.length; i++) {
						viesAdv.add(Integer.parseInt(l[i]));
					}
				}

			}

			objets.add(grilles);
			objets.add(bombes);
			objets.add(bombesAdv);
			objets.add(temps);
			objets.add(coups);
			objets.add(mode);
			objets.add(listeCases);
			objets.add(vies);
			objets.add(viesAdv);

		} finally {
			if (in != null)
				in.close();
		}

		return objets;
	}

	/**
	 * Permet d'effacer le contenu du fichier.
	 */
	public void effacer() {
		File f = new File(this.fileName);
		f.delete();
	}

	/**
	 * Vérifie si le fichier est vide ou inexistant.
	 * @return L'état du fichier
	 */
	public boolean isEmpty() {
		File f = new File(this.fileName);
		return f.length() == 0 || !f.exists();
	}

}
