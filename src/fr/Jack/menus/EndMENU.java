package fr.Jack.menus;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import fr.Jack.BatailleNavale;
import fr.Jack.GameAPI;
import fr.Jack.exceptions.MenuLaunchException;

/**
 * Cette classe créé les caractéristiques du menu de fin.
 * 
 * @author jackhogg
 * @version 1.0
 */
public class EndMENU {
	
	private final String title = "Bataille navale";
	
	public EndMENU(BatailleNavale bataille, GameAPI game) throws MenuLaunchException {
	    
		//Fenêtre du menu
        JFrame frame= new JFrame(); 
        
        //Panel principal (en cas de création d'un nouveau panel, en plus du corps)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        //Corps de la fenêtre
        JPanel corps = new JPanel(new GridBagLayout());
        GridBagConstraints constr = new GridBagConstraints();
    
        //Eléments du corps.
        JLabel head = new JLabel("Merci d'avoir jou\u00e9 \u00e0 la bataille navale !");
        Font fontgrilleadv = new Font("Caruban",Font.BOLD,15);
		head.setFont(fontgrilleadv);
        JLabel coups = new JLabel("Vous avez effectu\u00e9 " + bataille.getCoups() + " tentatives.");
        JLabel vainqueur;
        JLabel temps = new JLabel("Temps \u00e9coul\u00e9 : " + bataille.getGameTimer().getSeconds() + " secondes.");
        JButton button = new JButton("Terminer");
        JButton menu = new JButton("Menu Principal");
        
        //Traitement
        if (game.getJoueur() == 3) { // Mis à 3 afin d'éviter que le joueur puisse jouer pendant la fin de partie
        	vainqueur = new JLabel("Bravo ! Vous avez gagn\u00e9 !");
        }else vainqueur = new JLabel("Dommage... Parfois, les machines sont meilleures.");
        
        //Ajout d'évènements sur les boutons
        button.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            frame.dispose();
          }
        });
        
        menu.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            frame.dispose();
            try {
				new MainMENU();
			} catch (MenuLaunchException e1) {
				e1.setMenu("MainMENU");
				e1.getMessage();
				e1.printStackTrace();
			}
          }
        });
        
        //Ajout des éléments en fonction du layout.
        constr.gridx = 0; constr.gridy = 0;
        constr.insets = new Insets(5, 5, 5, 5);
        constr.anchor = GridBagConstraints.CENTER;
        constr.gridheight = 3;
        constr.gridwidth = 2;
        
        corps.add(head, constr);
        constr.gridy = 3;
        constr.gridheight = 2;
        corps.add(vainqueur, constr);

        constr.gridy = 5;
        constr.gridheight = 1;
        corps.add(coups, constr);
        
        constr.gridy = 6;
        corps.add(temps, constr);
        
        constr.gridy = 7;
        constr.insets = new Insets(2, 5, 2, 5);
        corps.add(button, constr);
        constr.gridy = 8;
        corps.add(menu, constr);
 
        mainPanel.add(corps);
 
        //Création de la fenêtre
        frame.setTitle(this.title);
        frame.setIconImage(new ImageIcon("explo.png").getImage());
        frame.add(mainPanel);
        frame.pack();
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}
	


}
