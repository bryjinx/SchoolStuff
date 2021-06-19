#ifndef AVL_H_
#define AVL_H_

struct AVL_T {
    int height;
    void *key;
    void *data;
    struct AVL_T *left;
    struct AVL_T *right;
};

int height(struct AVL_T *root);
int max(int a, int b);
void preorder(struct AVL_T *node, void(*keyprint)(struct AVL_T *));
void inorder(struct AVL_T *node, void(*keyprint)(struct AVL_T *));
void postorder(struct AVL_T *node, void(*keyprint)(struct AVL_T *));
struct AVL_T *find_max(struct AVL_T *node);
struct AVL_T *single_rotate_left(struct AVL_T *x);
struct AVL_T *single_rotate_right(struct AVL_T *w);
struct AVL_T *double_rotate_left(struct AVL_T *z);
struct AVL_T *double_rotate_right(struct AVL_T *z);
int get_bf(struct AVL_T *node);
struct AVL_T *insert(struct AVL_T *root, void *key, void *data, int(*avlcmp)(void *, void *), void(*insdata)(struct AVL_T *node, void *, void *));
void rstrip(char *s);
void tolowercase(char *s);
struct AVL_T *avl_new(char *file, struct AVL_T *root, int(*avlcmp)(void *, void *), void(*insdata)(struct AVL_T *node, void *, void *));
struct AVL_T *delete_node(struct AVL_T *root, void *key, int(*avlcmp)(void *, void *), void(*freedata)(struct AVL_T *node));
void avl_free(struct AVL_T *root, int(*avlcmp)(void *, void *), void(*freedata)(struct AVL_T *node));
void avl_find(struct AVL_T *root, void *key, int(*avlcmp)(void *, void *), void(*printit)(struct AVL_T *node));

#endif
