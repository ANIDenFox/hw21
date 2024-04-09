import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
      new UI();
    }
}

class UI extends JFrame implements MouseListener {
    private Random random = new Random();
    private ArrayList<CustomButton> buttons = new ArrayList<>();
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 600;

    UI() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLayout(null);
        setVisible(true);
        addMouseListener(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void moveButton(CustomButton button) {
        int x = button.getX();
        int y = button.getY();
        int width = button.getWidth();
        int height = button.getHeight();

        int stepx = button.getStepX();
        int stepy = button.getStepY();

        while (true) {
            if (x < 0 || x > WINDOW_WIDTH - width)
                stepx *= -1;

            if (y < 0 || y > WINDOW_HEIGHT - height)
                stepy *= -1;

            x += stepx;
            y += stepy;

            button.setBounds(x, y, width, height);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        CustomButton button = new CustomButton(random.nextInt(10) - 5, random.nextInt(10) - 5);

        button.setBackground(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));

        button.setBounds(e.getX(), e.getY(), 50, 50);
        add(button);
        buttons.add(button);

        Thread thread = new Thread(() -> moveButton(button));
        thread.start();

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(button);
                buttons.remove(button);
                revalidate();
                repaint();
            }
        });
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}

class CustomButton extends JButton {
    private int stepX;
    private int stepY;

    public CustomButton(int stepX, int stepY) {
        this.stepX = stepX;
        this.stepY = stepY;
    }

    public int getStepX() {
        return stepX;
    }

    public void setStepX(int stepX) {
        this.stepX = stepX;
    }

    public int getStepY() {
        return stepY;
    }

    public void setStepY(int stepY) {
        this.stepY = stepY;
    }
}
