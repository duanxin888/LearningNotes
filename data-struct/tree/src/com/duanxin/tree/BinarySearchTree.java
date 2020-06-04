package com.duanxin.tree;

/**
 * 二叉查找树：节点大于左节点，小于右节点
 * @author duanxin
 * @version 1.0
 * @className BinarySearchTree
 * @date 2020/06/04 20:06
 */
public class BinarySearchTree {

    private Node tree;

    /**
     * 查找节点
     * @param data 数据
     * @date 2020/6/4 20:08
     * @return com.duanxin.tree.BinarySearchTree.Node
     */
    public Node find(int data) {
        Node p = tree;
        while (p != null) {
            if (p.data == data) {
                return p;
            } else if (p.data < data) {
                p = p.right;
            } else {
                p = p.left;
            }
        }
        return null;
    }

    /**
     * 添加
     * @param data 数据
     * @date 2020/6/4 20:08
     */
    public void insert(int data) {
        if (tree == null) {
            tree = new Node(data);
            return ;
        }

        Node p = tree;
        while (p != null) {
            if (data >= p.data) { // 当 data 大等于 该节点时
                if (p.right == null) {
                    p.right = new Node(data);
                    return ;
                }
                p = p.right;
            } else {
                if (p.left == null) {
                    p.left = new Node(data);
                    return ;
                }
                p = p.left;
            }
        }
    }

    /**
     * 删除
     * @param data 数据
     * @date 2020/6/4 20:09
     * @return com.duanxin.tree.BinarySearchTree.Node
     */
    public Node delete(int data) {
        Node p = tree; // p 指向要删除的节点，初始化指向根节点
        Node pp = null; // pp 指向要删除节点的父节点
        // 查找要删除的节点
        while (p != null && p.data != data) {
            pp = p;
            if (p.data < data) {
                p = p.right;
            } else {
                p = p.left;
            }
        }
        if (p == null) { // 无此节点
            return null;
        }

        // 要删除的节点有两个子节点
        if (p.right != null && p.left != null) {
            // 找出右子树中最小节点
            Node minP = p.right; // 最小节点
            Node minPP = p; // 最小节点的父节点
            while (minP.left != null) {
                minPP = minP;
                minP = minP.left;
            }
            p.data = minP.data;
            // 现在变成要删除 minP
            p = minP;
            pp = minPP;
        }

        // 删除节点是叶子节点或者仅有一个子节点
        Node child; // p 的子节点
        if (p.right != null) { // p 存在两个子节点的时候
            child = p.right;
        } else if (p.left != null){ // p 不存在两个子节点的时候
            child = p.left;
        } else {
            child = null;
        }

        if (pp == null) { // 要删除的根节点
            tree = child;
        } else if (p == pp.left) { // p 节点是 pp 的左节点
            pp.left = child;
        } else {
            pp.right = child;
        }
        return p;
    }

    /**
     * 查找最小子节点
     * @date 2020/6/4 20:55
     * @return com.duanxin.tree.BinarySearchTree.Node
     */
    public Node findMinNode() {
        if (tree == null) {
            return null;
        }
        Node p = tree;
        while (p.left != null) {
            p = p.left;
        }
        return p;
    }

    /**
     * 查找最大子节点
     * @date 2020/6/4 20:56
     * @return com.duanxin.tree.BinarySearchTree.Node
     */
    public Node findMaxNode() {
        if (tree == null) {
            return null;
        }
        Node p = tree;
        while (p.right != null) {
            p = p.right;
        }
        return p;
    }

    /**
     * 顺序输出
     * @date 2020/6/4 20:58
     */
    public void print(Node node) {
        if (node == null) {
            return ;
        }
        print(node.left);
        System.out.print(node.data + "  ");
        print(node.right);
    }

    /**
     * 返回根节点
     * @date 2020/6/4 21:01
     * @return com.duanxin.tree.BinarySearchTree.Node
     */
    public Node root() {
        return tree;
    }

    public static class Node {
        private int data;
        private Node left;
        private Node right;

        public Node(int data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(2);
        bst.insert(4);
        bst.insert(1);
        bst.insert(8);
        bst.insert(10);
        bst.insert(12);
        bst.insert(6);
        bst.insert(9);
        bst.insert(22);
        bst.insert(15);
        bst.print(bst.root());
        System.out.println();
        // 删除
        bst.delete(6);
        bst.print(bst.root());
        System.out.println();
        System.out.println(bst.findMinNode().data);
        System.out.println(bst.findMaxNode().data);
    }
}
