/********************************************
 * Christian Camilo Taborda Campiño         *
 * Código: 1632081-3743                     *
 * Fecha de creación: 04/03/2017            *
 * Fecha de última modificación: 14/03/2017 *
 * ****************************************** 
 */

package memoria;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MemoriaGUI extends JFrame{
	
	//ATRIBUTOS:
	
	private static final int DIMENSION = 6;
	private JLabel titulo;
	private JPanel marco, norte;
	private Container contenedor;
	private Controlador juego;
	private JButton ayuda;
	private Casilla[][] botones;
	
	//MÉTODOS:	
	
	//Inicializa los componentes de la GUI:
	public void initGUI(){
		
		//Creación del juego:
		juego = new Controlador();
		
		//Inicialización del contenedor:
		contenedor = this.getContentPane();
		
		//Asignación del Layout al contenedor:
		contenedor.setLayout(new BorderLayout());
		
		//Creación del marco:
		marco = new JPanel();
		
		//Asignación del Layout al marco:
		marco.setLayout(new GridLayout(DIMENSION,DIMENSION));
		
		//Asignación de color al fondo del marco:
		marco.setBackground(Color.YELLOW);
		
		//Creación de la escucha de los botones:
		Escucha escucha = new Escucha();
		
		//Inicialización de los botones:
		botones = juego.getBotones();
		
		//Asignación de la escucha y almacenamiento en el marco de los botones:
		for(int x=0; x<DIMENSION; x++){
			for(int y=0; y<DIMENSION; y++){
				botones[x][y].addActionListener(escucha);
				marco.add(botones[x][y]);
			}
		}
		
		//Almacenamiento del marco en el contenedor:
		contenedor.add(marco, BorderLayout.CENTER);
		
		//Creación del panel del norte:
		norte = new JPanel();
		
		//Asignación del Layout y el color de fondo al panel del norte:
		norte.setLayout(new FlowLayout());
		norte.setBackground(Color.YELLOW);
		
		//Creación del título:
		titulo = new JLabel("¡Encuentra las parejas!           ");
		
		//Inicialización de la fuente para el título y el botón de ayuda:
		Font fuente = new Font("Arial", Font.BOLD, 35);
		
		//Especificación de las características del título:
		titulo.setOpaque(true);
		titulo.setFont(fuente);
		titulo.setForeground(Color.RED);
		titulo.setBackground(Color.YELLOW);
		titulo.setHorizontalAlignment(JLabel.CENTER);
		
		//Almacenamiento del título en el panel del norte:
		norte.add(titulo);
		
		//Inicialización del botón de ayuda:
		ayuda = new JButton("?");
		ayuda.setFont(fuente);
		ayuda.setForeground(Color.YELLOW);
		ayuda.setBackground(Color.RED);
		ayuda.setOpaque(true);
		ayuda.setFocusPainted(false);
		ayuda.addActionListener(escucha);
		
		//Almacenamiento del botón de ayuda en el panel del norte:
		norte.add(ayuda);
		
		//Almacenamiento del panel del norte en el contenedor:
		contenedor.add(norte, BorderLayout.NORTH);
		
		//Creación del hilo para los efectos del inicio de la aplicación:
		Thread hilo = new Thread(){
			
			//Creación del método del hilo:
			public synchronized void run(){
				
				//Creación de la estructura Try-Catch:
				try{
					
					//Tiempo de espera antes de mostrar las imágenes:
					sleep(800);
					
					//Giro de las casillas para la visualización del usuario:
					for(int x=0; x<DIMENSION; x++){
						for(int y=0; y<DIMENSION; y++){
							
							//Tiempo de espera para mostrar cada imagen:
							sleep(50);
							
							//Giro de la casilla:
							botones[x][y].frente();
							
						}
					}
					
					//Tiempo de espera de la visualización:
					sleep(3000);
					
					//Giro de las casillas para iniciar el juego:
					for(int x=0; x<DIMENSION; x++){
						for(int y=0; y<DIMENSION; y++){
																				
							//Tiempo de espera entre cada giro de casilla:
							sleep(50);
							
							//Giro final para comenzar el juego:
							botones[x][y].atras();	
							
						}
						
					}
					
				}catch(Exception E){}
				
			}
			
		};
		
		//Inicialización del método del hilo:
		hilo.start();		
		
	}
	
	//Constructor:
	public MemoriaGUI(){
		
		//Asignación del título al Frame:
		super("Memoria");
		
		//Inicialización de los componentes:
		initGUI();	
		
		//Asignación del icono al JFrame:
		ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/icono.png"));
		setIconImage(icono.getImage());
		
		//Especificación de las características:
		setSize(700,700);
		setResizable(false);
		setLocationRelativeTo(null);		
		setVisible(true);
		pack();
		
	}
	
	//Creación de la escucha de los botones:	
	private class Escucha implements ActionListener{
		
		//Creación del método de la escucha:
		public void actionPerformed(ActionEvent evento){
			
			//Validación del click en el botón de ayuda:			
			if(ayuda == evento.getSource()){
				
				//Impresión del mensaje de ayuda:
				juego.ayudar();
				
			}
			
			else{
				
				//Obtención del botón pulsado:
				Casilla boton = (Casilla)evento.getSource();
				
				//Jugamos con el botón pulsado:
				juego.jugar(boton.getFila(), boton.getColumna());
				
			}		
			
		}
		
	}

}
