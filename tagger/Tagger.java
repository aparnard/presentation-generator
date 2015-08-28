package tagger;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class Tagger
{
MaxentTagger mtagger;

public Tagger() throws Exception
{
mtagger= new MaxentTagger("models/wsj-0-18-left3words.tagger");
}

public String callTag( String sentence) // call from loop of sentences
{
return mtagger.tagString(sentence);
}
}



