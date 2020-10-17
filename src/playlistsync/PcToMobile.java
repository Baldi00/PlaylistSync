/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playlistsync;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Andrea
 */
public class PcToMobile {

    private static final String MOBILE_SONGS_PATH = "/storage/DC24-3A5F/Musica/";
    private static final String PC_SONGS_PATH = "E:\\Musica\\Andrea\\";
    
    public static void main(String[] args) throws FileNotFoundException, IOException, ParserConfigurationException, TransformerException, SAXException {
        
        //CREATE XML DOCUMENT
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder parser = factory.newDocumentBuilder();
        Document doc = parser.parse(new File("E:\\Musica\\Playlists\\Best of.zpl"));
        
        
        //READ ZPL
        NodeList songsOKElements = doc.getElementsByTagName("media");
        ArrayList<String> songsOK = new ArrayList();
        
        for(int i = 0; i < songsOKElements.getLength(); i++)
            songsOK.add(songsOKElements.item(i).getAttributes().item(0).getTextContent().toLowerCase());
        
        //READ M3U
        BufferedReader source = new BufferedReader(new FileReader("C:\\Users\\Andrea\\Desktop\\Generated Playlist 1.m3u8"));
        BufferedWriter dest = new BufferedWriter(new FileWriter("C:\\Users\\Andrea\\Desktop\\Best of.m3u"));
        String line = source.readLine();
        int cont = 0;
        while(line!=null){
            if(line.startsWith("#EXTINF")){
                String ext = line;
                line = source.readLine();
                String song = line;
                if(songsOK.contains(song.replace(MOBILE_SONGS_PATH, PC_SONGS_PATH).replace('/', '\\').toLowerCase())){
                    cont++;
                    dest.append(ext);
                    dest.newLine();
                    dest.append(song);
                    dest.newLine();
                }
            }
            line = source.readLine();
        }
        
        dest.close();
        System.out.println(cont);
    }
}
