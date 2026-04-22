package stew;

import main.GamePanel;

import javax.imageio.ImageIO;

import java.io.IOException;

public class OBJ_Stew extends SuperStew{
    GamePanel gp;
    public OBJ_Stew(GamePanel gp) {
        this.gp = gp;
        try {
            imageOuter = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/StewPotTest.png"));
            imageInner = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/StewPotTestInner.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        name = "stew.OBJ_Stew";


        System.out.println("SUCCESS: LOADED + '" + name +"'!");
    }

}
