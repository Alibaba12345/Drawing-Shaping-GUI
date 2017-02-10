
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * 
 */
public class FigureGUI extends JFrame implements ActionListener {

    private final String formattedDate;
    private ArrayList<Figure> figuresList = new ArrayList<Figure>();

    private static enum Action {

        RECTANGLE, CIRCLE, DATE
    };
    private JButton redButton = new JButton("Red");
    private JButton greenButton = new JButton("Green");
    private JButton blueButton = new JButton("Blue");
    private JButton rectangleButton = new JButton("Rectangle");
    private JButton circleButton = new JButton("Circle");
    private JButton exitButton = new JButton("Exit");
    private JTextArea listArea = new JTextArea(10, 10);
    private GregorianCalendar currentDate;
    private FiguresPanel figuresPanel = new FiguresPanel();
    private Action action = Action.RECTANGLE;
    private Color currentColor;
    private Point startPoint = null;

    /**
     *
     */
    public class FiguresPanel extends JPanel implements MouseListener {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (formattedDate != null) {
                g.drawString(formattedDate, 20, this.getHeight() - 20);
            }
            // draw all figures
            // put the date
            for (Figure figure : figuresList) {
                figure.draw(g);
            }
        }

        /**
         * Waits for the mouse click and creates the appropriate figures.
         * 
         */
        @Override
        public void mouseClicked(MouseEvent event) {
            switch (action) {
                case RECTANGLE:
                    // process the mouse click
                    if (startPoint == null) {
                        startPoint = event.getPoint();
                    } else {
                        double width = Math.abs(startPoint.x - event.getPoint().getX());
                        double height = Math.abs(startPoint.y - event.getPoint().getY());
                        int x = (int) Math.min(startPoint.x, event.getPoint().getX());
                        int y = (int) Math.min(startPoint.y, event.getPoint().getY());
                        figuresList.add(new Rectangle((int) width, (int) height,
                                currentColor, x, y));
                        repaint();
                        startPoint = null;
                        fillText();
                    }

                    break;
                case CIRCLE:
                    // process the mouse click
                    if (startPoint == null) {
                        startPoint = event.getPoint();
                    } else {
                        double width = Math.abs(startPoint.x - event.getPoint().getX());
                        double height = Math.abs(startPoint.y - event.getPoint().getY());
                        int radius = (int) Math.sqrt(width * width + height * height);
                        figuresList.add(new Circle(radius, currentColor,
                                startPoint.x, startPoint.y));
                        repaint();
                        startPoint = null;
                        fillText();
                    }
                    break;
            }

        }

        /**
         * Not used
         */
        @Override
        public void mouseEntered(MouseEvent arg0) {
        }

        /**
         * Not used
         */
        @Override
        public void mouseExited(MouseEvent arg0) {
        }

        /**
         * Not used
         */
        @Override
        public void mousePressed(MouseEvent arg0) {
        }

        /**
         * Not used
         */
        @Override
        public void mouseReleased(MouseEvent arg0) {
        }

    }

    /**
     * Sets up the entire interface.
     */
    public FigureGUI() {
        super("Figures GUI");
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3));
        JPanel listPanel = new JPanel(new GridLayout(1, 1));
        JPanel mainPanel = new JPanel(new GridLayout(1, 4));
        buttonPanel.add(redButton);

        buttonPanel.add(greenButton);

        buttonPanel.add(blueButton);
        buttonPanel.add(rectangleButton);
        buttonPanel.add(circleButton);
        buttonPanel.add(exitButton);
        listPanel.add(new JScrollPane(listArea));
        mainPanel.add(figuresPanel);
        redButton.addActionListener(this);
        greenButton.addActionListener(this);
        blueButton.addActionListener(this);
        rectangleButton.addActionListener(this);
        circleButton.addActionListener(this);
        exitButton.addActionListener(this);

        figuresPanel.addMouseListener(figuresPanel);
        /*
         * Use a File object to represent the figures file. Check
         * if it exists using the exists method.
         * 
         */
        File figures = new File("Figures");

        if (figures.exists()) {
            try {
                FileInputStream fileStream = new FileInputStream(figures);
                ObjectInputStream objectStream = new ObjectInputStream(fileStream);
                figuresList = (ArrayList<Figure>) objectStream.readObject();
                objectStream.close();
                fileStream.close();
                fillText();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (ClassNotFoundException c) {
                System.out.println("Class not found");
                c.printStackTrace();
            }
        }
        this.getContentPane().setLayout(new GridLayout(1, 3));
        this.getContentPane().add(figuresPanel);
        this.getContentPane().add(buttonPanel);
        this.getContentPane().add(listPanel);
        this.setSize(900, 300);
        setVisible(true);
        currentDate = new GregorianCalendar();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = dateFormat.format(currentDate.getTime());
    }

    /**
     * Listener for all buttons.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == redButton) {
            this.currentColor = Color.red;
        } else if (event.getSource() == greenButton) {
            this.currentColor = Color.green;
        } else if (event.getSource() == blueButton) {
            this.currentColor = Color.blue;
        } else if (event.getSource() == exitButton) {
            try {
                FileOutputStream fileStream = new FileOutputStream("figures");
                ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
                objectStream.writeObject(figuresList);
                objectStream.close();
                fileStream.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            System.exit(0);
        } else if (event.getSource() == rectangleButton) {
            this.action = Action.RECTANGLE;
        } else if (event.getSource() == circleButton) {
            this.action = Action.CIRCLE;
        }
    }

    private void fillText() {
        listArea.setText("");
        for (Figure figure : figuresList) {
            listArea.append(figure + "\r\n");
        }
    }

    /**
     * The method creates an FigureGUI object
     *
     * @param args not used
     */
    public static void main(String[] args) {
        new FigureGUI();
    }

}
