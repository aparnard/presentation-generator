package fimport;
import java.io.*;
import java.util.*;
public class ImportFile
{
private ArrayList<String> paragraphs; 
public ImportFile() {
paragraphs = new ArrayList<String>();
}
public void fimport(String tfile) throws Exception // gets title from main program
{
Scanner r = new Scanner(new FileInputStream(tfile),"UTF-8");
String read;
while(r.hasNextLine()) // while there is a new line it will read each line ( ie 1 para) and add it to the paragraphs arraylist
{
read=r.nextLine();
paragraphs.add(read);
}
}
public ArrayList<String> getPara()
{
return paragraphs;
}
}