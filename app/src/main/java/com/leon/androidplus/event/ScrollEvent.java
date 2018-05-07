package com.leon.androidplus.event;

public class ScrollEvent {

    private Direction mDirection;

    public ScrollEvent(Direction d) {
        mDirection = d;
    }

    public Direction getDirection() {
        return mDirection;
    }

    public enum Direction {
        UP, DOWN
    }
}
