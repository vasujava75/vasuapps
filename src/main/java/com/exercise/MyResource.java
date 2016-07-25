package com.exercise;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt(@QueryParam("testUrl") String url) {
		String errorResponse = "error";
		UrlDetails details = new UrlDetails();
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (Exception exception) {
			if(exception instanceof HttpStatusException){
				HttpStatusException httpExcep = (HttpStatusException)exception;
				return "Status code="+httpExcep.getStatusCode();
			}
			return "Mall formed url. Please enter url with http://www.abc.com or https://www.xyz.com";
		}
		ObjectMapper mapper = null;
		if (doc != null) {
			Elements externallinks = doc.select("a[href]");
			Elements internalLinks = doc.select("link[href]");
			String title = doc.title();
			StringBuilder sb = new StringBuilder();
			findHeadingsOfThePage(doc, sb);
			mapper = new ObjectMapper();
			details.setTitle(title);
			details.setHeadings(sb.toString());
			details.setExternalLinks(externallinks.size());
			details.setInternalLinks(internalLinks.size());
			List<Node>nods = doc.childNodes();
	         for (Node node : nods) {
	            if (node instanceof DocumentType) {
	                DocumentType documentType = (DocumentType)node;
	                if("<!DOCTYPE html>".equalsIgnoreCase(documentType.toString())){
	                	details.setVersion("HTML 4.01 or HTML5");
	                }else{
	                	details.setVersion("Below Html 5");
	                }
	            }
	        }
		} else {
			return errorResponse;
		}
		try {

			return mapper.writeValueAsString(details);
		} catch (JsonProcessingException e) {
			return errorResponse;
		}
	}

	private void findHeadingsOfThePage(Document hTags, StringBuilder sb) {
		if (hTags.select("h1").size() > 0) {
			sb.append("h1 ");
		}
		if (hTags.select("h2").size() > 0) {
			sb.append("h2 ");
		}
		if (hTags.select("h3").size() > 0) {
			sb.append("h3 ");
		}
		if (hTags.select("h4").size() > 0) {
			sb.append("h4 ");
		}
		if (hTags.select("h5").size() > 0) {
			sb.append("h5 ");
		}
		if (hTags.select("h6").size() > 0) {
			sb.append("h6 ");
		}
	}
}
