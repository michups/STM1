/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stm;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author micha
 */
@WebService(serviceName = "NewWebService")
public class NewWebService {
    
    public final float  longtitudeMAX = 51.4167f;
    public final float longtitudeMIN =  51.40892f;
    public final float  longtitudeSize = longtitudeMAX-longtitudeMIN;
    public final float latitudeMAX = 21.15f;
    public final float latitudeMIN = 21.136117f;
    public final float latitudeSize = latitudeMAX-latitudeMIN;
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
        /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "test")
    public String test(@WebParam(name = "name") String txt) {
       String base64Image = "";
       
	ClassLoader classLoader = getClass().getClassLoader(); 
	File file=null;
        try {
            file = new File(classLoader.getResource("test.PNG").toURI());
        } catch (URISyntaxException ex) {
            Logger.getLogger(NewWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
	try (FileInputStream imageInFile = new FileInputStream(file)) {
		// Reading a Image file from file system
		byte imageData[] = new byte[(int) file.length()];
                System.out.println(file.length());
		imageInFile.read(imageData);
		base64Image = Base64.getEncoder().encodeToString(imageData);
	} catch (FileNotFoundException e) {
		System.out.println("Image not found" + e);
	} catch (IOException ioe) {
		System.out.println("Exception while reading the Image " + ioe);
	}
	return base64Image;
    }
    
    /**
     *
     * @param longitude
     * @param latitude
     * @return
     */
    @WebMethod(operationName = "getMap")
    public String getMap(@WebParam(name = "longitude1")  Float longitude1, @WebParam(name = "latitude1") Float latitude1,
            @WebParam(name = "longitude2")  Float longitude2, @WebParam(name = "latitude2") Float latitude2) {
       String base64Image = "";
       /*TODO zabezpieczenia*/
       
       
	ClassLoader classLoader = getClass().getClassLoader();  
        BufferedImage cuttedImage = null;
        byte[] imageInByte = null;
        try {
      System.out.println("Value = " + classLoader.getResource("mapa.PNG").toURI());
            File file = new File(classLoader.getResource("mapa.PNG").toURI());

            Image srcImg = ImageIO.read(file);
            cuttedImage = cutImage(srcImg, longitude1, latitude1, longitude2, latitude2); 
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( cuttedImage, "PNG", baos );
            baos.flush();
            imageInByte = baos.toByteArray();
            base64Image = Base64.getEncoder().encodeToString(imageInByte); 
            baos.close();
        } catch (URISyntaxException ex) {
            Logger.getLogger(NewWebService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NewWebService.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
	return base64Image;
    }
    
    
    private BufferedImage cutImage(Image src, float longitude1, float latitude1, float longitude2, float latitude2) throws IOException{
        int x = Math.round(((longitude1-longtitudeMIN)/longtitudeSize)*1000f); 
        int y = Math.round(((latitude2-latitudeMIN)/latitudeSize)*1000f);  
        int w = Math.abs(Math.round(((longitude2-longitude1)/longtitudeSize)*1000f)); 
        int h =  Math.abs(Math.round(((latitude2-latitude1)/latitudeSize)*1000f));
        w=Math.abs(w);
        h=Math.abs(h);
        BufferedImage dest = ((BufferedImage)src).getSubimage(x, y, w, h);
//        BufferedImage dst = new BufferedImage(w, h, BufferedImage.TYPE_INT_BGR);
//        Graphics g = dst.getGraphics();
//        g.drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
//             BufferedImage bi = (BufferedImage)src;
        ImageIO.write(dest, "png", new File("duke_croppedaaa.png"));
//        ImageIO.write(src, "png", new File("duke_cropped.png"));
        return dest;
        
    }
}
