
import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ahmed
 */
public class TreeNode {

    private TreeNode parent;
    private LinkedList<TreeNode> children = new LinkedList<TreeNode>();
    private int level;
    private String name = new String("null");
    boolean terminal = false;
    private int x =0;
    private int y=0;
    private Point parentco;

    public void setParentco(Point p) {
        parentco = p;
    }

    public void setParent(TreeNode t) {
        parent = t;
    }

    public TreeNode getParent() {
        return parent;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setTerminal(boolean terminal) {
        this.terminal = terminal;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    int i = 0;

    public int getParentcox() {
        if (i == 0) {
            i = 1;
            return getParent().getX();
        }
        else {
            i=0;
            return -getParent().getX();
        }
    }

    public void setTerminal() {
        terminal = true;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public TreeNode(String s) {

        name = s;
    }

    public TreeNode() {
        x = 25;
        y = 255;
    }

    public TreeNode(String s, int l, TreeNode p) {
        // parent = p;
        level = l;
        name = s;
    }

    public void setChild(TreeNode s) {
        children.addLast(s);
    }
//
//    public void setParent(TreeNode parent) {
//        this.parent = parent;
//    }

    public void setChildren(LinkedList<TreeNode> children) {
        this.children = children;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setName(String name) {
        this.name = name;
    }
//
//    public TreeNode getParent() {
//        return parent;
//    }

    public LinkedList<TreeNode> getChildren() {
        return children;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }
//    int count =0;

    public int Count() {
        LinkedList<TreeNode> l = new LinkedList<TreeNode>();
        l = getChildren();
        if (l.isEmpty()) {
            return 0;
        }
        return Count() + l.size() + 1;
    }

}
