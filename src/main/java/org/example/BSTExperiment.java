package org.example;
import java.util.Random;

public class BSTExperiment {

    static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    static class BST {
        Node root;

        void insert(int value) {
            root = insert(root, value);
        }

        private Node insert(Node node, int value) {
            if (node == null) {
                return new Node(value);
            }

            if (value < node.value) {
                node.left = insert(node.left, value);
            } else {
                node.right = insert(node.right, value);
            }

            return node;
        }

        int height() {
            return height(root);
        }

        private int height(Node node) {
            if (node == null) {
                return 0;
            }

            return 1 + Math.max(
                    height(node.left),
                    height(node.right)
            );
        }

        int nodesVisited(int value) {
            Node current = root;
            int count = 0;

            while (current != null) {
                count++;

                if (value == current.value) {
                    return count;
                }

                if (value < current.value) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }

            return count;
        }
    }

    public static void main(String[] args) {
        Random random = new Random();

        int[] sizes = {100, 1000, 10000};
        int trials = 10;
        int K = 1000000;

        for (int size : sizes) {
            double totalHeight = 0;
            double totalSearchAverage = 0;

            for (int trial = 0; trial < trials; trial++) {
                BST tree = new BST();
                int[] values = new int[size];

                for (int i = 0; i < size; i++) {
                    int value = random.nextInt(K) + 1;

                    values[i] = value;
                    tree.insert(value);
                }

                totalHeight += tree.height();

                int totalVisits = 0;

                for (int value : values) {
                    totalVisits += tree.nodesVisited(value);
                }

                double averageVisits =
                        (double) totalVisits / size;

                totalSearchAverage += averageVisits;
            }

            double averageHeight =
                    totalHeight / trials;

            double averageVisits =
                    totalSearchAverage / trials;

            System.out.println("Number of values: " + size);
            System.out.printf(
                    "Average height: %.2f%n",
                    averageHeight
            );
            System.out.printf(
                    "Average nodes per search: %.2f%n",
                    averageVisits
            );
            System.out.println();
        }
    }
}
