package project3;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class LeafIterator implements Iterator<String>{
    private Queue<WordNode> contents = new LinkedList<>();

    public LeafIterator(WordNode root){
        contents.add(root);
    }

    @Override
    public boolean hasNext(){
        return (contents.peek() != null);
    }

    @Override
    public String next(){
        WordNode tmp = contents.remove();
        while(!tmp.isLeaf()){
            for(WordNode word : tmp.children){
                contents.add(word);
            }
            tmp = contents.remove();
        }
        return tmp.word;
    }
}
