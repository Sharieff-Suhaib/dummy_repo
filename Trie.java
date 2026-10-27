import java.util.HashMap;
import java.util.Map;

public class BuggyTrie {
    private static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEnd = false;
    }

    private final TrieNode root = new TrieNode();

    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node.children.putIfAbsent(ch, new TrieNode());
            node = node.children.get(ch);
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            if (!node.children.containsKey(ch)) {
                return false;
            }
            node = node.children.get(ch);
        }
        return true; // BUG: should return node.isEnd
    }

    public static void main(String[] args) {
        BuggyTrie trie = new BuggyTrie();
        trie.insert("apple");

        System.out.println(trie.search("apple"));
        System.out.println(trie.search("app")); 
    }
}