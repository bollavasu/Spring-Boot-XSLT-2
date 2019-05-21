package com.journaldev.spring;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class PersonController {
	
	@RequestMapping("/")
	public String welcome() throws Exception {
		String fileContent = transform();
		return fileContent;
	}
	
	public String transform() throws Exception {
		ClassLoader classLoader = this.getClass().getClassLoader();
		Source             xslt        = new StreamSource(new File(classLoader.getResource("transform.xslt").getFile()));
		Source             text        = new StreamSource(new File(classLoader.getResource("input.xml").getFile()));
		TransformerFactory factory     = TransformerFactory.newInstance();
		Transformer        transformer = factory.newTransformer(xslt);
		transformer.transform(text, new StreamResult(new File("Output.html")));
		
		String content = "";
		try
		{
			content = new String ( Files.readAllBytes(Paths.get("Output.html") ) );
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	
		return content;
    }
	
}
