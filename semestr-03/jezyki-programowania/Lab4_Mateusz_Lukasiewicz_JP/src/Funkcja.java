import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Funkcja {
    public enum TypFunkcji {
        LINIOWA("Liniowa", "y = a·x + b") {
            @Override
            public double oblicz(double x, double a, double b, double c) {
                return a * x + b;
            }
        },
        KWADRATOWA("Kwadratowa", "y = a·x² + b·x + c") {
            @Override
            public double oblicz(double x, double a, double b, double c) {
                return a * x * x + b * x + c;
            }
        },
        TRYGONOMETRYCZNA("Trygonometryczna", "y = a·sin(x − b·π) + c") {
            @Override
            public double oblicz(double x, double a, double b, double c) {
                return a * Math.sin(x - b * Math.PI) + c;
            }
        };

        private final String nazwa;
        private final String formula;

        TypFunkcji(String nazwa, String formula) {
            this.nazwa = nazwa;
            this.formula = formula;
        }

        public String getNazwa() {
            return nazwa;
        }

        public String getFormula() {
            return formula;
        }

        public abstract double oblicz(double x, double a, double b, double c);
    }

    float a, b, c;
    private final TypFunkcji typ_funkcji;
    List<Double> xValues;
    List<Double> yValues;
    private final Color kolor; // Kolor funkcji
    private final float grubosc; // Grubość linii

    public Funkcja(TypFunkcji typ_funkcji, float a, float b, float c, Color kolor) {
        this.typ_funkcji = typ_funkcji;
        this.a = a;
        this.b = b;
        this.c = c;
        this.kolor = kolor;
        this.xValues = new ArrayList<>();
        this.yValues = new ArrayList<>();

        // Generowanie losowego koloru i grubości linii
        Random rand = new Random();
        this.grubosc = 1.0f + rand.nextFloat() * 4.0f; // Grubość od 1.0 do 5.0
    }


    public void generujPunkty(double xMin, double xMax, int k) {
        xValues.clear();
        yValues.clear();

        double step = (xMax - xMin) / (k - 1); // Równy odstęp między punktami
        for (int i = 0; i < k; i++) {
            double x = xMin + i * step;
            double y = typ_funkcji.oblicz(x, a, b, c);
            xValues.add(x);
            yValues.add(y);
        }
    }

    public TypFunkcji getTypFunkcji() {
        return typ_funkcji;
    }

    public Color getKolor() {
        return kolor;
    }

    public float getGrubosc() {
        return grubosc;
    }

    @Override
    public String toString() {
        return "Funkcja: " + typ_funkcji.getNazwa() + ", a=" + a + ", b=" + b + ", c=" + c;
    }
}