/********************************************
 * Christian Camilo Taborda Campiño         *
 * Código: 1632081-3743                     *
 * Fecha de creación: 04/03/2017            *
 * Fecha de última modificación: 14/03/2017 *
 * ****************************************** 
 */

package memoria;

import java.awt.Font;
import java.util.Random;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

public class Controlador{
	
	//ATRIBUTOS:
	
	private static final int DIMENSION = 6;
	private Vector <ImageIcon> imagenes;
	private int contador, comparaciones;
	private boolean[] tanda1, tanda2;
	private int F, C;
	private Casilla[][] botones;
	private String tema;
	
	//MÉTODOS:
	
	//Retorna una imagen aleatoria del vector:
	public ImageIcon pintar(){	
		
		//Creación de la variable a retornar, el random y la posición:
		ImageIcon salida;
		Random aleatorio = new Random();
		int posicion;
		
		//Genera el número aleatorio sin repetirlo más de una vez:
		do{
			posicion = aleatorio.nextInt(imagenes.size());
		}while(tanda1[posicion] && tanda2[posicion]);
		
		//Inicialización de la variable a retornar:
		salida = imagenes.elementAt(posicion);
		
		//Control de la cantidad de veces que se repite un número:
		if(tanda1[posicion]){
			tanda2[posicion] = true;
		}
		else{
			tanda1[posicion] = true;
		}
		
		//Retorno:
		return salida;
	}
	
	//Retorna la imagen trasera de una casilla de acuerdo al tema:
	public ImageIcon pintarEspalda(){
		
		//Creación de la variable de salida:
		ImageIcon salida;
		
		//Inicialización de la variable de salida de acuerdo al tema:
		if(tema.equals("Pokemon")){
			salida = new ImageIcon(getClass().getResource("/iconos/pokebola.png"));
		}
		else{
			salida = new ImageIcon(getClass().getResource("/iconos/balon.png"));
		}
		
		//Retorno:
		return salida;
	}
	
	//Inicializa la matriz de casillas:
	public void initBotones(){
		
		//Creación de la matriz de botones:
		botones = new Casilla[DIMENSION][DIMENSION];
		
		//Creación de los botones de la matriz:
		for(int x=0; x<DIMENSION; x++){
			for(int y=0; y<DIMENSION; y++){
				botones[x][y] = new Casilla(x,y,pintar(),pintarEspalda());
			}
		}
		
	}
	
	//Retorna un tema:
	public String seleccionarTema(){
		
		//Inicialización del icono del JOptionPane:
		ImageIcon I = new ImageIcon(getClass().getResource("/iconos/tema.jpg"));
		
		//Creación de la variable a retornar:
		String salida;
		
		//Inicialización del arreglo con los temas:
		String[] temas = {"Pokemon", "Futbol"};
		
		//Inicialización de la variable a retornar:
		salida = (String)JOptionPane.showInputDialog(null, "Elige un tema:", "Selector de temas", JOptionPane.DEFAULT_OPTION, I, temas, temas[0]);
		
		//Cierre de la aplicación en caso de no elegir un tema:
		if(salida == null){
			System.exit(-1);
		}
		
		//Retorno:
		return salida;
		
	}
	
	//Inicializa el vector con las imágenes:	
	public void cargarVector(){
		
		//Creación del vector:
		imagenes = new Vector <ImageIcon> ();
		
		//Almacenamiento de las imágenes en el vector:
		for(int y=1; y<19; y++){
			ImageIcon imagen = new ImageIcon(getClass().getResource("/" + tema + "/" + y + ".jpg"));
			imagenes.addElement(imagen);
		}
		
	}
	
	//Inicializa las tandas:
	public void cargarTandas(){
		
		//Creación de las tandas:
		tanda1 = new boolean[18];
		tanda2 = new boolean[18];
		
		/*
		 * Las tandas sirven para controlar que las imágenes
		 * aleatorias sólo se repitan una vez.
		 * 
		 */
		
		//Inicialización de las tandas:
		for(int x=0; x<18; x++){
			tanda1[x] = false;
			tanda2[x] = false;
		}
		
	}
	
	//Constructor:	
	public Controlador(){
		
		//Cambio de color para los JOptionPane:
		UIManager.put("OptionPane.background",new ColorUIResource(255,255,255));
		UIManager.put("Panel.background",new ColorUIResource(255,255,255));
		
		//Inicialización de fuentes para los JOptionPane:
		Font label = new Font("Arial",Font.BOLD,22);
		Font button = new Font("Arial",Font.BOLD,15);
		
		//Cambio de fuente para los JOptionPane:
		UIManager.put("OptionPane.messageFont", label);
		UIManager.put("OptionPane.buttonFont", button);
		
		//Inicialización de los atributos:		
		tema = seleccionarTema();
		cargarVector();
		cargarTandas();
		initBotones();
		contador = 0;
		comparaciones = 0;
		
	}
	
	//Retorna la matriz de botones:
	public Casilla[][] getBotones(){
		return botones;
	}
	
	//Reinicializa las variables para un nuevo juego:
	public void reiniciar(){
				
		//Reinicialización de los atributos del controlador del juego:
		contador = 0;
		comparaciones = 0;
		tema = seleccionarTema();
		imagenes.clear();
		cargarVector();
		cargarTandas();
		
		//Creación del hilo para los efectos del reinicio del juego:
		Thread hilo = new Thread(){
			
			//Creación del método del hilo:
			public synchronized void run(){
				
				//Creación de la estructura Try-Catch:
				try{
					
					//Giro de las casillas para reiniciar el juego:
					for(int x=(DIMENSION-1); x>=0; x--){
						for(int y=(DIMENSION-1); y>=0; y--){
							botones[x][y].atras();
							
							//Tiempo de espera del giro de cada casilla:
							sleep(50);
							
							//Selección aleatoria de las imágenes para el nuevo juego:
							botones[x][y].setEspaldar(pintarEspalda());
							botones[x][y].setImagen(pintar());
							
						}
						
					}
					
					//Tiempo de espera antes de visualizar las casillas:
					sleep(400);
					
					//Giro de las casillas para la visualización del usuario:
					for(int x=(DIMENSION-1); x>=0; x--){
						for(int y=(DIMENSION-1); y>=0; y--){
							
							//Tiempo de espera para mostrar cada imagen:
							sleep(50);
							
							//Giro de cada casilla:
							botones[x][y].frente();
							
						}
					}
					
					//Tiempo de espera de la visualización:
					sleep(3000);
					
					//Giro de las casillas para reiniciar el juego:
					for(int x=(DIMENSION-1); x>=0; x--){
						for(int y=(DIMENSION-1); y>=0; y--){
							
							//Tiempo de espera entre cada giro de la casilla:
							sleep(50);
							
							//Giro final para el reinicio del juego:
							botones[x][y].atras();
							
						}
						
					}
					
				}catch(Exception E){}
				
			}
			
		};
		
		//Inicialización del método del hilo:
		hilo.start();
		
	}
	
	//Indica si el usuario desea reiniciar el juego o no retornando la decisión:
	public int seguir(){
		
		//Creación de variables a utilizar:
		int decision;
		String nombre;
		
		//Inicialización del nombre del icono de acuerdo al tema:
		if(tema.equals("Pokemon")){
			nombre = "preguntaP";
		}
		else{
			nombre = "preguntaC";
		}
		
		//Creación del icono a utilizar:
		ImageIcon imagen = new ImageIcon(getClass().getResource("/iconos/" + nombre + ".gif"));
		
		//Inicialización de la decisión del usuario:
		decision = JOptionPane.showConfirmDialog(null, "¿Quieres volver a jugar?", "Reinicio", JOptionPane.YES_NO_OPTION, JOptionPane.DEFAULT_OPTION, imagen);
		
		//Retorno:
		return decision;
		
	}
	
	//Reinicia el juego o no de acuerdo a la decisión del usuario:
	public void reinicio(){
		
		//Validación de la decisión tomada:
		if(seguir() == 0){
			reiniciar();
		}
		else{
			System.exit(-1);
		}
				
	}
	
	//Determina si el juego acabó y qué hacer:
	public void juzgar(){
		
		//Creación de la variable que determina la decisión:
		boolean juicio = true;
		
		//Validación del juego:
		for(int x=0; x<DIMENSION; x++){
			for(int y=0; y<DIMENSION; y++){
				if(!botones[x][y].getEstado()){
					juicio = false;
				}
			}
		}
		
		//Validación de la decisión obtenida:
		if(juicio){
			
			//Creación del hilo para la determinación del juego:
			Thread hilo = new Thread(){
				
				//Creación del método del hilo:
				public synchronized void run(){
					
					//Creación de la estructura Try-Catch:
					try{
						
						//Creación de la variable para cargar el icono a utilizar:
						String nombre;
						
						//Inicialización de la variable de acuerdo al tema:
						if(tema.equals("Pokemon")){
							nombre = "victoriaP";
						}
						else{
							nombre = "victoriaC";
						}
						
						//Tiempo de espera antes de determinar el juego:
						sleep(500);
						
						//Repintamos los botones para indicar el final del juego:
						for(int x=0; x<DIMENSION; x++){
							for(int y=0; y<DIMENSION; y++){
								
								//Tiempo esperado al habilitar cada casilla:
								sleep(50);
								
								//Repintado de los botones:
								botones[x][y].setEnabled(true);
								
							}
							
						}
						
						//Tiempo esperado antes de mostrar la sentencia del juego:
						sleep(400);
						
						//Creación del icono a utilizar:
						ImageIcon imagen = new ImageIcon(getClass().getResource("/iconos/" + nombre + ".gif"));
						
						//Determinación del juego:
						JOptionPane.showMessageDialog(null, "¡Has Ganado!\nNúmero de comparaciones: " + comparaciones, "Final", JOptionPane.DEFAULT_OPTION, imagen);
						
						//Validación de la continuación del juego:
						reinicio();
						
					}catch(Exception E){}
					
				}
				
			};
			
			//Inicialización del método del hilo:
			hilo.start();
			
		}
		
	}
	
	//Valida la jugada del usuario:
	public void jugar(int fila, int columna){
			
		//Contamos el turno:
		contador += 1;
		
		//Giro de la casilla:
		botones[fila][columna].girar();
		
		//Validación del turno:
		if(contador%2 != 0){	
			
			//Inicialización de la fila y columna a almacenar:
			F = fila;
			C = columna; 
			
		}
		
		else{
			
			//Validamos que no sea la misma casilla:
			if(!(fila == F && columna == C)){
				
				//Contamos el movimiento:
				comparaciones += 1;
				
				//Comparación de imágenes:
				if(botones[F][C].getImagen().equals(botones[fila][columna].getImagen())){
													
					//Creación del hilo para las jugadas correctas:
					Thread hilo = new Thread(){
											
						//Creación del método del hilo:
						public synchronized void run(){
													
							//Creación de la estructura Try-Catch:
							try{
								
								//Tiempo de espera entre la comparación:
								sleep(380);
								
								//Deshabilitación de las casillas:
								botones[F][C].setEnabled(false);
								botones[fila][columna].setEnabled(false);
								
							}catch(Exception E){}	
							
						}
												
					};
							
					//Inicialización del método del hilo:
					hilo.start();
					
				}
				
				else{
				
					//Creación del hilo para las jugadas incorrectas:
					Thread hilo = new Thread(){
						
						//Creación del método del hilo:
						public synchronized void run(){
							
							//Creación de la estructura Try-Catch:
							try{
								
								//Tiempo de espera entre la comparación:
								sleep(380);
								
								//Giro nuevamente de ambas casillas:
								botones[F][C].girar();
								botones[fila][columna].girar();
								
							}catch(Exception E){}
							
						}
						
					};
					
					//Inicialización del método del hilo:
					hilo.start();	
				}
				
			}
			
		}
				
		//Determinación de la continuación del juego:
		juzgar();
			
	}
	
	//Imprime un mensaje de ayuda:
	public void ayudar(){
		
		//Creación de la variable con el nombre de la imagen:
		String nombre;
		
		//Creación del icono para el JOptionPane según el tema:
		if(tema.equals("Pokemon")){
			nombre = "ayudaP";
		}
		else{
			nombre = "ayudaC";
		}
		
		ImageIcon I = new ImageIcon(getClass().getResource("/iconos/" + nombre + ".gif"));
		
		//Creación del mensaje a imprimir:
		String mensaje = "Pulsa en las casillas para revelar sus imágenes,\ntrata de recordar y encontrar sus parejas con la\nmenor cantidad de comparaciones posibles.\nSuerte.";
		
		//Impresión del mensaje:
		JOptionPane.showMessageDialog(null, mensaje, "Ayuda", JOptionPane.DEFAULT_OPTION, I);
		
	}
	
}
