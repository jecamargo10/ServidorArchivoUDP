package FabulosoServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Server   extends Thread{

	private String puerto;
	private static ArrayList<Client> info;
	private FileWriter archivo;
	private String archivoNombre;
	private String checksum;
	private int tamanio;
	private boolean recibiendo;


	public Server (String pPuerto)
	{	
		puerto = pPuerto;
		info = new ArrayList<Client>();
		recibiendo=false;
		this.start();


	}

	public ArrayList<Client> getarr()
	{
		return info;

	}
	public boolean clientExits(String pCliente)
	{
		for (int i = 0; i < info.size(); i++) 
		{
			if(info.get(i).getIpAddres().equals(pCliente))	
			{
				return true;

			}
		}
		return false;

	}
	public void addClient(String ip)
	{
		Client e = new Client(ip);
		info.add(e);
		try
		{
			PrintWriter out;
			String savestr = ip.replace(".", " ") +".csv";
			File f = new File(savestr);
			System.out.println(f.getAbsolutePath());
			out = new PrintWriter(savestr);			out.append("Id,Tiempo");
			out.append('\n');
			out.close();
		}catch (Exception x)
		{
			x.printStackTrace();
		}

	}
	public void addRecibido(String ip,String texto, int something,int numero)
	{

		for (int i = 0; i < info.size(); i++) {
			Client cliente =info.get(i);
			if(cliente.getIpAddres().equals(ip))	
			{

				cliente.recibido();
				cliente.addtime(something);

				cliente.aumentarTamanio(numero);
				try {
					PrintWriter out;
					String savestr = ip.replace(".", " ") +".csv";
					File f = new File(savestr);
					System.out.println("ESCRIBO");

					out = new PrintWriter(new FileOutputStream(new File(savestr), true));
					out.append(texto.split(":")[0] + ","+ texto.split(":")[1]);
					out.append('\n');
					out.close();

				} catch (FileNotFoundException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				try {
					PrintWriter out;
					String savestr = ip.replace(".", " ") + " estadisticas"+".csv";
					File f = new File(savestr);
					System.out.println(f.getAbsolutePath());
					out = new PrintWriter(savestr);
					out.append("Objetos Recibidos,Objetos Fallidos,Tiempo Promedio");
					out.append('\n');
					System.out.println("FALLIDOS"+cliente.getObjetosFallidos());
					System.out.println("cantidad"+cliente.getCantidad());
					out.append(cliente.getObjetosRecibidos()+","+cliente.getObjetosFallidos()+","+cliente.getTiempoPromedio()+ "ms");
					out.close();
				} catch (FileNotFoundException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}



			}	
		}
	}





	public void run()
	{
		DatagramSocket sock = null;

		try
		{
			//1. creating a server socket, parameter is local port number
			sock = new DatagramSocket(Integer.parseInt(puerto));
			sock.setReceiveBufferSize(64000);
			//buffer to receive incoming data


			//2. Wait for an incoming data
			//   echo("Server socket created. Waiting for incoming data...");

			//communication loo

			while(true)
			{
				byte[] buffer = new byte[1024];
				DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
				sock.receive(incoming);
				byte [] recibido = new byte[incoming.getLength()];
				recibido =  incoming.getData();
				String nombre = incoming.getAddress().getHostAddress() +" port "+ incoming.getPort();
				boolean existo = clientExits(nombre);
				if(!existo)
				{
					addClient(nombre);
				}
				String llegada = new String (recibido);
				System.out.println(llegada);
				//Coje lo de llegada asociado al archivo
				if (llegada.startsWith("ARCHIVO:"))
				{
					archivoNombre = llegada.split(":")[1];
					checksum = llegada.split(":")[2];
					String tamano= llegada.split(":")[3];
					tamanio=Integer.parseInt(tamano);
					byte[] receiveData = new byte[tamanio];
					for (int i = 0; i < receiveData.length; i++) {
						if((receiveData.length-i)>= 64000){
							
							DatagramPacket  receivePacket = new DatagramPacket(receiveData, i,64000 );
							sock.receive(receivePacket);
							System.out.println("llega");
							i+=(64000-1);
							System.out.println("recibidos "+i+" bytes del archivo...");
						}
						else{
							DatagramPacket  receivePacket = new DatagramPacket(receiveData, i,receiveData.length-i);
							sock.receive(receivePacket);
							System.out.println("recibido ultimo paquete de"+(i+(receiveData.length-i))+" bytes del archivo.");
							i+=(64000-1);


						}
					}
					FileOutputStream fos = new FileOutputStream("./"+archivoNombre);
					fos.write(receiveData);
					fos.close();

					MessageDigest md;
					try {
						md = MessageDigest.getInstance("SHA1");



						FileInputStream fis = new FileInputStream(archivoNombre);

						byte[] dataBytes = new byte[1024];

						int nread = 0;

						while ((nread = fis.read(dataBytes)) != -1) {
							md.update(dataBytes, 0, nread);
						};

						byte[] mdbytes = md.digest();

						//convert the byte to hex format
						StringBuffer sb = new StringBuffer("");
						for (int i = 0; i < mdbytes.length; i++) {
							sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
						}

						System.out.println("Digest(in hex format):: " + sb.toString()+ "is equal to"+checksum);

					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


				}
			}


		}

		catch(IOException e)
		{
			System.err.println("IOException " + e);
		}




	}

	//simple function to echo data to terminal
	public static void echo(String msg)
	{
		System.out.println(msg);
	}


	public static void main(String args[])
	{
		String puerto = "5000";
		for (int i = 0; i < args.length; i++) {
			puerto = args[i];
		}
		System.out.println("Puerto: "+puerto);

		Server servidor = new Server(puerto);




	}



}




