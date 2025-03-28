import java.util.Scanner;

class TreeNode {
    String value;
    TreeNode left, right;
    int height;

    public TreeNode(String value) {
        this.value = value;
        this.height = 1;
    }
}

class AVLTree {
    private TreeNode root;

    private int height(TreeNode node) {
        return node == null ? 0 : node.height;
    }

    private int getBalance(TreeNode node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private TreeNode rightRotate(TreeNode y) {
        TreeNode x = y.left;
        TreeNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private TreeNode leftRotate(TreeNode x) {
        TreeNode y = x.right;
        TreeNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    public void insert(String value) {
        root = insertRec(root, value);
    }

    private TreeNode insertRec(TreeNode node, String value) {
        if (node == null) {
            return new TreeNode(value);
        }

        if (value.compareTo(node.value) < 0) {
            node.left = insertRec(node.left, value);
        } else if (value.compareTo(node.value) > 0) {
            node.right = insertRec(node.right, value);
        } else {
            return node; // Duplicate values are not allowed
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        if (balance > 1 && value.compareTo(node.left.value) < 0) {
            return rightRotate(node);
        }

        if (balance < -1 && value.compareTo(node.right.value) > 0) {
            return leftRotate(node);
        }

        if (balance > 1 && value.compareTo(node.left.value) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && value.compareTo(node.right.value) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public void preorder() {
        preorderRec(root);
        System.out.println();
    }

    private void preorderRec(TreeNode node) {
        if (node != null) {
            System.out.print(node.value + " ");
            preorderRec(node.left);
            preorderRec(node.right);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AVLTree avlTree = new AVLTree();

        System.out.println("Enter numbers or alphabets to insert into the AVL tree. Type 'end' to finish.");

        while (true) {
            System.out.print("Input: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("end")) {
                break;
            }

            if (input.matches("-?\\d+")) { // Integer input
                avlTree.insert(input);
            } else if (input.matches("[a-zA-Z]+")) { // Alphabetic input
                avlTree.insert(input);
            } else {
                System.out.println("Invalid input. Please enter a valid number or alphabet.");
            }
        }

        System.out.println("Preorder traversal of the AVL tree:");
        avlTree.preorder();
        scanner.close();
    }
}
