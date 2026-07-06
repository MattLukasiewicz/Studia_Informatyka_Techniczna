import java.awt.*;
import java.util.List;
// Klasa reprezentująca samochód

public class Car implements Runnable {
    private int x, y; // Pozycja samochodu
    private final java.util.List<Point> path; // Ścieżka ruchu samochodu
    private final Color color; // Kolor samochodu
    private final PanelSkrzyzowania panel;
    private final int direction; // Kierunek z którego jedzie  samochod
    private volatile boolean running = true; // Flaga sterująca działaniem
    private final int carSize = 40; // Rozmiar auta
    private final int speed = 4; // Szybkość auta
    private long waitStartTime;
    private long waitTime = 0;

    public Car(int startX, int startY, java.util.List<Point> path, Color color, int direction, PanelSkrzyzowania panel) {
        this.x = startX;
        this.y = startY;
        this.path = path;
        this.color = color;
        this.direction = direction;
        this.panel = panel;
        startWaiting();

    }
    public void stop() {
        running = false; // Ustawia flagę na false, co przerywa pętlę w metodzie run
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public synchronized int getCarSize() {
        return carSize;
    }
    public void startWaiting() {
        this.waitStartTime = System.currentTimeMillis();
    }

    public void stopWaiting() {
        this.waitTime = System.currentTimeMillis() - this.waitStartTime;
    }

    public long getWaitTime() {
        return waitTime;
    }
    public int getDirection() {
        return direction;
    }
    private boolean isTurning = false;

    public boolean isTurning() {
        return isTurning;
    }




    @Override
    public void run() {

        try {
            boolean firstTime=true;
            for (Point point : path) {
                while (x != point.x || y != point.y) {
                    if (isBeforeStopLine()) {
                        if (!canMove()) {


                            Thread.sleep(50);
                            continue;
                        }
                    }



                    moveTowards(point.x, point.y);
                    Thread.sleep(30);// Odświeżenie panelu
                    panel.repaint();
                }
                if(firstTime) {
                    if (!isBeforeStopLine()) {
                        stopWaiting();
                        panel.carProcessed(this);
                        panel.incrementThroughput();
                        //panel.carExitedLane(this.getDirection());
                    }
                    firstTime=false;
                }
            }



            running = false;
            panel.removeCar(this); // Usunięcie samochodu z panelu
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private boolean isOutsideIntersection() {
        int width = panel.getWidth();
        int height = panel.getHeight();
        int intersectionSize = Math.min(width, height) / 3;
        int centerX = (width - intersectionSize) / 2;
        int centerY = (height - intersectionSize) / 2;

        // Sprawdzamy, czy samochód jest poza obszarem skrzyżowania
        switch (direction) {
            case 0: // Północ
                return x < centerX || x > centerX + intersectionSize || y > centerY + intersectionSize;

            case 1: // Południe
                return x < centerX || x > centerX + intersectionSize || y < centerY;

            case 3: // Zachód
                return y < centerY || y > centerY + intersectionSize || x > centerX + intersectionSize;

            case 2: // Wschód
                return y < centerY || y > centerY + intersectionSize || x < centerX;

            default:
                return false; // Dla nieznanego kierunku
        }
    }


    private boolean isBeforeStopLine() {
        int width = panel.getWidth();
        int height = panel.getHeight();
        int intersectionSize = Math.min(width, height) / 3;
        int centerX = (width - intersectionSize) / 2;
        int centerY = (height - intersectionSize) / 2;
        int laneWidth = intersectionSize / 2;


        switch (direction) {
            case 0: // Północ
                if (y <= centerY) {
                    return true;
                } else {
                    isTurning = true;
                    return false;
                } // Północ
            case 1: // Południe
                if (y >= centerY + intersectionSize) {
                    return true;
                } else {
                    isTurning = true;
                    return false;
                }
            case 3: // Zachód -
                if (x <= centerX) {
                    return true;
                } else {
                    isTurning = true;
                    return false;
                }
            case 2: // Wschód
                if (x >= centerX+intersectionSize){
                    return true;
                } else {
                    isTurning = true;
                    return false;
                }

            default:
                return false; // Dla nieznanego kierunku
        }
    }



    private boolean canMove() {
        int width = panel.getWidth();
        int height = panel.getHeight();
        int intersectionSize = Math.min(width, height) / 3;
        int centerX = (width - intersectionSize) / 2;
        int centerY = (height - intersectionSize) / 2;
        int laneWidth = intersectionSize / 2;

        // Uzyskanie listy samochodów na tym samym pasie
        List<Car> carsOnLane = panel.getCarsOnLane(direction);


        // Sprawdzanie, czy nie ma innych samochodów zbyt blisko
        for (Car otherCar : carsOnLane) {
            if (isTooClose(otherCar)) {
                return false; // Jeśli samochód jest zbyt blisko, nie ruszaj
            }
        }


        switch (direction) {
            case 0: // Północ
                return "GREEN".equals(panel.getNorthLight().getState())||y <centerY-30;
            case 1: // Południe
                return "GREEN".equals(panel.getSouthLight().getState())|| y >= centerY + intersectionSize+30;
            case 3: // Zachód -
                return "GREEN".equals(panel.getWestLight().getState())||x <= centerX-30;
            case 2: // Wschód -
                return "GREEN".equals(panel.getEastLight().getState())||x > centerX+intersectionSize+30;
            default:
                return false; // Dla nieznanego kierunku
        }

    }



    private boolean isTooClose(Car otherCar) {//jesli prawda to w canMove nie ruysza
        if (otherCar.isTurning()) {
            return false; // Ignoruj samochody w trakcie skręcania
        }

        // Sprawdzenie, czy inne auto znajduje się przed tym samochodem w danym kierunku
        switch (direction) {
            case 0: // Północ
                return otherCar.getY() > y && otherCar.getY() < y + getCarSize()*2; // Inne auto przed nim
            case 1: // Południe
                return otherCar.getY() < y && otherCar.getY() > y - getCarSize()*2; // Inne auto przed nim
            case 2: // Wschód
                return otherCar.getX() < x && otherCar.getX() > x - getCarSize()*2; // Inne auto przed nim;
            case 3: // Zachód
                return otherCar.getX() > x && otherCar.getX() < x + getCarSize()*2; // Inne auto przed nim
            default:
                return false; // Dla nieznanego kierunku
        }
    }


    public boolean hasFinished() {
        return !running && x == path.get(path.size() - 1).x && y == path.get(path.size() - 1).y;
    }



    private void moveTowards(int targetX, int targetY) {


        if (Math.abs(x - targetX) <= speed) {
            x = targetX; // Jeśli samochód jest blisko celu, ustaw go na cel
        } else if (x < targetX) {
            x += speed;
        } else if (x > targetX) {
            x -= speed;
        }

        if (Math.abs(y - targetY) <= speed) {
            y = targetY; // Jeśli samochód jest blisko celu, ustaw go na cel
        } else if (y < targetY) {
            y += speed;
        } else if (y > targetY) {
            y -= speed;
        }
    }




    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x - carSize / 2, y - carSize / 2, carSize, carSize);
    }

}