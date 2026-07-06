import os
import sys

katalog= sys.argv[1]
plik=sys.argv[2]
with open (plik, 'w') as log:
	for root, dirs, files in os.walk(katalog):
		for f in files:
			sciezka = os.path.join(root,f)
			if os.path.getsize(sciezka) == 0 and not os.access(sciezka,os.R_OK):
				os.remove(sciezka)
				log.write(f +'\n')
