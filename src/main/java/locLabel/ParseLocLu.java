package locLabel;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;









import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

@Deprecated
public class ParseLocLu {
	private static Directory directory;
	private static Analyzer analyzer;
	private static IndexWriterConfig iwc;
	private static IndexWriter writer;
	private static QueryParser parser;
	private static IndexReader reader;
	static{
		try {
			directory=new RAMDirectory();
			analyzer=new StandardAnalyzer(Version.LUCENE_43);
			iwc=new IndexWriterConfig(Version.LUCENE_43, analyzer);
			writer=new IndexWriter(directory,iwc);
			parser=new QueryParser(Version.LUCENE_43, "content", analyzer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//将文件加入索引中
	public static void addIndex(File file) throws IOException{
		System.out.println("filename:" + file.getName());
		Document document=new Document();
		document.add(new StringField("filename", file.getName(), Field.Store.YES));
		document.add(new TextField("content",new FileReader(file)));
		writer.addDocument(document);
	}
	
	public static Map<String, List<String>> search(List<String> searchList,int num) throws IOException, ParseException{
		if(reader==null){
			writer.close();
			reader=DirectoryReader.open(directory);
		}
		IndexSearcher searcher=new IndexSearcher(reader);
		
		Map<String, List<String>> resultMap=new HashMap<String, List<String>>();
		
		Iterator<String> strIterator=searchList.iterator();
		while(strIterator.hasNext()){
			String string=strIterator.next();
			Query query=parser.parse(string);
			TopDocs docs =searcher.search(query, num);
			if(docs.totalHits>0){
				System.out.println(string+": 一共搜索到结果:" + docs.totalHits + "条");
				for (ScoreDoc scoreDoc : docs.scoreDocs){
					Document document = searcher.doc(scoreDoc.doc);
					String filename=document.get("filename");
					
					List<String> locStrList;
					if((locStrList=resultMap.get(filename))==null){
						locStrList=new LinkedList<String>();
						resultMap.put(filename, locStrList);
					}
					locStrList.add(string);
				}
			}
		}
		return resultMap;
	}
	
}
