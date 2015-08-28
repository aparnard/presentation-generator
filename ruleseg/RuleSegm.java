package ruleseg;

//import com.aliasi.tokenizer.*;
import java.util.*;
import java.io.*;

public class RuleSegm extends ArrayList<String>
{
private ArrayList<String> words;
private ArrayList<String> tags;
private ArrayList<String> nouns;
private ArrayList<String> example;
private ArrayList<String> stopwords;
private int len,defnflag;

public RuleSegm()
{
words= new ArrayList<String>();
tags= new ArrayList<String>();
nouns= new ArrayList<String>();
example= new ArrayList<String>();
example.add(0,"\\Empty\\");
defnflag=0;
}

public String getBullet(String sent, ArrayList<String> n)
{
split(sent); // convert tagged sentence to array of words and tags
len=tags.size();
chkEg(); // check if an example is given in the the sentence.. if it is remove it nd store it in example arraylist

populateNouns(n); // store nouns of the current para in an array
chkDefn(); // check if something is defined
if (words.size()>10) sentBreaker(); // if sentence longer than 10 words.. break it
removeNoun(); //remove nouns from the beginning of the sentences ( these nouns have already been introduced in prev sentences)
setNouns(); // add new nouns introduced in this sentence to the noun array.
removeStop(); // remove stop words from beginning of sentences
addEg(); // add example as a subpoint

return toString(words);
}

public void split(String sent)
{
String[] tsplit=sent.split("[\\_\\ ]");
      for (int k=0;k<tsplit.length;k++) 
        {
		if(k%2==0)	words.add(tsplit[k].toLowerCase());
      	else	tags.add(tsplit[k]);
        }
}


public void chkEg()
{ for(int i=0;i<words.size()-1;i++) {
if( words.get(i).equalsIgnoreCase("for") && words.get(i+1).equalsIgnoreCase("example")) {
removePh(i,words.size()); 
break;
}
}
}

public void removePh(int start,int end)
{
int k=0;
example.remove(0);
for(int i=start;i<end;i++)
example.add(k++,words.get(i));
for(int j=start;j<end;j++){
words.remove(start);
tags.remove(start);
}}

private void populateNouns(ArrayList<String> n)
{
for(int i=0;i<n.size();i++)
nouns.add(i,n.get(i));
}


public void setNouns(){
int nouncount=nouns.size();
int flag=1;

for(int i=0;i<words.size();i++)
{
String cmp=tags.get(i);
if(cmp.equals("NN") || cmp.equals("NNS") || cmp.equals("NNP") || cmp.equals("NNPS"))
{
if(!isList(i)){
for(int j=0;j<nouncount;j++)
{
if(nouns.get(j).equalsIgnoreCase(words.get(i))) flag=200;}
if (flag!=200) nouns.add(nouncount++,words.get(i));
}}}}



private boolean isList(int i)
{
int size= words.size() -1;
if(size>=i+2) {
if (tags.get(i+1).equals("NN")||tags.get(i+1).equals("NNS")||tags.get(i+1).equals("NNP")||tags.get(i+1).equals("NNPS"))
{
if(tags.get(i+2).equals("NN")||tags.get(i+2).equals("NNS")||tags.get(i+2).equals("NNP")||tags.get(i+2).equals("NNPS"))
return true;
}}
return false;
}

public ArrayList<String> getNouns()
{
return nouns;
}

public void chkDefn()
{
int i,j,k,l;
l=words.size()-1;
for(i=0;i<words.size();i++) {
if(l<=i+1) break;
else if (l<=i+2) break;
else {
j=i+1;
k=j+1;
if ( !tags.get(i).equals("DT")){
if ( tags.get(j).equals("NN") || tags.get(j).equals("NNS")) {
if (tags.get(k).equals("VBZ") || tags.get(k).equals("VBP")) {
if(!nouns.contains(words.get(j))){
tags.set(k,":");
words.set(k,":");
defnflag=100;

//}
}}}}}}}

private void removeStop(){
while(tags.get(0).equals("DT") || tags.get(0).equals("IN") || tags.get(0).equals("RB") || tags.get(0).equals("PRP") ){ // check tags
tags.remove(0);
words.remove(0); }
while(words.get(0).equalsIgnoreCase("is") || words.get(0).equalsIgnoreCase("are")) {
tags.remove(0);
words.remove(0);
}}

private void removeNoun() 
{
if(defnflag!=100) {
while(nouns.contains(words.get(0).toLowerCase()))
{words.remove(0);
tags.remove(0);
}}}

public void addEg()
{
if(!example.get(0).equals("\\Empty\\"))
{
words.add(words.size(),"\r\t\t");
words.addAll(words.size(),example);
}
}
private void sentBreaker()
{
int ind;
if(defnflag!=100) {
if(words.contains("and")) {
ind=words.indexOf("and");
if(tags.get(ind+1).equals("VB") || tags.get(ind+1).equals("VBZ") || tags.get(ind+1).equals("VBP")){
words.set(ind,"\r\t\t");
tags.set(ind,"\r\t\t");}
}
else if( words.contains("such"))
{
ind=words.indexOf("such");
if(words.get(ind+1).equals("as"))
words.add(ind,"\r\t\t");
tags.add(ind,"\r\t\t");
}
}

if(defnflag==100) {
if(words.indexOf(":") != words.lastIndexOf(":")) {
ind=words.lastIndexOf(":");
words.set(ind,"\r\t\t");
tags.set(ind,"\r\t\t");
}
else if( words.contains("such"))
{
ind=words.indexOf("such");
if(words.get(ind+1).equals("as"))
words.add(ind,"\r\t\t");
tags.add(ind,"\r\t\t");
}
}

}

public String toString(ArrayList<String> st)
{
int len=st.size();
String result="";
for(int i=0;i<len;i++)
result = result+" " + st.get(i);
result= result.trim();
String fres=result.substring(0, 1).toUpperCase() + result.substring(1);
return fres;
}

}