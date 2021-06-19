/***
 *@file heap.c
 *@brief Reads in integers from a file and stores them in a pointer array. Heapifies and then performs a heap sort. Then prints out data in ascending order. 
 *@author Scott Chadde / (heapify / heapsort) Bryonna Klumker
 *@date 10-31-15
 *@bug
 */
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include <assert.h>
#include <math.h>

#define LEN 4096


struct heap_t {
	int last; 		/* index of last heap element in data array */
	int size;               /* number of elements in array */
	int max; 		/* allocated size of array */
	int *data;		/* the data array */

};

enum {INIT = 1, GROW = 2};

void heapify(int *nums, int end);
int main(int argc, char **argv) 
{

	char buf[LEN];
	FILE *fp = NULL;
	int i = 0;
	int cur, end, tmp;

       	if (argc != 2) {
		printf("error in input\n");
		printf("usage: ./heap [FILE]\n");
		printf("[FILE] is a list of integers one per line\n");
		exit(EXIT_FAILURE);
	}
	else {
		fp = fopen(argv[1], "r");
		assert(fp);
	}

	struct heap_t *heap = malloc(sizeof(struct heap_t));
	heap->last = 0;
	heap->size = INIT;
	heap->max = INIT;
	heap->data = NULL;

	while (fgets(buf, LEN, fp)) {

		/* read in data from file */
		/* assign to heap->data */

		/* grow the array as necessary */
		if (heap->size > heap->max) {
			heap->data = realloc(heap->data, GROW * heap->max *sizeof(int));
			assert(heap->data);
			heap->max = GROW * heap->max;
		}
		else if (heap->data == NULL) {
			heap->data = malloc(INIT * sizeof(int));
			assert(heap->data);
		}
		*(heap->data + i) = atoi(buf);
		heap->size++;
		i++;
	}	
		       

	/* size is off by one */
	heap->size--;
    i--;
    heap->last = i;
	/* heapify and then heapsort*/
    heapify(heap->data, heap->last); 
               
    end = heap->last;
    while (end != 0) {
        tmp = heap->data[0];
        heap->data[0] = heap->data[end];
        heap->data[end] = tmp;
        end--;
        heapify(heap->data, end);
    }          
	/* send data to stdin -- if you correctly built a heapsort
         * this will print the data in ascending order */
	for (i = 0; i < heap->size; i++) {
		printf("%d\n", heap->data[i]);	
	}
    

	/* cleanup */
	free(heap->data);
	free(heap);
	fclose(fp);

	return 0;
}
/**
 *Heapifies an array. May not do whole array; only sees array of size (end + 1)
 *@param *nums pointer version of an integer array
 *@param end the last index we're considering as part of the array
 */
void heapify(int *nums, int end)
{
    int p = 0;
    int i = 0;
    int tmp = 0;
    int c = 0;  
    while ( c <= end ) {
        if (c > 0) {
            p = (c - 1)/2;
            i = c;
            while( c != 0 ) {
                if (nums[c] > nums[p]) {
                //swap
                    tmp = nums[c];
                    nums[c] = nums[p];
                    nums[p] = tmp;
                }
                //move c to p and p to c's new p
                c = p;
                p = (c - 1)/2;
               }
        }
        c = i + 1;
    }
}
