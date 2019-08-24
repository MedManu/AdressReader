/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adressreader;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import org.jsoup.Connection.Response;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author mamed
 */
public class AdressReader {

    /**
     * @param args the command line arguments
     */
  
   public static void parsefrompage (int pageside, int wiederholungen) {
       
       for (int i = 0; i < wiederholungen; i++) {
           System.out.println("running...");
		Document document;
                Response documentpage;
               
		try {
			//Get Document object after parsing the html from given url.
//                        documentpage = Jsoup.connect("https://www.physiotherapie.at/go.asp?sektion=personen&aktion="
//                                + "view&berufsgruppe=phy&geschlecht=A&bereich_id=9104&subbereich_id=0&order_by=crabc&order_by_marker=K&bundesland=&bezirks_id="
//                                + "0&ort=PLZ+%2F+Ort&distance=0&kategorie_id=0&familienname=Name&search_simple=Suche+starten").execute();
                        
                        Response pages = Jsoup.connect("https://www.physiotherapie.at/go.asp?ZS=&ss=ergebnis&sektion=personen&aktion=view&suchformular_id=9&bereich_id="
                                + "9104&berufsgruppe=phy&geschlecht=A&order_by=crabc&order_by_marker=K&bundesland=&DS="+ Integer.toString(pageside)).execute();
                        
                        pageside += 15;
                      
                    document = Jsoup.parse(pages.body());
                    
                    Elements eintraege = document.select("div.columns > div.black1");
                   
                    
                    for (Element eintrag : eintraege) {
                       
                        Element number = eintrag.select("span.is-hidden-desktop").first();
                        Element mobil = eintrag.select("span.is-hidden-touch").first();
                        Element homepage = eintrag.select("u").first();
                        Element lastName = eintrag.select("h2 span").first();
                        
                        Element firstName = eintrag.select("h2").first();
                        firstName.select("span").remove();
                        
                        Elements adressen = eintrag.select("div.black1");
                        
                        System.out.println("Nachname: " + lastName.text());
                        System.out.println("Vorname: "+ firstName.text());
                        
                        for (Element address : adressen) {
                     
                            if (!address.select("span").isEmpty() && (!address.select("div").isEmpty())){
                                
                                address.select("span").remove();
                                address.select("div").remove();
                                
                            } 
                        if (!address.text().trim().isEmpty()) {
                        System.out.println("Adresse: " + address.text());
                        
                        }
                        }
                       
                        System.out.println(homepage.text());
                        
                        if (number != null) {
                        System.out.println(number.text());
                        }
                        
                        if (mobil != null ){
                        System.out.println(mobil.text());
                        }
                        
                        System.out.println(" ");
                       
                        
                    }
                  
		} 
                catch (IOException e) {
			e.printStackTrace();
		}
		         System.out.println("done");
	}

	
   }
        
        public static void main(String[] args) {
        
           parsefrompage(1,10);
    }
        
        
    }
    

