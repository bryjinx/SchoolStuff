#include <stdio.h>
#include <ctype.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include <math.h>

#define LEN 4096
/***
 *@file state_hash.c
 *@brief My hash of the postal file.
 *@author Bryonna Klumker
 *@todo quadratic probing for repeats. 
 *@date 11-13-15
 *@bug works with postal only. 
 */
struct postal_t {
        char *abrv; /*two digit abbreviation, capitalized */
        char *name; /*name in start case */
};


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
    int i = strlen(s);
    while(isspace(s[i]) ) {
        s[i] = '\0';
        i--;
    }
}

/***
 *Takes a char pointer and uses the letters in the string to come up with a hass using ascii polynomial method. assumes rstrip has been used. 
 *@param key the abbreviation
 *@param m size of the table
 */
int get_hash(char *key, int m)
{
    int i = 0;
    int hash = 0;
    
    for (; i < strlen(key); i++) {
        hash = (key[i] * pow(128, i)) + hash;
    }
    return hash % m;
}

int main(int argc, char *argv[])
{
    char buf[LEN] = {'\0'};
    char inp[LEN];
    char inp2[LEN];
    char abv[3];
    char ch;
    char *key = NULL;
    char *nme = NULL;
    int hash = 0;
    int i;
    
    struct postal_t *postal = malloc(200 * sizeof(struct postal_t));
    assert(postal);
    
	FILE *fp = fopen("postal", "r");
	assert(fp);

	while(fgets(buf, LEN, fp)) {
	        //printf("%s\n", (buf + 32));
        key = malloc(3*sizeof(char));
        assert(key != NULL);
	    memcpy(key, (buf + 32), 2);
        rstrip(key);
    
        hash = get_hash(key, 200);

        nme = malloc((strlen(buf) - 2)*sizeof(char));
        assert(nme != NULL);
        memcpy(nme, buf, 32);
        rstrip(nme);

        postal[hash].name = nme;
        postal[hash].abrv = key;
        free(key);
        free(nme);
        }
    fclose(fp);

    /**Menu for lookup and debug*/
    printf("Hello! Welcome to Bry's state lookup\n");
    while( ch != 'q') {
        printf("What would you like to do?\n a) Look up state\n b) Print table\n q) quit\n >>");
        fgets(inp, 8, stdin);
        sscanf(inp, "%c", &ch);
        //printf("%c\n", ch);
        switch(ch) {
            case('a'):
                printf("Please enter a 2 letter abbreviation\n>> ");
                fgets(inp, 32, stdin);
                sscanf(inp, "%s", abv);
                rstrip(abv);
                hash = get_hash(abv, 200);
                if(postal[hash].abrv)
                    printf("%s\n", postal[hash].name);
                    
                else
                    printf("It's not in the table.\n");
                    
                break;

            case('b'):
                i = 0;
                hash = 0;
                for(; i < 200; i++) {
                    if (postal[i].abrv) {
                        printf("i = %d\n%s\t%s\n\n", hash, postal[i].abrv, postal[i].name);
                        hash++;
                    }
                }
                break;
                
            case('q'):
                printf("Goodbye!\n");
                break;
                
            default:
                printf("You've entered something that's not a choice\n");
                break;
        }
    }
    i = 199;
    for(; i >= 0; i--) {
        if (postal[i].abrv) {
            free(postal[i].abrv);
            free(postal[i].name);
        }
    }
    free(postal);
    return 0;
}

