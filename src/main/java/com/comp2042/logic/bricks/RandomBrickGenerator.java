package com.comp2042.logic.bricks;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomBrickGenerator implements BrickGenerator {

    private final List<Brick> brickList;

    private final Deque<Brick> nextBricks = new ArrayDeque<>();
//preview for 3 bricks
    private static final int QUEUE_SIZE = 3;

    public RandomBrickGenerator() {
        brickList = new ArrayList<>();
        brickList.add(new IBrick());
        brickList.add(new JBrick());
        brickList.add(new LBrick());
        brickList.add(new OBrick());
        brickList.add(new SBrick());
        brickList.add(new TBrick());
        brickList.add(new ZBrick());
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));

        while (nextBricks.size() < QUEUE_SIZE) {
            nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        }
    }

    @Override
    public Brick getBrick() {
        if (nextBricks.size() <= 1) {
            nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        }
        //Takes the next brick and tops up the queue
        Brick b = nextBricks.poll();
        while (nextBricks.size() < QUEUE_SIZE) {
            nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        }

        return b;
    }

    @Override
    public Brick getNextBrick() {
        return nextBricks.peek();
    }
    public List<Brick> getNextThree() {
        return new ArrayList<>(nextBricks);
    }

    //return the next 3 bricks as shape matrices
    @Override
    public List<int[][]> getNextThreeShapes() {
        List<int[][]> list = new ArrayList<>();
        for (Brick b : nextBricks) {
            list.add(b.getShapeMatrix().get(0));
        }
        return list;
    }

}
