import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class BujjiCar extends JPanel implements ActionListener, KeyListener{
    int boardWidth=360;
    int boardHeight=640;
    //Creating The Images Variables..
    Image backgroundImg;
    Image carImg;
    Image topPipeImg;
    Image bottomPipeImg;
    //Bujji Car
    int carX=boardWidth/8;//car
    int carY=boardHeight/2;//car
    int carWidth=140;//car
    int carHeight=128;//car
    class Car{
        int x=carX;
        int y=carY;
        int width=carWidth;
        int height=carHeight;
        Image img;
        Car(Image img){
            this.img=img;
        }
    }
    //Pipes
    int pipeX= boardWidth;
    int pipeY=0;
    int pipeWidth=64;
    int pipeHeight=512;
    class Pipe{
        int x=pipeX;
        int y=pipeY;
        int width=pipeWidth;
        int height=pipeHeight;
        Image img;
        boolean passed=false;
        Pipe(Image img){
            this.img=img;
        }
    }

    //game logic
    Car car;
    int velocityX=-4;
    int velocityY=0;
    int gravity=1;

    ArrayList<Pipe>pipes;
    Random random=new Random();

    Timer gameLoop;
    Timer placePipesTimer;
    boolean gameOver=false;
    double score=0;


    BujjiCar(){
        setPreferredSize(new Dimension(boardWidth,boardHeight));
        //setBackground(Color.blue);
        setFocusable(true);
        addKeyListener(this);

        //loading the images..
        backgroundImg=new ImageIcon(getClass().getResource("./spacebg.png")).getImage();
        carImg=new ImageIcon(getClass().getResource("./Bujji.png")).getImage();
        topPipeImg=new ImageIcon(getClass().getResource("./ToopPipe 3Copy.png")).getImage();
        bottomPipeImg=new ImageIcon(getClass().getResource("./ToopPipe.png")).getImage();
        //car
        car=new Car(carImg);
        pipes =new ArrayList<Pipe>();
        //place papes timer
        placePipesTimer=new Timer(2000,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                placePipes();
            }
        });
        placePipesTimer.start();
        //gametimer
        gameLoop= new Timer(1000/60,this);
        gameLoop.start();
        
    }
    public void placePipes(){
        int randomPipeY=(int)(pipeY-pipeHeight/4-Math.random()*(pipeHeight/2));
        int openingSpace=boardHeight/4;

        Pipe topPipe= new Pipe(topPipeImg);
        topPipe.y=randomPipeY;
        pipes.add(topPipe);
        Pipe bottomPipe=new Pipe(bottomPipeImg);
        bottomPipe.y=topPipe.y+pipeHeight+openingSpace;
        pipes.add(bottomPipe);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        //Drawing Background
        g.drawImage(backgroundImg,0, 0, boardWidth,boardHeight,null);

        //draw car
        g.drawImage(car.img, car.x, car.y, car.width, car.height, null);
        g.setColor(Color.red);

        //draw pipes
        for(int i=0;i<pipes.size();i++){
            Pipe pipe=pipes.get(i);
            g.drawImage(pipe.img,pipe.x,pipe.y,pipe.width,pipe.height,null);
        }
        //score
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN,32));
        if(gameOver){
            g.drawString("Game Over" +String.valueOf((int)score),10,35);
        }
        else{
            g.drawString(String.valueOf((int)score),10,35);
        }
    }
    public void move(){
        //car
        velocityY+=gravity;
        car.y+=velocityY;
        car.y=Math.max(car.y,0);

        //pipes
        for(int i=0;i<pipes.size();i++){
            Pipe pipe=pipes.get(i);
            pipe.x+=velocityX;
            if(!pipe.passed&&car.x>pipe.x+pipe.width){
                pipe.passed=true;
                score+=0.5;// we have two pipes, so 0.5 per each pipe
            }
            if(collision(car, pipe)){
                gameOver=true;
            }
        }
        if(car.y>boardHeight){
            gameOver=true;
        }
    }
        public boolean collision(Car a, Pipe b) {
        int offsetX = 20;
        int offsetY = 45;
        int hitboxWidth = a.width - 40;
        int hitboxHeight = a.height - 90;
    
        return a.x + offsetX < b.x + b.width &&
               a.x + offsetX + hitboxWidth > b.x &&
               a.y + offsetY < b.y + b.height &&
               a.y + offsetY + hitboxHeight > b.y;
    }
    


  /*  public boolean collision(Bird a,Pipe b){
        return a.x<b.x+b.width &&
               a.x+a.width>b.x &&
               a.y<b.y+b.height &&
               a.y+a.height>b.y;

    }*/
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver){
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            velocityY=-9;
        }
        if(gameOver){
            //for restarting the game
            car.y=carY;
            velocityY=0;
            pipes.clear();
            score=0;
            gameOver=false;
            gameLoop.start();
            placePipesTimer.start();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {
    }

}
