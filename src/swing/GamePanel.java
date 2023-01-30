package swing;

import constant.Constant;
import controller.GameController;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 * @descript:
 * @author: baomengyang <baomengyang@sina.cn>
 * @create: 2023-01-30 10:07
 */
public class GamePanel extends JPanel {

    private static GameController gameController;


    public GamePanel() {
        super(null);
        this.setBounds(Constant.PANEL_X, Constant.PANEL_Y, Constant.PANEL_WIDTH, Constant.PANEL_HEIGHT);
        this.setBackground(Constant.PANEL_BACK_GROUND_COLOR);

        init();

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gameController.click(e.getX() - Constant.BLOCK_OFFSET_X, e.getY() - Constant.BLOCK_OFFSET_Y)) {
                    repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        this.repaint();
    }

    private void init() {
        gameController = new GameController();
        gameController.start(5000, 0);
    }

    public void paint(Graphics g) {
        super.paint(g);

        ((Graphics2D) g).setStroke(new BasicStroke(2.5f));

        gameController.forVData((data) -> {
            drawData(g, data.getX() + Constant.BLOCK_OFFSET_X, data.getY() + Constant.BLOCK_OFFSET_Y, data.getBlockType(), data.isCanTouch());
        });

        g.setColor(Color.lightGray);

        gameController.forVBottomData((i, type) -> {
            drawBottomData(g, Constant.FRAME_WIDTH - Constant.BLOCK_WIDTH * 2,
                    Constant.PANEL_Y + 20 + i * (20 + Constant.BLOCK_HEIGHT), type);
        });
    }

    public void drawData(Graphics g, int x, int y, int type, boolean light) {
        if (light) {
            g.setColor(Constant.getColor(type));
        } else {
            g.setColor(Constant.getColor(type).darker());
        }
        g.fillRect(x, y, Constant.BLOCK_WIDTH, Constant.BLOCK_HEIGHT);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, Constant.BLOCK_WIDTH, Constant.BLOCK_HEIGHT);
        g.drawOval(x, y, Constant.BLOCK_WIDTH, Constant.BLOCK_HEIGHT);

        if (!light) {
            g.drawLine(x, y, x + Constant.BLOCK_WIDTH, y + Constant.BLOCK_HEIGHT);
            g.drawLine(x + Constant.BLOCK_WIDTH, y, x, y + Constant.BLOCK_HEIGHT);
        }
    }

    public void drawBottomData(Graphics g, int x, int y, int type) {
        if (type >= 0) {
            drawData(g, x, y, type, true);
            return;
        }
        g.setColor(Color.WHITE.darker());
        g.fillRect(x, y, Constant.BLOCK_WIDTH, Constant.BLOCK_HEIGHT);
    }
}
