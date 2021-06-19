/**
 *@name avler.c
 *@brief loads a file given in the command line into an avl tree then provides a list of options for manipulating the tree
 *@author Bryonna Klumker
 *@bug 
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "avl.h"

#define LEN 100

/*** helper function -- prints a key
 *@param node node holding key and data
 */
void keyprint(struct AVL_T *node)
{
    printf("%s\n", (char *)(node->key));
}

/*** helper function -- prints a key and its data
 *@param node node holding key and data
 */
void keydataprint(struct AVL_T *node)
{
    printf("%s\n%s\n", (char *)(node->key), (char *)(node->data));
}

/*** helper function -- compares contents of two void pointers
 *@param a
 *@param b
*/
int avlcmp(void *a, void *b)
{
    int x = *(int *)a;
    int y = *(int *)b;    
    if (x < y)
        return -1;
    else if (x > y)
        return 1;
    else 
        return 0;
}

/*** helper function -frees data in a node
 *param node
 */
void freedata(struct AVL_T *node)
{
    free(node->key);
    free(node->data);
}

/*** helper function -puts data in a node
 *@param key of data for ordering in tree
 *@param data data associated with key
 */
void insdata(struct AVL_T *node, void *key, void *data)
{
    node->data = data;
    node->key = key;
}

int main(int argc, char *argv[]) 
{
    struct AVL_T *root = NULL;
    char ch;
    char *k = NULL;
    char buf[LEN] = {'\0'};
    char inp[LEN] = {'\0'};
    
    if (!argv[1]) {
        printf("arg [1] -- missing file name\n");
        return 0;
    }
    else { 
        root = avl_new(argv[1], root, avlcmp, insdata);
        if (!root)
            return 0;
    }
    //have menu to print preorder/inorder/postorder/add/delete
    while (ch != 'q') {
        printf("What would you like to do?\na) Print preorder\nb) Print inorder\nc) Print postrder\nd) delete an element\nq) quit\n >> ");
        fgets(buf, 16, stdin);
        sscanf(buf, "%c", &ch);
        printf("\n");
        switch(ch) {
            case('a'):
                preorder(root, keyprint);
                break;
            case('b'):
                inorder(root, keyprint);
                break;
            case('c'):
                postorder(root, keyprint);
                break;
            case('d'):
                printf("Delete (give key) >> ");
                fgets(inp, LEN, stdin);
                rstrip(inp);
                k = malloc(sizeof(char)*(strlen(inp) + 1));
                assert(k);
                strncpy(k, inp, sizeof(char)*(strlen(inp) + 1));
                printf("%s\n", k);
                root = delete_node(root, k, avlcmp, freedata);
                free(k);
                break;
            case('q'):
                printf("Goodbye!\n");
                
                break;
            default:
                printf("You've entered something that's not a choice");
                break;
        }
    }
    avl_free(root, avlcmp, freedata);
    return 0;
}
