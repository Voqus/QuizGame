package com.quiz.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Graphic User Interface(GUI) to choose the quiz game.
 *
 * @author Voqus
 */
public final class ChooseGUI extends JFrame implements ActionListener
{
    private JButton[] _quizChoice = new JButton[3];
    private JLabel _instructionLabel;

    public ChooseGUI()
    {
        super("Quiz Game");
        initialize();
    } // Constructor

    public void initialize()
    {
        setLayout(new FlowLayout());
        
        // Initialize the buttons and assign actionlistener to it
        _quizChoice[0] = new JButton("Vocabulary");
        _quizChoice[0].addActionListener(this);
        _quizChoice[1] = new JButton("Grammar");
        _quizChoice[1].addActionListener(this);
        _quizChoice[2] = new JButton("Spelling");
        _quizChoice[2].addActionListener(this);

        _instructionLabel = new JLabel("<html><center>Hello there <font color=\"red\"><b>player</b></font>."
                + "<br/>Choose the <b>category</b> you want to play.</center></html>",
                SwingConstants.CENTER);

        // mainPanel contains a label and the buttons
        JPanel mainPanel = new JPanel();
        // set the layout of mainPanel to 4 rows and 1 column
        mainPanel.setLayout(new GridLayout(4, 1));

        mainPanel.add(_instructionLabel);
        mainPanel.add(_quizChoice[0]);
        mainPanel.add(_quizChoice[1]);
        mainPanel.add(_quizChoice[2]);
        
        // add mainPanel to the top-level container
        add(mainPanel);

        // automatically resize and set that size to minimum
        pack();
        setMinimumSize(new Dimension(getWidth(), getHeight()));
        setSize(getWidth() + 50, getHeight() + 150);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    } // initialize

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == _quizChoice[0])
        {
            SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    new MainGUI(((JButton) e.getSource()).getText().toLowerCase());
                    dispose();
                } // run
            }); // invokeLater
            
            // Lamda Expression Java8+
            // SwingUtilities.invokeLater(()-> {new MainGUI(((JButton) e.getSource()).getText().toLowerCase());dispose();});
        } // if
        if (e.getSource() == _quizChoice[1])
        {
            SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    new MainGUI(((JButton) e.getSource()).getText().toLowerCase());
                    dispose();
                } // run
            }); // invokeLater
            
            // Lamda Expression Java8+
            // SwingUtilities.invokeLater(()-> {new MainGUI(((JButton) e.getSource()).getText().toLowerCase());dispose();});
        } // if
        if (e.getSource() == _quizChoice[2])
        {
            SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    new MainGUI(((JButton) e.getSource()).getText().toLowerCase());
                    dispose();
                } // run
            }); // invokeLater
            
            // Lamda Expression Java8+
            // SwingUtilities.invokeLater(()-> {new MainGUI(((JButton) e.getSource()).getText().toLowerCase());dispose();});
        } // if
    } // actionPerformed

}
