import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;


import javax.swing.JFrame;


public class Game  extends Canvas implements Runnable,KeyListener{
	
	public int tamanho = 9 ;
	public Node[] nodeSnake = new Node [tamanho];
	
	
	public Config conf = new Config() ;
	
	public boolean left,right,up,down;
	
	
	public Game() {
		this.setPreferredSize(new Dimension(480,480));
		for(int i= 0; i < nodeSnake.length; i++) {
			nodeSnake[i]= new Node(0,0);		
			}
		this.addKeyListener(this);
	}
	
	
	public void tick() {
		for(int i = nodeSnake.length - 1; i > 0; i--) {
			nodeSnake[i].x = nodeSnake[i-1].x;
			nodeSnake[i].y = nodeSnake[i-1].y;
		}
		if(nodeSnake[0].x + 10 < 0) {
			conf.nivel = 1;
			conf.spd = 1 ;
			tamanho = 10; 
			System.out.println(" Game Over " );
			System.out.println("Nivel: " + conf.nivel);
			System.out.println("Tamanho: " + tamanho);
			nodeSnake[0].x = 470;
		}else if(nodeSnake[0].x >= 480) {
			conf.nivel = 1;
			conf.spd = 1 ;
			tamanho = 10; 
			System.out.println(" Game Over " );
			System.out.println("Nivel: " + conf.nivel);
			System.out.println("Tamanho: " + tamanho);
			nodeSnake[0].x = -10;
		}else if(nodeSnake[0].y + 10 < 0) {
			conf.nivel = 1;
			conf.spd = 1 ;
			tamanho = 10; 
			System.out.println(" Game Over " );
			System.out.println("Nivel: " + conf.nivel);
			System.out.println("Tamanho: " + tamanho);
			nodeSnake[0].y = 480;
		}else if(nodeSnake[0].y >= 480) {
			conf.nivel = 1;
			conf.spd = 1 ;
			tamanho = 10; 
			System.out.println(" Game Over " );
			System.out.println("Nivel: " + conf.nivel);
			System.out.println("Tamanho: " + tamanho);
			nodeSnake[0].y = -10;
		}
		

		/*if(nodeSnake[0].x + 10 < 0) {
			nodeSnake[0].x = 470;
		}else if (nodeSnake[0].x >= 480) {
			nodeSnake[0].x = -10;
		}
		if(nodeSnake[0].y + 10 < 0) {
			nodeSnake[0].y = 480;
		}else if (nodeSnake[0].y >= 480) {
			nodeSnake[0].y = -10;
		}*/
		
		
		
		if(right) {
			nodeSnake[0].x+=conf.spd;
		}else if (up) {
			nodeSnake[0].y-=conf.spd;
		}else if (down) {
			nodeSnake[0].y+=conf.spd;
		}else if(left) {
			nodeSnake[0].x-=conf.spd;
		}
		
		if(new Rectangle(nodeSnake[0].x,nodeSnake[0].y,10,10) .intersects(new Rectangle(conf.ratX,conf.ratY,10,10))) {
			conf.ratX = new Random().nextInt(480-10);
			conf.ratY= new Random().nextInt(480-10);
			conf.nivel++;
			conf.spd++;
			tamanho++;
			
			System.out.println("Nivel: " + conf.nivel);
			System.out.println("Tamanho: " + tamanho);

		}
	
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g= bs.getDrawGraphics();
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, 480, 480);
		for(int i = 0; i < nodeSnake.length; i++) {
			g.setColor(Color.red);
			g.fillRect(nodeSnake[i].x, nodeSnake[i].y, 10, 10);
		}
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(conf.ratX,conf.ratY, 10, 10);
		
		g.dispose();
		bs.show();
		
	}
	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame ("Snake");
		frame.add(game);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		new Thread(game).start();
	}
	@Override
	public void run () {
		
		while(true){
			tick();
			render();
			try {
				Thread.sleep(1000/60);
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
			left = false;
			up = false;
			down = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
			right = false;
			up = false;
			down = false;
		}else if (e.getKeyCode() == KeyEvent.VK_UP) {
			up = true;
			right = false;
			left = false;
			down = false;
		}else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = true;
			right = false;
			left = false;
			up = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}

}
