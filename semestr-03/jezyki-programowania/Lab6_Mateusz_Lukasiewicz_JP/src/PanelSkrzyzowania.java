import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Panel rysujący skrzyżowanie
public class PanelSkrzyzowania extends JPanel {
    private final List<Car> cars = new ArrayList<>();
    private int carSize = 40; // Domyślny rozmiar auta
    private int minDistance = carSize * 2; // Minimalny dystans między samochodami
    public PanelSkrzyzowania() {
        // Inicjalizacja innych elementów
        startStatisticsUpdater(); // Uruchomienie timera
    }
    private int[] carsInLane = new int[4];

    // Funkcja do zgłoszenia wjazdu samochodu na pas
    public void carEnteredLane(int direction) {
        carsInLane[direction]++;
    }

    // Funkcja do zgłoszenia wyjazdu samochodu z pasa
    public void carExitedLane(int direction) {
        carsInLane[direction]--;
    }

    // Funkcja, która zwraca liczbę samochodów na danym kierunku
    public int getCarsInLane(int direction) {
        return carsInLane[direction];
    }
    //Światła
    private final TrafficLight northLight = new TrafficLight(30, 80);
    private final TrafficLight southLight = new TrafficLight(30, 80);
    private final TrafficLight eastLight = new TrafficLight(30, 80);
    private final TrafficLight westLight = new TrafficLight(30,80);

    // Metoda do cyklicznej aktualizacji statystyk


    public synchronized void setCarSize(int carSize) {
        this.carSize = carSize;
        this.minDistance = carSize * 2; // Aktualizuj minimalny odstęp przy zmianie rozmiaru auta
    }

    public synchronized void addCar(Car car) {
        cars.add(car);
        carEnteredLane(car.getDirection()); // Zwiększ licznik dla odpowiedniego kierunku
        new Thread(car).start();
    }

    public synchronized void removeCar(Car car) {
        cars.remove(car);
        carExitedLane(car.getDirection()); // Zmniejsz licznik dla odpowiedniego kierunku
        repaint();
    }


    public synchronized void clearCars() {
        for (Car car : cars) {
            car.stop(); // Zatrzymuje wątek każdego samochodu
        }
        cars.clear(); // Czyści listę samochodów
        repaint();    // Odświeża rysowanie panelu
    }
    public synchronized List<Car> getCarsOnLane(int laneDirection) {
        List<Car> carsOnLane = new ArrayList<>();
        for (Car car : cars) {
            if (car.getDirection() == laneDirection) {
                carsOnLane.add(car);
            }
        }
        return carsOnLane;
    }


    public synchronized boolean canCreateCar(int startX, int startY) {
        for (Car car : cars) {
            int dx = car.getX() - startX;
            int dy = car.getY() - startY;
            double distance = Math.sqrt(dx * dx + dy * dy);

            // Jeśli dystans jest mniejszy niż minimalny, zwróć false
            if (distance < minDistance) {
                return false;
            }
        }
        return true; // Jeśli żaden samochód nie jest zbyt blisko, można dodać auto
    }
    private long totalWaitTime = 0;
    private int totalCarsProcessed = 0;

    public synchronized void carProcessed(Car car) {
        totalWaitTime += car.getWaitTime();
        totalCarsProcessed++;
        repaint();
    }

    public synchronized double getAverageWaitTime() {
        return totalCarsProcessed > 0 ? (double) totalWaitTime / totalCarsProcessed : 0;
    }
    private int carsPassedInLastSecond = 0;

    private void resetThroughputCounter() {
        carsPassedInLastSecond = 0;
    }

    public synchronized void incrementThroughput() {
        carsPassedInLastSecond++;
    }

    public synchronized int getThroughput() {
        return carsPassedInLastSecond;
    }

    // Metoda do wyświetlania statystyk w konsoli
    public synchronized void logTrafficStatistics() {
        System.out.println("=== Statystyki Ruchu ===");
        System.out.println("Łączna liczba samochodów stworzonych: " + (totalCarsProcessed + cars.size()));
        System.out.println("Liczba samochodów przetworzonych: " + totalCarsProcessed);
        System.out.println("Liczba samochodów w kolejce: " + cars.size());
        System.out.println("Liczba samochodów na pasach i w kolejce dla każdego kierunku:");

        // Nazwy kierunków pasów
        String[] laneDirections = {"Północ", "Południe", "Wschód", "Zachód"};

        for (int i = 0; i < 4; i++) {
            int carsInLane = getCarsInLane(i); // Liczba samochodów na pasie
            int carsInQueue = countCarsInQueueForDirection(i); // Liczba samochodów w kolejce z danym kierunkiem
            System.out.println("  Pas (" + laneDirections[i] + "): " + carsInLane + " na pasie, "
                    + carsInQueue + " w kolejce");
        }

        System.out.println("Średni czas oczekiwania (przetworzone): "
                + String.format("%.2f", getAverageWaitTime() / 1000.0) + " s");

        System.out.println("==========================");
    }
    // Metoda licząca samochody w kolejce dla danego kierunku
    private int countCarsInQueueForDirection(int direction) {
        // Filtrowanie samochodów w kolejce na podstawie kierunku jazdy
        return (int) cars.stream()
                .filter(car -> car.getDirection() == direction)
                .count();
    }
    // Metoda dostępowa, która pozwala na dostęp do liczenia samochodów w kolejce
    public int getCountCarsInQueueForDirection(int direction) {
        return countCarsInQueueForDirection(direction);
    }

    // Aktualizacja metody startStatisticsUpdater, aby wyświetlać dane w konsoli co 5 sekund
    private void startStatisticsUpdater() {
        Timer timer = new Timer(500, e -> {
            resetThroughputCounter(); // Reset przepustowości
            repaint(); // Odśwież panel
        });
        timer.start();


    }


    @Override
    protected synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        int intersectionSize = Math.min(width, height) / 3;
        int centerX = (width - intersectionSize) / 2;
        int centerY = (height - intersectionSize) / 2;
        int laneWidth = intersectionSize / 2;
        int separatorWidth = 2;

        // Pasy ruchu
        g.setColor(Color.BLUE);
        g.fillRect(centerX, 0, laneWidth, centerY);
        g.fillRect(centerX + laneWidth, centerY + intersectionSize, laneWidth, height - (centerY + intersectionSize));
        g.fillRect(0, centerY + laneWidth, centerX, laneWidth);
        g.fillRect(centerX + intersectionSize, centerY, width - (centerX + intersectionSize), laneWidth);

        g.setColor(Color.RED);
        g.fillRect(centerX + laneWidth, 0, laneWidth, centerY);
        g.fillRect(centerX, centerY + intersectionSize, laneWidth, height - (centerY + intersectionSize));
        g.fillRect(0, centerY, centerX, laneWidth);
        g.fillRect(centerX + intersectionSize, centerY + laneWidth, width - (centerX + intersectionSize), laneWidth);

        // Separator
        g.setColor(Color.WHITE);
        g.fillRect(centerX + laneWidth - separatorWidth / 2, 0, separatorWidth, height);
        g.fillRect(0, centerY + laneWidth - separatorWidth / 2, width, separatorWidth);

        // Skrzyżowanie
        g.setColor(Color.DARK_GRAY);
        g.fillRect(centerX, centerY, intersectionSize, intersectionSize);





        // Linie zatrzymania
        g.setColor(Color.WHITE);
        int stopLineWidth = 2;


        // Północ
        g.fillRect(centerX, centerY, laneWidth, stopLineWidth);
        // Południe
        g.fillRect(centerX + laneWidth, centerY + intersectionSize, laneWidth, stopLineWidth);
        // Zachód
        g.fillRect(centerX, centerY + laneWidth, stopLineWidth, laneWidth);
        // Wschód
        g.fillRect(centerX + laneWidth * 2, centerY, stopLineWidth, laneWidth);

        // Rysowanie samochodów
        for (Car car : cars) {
            car.draw(g);
        }


        //Sygnalizacja
        // Północ
        northLight.draw(g, centerX - 40, centerY - 100);

        // Południe
        southLight.draw(g, centerX +270, centerY + 270);

        // Wschód
        eastLight.draw(g, centerX + 270, centerY - 100);

        // Zachód
        westLight.draw(g, centerX - 40, centerY + 270);



        // Wyświetlanie statystyk
        g.setColor(Color.BLACK);
        g.drawString("Średni czas oczekiwania: " + String.format("%.2f", getAverageWaitTime()/1000.0) + " s", 10, 20);
        g.drawString("Przepustowość: " + getThroughput() + " pojazdów/s", 10, 40);
    }
    public TrafficLight getNorthLight() {
        return northLight;
    }

    public TrafficLight getSouthLight() {
        return southLight;
    }

    public TrafficLight getEastLight() {
        return eastLight;
    }

    public TrafficLight getWestLight() {
        return westLight;
    }
}
class TrafficLight {
    private String state; // Możliwe wartości: "RED", "YELLOW", "GREEN"
    private final int width; // Szerokość sygnalizatora
    private final int height; // Wysokość sygnalizatora
    private final int lightDiameter; // Średnica każdego światła

    public TrafficLight() {
        this(50, 150); // Domyślna szerokość i wysokość
    }

    public TrafficLight(int width, int height) {
        this.state = "RED"; // Domyślnie wszystkie światła są czerwone
        this.width = width;
        this.height = height;
        this.lightDiameter = height / 5; // Średnica światła zależna od całkowitej wysokości
    }

    public synchronized String getState() {
        return state;
    }

    public synchronized void setState(String state) {
        this.state = state;
    }

    public void draw(Graphics g, int x, int y) {
        // Rysowanie prostokąta jako tło sygnalizacji
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);

        // Rysowanie świateł od góry do dołu: zielone, żółte, czerwone
        int lightX = x + (width - lightDiameter) / 2; // Środek poziomy dla świateł
        int greenY = y + 10; // Pozycja zielonego światła (na górze)
        int yellowY = greenY + lightDiameter + 10; // Pozycja żółtego światła (po środku)
        int redY = yellowY + lightDiameter + 10; // Pozycja czerwonego światła (na dole)

        g.setColor(state.equals("GREEN") ? Color.GREEN : Color.DARK_GRAY);
        g.fillOval(lightX, greenY, lightDiameter, lightDiameter);

        g.setColor(state.equals("YELLOW") ? Color.YELLOW : Color.DARK_GRAY);
        g.fillOval(lightX, yellowY, lightDiameter, lightDiameter);

        g.setColor(state.equals("RED") ? Color.RED : Color.DARK_GRAY);
        g.fillOval(lightX, redY, lightDiameter, lightDiameter);
    }

}