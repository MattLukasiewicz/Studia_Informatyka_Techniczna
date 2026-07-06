import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelRysujacy extends JPanel {
    private final List<Funkcja> listaFunkcji;

    private double xMin = -10; // Domyślny zakres X (minimalny)
    private double xMax = 10;  // Domyślny zakres X (maksymalny)
    private double yMin = -10; // Domyślny zakres Y (minimalny)
    private double yMax = 10;  // Domyślny zakres Y (maksymalny)
    private int k = 100;       // Domyślna liczba punktów

    public PanelRysujacy(List<Funkcja> listaFunkcji) {
        this.listaFunkcji = listaFunkcji;
        setBackground(Color.WHITE); // Tło panelu
    }

    public double getXMin() {
        return xMin;
    }

    public void setXMin(double xMin) {
        this.xMin = xMin;
        repaint(); // Odświeżanie po zmianie
    }

    public double getXMax() {
        return xMax;
    }

    public void setXMax(double xMax) {
        this.xMax = xMax;
        repaint(); // Odświeżanie po zmianie
    }

    public double getYMin() {
        return yMin;
    }

    public double getYMax() {
        return yMax;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
        repaint(); // Odświeżanie po zmianie
    }

    public void setYMin(double yMin) {
        this.yMin = yMin;
        repaint(); // Odświeżanie po zmianie
    }

    public void setYMax(double yMax) {
        this.yMax = yMax;
        repaint(); // Odświeżanie po zmianie
    }
    public void updateYRange() {
        yMin = Double.POSITIVE_INFINITY;
        yMax = Double.NEGATIVE_INFINITY;

        // Przejdź przez wszystkie funkcje i znajdź minimalne/maksymalne Y
        for (Funkcja funkcja : listaFunkcji) {
            for (double y : funkcja.yValues) {
                if (y < yMin) yMin = y;
                if (y > yMax) yMax = y;
            }
        }


        repaint(); // Odśwież wykres po zmianie zakresów
    }
    private double calculateStep(double range) {
        double rawStep = range / 10; // Domyślnie podziałka co 1/10 zakresu
        double magnitude = Math.pow(10, Math.floor(Math.log10(rawStep))); // Najbliższa potęga 10
        double refinedStep = rawStep / magnitude;

        if (refinedStep > 5) {
            refinedStep = 10;
        } else if (refinedStep > 2) {
            refinedStep = 5;
        } else if (refinedStep > 1) {
            refinedStep = 2;
        } else {
            refinedStep = 1;
        }
        return refinedStep * magnitude;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        int margin = 5; // Minimalny margines boczny

        double rangeX = xMax - xMin;
        double stepX = calculateStep(rangeX);
        double rangeY = yMax - yMin;
        double stepY = calculateStep(rangeY);

        // Skalowanie na podstawie dokładnych wartości bez marginesów
        double scaleX = (width - 2 * margin) / rangeX;
        double scaleY = height / rangeY;

        int centerX = (int) (margin + (-xMin) * scaleX);
        int centerY = (int) (height - (-yMin) * scaleY);

        // Rysowanie osi
        g2d.setColor(Color.BLACK);
        g2d.drawLine(margin, centerY, width - margin, centerY); // Oś OX
        g2d.drawLine(centerX, 0, centerX, height); // Oś OY

        // Rysowanie podziałek na osi OX
        for (double i = Math.ceil(xMin / stepX) * stepX; i <= xMax; i += stepX) {
            int x = (int) (margin + (i - xMin) * scaleX);
            g2d.drawLine(x, centerY - 5, x, centerY + 5);
            g2d.drawString(String.format("%.1f", i), x - 10, centerY + 20);
        }

        // Rysowanie podziałek na osi OY
        for (double i = Math.ceil(yMin / stepY) * stepY; i <= yMax; i += stepY) {
            int y = (int) (height - (i - yMin) * scaleY);
            g2d.drawLine(centerX - 5, y, centerX + 5, y);
            g2d.drawString(String.format("%.1f", i), centerX + 10, y + 5);
        }

        // Rysowanie funkcji
        for (Funkcja funkcja : listaFunkcji) {
            g2d.setColor(funkcja.getKolor());
            g2d.setStroke(new BasicStroke(funkcja.getGrubosc()));

            for (int i = 1; i < funkcja.xValues.size(); i++) {
                int x1 = (int) (margin + (funkcja.xValues.get(i - 1) - xMin) * scaleX);
                int y1 = (int) (height - (funkcja.yValues.get(i - 1) - yMin) * scaleY);
                int x2 = (int) (margin + (funkcja.xValues.get(i) - xMin) * scaleX);
                int y2 = (int) (height - (funkcja.yValues.get(i) - yMin) * scaleY);

                g2d.drawLine(x1, y1, x2, y2);
            }
        }
    }

}