/********************************************
 * Christian Camilo Taborda Campi�o         *
 * C�digo: 1632081-3743                     *
 * Fecha de creaci�n: 04/03/2017            *
 * Fecha de �ltima modificaci�n: 08/03/2017 *
 * ****************************************** 
 */

package memoria;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Casilla extends JButton{
	
	//ATRIBUTOS:
	
	private static final int DIMENSION = 100;
	private int fila, columna;
	private ImageIcon imagen, espaldar;
	private boolean estado;
	
	//M�TODOS:
	
	//Constructor:
	public Casilla(int fila, int columna, ImageIcon imagen, ImageIcon espaldar){
		
		//Inicializaci�n de los atributos:		
		this.fila = fila;
		this.columna = columna;
		this.imagen = imagen;
		this.espaldar = espaldar;
		estado = false;
		
		//Especificaci�n de las caracter�sticas:		
		this.setOpaque(true);
		this.setIcon(espaldar);
		Dimension D = new Dimension(DIMENSION,DIMENSION);
		this.setPreferredSize(D);
		
	}
	
	//Retorna la fila:
	public int getFila(){
		return fila;
	}
	
	//Retorna la columna:
	public int getColumna(){
		return columna;
	}
	
	//Retorna la imagen:
	public ImageIcon getImagen(){
		return imagen;
	}
	
	//Cambia la imagen:
	public void setImagen(ImageIcon imagen){
		this.imagen = imagen;
	}
	
	//Cambia la imagen trasera:
	public void setEspaldar(ImageIcon espaldar){
		this.espaldar = espaldar;
	}
	
	//Retorna el estado:
	public boolean getEstado(){
		return estado;
	}
	
	//Cambia el estado y pone la imagen frontal:
	public void frente(){
		this.setIcon(imagen);
		estado = true;
	}
	
	//Cambia el estado y pone la imagen trasera:
	public void atras(){
		this.setIcon(espaldar);
		estado = false;
	}
	
	//Cambia las caras de la casilla:
	public void girar(){
		
		//Creaci�n un booleano auxiliar:
		boolean auxiliar = estado;
		
		//Cambio de estado de la casilla:
		if(auxiliar){
			estado = false;
		}
		else{
			estado = true;
		}
		
		//Creaci�n del hilo para los efectos del giro de la casilla:
		Thread hilo = new Thread(){
			
			//Creaci�n del m�todo del hilo:
			public synchronized void run(){
				
				//Creaci�n de la estructura Try-Catch:
				try{
					
					//Minimizaci�n del tama�o de la casilla:
					for(int x=DIMENSION; x>1; x-=3){
						Dimension D = new Dimension(x,x);
						sleep(2);
						setSize(D);
					}
					
					//Cambio de imagen de la casilla:
					if(auxiliar){
						setIcon(espaldar);
					}
					else{
						setIcon(imagen);
					}
					
					//Maximizaci�n del tama�o de la casilla:
					for(int x=1; x<(DIMENSION+1); x+=3){
						Dimension D = new Dimension(x,x);
						sleep(2);
						setSize(D);
					}
					
				}catch(Exception E){}
				
			}
			
		};
		
		//Inicializaci�n del m�todo del hilo:
		hilo.start();	
		
	}	
	
}
