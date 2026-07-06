#!/bin/bash
K1="$1"
K2="$2"


for i in "$K1"/*; do
        if [ -f "$i" ] && [ -w "$i" ]; then
		npliku=$(basename "$i")
		if [ ! -e  "$K2/$npliku" ]; then
			mv "$i" "$K2/"
		fi
	fi
done

