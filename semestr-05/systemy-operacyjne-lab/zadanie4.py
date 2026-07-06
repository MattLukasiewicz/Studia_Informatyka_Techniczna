import os
import sys
k1= sys.argv[1]
k2=sys.argv[2]
for plik in os.listdir(k1):
	src =os.path.join(k1,plik)
	dst = os.path.join(k2,plik)
	if os.path.isfile(src) and os.access(src, os.R_OK) and not os.path.exists(dst):
		os.rename(src,dst)
