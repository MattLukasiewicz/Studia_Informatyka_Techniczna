#!/bin/bash

K1=$(readlink -f "$1")
K2=$(readlink -f "$2")


for plik in "$K2"/*
do

    if [ -f "$plik" ]; then

        ln -s "$plik" "$K1/"
    fi
done
