package controller;

public class RandomSeed {
    private long _seed = 0;

    public void init(long seed) {
        this._seed = seed;
    }

    public double random() {
        int min = 0;
        int max = 1;
        this._seed = (this._seed * 9301 + 49297) % 233280;
        double rnd = this._seed / 233280.0;
        return min + rnd * (max - min);
    }
}