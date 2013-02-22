/* Luca RINAUDO - l.rinaudo@rt-iut.re
 * Projet de d�veloppement d'une plateforme de supervision r�seau
 * 
 * Architecture bas�e sur un serveur en php/mysql qui recense des informations syst�mes & r�seau
 * provenant de plusieurs agents.
 * 
 * v1.1 - traitement + envoi
 */


import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
 
public class Agents {
	
	public static void main(String[] args)  {
	  
	  //String serveurPHP = args[0];
	 
	 /////////////////////
	 //Partie Traitement//
	 /////////////////////
	  
	try{
	 
  	// Obtenir le nom et l'adresse LOGIQUE 
	
    InetAddress addressIP = InetAddress.getLocalHost () ;
    String hostName = addressIP.getHostName();
     NetworkInterface ni = NetworkInterface.getByInetAddress(addressIP);
    byte[] mac = ni.getHardwareAddress();
    
    System.out.println("Mon Nom : "+hostName); // visualisation du nom
    System.out.println("Mon @IP : "+addressIP); // Visualisation de l'@IP
    
 
   
    
    //String o1 = Byte.toString(mac[0]);
    //String o2 = Byte.toString(mac[1]);
    // String o3 = Byte.toString(mac[2]);
    // String o4 = Byte.toString(mac[3]);
    // String o5 = Byte.toString(mac[4]);
    // String o6 = Byte.toString(mac[5]);
    //String addressMAC = o1+":"+o2+":"+o3+":"+o4+":"+o5+":"+o6;
   
    
  // Obtenir l'adresse PHYSIQUE 
    
    
    StringBuffer addressMAC = new StringBuffer();
     for (int i = 0; i < mac.length; i++) {
    	 addressMAC.append(String.format( "%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
     }
     
     System.out.println("Mon @MAC : "+addressMAC); // Visualisation de l'@MAC
     
     //Traitement de la date de derniere MAJ
     
     Date maintenant = new Date(); 
     SimpleDateFormat formatDateJour = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss"); 
     String dateFormatee = formatDateJour.format(maintenant); 
     
     System.out.println("Date MAJ : " + dateFormatee); // Visualisation de la date
     
     
     // On cr�e une nouvelle chaine de caract�re qui regroupe toutes les informations qu'on va envoyer apr�s
     
  // serveur de Ritchy
    String informationSentence = "nom="+hostName+"&ipAdress="+addressIP+"&macAdress="+addressMAC+"&date"+dateFormatee; 
    
 // serveur de Antoine   
    //String informationSentence = "ip="+addressIP+"&hostname="+hostName+"&mac="+addressMAC+"&date"+dateFormatee; 
    
    //System.out.println(informationSentence); //visulation de l'information 
    
    
    
    //////////////////
    // Partie Envoi //
    //////////////////
    
    // L'url du site auquel on s'appr�te � se connecter
    
    // Serveur de Ritchy
   URL url = new URL("http://192.168.60.240/IC4/PHP/index.php"); // a d�finir en statique
   
   //Serveur de Antoine
   //URL url = new URL("http://10.210.177.46/agents/NetworkMonitoring.php");
     
    // Cr�ation de l'objet httpsURLConnection.
    HttpURLConnection http = (HttpURLConnection) url.openConnection();
      
    // On lance la connexion
    http.setDoOutput(true);
    http.setRequestMethod("POST"); // On pr�cise la m�thode � utiliser pour le traitement en  php
    http.connect();
    
   // On extrait le flux d'entr�e pour l'envoyer au serveur
    PrintWriter outToServerPHP = new PrintWriter(http.getOutputStream(), true);
    
   outToServerPHP.println(informationSentence);
   
   System.out.println(http.getResponseMessage());
   System.out.println(http.getResponseCode());
    
	  } catch(IOException e) {
			e.printStackTrace();
		}
    
    
  } 
}