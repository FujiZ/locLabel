package test;

import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

public class TestHanlp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(HanLP.segment("你好，欢迎使用HanLP汉语处理包！"));
		Segment segment = HanLP.newSegment().enablePlaceRecognize(true);
		List<Term> termList = segment.seg("你好，欢迎使用HanLP汉语处理包！中国上海市宝山区");
		for(Term term:termList){
			if(term.nature.equals(Nature.ns)||term.nature.equals(Nature.nsf))
			System.out.println(term.nature.toString()+" "+term.word);
		}
	}

}
