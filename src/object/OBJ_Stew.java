package object;

import main.GamePanel;

import javax.imageio.ImageIO;

import java.io.IOException;

public class OBJ_Stew extends SuperObject {
    GamePanel gp;
    public OBJ_Stew(GamePanel gp) {
        this.gp = gp;
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/StewPotTest.png"));
            image2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/StewPotTestInner.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        name = "object.OBJ_Stew";
        type = "stew_test";

        System.out.println("SUCCESS: LOADED + '" + name +"'!");
    }

}
