/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playlistsync;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

/**
 *
 * @author Andrea
 */
public class MobileToPc {

    private static final String PLAYLIST_NAME = "Best of";
    private static final String MOBILE_SONGS_PATH = "/storage/DC24-3A5F/Musica/";
    private static final String PC_SONGS_PATH = "E:\\Musica\\Andrea\\";
    private static final String PC_PLAYLIST_PATH = "E:\\Musica\\Playlists\\";
    
    public static void main(String[] args) throws FileNotFoundException, IOException, ParserConfigurationException, TransformerException {
        
        //CREATE XML DOCUMENT
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder parser = factory.newDocumentBuilder();
        Document doc = parser.newDocument();
        
        
        //READ M3U
        Element seq = doc.createElement("seq");
        BufferedReader source = new BufferedReader(new FileReader("C:\\Users\\Andrea\\Desktop\\"+PLAYLIST_NAME+".m3u8"));
        String line = source.readLine();
        while(line!=null){
            if(line.startsWith("/")){
                Element media = doc.createElement("media");
                media.setAttribute("src",line.replace(MOBILE_SONGS_PATH, PC_SONGS_PATH).replace('/', '\\'));
                System.out.println(line.replace(MOBILE_SONGS_PATH, PC_SONGS_PATH).replace('/', '\\'));
                seq.appendChild(media);
            }
            line = source.readLine();
        }
        
        //WRITE XML
        Element root = doc.createElement("smil");
        Element head = doc.createElement("head");
        Element title = doc.createElement("title");
        title.setTextContent(PLAYLIST_NAME);
        Element body = doc.createElement("body");
        
        head.appendChild(title);
        body.appendChild(seq);
        root.appendChild(head);
        root.appendChild(body);
        doc.appendChild(root);
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(new File(PC_PLAYLIST_PATH+PLAYLIST_NAME+".zpl"));

        transformer.transform(domSource, streamResult);
        
    }
    
}
