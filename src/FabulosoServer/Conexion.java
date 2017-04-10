package FabulosoServer;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import clienteudp.backend.ObjetoUDP;

public class Conexion extends Thread{

private DatagramSocket socket;
byte []  llegan;
private Server superServer;
String direccion;
private FileWriter archivo;
private  BufferedWriter bufferedWriter ;
	public Conexion(DatagramSocket pSock, byte[] recibido, Server server, String nombre, FileWriter parchivo) 
	{
		socket = pSock;
		llegan= recibido;
		superServer=server;
		direccion= nombre;
		archivo=parchivo;
		this.start();  

		// TODO Auto-generated constructor stub
	}

	public void run() { 

		
		
		try
		{

			System.out.println("LLEGA ALGO");
			String llegada = new String (llegan);
			
		
		         bufferedWriter = new BufferedWriter(archivo);
		            String str = new String(new String(llegan, 0, llegan.length));
		            bufferedWriter.write(str);
		            bufferedWriter.newLine();
		        
		        bufferedWriter.close();
			

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	
	}

}
