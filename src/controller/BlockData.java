package controller;

import constant.Constant;

public class BlockData {
    public final int blockType;
    private final int[] centerPosition = new int[2]; //中心点
    private final int[] cornerPosition = new int[4]; //四点位置

    private boolean canTouch;

    public BlockData(int blockType,int x, int y) {
        this.blockType = blockType;

        this.centerPosition[0] = x;
        this.centerPosition[1] = y;

        this.cornerPosition[0] = x - Constant.BLOCK_WIDTH / 2;
        this.cornerPosition[1] = y - Constant.BLOCK_HEIGHT / 2;
        this.cornerPosition[2] = x + Constant.BLOCK_WIDTH / 2;
        this.cornerPosition[3] = y + Constant.BLOCK_HEIGHT / 2;
    }

    public boolean isCanTouch() {
        return canTouch;
    }

    public void setCanTouch(boolean canTouch) {
        this.canTouch = canTouch;
    }

    public int[] getPos() {
        return this.centerPosition;
    }

    public int[] getBoundRect() {
        return this.cornerPosition;
    }

    public int getX() {
        return cornerPosition[0];
    }

    public int getY() {
        return cornerPosition[1];
    }

    public int getX1() {
        return cornerPosition[2];
    }

    public int getY1() {
        return cornerPosition[3];
    }

    public int getBlockType() {
        return blockType;
    }
}