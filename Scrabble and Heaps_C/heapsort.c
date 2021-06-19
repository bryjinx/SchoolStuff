#include "heapsort.h"
#include <sys/types.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/***
 *@author Bryonna Klumker
 *@date Nov 7 2015
 *@brief heapsort c file
 *@bug when it prints it has repeats and junk. I'm almost sure it's due to one or both of the swaps in this function.

The other way I had it was just reassigning addresses but it wouldn't compile. I got these errors:
heapsort.c: In function ‘heapsort’:
heapsort.c:41:36: error: lvalue required as left operand of assignment
             (base + (nel * width)) = tmp;
                                    ^
heapsort.c:54:46: error: lvalue required as left operand of assignment
                         (base + (c * width)) = (base + (p * width));
                                              ^
heapsort.c:55:46: error: lvalue required as left operand of assignment
                         (base + (p * width)) = tmp;



    I need more help/time to be able to get this one working ... but here it is anyways. 
 */
 
/***
 * Generic data type heapsort. returns 0 for success and -1 for failure.
 *@param base base structure holding data to be heaped
 *@param nel num elements in array
 *@param width size of each element
 *@param compar comparison function pointer
 */
int heapsort(void *base, size_t nel, size_t width, int (*compar)(const void *, const void *))
{

    int p = 0;
    int i = 0;
    char *tmp = NULL;
    int c = 0;
    int cmpr;
    int end = nel;
 
    /* todo generic heapsort */
    /* note you have to do pointer arthimetic with void pointers */
    /* can't use array notation, base[i] as that makes no sense as base is a void pointer  */
    /* correct way to get the i-th element is (base + i * width) */

    while (nel > 0) {
        if (nel != end) {
            /*This is the moving to end of array*/
            tmp = strdup((char *)base);
            *(char *)base = *(char *)(base + (nel * width));
            *(char *)(base + (nel * width)) = *(char *)tmp;
            nel--;
            //free(tmp);
        }
        c = 0;
        while ( c <= nel ) {
            if (c > 0) {
                p = (c - 1)/2;
                i = c;
                while( c != 0 ) {
                    cmpr = compar((base + (c * width)), (base + (p * width)));
                    if (cmpr > 0) { /*if child > parent*/
                    //swap
                        tmp = strdup((char *)(base + (c * width)));
                        *((char *)base + (c * width)) = *((char *)base + (p * width));
                        *((char *)base + (p * width)) = *tmp;
                        //free(tmp);
                    }
                    //move child to parent and parent to child's new parent
                    c = p;
                    p = (c - 1)/2;
                   }
            }
            c = i + 1;
        }
        if (nel == end)
            nel--;
    }
    
    return 0;
}

