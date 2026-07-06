import re
import csv
import os
from datetime import datetime

try:
    if os.path.exists('P.csv'):
        with open('P.csv', mode='r') as f:
            reader = csv.reader(f)
            for wiersz in reader:
                nowy_wiersz = []
                for i in range(len(wiersz)):
                    pole = wiersz[i]
                    if i != 2:
                        match = re.search(r"(\d{2}/\d{2}/\d{2})", pole)
                        if match:
                            try:
                                stara = match.group(1)
                                obiekt = datetime.strptime(stara, "%y/%d/%m")
                                nowa = obiekt.strftime("%-d %B %Y")
                                pole = pole.replace(stara, nowa)
                            except ValueError:
                                pass
                    nowy_wiersz.append(pole)
                print(",".join(nowy_wiersz))
    else:
        print("Plik P.csv nie istnieje")
except Exception as e:
    print(f"Blad: {e}")
