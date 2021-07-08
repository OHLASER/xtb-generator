package sk.kuzmisin.xtbgenerator;

import com.google.common.io.Files;
import com.google.javascript.jscomp.JsMessage;
import com.google.javascript.jscomp.SourceFile;
import com.google.javascript.jscomp.XtbMessageBundle;
import org.junit.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;


import static org.junit.Assert.*;

public class XtbGeneratorTest {
    static InputStream loadStreamFromClass(String resourceName) {
        InputStream is = XtbGeneratorTest.class.getResourceAsStream(
            "/" + resourceName);

        byte[] buffer = new byte[1024];
        
        is.mark(Integer.MAX_VALUE);


        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            while (true) {
                int readSize = 0;
                readSize = is.read(buffer, 0, buffer.length);
                if (readSize > 0) {
                    os.write(buffer, 0, readSize);
                } else {
                    break;
                }
                
            }
        } catch (java.io.IOException ex) {
        }
        try {
            is.reset(); 
        } catch (java.io.IOException ex) {
        }
        return new ByteArrayInputStream(os.toByteArray());
    }

    static String getStringFromClass(String resourceName) {
        InputStream xtbStream = loadStreamFromClass(resourceName);
        InputStreamReader xtbReader = new InputStreamReader(xtbStream, 
            StandardCharsets.UTF_8); 
        
        char[] buffer = new char[1024];
        StringWriter sw = new StringWriter();
        try {
            while (true) {
                int readSize = xtbReader.read(buffer, 0, buffer.length);
                if (readSize != -1) {
                    sw.write(buffer, 0, readSize); 
                } else {
                    break;
                }
           }
           xtbStream.close();
        } catch (IOException ex) {
        }
        String result = sw.toString().trim();
        return result;

    }
    protected StringWriter output;

    /**
     * Mock for XtbGenerator
     */
    class MockXtbGenerator extends XtbGenerator {
        protected InputStream getTranslationFileInputStream() {
            if (this.translationFile == null) {
                return null;
            }
            InputStream result = getMessagesXtbStream();
            return result;
        }


        private InputStream getMessagesXtbStream() {
            return loadStreamFromClass("messages.xtb");
        }

        String getMessagesContents() {
            return getStringFromClass("messages.xtb");
        }


        protected Writer getOutputWriter() {
            output = new StringWriter();
            return output;
        }
    }

    /**
     * Test loading JS messages from JS file into Map<Id, JsMessage>
     */
    @Test
    public void testGetMessagesFromJs() throws IOException {
        XtbGenerator xtbGenerator = new XtbGenerator();
        xtbGenerator.setJsFile(getTestCollectionFrom("messages.js"));
        Map<String, JsMessage> messages = xtbGenerator.getMessagesFromJs();

        assertEquals(4, messages.size());
    }

    /**
     * Test loading messages from XTB file
     */
    @Test
    public void testGetMessagesFromTranslationFile() throws IOException {
        final MockXtbGenerator xtbGenerator = new MockXtbGenerator();
        xtbGenerator.setTranslationFile("messages.xtb");
        XtbMessageBundle xtbMessageBundle;
		try {
			xtbMessageBundle = xtbGenerator.getMessageBundleFromTranslationFile();


			Iterator<JsMessage> bundleIterator = xtbMessageBundle.getAllMessages().iterator();
			int i;
			for (i = 0; bundleIterator.hasNext(); i++) {
				bundleIterator.next();
			}

        	assertEquals(4, i);
		} catch (Exception ex) {
			ex.printStackTrace();
		}	
    }

    /**
     * Test comparing JS vs. XTB -> method must return only "NEW"
     */
    private void getMessages() throws IOException, ParserConfigurationException {
        final MockXtbGenerator xtbGenerator = new MockXtbGenerator();
        xtbGenerator.setTranslationFile("messages.xtb");

        // test - nothing to add
        xtbGenerator.setJsFile(getTestCollectionFrom("messages.js"));
        Map<String, JsMessage> messages = xtbGenerator.getMessages();
        assertEquals(0, messages.size());

        // test - 2 new to add
        xtbGenerator.setJsFile(getTestCollectionFrom("messages.js", "messages2add.js"));
        messages = xtbGenerator.getMessages();
        assertEquals(2, messages.size());

        assertNotNull(messages.get("8934769802850633699")); // MSG_TEST_5
        assertNotNull(messages.get("601502449317640790")); // MSG_TEST_6
    }

    /**
     * Complex test without already existed translation file
     */
    @Test
    public void testEmpty() throws IOException, URISyntaxException,
        ParserConfigurationException {
        final MockXtbGenerator xtbGenerator = new MockXtbGenerator();
        xtbGenerator.setJsFile(getTestCollectionFrom("messages.js"));
        xtbGenerator.setLang("cs");
        xtbGenerator.run();
        
        assertEquals(xtbGenerator.getMessagesContents(), output.toString());
    }

    /**
     * Complex test against translation file
     */
    @Test
    public void testAppend() throws IOException, URISyntaxException,
        ParserConfigurationException  {
        final MockXtbGenerator xtbGenerator = new MockXtbGenerator();
        xtbGenerator.setJsFile(getTestCollectionFrom("messages.js", "messages2add.js"));
        xtbGenerator.setTranslationFile("messages.xtb");
        xtbGenerator.setLang("cs");
        xtbGenerator.run();


        String expected = getStringFromClass("messages_append.xtb");

        String actual = output.toString();
        System.out.println(actual);
        assertEquals(expected, actual);
    }

    protected Collection<SourceFile> getTestCollectionFrom(String... fileName) throws IOException {
        final Collection<SourceFile> sourceFileCollections = new ArrayList<>();

        for (int i = 0; i < fileName.length; i++) {
            final InputStream fileStream = XtbGeneratorTest.class.getResourceAsStream("/" + fileName[i]);
            sourceFileCollections.add(SourceFile.fromInputStream(fileName[i], fileStream));
            fileStream.close();
        }

        return sourceFileCollections;
    }
}
