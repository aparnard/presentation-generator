package slidegen;
import java.io.*;
import java.awt.*;
import java.util.*;
import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.usermodel.*;
import org.apache.poi.hslf.model.TextBox;

public class SlideGen 
{
 private SlideShow ppt;
public SlideGen()
{
    ppt = new SlideShow();
	
}
private String stringConvert(ArrayList<String> blist) // converts Arraylist to string seperated by "\r
{ // each time "\r" comes in the string this class will put it in a new line with a bullet
StringBuilder sb = new StringBuilder();
for (String s : blist)
{
    sb.append(s);
    sb.append("\r");
}

return sb.toString();
}
public void newSlide(String title, ArrayList<String> bullets) // called whenever a new slide is to be created
{
  Slide s = ppt.createSlide();
  TextBox head = s.addTitle(); 
  head.setText(title);// add titile to slide
  String sbullet = stringConvert(bullets); // convert arraylist to string seperated by "\r"
  TextBox text = new TextBox(); 
  RichTextRun rt = text.getTextRun().getRichTextRuns()[0];
  text.setText(sbullet); // add bullet points
  rt.setFontSize(18); // format bullet points
  rt.setBullet(true);
  rt.setBulletOffset(0);  //bullet offset
  rt.setTextOffset(50);   //text offset (should be greater than bullet offset)
  rt.setBulletChar('\u263A'); //bullet character
  text.setAnchor(new java.awt.Rectangle(50, 200, 600, 550));  //position of the text box in the slide
  s.addShape(text);

}    
 
public void savePres(String title) throws Exception // called at the end of the program.. when file needs to be saved
{
String filename= title +".ppt";
    FileOutputStream out = new FileOutputStream(filename);
    ppt.write(out);
    out.close();
}
}
