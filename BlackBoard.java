package blackboard;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;
import javax.swing.*;
import javax.swing.JMenuBar;

public class BlackBoard extends JFrame{

    JMenuBar mainMenuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem newMenuItem = new JMenuItem("New");
    JMenuItem exitMenuItem = new JMenuItem("Exit");
    GraphicsPanel drawPanel = new GraphicsPanel();
    JLabel leftColorLabel = new JLabel();
    JLabel rigthColorLabel = new JLabel();
    JPanel colorPanel = new JPanel();
    JLabel[] colorLabel = new JLabel[8];
    Graphics2D g2D;
    double xPrevious, yPrevious;
    Color drawColor, leftColor, rightColor;
    static Vector myLines = new Vector(200, 100);
    public static void main(String[] args) {
    
       new BlackBoard().show();
    }

    public BlackBoard() throws HeadlessException {
        setTitle("BlackBoard");
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitForm(e);
            }
            
});
        getContentPane().setLayout(new GridBagLayout());
        setJMenuBar(mainMenuBar);
        fileMenu.setMnemonic('F');
        mainMenuBar.add(fileMenu);
        fileMenu.add(newMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newMenuItemActionPerformed(e);
            }
        });
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitMenuItemActionPerformed(e);
            }
        });
        drawPanel.setPreferredSize(new Dimension(500, 400));
        drawPanel.setBackground(Color.BLACK);
        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.gridheight = 2;
        gridConstraints.insets = new Insets(10, 10, 10, 10);
        getContentPane().add(drawPanel, gridConstraints);
        drawPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                drawPanelMousePressed(e);
            }
            
        });
        drawPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                drawPanelMouseDragged(e);
            }
            
});
        drawPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                drawPanelMouseReleased(e);
            }
            
});
        leftColorLabel.setPreferredSize(new Dimension(40, 40));
        leftColorLabel.setOpaque(true);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 0;
        gridConstraints.anchor = GridBagConstraints.NORTH;
        gridConstraints.insets = new Insets(10, 5, 10, 10);
        getContentPane().add(leftColorLabel, gridConstraints);
        rigthColorLabel.setPreferredSize(new Dimension(40, 40));
        rigthColorLabel.setOpaque(true);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 0;
        gridConstraints.anchor = GridBagConstraints.NORTH;
        gridConstraints.insets = new Insets(10, 5, 10, 10);
        getContentPane().add(rigthColorLabel, gridConstraints);
        colorPanel.setPreferredSize(new Dimension(80, 160));
        colorPanel.setBorder(BorderFactory.createTitledBorder("Colors"));
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 1;
        gridConstraints.gridwidth = 2;
        gridConstraints.anchor = GridBagConstraints.NORTH;
        gridConstraints.insets = new Insets(10, 10, 10, 10);
        getContentPane().add(colorPanel, gridConstraints);
        colorPanel.setLayout(new GridBagLayout());
        int j = 0;
        for(int i = 0; i < 8; i++){
            colorLabel[i] = new JLabel();
            colorLabel[i].setPreferredSize(new Dimension(30, 30));
            colorLabel[i].setOpaque(true);
            gridConstraints = new GridBagConstraints();
            gridConstraints.gridx = j;
            gridConstraints.gridy = i - j * 4;
            colorPanel.add(colorLabel[i], gridConstraints);
            if(i == 3){
                j++;
            }
            colorLabel[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    colorMousePressed(e);
                }
                
});
        }
        colorLabel[0].setBackground(Color.GRAY);
        colorLabel[1].setBackground(Color.BLUE);
        colorLabel[2].setBackground(Color.GREEN);
        colorLabel[3].setBackground(Color.CYAN);
        colorLabel[4].setBackground(Color.RED);
        colorLabel[5].setBackground(Color.MAGENTA);
        colorLabel[6].setBackground(Color.YELLOW);
        colorLabel[7].setBackground(Color.WHITE);
        leftColor = colorLabel[0].getBackground();
        leftColorLabel.setBackground(leftColor);
        rightColor = colorLabel[7].getBackground();
        rigthColorLabel.setBackground(rightColor);
        pack();
        g2D = (Graphics2D)drawPanel.getGraphics();
    }
    void newMenuItemActionPerformed(ActionEvent e){
        int response;
        response = JOptionPane.showConfirmDialog(null, "Are you sure you want to start ned Drawint?", "New Drawing", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(response == JOptionPane.YES_OPTION){
            myLines.removeAllElements();
            drawPanel.repaint();
        }
    }
    void exitMenuItemActionPerformed(ActionEvent e){
        int response;
        response = JOptionPane.showConfirmDialog(null, "Are you sure you want exit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      if(response == JOptionPane.NO_OPTION){
          return;
      }else{
          exitForm(null);
      }
    }
    void colorMousePressed(MouseEvent e){
        Component clickedColor = e.getComponent();
        Toolkit.getDefaultToolkit().beep();
        if(e.getButton()==MouseEvent.BUTTON1){
            leftColor = clickedColor.getBackground();
            leftColorLabel.setBackground(leftColor);
        }
        else if(e.getButton() == MouseEvent.BUTTON3){
            rightColor = clickedColor.getBackground();
            rigthColorLabel.setBackground(rightColor);
        }
    }
    void drawPanelMousePressed(MouseEvent e){
        if(e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3){
            xPrevious = e.getX();
            yPrevious = e.getY();
            if(e.getButton() == MouseEvent.BUTTON1){
                drawColor = leftColor;
            }else{
                drawColor = rightColor;
            }
        }
    }
    void drawPanelMouseDragged(MouseEvent e){
        Line2D.Double myLine = new Line2D.Double(xPrevious, yPrevious, e.getX(), e.getY());
        g2D.setPaint(drawColor);
        g2D.draw(myLine);
        xPrevious = e.getX();
        yPrevious = e.getY();
        myLines.add(new ColoredLine(myLine, drawColor));
    }
    void drawPanelMouseReleased(MouseEvent e){
        if(e.getButton()==MouseEvent.BUTTON1 || e.getButton()==MouseEvent.BUTTON3){
            Line2D.Double myLine = new Line2D.Double(xPrevious, yPrevious, e.getX(), e.getY());
            g2D.setPaint(drawColor);
            g2D.draw(myLine);
        }
    }
    void exitForm(WindowEvent e){
        g2D.dispose();
        System.exit(0);
    }
    class GraphicsPanel extends JPanel{

    public GraphicsPanel() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D)g;
        super.paintComponent(g2D);
        for(int i = 0; i < BlackBoard.myLines.size(); i++){
            ColoredLine thisLine = (ColoredLine)BlackBoard.myLines.elementAt(i);
            g2D.setColor(thisLine.theColor);
            g2D.draw(thisLine.theLine);
        }
        g2D.dispose();
    }
       
}
    class ColoredLine {
    public Line2D.Double theLine;
    public Color theColor;

    public ColoredLine(Line2D.Double theLine, Color theColor) {
        this.theLine = theLine;
        this.theColor = theColor;
    }
    
}
}
    

