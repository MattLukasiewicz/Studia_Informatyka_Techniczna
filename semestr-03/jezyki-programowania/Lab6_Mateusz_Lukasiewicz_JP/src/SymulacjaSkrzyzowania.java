import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// Główna klasa aplikacji
public class SymulacjaSkrzyzowania {
    private static Integer lastDirection = null;  // Ostatni wybrany kierunek
    public static void main(String[] args) {
        JFrame frame = new JFrame("Symulacja skrzyżowania");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PanelSkrzyzowania panel = new PanelSkrzyzowania();
        frame.add(panel);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);

        // Uruchamianie sterownik sygnalizacji świetlnej
        SterownikSygnalizacji controller = new SterownikSygnalizacji(panel);
        new Thread(controller).start();


        // Funkcja do uruchamiania symulacji
        Runnable startSimulation = () -> {
            panel.clearCars(); // Usunięcie wszystkich samochodów i zatrzymanie ich wątków

            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.scheduleAtFixedRate(() -> {
                Random random = new Random();

                int startX = 0, startY = 0; // Początkowe współrzędne
                List<Point> path = new ArrayList<>();
                int centerX = frame.getWidth() / 2;
                int centerY = frame.getHeight() / 2;
                int laneWidth = frame.getHeight() / 6;



                // Określenie liczby samochodów w każdym kierunku
                int northCount = panel.getCarsInLane(0) + panel.getCountCarsInQueueForDirection(0);
                int southCount = panel.getCarsInLane(1) + panel.getCountCarsInQueueForDirection(1);
                int eastCount = panel.getCarsInLane(2) + panel.getCountCarsInQueueForDirection(2);
                int westCount = panel.getCarsInLane(3) +  panel.getCountCarsInQueueForDirection(3);

                List<Integer> directions = new ArrayList<>();
                int minCount = Math.min(Math.min(northCount, southCount), Math.min(eastCount, westCount));

                // Dodajemy wszystkie kierunki, które mają najmniejszą liczbę samochodów
                if (northCount == minCount) {
                    directions.add(0);  // Północ
                }
                if (southCount == minCount) {
                    directions.add(1);  // Południe
                }
                if (eastCount == minCount) {
                    directions.add(2);  // Wschód
                }
                if (westCount == minCount) {
                    directions.add(3);  // Zachód
                }

                // Jeśli lastDirection nie jest null, usuwamy ten kierunek z listy dostępnych kierunków
                if (lastDirection != null) {
                    directions.remove(lastDirection);
                }

                // Jeżeli dostępne kierunki są, wybieramy jeden losowo
                int direction;
                if (!directions.isEmpty()) {
                    direction = directions.get(random.nextInt(directions.size()));
                } else {
                    // Jeśli wszystkie dostępne kierunki zostały wybrane, wybieramy ostatni kierunek
                    direction = lastDirection;
                }

                // Zaktualizuj ostatni kierunek
                lastDirection = direction;

                switch (direction) {
                    case 0: // Północ
                        startX = centerX - laneWidth / 2;
                        startY = 0; // Początek na górze ekranu
                        path.add(new Point(startX, centerY - laneWidth / 2)); // Skrzyżowanie
                        switch (random.nextInt(3)) {
                            case 0: // Południe - na wprost
                                path.add(new Point(startX, frame.getHeight())); // Na dół
                                break;
                            case 1: // Wschód - w lewo
                                path.add(new Point(frame.getWidth(), centerY + laneWidth / 2)); // Na prawo
                                break;
                            case 2: // Zachód - w prawo
                                path.add(new Point(0, centerY - laneWidth / 2)); // Na lewo
                                break;
                        }
                        break;
                    case 1: // Południe
                        startX = centerX + laneWidth / 2;
                        startY = frame.getHeight(); // Początek na dole ekranu
                        path.add(new Point(startX, centerY + laneWidth / 2)); // Skrzyżowanie
                        switch (random.nextInt(3)) {
                            case 0: // Północ - na wprost
                                path.add(new Point(startX, 0)); // Na górę
                                break;
                            case 1: // Zachód - w lewo
                                path.add(new Point(0, centerY - laneWidth / 2)); // Na lewo
                                break;
                            case 2: // Wschód - w prawo
                                path.add(new Point(frame.getWidth(), centerY + laneWidth / 2)); // Na prawo
                                break;
                        }
                        break;
                    case 2: // Wschód
                        startX = frame.getWidth(); // Początek z prawej strony
                        startY = centerY - laneWidth / 2;
                        path.add(new Point(centerX + laneWidth / 2, startY)); // Skrzyżowanie
                        switch (random.nextInt(3)) {
                            case 0: // Zachód - na wprost
                                path.add(new Point(0, startY)); // Na lewo
                                break;
                            case 1: // Południe - w lewo
                                path.add(new Point(centerX - laneWidth / 2, frame.getHeight())); // Na dół
                                break;
                            case 2: // Północ - w prawo
                                path.add(new Point(centerX + laneWidth / 2, 0)); // Na górę
                                break;
                        }
                        break;
                    case 3: // Zachód
                        startX = 0; // Początek z lewej strony
                        startY = centerY + laneWidth / 2;
                        path.add(new Point(centerX - laneWidth / 2, startY)); // Skrzyżowanie
                        switch (random.nextInt(3)) {
                            case 0: // Wschód - na wprost
                                path.add(new Point(frame.getWidth(), startY)); // Na prawo
                                break;
                            case 1: // Północ - w lewo
                                path.add(new Point(centerX + laneWidth / 2, 0)); // Na górę
                                break;
                            case 2: // Południe - w prawo
                                path.add(new Point(centerX - laneWidth / 2, frame.getHeight())); // Na dół
                                break;
                        }
                        break;
                    default:
                        System.err.println("Nieprawidłowy kierunek: " + direction);
                        return; // Wyjście w przypadku błędnego kierunku
                }


                if (panel.canCreateCar(startX, startY)) {
                    panel.addCar(new Car(startX, startY, path, Color.GREEN,direction, panel));
                }
            }, 0, 5, TimeUnit.SECONDS);
        };


        // Start symulacji po uruchomieniu programu
        startSimulation.run();

        // Dodanie ComponentListener do JFrame
        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                startSimulation.run(); // Restart symulacji po zmianie rozmiaru okna
            }
        });

        frame.setVisible(true);
    }
}

