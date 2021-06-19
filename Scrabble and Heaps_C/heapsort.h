#ifndef HEAPSORT_H_
#define HEAPSORT_H_
#include <sys/types.h>

/**
 * sort an array of nel objects
 * @param base initial element of the array
 * @param nel the number of elements in the array
 * @param width the size of each element
 * @compar function pointer to comparison function 
 * @return 0 for success; -1 for error 
 *
 * The comparison function must return an integer less than, equal to, or greater than zero
 * if the first argument is considered to be less than, equal to, or greater than the second.
 *
 * The function comes from freebsd.
 */
int heapsort(void *base, size_t nel, size_t width, int (*compar)(const void *, const void *));

#endif
