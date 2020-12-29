/********************************************
 * Christian Camilo Taborda Campi�o         *
 * C�digo: 1632081-3743                     *
 * Fecha de creaci�n: 04/03/2017            *
 * Fecha de �ltima modificaci�n: 14/03/2017 *
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
	
	//M�TODOS:	
	
	//Inicializa los componentes de la GUI:
	public void initGUI(){
		
		//Creaci�n del juego:
		juego = new Controlador();
		
		//Inicializaci�n del contenedor:
		contenedor = this.getContentPane();
		
		//Asignaci�n del Layout al contenedor:
		contenedor.setLayout(new BorderLayout());
		
		//Creaci�n del marco:
		marco = new JPanel();
		
		//Asignaci�n del Layout al marco:
		marco.setLayout(new GridLayout(DIMENSION,DIMENSION));
		
		//Asignaci�n de color al fondo del marco:
		marco.setBackground(Color.YELLOW);
		
		//Creaci�n de la escucha de los botones:
		Escucha escucha = new Escucha();
		
		//Inicializaci�n de los botones:
		botones = juego.getBotones();
		
		//Asignaci�n de la escucha y almacenamiento en el marco de los botones:
		for(int x=0; x<DIMENSION; x++){
			for(int y=0; y<DIMENSION; y++){
				botones[x][y].addActionListener(escucha);
				marco.add(botones[x][y]);
			}
		}
		
		//Almacenamiento del marco en el contenedor:
		contenedor.add(marco, BorderLayout.CENTER);
		
		//Creaci�n del panel del norte:
		norte = new JPanel();
		
		//Asignaci�n del Layout y el color de fondo al panel del norte:
		norte.setLayout(new FlowLayout());
		norte.setBackground(Color.YELLOW);
		
		//Creaci�n del t�tulo:
		titulo = new JLabel("�Encuentra las parejas!           ");
		
		//Inicializaci�n de la fuente para el t�tulo y el bot�n de ayuda:
		Font fuente = new Font("Arial", Font.BOLD, 35);
		
		//Especificaci�n de las caracter�sticas del t�tulo:
		titulo.setOpaque(true);
		titulo.setFont(fuente);
		titulo.setForeground(Color.RED);
		titulo.setBackground(Color.YELLOW);
		titulo.setHorizontalAlignment(JLabel.CENTER);
		
		//Almacenamiento del t�tulo en el panel del norte:
		norte.add(titulo);
		
		//Inicializaci�n del bot�n de ayuda:
		ayuda = new JButton("?");
		ayuda.setFont(fuente);
		ayuda.setForeground(Color.YELLOW);
		ayuda.setBackground(Color.RED);
		ayuda.setOpaque(true);
		ayuda.setFocusPainted(false);
		ayuda.addActionListener(escucha);
		
		//Almacenamiento del bot�n de ayuda en el panel del norte:
		norte.add(ayuda);
		
		//Almacenamiento del panel del norte en el contenedor:
		contenedor.add(norte, BorderLayout.NORTH);
		
		//Creaci�n del hilo para los efectos del inicio de la aplicaci�n:
		Thread hilo = new Thread(){
			
			//Creaci�n del m�todo del hilo:
			public synchronized void run(){
				
				//Creaci�n de la estructura Try-Catch:
				try{
					
					//Tiempo de espera antes de mostrar las im�genes:
					sleep(800);
					
					//Giro de las casillas para la visualizaci�n del usuario:
					for(int x=0; x<DIMENSION; x++){
						for(int y=0; y<DIMENSION; y++){
							
							//Tiempo de espera para mostrar cada imagen:
							sleep(50);
							
							//Giro de la casilla:
							botones[x][y].frente();
							
						}
					}
					
					//Tiempo de espera de la visualizaci�n:
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
		
		//Inicializaci�n del m�todo del hilo:
		hilo.start();		
		
	}
	
	//Constructor:
	public MemoriaGUI(){
		
		//Asignaci�n del t�tulo al Frame:
		super("Memoria");
		
		//Inicializaci�n de los componentes:
		initGUI();	
		
		//Asignaci�n del icono al JFrame:
		ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/icono.png"));
		setIconImage(icono.getImage());
		
		//Especificaci�n de las caracter�sticas:
		setSize(700,700);
		setResizable(false);
		setLocationRelativeTo(null);		
		setVisible(true);
		pack();
		
	}
	
	//Creaci�n de la escucha de los botones:	
	private class Escucha implements ActionListener{
		
		//Creaci�n del m�todo de la escucha:
		public void actionPerformed(ActionEvent evento){
			
			//Validaci�n del click en el bot�n de ayuda:			
			if(ayuda == evento.getSource()){
				
				//Impresi�n del mensaje de ayuda:
				juego.ayudar();
				
			}
			
			else{
				
				//Obtenci�n del bot�n pulsado:
				Casilla boton = (Casilla)evento.getSource();
				
				//Jugamos con el bot�n pulsado:
				juego.jugar(boton.getFila(), boton.getColumna());
				
			}		
			
		}
		
	}

}
