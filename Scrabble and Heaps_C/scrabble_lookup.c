#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <string.h>
#include <ctype.h>
#include <math.h>
/***
 *@file scrabble_lookup.c
 *@brief loads a file of strings into a hash table and has a menu that allows you to determine if a word's in the file. 
 *@author Bryonna Klumker
 *@date 11-14-15
 *@bug 
 */
#define LEN 128

struct node_t {
    char *word; /* dictionary word */
    struct node_t *next; 
};

struct list_t {
    int nnodes; /* number of nodes list contains */
    struct node_t *head; /*head of the list */
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
    int i = strlen(s) - 1;
    while(isspace(s[i])) {
        s[i] = '\0';
        i--;
    }
}

/***
 *Takes a char pointer and uses the letters in the string to come up with a hash using ascii polynomial method. assumes rstrip has been used. 
 *@param key the abbreviation
 *@param m size of the table
 */
int get_hash(char *key, size_t m)
{
    int i = 0;
    int hash = 0;
    //printf("%d:init\n", hash);
    for (; i < strlen(key); i++) {
        hash = (key[i] * pow(16, i)) + hash;
    }
    //printf("%s, %d\n", key, hash);
    return hash % m;
}

/***
 * Prints dictionary. Returns load factor
 *@param dic pointer to list of pointers 
 *@param m table size
 */
float print_dict(struct list_t **dic, int m)
{
    int i, k;
    float xf = 0.0;
    struct node_t *node = NULL;

    for (i = 0; i < m; i++) {
        printf("     %d: ", i);
        
        if (dic[i]) {
            k = 0;
            xf += dic[i]->nnodes;
            node = dic[i]->head;
            while(node != NULL) {
                rstrip(node->word);
                if (node->next != NULL)
                    printf("%s, ", node->word);
                else
                    printf("%s\n", node->word);
                k++;
                node = node->next;
            }
        } else {
            printf("\n");
        }
        
    }
    return xf / m;
}
void free_list(struct node_t *node) 
{
    struct node_t *tmp = NULL;
    
    while (node->next) {
        tmp = node;
        node = node->next;
        free(tmp->word);
        free(tmp);
    }
    free(node->word);
    free(node);
}

void free_dict(struct list_t **dic, size_t m)
{
    int k = m - 1;
    struct node_t *tnode = NULL;
    
    for(; k >= 0; k--) {
        if (dic[k]) {
            tnode = dic[k]->head;
            if (tnode) {
                free_list(tnode);
            }
            free(dic[k]);
        }
    }
}

int main(int argc, char *argv[]) 
{
	char buf[LEN];
	char inp[LEN];
	char ch[20];
	char *tmpwrd = NULL;
	char stp = 'n';
	int fnd = 0;
	struct node_t *node = NULL;
	FILE *filen = NULL;
    int hash;
    float ldfct;
    //this fails if argv[1] is not provided
    if (!argv[1]) {
        printf("No file provided. Try again.\n");
        return 0;
    }
    else {
        filen = fopen(argv[1], "r");
        if (filen == NULL) {
            printf("No such file - fopen failed");
            return -1;
        }
    }
    if (!argv[2]) {
        printf("No table size provided. Try again\n");
        return -2;
    }
    //printf("%s\n %s\n", argv[1], argv[2]);
    //prrovide an error check and exit gracefully
    //with a usage statement
    size_t tbl_size = strtol(argv[2], NULL, 10);
    struct list_t **dict = calloc(tbl_size, sizeof(struct list_t *));
    assert(dict);

    //load dicttionary into hash table

    while(fgets(buf, LEN, filen) != NULL) {
        rstrip(buf);
        tolowercase(buf);
        hash = get_hash(buf, tbl_size);
        //printf("buf %s\n", buf);
        if (dict[hash] == NULL) {
            dict[hash] = malloc(sizeof(struct list_t));
            assert(dict[hash]);

            dict[hash]->head = malloc(sizeof(struct node_t));
            assert(dict[hash]->head);

            dict[hash]->head->next = NULL;
            dict[hash]->nnodes = 1;
            dict[hash]->head->word = strdup(buf);
        }
        else {
            node = malloc(sizeof(struct node_t));
            assert(node);
            node->next = dict[hash]->head;
            dict[hash]->head = node;
            dict[hash]->head->word = strdup(buf);
            dict[hash]->nnodes++;
        }
        //printf("%s", dict[hash]->head->word);
    }
    fclose(filen);
    //debug print hash table (see scrabble50000)
    //debug print load factor
    /*ldfct = print_dict(dict, tbl_size);
    printf("table size %d load factor: %.2f\n", tbl_size, ldfct);*/
    
    printf("Word lookup:\n");
    while (stp != 'q') {
        printf(" a) enter word\n q) quit\n>> ");
        fgets(inp, 32, stdin);
        sscanf(inp, "%c", &stp);
        switch(stp) {
            case('q'):
                printf("Goodbye!\n");
                break;
            case('a'):
                fnd = 0;
                printf("What word would you like to find?\n>> ");
                fgets(inp, 56, stdin);
                sscanf(inp, "%s", ch);
                tolowercase(ch);
                rstrip(ch);
                tmpwrd = strdup(ch);
                hash = get_hash(ch, tbl_size);
                //printf("%d\n", hash);
                if (dict[hash]) {
                    node = dict[hash]->head;
                    while (node) {
                        if (strcmp(node->word, tmpwrd) == 0) {
                            fnd = 1;
                            node = node->next;
                         }
                        else {
                            node = node->next;
                        }
                    }
                }
                if (fnd == 1) {
                    printf("It's in the dictionary. Nice!\n");
                }    
                else {
                    printf("Not in there; sorry\n");
                }
                free(tmpwrd);
                break;
            default:
                printf("You've entered something that's not a choice\n");
                break;
                
            }
        }
    free_dict(dict, tbl_size);
    free(dict);
    return 0;
}
    //menu to lookup a word only -- should be able to turn off for debug printing
    //free memory


