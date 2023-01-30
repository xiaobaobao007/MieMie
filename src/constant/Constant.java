package constant;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @descript:
 * @author: baomengyang <baomengyang@sina.cn>
 * @create: 2023-01-30 10:03
 */
public class Constant {
    public static final int FRAME_WIDTH = 780;
    public static final int FRAME_HEIGHT = 780;

    public static final int PANEL_X = 10;
    public static final int PANEL_Y = 10;
    public static final int PANEL_WIDTH = FRAME_WIDTH;
    public static final int PANEL_HEIGHT = FRAME_HEIGHT;

    public static final Color FRAME_BACK_GROUND_COLOR = new Color(83, 82, 82);
    public static final Color PANEL_BACK_GROUND_COLOR = FRAME_BACK_GROUND_COLOR;

    public static final int BLOCK_WIDTH = 80;
    public static final int BLOCK_HEIGHT = 90;

    public static final int BLOCK_OFFSET_X = 280;
    public static final int BLOCK_OFFSET_Y = 100;

    public static final List<Color> COLOR_LIST = Stream.of(
            new Color(255, 255, 255),//白色
            new Color(255, 0, 0),//红色
            new Color(255, 255, 0),//黄色
            new Color(255, 153, 18),//镉黄
            new Color(255, 192, 203),//粉红
            new Color(255, 227, 132),//粉黄
            new Color(255, 128, 0),//橘黄
            new Color(255, 0, 255),//深红
            new Color(0, 255, 0),//绿色
            new Color(128, 42, 42),//棕色
            new Color(0, 255, 255),//青色
            new Color(8, 46, 84),//靛青色
            new Color(34, 139, 34),//森林绿
            new Color(160, 82, 45),//赫色
            new Color(0, 0, 255),//蓝色
            new Color(160, 32, 240),//肖贡土色
            new Color(218, 112, 214),//淡紫色
            new Color(25, 25, 112),//深蓝
            new Color(0, 199, 140)//土耳其蓝
    ).collect(Collectors.toList());

    public static Color getColor(int type) {
        return COLOR_LIST.get(type);
    }

}
