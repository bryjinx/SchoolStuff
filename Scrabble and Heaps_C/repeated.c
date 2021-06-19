#include <stdio.h>
#include <stdlib.h>
#include <string.h>
/***
 *@file repeated.c
 *@brief takes a char as a command line argument and prints the first repeated letter
 *@date 11-14-15
 *@bug Has a weird bug where you enter anything other than a letter it ... just doesn't do anything.
 */
char strchrrep(char *s);

int main(int argc, char **argv)
{
    char rpt;
	if (argc != 2) {
		printf("error in input\n");
		printf("usage: ./repeated [STRING]\n");
		printf("where [STRING] is the string to find the first repeated character in\n");
		exit(EXIT_FAILURE);
	}
	printf("gets here??");
	printf("Your input: %s\n", argv[1]); 
    rpt = strchrrep(argv[1]);
        /* call strchrrep(argv[1]) */;


	/* print the first repeated char in argv[1] */
	printf("The first repeated character is %c\n", rpt);
	return 0;
}

char strchrrep(char *s)
{
    int i = 0;
    int k; /*this is just a getting a temp so the compiler doesn't whine about type*/
    int ski[128] = {0};
    printf("gets here?");
    printf("In f: %s\n", s);
    for(; i < strlen(s); i++) {
        k = s[i];
        printf("k:%d chrk:%c\n", k, k);
        printf("s[i]: %c\n", s[i]);
        if (k >= 0 || k < 128) {
            ski[k]++;
            if (ski[k] == 2)
                break;
        }
    }
    printf("%c\n", s[i]);
    return s[i];
}
