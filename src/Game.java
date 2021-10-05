import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Game extends JPanel implements Runnable, FrameVariables {

    private BufferedImage image;
    private int[] x;
    private int[] y;
    private int snakeW = 10;
    private int snakeH = 10;
    private int snakeL = 5;
    private char direction = 'w';
    private int appleX;
    private int appleY;
    private int appleW;
    private int appleH;

    public void run(){
        init();

        long last = System.nanoTime();
        double deltaT = 0, ns = 1000000000/10.0;

        while(true){
            long now = System.nanoTime();
            deltaT += (now-last)/ns;
            last = now;

            while(deltaT >= 1){
                update();
                deltaT--;
            }

            render();
        }
    }

    private void init(){
        this.image = new BufferedImage(FrameVariables.WIDTH, FrameVariables.HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.x = new int[3600];
        this.y = new int[3600];
        x[0] = 300;
        y[0] = 300;
        snakeW = 10;
        snakeH = 10;
        appleH = 10;
        appleW = 10;

        moveApple();
    }

    private void update(){
        // move
        move();
        // check collision
        // // move apple || end game
        int collisionType = checkCollision();
        if(collisionType == 1){
            snakeL += 5;
            moveApple();
        } else if(collisionType == 2){
            System.exit(0);
        }
    }

    public void changeDirection(char dir){
        if(dir == 'w' && direction != 's'){
            direction = dir;
        }
        if(dir == 's' && direction != 'w'){
            direction = dir;
        }
        if(dir == 'a' && direction != 'd'){
            direction = dir;
        }
        if(dir == 'd' && direction != 'a'){
            direction = dir;
        }
    }

    private void move(){
        for(int i = snakeL-1; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        if(direction == 'w'){
            y[0] -= 10;
        } else if(direction == 's'){
            y[0] += 10;
        } else if(direction == 'a'){
            x[0] -= 10;
        } else if(direction == 'd'){
            x[0] += 10;
        }
    }

    private void moveApple(){
        Random rand = new Random();
        appleX = (rand.nextInt(FrameVariables.WIDTH)/10)*10;
        appleY = (rand.nextInt(FrameVariables.HEIGHT)/10)*10;
    }

    private int checkCollision(){
        for(int i = snakeL-1; i > 0; i--){
            System.out.println("x " + x[0]);
            System.out.println("y " + y[0]);
            if(x[i] == x[0] && y[i] == y[0])
                return 2;
            if(x[i] > FrameVariables.WIDTH || x[i] < 0 || y[i] < 0 || y[i] >= FrameVariables.HEIGHT)
                return 2;
            if(x[i] == appleX && y[i] == appleY)
                return 1;
        }

        return 0;
    }

    private void render(){
        BufferStrategy bs = Snake.getInstance().getBufferStrategy();
        if(bs == null){
            Snake.getInstance().createBufferStrategy(3);
            return;
        }

        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0,0,FrameVariables.WIDTH, FrameVariables.HEIGHT);
        g.setColor(Color.green);
        for(int i = snakeL; i > 0; i--){
            if(i-1 == 0){
                g.setColor(Color.red);
            }
            g.fillRect(x[i-1],y[i-1],snakeW, snakeH);
        }
        g.setColor(Color.red);
        g.fillRect(appleX, appleY, appleW, appleH);

        g.dispose();

        bs.getDrawGraphics().drawImage(image, 8,32,FrameVariables.WIDTH,FrameVariables.HEIGHT,null);
        bs.getDrawGraphics().dispose();

        bs.show();
    }
}
