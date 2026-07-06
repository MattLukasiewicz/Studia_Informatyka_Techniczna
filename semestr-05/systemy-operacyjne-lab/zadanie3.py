import os
import sys
katalog=sys.argv[1]
ile_malych=0
ile_zprawem = 0
for root, dirs, files in os.walk(katalog):
	for d in dirs:
		if os.access(os.path.join(root,d),os.R_OK):
			ile_zprawem+=1
	for f in files:	
		sciezka = os.path.join(root,f)
		if os.path.isfile(sciezka) and  os.path.getsize(sciezka)< 15:
			ile_malych+=1
print(ile_malych)
print(ile_zprawem)
