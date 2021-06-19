/***
 *@file test_qsort.c
 *@brief file where I call qsort and write a comparison function
 *@author Bryonna Klumker
 *@date 11-10-15
 *@bug 
 */
#include <stdio.h>
#include <assert.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

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

/***Comparison function for qsort
 *@param w1 first word
 *@param w2 second word
*/
int cmpstr(const void *w1, const void *w2)
{
    int c;
    c = strcmp(*(char **)w1, *(char **)w2);
    //printf("cmp: %d\t%s\t%s\n", c, w1, w2);
    return c;
}

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

	/* this leaks -- fix */
	while(fgets(buf, LEN, fp)) {
		/* remove new line */
		rstrip(buf); 
		tolowercase(buf);
		t = malloc((strlen(buf) + 1) * sizeof(char));
            assert(t);
		strncpy(t, buf, strlen(buf) + 1);
        /* this needs improvement- could start at 128*/
		list = realloc(list, word * sizeof(char *));
		list[word - 1] = t;
		word++;
	}
	fclose(fp);
        /* overcounted */
        word--;
    
        /* print the list */
	for(i = 0; i < word; i++)
		printf("%s", list[i]);
		
	qsort((void *)list, i, sizeof(char *), cmpstr);

    printf("\n");
        /* print sorted list */	
    for(i = 0; i < word; i++) 
		printf("%s", list[i]);

    for(i = word - 1; i >=0; i--) {
        if (list[i]) {
            free(list[i]);
        }
    }
    free(list);
    return 0;
}
