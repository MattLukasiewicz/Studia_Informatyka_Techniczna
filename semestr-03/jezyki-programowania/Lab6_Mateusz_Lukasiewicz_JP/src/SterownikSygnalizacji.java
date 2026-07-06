import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class SterownikSygnalizacji implements Runnable {
    private final PanelSkrzyzowania panel;

    public SterownikSygnalizacji(PanelSkrzyzowania panel) {
        this.panel = panel;
    }

    @Override
    public void run() {
        try {
            int currentDirection = -1; // Ostatni kierunek z zielonym światłem
            long greenStartTime = 0; // Czas rozpoczęcia zielonego światła
            int maxGreenLightDuration = 20000; // Maksymalny czas zielonego światła (20 sekund)
            int minGreenLightDuration = 8000;  // Minimalny czas zielonego światła (8 sekund)

            while (true) {
                int maxCars = 0;
                List<Integer> directionsWithMaxCars = new ArrayList<>();

                // Znalezienie kierunku z największą liczbą samochodów
                for (int i = 0; i < 4; i++) {
                    int carsWaiting = panel.getCarsInLane(i);
                    if (carsWaiting > maxCars) {
                        maxCars = carsWaiting;
                        directionsWithMaxCars.clear();
                        directionsWithMaxCars.add(i);
                    } else if (carsWaiting == maxCars) {
                        directionsWithMaxCars.add(i); // Dodajemy kierunek o tej samej liczbie samochodów
                    }
                }

                // Jeśli kierunek z największą liczbą samochodów jest ten sam co obecny
                if (currentDirection == directionsWithMaxCars.get(0)) {
                    // Sprawdź czas trwania zielonego światła
                    long elapsedTime = System.currentTimeMillis() - greenStartTime;
                    if (elapsedTime < maxGreenLightDuration) {
                        Thread.sleep(1000); // Kontynuuj zielone światło przez 1 sekundę
                        continue; // Wracaj do początku pętli
                    }
                }

                // Zmień światło na żółte dla obecnego kierunku
                if (currentDirection != -1) {
                    setYellowLight(currentDirection);
                    Thread.sleep(2000); // Światło żółte przez 2 sekundy
                }

                // Ustaw nowy kierunek z zielonym światłem
                // Jeśli jest więcej niż jeden kierunek o tej samej liczbie samochodów, losuj jeden
                currentDirection = directionsWithMaxCars.get(new Random().nextInt(directionsWithMaxCars.size()));
                setGreenLight(currentDirection);
                greenStartTime = System.currentTimeMillis(); // Zapisz czas rozpoczęcia zielonego światła

                // Utrzymuj zielone światło przez minimalny czas
                Thread.sleep(minGreenLightDuration);

                // Logowanie danych
                ///System.out.println("=== Zmiana świateł ===");
                ///System.out.println("Zielone światło dla kierunku: " + currentDirection);
                ///panel.logTrafficStatistics(); // Wywołanie logowania danych
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    private void setGreenLight(int direction) {
        panel.getNorthLight().setState(direction == 0 ? "GREEN" : "RED");
        panel.getSouthLight().setState(direction == 1 ? "GREEN" : "RED");
        panel.getEastLight().setState(direction == 2 ? "GREEN" : "RED");
        panel.getWestLight().setState(direction == 3 ? "GREEN" : "RED");
        panel.repaint();
    }

    private void setYellowLight(int direction) {
        switch (direction) {
            case 0 -> panel.getNorthLight().setState("YELLOW");
            case 1 -> panel.getSouthLight().setState("YELLOW");
            case 2 -> panel.getEastLight().setState("YELLOW");
            case 3 -> panel.getWestLight().setState("YELLOW");
        }
        panel.repaint();
    }

    private void setAllLightsRed() {
        panel.getNorthLight().setState("RED");
        panel.getSouthLight().setState("RED");
        panel.getEastLight().setState("RED");
        panel.getWestLight().setState("RED");
        panel.repaint();

        // Krótkie opóźnienie dla bezpieczeństwa
        try {
            Thread.sleep(2000); // Czas na upewnienie się, że skrzyżowanie jest puste
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }





}