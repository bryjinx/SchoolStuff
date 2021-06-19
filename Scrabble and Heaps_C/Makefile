CC=gcc
CFLAGS=-g -Wall

all: test_qsort heapsort test_heap state_hash scrabble_lookup repeated scrabble_bsearch

test_qsort: test_qsort.c 
	$(CC) $(CFLAGS) $@.c -o $@

heapsort: heapsort.c
	$(CC) $(CFLAGS) -c $@.c

test_heap: test_heap.c heapsort
	$(CC) $(CFLAGS) $@.c heapsort.o -o $@

state_hash: state_hash.c 
	$(CC) $(CFLAGS) $@.c -o $@ -lm

scrabble_lookup: scrabble_lookup.c 
	$(CC) $(CFLAGS) $@.c -o $@ -lm

repeated: repeated.c 
	$(CC) $(CFLAGS) $@.c -o $@

scrabble_bsearch:
	$(CC) $(CFLAGS) $@.c -o $@

clean:
	rm *.o test_heap state_hash test_qsort scrabble_lookup repeated scrabble_bsearch
