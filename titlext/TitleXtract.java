package
 titlext;
import java.util.*;
import java.io.*;


public class TitleXtract
{
private HashMap<String,Integer> words;
private ArrayList<String> stop;
private String input;

public TitleXtract() throws Exception
{
words=new HashMap<String,Integer>();
stop=new ArrayList<String>();
Scanner r = new Scanner(new FileInputStream("stopword.txt"),"UTF-8");
String read;
int i=0;
while(r.hasNextLine())
{
read=r.nextLine();
stop.add(i++,read);
}
}
public void strip()
{
String b=input.replaceAll("([a-z]+)[?:!.,;]*", "$1"); // WHAT DOES THIS DO?
input=b.toLowerCase();
}

public void upload()
{
StringTokenizer st = new StringTokenizer(input);
     while (st.hasMoreTokens()) {
	 String token = st.nextToken();
		if(!stop.contains(token))
         {
		 if(words.containsKey(token))
			{
			int val=words.get(token).intValue();
			val=val+ 1;
			words.put(token,val);
			}
			else
			{
			words.put(token,1);} 
		 }
     }
}

private String order()
{
List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(words.entrySet());
Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
    public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
        return e2.getValue().compareTo(e1.getValue()); 
    }
});
int i=0;
String title= " ";

for (Map.Entry<String, Integer> entry : entries) {
	i=i+1;
	title=title.concat(entry.getKey());
	title=title.concat(" ");
	if(i==3) break;
		}
	return title;
}
public String getTitle(String in)
{
input=in;
strip(); // remove punctuation
upload(); // add words and count frequency in hash map
return order(); // re order hash map using frequency. and return top 3 most frequent words
}
}
