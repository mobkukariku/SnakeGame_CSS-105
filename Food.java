import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Food extends Rectangle{
    int position_X; 
    int position_Y;

    public int getPositionX(){
        return position_X;
    }

    public int getPositionY(){
        return position_Y;
    }

    public Food(int x, int y){
        super(App.BlockSize, App.BlockSize);
        position_X = x;
        position_Y = y;
        setTranslateX(position_X*App.BlockSize);
        setTranslateY(position_Y*App.BlockSize);
        variableFOOD();
    }

    public void variableFOOD(){
        int Random = ((int)(Math.random()*10))%7;
        Image FoodImage;
        switch (Random) {
            case 0:
                FoodImage = new Image("images/food1.png");
                break;
            case 1:
                FoodImage = new Image("images/food2.png");
                break;
            case 2:
                FoodImage = new Image("images/food3.png");
                break;
            case 3:
                FoodImage = new Image("images/food4.png");
                break;
            case 4:
                FoodImage = new Image("images/food1.png");
                break;
            case 5:
                FoodImage = new Image("images/food5.png");
                break;  
            case 6:
                FoodImage = new Image("images/food6.png");
                break;      
            default:
                FoodImage = new Image("images/food1,png");
                break;
        }
        ImagePattern pattern = new ImagePattern(FoodImage);
        setFill(pattern); 
        
    }
}