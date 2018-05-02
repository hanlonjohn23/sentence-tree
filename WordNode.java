/* Elementary class that is used in the operation of the SortedList class.
 * Node contains two fields: value, which stores a primitive int value, and next, which recursively points to a
 * different Node.
 */

package project3;

import java.util.LinkedList;

public class WordNode {
    String word;

    LinkedList<WordNode> children = new LinkedList<>();

    public boolean isLeaf(){
        return (this.children.isEmpty());
    }

    public WordNode(){
        word = "";
     }

     public WordNode(String word){
        this.word = word;
     }
}
