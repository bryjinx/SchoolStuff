/***
 *@file scrabble_bsearch.c
 *@brief Scrable helper menu. Helps you determine if a word is in the dictionary and what is the best play one can make with the tiles they have. Same as from Hw5 but uses c's bsearch() function instead of mine. 
 *@author Bryonna Klumker
 *@date 11-05-15
 *@bug 
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <assert.h>
#include <ctype.h>

#define LEN 100
#define END 178468

enum vals {INIT = 1, GROW = 2};
struct data_t {
	int nval; /*** current # of words in array*/
	int max; /***allocated number of words*/
	char **indx; /*** the index of array of words */
};
/***
 * Determines if a given word is in the Scrabble Dictionary
 * @param data the dictionary structure holding the words
 * @param word the word we're looking for
 */
void is_w(struct data_t *data, char *word)
{
 	int lo = 0;
 	int hi = END;
	int cmpr;
	int k, i = 0;
	int cond = 0;
	char *guess = NULL;
	
	while (cond != 1) {
		k = (lo + hi) / 2;
		guess = (data->indx[k]);
		cmpr = strcmp(guess, word);
		if (cmpr < 0)
			lo = k;
		else if (cmpr > 0)
			hi = k;
		else if (cmpr == 0)
			cond = 1;
		i++;
        if (i > 10000)
            cond = 1;
	}
    if (i <= 10000)
		printf("It is a word. Kudos!\n");
	else
		printf("Not a word, sorry.\n");
}
/***
 * Checks to see if tiles has enough of the right characters to spell word. Return 0 if yes and 1 if no. 
 *@param word word we're trying to make
 *@param tiles tiles we have
 */
int has_letters(char *word, char *tiles)
{
    int i = 0;
    int k = 0;
    char count1[26] = {0}; /*cnt of all letters in tiles*/
    char count2[26] = {0}; /*cnt of all letters in word*/
    if (strlen(word) > strlen(tiles))
        return 1;
        
    for(; i < strlen(tiles); i++) {
        k = tiles[i] - 97;
        count1[k]++;
    }

    for(i = 0; i < strlen(word); i++) {
        k = word[i] - 97;
        count2[k]++;
    }
    
    for(i = 0; i < 26; i++) {
        if (count1[i] < count2[i]) {
            return 1;
        }
    }
    return 0;
}
/***
 *Calculates Scrabble point value of a word and returns the score
 *@param word the word
*/
int get_score(char *word)
{
    int scr = 0;
    int point = 0;
    int i = 0;

    for(i = 0; i <= strlen(word) - 1; i++) {
    	if ((word[i] == 'a') || (word[i] == 'e') || (word[i] == 'i') || (word[i] == 'l') || (word[i] == 'n') || (word[i] == 'o') || (word[i] == 'r') || (word[i] == 's') || (word[i] == 't'))
    		point = 1;
        else if ((word[i] == 'b') || (word[i] == 'c') || (word[i] == 'm') || (word[i] == 'p'))
            point = 3;
    	else if ((word[i] == 'd') || (word[i] == 'g'))
    		point = 2;
		else if ((word[i] == 'f') || (word[i] == 'h') || (word[i] == 'v') || (word[i] == 'w') || (word[i] == 'y'))
			point = 4;
		else if ((word[i] == 'j') || (word[i] == 'x'))
		    point = 8;
		else if((word[i] == 'k'))
		    point = 5;
		else if((word[i] == 'q') || (word[i] == 'z'))
		    point = 10;
        scr += point;
    }
    return scr; 
    
}
/***
 *Finds and prints best play from tiles
 *@param data the structure holding dictionary
 *@param tiles the pointer pointing to the tiles 
 */
void best_play(struct data_t *data, char *tiles)
{

    int cmpr = 3;
    int scr1 = 0;
    int scr2 = 0;
    int bstscr = 0;
    int i = 0;
    char *best = NULL;
    for (i = 0; i <= END; i++) {
        cmpr = has_letters(data->indx[i], tiles);
        if (cmpr == 0) {
            if (best == NULL) {
                best = data->indx[i];
         }
         
            else {
                scr1 = get_score(best);
                scr2 = get_score(data->indx[i]);
                if (scr1 == scr2) {
                    bstscr = scr1;
                }
                else if (scr1 > scr2) {
                    best = best;
                    bstscr = scr1;
                }
                else {
                    best = data->indx[i];
                    bstscr = scr2;
                }
            }
        }
    }
    if (best == NULL)
        printf("I can't make a word with that.\n");
    else
        printf("The best play is %s for %d points\n", best, bstscr);
}
/***Comparison function for qsort
 *@param w1 first word
 *@param w2 second word
*/
int cmpstr(const void *w1, const void *w2)
{
    int c;
    c = strcmp((char *)w1, *(char **)w2);
    //printf("cmp: %d: %s vs %s\n", c, (char *)w1, (char *)w2);
    return c;
}

int main(void) 
{

	FILE *filename = fopen("scrabble.txt", "r");
	char buf[LEN];
    char inp[LEN];
    char inp2[LEN];
    char *bs = NULL;
	int i = 0;
    int j = 0;
    char ch;
    char word[32];

	if (!filename) {
		printf("fopen -- Failed\n");
		return 0;
	}
	struct data_t *data = malloc(sizeof(struct data_t));
    assert(data != NULL);
	data->nval = INIT;
	data->max = INIT;
	data->indx = NULL;
    
	while (fgets(buf, LEN, filename) != NULL) {
		if(data->indx == NULL) {
			data->indx = malloc(INIT*sizeof(char *));
            assert(data->indx != NULL);
            data->indx[0] = NULL;
        }    
		else if (data->nval >= data->max) {
			data->indx = realloc(data->indx, GROW*data->max*sizeof(char *));
			data->max = GROW * data->max;
		}
		
        for(j = strlen(buf) - 1; j >= 0; j--) {
                if (isspace(buf[j]))
                    buf[j] = '\0';
        }
        data->indx[i] = malloc(sizeof(char)*(strlen(buf) + 1));
        assert(data->indx[i] != NULL);
        strncpy(data->indx[i], buf, strlen(buf) + 1);
        i++;
        data->nval++;
	}
	data->nval--;
	fclose(filename);
	/*for(i = 0; i < END; i++) {
        printf("%s\n", data->indx[i]);
    }*/
    printf("Welcome to the Scrabble Helper\nWhat would you like to do?\n");
    while((ch != 'q')) {
        printf("(a) Check if a word is in the Scrabble Dictionary\n(b) Determine best play from Tiles\n(q) Quit\n>> ");
        fgets(inp, 8, stdin);
        sscanf(inp, "%c", &ch);
        switch(ch) {
            case ('a'):
                printf("What word are you considering\n>> ");
                fgets(inp, 32, stdin);
                sscanf(inp, "%s", word);
                for(i = strlen(word) - 1; i >= 0; i--) {
                    if (isspace(word[i]))
                        word[i] = '\0';
                }
                bs = bsearch((void *)word, (void *)data->indx, END, sizeof(char *), cmpstr);
                if (bs == NULL)
                    printf("It's not a word. Sorry\n");
                else
                    printf("It is a word. Kudos!\n");
				
                break;
            case ('b'):
                printf("What tiles do you have\n>> ");
                fgets(inp2, 32, stdin);
                sscanf(inp2, "%s", word);
                for(i = strlen(word) - 1; i >= 0; i--) {
                    if (isspace(word[i]))
                        word[i] = '\0';
                }
                best_play(data, word);
                break;
            case ('q'):
                printf("Goodbye. You better win, you cheater you\n");
                break;
            default:
                printf("You've entered something that's not a choice.\n");
                break;
        }
    }
    for(i = END; i >= 0; i--)
        free(data->indx[i]);
    free(data->indx);
    free(data);
	return 0;
}
