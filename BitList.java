
/*
I, <Eden Bresler> (<209424589>), assert that the work I submitted is entirely my own.
I have not received any part from any other student in the class,
nor did I give parts of it for use to others.
I realize that if my work is found to contain code that is not originally my own,
 a formal case will be opened against me with the BGU disciplinary committee.
*/

import java.util.LinkedList;
import java.util.Iterator;

public class BitList extends LinkedList<Bit> {

    private int numberOfOnes;

    // Do not change the constructor
    public BitList() {
        numberOfOnes = 0;
    }

    // Do not change the method
    public int getNumberOfOnes() {
        return numberOfOnes;
    }


//=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 2.1 ================================================

    public void addLast(Bit element) {
    	if(element ==null) {
    		throw new IllegalArgumentException("The input is illegal");
    	}
    	if(element.equals(Bit.ONE)) {
    		numberOfOnes=numberOfOnes +1;
    	}
    	super.addLast(element);
    }

    public void addFirst(Bit element) {
    	if(element ==null) {
    		throw new IllegalArgumentException("The input is illegal");
    	}
    	if(element.equals(Bit.ONE)) {
    		numberOfOnes=numberOfOnes +1;
    	}
        super.addFirst(element);
    }

    public Bit removeLast() {
    	Bit ans = super.removeLast();
    	if (ans.equals(Bit.ONE)) {
    		numberOfOnes=numberOfOnes -1;
    	}
    	return ans;
    }

    public Bit removeFirst() {
    	Bit ans = super.removeFirst();
    	if (ans.equals(Bit.ONE)) {
    		numberOfOnes=numberOfOnes -1;
    	}
    	return ans;
    }

    //=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 2.2 ================================================
    public String toString() {
    	String theString = ">";
    	Iterator<Bit> cha= super.iterator();
    	while (cha.hasNext()) {
    		theString = cha.next().toString()+ theString;
    	}
    	theString= "<" +theString;
    	return theString;
    }
    
    //=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 2.3 ================================================
    public BitList(BitList other) {
    	if(other ==null) {
    		throw new IllegalArgumentException("The input is illegal");
    	}
    	Iterator iter = other.iterator();
    	while (iter.hasNext()) {
    		this.addLast((Bit)iter.next());
    	}
    }

    //=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 2.4 ================================================
    public boolean isNumber() {
    	boolean ans= true;
    	if (this.isEmpty()||(numberOfOnes <= 1 & this.getLast()==(Bit.ONE))) {
    		ans = false;
    	}
    	return ans;
    	
    }
    
    //=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 2.5 ================================================
    public boolean isReduced() {
    	boolean ans= false;
    	if(this.isNumber()) {
    		if(this.size()<=2 & (this.toString().equals("<0>") | this.toString().equals("<01>") | this.toString().equals("<11>")) ){
    			ans =true;
    		}
    		else if(this.size()>= 3 && (this.toString().substring(0,3).equals("<10") |this.toString().substring(0,3).equals("<01") )){
    			ans =true;
    		}
    		else if(this.size()>= 3 && numberOfOnes == 2 & this.toString().substring(0,3).equals("<11") ) {
    			ans =true;
    		}
    		
    	}
    return ans;
    }

    public void reduce() {
    	while(this.isReduced()==false) {
    		this.removeLast();
    	}
        
    }

    //=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 2.6 ================================================
    public BitList complement() {
    	BitList compBitList = new BitList();
    	Iterator iterator = this.iterator();
    	while (iterator.hasNext()) {
    		if(iterator.next()== Bit.ZERO) {
    			compBitList.addLast(Bit.ONE);
    		}
    		else {
    			compBitList.addLast(Bit.ZERO);
    		}
    	}
    	return compBitList;
    }

    //=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 2.7 ================================================
    public Bit shiftRight() {
    	Bit theRightNum = null;
        if(! this.isEmpty()) {
        	theRightNum=this.removeFirst();
        }
        return theRightNum;
    }

    public void shiftLeft() {
    	this.addFirst(Bit.ZERO);
    }

    //=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 2.8 ================================================
    public void padding(int newLength) {
       int numOfBits = newLength - super.size();
       if (numOfBits >0 ){
    	   Bit bitToAdd = super.getLast();
    	   for (int i =0; i <numOfBits; i=i+1) {
    		   this.addLast(bitToAdd);
    	   }
    	   
       }
    }


    //----------------------------------------------------------------------------------------------------------
    // The following overriding methods must not be changed.
    //----------------------------------------------------------------------------------------------------------
    public boolean add(Bit e) {
        throw new UnsupportedOperationException("Do not use this method!");
    }

    public void add(int index, Bit element) {
        throw new UnsupportedOperationException("Do not use this method!");
    }

    public Bit remove(int index) {
        throw new UnsupportedOperationException("Do not use this method!");
    }

    public boolean offer(Bit e) {
        throw new UnsupportedOperationException("Do not use this method!");
    }

    public boolean offerFirst(Bit e) {
        throw new UnsupportedOperationException("Do not use this method!");
    }

    public boolean offerLast(Bit e) {
        throw new UnsupportedOperationException("Do not use this method!");
    }

    public Bit set(int index, Bit element) {
        throw new UnsupportedOperationException("Do not use this method!");
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Do not use this method!");
    }
}
