package com.raj.strings;

import com.raj.Util;

import java.util.*;

public class BoggleSolver {

    /**
     * You are given a dictionary set dictionary that contains dictionaryCount distinct words and a matrix mat of size n*m.
     * Your task is to find all possible words that can be formed by a sequence of adjacent characters in the matrix mat.
     * Note that we can move to any of 8 adjacent characters, but a word should not have multiple instances of the same cell of the matrix.
     */
    public static void main(String[] args) {
        System.out.println(boggle_solver(Arrays.asList("bst", "heap", "tree"), Arrays.asList("bsh", "tee", "arh")));
    }

    static void brute_force() {
        /**
         * Insert all the words from the dictionary into a hash map for a linear time lookup operation (linear over length of string because it takes linear time to calculate hash of a string).
         * Now, we iterate over all the cells of the matrix mat and assume each cell as the first character of the our word
         * Recursively build all the possible words by visiting all its neighbouring cells and each time we visit its neighbour we keep building the word by appending the neighbour’s cell char at the end of our current word.
         * Also, each time we build a word we make a lookup in our hash map. If the current state of the word exists in the hash map then we found it in the mat and we add it to the found words set.
         * Also, once we found a word we remove it from the HashMap so as to avoid its duplicate match.
         * Now, consider a cell (i,j) as the first character when we are building our word. First move we can move to 8 directions.
         * For next move we only have 7 directions as one direction of the 8 possible direction will be the previous visited cell.
         * So, from this point on we will be having 7 possible directions to visit for the current cell. So, 7 possible dirs for each of the mxn cells.
         * Runtime = O(max_length_of_string * m * n * (7^max_length_of_string))    ... assuming we terminate dfs after max dict word
         *  where n denotes the number of strings in given array mat,
         *  m denotes the length of a string of given array mat,
         *  max_length_of_string is the maximum length of the dictionary word and dictionaryCount denotes the number of words in dictionary.
         * Aux space = O(dictionaryCount * max_length_of_string) + O(n*m)
         */
    }

    /**
     * Optimal:
     * We will iterate over all cells of the matrix mat and for each cell we will do a DFS traversal as the first character of the word,
     * But this time we will be using our trie to guide our dfs & we will only visit that neighbour that assures that
     * the word with the current prefix exists in the dictionary. Using this we will prune a lot of branches in our word building traversal.
     * Every time we find a word we simply remove it from the trie. This will ensure that the trie does not give dupes
     * for previously found words & hence it will prune some more DFS branches.
     *
     * As we are still doing the same DFS traversal as in the brute force approach but with some intelligent choices
     * while choosing the direction from the current cell, so as we form the target word more quickly.
     * But for worst case we will end up forming all possible words even after the guiding given by the trie.
     *
     * Consider below example :
     * mat = [“aaaaaaaaaa”,
     * “aaaaaaaaaa”,
     * “aaaaaaaaaa”,
     * “aaaaaaxxxx”,
     * “aaaaaaxbcd”]
     * dictionary = [ “aaaaaaaaab” , “aaaaaaaaac”, “aaaaaaaaad”]
     * As it is evident that the letter ‘b’,’c’ and ‘d’ and being shielded by the cover of ‘x’ layer.
     * Hence unfortunately total time complexity still be O(n*m*7^(max_length_of_string)) + O(dictionaryCount*max_length_of_string), but this is only in worst case.
     * In average and ideal cases this approach performs much better => O(n * m * max_length_dict_word)
     *
     * Leetcode - Concise/Efficient Solution: https://leetcode.com/problems/word-search-ii/discuss/59780/Java-15ms-Easiest-Solution-(100.00)
     */
    public static List<String> boggle_solver(List<String> dictionary, List<String> mat) {
        Set<String> res = new HashSet<>();
        // build Trie of dictionary words
        Trie trie = new Trie(dictionary);

        // build a 2D grid of mat
        Grid grid = new Grid(mat);

        // fix a grid char to launch dfs
        for (int i = 0; i < grid.G.length; i++) {
            for (int j = 0; j < grid.G[0].length; j++) {
                char c = grid.G[i][j];
                grid.G[i][j] = '$';  // mark visited by reusing the grid (no extra space for visited)
                List<String> words = new ArrayList<>();
                Stack<Character> stack = new Stack<>();
                stack.add(c);       // add char
                dfs(grid, trie, trie.root.childs.get(c), stack, words, new Util.Point(i,j));
                res.addAll(words);
                grid.G[i][j] = c;  // revert visited flag
                stack.pop();     // revert char
            }
        }
        return new ArrayList<>(res);
    }

    static void dfs(Grid grid, Trie trie, TNode node, Stack<Character> soFar, List<String> words, Util.Point point) {
        if (node == null) return;
        if (node.isWord) {
            StringBuilder sb = new StringBuilder();
            soFar.forEach(sb::append);
            words.add(sb.toString()); // valid word found
            trie.removeStr(sb.toString());  // remove from trie to prune dfs branches later
        }
        List<Util.Point> neigh = grid.getNeighbors(point);
        for (Util.Point w : neigh) {
            char c = grid.G[w.x][w.y];
            if (grid.G[w.x][w.y] != '$' && node.childs.containsKey(c)) { // if unvisited and is a valid prefix
                // dfs
                grid.G[w.x][w.y] = '$'; // mark visited
                soFar.add(c);           // append char
                dfs(grid, trie, node.childs.get(c), soFar, words, w);
                grid.G[w.x][w.y] = c;   // revert visited
                soFar.pop();
            }
        }
    }

    static class Grid {
        char[][] G;
        int r,c;

        Grid(List<String> mat) {
            r = mat.size();
            c = mat.get(0).length();
            G = new char[r][c];

            // build code
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    G[i][j] = mat.get(i).charAt(j);
                }
            }
        }

        int[][] offsets = new int[][] {
                {0,-1},{0,1},{-1,0},{1,0},{-1,-1},{-1,1},{1,1},{1,-1}
        };

        List<Util.Point> getNeighbors(Util.Point point) {
            List<Util.Point> res = new ArrayList<>();
            int x = point.x, y = point.y;
            // return neighboring chars
            for (int[] offset:offsets) {
                int _x = x + offset[0], _y = y + offset[1];
                if (_x < 0 || _x >= r || _y < 0 || _y >= c) continue;
                res.add(new Util.Point(_x,_y));
            }
            return res;
        }

    }

    static class Trie {
        public TNode root = new TNode('#');

        Trie(List<String> dictionary) {  // build Trie
            for (String s : dictionary) {
                TNode n = root;
                for (char c : s.toCharArray()) {
                    if (!n.childs.containsKey(c)) n.childs.put(c, new TNode(c));
                    n = n.childs.get(c);
                }
                n.isWord = true;
            }
        }

        public void removeStr(String s) {
            remove(root, s, 0);
        }

        TNode remove(TNode n, String s, int depth) {
            if (n == null) return null;

            // if last char of str is being processed
            if (depth == s.length()) {
                // node is no more end of word
                n.isWord = false;
                // node is single, no other childs exist, delete it
                if (n.childs.isEmpty()) n = null;
                return n;
            }

            // if not last char, recursively delete
            n.childs.put(s.charAt(depth), remove(n.childs.get(s.charAt(depth)), s, depth+1));
            if (n.childs.get(s.charAt(depth)) == null && !n.isWord) n.childs.remove(s.charAt(depth));
            if (n.childs.isEmpty() && !n.isWord) n = null;
            return n;
        }

    }

    static class TNode {
        char ch;
        boolean isWord = false;
        Map<Character,TNode> childs = new HashMap<>();
        TNode(char c) { ch = c; }
    }

}
