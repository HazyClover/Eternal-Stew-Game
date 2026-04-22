package main;

import entity.Player;
import harvestable.SuperHarvestable;
import stew.SuperStew;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 40;
    public final int maxWorldRow = 30;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    int FPS = 60;

    public int gameLifetime;

    // Initialise Shit
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    public UI ui = new UI(this);
    Thread gameThread;
    public CollisionDetector collisionDet = new CollisionDetector(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Player player;
    public SuperHarvestable[] obj = new SuperHarvestable[10];
    public SuperStew[] stewPots = new SuperStew[1];

    {
        try {
            player = new Player(this, keyH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.gray);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){
        aSetter.setObject();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void threadSleep(long time) {
        //System.out.println("THREADSLEEP: Thread has been asked to sleep for: " + ((double)time/1000) + " seconds.");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println("THREADSLEEP: FAILED TO SLEEP");
            throw new RuntimeException(e);
        }
    }
    public void run() {

        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                if(player.isSleeping) {
                    threadSleep(player.callThreadSleep());
                    player.isSleeping = false;
                }
                delta--;

                drawCount++;
            }

            if(timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
                gameLifetime++;
            }

        }
    }

    public void update() {

        player.update();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        tileM.draw(g2);

        for (SuperHarvestable superHarvestable : obj) {
            if (superHarvestable != null) {
                superHarvestable.draw(g2, this);
            }
        }

        for (SuperStew superStew : stewPots) {
            if (superStew != null) {
                superStew.draw(g2, this);
            }
        }

        player.draw(g2);

        ui.draw(g2);
        g2.dispose();
    }
}
