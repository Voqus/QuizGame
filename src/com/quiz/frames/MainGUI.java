package com.quiz.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.quiz.xml.XMLLoader;

/**
 * The main Graphic User Interface (GUI) that contains the questions-answers for
 * the quiz.
 *
 * @author Voqus
 */
public final class MainGUI extends JFrame implements ActionListener
{

    private static final long serialVersionUID = 1L;

    /** JComponents for this interface -- START */
    private static JLabel _timerLabel, _questionLabel, _questionNumberLabel, _informationLabel, _scoreLabel;
    private static JComboBox<Object> _answerComboBox;
    private static JButton _submitButton, _backButton;
    /** JComponents for this interface -- END */

    /** Components for the quiz game -- START */
    private static Document _doc;
    private NodeList _nodes;
    private Node _node;
    private static List<String> _items = new ArrayList<>();

    private static String _answerGiven, _answer;

    private static Timer _timer;
    private long _current = 0;
    private static final int WAITING_TIME = 10; // 10 seconds for timer
    private int _correctAnswers = 0, _totalAnswers = 0, _totalPoints = 0, _questionPoints = 0;
    private int _nodeCounter = 0, _questionCounter = 1;
    /** Components for the quiz game -- END */

    /**
     * @param subject
     */
    public MainGUI(final String subject)
    {
        // Set title for GUI
        super("Quiz");

        // try to load the appropriate xml for the quiz
        try
        {
            _doc = XMLLoader.loadFile(new File("src/com/quiz/res/" +subject + ".xml"));
           // JOptionPane.showMessageDialog(null, ""+new File(MainGUI.class.getResource("/com/quiz/res/" + subject + ".xml").getPath()));
        } // try
        catch (IOException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "We're sorry but there is no such quiz available yet.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } // catch
        
        initialize();
    } // Constructor

    /**
     * Initializes the main graphical user interface (GUI) of the quiz.
     */
    private void initialize()
    {
        setLayout(new FlowLayout());

        // questionPanel contains the questions and stats
        JPanel questionPanel = new JPanel();
        // set layout for the panel, 6 rows 1 column
        questionPanel.setLayout(new GridLayout(6, 1));

        _questionNumberLabel = new JLabel("Question " + _questionCounter, JLabel.CENTER);
        Font font = new Font("Arial", Font.BOLD, 16);
        _questionNumberLabel.setFont(font);
        _timerLabel = new JLabel("", JLabel.CENTER);
        _questionLabel = new JLabel();
        _informationLabel = new JLabel("", JLabel.CENTER);
        _scoreLabel = new JLabel("", JLabel.CENTER);

        _answerComboBox = new JComboBox<Object>();

        _nodes = _doc.getElementsByTagName("question");
        _totalAnswers = _nodes.getLength();

        _scoreLabel.setText(_correctAnswers + "/" + _totalAnswers + "   Total Points: " + _totalPoints);

        _node = _nodes.item(_nodeCounter);

        // if _node is null it means that the xml contains no data for the quiz
        if (_node == null)
        {
            JOptionPane.showMessageDialog(null, "We're sorry but there is not enough data for you to play on this language.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } // if

        if (_node.getNodeType() == Node.ELEMENT_NODE)
        {
            Element element = (Element) _node;
            _questionPoints = Integer.parseInt(XMLLoader.getValue("points", element));
            _questionLabel.setText(XMLLoader.getValue("questionName", element));
            _answer = XMLLoader.getValue("correct", element);

            _items = new ArrayList<String>(XMLLoader.getTextOf(XMLLoader._doc, "answer" + (_nodeCounter + 1)));

            _answerComboBox = new JComboBox<Object>(_items.toArray());
            // setting initial answer to JComboBox
            _answerGiven = String.valueOf(_answerComboBox.getItemAt(0));

        } // if

        questionPanel.add(_questionNumberLabel);
        questionPanel.add(_timerLabel);
        questionPanel.add(_questionLabel);
        questionPanel.add(_answerComboBox);
        questionPanel.add(_informationLabel);
        questionPanel.add(_scoreLabel);

        // buttonPanel contains the buttons;
        JPanel buttonPanel = new JPanel();
        _submitButton = new JButton("Submit");
        _backButton = new JButton("Back to menu");
        _backButton.setEnabled(false);
        
        // Assign actionListeners on buttons & combobox
        _submitButton.addActionListener(this);
        _backButton.addActionListener(this);
        _answerComboBox.addActionListener(this);

        buttonPanel.add(_submitButton);
        buttonPanel.add(_backButton);

        // panel contains the above panels in one.
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(questionPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // mainPanel contains the panel and sets it to the center of the container.
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(panel, BorderLayout.CENTER);
        // add the mainPanel to the top-level container
        add(mainPanel);
        
        // automatically resize and set that size to minimum
        pack();
        setMinimumSize(new Dimension(getWidth(), getHeight()));
        setSize(getWidth() + 200, getHeight());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // start the timer of the quiz game
        _timer = new Timer(1000, this);
        _timer.start();
        _current = System.currentTimeMillis();
    } // initialize

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == _timer)
        {

            long time = System.currentTimeMillis();
            long passed = time - _current;
            long remaining = (WAITING_TIME * 1000) - passed;

            // if the remaining time hits zero, handle the rest and go to next question.
            if (remaining <= 0)
            {
                _timerLabel.setText("");
                _timer.stop();

                JOptionPane.showMessageDialog(null, "The correct answer was: " + _answer, "Incorrect", JOptionPane.ERROR_MESSAGE);
                nextQuestion();

                _timer.restart();
                _current = System.currentTimeMillis();
            } // if
            else
            {
                long seconds = remaining / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                _timerLabel.setText("Time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds % 60));

                Font font = new Font("Arial", Font.PLAIN, 14);
                _timerLabel.setFont(font);
                _timerLabel.setForeground(Color.RED);
            } // else
        } // if
        if (e.getSource() == _submitButton)
        {
            if (_answerGiven.equals(_answer))
            {
                _informationLabel.setForeground(Color.GREEN);
                _informationLabel.setText("CORRECT");

                _correctAnswers++;
                _totalPoints += _questionPoints;
                _scoreLabel.setText(_correctAnswers + "/" + _totalAnswers + "     Total Points:" + _totalPoints);

                nextQuestion();
            } // if
            else
            {
                _timer.stop();
                _informationLabel.setForeground(Color.RED);
                _informationLabel.setText("INCORRECT");
                JOptionPane.showMessageDialog(null, "The correct answer was: " + _answer, "Incorrect", JOptionPane.ERROR_MESSAGE);

                _timer.restart();
                _current = System.currentTimeMillis();
                nextQuestion();
            } // else
        } // if
        else if (e.getSource() == _backButton)
        {
            SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    new ChooseGUI();
                    dispose();
                }
            });
            // Lamda expression java8+
            // SwingUtilities.invokeLater(()-> {new ChooseGUI(); dispose();});
        } // else if
        else if (e.getSource() == _answerComboBox)
        {
            _answerGiven = String.valueOf(_answerComboBox.getSelectedItem());
        } // else if
    } // actionPerformed

    /**
     * Selects the next node as question for the game.
     */
    private void nextQuestion()
    {
        _node = _nodes.item(++_nodeCounter);// get next node
        _questionCounter++;

        if (_node == null)
        {
            _timer.stop();
            _timerLabel.setText("");
            JOptionPane.showMessageDialog(null, "You have ended the quiz with score: " + _totalPoints, "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            _submitButton.setEnabled(false);
            _backButton.setEnabled(true);
            return;
        } // if

        // Refresh lists for next question
        _items.clear();
        _answerComboBox.removeAllItems();

        if (_node.getNodeType() == Node.ELEMENT_NODE)
        {
            Element element = (Element) _node;
            _questionNumberLabel.setText("Question " + _questionCounter);
            _questionLabel.setText(XMLLoader.getValue("questionName", element));
            _answer = XMLLoader.getValue("correct", element);
            _questionPoints = Integer.parseInt(XMLLoader.getValue("points", element));
        } // if
        _items = new ArrayList<String>(XMLLoader.getTextOf(_doc, "answer" + (_nodeCounter + 1)));

        // add all the answers to combo box
        // Lamba Expression Java8+
        //_items.forEach((e)-> {_answerComboBox.addItem(e);});
        for(String item : _items)
        {
            _answerComboBox.addItem(item);
        } // for

        // reset the timer back to 10 seconds.
        _timer.restart();
        _current = System.currentTimeMillis();
    } // nextQuestion
}
