package controller;

import constant.Constant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class GameController {
    private static final int[] LEVEL_TYPE_NUM = new int[]{5};//第几关的固定类型数

    private static final int[][][] LEVEL_POSITION = new int[][][]{{{-160, 440}, {-160, 395}, {0, 440}, {0, 395},//第几关的固定位置
            {160, 440}, {160, 395}, {-160, 260}, {-160, 215}, {0, 260}, {0, 215}, {160, 260},
            {160, 215}, {-160, 80}, {-160, 35}, {0, 80}, {0, 35}, {160, 80}, {160, 35}}};

    private static final int X_NUM = 13;
    private static final int Y_NUM = 15;

    private static final int numBlockTypeCount = 6; // 随机关卡，种类的最少个数
    private static final int numBlockTypeAdd = 1; // 随机关卡，下一关比该关卡多几个种类
    private static final int numBlocksCount = 60; // 随机关卡，最少个块数 必须是3的倍数
    private static final int numBlocksCountAdd = 12; // 随机关卡，下一关比该关卡多几个块 必须是3的倍数
    private static final List<int[]> vPosList = new ArrayList<>(); // 可生成方块的坐标列表

    private List<BlockData> topData; // 场上数据
    private List<BlockData> bottomData;// 底部数据
    private RandomSeed randSeed;
    private int numLevel;

    public void start(int seed, int numLevel) {
        init();
        initLevel(seed, numLevel);
        refreshNodes();
    }

    private void init() {
        // 初始化可随机生成方块的点
        if (!GameController.vPosList.isEmpty()) {
            return;
        }
        for (int j = 0; j < GameController.Y_NUM; j++) {
            for (int i = 0; i < GameController.X_NUM; i++) {
                int[] arr = {i * 40 - (GameController.X_NUM - 1) * 40 / 2, j * 45 - 55};
                GameController.vPosList.add(arr);
            }
        }
    }

    // 根据开始这一关时服务器下发的随机数种子来创建关卡
    private void initLevel(int seed, int numLevel) {
        this.numLevel = numLevel;

        if (randSeed == null) {
            this.randSeed = new RandomSeed();
            this.randSeed.init(seed);
        }

        this.topData = new ArrayList<>();
        this.bottomData = new ArrayList<>();

        int typeNum = GameController.numBlockTypeCount + numLevel * GameController.numBlockTypeAdd;
        if (GameController.LEVEL_TYPE_NUM.length > numLevel) {
            typeNum = GameController.LEVEL_TYPE_NUM[numLevel];
        }

        if (typeNum > Constant.COLOR_LIST.size()) {
            typeNum = Constant.COLOR_LIST.size();
        }

        ArrayList<int[]> arrPos = new ArrayList<>();
        if (GameController.LEVEL_POSITION.length <= numLevel) {
            int num_block_geShu = GameController.numBlocksCount + numLevel * GameController.numBlocksCountAdd;
            for (int i = 0; i < num_block_geShu; i++) {
                int i_v3_random = (int) (this.randSeed.random() * GameController.vPosList.size());
                int[] pos = GameController.vPosList.get(i_v3_random).clone();
                arrPos.add(pos);
            }
        } else {
            int[][] posList = GameController.LEVEL_POSITION[numLevel];
            for (int[] arrayData : posList) {
                arrPos.add(arrayData.clone());
            }
        }

        int blockNum = -1;
        int num_type_random = (int) (this.randSeed.random() * typeNum);
        for (int[] arrayData : arrPos) {
            if (++blockNum % 3 == 0) {
                num_type_random = (int) (this.randSeed.random() * typeNum);
            }

            BlockData data = new BlockData(num_type_random, arrayData[0], arrayData[1]);
            this.topData.add(data);
        }

    }

    // 刷新节点的可点击状态
    private void refreshNodes() {
        int nSize = this.topData.size();
        for (int i = 0; i < nSize; i++) {
            BlockData data = this.topData.get(i);
            data.setCanTouch(true);
            int[] rect1 = data.getBoundRect();
            for (int j = i + 1; j < nSize; ++j) {
                BlockData data2 = this.topData.get(j);
                int[] rect2 = data2.getBoundRect();
                if (GameController.isRectCross(rect1[0], rect1[1], rect1[2], rect1[3], rect2[0], rect2[1], rect2[2], rect2[3])) {
                    data.setCanTouch(false);
                    break;
                }
            }
        }
    }

    static boolean isRectCross(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        return (Math.max(x1, x3) + 0.5 < Math.min(x2, x4) && Math.max(y1, y3) + 0.5 < Math.min(y2, y4)); // 增加一点像素误差
    }

    private void addBottom(BlockData data) {
        BlockData bottomData = new BlockData(data.blockType, data.getPos()[0], data.getPos()[1]);

        // 找到同一个元素插在它后边
        int index = this.bottomData.size();
        for (int i = index - 1; i >= 0; --i) {
            if (this.bottomData.get(i).blockType == bottomData.blockType) {
                index = i + 1;
            }
        }

        // 插入底部栏
        this.bottomData.add(index, bottomData);

        // 判断底部栏消除
        ArrayList<Integer> vHas = new ArrayList<Integer>();
        for (int i = 0; i < this.bottomData.size(); ++i) {
            if (this.bottomData.get(i).blockType == data.blockType)
                vHas.add(i);
        }
        if (vHas.size() >= 3) {
            for (int i = 0; i < vHas.size(); ++i) {
                this.bottomData.remove(vHas.get(i) - i);
            }
        }
    }

    public void forVData(Consumer<BlockData> consumer) {
        for (BlockData data : topData) {
            consumer.accept(data);
        }
    }

    public void forVBottomData(BiConsumer<Integer, Integer> consumer) {
        int i = 0;
        for (BlockData data : bottomData) {
            consumer.accept(i++, data.getBlockType());
        }
        for (int j = bottomData.size(); j < 6; j++) {
            consumer.accept(i++, -1);
        }
    }

    public boolean click(int x, int y) {
        if (topData == null || topData.isEmpty()) {
            start(-1, numLevel + 1);
            return false;
        }

        for (BlockData data : topData) {
            if (!data.isCanTouch()) {
                continue;
            }
            if (data.getX() < x && data.getY() < y && data.getX1() > x && data.getY1() > y) {
                topData.remove(data);
                addBottom(data);
                refreshNodes();

                if (topData.isEmpty()) {
                    start(-1, numLevel + 1);
                }

                if (bottomData.size() >= 6) {
                    start(-1, 0);
                }

                return true;
            }
        }
        return false;
    }
}