package com.nhnent.dooray;

import java.util.Scanner;

public class SachunSung {
    private enum Direction {
        E,W,S,N
    }
    private Command command;
    private int mapSize;
    private char[][] map;
    private Point sourcePoint;
    private Point targetPoint;
    private static final int LIMIT = 3;

    public SachunSung() {
        this.mapSize = 5;
        this.command = new Command();
        initMap();
    }

    private void initMap() {
        this.map = new char[][] {
            {'\0','\0','\0','\0','\0'},
            {'\0','!','@','#','\0'},
            {'\0','@','#','$','\0'},
            {'\0','!','\0','$','\0'},
            {'\0','\0','\0','\0','\0'},
        };
    }

    public boolean isMovable(Point point) {
        return isValidPoint(point) && !isMarker(point);

    }

    private boolean isValidPoint(Point point) {
        return !(point.x < 0 || point.x >= mapSize || point.y < 0 || point.y >= mapSize);
    }

    private boolean isMarker(Point point) {
        return isValidPoint(point) && !sourcePoint.equals(point) && map[point.y][point.x] != '\0';
    }

    private boolean matching() {
        return map[sourcePoint.y][sourcePoint.x] == map[targetPoint.y][targetPoint.x];
    }

    private boolean isValidLineCount(int lineCount) {
        return lineCount <= LIMIT;
    }

    private void deleteMarker() {
        map[sourcePoint.y][sourcePoint.x] = '\0';
        map[targetPoint.y][targetPoint.x] = '\0';
    }




    private boolean moveAndMatch(Direction direction, int lineCount, Point point) {
        if (!isValidLineCount(lineCount)) return false;
        Point newPoint = null;
        switch (direction) {
            case E: {
                newPoint = new Point(point.x+1, point.y);
                if (isMovable(newPoint)) {
                    return moveAndMatch(Direction.E, lineCount, newPoint)
                        || moveAndMatch(Direction.W, lineCount+1, newPoint)
                        || moveAndMatch(Direction.S, lineCount+1, newPoint)
                        || moveAndMatch(Direction.N, lineCount+1, newPoint);
                }
                break;
            }
            case W: {
                newPoint = new Point(point.x-1, point.y);
                if (isMovable(newPoint)) {
                    return moveAndMatch(Direction.E, lineCount+1, newPoint)
                        || moveAndMatch(Direction.W, lineCount, newPoint)
                        || moveAndMatch(Direction.S, lineCount+1, newPoint)
                        || moveAndMatch(Direction.N, lineCount+1, newPoint);
                }
                break;
            }
            case S: {
                newPoint = new Point(point.x, point.y+1);
                if (isMovable(newPoint)) {
                    return moveAndMatch(Direction.E, lineCount+1, newPoint)
                        || moveAndMatch(Direction.W, lineCount+1, newPoint)
                        || moveAndMatch(Direction.S, lineCount, newPoint)
                        || moveAndMatch(Direction.N, lineCount+1, newPoint);
                }
                break;
            }
            case N: {
                newPoint = new Point(point.x, point.y-1);
                if (isMovable(newPoint)) {
                    return moveAndMatch(Direction.E, lineCount+1, newPoint)
                        || moveAndMatch(Direction.W, lineCount+1, newPoint)
                        || moveAndMatch(Direction.S, lineCount+1, newPoint)
                        || moveAndMatch(Direction.N, lineCount, newPoint);
                }
                break;
            }
        }
        return isMarker(newPoint) && matching();
    }

    private void play() throws Exception{
        while (true) {
            print();

            String command = this.command.getCommand();
            setPoint(command);
            if (isValidPoint(sourcePoint, targetPoint)
                && (moveAndMatch(Direction.E, 1, sourcePoint)
                || moveAndMatch(Direction.W, 1, sourcePoint)
                || moveAndMatch(Direction.S, 1, sourcePoint)
                || moveAndMatch(Direction.N, 1, sourcePoint))) {
                System.out.println("true");
                deleteMarker();
            } else {
                System.out.println("false");
            }

            if (clear()) break;
        }
    }

    private boolean isValidPoint(Point sourcePoint, Point targetPoint) {
        return map[sourcePoint.y][sourcePoint.x] != '\0' && map[targetPoint.y][targetPoint.x] != '\0';
    }

    private void setPoint(String command) {
        int sourcePosition = Integer.parseInt(command.split(",")[0]);
        int targetPosition = Integer.parseInt(command.split(",")[1]);
        this.sourcePoint = new Point((sourcePosition-1) % this.mapSize, (sourcePosition-1) / this.mapSize);
        this.targetPoint = new Point((targetPosition-1) % this.mapSize, (targetPosition-1) / this.mapSize);
    }

    private void print() {
        int number = 1;
        for (char[] line : map) {
            for (char item : line) {
                System.out.printf("%2d:", number++);
                if (item =='\0')
                    System.out.print("  ");
                else
                    System.out.printf("%c ", item);
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean clear() {
        for (char[] line : map) {
            for (char item : line) {
                if (item != '\0') return false;
            }
        }
        return true;
    }

    public static class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            Point targetPoint = (Point)obj;
            return this.x == targetPoint.x && this.y == targetPoint.y;
        }
    }

    public static class Command {
        private Scanner scan = new Scanner(System.in);

        public String getCommand() {
            String command;
            command = scan.next();
            if (isValidCommand(command)) {
                return command;
            }
            return null;
        }

        private boolean isValidCommand(String command) {
            return command.split(",").length == 2;

        }
    }


    public static void main(String[] args) throws Exception {
        SachunSung sachunSung = new SachunSung();
        sachunSung.play();
    }
}
