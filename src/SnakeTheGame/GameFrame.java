/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SnakeTheGame;

/**
 *
 * @author Asus
 */

    import javax.swing.JFrame;

public  class GameFrame extends JFrame{

	GameFrame(){
		this.add(new GamePanel());
		this.setSize(1400,700);
                this.setTitle("Snake with Java");
                this.setLocationRelativeTo(null);
                this.setResizable(false);
                this.setVisible(true);
                this.pack();
                this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
                
		
	}
}

