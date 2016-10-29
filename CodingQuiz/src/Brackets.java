import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Brackets {
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		BufferedReader br = null;
		List<String> lstInput = new ArrayList<String>();
		try
		{
			InputStreamReader ir = new InputStreamReader(System.in);
			br = new BufferedReader(ir);
			
			String str = br.readLine();
			int testCount = Integer.parseInt(str);
			if(testCount > 100) return;
			
			int inputCount = 0;	
			
			while(str != null && testCount > inputCount)
			{						
				str = br.readLine().trim();
				
				if(str.length() > 10000)
				{
					str = str.substring(0, 9999);
				}
				lstInput.add(str);
				inputCount++;
			}
		}		
		finally{
			if(br !=null) br.close();
		}	
		Brackets bk = new Brackets();		
		
		for(String input : lstInput)
		{
			Stack<Character> factorStack = new Stack<Character>();		
			
			boolean isNotMatched = false;		
			String strCalc = "";
			for(int i=0; i < input.length(); i++)
			{
				char ch = input.charAt(i);
				if(ch == '(' || ch == '{' || ch == '[')
				{					
					factorStack.push(ch);
					int nextCharIdx = i + 1;
					if(nextCharIdx < input.length())
					{
						char nextChar = input.charAt(nextCharIdx);
						if(nextChar == '(' || nextChar == '{' || nextChar == '[')
						{
							String inut = Character.toString(ch)+Character.toString(bk.getPairChar(ch));
							int calcFactor = bk.getCalcValue(inut);
							
							if(strCalc.length() > 0)
							{
								String lastStr = Character.toString(strCalc.charAt(strCalc.length()-1));
								if(Pattern.matches("[1-3|)]", lastStr)) strCalc += "+";	
							}
							
							strCalc += Integer.toString(calcFactor) + "*(";													
						}
					}
				}
				else if(ch == ')' || ch == '}' || ch == ']')
				{					
					if(factorStack.size() == 0) 
					{
						isNotMatched = true;
						break;
					}
					
					char popChar = factorStack.pop();
					if(popChar == bk.getPairChar(ch))
					{
						String inut = Character.toString(popChar)+Character.toString(ch);
						int calcFactor = bk.getCalcValue(inut);
						
						int prevCharIdx = i - 1;
						char prevChar = input.charAt(prevCharIdx);
						
						if(prevChar == bk.getPairChar(ch)) 
						{		
							if(strCalc.length() > 0)
							{
								String lastStr = Character.toString(strCalc.charAt(strCalc.length()-1));
								if(Pattern.matches("[1-3|)]", lastStr)) strCalc += "+";	
							}
													
							strCalc += Integer.toString(calcFactor);							
						}
						else
						{
							strCalc += ")";							
						}
					}
					else
					{
						isNotMatched = true;
						break;
					}								
				}
			}
			
			if(isNotMatched) System.out.println(0);
			else
			{				
				try{
					Queue<String> postFixQueue = bk.getPostFix(strCalc);
					long calcVal = bk.calcPostfix(postFixQueue);
					System.out.println(calcVal);
				}
				catch(Exception e)
				{
					System.out.println(0);
				}			
			}		
		}
	}
	
	private Map<String, Integer> hashMap = new HashMap<String, Integer>();
	
	public Brackets()
	{
		hashMap.put("+",0);
		hashMap.put("*", 1);
		hashMap.put("(", -1);
	}
	
	private Queue<String> getPostFix(String infix)
	{		
		Stack<String> stack = new Stack<String>();
		Queue<String> postfixQueue = new LinkedList<String>();
		
		Pattern p = Pattern.compile("[0-3]+|\\(|\\)|\\+|\\*");
		Matcher m = p.matcher(infix);
		
		while(m.find())
		{
			String word = m.group();
			if(word.equals("(")) stack.push(word);
			
			else if(hashMap.containsKey(word))
			{
				while(true)
				{
					if(stack.isEmpty() || hashMap.get(stack.peek()) < hashMap.get(word))
					{
						stack.push(word);
						break;
					}
					else
					{
						String popStr = stack.pop();						
						postfixQueue.add(popStr);
					}
				}
			}
			else if(word.equals(")"))
			{
				while(true)
				{
					String popStr = stack.pop();
					if(popStr.equals("(")) break;					
					else postfixQueue.add(popStr);					
				}
			}
			else
			{
				postfixQueue.add(word);
			}
		}
		
		while(!stack.isEmpty())
		{
			String popStr = stack.pop();			
			postfixQueue.add(popStr);
		}
		
		return postfixQueue;
	}
	
	private long calcPostfix(Queue<String> postfixQueue)
	{
		Stack<Long> stack = new Stack<Long>();
		
		long firstVal;
		long secVal;
		
		while(!postfixQueue.isEmpty())
		{
			String word = postfixQueue.remove();
			
			if(hashMap.containsKey(word))
			{
				secVal =stack.pop();
				firstVal = stack.pop();
				
				long val = 0;
				switch(word.charAt(0))
				{
					case '+' :
						val = firstVal + secVal;
						stack.push(val%100000000);
						break;
					case '*' :
						val = firstVal * secVal;
						stack.push(val%100000000);
						break;
				}
			}
			else
			{
				stack.push(Long.valueOf(word));
			}
		}
		
		return stack.pop();
	}
	
	private char getPairChar(char input)
	{
		char out = ' ';
		if(input == ')') out = '(';
		else if(input == '}') out = '{';
		else if(input == ']') out = '[';
		else if(input == '(') out = ')';
		else if(input == '{') out = '}';
		else if(input == '[') out = ']';
		return out;		
	}
	
	private int getCalcValue(String input)
	{
		int out = 0;
		if(input.equals("()")) out = 1;
		else if(input.equals("{}")) out = 2;
		else if(input.equals("[]")) out = 3;
		return out;		
	}
	
	
	

}
