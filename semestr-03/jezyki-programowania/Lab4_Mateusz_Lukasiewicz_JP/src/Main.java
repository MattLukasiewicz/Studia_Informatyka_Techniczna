import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Main {
    public static void aktywujPola(boolean aktywne, JTextField poleA, JTextField poleB, JTextField poleC, JComboBox<Funkcja.TypFunkcji> comboBox, JComboBox<?> comboKolory) {
        poleA.setEnabled(aktywne);
        poleB.setEnabled(aktywne);
        comboBox.setEnabled(aktywne);
        comboKolory.setEnabled(aktywne);

        // Pole C zależy od typu funkcji
        Funkcja.TypFunkcji typ = (Funkcja.TypFunkcji) comboBox.getSelectedItem();
        if (typ == Funkcja.TypFunkcji.LINIOWA) {
            poleC.setText("0");
            poleC.setEnabled(false);
            poleC.setBackground(Color.GRAY);
        } else {
            poleC.setEnabled(aktywne);
            poleC.setBackground(aktywne ? Color.WHITE : Color.GRAY);
        }
    }



    public static void ustawStanListy(boolean aktywne, JList<Funkcja> lista, JButton buttonEdytuj, JButton buttonUsun) {
        lista.setEnabled(aktywne);
        buttonEdytuj.setEnabled(aktywne && !lista.isSelectionEmpty());
        buttonUsun.setEnabled(aktywne && !lista.isSelectionEmpty());
    }
    private static void zaktualizujStanPol(JTextField poleC, Funkcja.TypFunkcji typ) {
        if (typ == Funkcja.TypFunkcji.LINIOWA) {
            poleC.setText("0");          // Dla funkcji liniowej ustaw na 0
            poleC.setEnabled(false);     // Wyłącz pole c
            poleC.setBackground(Color.GRAY); // Zmień kolor na szary (nieaktywne)
        } else {
            poleC.setText("");           // Dla innych typów funkcji wyczyść pole
            poleC.setEnabled(true);
            poleC.setBackground(Color.WHITE); // Przywroc kolor na bialy (aktywne)
        }
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800); // Ustawienie rozmiaru okna
        frame.setLayout(null);
        frame.setBackground(Color.LIGHT_GRAY);



        //Panel gorny
        JPanel panelGorny = new JPanel();
        panelGorny.setLayout(null);
        panelGorny.setBounds(0, 0, 700, 100);
        panelGorny.setBackground(Color.LIGHT_GRAY);

        JLabel labelXMin = new JLabel("X Min:");
        JTextField poleXMin = new JTextField("-10");
        JLabel labelXMax = new JLabel("X Max:");
        JTextField poleXMax = new JTextField("10");
        JLabel labelK = new JLabel("K:");
        JTextField poleK = new JTextField("100");
        JButton buttonAktualizuj = new JButton("Aktualizuj");

        labelXMin.setBounds(10, 10, 50, 30);
        poleXMin.setBounds(60, 10, 100, 30);
        labelXMax.setBounds(180, 10, 50, 30);
        poleXMax.setBounds(230, 10, 100, 30);
        labelK.setBounds(340, 10, 20, 30);
        poleK.setBounds(360, 10, 100, 30);
        buttonAktualizuj.setBounds(480, 10, 100, 30);



        panelGorny.add(labelXMin);
        panelGorny.add(poleXMin);
        panelGorny.add(labelXMax);
        panelGorny.add(poleXMax);
        panelGorny.add(labelK);
        panelGorny.add(poleK);
        panelGorny.add(buttonAktualizuj);

        // Panel prawy
        Panel panelPrawy = new Panel();
        panelPrawy.setBounds(701, 0, 500, 800);

        // Lista funkcji
        DefaultListModel<Funkcja> modelListy = new DefaultListModel<>();
        JList<Funkcja> list = new JList<>(modelListy);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(20, 20, 300, 200);
        panelPrawy.add(scrollPane);

        // Panel na pola a, b, c i wybór typu funkcji i koloru
        JPanel panelDolny = new JPanel();
        panelDolny.setLayout(null);
        panelDolny.setBounds(20, 240, 460, 200);
        panelDolny.setBackground(Color.GRAY);


        JTextField poleA = new JTextField();
        JTextField poleB = new JTextField();
        JTextField poleC = new JTextField();

        JLabel labelA = new JLabel("a:");
        JLabel labelB = new JLabel("b:");
        JLabel labelC = new JLabel("c:");

        labelA.setBounds(10, 10, 20, 30);
        poleA.setBounds(40, 10, 100, 30);
        labelB.setBounds(10, 50, 20, 30);
        poleB.setBounds(40, 50, 100, 30);
        labelC.setBounds(10, 90, 20, 30);
        poleC.setBounds(40, 90, 100, 30);

        panelDolny.add(labelA);
        panelDolny.add(poleA);
        panelDolny.add(labelB);
        panelDolny.add(poleB);
        panelDolny.add(labelC);
        panelDolny.add(poleC);






        // Dodanie ComboBox  typ funkcji
        JComboBox<Funkcja.TypFunkcji> comboBox = new JComboBox<>(Funkcja.TypFunkcji.values());
        comboBox.setBounds(160, 90, 150, 30);
        panelDolny.add(comboBox);

        // Dodanie  panelu do panelu głównego
        panelPrawy.add(panelDolny);

        // Logika c
        comboBox.addActionListener(e -> {
            Funkcja.TypFunkcji typ = (Funkcja.TypFunkcji) comboBox.getSelectedItem();
            zaktualizujStanPol(poleC, typ);
        });


        // Ustawienie domyślnej wartości i aktualizacja stanu pola C
        comboBox.setSelectedItem(Funkcja.TypFunkcji.LINIOWA); // Domyślnie Liniowa
        zaktualizujStanPol(poleC, Funkcja.TypFunkcji.LINIOWA); // Pole C nieaktywne

        // Obsługa wyboru typu funkcji w JComboBox
        comboBox.addActionListener(e -> {
            Funkcja.TypFunkcji typ = (Funkcja.TypFunkcji) comboBox.getSelectedItem();
            zaktualizujStanPol(poleC, typ); // Aktualizacja stanu pola C w zależności od wybranego typu
        });

        JButton buttonDodajRysuj = new JButton("Dodaj");
        JButton buttonUsun = new JButton("Usuń");
        JButton buttonEdytuj = new JButton("Edytuj");
        int buttonWidth = 90;
        int buttonHeight = 30;
        int buttonSpacing = 10;

        buttonDodajRysuj.setBounds(330, 20, buttonWidth, buttonHeight);
        buttonUsun.setBounds(330, 60, buttonWidth, buttonHeight);
        buttonEdytuj.setBounds(330, 100, buttonWidth, buttonHeight);

        buttonUsun.setEnabled(false);
        buttonEdytuj.setEnabled(false);

        panelPrawy.add(buttonDodajRysuj);
        panelPrawy.add(buttonUsun);
        panelPrawy.add(buttonEdytuj);

        LinkedList<Funkcja> listaFunkcji = new LinkedList<>();
        PanelRysujacy panelRysujacy = new PanelRysujacy(listaFunkcji);

        panelRysujacy.setBounds(50, 120, 600, 600);

        // Obsługa listy i podświetlania przycisków
        list.addListSelectionListener(e -> {
            if (list.isEnabled()) {
                boolean zaznaczenie = !list.isSelectionEmpty();
                buttonUsun.setEnabled(zaznaczenie);
                buttonEdytuj.setEnabled(zaznaczenie);
            }
        });
        // Lista kolorów
        LinkedList<Kolor> listaKolorow = new LinkedList<>();
        String[] koloryNazwy = {"Czerwony", "Zielony", "Niebieski", "Żółty", "Czarny", "Pomarańczowy", "Różowy", "Szary", "Fioletowy", "Błękitny"};
        Color[] koloryRGB = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.BLACK, Color.ORANGE, Color.PINK, Color.GRAY, new Color(128, 0, 128), Color.CYAN};

        for (int i = 0; i < koloryNazwy.length; i++) {
            listaKolorow.add(new Kolor(koloryNazwy[i], koloryRGB[i]));
        }

        // Model ComboBox z listy kolorów
        DefaultComboBoxModel<Kolor> modelKolorow = new DefaultComboBoxModel<>(listaKolorow.toArray(new Kolor[0]));
        JComboBox<Kolor> comboKolory = new JComboBox<>(modelKolorow);
        comboKolory.setBounds(320, 90, 120, 30);
        panelDolny.add(comboKolory);

        // Domyślne ustawienie panelu dolnego jako nieaktywnego
        panelDolny.setBackground(Color.GRAY); // Panel wyłączony wizualnie
        aktywujPola(false, poleA, poleB, poleC, comboBox,comboKolory); // Pola domyślnie nieaktywne

        buttonDodajRysuj.addActionListener(new ActionListener() {
            private boolean trybDodawania = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (trybDodawania) {
                    // Tryb "Dodaj" - aktywacja pól
                    aktywujPola(true, poleA, poleB, poleC, comboBox, comboKolory);
                    buttonDodajRysuj.setText("Rysuj");
                    trybDodawania = false;
                } else {
                    // Tryb "Rysuj" - dodanie funkcji
                    try {
                        float a = Float.parseFloat(poleA.getText().trim());
                        float b = Float.parseFloat(poleB.getText().trim());
                        float c = Float.parseFloat(poleC.getText().trim());
                        Funkcja.TypFunkcji typ = (Funkcja.TypFunkcji) comboBox.getSelectedItem();

                        Kolor wybranyKolor = (Kolor) comboKolory.getSelectedItem();
                        if (wybranyKolor == null) {
                            JOptionPane.showMessageDialog(frame, "Wybierz kolor dla funkcji!", "Błąd", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Usuń wybrany kolor z ComboBox
                        modelKolorow.removeElement(wybranyKolor);

                        // Utworzenie nowej funkcji
                        Funkcja nowaFunkcja = new Funkcja(typ, a, b, c, wybranyKolor.getKolor());
                        nowaFunkcja.generujPunkty(panelRysujacy.getXMin(), panelRysujacy.getXMax(), panelRysujacy.getK());

                        listaFunkcji.add(nowaFunkcja);
                        modelListy.addElement(nowaFunkcja);
                        panelRysujacy.updateYRange();

                        // Resetowanie pól
                        poleA.setText("");
                        poleB.setText("");
                        poleC.setText("");
                        comboKolory.setSelectedIndex(-1);

                        // Powrót do trybu "Dodaj"
                        aktywujPola(false, poleA, poleB, poleC, comboBox, comboKolory);
                        buttonDodajRysuj.setText("Dodaj");
                        trybDodawania = true;

                        // Odśwież panel rysujący
                        panelRysujacy.repaint();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Wprowadź poprawne wartości liczbowe!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });




        //Button usun
        buttonUsun.addActionListener(e -> {
            Funkcja wybranaFunkcja = list.getSelectedValue();
            if (wybranaFunkcja != null) {
                int potwierdzenie = JOptionPane.showConfirmDialog(frame,
                        "Czy na pewno chcesz usunąć tę funkcję?",
                        "Potwierdzenie",
                        JOptionPane.YES_NO_OPTION);

                if (potwierdzenie == JOptionPane.YES_OPTION) {
                    listaFunkcji.remove(wybranaFunkcja);
                    modelListy.removeElement(wybranaFunkcja);

                    // Przywrócenie koloru do listy i modelu
                    for (Kolor kolor : listaKolorow) {
                        if (kolor.getKolor().equals(wybranaFunkcja.getKolor())) {
                            modelKolorow.addElement(kolor);
                            break;
                        }
                    }

                    // Jeśli lista funkcji jest pusta, ustaw domyślny zakres na (-10, 10) z (0, 0) na środku
                    if (listaFunkcji.isEmpty()) {
                        panelRysujacy.setXMin(-10);
                        panelRysujacy.setXMax(10);
                        panelRysujacy.setK(100); // Domyślna liczba punktów
                        panelRysujacy.updateYRange(); // Ręcznie ustaw zakres Y
                        panelRysujacy.setYMin(-10);
                        panelRysujacy.setYMax(10);
                    } else {
                        // Jeśli są jeszcze funkcje, aktualizuj zakres dynamicznie
                        panelRysujacy.updateYRange();
                    }

                    // Odśwież panel rysujący
                    panelRysujacy.repaint();
                }
            }
        });





        buttonEdytuj.addActionListener(e -> {
            Funkcja wybranaFunkcja = list.getSelectedValue();
            if (wybranaFunkcja != null) {
                // Przywrócenie koloru do listy i modelu
                for (Kolor kolor : listaKolorow) {
                    if (kolor.getKolor().equals(wybranaFunkcja.getKolor())) {
                        modelKolorow.addElement(kolor);
                        break;
                    }
                }

                // Ustaw pola na wartości edytowanej funkcji
                poleA.setText(String.valueOf(wybranaFunkcja.a));
                poleB.setText(String.valueOf(wybranaFunkcja.b));
                poleC.setText(String.valueOf(wybranaFunkcja.c));
                comboBox.setSelectedItem(wybranaFunkcja.getTypFunkcji());
                comboKolory.setSelectedItem(new Kolor("Tymczasowy", wybranaFunkcja.getKolor()));

                listaFunkcji.remove(wybranaFunkcja);
                modelListy.removeElement(wybranaFunkcja);

                // Zmień stan przycisku Dodaj/Rysuj
                buttonDodajRysuj.setText("Zapisz");
                aktywujPola(true, poleA, poleB, poleC, comboBox, comboKolory);

                // Aktualizacja zakresu Y po usunięciu funkcji do edycji
                panelRysujacy.updateYRange();
            }
        });





        // Obsługa przycisku "Aktualizuj"
        buttonAktualizuj.addActionListener(e -> {
            try {
                double xMin = Double.parseDouble(poleXMin.getText());
                double xMax = Double.parseDouble(poleXMax.getText());
                int k = Integer.parseInt(poleK.getText());

                if (k < 100 || xMin >= xMax) {
                    JOptionPane.showMessageDialog(frame, "Nieprawidłowe wartości!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                panelRysujacy.setXMin(xMin);
                panelRysujacy.setXMax(xMax);
                panelRysujacy.setK(k);

                for (Funkcja funkcja : listaFunkcji) {
                    funkcja.generujPunkty(xMin, xMax, k);
                }

                panelRysujacy.updateYRange(); // Aktualizujemy zakres Y
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Wprowadź poprawne wartości!", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        });



        // Przyscisk "Domyślne" w panelu górnym
        JButton buttonDomyslne = new JButton("Domyślne");
        buttonDomyslne.setBounds(480, 50, 100, 30); // Ustaw odpowiednie pozycjonowanie w panelu górnym
        panelGorny.add(buttonDomyslne);

        buttonDomyslne.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "Czy na pewno ustawić wartości domyślne?",
                    "Potwierdzenie",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // Ustaw wartości domyślne
                poleXMin.setText("-10");
                poleXMax.setText("10");
                poleK.setText("100");

                // Zaktualizuj parametry panelu rysującego
                panelRysujacy.setXMin(-10);
                panelRysujacy.setXMax(10);
                panelRysujacy.setK(100);

                // Przegeneruj punkty dla wszystkich funkcji
                for (Funkcja funkcja : listaFunkcji) {
                    funkcja.generujPunkty(-10, 10, 100);
                }

                // Zaktualizuj zakres Y i odśwież wykres
                panelRysujacy.updateYRange();
                panelRysujacy.repaint();

                // Powiadom użytkownika
                JOptionPane.showMessageDialog(frame, "Wartości domyślne zostały ustawione i wykres został zaktualizowany.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        // Przycsik "Reset" w dolnej części panelu prawego
        JButton buttonReset = new JButton("Reset");
        buttonReset.setBounds(150, 700, 200, 50);// Dół panelu prawego
        panelPrawy.add(buttonReset);

        buttonReset.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "Czy na pewno zresetować program?",
                    "Potwierdzenie",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                frame.dispose(); // Zamykamy bieżące okno
                SwingUtilities.invokeLater(() -> Main.main(new String[]{})); // Restart programu
            }
        });

        frame.add(panelGorny);
        frame.add(panelRysujacy);
        frame.add(panelPrawy);
        frame.setVisible(true);
    }
}