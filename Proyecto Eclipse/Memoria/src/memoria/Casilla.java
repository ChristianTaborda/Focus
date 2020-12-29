/********************************************
 * Christian Camilo Taborda Campiño         *
 * Código: 1632081-3743                     *
 * Fecha de creación: 04/03/2017            *
 * Fecha de última modificación: 08/03/2017 *
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
	
	//MÉTODOS:
	
	//Constructor:
	public Casilla(int fila, int columna, ImageIcon imagen, ImageIcon espaldar){
		
		//Inicialización de los atributos:		
		this.fila = fila;
		this.columna = columna;
		this.imagen = imagen;
		this.espaldar = espaldar;
		estado = false;
		
		//Especificación de las características:		
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
		
		//Creación un booleano auxiliar:
		boolean auxiliar = estado;
		
		//Cambio de estado de la casilla:
		if(auxiliar){
			estado = false;
		}
		else{
			estado = true;
		}
		
		//Creación del hilo para los efectos del giro de la casilla:
		Thread hilo = new Thread(){
			
			//Creación del método del hilo:
			public synchronized void run(){
				
				//Creación de la estructura Try-Catch:
				try{
					
					//Minimización del tamaño de la casilla:
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
					
					//Maximización del tamaño de la casilla:
					for(int x=1; x<(DIMENSION+1); x+=3){
						Dimension D = new Dimension(x,x);
						sleep(2);
						setSize(D);
					}
					
				}catch(Exception E){}
				
			}
			
		};
		
		//Inicialización del método del hilo:
		hilo.start();	
		
	}	
	
}
