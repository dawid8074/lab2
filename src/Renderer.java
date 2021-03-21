package tetris;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Renderer extends JPanel
{

    private static final long serialVersionUID = 2L;

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Tetris.tetris.repaint(g);
    }

}
