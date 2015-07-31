package locLabel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ParseLocRegex {
	public static List<String> parse(File file) throws IOException{
		List<String> locList=new LinkedList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line=null;
		while ((line = reader.readLine()) != null) {
			Iterator<String> locIterator=Loc2XML.locStrList.iterator();
			while(locIterator.hasNext()){
				String locString=locIterator.next();
				if(line.contains(locString))
					locList.add(locString);
			}
		}
		reader.close();
		return locList;
	}
}
