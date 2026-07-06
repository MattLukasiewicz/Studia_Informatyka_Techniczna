#!/bin/bash

Kz=$1
Kd=$2

find "$Kz" -type f -size -10c -exec mv {} "$Kd" \;

echo
