import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Snake extends JFrame implements FrameVariables{

    private static Snake snake;

    Snake(){
        snake = this;
        Game game = new Game();

        game.setPreferredSize(new Dimension(FrameVariables.WIDTH, FrameVariables.HEIGHT));

        this.buildFrame(game);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                char dir = e.getKeyChar();
                if(dir == 'w' || dir == 's' || dir == 'a' || dir == 'd'){
                    game.changeDirection(dir);
                }
            }
        });

        Thread thread = new Thread(game);
        thread.start();
    }

    private void buildFrame(Game game){
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(TITLE);
        add(game);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static Snake getInstance(){ return snake; }

    public static void main(String[] args){
        new Snake();
    }
}
