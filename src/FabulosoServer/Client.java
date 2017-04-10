package FabulosoServer;

public class Client 
{
private String ipAddres;
private int objetosRecibidos;
private int objetosFallidos;
private int sumaPromedio;
private int cantidad;
public int getCantidad() {
	return cantidad;
}
public void setCantidad(int cantidad) {
	this.cantidad = cantidad;
}
private int tiempoPromedio;
private int tamanioMaximo;


public Client (String pipAddres)
{
	ipAddres = pipAddres;
	cantidad =0;
	sumaPromedio=0;
	tiempoPromedio = 0;
	objetosFallidos=0;
	objetosRecibidos=0;
	tamanioMaximo=0;
}
public void aumentarTamanio(int tamanio)
{
	if (tamanioMaximo > tamanio)
	{
		
	}
	else
	{
		tamanioMaximo = tamanio;
		
	}
	
}
public void recibido()
{
	objetosRecibidos++;
}

public void addtime(int time)
{
	sumaPromedio+=time;
	cantidad ++;
	tiempoPromedio = sumaPromedio/cantidad;
	
}
public int getTiempoPromedio() {
	return tiempoPromedio;
}


public void setTiempoPromedio(int tiempoPromedio) {
	this.tiempoPromedio = tiempoPromedio;
}



public int getObjetosFallidos() {
	return tamanioMaximo - objetosRecibidos;
}
public void setObjetosFallidos(int objetosFallidos) {
	this.objetosFallidos = objetosFallidos;
}
public int getObjetosRecibidos() {
	return objetosRecibidos;
}
public void setObjetosRecibidos(int objetosRecibidos) {
	this.objetosRecibidos = objetosRecibidos;
}
public String getIpAddres() {
	return ipAddres;
}
public void setIpAddres(String ipAddres) {
	this.ipAddres = ipAddres;
}

}
