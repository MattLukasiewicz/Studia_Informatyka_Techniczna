import os
import sys
D= sys.argv[1]
K = sys.argv[2]
for root, dirs, files in os.walk(D):
	for name in files:
		src = os.path.join(root,name)
		dst = os.path.join(K,name)
		if os.path.isfile(src) and os.access(src,os.W_OK):
			if not os.path.exists(dst):
				os.rename(src,dst)
