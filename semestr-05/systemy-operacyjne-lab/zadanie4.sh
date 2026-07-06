#!/bin/bash


K1=$(readlink -f "$1")
K2=$(readlink -f "$2")



for i in "$K1"/*
do
    if [ -d "$i" ]; then

	mv "$i" "$K2/"
        nazwa_katalogu=$(basename "$i")
        ln -s "$K2/$nazwa_katalogu" "$K1/$nazwa_katalogu"
    fi
done
