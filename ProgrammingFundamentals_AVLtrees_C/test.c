/**
 *@name test.c
 *@brief runs tests given in hw8
 *@author Bryonna Klumker
 *@bug the delete node does wonky things sometimes
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "avl.h"

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


int main()
{
    struct AVL_T *root = NULL;
    
    root = avl_new("greek.txt", root, avlcmp, insdata);
    printf("1) In-Order w/ data\n");
    inorder(root, keydataprint);
    printf("\n");
    printf("2) Pre-Order\n");
    preorder(root, keyprint);
    printf("\n");
    printf("3) Post-Order\n");
    postorder(root, keyprint);
    printf("\n4) ");
    avl_find(root, "Dionysis", avlcmp, keydataprint);
    printf("\n5) ");
    avl_find(root, "Jupiter", avlcmp, keydataprint);
    printf("\n 6)\n");
    root = delete_node(root, "Hestia", avlcmp, freedata);
    root = delete_node(root, "Artemis", avlcmp, freedata);
    root = delete_node(root, "Hades", avlcmp, freedata); 
    root = delete_node(root, "Pandora", avlcmp, freedata);
    root = delete_node(root, "Hephaestus", avlcmp, freedata);
    root = delete_node(root, "Zues", avlcmp, freedata);
    inorder(root, keyprint);
    printf("\n");
    avl_free(root, avlcmp, freedata);
    return 0;
}
