CC = gcc
CFLAGS = -g -Wall

all: simpson scrabble heap random grand

simpson: simpson.c
	$(CC) $(CFLAGS) $@.c -o $@ -lm

scrabble: scrabble.c
	$(CC) $(CFLAGS) $@.c -o $@ 

heap: heap.c
	$(CC) $(CFLAGS) $@.c -o $@ -lm

random: random.c random.h
	$(CC) -c $@.c

grand: grand.c random
	$(CC) $(CFLAGS) $@.c random.o -o $@

