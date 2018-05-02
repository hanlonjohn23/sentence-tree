/* Class stores a sorted list of integers in ascending order. It may contain duplicate values. The class has an
 * instance variable called head of type Node, which is used as the begin point for the list.
 */

package project3;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class TextTree implements Iterable<String>{
    private WordNode head = new WordNode();

    /**
     * Splits the sentence up by space characters into N words. These words are then inserted into the tree consisting
     * of potentially existing nodes and newly created nodes. That is, this method will match a node’s value at
     * level 1 to word 0, then match a child of the existing node to word 1, etc. until no match is found. At this
     * point, new nodes are created as a descendant path for each following word in the sentence. Note that a word in
     * this case consists of any characters other than space and may include symbols
     * @param sentence sentence to be added to the tree
     * @return true if sentence is added, false if sentence is already extant.
     */
    public boolean add(String sentence) {
        int wordIndex;
        String currentWord;
        WordNode tmp = head;
        WordNode nextWord = null;

        if (head.isLeaf()) {                              // Used only if tree is currently empty
            while (!sentence.isEmpty()) {
                wordIndex = sentence.indexOf(" ");        // wordIndex is equal to index of the space character
                if (wordIndex > 0) {
                    currentWord = sentence.substring(0, wordIndex - 1);
                    sentence = sentence.substring(wordIndex + 1);
                    nextWord = new WordNode(currentWord);
                    tmp.children.add(nextWord);
                    tmp = nextWord;
                } else if (wordIndex == -1) {
                    currentWord = sentence;
                    nextWord = new WordNode(currentWord);
                    tmp.children.add(nextWord);
                    return true;
                }

            }
        } else {                                            // Test tree for existing nodes
            while (!sentence.isEmpty()) {
                boolean duplicateWord = false;
                wordIndex = sentence.indexOf(" ");          // wordIndex is equal to index of the space character
                if (wordIndex > 0) {
                    currentWord = sentence.substring(0, wordIndex - 1);
                    sentence = sentence.substring(wordIndex + 1);
                    for (WordNode child : tmp.children) {
                        if (currentWord.equals(child.word)) {
                            duplicateWord = true;
                            nextWord = child;
                            break;
                        }
                    }
                    if (!duplicateWord) {
                        nextWord = new WordNode(currentWord);
                        tmp.children.add(nextWord);
                    }
                    tmp = nextWord;
                } else if (wordIndex == -1) {
                    currentWord = sentence;
                    for (WordNode child : tmp.children) {
                        if (currentWord.equals(child.word)) {
                            return false;
                        }
                    }
                    nextWord = new WordNode(currentWord);
                    tmp.children.add(nextWord);
                    return true;
                }
            }
        }
        return false;                        //added since IJ flagging as an error. This false should never be returned.
    }

    /**
     * Checks if a the given sentence already exists in the list
     * @param sentence String checked
     * @return true if sentence exists, false otherwise
     */
   public boolean contains(String sentence) {
       int wordIndex;
       String currentWord;
       WordNode tmp = head;
       boolean wordMatch;

       if (head.isLeaf()) {
           return false;
       } else {                                            // Test tree for existing nodes
          while (!sentence.isEmpty()) {
              wordIndex = sentence.indexOf(" ");          // wordIndex is equal to index of the space character
              wordMatch = false;
              if (wordIndex > 0) {
                  currentWord = sentence.substring(0, wordIndex - 1);
                  sentence = sentence.substring(wordIndex + 1);
                  for (WordNode child : tmp.children) {
                      if (currentWord.equals(child.word)) {
                          wordMatch = true;
                          tmp = child;
                          break;
                      }
                  }
                  if (!wordMatch) {
                      return false;
                  }
              } else if (wordIndex == -1) {
                  currentWord = sentence;
                  for (WordNode child : tmp.children) {
                      if (currentWord.equals(child.word)) {
                          return true;
                      }
                  }
                  return false;
              }
           }
       }
       return false;                         //added since IJ flagging as an error. This false should never be returned.
   }


    /**
     * Gets the height of the tree
     * @return The height of the tree as an int value
     */
    public int height(){
        int height = 0;
        Queue<WordNode> currentLevel = new LinkedList<>();
        Queue<WordNode> nextLevel = new LinkedList<>();
        WordNode tmp;

        currentLevel.add(this.head);

        while(currentLevel.peek() != null){	            //Uses a queue per level to ensure that all paths are explored
            tmp = currentLevel.remove();
            if(!tmp.isLeaf()){
                for(WordNode word : tmp.children){
                    nextLevel.add(word);
                }
            }
            if(currentLevel.peek() == null){
                currentLevel = nextLevel;
                nextLevel = new LinkedList<>();
                if (currentLevel.peek() != null){
                    height++;
                }
            }
        }

        return height;
    }

    /**
     * Gets the size of the tree
     * @return The number of nodes in the current tree
     */
    public int size(){

        int size = 0;
        Queue<WordNode> processingQueue = new LinkedList<>();
        WordNode tmp;

        processingQueue.add(this.head);

        while(processingQueue.peek() != null){
            tmp = processingQueue.remove();
            size++;

            if(!tmp.isLeaf()){
                for(WordNode word : tmp.children){
                    processingQueue.add(word);
                }
            }
        }

        return size;

    }

    /**
     * Creates a iterator for all the leaf nodes in this tree
     * @return Total integers in this list
     */
    @Override
    public Iterator<String> iterator(){
       return new LeafIterator(this.head);
    }


}