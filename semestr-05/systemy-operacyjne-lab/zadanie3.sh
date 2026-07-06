#!/bin/bash
K="$1"
p="$2"


for i in "$K"/*; do
        if [ -f "$i" ] && [ ! -s "$i" ] && [ ! -x "$i" ]; then
        	npliku=$(basename "$i")
		echo "$i" >> "$p"
		rm "$i"
        fi
done
