import tagger.*;
import sentsplit.*;
import fimport.*;
import ruleseg.*;
import slidegen.*;
import titlext.*;
import java.io.*;
import java.util.*;

public class Tester {
public static void main(String[] args) throws Exception {

ImportFile imp = new ImportFile();
SplitSentence ss=new SplitSentence();
Tagger tag=new Tagger();
SlideGen ppt=new SlideGen();
PrintWriter tout=new PrintWriter("Output_TitleXtract.txt");
PrintWriter pout=new PrintWriter("Output_POST.txt");
PrintWriter sout=new PrintWriter("Output_SentSplit.txt");
PrintWriter rout=new PrintWriter("Output_RuleBased.txt");
ArrayList<String> sentlist=new ArrayList<String>();
// import file - summarization
imp.fimport(args[0]);
ArrayList<String> para=imp.getPara();
for(int i=0;i<para.size();i++) {
ArrayList<String> bullets= new ArrayList<String>();
ArrayList<String> nouns= new ArrayList<String>();
//Title Extraction
TitleXtract tx=new TitleXtract();
String title= tx.getTitle(para.get(i)).toUpperCase(Locale.ENGLISH);
tout.println("Extracted Title is :" +title);
// sentence extraction
sentlist=ss.sentSplit(para.get(i));
sout.println(sentlist);
int n= sentlist.size();
for(int j=0;j<n;j++) {
//POS tagging
String tagged=tag.callTag(sentlist.get(j));
pout.println(tagged);
// rule based segmentation
RuleSegm rbs=new RuleSegm();
String bullet = rbs.getBullet(tagged,nouns);
rout.println(bullet);
bullets.add(j,bullet);
nouns= new ArrayList<String>();
nouns.addAll(0,rbs.getNouns());
}

//slide generation
int r=bullets.size();
int p=0, mul=0;
while(r>6)
{
ArrayList<String> subbul=new ArrayList<String>();
for(int k=0;k<4;k++)
subbul.add(bullets.get(p+k));
ppt.newSlide(title,subbul);
p=p+5; //6 //12
r=n-5; //8 //2 0-11
mul+=1;
}
if(n>0)
{
ArrayList<String> subbul=new ArrayList<String>();
for(int q=mul*5;q< bullets.size();q++)
subbul.add(bullets.get(q));
ppt.newSlide(title,subbul);
}

}
ppt.savePres(args[1]);
System.out.println("Presentation Slides Successfully Created as : " + args[1] + ".ppt");
tout.close();
rout.close();
sout.close();
pout.close();
}
}