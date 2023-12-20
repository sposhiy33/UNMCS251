import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Class that directly builds the GUI. The GUI consists of one MAIN PANEL to
 * which subpanels are added: (1) Graphics panel that contains a graphical 
 * element (2) Interactive Panel in which there is: (a) a label at the top
 * descibing how many time a button is pressed (b) a button that prompts a 
 * pop-up dialouge that descibes how many times it has been pressed (c) a 
 * series of radio button that changes the severity of the color gradient 
 * between a series of squared that are rendered in the graphics panel
 * @author Shrey Poshiya
 */

public class LayoutPractice {

    // memeber variable that keeps track of how many times the button has
    // been pressed
    private int clicks = 0;
    
    /**
     * Elements of a GUI are created in one Frame (see class description
     * for detailed explanation of all componenets).
     * @param args - command line arguments (not used in this project)
     */
    public static void main(String[] args) {
        
        // a new instance of this GUI
        LayoutPractice GUI = new LayoutPractice();
    
        // new frame
        JFrame frame = new JFrame("Practice Layout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // the main panel to which all other panels will be added, uses
        // box layout
        JPanel mainPanel = new JPanel(); 
        mainPanel.setLayout(new BoxLayout(mainPanel, 
                                BoxLayout.X_AXIS));                

        // panel containing graphics
        DrawPanel drawPanel = new DrawPanel();
        
        // BUTTON PANEL //
        // panel containg: dialouge, button button, radio button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        
        // label at the top of the button panel
        JLabel label = new JLabel("Button Clicks = " + GUI.clicks);
        buttonPanel.add(label);
        label.setHorizontalAlignment(JLabel.CENTER); // center text in line
        buttonPanel.add(Box.createVerticalGlue());
        
        // button at the center of the panel
        JButton button = new JButton("Click Me!");
        // when the button is pressed, prompt a pop up dialouge and update the
        // label at the top of the panel
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                GUI.clicks++;
                JOptionPane.showMessageDialog(buttonPanel,
                                              "button has been clicked " +
                                              GUI.clicks + " times", 
                                              "TITLE",
                                              JOptionPane.INFORMATION_MESSAGE);
                label.setText("Button Clicks = " + GUI.clicks);
            }
        });
        buttonPanel.add(button, BorderLayout.CENTER);        
        buttonPanel.add(Box.createVerticalGlue());        

        // all radio buttons
        JRadioButton radio1 = new JRadioButton("High", true);
        JRadioButton radio2 = new JRadioButton("Medium");
        JRadioButton radio3 = new JRadioButton("None");
         
        // add radio buttons to a group so that only one can be selected at
        // one time
        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(radio1);       
        radioGroup.add(radio2);       
        radioGroup.add(radio3); 

        // upon each radio button being selected, update the image rendered on
        // the graphical panel accordingly
        radio1.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent ev) {
                drawPanel.repaintColorOffset(40);
            }
        });
        radio2.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent ev) {
                drawPanel.repaintColorOffset(28);
            }
        });
        radio3.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent ev) {
                drawPanel.repaintColorOffset(0);
            }
        });        

        // arrange radio buttons in a panel
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new GridLayout(3,1));
        radioPanel.add(radio1);
        radioPanel.add(radio2);
        radioPanel.add(radio3);
        // add a border around the radio panel
        radioPanel.setBorder(BorderFactory.createTitledBorder(
                   BorderFactory.createEtchedBorder(), "Color Gradient"));

        buttonPanel.add(radioPanel, BorderLayout.SOUTH);        

        // add all subpanels to the main panel
        mainPanel.add(drawPanel);
        mainPanel.add(buttonPanel);

        // render the panel
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setVisible(true);

    }
}
