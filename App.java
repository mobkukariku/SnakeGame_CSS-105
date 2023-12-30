import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class App extends Application{

    static int BlockSize= 40;
    int width = 15;
    int height = 15;

    int zi = 3;
    int bestScore =0 ;

    long then = System.nanoTime();

    boolean ChangedValue = false;
    int NextUpdate;
    boolean HasNext = false;

    Field field;
    File saveScore = new File("bestScore.txt");

    @Override
    public void start(Stage game){
        bestScore = loadBestScore();
        VBox Root = new VBox(10);

        Root.setPadding(new Insets(10));
        

        field = new Field(width, height);
        field.addSnake(new Snake(zi, field));


        Label score = new Label("Score: 0");


        AnimationTimer time = new AnimationTimer() {
            public void handle(long now){
                if(now - then>(int)(1000000000/field.curTime)){
                    field.update();
                    then = now;
                    score.setText("Score: "+field.Score);
                    score.setFont(Font.font("Minecraft", 16));
                    ChangedValue = false;
                    if(HasNext){
                    SetDirection(field.snake, NextUpdate);
                        HasNext = false;

                    }
                    if(field.isDead()){
                        stop();
                        if(field.Score>bestScore){
                                bestScore = field.Score;
                                saveBestScore(bestScore);
                            }
                        Alert alertt = new Alert(AlertType.INFORMATION);
                        alertt.setHeaderText("😎Game Over 😎");
                        alertt.setContentText("🔹Your Score is: "+field.Score+" 🔹"+"\n🏆Best Score: "+ bestScore+" 🏆");
                        Platform.runLater(alertt::showAndWait);

                        alertt.setOnHidden(e->{
                            Root.getChildren().clear();
                            field=new Field(width, height);
                            field.addSnake(new Snake(zi, field));
                            score.setText("Score: 0");
                            Root.getChildren().addAll(field, score);
                            score.setFont(Font.font("Minecraft Rus", 16));

                            start();
                        });
                    }
                }
            }
        };
        time.start();


        
        Root.getChildren().addAll(field, score);

        Scene sc = new Scene(Root);

        sc.setOnKeyPressed(e ->{
            if((e.getCode().equals(KeyCode.W) || e.getCode().equals(KeyCode.UP)) && field.snake.getDirection() != Block.DOWN){
            SetDirection(field.snake, Block.UP);
            Snake.updateHeadDirection();
            }
            if((e.getCode().equals(KeyCode.S ) || e.getCode().equals(KeyCode.DOWN)) && field.snake.getDirection() != Block.UP){
            SetDirection(field.snake, Block.DOWN);
            Snake.updateHeadDirection();
            }
            if((e.getCode().equals(KeyCode.D) || e.getCode().equals(KeyCode.RIGHT)) && field.snake.getDirection() != Block.LEFT){
            SetDirection(field.snake, Block.RIGHT);
            Snake.updateHeadDirection();
            }
            if((e.getCode().equals(KeyCode.A) || e.getCode().equals(KeyCode.LEFT)) && field.snake.getDirection() != Block.RIGHT){
            SetDirection(field.snake, Block.LEFT);
            Snake.updateHeadDirection();
            }
        });
        game.setResizable(false);
        game.setScene(sc);
        game.setTitle("SNAKE GAME");
        game.show();
    }

    public void SetDirection(Snake s, int d){
        if(!ChangedValue){
            s.setDirection(d);
            ChangedValue=true;
        }else{
            HasNext = true;
            NextUpdate = d;
        }
    }

    public void saveBestScore(int bestScore) {
        try {
            if (!saveScore.exists()) {
                saveScore.createNewFile();
            }
            FileWriter writer = new FileWriter(saveScore);
            writer.write(String.valueOf(bestScore));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int loadBestScore() {
    int loadedBestScore = 0;
    try {
        if (saveScore.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(saveScore));
            String line = reader.readLine();
            if (line != null && !line.isEmpty()) {
                loadedBestScore = Integer.parseInt(line.trim());
            }
            reader.close();
        }
    } catch (IOException | NumberFormatException e) {
        System.out.println("ERRORMAN" + e.getMessage());
    }
    return loadedBestScore;
}
    public static void main(String[] args){
        launch(args);
    }

}