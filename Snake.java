import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Snake {
    ArrayList<Block> blocks = new ArrayList<>();
    static Block headSnake;
    Block tail;
    private static Image[] headImages;

    public Snake(int il, Field field){
        int ipx = field.getW()/2;
        int ipy = field.getH()/2;
        headSnake = new Block(ipx, ipy,null, field);
        blocks.add(headSnake);

        headImages = new Image[]{
            new Image("images/headUP.png"),
            new Image("images/headRIGHT.png"),
            new Image("images/headDOWN.png"),
            new Image("images/headLEFT.png")
        };

        updateHeadDirection();

        tail = headSnake;

        for(int i = 1; i<il; i++){
            Block  b = new Block(ipx + i, ipy, tail, field);
            blocks.add(b);
            tail = b;

        }
    

    }

    public void changeDirection(int newDirection) {
        headSnake.direction = newDirection;
        updateHeadDirection(); }
    static void updateHeadDirection() {
            Image headImage = headImages[headSnake.direction];
            ImagePattern pattern = new ImagePattern(headImage);
            headSnake.setFill(pattern);
        }

        public void rotateLeft() {
            int newDirection = (headSnake.direction + 3) % 4; 
            changeDirection(newDirection); 
        }
    
        public void rotateRight() {
            int newDirection = (headSnake.direction + 1) % 4; 
            changeDirection(newDirection); 
        }


    public int getDirection(){
        return headSnake.direction;
    }

    public void setDirection(int d){
        headSnake.direction = d;
    }
}
