#!/bin/bash
K="$1"
p=0
k=0

for i in "$K"/*; do
	if [ -f "$i" ] && [ ! -s "$i" ]; then
		echo "$i"
		((p++))
	fi 
	if [ -d "$i" ] && [ -x "$i" ]; then
		((k++))
	fi
done


echo "$p"
echo "$k"
