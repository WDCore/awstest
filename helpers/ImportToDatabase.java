package helpers;

import java.io.File;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;

import entities.Issue;

public class ImportToDatabase {
	public static void main(String[] args) {
		XStream xstream = new XStream();
		xstream.setMode(XStream.NO_REFERENCES);
		
		ArrayList<Issue> listIssues = (ArrayList<Issue>) xstream.fromXML(new File("files/xml/issue.xml"));
		
		listIssues.stream().forEach(issue -> issue.setStatusIcon(issue.getStatusId()));
		
		listIssues.stream().sorted((Issue i1, Issue i2) -> { return Integer.compare(i2.getId(), i1.getId()); }).forEach(issue -> {
			Loggo.log(". ", issue.getId() + "", issue.getName());
			issue.insert();
		});
	}
}