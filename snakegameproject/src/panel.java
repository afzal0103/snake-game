import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.util.Arrays;
import java.util.Random;
import java.awt.event.*;
import java.awt.event.ActionListener;

public class panel extends JPanel implements ActionListener{
//    setting the size of the panel
    static final int width = 1200;
    static final int  height = 600;

    static int unit = 50;
    int totunit = (width*height)/unit;
    boolean flag = false;

    //food x and y coordinate
    int fx, fy;
    Random random;

    char dir = 'R';
    Timer timer;
    static int DELAY = 160;
    int score;

    int[] xsnake = new int[288];
    int[] ysnake = new int [288];
    int length = 3;


    panel(){
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.black);
        this.addKeyListener(new key());
//        enables keyboard input tp the applications
        this.setFocusable(true);
        random = new Random();
        gamestart();

    }
    public void gamestart(){
        spawnfood();
        flag = true;
 //     timer to check on the game state in each 160s
        timer = new Timer(160, (ActionListener) this);
        timer.start();
    }

    public void spawnfood(){
        fx = random.nextInt(width/unit)*50;
        fy = random.nextInt(height/unit)*50;
    }

    public void paintComponent(Graphics graphic){
        super.paintComponent(graphic);
        draw(graphic);
    }

    public void draw(Graphics graphic) {
        if (flag) {
//            to spawn the food particle
            graphic.setColor(Color.orange);
            graphic.fillOval(fx, fy, unit, unit);

//          to draw the snake
            for (int i = 0; i < length; i++) {
                if (i == 0) {
                    graphic.setColor(Color.RED);
                    graphic.fillRect(xsnake[0], ysnake[0], unit, unit);
                } else {
                    graphic.setColor(Color.GREEN);
                    graphic.fillRect(xsnake[i], ysnake[i], unit, unit);
                }
            }
//            for drawing the score element
            graphic.setColor(Color.CYAN);
            graphic.setFont(new Font("comic Sans MS", Font.BOLD, 40));
            FontMetrics fme = getFontMetrics(graphic.getFont());
            graphic.drawString("Score: " + score, (width - fme.stringWidth("Score: " + score)) / 2, graphic.getFont().getSize());
        }
//        while the game is not running
        else {
            gameover(graphic);
        }
    }
    public void gameover(Graphics graphic){
//        the score display
        graphic.setColor(Color.CYAN);
        graphic.setFont(new Font("comic Sans MS", Font.BOLD, 48));
        FontMetrics fme = getFontMetrics(graphic.getFont());
        graphic.drawString("Score: " + score,(width-fme.stringWidth("Score: "+score))/2,graphic.getFont().getSize());
//

//       to display the game over text
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("comic Sans MS", Font.BOLD, 80));
        FontMetrics fme1 = getFontMetrics(graphic.getFont());
        graphic.drawString("GAME OVER! " + score, (width-fme1.stringWidth("GAME OVER!"))/2, height/2);

//      to display the replay prompt
        graphic.setColor(Color.GREEN);
        graphic.setFont(new Font("comic Sans MS", Font.BOLD, 40));
        FontMetrics fme2 = getFontMetrics(graphic.getFont());
        graphic.drawString("Press R to replay", (width-fme2.stringWidth("Press R to replay"))/2, height/2 + 150);


        }
    public void move(){
//      for all other body parts
        for(int i = length; i>0; i--){
            xsnake[i] = xsnake[i-1];
            ysnake[i] = ysnake[i-1];
        }
//      for updating head
        switch(dir) {
            case 'U':
                ysnake[0] = ysnake[0] - unit;
                    break;
            case 'D':
                ysnake[0] = ysnake[0] + unit;
                    break;
            case 'L':
                xsnake[0] = xsnake[0] - unit;
                break;
            case 'R':
                xsnake[0] = xsnake[0] + unit;
                break;

        }
    }
    void check() {
//        checking if head has it the body
        for (int i = length; i > 0; i--) {
            if ((xsnake[0] == xsnake[i]) && (ysnake[0] == ysnake[i])) {
                flag = false;
            }
        }

//checking hit with the walls
        if (xsnake[0] < 0) {
            flag = false;
        } else if (xsnake[0] > width) {
            flag = false;
        } else if (ysnake[0] < 0) {
            flag = false;
        }

        if (flag == false) {
            timer.stop();
        }

    }
    public void foodeaten(){
        if((xsnake[0]==fx) && (ysnake[0] ==fy)){
            length++;
            score++;
            spawnfood();
        }
    }

    public void checkhit(){
//        to check hit with the boundary
        if(ysnake[0] < 0){
            flag = false;
        }
        if(ysnake[0] >600){
            flag = false;
        }
        if(xsnake[0] < 0){
            flag = false;
        }
        if(xsnake[0] > 1200){
            flag=  false;
        }
//        to check hits with its own body
        for(int i = length; i>0; i--){
            if((xsnake[0] == xsnake[i]) && (ysnake[0] == ysnake[i])){
                flag = false;
            }
        }
        if(flag == false){
            timer.stop();
        }
    }
    public class key extends KeyAdapter{
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(dir !='R'){
                        dir = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(dir!='L'){
                        dir = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(dir!='D'){
                        dir = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(dir!='U'){
                        dir = 'D';
                    }
                    break;
                case KeyEvent.VK_R:
                    score = 0;
                    length = 3;
                    dir = 'R';
                    Arrays.fill(xsnake, 0);
                    Arrays.fill(ysnake, 0);
                    gamestart();
            }

        }
    }
    public void actionPerformed(ActionEvent evt){
        if(flag){
            move();
            foodeaten();
            checkhit();
        }
        repaint();
    }

}


