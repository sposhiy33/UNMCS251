import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Class of type JPanel that specifies the components of the graphics to be
 * renderd on the panel 
 * @author Shrey Poshiya
 */
public class DrawPanel extends JPanel implements ComponentListener {
    
    // specifies the strength of the color gradient between sqaures in the
    // panel
    private int colorOffset = 40;
   
    /**
     * init size of this panel
     */
    public Dimension getPreferredSize() {
        return new Dimension(200,200);
    }

    /**
     * Paints each component on this panel. Paints rectangles of descending
     * size and color. The size of the inital rectangle is dependent on the 
     * current size of the panel
     * @param g - graphic class that paints components on panel
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paitComponent(g);
        
        // get height and width of current panel
        int height = this.getHeight();
        int width = this.getWidth();

        int hScale = (int) height/5;
        int wScale = (int) width/5;

        g.setColor(new Color(200, 100, 100));
        g.fillRect(0,0, width, height);
        g.setColor(new Color(200-colorOffset, 100, 100));
        g.fillRect(0,0, width-wScale, height-hScale);
        g.setColor(new Color(200-(2*colorOffset), 100, 100));
        g.fillRect(0,0, width-(2*wScale), height-(2*hScale));
        g.setColor(new Color(200-(3*colorOffset), 100, 100));
        g.fillRect(0,0, width-(3*wScale), height-(3*hScale));
        g.setColor(new Color(200-(4*colorOffset), 100, 100));
        g.fillRect(0,0, width-(4*wScale), height-(4*hScale));
    }

    /**
     * This method is used to repaint the graphic such that the magnitude
     * of the descending color gradient is changed
     * @param offset - int representing the specified color gradient between
     *                 the drawn rectangles
     */ 
    public void repaintColorOffset(int offset) {
        this.colorOffset=offset;
        repaint();
    }    

    /**
     * When this panel is resized, repaint this panel such that the painting
     * is scaled to the current size of the panel.
     * @param ev - Component event that signal when a change to this 
     *             component, this panel, is made
     */
    public void componentResized(ComponentEvent ev) {
        repaint(); 
    }

    // other classes need to implement ComponentListener (not used)
    public void componentMoved(ComponentEvent ev) { }
    public void componentShown(ComponentEvent ev) { }
    public void componentHidden(ComponentEvent ev) { }   
   
    /**
     * main method used to test this class. Simply displays an instance of this
     * panel on a frame.
     * @param args - string array of command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Drawing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        DrawPanel drawPanel = new DrawPanel();
        frame.setContentPane(drawPanel);
        frame.pack();
        frame.setVisible(true);
    }

} 
