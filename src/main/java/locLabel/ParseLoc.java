package locLabel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
@Deprecated
public class ParseLoc {
	private static Segment segment = HanLP.newSegment().enablePlaceRecognize(true);
	
	public static List<String> parse_CN(File file) throws IOException{
		List<String> locList=new LinkedList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line=null;
		while ((line = reader.readLine()) != null) {
			List<Term> termList = segment.seg(line);
			Iterator<Term> termIterator=termList.iterator();
			while(termIterator.hasNext()){
				Term curTerm=termIterator.next();
				if(curTerm.nature.equals(Nature.ns)||curTerm.nature.equals(Nature.nsf)){
					if(!curTerm.word.equals("")&&(curTerm.word.endsWith("省")||curTerm.word.endsWith("市")||curTerm.word.endsWith("区")||curTerm.word.endsWith("县")))
						locList.add(curTerm.word.substring(0, curTerm.word.length()-1));
					else
						locList.add(curTerm.word);
				}
			}
		}
		reader.close();
		return locList;
	}
	
	
}
