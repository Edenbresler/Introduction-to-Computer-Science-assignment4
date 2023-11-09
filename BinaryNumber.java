
/*
I, <Eden Bresler> (<209424589>), assert that the work I submitted is entirely my own.
I have not received any part from any other student in the class,
nor did I give parts of it for use to others.
I realize that if my work is found to contain code that is not originally my own,
 a formal case will be opened against me with the BGU disciplinary committee.
*/

import java.util.Iterator;


public class BinaryNumber implements Comparable<BinaryNumber>{

    private static final BinaryNumber ZERO = new BinaryNumber(0);
    private static final BinaryNumber ONE  = new BinaryNumber(1);
    private BitList bits;

    // Copy constructor
    //Do not chainge this constructor
    public BinaryNumber(BinaryNumber number) {
        bits = new BitList(number.bits);
    }

    //Do not chainge this constructor
    private BinaryNumber(int i) {
        bits = new BitList();
        bits.addFirst(Bit.ZERO);
        if (i == 1)
            bits.addFirst(Bit.ONE);
        else if (i != 0)
            throw new IllegalArgumentException("This Constructor may only get either zero or one.");
    }

    //Do not chainge this method
    public int length() {
        return bits.size();
    }

    //Do not change this method
    public boolean isLegal() {
        return bits.isNumber() & bits.isReduced();
    }

    //=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.1 ================================================
    public BinaryNumber(char c) {
    	if((c<'0' | c > '9')) {
    		throw new IllegalArgumentException("The input is illegal");
    	}
    	int cValue = c- '0';
    	bits = new BitList();
    	while (cValue > 0) {
    		int digit = cValue%2 ;
    		bits.addLast(new Bit(digit));
    		cValue= cValue / 2;
    	}
    	bits.addLast(Bit.ZERO);
    }

  //=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.2 ================================================
    public String toString() {
        // Do not remove or change the next two lines
        if (!isLegal()) // Do not change this line
            throw new RuntimeException("I am illegal.");// Do not change this line
        //
    	String theString = "";
    	Iterator cha= bits.iterator();
    	while (cha.hasNext()) {
    		theString = cha.next().toString()+ theString;
    	}
    	return theString;
    }
    

    //=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.3 ================================================
    public boolean equals(Object other) {
    	boolean isEqual= true;
    	if (other instanceof BinaryNumber == false) {
    		isEqual = false;
    	}
    	else {
    	int thisSize = this.bits.size();
    	BinaryNumber binOther = new BinaryNumber((BinaryNumber)other);
    	int otherSize = binOther.bits.size();
    	if(this.bits.size() != otherSize){
    		isEqual = false;
    	}
    	else {
    		Iterator iterat= bits.iterator();
    		Iterator iters= ((BinaryNumber)other).bits.iterator();
    		while (iterat.hasNext()) {
    			if((iterat.next()).equals((iters).next())==false) {
    				isEqual = false;
    			}
    		}
    	}
    	}
    	return isEqual;
    }

    //=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.4 ================================================
    public BinaryNumber add(BinaryNumber addMe) {
    	if (!(addMe.isLegal())) {
    		throw new IllegalArgumentException("The input is illegal");
    	}
    	BinaryNumber theNewOne = new BinaryNumber(0);
    	BinaryNumber newAddMe =new BinaryNumber(addMe);
    	BinaryNumber newBits = new BinaryNumber(this);
    	Bit sighOfThis = this.bits.getLast();
    	Bit sighOfOther =addMe.bits.getLast();
    	
    	if(newBits.length()>newAddMe.length()) {
    		newAddMe.bits.padding(newBits.length());
    	}
    	if(newBits.length()<newAddMe.length()) {
    		newBits.bits.padding(newAddMe.length());
    	}
 		Iterator iterat= newBits.bits.iterator();
		Iterator iterato= newAddMe.bits.iterator();
		Bit c = Bit.ZERO;
		while (iterat.hasNext()) {
			Bit a = (Bit)iterat.next();
			Bit b = (Bit)iterato.next();
	   		Bit theSum = Bit.fullAdderSum(a, b,c);
	   		theNewOne.bits.addLast(theSum);
	   		c = Bit.fullAdderCarry(a, b,c);
		}
		if(c.equals(Bit.ONE) & sighOfThis.equals(sighOfOther) ){
			theNewOne.bits.addLast(c);
		}
		theNewOne.bits.removeFirst();
		if (sighOfThis.equals(sighOfOther)& (sighOfThis.equals(theNewOne.bits.getLast())==false) ) {
			theNewOne.bits.addLast(sighOfThis);
		}
		theNewOne.bits.reduce();
		return theNewOne;
    }

    //=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.5 ================================================
    public BinaryNumber negate() {
    	BinaryNumber theNegateOne = new BinaryNumber('0');
    	Iterator iterat= this.bits.iterator();
    	while (iterat.hasNext()) {
    		Bit nextBit = (Bit)iterat.next();
    		theNegateOne.bits.addLast(nextBit.negate());
    	}
		if(theNegateOne.bits.isEmpty() == false) {
			theNegateOne.bits.removeFirst(); 
			theNegateOne= theNegateOne.add(BinaryNumber.ONE);
		}
		if(theNegateOne.bits.isReduced()== false) {
			theNegateOne.bits.reduce();
		}
    	return theNegateOne;
    	
    }

    //=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.6 ================================================
    public BinaryNumber subtract(BinaryNumber subtractMe) {
    	if (!(subtractMe.isLegal())) {
    		throw new IllegalArgumentException("The input is illegal");
    	}
    	BinaryNumber theNagOfSubtractMe = subtractMe.negate();
    	BinaryNumber subtraction = this.add(theNagOfSubtractMe);
    	return subtraction;
    }

    //=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.7 ================================================
    public int signum() {
    	int signum;
    	BinaryNumber zero = new BinaryNumber('0');
    	Bit sighOfThis = this.bits.getLast();
    	if(this.equals(zero)) {
    		signum =0;
    	}
    	else if(sighOfThis.equals((Bit)Bit.ONE)) {
    		signum = -1;
    	}
    	else {
    		signum = 1;
    	}
    	return signum;
    }

    //=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.8 ================================================
    public int compareTo(BinaryNumber other) {
    	if (!(other.isLegal())) {
    		throw new IllegalArgumentException("The input is illegal");
    	}
    	BinaryNumber subtraction = this.subtract(other);
    	return subtraction.signum() ;
    		
    	
    		
    }

    //=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.9 ================================================
    public int toInt() {
        // Do not remove or change the next two lines
        if (!isLegal()) // Do not change this line
            throw new RuntimeException("I am illegal.");// Do not change this line
        //
		if(this.length() >32) {
			throw new RuntimeException("The Number is too match big");
		}
        int mult;
        int intNumb=1;
        int sum =0;
        Iterator iterat= this.bits.iterator();
        if(this.bits.getLast().equals(Bit.ZERO)) {
        	int index = 0;
        	while (iterat.hasNext()) {
        		if(index !=0) {
        			intNumb=1;
        			for(int theIndex = index; theIndex > 0;theIndex =theIndex -1 ) {
        				intNumb=intNumb*2;
        			}
        			if(iterat.next().equals(Bit.ONE)) {
        				mult = 1;
        			}
        			else {
        				mult = 0;
        			}
        			intNumb=intNumb * mult;
        			sum =sum + intNumb;
        			index= index+1;
        		}
        		else {
        			iterat.next();
        			index= index+1;
        		}
        		
        	}
    		if(this.bits.getFirst().equals(Bit.ONE)) 
    			sum =sum +1;
        }
        else {
        	BinaryNumber one = new BinaryNumber('1');
        	BinaryNumber lessOne = new BinaryNumber(this.subtract(one));
        	lessOne.bits.reduce();
        	BinaryNumber theNegateOne = new BinaryNumber('0');
        	theNegateOne.bits.removeFirst();
        	Iterator iterator= lessOne.bits.iterator();
        	while (iterator.hasNext()) {
        		Bit currBit = (Bit)iterator.next();
        		Bit theNegNum =currBit.negate();
        		theNegateOne.bits.addLast(theNegNum);
        	}   		
    		if(theNegateOne.bits.isReduced()== false) {
    			theNegateOne.bits.reduce();
    		}
    		
    		sum = theNegateOne.toInt();
    		sum = sum*-1;
        }
        return sum;
    }

    //=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.10 ================================================
    // Do not change this method
    public BinaryNumber multiply(BinaryNumber multiplyMe) {
    	if (!(multiplyMe.isLegal())) {
    		throw new IllegalArgumentException("The input is illegal");
    	}
    	BinaryNumber ans= new BinaryNumber('0');
    	BinaryNumber thisNeg =this.negate();
    	BinaryNumber meNeg = multiplyMe.negate();
    	if(this.bits.getLast().equals(Bit.ONE) & multiplyMe.bits.getLast().equals(Bit.ONE)) {
    		ans=thisNeg.multiplyPositive(meNeg);
    	}
    	else if(this.bits.getLast().equals(Bit.ONE) ) {
    		ans = thisNeg.multiplyPositive(multiplyMe);
    		ans.bits.reduce();
    		ans = ans.negate();
    	}
    	else if(multiplyMe.bits.getLast().equals(Bit.ONE)) {
    		ans =this.multiplyPositive(meNeg);
    		ans.bits.reduce();
    		ans = ans.negate();
    	}
    	else {
    		ans = this.multiplyPositive(multiplyMe);
    	}
    	ans.bits.removeFirst();
    	ans.bits.reduce();
    	return ans;
    }

    private BinaryNumber multiplyPositive(BinaryNumber multiplyMe) {
    	if (!(multiplyMe.isLegal())) {
    		throw new IllegalArgumentException("The input is illegal");
    	}
    	BinaryNumber zero = new BinaryNumber('0');
    	BinaryNumber sum = new BinaryNumber(0);
    	if(multiplyMe.equals(zero) | this.equals(zero)) {
    		return zero;
    	}
    	BinaryNumber multiThis = new BinaryNumber(this);
    	BitList multiZero = zero.bits;
    	Iterator iter= multiplyMe.bits.iterator();
    	while (iter.hasNext()) {
    		if(((Bit)iter.next()).equals(Bit.ZERO)) {
    			multiThis.bits.addFirst(Bit.ZERO);
    			
    		}
    		else {
    			multiThis.bits.addFirst(Bit.ZERO);
    			sum = sum.add(multiThis);
    		}
    	}
    	sum.bits.reduce();
    	return sum;
    }

    //=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.11 ================================================
    // Do not change this method
    public BinaryNumber divide(BinaryNumber divisor) {
    	// Do not remove or change the next two lines
    	if (divisor.equals(ZERO)) // Do not change this line
            throw new RuntimeException("Cannot divide by zero."); // Do not change this line
    	//
    	if (!(divisor.isLegal())) {
    		throw new IllegalArgumentException("The input is illegal");
    	}
    	BinaryNumber ans= new BinaryNumber('0');
    	BinaryNumber thisNeg =this.negate();
    	BinaryNumber meNeg = divisor.negate();
    	if(this.bits.getLast().equals(Bit.ONE) & divisor.bits.getLast().equals(Bit.ONE)) {
    		ans=thisNeg.dividePositive(meNeg);
    	}
    	else if(this.bits.getLast().equals(Bit.ONE) ) {
    		ans = thisNeg.dividePositive(divisor);
    		ans.bits.reduce();
    		ans = ans.negate();
    	}
    	else if(divisor.bits.getLast().equals(Bit.ONE)) {
    		ans =this.dividePositive(meNeg);
    		ans.bits.reduce();
    		ans = ans.negate();
    	}
    	else {
    		ans = this.dividePositive(divisor);
    	}
    	ans.bits.reduce();
    	return ans;
    		
    	

    }

    private BinaryNumber dividePositive(BinaryNumber divisor) {
    	BinaryNumber div = new BinaryNumber(divisor);
    	BinaryNumber zero = new BinaryNumber(0);
    	BinaryNumber ans = new BinaryNumber('1');
    	if( this.compareTo(divisor)<0 |this.equals(ZERO)) {
    		zero =zero;
    	}
    	else if(this.compareTo(divisor)==0) {
    		zero =zero.add(ONE);
    	}
    	else {
    		while(this.compareTo(divisor)>=0 ) {
    		divisor =divisor.multiplyBy2();
    		ans=ans.multiplyBy2();
    		}
    		divisor=divisor.divideBy2();
    		ans=ans.divideBy2();
    		ans = ans.add((this.subtract(divisor)).divide(div));
    		
    	}
    	return ans;	
    }

    //=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.12 ================================================
    public BinaryNumber(String s) {
    	BitList bitsAnswer = new BitList();
    	BinaryNumber answer = new BinaryNumber('0');
    	BinaryNumber one = new BinaryNumber('1');
    	BinaryNumber nine = new BinaryNumber('9');
    	BinaryNumber power = new BinaryNumber(one.add(nine));
    	BinaryNumber add = new BinaryNumber('1');

    	boolean negative= false;
    	if(s.charAt(0)== '-') {
    		negative= true;
    		s=s.substring(1);
    	}
    	
    	int length = s.length();
    	for(int i=length-1; i>= 0; i=i-1) {
    		BinaryNumber currNum = new BinaryNumber(s.charAt(i));
    		for(i=i; i>0 ; i=i-1) {
    			add=one.multiply(power);
    		}
    		add=add.multiply(currNum);

    		answer=answer.add(add);
    	}
    	if(negative) {
    		answer=answer.negate();
    	}
    	this.bits = answer.bits;

    }

    //=========================== Intro2CS 2021, ASSIGNMENT 4, TASK 3.13 ================================================
    public String toIntString() {
        // Do not remove or change the next two lines
        if (!isLegal()) // Do not change this line
            throw new RuntimeException("I am illegal.");// Do not change this line
        //
        throw new UnsupportedOperationException("Delete this line and implement the method.");
    }


    // Returns this * 2
    public BinaryNumber multiplyBy2() {
        BinaryNumber output = new BinaryNumber(this);
        output.bits.shiftLeft();
        output.bits.reduce();
        return output;
    }

    // Returens this / 2;
    public BinaryNumber divideBy2() {
        BinaryNumber output = new BinaryNumber(this);
        if (!equals(ZERO)) {
            if (signum() == -1) {
                output.negate();
                output.bits.shiftRight();
                output.negate();
            } else output.bits.shiftRight();
        }
        return output;
    }

}
