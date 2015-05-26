package prodAndes.cola;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.jboss.mq.SpyConnection;
import org.jboss.mq.SpyConnectionFactory;

import prodAndes.dao.ConsultaDAO;
import prodAndes.vos.ConsultaEtapaProduccion;
import prodAndes.vos.ProductoCantidad;

public class ColaProdAndes implements MessageListener {

	/**
	 * Clase que se encarga de hacer las consultas a la base de datos
	 */
	private ConsultaDAO dao;
	
	/**
	 * Datasource fuente de datos
	 */
	private DataSource ds;
	

	/**
	 * Conexion para la base de datos
	 */
	private Connection conn;
	
	/**
	 * Contexto inicial
	 */
	private InitialContext ctx;
	
	/**
	 * Fabrica de conexiones
	 */
	 private SpyConnectionFactory connectionFactory;
	 
	 /**
		 * Cola donde se postean los requests de envios que no se pueden suplir
		 * localmente
		 */
	private Queue colaRequests1;
	
	/**
	 * Cola de donde se reciben los replies de envios que no se pueden suplir
	 * localmente
	 */
	private Queue colaReplies1;
	
	private ArrayList dinamicos;
	
	/**
	 * Instancia de la fachada que se crea para que se use
	 */
	private static ColaProdAndes instance;
	
	
	/**
	 * Retorna la instancia de la clase principal
	 * 
	 * @return la instancia de la clase principal
	 */
	
	public static ColaProdAndes getInstance() {
		if (instance == null)
			instance = new ColaProdAndes();
		return instance;
	}
	
	/**
	 * Metodo constructor de la clase EntregAndes
	 */
	
	public  ColaProdAndes() {
		dinamicos = new ArrayList();
		dao = new ConsultaDAO();
		try {
			// Inicia el contexto seg�n la interfaz dada por JBOSS.
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY,
					"org.jnp.interfaces.NamingContextFactory");
			System.out.println("CONTEXT FACTORY");
			env.put(Context.PROVIDER_URL, "jnp://localhost:1099");
			env.put(Context.URL_PKG_PREFIXES,
					"org.jboss.naming:org.jnp.interfaces");
			System.out.println("LOCALHOST");
			ctx = new InitialContext(env);
			System.out.println("CTX     " + ctx);
			// Inicializa los datasource por JNDI.
			ds = (DataSource) ctx.lookup("java:XAProd");
			System.out.println("PASOOOOOO ");
			// Inicializa la fabrica de conexiones JMS.
			connectionFactory = (SpyConnectionFactory) ctx
					.lookup("ConnectionFactory");
			System.out.println("CONNECTION FACTORY");
			// inicializa la coneccion
			conn = connectionFactory.createConnection();
			System.out.println("HIZO CONN");

			// inicializa la sesion
			Session session = conn.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			System.out.println("SESSION ");
			// inicializa las colas
			colaRequests1 = (Queue) ctx.lookup("queue/Requests1");
			MessageConsumer consumer2 = session.createConsumer(colaRequests1);
			consumer2.setMessageListener(this);

			colaReplies1 = (Queue) ctx.lookup("queue/Replies1");
			MessageConsumer consumer = session.createConsumer(colaReplies1);
			consumer.setMessageListener(this);

			conn.start();
			System.out
					.println("ProdAndes - Se cargo el data source & la fabrica de conexiones de forma adecuada..");

		} catch (Exception e) {
			// Ocurrio un error y se imprimira por consola
			e.printStackTrace();
		}
	}
	
	public void registrarPedido(ArrayList<ProductoCantidad> productos, String idped, String cliente) throws Exception{
		dao.registrarPedidoProductos(productos, idped, cliente);
	}
	
	public String registrarPedidoEnvio() throws Exception{
		
		String resp = "";

		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

		MessageProducer producer = session.createProducer(colaRequests1);

		TextMessage txm = session.createTextMessage();
		
		// txm.setText("colaRequests1 - 1 - "+id+" - "+tipo);

		// producer.send(txm);
		
		System.out.println("ENVIO MENSAJE!!!!!");

		session.close();

		return resp;
	}
	
	public ArrayList<ConsultaEtapaProduccion> consultarEjecucionEtapas(String fechaInicio, String fechaFin) throws Exception{
		return dao.consultaEtapaProdccion2(fechaInicio, fechaFin);
	}
	
	public void respuestaExitosa(String tipo) throws Exception{
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

		MessageProducer producer = session.createProducer(colaRequests1);

		TextMessage txm = session.createTextMessage();

		txm.setText("respuesta1 - " + tipo + " - Solicitud exitosa");

		producer.send(txm);

		session.close();
	}
	
	
	
	@Override
	public void onMessage(Message m) {
		// TODO Auto-generated method stub
				try {

					TextMessage ms = (TextMessage) m;

					String linea = ms.getText();

					String args[] = linea.split(" - ");

					if (args[0].equals("consulta2")) {
						// Ver envios del usuario
						if (args[1].equals("1")) {					
							//registrarPedido(args[2], args[3], args[4], args[5]);
						}else if (args[1].equals("2")) {
							//cambiarEstado(args[2], args[3]);
						}else if (args[1].equals("3")) {
							//ArrayList<String> ejecuciones = consultarEjecucionDeEtapas(args[2], args[3], args[4]);
							//responderConEjecucionesDeEtapas(ejecuciones);
						}
					}else if (args[0].equals("respuesta2")) {
						// Ver envios del usuario

						if ( args[1].equals("1") || args[1].equals("2") ) {
							
						}else if( args[1].equals("3") ){
							String[] data = linea.substring( 16, ( linea.length() - 1 ) ).split("-");
							
							ArrayList<String> ejec = new ArrayList<String>();
							
							for (int i = 0; i < data.length; i++) {
								String data0 = data[i];
								ejec.add(data0);
								
//								//Numero del Pedido
//								String numPedido = data0[0];
//								//Codigo de la EstaciÛn
//								String codEstacion = data0[1];
//								//Numero de ejecuciones
//								String numEjecuciones = data0[2];
							}
						}
					}
				} catch (Exception e) {

				}
			}
	
//	public void cambiarEstado(String codEst, String estad) throws Exception {
//		mundo.cambiarEstado(codEst, estad);
//		respuestaExitosa("2");
//	}

	public String cambiarEstadoEnvio(String codEst, String estad) throws Exception {
		String resp = "";

		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

		MessageProducer producer = session.createProducer(colaRequests1);

		TextMessage txm = session.createTextMessage();

		txm.setText("consulta1 - 2 - " + codEst + " - " + estad);

		producer.send(txm);

		System.out.println("Mensaje enviado.");

		session.close();

		return resp;
	}

//	public ArrayList<String> consultarEjecucionDeEtapas( String parametro, String tipo, String tipoMetodo) throws SQLException{
//		if( tipoMetodo.equals("1") ){
//			return mundo.darEjecucionesPend(parametro, tipo, true);
//		}else{
//			return mundo.darEjecucionesPend(parametro, tipo, false);
//		}
//	}

	public void responderConEjecucionesDeEtapas( ArrayList<String> ejecuciones ) throws Exception {
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

		MessageProducer producer = session.createProducer(colaRequests1);

		TextMessage txm = session.createTextMessage();

		String resp = "";

		for (int i = 0; i < ejecuciones.size(); i++) {
			String[] data = ejecuciones.get(i).split("-");

			//Numero del Pedido: 
			resp += data[0];
			//Codigo de la EstaciÛn
			resp += ":" + data[1];
			//Numero de ejecuciones
			resp += ":" + data[2] + "-";
		}

		txm.setText("respuesta1 - 3 -" + resp);

		producer.send(txm);

		session.close();
	}

	
	
	}

	

