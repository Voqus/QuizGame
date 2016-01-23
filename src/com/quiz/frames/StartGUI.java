package com.quiz.frames;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * The starting Graphic User Interface(GUI) for the quiz.
 *
 * @author Voqus
 */
public class StartGUI extends JFrame
{

    /** JComponents for the app -- START */
    private JButton _startButton, _cancelButton;
    private JLabel _imageLabel;
    /** JComponents for the app -- END */

    public StartGUI()
    {
        // Setting title for the application.
        super("Quiz");
        initialize();
    } // Constructor

    /**
     * Initializes the graphic user interface for the startup of quiz
     * application.
     */
    private void initialize()
    {
        setLayout(new FlowLayout());

        // Add the logo to the panel
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());

        _imageLabel = new JLabel("", JLabel.CENTER);
        _imageLabel.setIcon(new ImageIcon(StartGUI.class.getResource("/com/quiz/res/quizLogo.png")));

        // add them both to a panel
        imagePanel.add(_imageLabel, BorderLayout.CENTER);

        // create new panel for the components
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        _startButton = new JButton("Play");
        _startButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SwingUtilities.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        new ChooseGUI();
                        dispose();
                    } // run
                    
                }); // invokeLater
            } // actionPerformed
        }); // addActionListener
        
        // Lamda expression Java8+ version
        //_startButton.addActionListener((e)->{new ChooseGUI(); dispose();});

        _cancelButton = new JButton("Cancel");
        _cancelButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            } // actionPerformed
        }); // addActionListener
        
        // Lambda expression Java8+ version
        //_cancelButton.addActionListener((e)->{System.exit(0);});

        panel.add(_startButton);
        panel.add(_cancelButton);
        panel.setBorder(BorderFactory.createTitledBorder(""));

        // create a main panel to include the above panels in one.
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(imagePanel, BorderLayout.CENTER);
        mainPanel.add(panel, BorderLayout.SOUTH);

        add(mainPanel);
        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    } // initialize
}
