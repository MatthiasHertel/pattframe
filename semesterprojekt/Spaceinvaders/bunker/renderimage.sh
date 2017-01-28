#!/bin/bash
mogrify -resize 8x8 ./8x8/*.png
mogrify -resize 16x16 ./16x16/*.png
mogrify -resize 32x32 ./32x32/*.png
