package swing;

import constant.Constant;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @descript:
 * @author: baomengyang <baomengyang@sina.cn>
 * @create: 2023-01-30 10:02
 */
public class GameFrame extends JFrame {

    public void init() {
        this.setTitle("咩咩咩~~~");

//        Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        int JFrame_X = (int) (ScreenSize.getWidth() - Constant.FRAME_WIDTH) / 2;
//        int JFrame_Y = (int) (ScreenSize.getHeight() - Constant.FRAME_HEIGHT) / 2;
//        this.setBounds(JFrame_X, JFrame_Y, Constant.FRAME_WIDTH, Constant.FRAME_HEIGHT);

        this.setBounds(-1000, 100, Constant.FRAME_WIDTH, Constant.FRAME_HEIGHT);

        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel = new JPanel();
        jPanel.setBounds(0, 0, Constant.FRAME_WIDTH, Constant.FRAME_HEIGHT);
        jPanel.setBackground(Constant.FRAME_BACK_GROUND_COLOR);

        this.add(new GamePanel());
        this.add(jPanel);
    }

}
