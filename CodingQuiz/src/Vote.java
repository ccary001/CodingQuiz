import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Vote {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = null;
		List<String> lstNames = new ArrayList<String>();
		try
		{
			InputStreamReader ir = new InputStreamReader(System.in);
			br = new BufferedReader(ir);
			
			String str = br.readLine();
			
			while(str != null)
			{
				String[] names = str.split("\\s");
				for(String name : names)
				{
					if(str.length() <= 50)
					{
						lstNames.add(name.trim());
					}
				}				
									
				str = br.readLine();
			}
		}		
		finally{
			if(br !=null) br.close();
		}		
		
		Collections.sort(lstNames);		
		List<String> lstAnswers = new ArrayList<String>();		
		
		int voteCount = 0;
		int maxVoteCount = 0;
		boolean isUpdateCheck = false;
	
		for(int i=0; i < lstNames.size(); i++)
		{			
			voteCount++;
			int nextIndex = i + 1;
			String currName = lstNames.get(i).toString();
			if(nextIndex < lstNames.size())
			{
				String nextName = lstNames.get(nextIndex).toString();
				if(!currName.equals(nextName))
				{				
					isUpdateCheck = true;
				}
			}
			else
			{
				isUpdateCheck = true;
			}	
			
			if(isUpdateCheck)
			{
				if(maxVoteCount <= voteCount)
				{
					if(maxVoteCount != voteCount)
					{
						lstAnswers.clear();
					}
					
					lstAnswers.add(currName);						
					maxVoteCount = voteCount;	
					
				}
				isUpdateCheck = false;
				voteCount = 0;
			}			
		}
		
		lstAnswers = lstAnswers.stream().distinct().collect(Collectors.toList());
		Collections.sort(lstAnswers);
		
		for(String name : lstAnswers)
		{
			System.out.println(name);
		}
	}
}
