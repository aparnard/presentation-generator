package sentsplit;
import sptoolkit.*;
import java.io.*;
import java.util.*;

public class SplitSentence
{
public ArrayList<String> sentSplit(String para) // each para is a string input. ie 1 para
{
SentParDetector s= new SentParDetector();
String res= s.markupRawText(2,para); // splits the paragraph into seperate sentences. Each sentence is seperated by a "\n" now // MARK UP RAW TEXT
String[] a = res.split("\n");// the string is split at "\n" and each element is stored as a seperate array element
List<String> l = Arrays.<String>asList(a); // these lines convert string array to Arraylist
ArrayList<String> al = new ArrayList<String>(l);
ArrayList<String> result= strip(al); // removes all punctuation marks
return result;
}

public ArrayList<String> strip(ArrayList<String> al) // function to remove all punctuation marks
{

for(int i=0;i<al.size();i++) {
String a=al.get(i);
String b=a.replaceAll("([a-z]+)[?:!.,;]*", "$1");
al.set(i,b);
}
return al;
}
}

