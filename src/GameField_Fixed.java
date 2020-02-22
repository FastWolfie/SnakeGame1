import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.function.Consumer;

public class GameField_Fixed<i> extends JPanel implements ActionListener{
    private final int Size = 320;
    private final int Dot_Size = 16;
    private final int All_Dots = 400;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private ArrayList<Pos> Snake = new ArrayList<>();
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    private int dots;

    public GameField_Fixed(){
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }
    private void createApple() {
        appleX = new Random().nextInt(20)*Dot_Size;
        appleY = new Random().nextInt(20)*Dot_Size;
    }
    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            Snake.add(new Pos(48 - i * Dot_Size,48));
        }
        timer = new Timer(250,this);
        timer.start();
        createApple();
    }
    public void loadImages(){
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame) {
            chekApple();
            //checkCollisions();
            move();
        }
        repaint();
    }

    private void checkCollisions() {
        Snake.forEach(pos -> {
            if((pos.getY() >= 0 && pos.getY() <= 20)){
                System.out.println("Failed");
                //inGame = false;
            }
        });
//        for(int x = 0;x< 20  ;x++){
//            for(int y = 0;y< 20  ;y++){
//                Snake.add(new Pos(x,y));
//            }
//        }
//        System.out.println("Done");
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left= true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right= true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                right= false;
                left = false;
                down = true;
            }
            if(key == KeyEvent.VK_UP && !down){
                left= false;
                up = true;
                right = false;
            }

        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(apple,appleX,appleY,this);
            Snake.forEach(pos -> g.drawImage(dot,pos.getX(),pos.getY(),this));
//            row = 0;
//            Snake.forEach(pos ->{draw_pos(g,this,pos,Snake.indexOf(pos));});

        }else{
            String str = "Game Over";
            Font f = new Font("Arial",Font.BOLD,14);
            g.setColor(Color.green);
            g.drawString(str,125,Size/2);

        }
    }
//    int row;
//    public void draw_pos(Graphics g,ImageObserver img,Pos pos,int number){
//        String str = number+"| x:"+pos.getX()+" y:"+ pos.getY();
//        g.setColor(Color.white);
//        g.drawString(str,240,(row*15)+30);
//        row ++;
//    }
    public void move(){
        Pos newpos = new Pos(Snake.get(0).getX(),Snake.get(0).getY());
        if(left){
            newpos.setX(newpos.getX()-Dot_Size);
            if(newpos.getX() > 288 || newpos.getX() < 0){
                System.out.println("Dead");
                inGame = false;
            }
        }
        if(right){
            newpos.setX(newpos.getX()+Dot_Size);
            if(newpos.getX() > 288 || newpos.getX() < 0){
                System.out.println("Dead");
                inGame = false;
            }
        }
        if(up){
            newpos.setY(newpos.getY()-Dot_Size);
            if(newpos.getY() > 288 || newpos.getY() < 0){
                System.out.println("Dead");
                inGame = false;
            }
        }
        if(down){
            newpos.setY(newpos.getY()+Dot_Size);
            if(newpos.getY() > 288 || newpos.getY() < 0){
                System.out.println("Dead");
                inGame = false;
            }
        }


        ArrayList<Pos> buf = new ArrayList<>();
        Snake.forEach(pos -> {buf.add(Snake.indexOf(pos),new Pos(pos.getX(),pos.getY()));});

        for(int i = 0;i<= Snake.toArray().length-2  ;i++){
//            System.out.println("Org: "+Snake.get(0));
//            System.out.println("Org1: "+Snake.get(1));
            Snake.get(i+1).setX(buf.get(i).getX());
            Snake.get(i+1).setY(buf.get(i).getY());
            //Snake.get(0).setX(0);
            //Snake.get(0).setY(0);
//            System.out.println("After: "+Snake.get(0));
//            System.out.println("After1: "+Snake.get(0));
        }
        Snake.get(0).setX(newpos.getX());Snake.get(0).setY(newpos.getY());
    }
    public void chekApple(){
        if(((Pos)Snake.toArray()[0]).getX() == appleX && ((Pos)Snake.toArray()[0]).getY() == appleY){
            dots++;
            Pos lpos = Snake.get(Snake.size()-1);
            Snake.add(new Pos(lpos.getX(),lpos.getY()));
            createApple();
        }
    }
}





