/**
 *@name avl.c
 *@brief holds avl tree related functions
 *@author Bryonna Klumker - Scott Chadde
 *@bug the inorder postorder preorder don't print the same things as what I get when I do it manually. I ignored this given time restraints and I talked to 4 other people who had the same problem which leads me to believe that it's in Scott's insert function
 *@bug the delete node does wonky things sometimes
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <assert.h>
#include "avl.h"

#define LEN 800

int height(struct AVL_T *root)
{
	if (!root)
		return 0;

	else
		return root->height;
}

int max(int a, int b)
{
	if (a < b)
		return b;
	else
		return a;
}

/*prints an avl tree in pre order
 *@param node root node of tree or subtree
 */
void preorder(struct AVL_T *node, void(*keyprint)(struct AVL_T *))
{
    if (node != NULL) {
        keyprint(node);
        preorder(node->left, keyprint);
        preorder(node->right, keyprint);
    }
}

/*prints an avl tree in order
 *@param node root node of tree or subtree
 */
void inorder(struct AVL_T *node, void(*keyprint)(struct AVL_T *))
{
	if (node != NULL) {
		inorder(node->left, keyprint);
		keyprint(node);
		inorder(node->right, keyprint);
	}
}

/*prints an avl tree in post order
 *@param node root node of tree or subtree
 */
void postorder(struct AVL_T *node, void(*keyprint)(struct AVL_T *))
{
    if (node != NULL) {
        postorder(node->left, keyprint);
        postorder(node->right, keyprint);
        keyprint(node);
   }
}
/*
 * Finds a node to replace the node being deleted
 *@param node of avl tree to be replaced
 */
struct AVL_T *find_max(struct AVL_T *node)
{
    struct AVL_T *tmp = node;
    while(tmp->right != NULL) {
        tmp = tmp->right;
    }
    return tmp;
}

/* LL new node inserted into the left subtree of the left subtree of the critical node */
struct AVL_T *single_rotate_left(struct AVL_T *x)
{
	struct AVL_T *w = x->left;
	x->left = w->right;
	w->right = x;
	
	x->height = max(height(x->left), height(x->right)) + 1;
	w->height = max(height(w->left), x->height) + 1;
	return w; 		/* new root */
	
}


/* RR new node inserted into the right subtree of the right subtree of the critical node */
struct AVL_T *single_rotate_right(struct AVL_T *w)
{
	struct AVL_T *x = w->right;
	w->right = x->left;
	x->left = w;
	
	w->height = max(height(w->left), height(w->right)) + 1;
	x->height = max(height(x->right), w->height) + 1;
	return x; 		/* new root */
	
}

/* LR rotation new node inserted into the right subtree of the left subtree of the critical node  */
struct AVL_T *double_rotate_left(struct AVL_T *z)
{
	z->left = single_rotate_right(z->left);
	return single_rotate_left(z);
}

/* RL rotation new node inserted into the left subtree of the right subtree of the critical node  */
struct AVL_T *double_rotate_right(struct AVL_T *z)
{
	z->right = single_rotate_left(z->right);
	return single_rotate_right(z);
}
/*given a node returns it's balance factor. lh - rh
 *@param node the node
 */
int get_bf(struct AVL_T *node)
{
    int lh, rh;
    if (!node->left)
        lh = 0;
    else
        lh = height(node->left);
    
    if (!node->right)
        rh = 0;
    else
        rh = height(node->right);
    
    return (lh - rh);
}
/*inserts a node given a *(int *)key into an avl tree
 *@param root root of avl tree
 *@param *(int *)key *(int *)key to be inserted
 */
struct AVL_T *insert(struct AVL_T *root, void *key, void *data, int(*avlcmp)(void *, void *), void(*insdata)(struct AVL_T *node, void *, void *))
{
	if (!root) {
		root = malloc(sizeof(struct AVL_T));
		if (!root) {
			printf("insert: malloc error\n");
			return NULL;
		}
		else {
			insdata(root, key, data);
			root->height = 0;
			root->left = NULL;
			root->right = NULL;
		}
		
	}
	else if(avlcmp(key, root->key) < 0) { /* left subtree *///u
		root->left = insert(root->left, key, data, avlcmp, insdata);
		if ((height(root->left) - height(root->right)) == 2) {
			if (avlcmp(key, root->left->key) < 0) /* left subtree */
				root = single_rotate_left(root); /* LL */
			else 	/* LR */
				root = double_rotate_left(root);

		}
	}
	else if (avlcmp(key, root->key) > 0) { /* right subtree */
		root->right = insert(root->right, key, data, avlcmp, insdata);
		if ((height(root->right) - height(root->left)) == 2) {
			if (avlcmp(key, root->right->key) < 0) /* left subtree */
				root = double_rotate_right(root); /* RL */
			else 	/* RR */
				root = single_rotate_right(root);
		}
	}

	root->height = max(height(root->left), height(root->right)) + 1;

	return root;
}
/* trim off whitespace from the right 
 *@param s string to be processed
*/
void rstrip(char *s)
{
    int i = strlen(s) - 1;
    while(s[i] == '\n') {
        s[i] = '\0';
        i--;
    }
}
/***convert string to lowercase. Assumes rstrip has been used first
 *@param s the string
 */
void tolowercase(char *s)
{
    int i;
    for (i = 0; i < strlen(s); i++)
        s[i] = tolower(s[i]);
    
}

/* Makes a new avl tree from a file holding data -- ints
 *@param file filename holding data
 *@param root root of tree, empty or existing
 */
struct AVL_T *avl_new(char *file, struct AVL_T *root, int(*avlcmp)(void *, void *), void(*insdata)(struct AVL_T *node, void *, void *))
{
    void *tmp_key = NULL;
    void *tmp_data = NULL;
    char buf[LEN] = {'\0'};
    FILE *filename = NULL;
    filename = fopen(file, "r");
    if (!filename) {
        printf("fopen-failed\n");
        return NULL;
    }
    int count = 0;
    while(fgets(buf, LEN, filename) != NULL) {
        if (count % 2 == 0) { //key
            rstrip(buf);
            tmp_key = malloc((strlen(buf) + 1)*sizeof(char));
            assert(tmp_key);
            strncpy((char *)tmp_key, buf, (strlen(buf) + 1)*sizeof(char));
        }
        else {//data for when generic
            rstrip(buf);
            tmp_data = malloc((strlen(buf) + 1)*(sizeof(char)));
            assert(tmp_data);
            strncpy((char *)tmp_data, buf, (strlen(buf) + 1)*sizeof(char));
            root = insert(root, tmp_key, tmp_data, avlcmp, insdata);
        }
      count++;
    }
    fclose(filename);
    return root;
}
/* this is a delete for a bst not an avl tree */
/* make it an avl deletion by accounting for rotations after you delete a node */
struct AVL_T *delete_node(struct AVL_T *root, void *key, int(*avlcmp)(void *, void *), void(*freedata)(struct AVL_T *node))
{

	struct AVL_T *tmp = NULL;
    int bf;
	/* find node */
	if (root == NULL)
		printf("element not found\n");
	else if(avlcmp(key, root->key) < 0) { /* left subtree */
		root->left = delete_node(root->left, key, avlcmp, freedata);
	}
	else if (avlcmp(key, root->key) > 0) { /* right subtree */
		root->right = delete_node(root->right, key, avlcmp, freedata);
	}
	else { 			/* found element */
		if (root->left && root->right) {
			/* two children */
			/* replace with largest in left subtree */
			/* you need to implement the find_max function */
			/* uncomment the next two lines once find_max is implemented */
			tmp = find_max(root->left);
			root->key = tmp->key;
			root->left = delete_node(root->left, root->key, avlcmp, freedata);
			
		}
		else {

			tmp = root;
			/* no children */
			if (root->left == NULL && root->right == NULL)
				root = NULL;
			else if (root->left != NULL) 			/* one child */
				root = root->left;
			else
				root = root->right;
				
		    freedata(tmp);
			free(tmp);
		}
	}
	if (!root) 
	    return root;
	    
	root->height = max(height(root->left), height(root->right)) + 1;
	
	bf = get_bf(root);
	
	if (bf == 2 && get_bf(root->left) >= 0) {
	    root = single_rotate_right(root);
	}
	else if (bf == 2 && get_bf(root->left) < 0) {
	    root->left = single_rotate_left(root->left);
	    root = single_rotate_right(root);
	}
	else if (bf == -2 && get_bf(root->right) <= 0) {
	    root = single_rotate_left(root);
	}
	else if (bf == -2 && get_bf(root->right) > 0) {
	    root->right = single_rotate_right(root->right);
	    root = single_rotate_left(root);
	}
	root->height = max(height(root->left), height(root->right)) + 1;
	return root;
}

/***Deletes an entire tree
 *@param root root of tree
 */
void avl_free(struct AVL_T *root, int(*avlcmp)(void *, void *), void(*freedata)(struct AVL_T *node)) {
    if (root) {
        avl_free(root->left, avlcmp, freedata);
        avl_free(root->right, avlcmp, freedata);
        freedata(root);
        free(root);
    }
}

/*** Finds an AVL node given a key and returns it
 *@param root root of tree
 *@param key to be found
 */
void avl_find(struct AVL_T *root, void *key, int(*avlcmp)(void *, void *), void(*printit)(struct AVL_T *node))
{
    if (root == NULL)
		printf("Element not found\n");
	else if(avlcmp(key, root->key) < 0) { /* left subtree */
		avl_find(root->left, key, avlcmp, printit);
	}
	else if (avlcmp(key, root->key) > 0) { /* right subtree */
		avl_find(root->right, key, avlcmp, printit);
	}
	else {
	    printit(root);
	}
} 


