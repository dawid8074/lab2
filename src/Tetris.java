package tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Tetris implements ActionListener, MouseListener, KeyListener
{

    public static Tetris tetris;


    public boolean[][] Tablica_pol=new boolean[10][20];
    public boolean[][] bufor=new boolean[10][20];

    public boolean[][] dlugi=new boolean[10][20];
    public boolean[][] krotki=new boolean[10][20];
    public boolean[][] zetka=new boolean[10][20];
    public boolean[][] zetka2=new boolean[10][20];
    public boolean[][] dzyndzel=new boolean[10][20];
    public boolean[][] kwadrat=new boolean[10][20];
    public boolean[][] elka=new boolean[10][20];
    public boolean[][] elka2=new boolean[10][20];
    public ArrayList<boolean[][]> klocki;

    public boolean next_move=false;

    public boolean move_left=true;
    public boolean move_right=true;
    public boolean move_down=true;

    public boolean czy_kwadrat;
    boolean czy_mam_pelna_linie=true;
    boolean cz_moge_obrot=true;

    public final int WIDTH = 800, HEIGHT = 1000;

    public Renderer renderer;


    public int score=0;
    public int indeks; //losowanego klocka

    public int i, j, k, l;

    public boolean gameOver, started;

    public Random rand;

    public Tetris()
    {

        JFrame jframe = new JFrame();
        Timer timer = new Timer(800, this);

        renderer = new Renderer();
        rand = new Random();
        klocki= new ArrayList<>();

        jframe.add(renderer);
        jframe.setTitle("tetris");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(WIDTH, HEIGHT);
        jframe.addMouseListener(this);
        jframe.addKeyListener(this);
        jframe.setResizable(false);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);


        for (i=0; i<10; i++)
        {
            for (j=0; j<20; j++)
            {
                Tablica_pol[i][j]=false;
            }
        }

        //klocki:

        //****
        for (i=0; i<10; i++)
        {
            for (j=0; j<20; j++)
            {
                dlugi[i][j]=false;
            }
        }
        for (i=3; i<8; i++)
        {
            dlugi[i][0]=true;
        }

        //  ***
        for (i=0; i<10; i++)
        {
            for (j=0; j<20; j++)
            {
                krotki[i][j]=false;
            }
        }
        for (i=4; i<7; i++)
        {
            krotki[i][0]=true;
        }

        //    *
        //   ***
        for (i=0; i<10; i++)
        {
            for (j=0; j<20; j++)
            {
                dzyndzel[i][j]=false;
            }
        }
        for (i=3; i<6; i++)
        {
            dzyndzel[i][0]=true;
        }
        dzyndzel[4][1]=true;

        //    **
        //   **
        for (i=0; i<10; i++)
        {
            for (j=0; j<20; j++)
            {
                zetka[i][j]=false;
            }
        }
        zetka[4][0]=true;
        zetka[5][0]=true;
        zetka[3][1]=true;
        zetka[4][1]=true;

        //    **
        //     **
        for (i=0; i<10; i++)
        {
            for (j=0; j<20; j++)
            {
                zetka2[i][j]=false;
            }
        }
        zetka2[4][0]=true;
        zetka2[5][0]=true;
        zetka2[5][1]=true;
        zetka2[6][1]=true;
        //      **
        //      **
        for (i=0; i<10; i++)
        {
            for (j=0; j<20; j++)
            {
                kwadrat[i][j]=false;
            }
        }
        kwadrat[4][0]=true;
        kwadrat[5][0]=true;
        kwadrat[4][1]=true;
        kwadrat[5][1]=true;

        //    ***
        //      *
        for (i=0; i<10; i++)
        {
            for (j=0; j<20; j++)
            {
                elka[i][j]=false;
            }
        }
        for (i=3; i<6; i++)
        {
            elka[i][0]=true;
        }
        elka[5][1]=true;

        //    ***
        //    *
        for (i=0; i<10; i++)
        {
            for (j=0; j<20; j++)
            {
                elka2[i][j]=false;
            }
        }
        for (i=3; i<6; i++)
        {
            elka2[i][0]=true;
        }
        elka2[3][1]=true;

        klocki.add(dlugi);
        klocki.add(krotki);
        klocki.add(dzyndzel);
        klocki.add(zetka);
        klocki.add(kwadrat);
        klocki.add(elka);
        klocki.add(zetka2);
        klocki.add(elka2);
        timer.start();
    }


    public void obrot()
    {

        if (gameOver)
        {
            for (i=0; i<10; i++)
                {
                    for (j=0; j<20; j++)
                    {
                        Tablica_pol[i][j]=false;
                    }
                }
            score=0;
            gameOver = false;
            next_move=false;
        }

        if (!started)
        {
            started = true;
        }
        else if (!gameOver)
        {
            //instrukcje obrotu
            boolean[][] temp_obrotu=new boolean[10][20];
            int max_wysokosc=0;
            int kolumna_max_wys=0;
            int max_szerokosc=0;
            int wiersz_max_szer=0;
            int temp=0;
            int srodek_kolumna=0;
            int srodek_wiersz=0;
            for (i=0; i<10; i++) {
                for (j = 0; j < 20; j++) {
                    if (bufor[i][j])
                        temp++;
                }
                if (temp >= max_wysokosc)
                {
                    max_wysokosc = temp;
                    kolumna_max_wys=i;
                }
                temp = 0;
            }
            for (j = 0; j < 20; j++)
            {
                for (i=0; i<10; i++)
                {
                    if(bufor[i][j])
                    {
                        temp++;
                    }
                }
                if (temp >= max_szerokosc)
                {
                    max_szerokosc = temp;
                    wiersz_max_szer=j;
                }
                temp = 0;
            }
            //przekrecenie
            if(max_szerokosc==max_wysokosc)
            {
                if (czy_kwadrat)
                {
                    //nic nie rób
                }
                else
                {
                    srodek_wiersz=wiersz_max_szer;
                    srodek_kolumna=kolumna_max_wys;
                }
            }
            else
            {
                if (max_szerokosc>max_wysokosc)
                {
                    srodek_wiersz=wiersz_max_szer;
                    for (i=0; i<10; i++)
                    {
                        if (bufor[i][wiersz_max_szer]) {
                            srodek_kolumna = i + max_szerokosc / 2;
                            break;
                        }
                    }
                }
                else
                {
                    srodek_kolumna=kolumna_max_wys;
                    for (i=0; i<20; i++)
                    {
                        if (bufor[kolumna_max_wys][i]) {
                            srodek_wiersz = i + max_wysokosc / 2;
                            break;
                        }
                    }
                }
            }
            try {
                //obracam klocek na trzeciej plaszczyźnie
                for (i = 0; i < 10; i++) {
                    for (j = 0; j < 20; j++) {
                        if (bufor[i][j]) {

                            if (i <= srodek_kolumna) {
                                if (j <= srodek_wiersz) {
                                    temp_obrotu[srodek_kolumna + (Math.abs(j - srodek_wiersz))][srodek_wiersz - (Math.abs(i - srodek_kolumna))] = true;

                                } else {
                                    temp_obrotu[srodek_kolumna - (Math.abs(j - srodek_wiersz))][srodek_wiersz - (Math.abs(i - srodek_kolumna))] = true;
                                }

                            } else {
                                if (j <= srodek_wiersz) {
                                    temp_obrotu[srodek_kolumna + (Math.abs(j - srodek_wiersz))][srodek_wiersz + (Math.abs(i - srodek_kolumna))] = true;

                                } else {
                                    temp_obrotu[srodek_kolumna - (Math.abs(j - srodek_wiersz))][srodek_wiersz + (Math.abs(i - srodek_kolumna))] = true;
                                }
                            }
                        }
                    }
                }
                //sprawdzenie pokrywania sie klocków trzeciej plaszczyzny do pierwszej
                for (i = 0; i < 10; i++) {
                    for (j = 0; j < 20; j++) {
                        if (temp_obrotu[i][j]) {
                            if (Tablica_pol[i][j]) {
                                cz_moge_obrot = false;
                            }
                        }
                    }
                }
                //jeżeli moge to kopiuje klocek z trzeciej płaszczyzny na druga
                if (cz_moge_obrot) {
                    for (i = 0; i < 10; i++) {
                        for (j = 0; j < 20; j++) {
                            if (temp_obrotu[i][j]) {
                                bufor[i][j] = true;
                            } else {
                                bufor[i][j] = false;
                            }
                        }
                    }
                }
                cz_moge_obrot=true;
            }
            catch (Exception e)
            {
                //nie rob nic
            }



        }
        renderer.repaint();
    }

    public void w_lewo()
    {
        for (j=0; j<=19; j++) {

            if (bufor[0][j])
            {
                move_left=false;
                break;
            }
            for (i = 1; i <=9; i++) {

                if (bufor[i][j] && !bufor[i-1][j]) {
                    if (Tablica_pol[i-1][j]) {
                        move_left = false;
                        break;
                    }
                }
            }
        }
        for (i=0; i<10; i++)
            if (bufor[i][19])
            {
                move_left=false;
            }
        if (move_left)
        {
            for (j=0; j<=19; j++) {

                for (i = 1; i <=9; i++) {
                    if (bufor[i][j])
                    {
                        bufor[i-1][j]=true;
                        bufor[i][j]=false;
                    }
                }
            }
        }
        else
            move_left=true;
        renderer.repaint();

    }

    public void w_prawo()
    {
        for (j=0; j<=19; j++) {

            if (bufor[9][j])
            {
                move_right=false;
                break;
            }
            for (i = 0; i <=8; i++) {

                if (bufor[i][j] && !bufor[i+1][j]) {
                    if (Tablica_pol[i+1][j]) {
                        move_right = false;
                        break;
                    }
                }
            }
        }
        for (i=0; i<10; i++)
            if (bufor[i][19])
            {
                move_right=false;
            }
        if (move_right)
        {
            for (j=0; j<19; j++) {

                for (i = 9; i >= 0; i--) {
                    if (bufor[i][j])
                    {
                        bufor[i+1][j]=true;
                        bufor[i][j]=false;
                    }
                }
            }
        }
        else
            move_right=true;
        renderer.repaint();

    }

    public void w_dol()
    {
        for (i=0; i<10; i++)
        {

            if (bufor[i][19])
            {
                move_down=false;
                break;
            }
            for (j = 0; j <19; j++) {

                if (bufor[i][j] && !bufor[i][j+1]) {
                    if (Tablica_pol[i][j+1]) {
                        move_down = false;
                        break;
                    }
                }
            }
        }
        if (move_down)
        {
            for (i=0; i<10; i++) {

                for (j = 19; j>=0; j--) {
                    if (bufor[i][j])
                    {
                        bufor[i][j+1]=true;
                        bufor[i][j]=false;
                    }
                }
            }
        }
        else
            move_down=true;
        renderer.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (started)
        {
            //wylosuj klocka
            if (!next_move)
            {
                next_move=true;
                indeks=rand.nextInt(8);
                if (indeks==4)
                {
                    czy_kwadrat=true;
                }
                else
                {
                    czy_kwadrat=false;
                }
                for (i=0; i<10; i++)
                {
                    for (j=0; j<20; j++)
                    {
                        if (klocki.get(indeks)[i][j])
                        {
                            bufor[i][j]=true;
                        }
                    }
                }
                for (i=0; i<10; i++) {
                    for (j = 0; j < 20; j++) {
                        if (Tablica_pol[i][j] && bufor[i][j])
                        {
                            gameOver=true;
                            started=false;
                            for (i=0; i<10; i++) {
                                for (j = 0; j < 20; j++) {
                                    bufor[i][j]=false;
                                }
                            }
                        }
                    }
                }
            }
            for (i=0; i<=9; i++)
            {

                for (j=0; j<19; j++)
                {

                    if (bufor[i][j] && !bufor[i][j+1])
                    {
                        if (Tablica_pol[i][j+1])
                        {
                            next_move=false;
                        }
                    }
                }
            }
            for (i=0; i<10; i++)
                if (bufor[i][19])
                {
                    next_move=false;
                }

            if (!next_move)
            {
                for (i=0;i<10;i++)
                {
                    for (j=0;j<20;j++)
                    {
                        if (bufor[i][j]) {
                            Tablica_pol[i][j]=true;
                            bufor[i][j] = false;
                        }
                    }
                }
            }
            else
            {
                for (i=9; i>=0; i--)
                {
                    for (j = 18; j>=0; j--)
                    {
                        if (bufor[i][j])
                        {

                            bufor[i][j+1]=true;
                            bufor[i][j]=false;
                        }
                    }
                }
            }
            //sprawdzanie pełych linii do skasowania
            for (j=19; j>=0; j--)
            {
                for (i=0; i<10; i++)
                {
                    if (!Tablica_pol[i][j])
                    {
                        czy_mam_pelna_linie=false;
                    }
                }
                if (czy_mam_pelna_linie)
                {
                    for (int l= 0; l < 10; l++)
                    {
                        Tablica_pol[l][j]=false;
                        score++;
                    }
                    for (k=j; k>0; k--)
                    {
                        for (l= 0; l < 10; l++)
                        {
                            Tablica_pol[l][k]=Tablica_pol[l][k-1];
                        }
                    }
                    j++;
                }
                czy_mam_pelna_linie=true;
            }

        }

        renderer.repaint();

    }

    public void repaint(Graphics g)
    {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        for (int i=0; i<10; i++)
        {
            for (int j=0; j<20; j++)
            {
                if (!Tablica_pol[i][j])
                {
                    g.setColor(Color.darkGray);
                    g.fillRect((i*40)+50,(j*40)+150,39,39);
                }
                else
                {
                    g.setColor(Color.white);
                    g.fillRect((i*40)+50,(j*40)+150,39,39);
                }
            }
        }

        for (int i=0; i<10; i++)
        {
            for (int j=0; j<20; j++)
            {
                if (bufor[i][j])
                {
                    paintbufor(g, i, j);
                }
            }
        }


        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 100));
        g.setColor(new Color(255,215,0));
        g.drawString("Tetris", 230, 120);

        if (!started)
        {
            g.setFont(new Font("Arial", 1, 40));
            g.setColor(new Color(255,140,0));
            g.drawString("Naciśnij spacje", WIDTH/2+80, (HEIGHT / 2 - 50)-200);
            g.drawString("aby rozpoczac", WIDTH/2+80, (HEIGHT / 2 - 50)-120);
        }
        if (gameOver)
        {
            g.setColor(new Color(105,105, 105));
            g.fillRect(500,580,190,150);
            g.setColor(new Color(255,0,0));
            g.setFont(new Font("Arial", 1, 40));
            g.drawString("wynik", 540, 630);
            g.drawString(String.valueOf(score), 580, 700);
            g.setFont(new Font("Arial", 1, 120));
            g.setColor(Color.blue);
            g.drawString("Game Over!", 60, HEIGHT / 2 - 50);

        }
        if (!gameOver && started)
        {
            g.setColor(new Color(105,105, 105));
            g.fillRect(500,580,190,150);
            g.setColor(Color.blue);
            g.setFont(new Font("Arial", 1, 40));
            g.drawString("wynik", 540, 630);
            g.drawString(String.valueOf(score), 580, 700);
        }

    }
    public void paintbufor(Graphics g,int x, int y)
    {
        g.setColor(Color.blue);
        g.fillRect((x*40)+50,(y*40)+150,39,39);
    }

    public static void main(String[] args)
    {
        tetris = new Tetris();
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
                obrot();
        }
        if (e.getKeyCode()==KeyEvent.VK_LEFT)
        {
                w_lewo();
        }
        if (e.getKeyCode()==KeyEvent.VK_RIGHT)
        {
                w_prawo();
        }

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode()==KeyEvent.VK_DOWN)
        {
            w_dol();
        }
    }

}
