#!/bin/bash

D=$1
P=$2

find "$D" -type f -printf "%p %s\n" | head -n 8 | sort -n -k 2 | tee "$P"

echo
