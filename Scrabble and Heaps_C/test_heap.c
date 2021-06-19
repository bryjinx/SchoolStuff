#include <stdio.h>
#include <assert.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include "heapsort.h"

/***
 *@file test_heap.c
 *@brief test file for heapsort. Also has comparison functions. 
 *@author Bryonna Klumker
 *@date 11-13-15
 *@bug 
 */
#define LEN 4096

/***convert string to lowercase. Assumes rstrip has been used first
 *@param s the string
 */
void tolowercase(char *s)
{
    int i;
    for (i = 0; i < strlen(s); i++)
        s[i] = tolower(s[i]);
    
}

/* trim off whitespace from the right 
 *@param s string to be processed
*/
void rstrip(char *s)
{
    int i = strlen(s) - 1;
    while(isspace(s[i])) {
        s[i] = '\0';
        i--;
    }
}
int heapcmpr(const void *w1, const void *w2) 
{
    int c;
    c = strcmp((char *)w1, (char *)w2);
    //printf("cmp: %d\t%s\t%s\n", c, (char *)w1, (char *)w2);
    return c;
}
    //return strcmp(*(char **)w1, *(char **)w2);


int main(int argc, char *argv[])
{
    char buf[LEN];
	char *t = NULL;

	char **list = NULL;
	int word = 1;
	int i;
	FILE *fp;

	fp = fopen(argv[1], "r");
	assert(fp);

        /* this leaks  you need to fix*/
	while(fgets(buf, LEN, fp)) {
		/* remove new line */
		rstrip(buf); 
		tolowercase(buf);
		t = malloc((strlen(buf) + 1) * sizeof(char));
                assert(t);
		strncpy(t, buf, strlen(buf) + 1);
		//printf("%s\n", t);  
		list = realloc(list, word * sizeof(char *)); 
		list[word - 1] = t;
		word++;
	}
        /* overcounted */
        word--;
    
        /* print the list */
	for(i = 0; i < word; i++) 
		printf("%s\n", list[i]);

    printf("\n");

	heapsort((void *)list, i - 1, sizeof(char *), heapcmpr);
        /* user needs to provide comparison function */
	
        /* print the sorted list */
    for(i = 0; i < word; i++) 
		printf("%s\n", list[i]);
    
    for(i = word - 1; i >=0; i--) {
        if (list[i]) {
            free(list[i]);
        }
    }
    free(list);
       return 0; 
}
