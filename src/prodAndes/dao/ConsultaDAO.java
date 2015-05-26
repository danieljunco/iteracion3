package prodAndes.dao;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import prodAndes.vos.Cliente;
import prodAndes.vos.Componente;
import prodAndes.vos.ConsultaComponente;
import prodAndes.vos.ConsultaEtapaProduccion;
import prodAndes.vos.ConsultaMateria;
import prodAndes.vos.ConsultaProducto;
import prodAndes.vos.EstacionProduccion;
import prodAndes.vos.EtapaProduccion;
import prodAndes.vos.MateriaPrima;
import prodAndes.vos.PedidoCliente;
import prodAndes.vos.PedidoMaterial2;
import prodAndes.vos.PedidoProveedor;
import prodAndes.vos.Pedidos2;
import prodAndes.vos.Producto;
import prodAndes.vos.ProductoCantidad;
import prodAndes.vos.Proveedor;
import prodAndes.vos.RFC13;
import prodAndes.vos.ReqPedidoCliente;
import prodAndes.vos.ReqPedidoProveedor;



public class ConsultaDAO {
	
	// ----------------------------------------------------
	// Constantes
	// ----------------------------------------------------
	
		/**
		 * ruta donde se encuentra el archivo de conexion.
		 */
	private final static String RUTA_DB = "jdbc:oracle:thin:@prod.oracle.virtual.uniandes.edu.co:1531:prod";
	
	private final static String USER_DB = "ISIS2304221510";
	
	private final static String PASS_DB = "hminskjesse";
	
	private final static String DRIVER_DB = "oracle.jdbc.driver.OracleDriver";
	
	//---------------------------------------------------------------
	//Atributos
	//---------------------------------------------------------------
		
		/**
		 * conexion con la base de datos
		 */
		public Connection conexion;

		/**
		 * nombre del usuario para conectarse a la base de datos.
		 */
		public String usuario;

		/**
		 * clave de conexion a la base de datos.
		 */
		public String pass;

		/**
		 * URL al cual se debe conectar para acceder a la base de datos.
		 */
		public String URLConexion;
		
		public ConsultaDAO()
		{
			
		}
		/**
		 * obtiene ls datos necesarios para establecer una conexion
		 * Los datos se obtienen a partir de un archivo properties.
		 * @param path ruta donde se encuentra el archivo properties.
		 */
		public void inicializar()
		{
			try
			{
				URLConexion = RUTA_DB;	
				usuario = USER_DB;	
				pass = PASS_DB;	
				final String driver = DRIVER_DB;
				Class.forName(driver);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}	
		}
		
		/**
		 * 
		 * @param url
		 * @param usuario
		 * @param clave
		 * @throws SQLException
		 */
		public void establecerConexion(String url, String usuario, String clave) throws SQLException
		{
			try
			{
				conexion = DriverManager.getConnection(url,usuario,clave);
			}
			catch( SQLException exception )
			{
				throw new SQLException( "ERROR: ConsultaDAO obteniendo una conexin." );
			}
		}
	
		/**
		 * 
		 * @param connection
		 * @throws Exception
		 */
		public void closeConnection(Connection connection) throws Exception {        
			try {
				connection.close();
				connection = null;
			} catch (SQLException exception) {
				throw new Exception("ERROR: ConsultaDAO: closeConnection() = cerrando una conexion.");
			}
		}
		

		public ArrayList<RFC13> consultarMaterialesMasUtilizados() throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<RFC13> cuentas = new ArrayList<RFC13>();
			RFC13 cuenta = new RFC13();
			
			try {
				prepStmt = conexion.prepareStatement("select id_mat, cuenta from (select id_mat, count(id_mat) as cuenta from( select codigo, id_mat, y.CANTIDAD from ETAPAS_PRODUCCION x inner join COMPUESTO_DE_COMP y on x.id_comp=y.id_comp) group by id_mat) where cuenta = (select max(cuenta) from (select id_mat, count(id_mat) as cuenta from( select codigo, id_mat, y.CANTIDAD from ETAPAS_PRODUCCION x inner join COMPUESTO_DE_COMP y on x.id_comp=y.id_comp) group by id_mat))" );
				ResultSet rs = prepStmt.executeQuery();
				while (rs.next()) {
					String idMaterial = rs.getString("ID_MAT");
					int count = rs.getInt("CUENTA");
					cuenta.setIdMaterial(idMaterial);
					cuenta.setCuenta(count);
					cuentas.add(cuenta);
					cuenta = new RFC13();
					}

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				if(prepStmt!=null){
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return cuentas;
			
		}
		
		public ArrayList<ConsultaEtapaProduccion> consultaEtapaProdccion1ConFiltro(String fechaInicio, String fechaFin, String iFiltro, String filtro) throws Exception {
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<ConsultaEtapaProduccion> etapas = new ArrayList<ConsultaEtapaProduccion>();
			ConsultaEtapaProduccion etapa = new ConsultaEtapaProduccion();
			try {
				SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd");
				
				Calendar c1 = Calendar.getInstance();
				c1.setTime(df.parse(fechaInicio));
				
				String month1 = "";
				
				if ((c1.get(Calendar.MONTH)+1) < 10 )
					month1 = "0"+(c1.get(Calendar.MONTH)+1);
				else 
					month1 = ""+(c1.get(Calendar.MONTH)+1);
				
				Calendar c2 = Calendar.getInstance();
				c2.setTime(df.parse(fechaFin));
				
				String month2 = "";
				
				if ((c2.get(Calendar.MONTH)+1) < 10 )
					month2 = "0"+(c2.get(Calendar.MONTH)+1);
				else 
					month2 = ""+(c2.get(Calendar.MONTH)+1);
				
				if(iFiltro==null && filtro ==null){
					prepStmt = conexion.prepareStatement("select id_pedido, id_mat, id_producto,"
							+ " cantidad, id_etapa, costo_unitario, fecha_inicio, fecha_fin "
							+ " from CONTENIDO_PEDIDO cp "
							+ " right join COMPUESTO_DE_PROD cdp on cp.id_producto=cdp.id_producto "
							+ " right join PRODUCTOS p on p.CODIGO=cp.ID_PRODUCTO "
							+ " right join COMPUESTO_DE_COMP cc on cdp.ID_COMPONENTE=cc.ID_COMP "
							+ " right join ETAPAS_PRODUCCION ep on ep.ID_PRODUCTO=cp.id_producto "
							+ " right join EJECUCION_ETAPA ee on ee.ID_ETAPA=ep.CODIGO "
							+ " intersect  select id_pedido, id_mat, id_producto, cantidad, id_etapa, costo_unitario, fecha_inicio, fecha_fin "
							+ " from CONTENIDO_PEDIDO cp1 "
							+ " left join COMPUESTO_DE_PROD cdp1 on cp1.id_producto=cdp1.id_producto "
							+ " left join PRODUCTOS p1 on p1.CODIGO=cp1.ID_PRODUCTO "
							+ " left join COMPUESTO_DE_COMP cc1 on cdp1.ID_COMPONENTE=cc1.ID_COMP "
							+ " left join ETAPAS_PRODUCCION ep1 on ep1.ID_PRODUCTO=cp1.id_producto "
							+ " left join EJECUCION_ETAPA ee1 on ee1.ID_ETAPA=ep1.CODIGO");
				}
				
				else if(fechaInicio==null && fechaFin==null && iFiltro==null && filtro == null){
					prepStmt = conexion.prepareStatement("select id_pedido, id_mat, id_producto,"
							+ " cantidad, id_etapa, costo_unitario, fecha_inicio, fecha_fin "
							+ " from CONTENIDO_PEDIDO cp "
							+ " right join COMPUESTO_DE_PROD cdp on cp.id_producto=cdp.id_producto "
							+ " right join PRODUCTOS p on p.CODIGO=cp.ID_PRODUCTO "
							+ " right join COMPUESTO_DE_COMP cc on cdp.ID_COMPONENTE=cc.ID_COMP "
							+ " right join ETAPAS_PRODUCCION ep on ep.ID_PRODUCTO=cp.id_producto "
							+ " right join EJECUCION_ETAPA ee on ee.ID_ETAPA=ep.CODIGO "
							+ " intersect  select id_pedido, id_mat, id_producto, cantidad, id_etapa, costo_unitario, fecha_inicio, fecha_fin "
							+ " from CONTENIDO_PEDIDO cp1 "
							+ " left join COMPUESTO_DE_PROD cdp1 on cp1.id_producto=cdp1.id_producto "
							+ " left join PRODUCTOS p1 on p1.CODIGO=cp1.ID_PRODUCTO "
							+ " left join COMPUESTO_DE_COMP cc1 on cdp1.ID_COMPONENTE=cc1.ID_COMP "
							+ " left join ETAPAS_PRODUCCION ep1 on ep1.ID_PRODUCTO=cp1.id_producto "
							+ " left join EJECUCION_ETAPA ee1 on ee1.ID_ETAPA=ep1.CODIGO");
				}
				
				else if(iFiltro.equals("idPedido")){
					prepStmt = conexion.prepareStatement("select id_pedido, id_mat, id_producto,"
							+ " cantidad, id_etapa, costo_unitario, fecha_inicio, fecha_fin "
							+ " from CONTENIDO_PEDIDO cp "
							+ " right join COMPUESTO_DE_PROD cdp on cp.id_producto=cdp.id_producto "
							+ " right join PRODUCTOS p on p.CODIGO=cp.ID_PRODUCTO "
							+ " right join COMPUESTO_DE_COMP cc on cdp.ID_COMPONENTE=cc.ID_COMP "
							+ " right join ETAPAS_PRODUCCION ep on ep.ID_PRODUCTO=cp.id_producto "
							+ " right join EJECUCION_ETAPA ee on ee.ID_ETAPA=ep.CODIGO "
							+ " intersect  select id_pedido, id_mat, id_producto, cantidad, id_etapa, costo_unitario, fecha_inicio, fecha_fin "
							+ " from CONTENIDO_PEDIDO cp1 "
							+ " left join COMPUESTO_DE_PROD cdp1 on cp1.id_producto=cdp1.id_producto "
							+ " left join PRODUCTOS p1 on p1.CODIGO=cp1.ID_PRODUCTO "
							+ " left join COMPUESTO_DE_COMP cc1 on cdp1.ID_COMPONENTE=cc1.ID_COMP "
							+ " left join ETAPAS_PRODUCCION ep1 on ep1.ID_PRODUCTO=cp1.id_producto "
							+ " left join EJECUCION_ETAPA ee1 on ee1.ID_ETAPA=ep1.CODIGO where fecha_inicio >= TO_DATE('" + c1.get(Calendar.DAY_OF_MONTH) + "/" + month1 + "/" + c1.get(Calendar.YEAR) +"', 'DD-MM-YY') and fecha_fin <= TO_DATE('" + c2.get(Calendar.DAY_OF_MONTH) + "/" + month2 + "/" + c2.get(Calendar.YEAR) +"', 'DD-MM-YY') and id_pedido='"+filtro+"' ");
				}
				else if (iFiltro.equals("idMaterial")){
					prepStmt = conexion.prepareStatement("select id_pedido, id_mat, id_producto,"
							+ " cantidad, id_etapa, costo_unitario, fecha_inicio, fecha_fin "
							+ " from CONTENIDO_PEDIDO cp "
							+ " right join COMPUESTO_DE_PROD cdp on cp.id_producto=cdp.id_producto "
							+ " right join PRODUCTOS p on p.CODIGO=cp.ID_PRODUCTO "
							+ " right join COMPUESTO_DE_COMP cc on cdp.ID_COMPONENTE=cc.ID_COMP "
							+ " right join ETAPAS_PRODUCCION ep on ep.ID_PRODUCTO=cp.id_producto "
							+ " right join EJECUCION_ETAPA ee on ee.ID_ETAPA=ep.CODIGO "
							+ " intersect  select id_pedido, id_mat, id_producto, cantidad, id_etapa, costo_unitario, fecha_inicio, fecha_fin "
							+ " from CONTENIDO_PEDIDO cp1 "
							+ " left join COMPUESTO_DE_PROD cdp1 on cp1.id_producto=cdp1.id_producto "
							+ " left join PRODUCTOS p1 on p1.CODIGO=cp1.ID_PRODUCTO "
							+ " left join COMPUESTO_DE_COMP cc1 on cdp1.ID_COMPONENTE=cc1.ID_COMP "
							+ " left join ETAPAS_PRODUCCION ep1 on ep1.ID_PRODUCTO=cp1.id_producto "
							+ " left join EJECUCION_ETAPA ee1 on ee1.ID_ETAPA=ep1.CODIGO where fecha_inicio >= TO_DATE('" + c1.get(Calendar.DAY_OF_MONTH) + "/" + month1 + "/" + c1.get(Calendar.YEAR) +"', 'DD-MM-YY') and fecha_fin <= TO_DATE('" + c2.get(Calendar.DAY_OF_MONTH) + "/" + month2 + "/" + c2.get(Calendar.YEAR) +"', 'DD-MM-YY') and id_mat='"+filtro+"' ");
				}
				else if(iFiltro.equals("idProducto")){
					prepStmt = conexion.prepareStatement("select id_pedido, id_mat, id_producto,"
							+ " cantidad, id_etapa, costo_unitario, fecha_inicio, fecha_fin "
							+ " from CONTENIDO_PEDIDO cp "
							+ " right join COMPUESTO_DE_PROD cdp on cp.id_producto=cdp.id_producto "
							+ " right join PRODUCTOS p on p.CODIGO=cp.ID_PRODUCTO "
							+ " right join COMPUESTO_DE_COMP cc on cdp.ID_COMPONENTE=cc.ID_COMP "
							+ " right join ETAPAS_PRODUCCION ep on ep.ID_PRODUCTO=cp.id_producto "
							+ " right join EJECUCION_ETAPA ee on ee.ID_ETAPA=ep.CODIGO "
							+ " intersect  select id_pedido, id_mat, id_producto, cantidad, id_etapa, costo_unitario, fecha_inicio, fecha_fin "
							+ " from CONTENIDO_PEDIDO cp1 "
							+ " left join COMPUESTO_DE_PROD cdp1 on cp1.id_producto=cdp1.id_producto "
							+ " left join PRODUCTOS p1 on p1.CODIGO=cp1.ID_PRODUCTO "
							+ " left join COMPUESTO_DE_COMP cc1 on cdp1.ID_COMPONENTE=cc1.ID_COMP "
							+ " left join ETAPAS_PRODUCCION ep1 on ep1.ID_PRODUCTO=cp1.id_producto "
							+ " left join EJECUCION_ETAPA ee1 on ee1.ID_ETAPA=ep1.CODIGO where fecha_inicio >= TO_DATE('" + c1.get(Calendar.DAY_OF_MONTH) + "/" + month1 + "/" + c1.get(Calendar.YEAR) +"', 'DD-MM-YY') and fecha_fin <= TO_DATE('" + c2.get(Calendar.DAY_OF_MONTH) + "/" + month2 + "/" + c2.get(Calendar.YEAR) +"', 'DD-MM-YY') and id_producto='"+filtro+"' ");
				}
				else if(iFiltro.equals("cantidad")){
					prepStmt = conexion.prepareStatement("select id_pedido, id_mat, id_producto,"
							+ " cantidad, id_etapa, costo_unitario, fecha_inicio, fecha_fin "
							+ " from CONTENIDO_PEDIDO cp "
							+ " right join COMPUESTO_DE_PROD cdp on cp.id_producto=cdp.id_producto "
							+ " right join PRODUCTOS p on p.CODIGO=cp.ID_PRODUCTO "
							+ " right join COMPUESTO_DE_COMP cc on cdp.ID_COMPONENTE=cc.ID_COMP "
							+ " right join ETAPAS_PRODUCCION ep on ep.ID_PRODUCTO=cp.id_producto "
							+ " right join EJECUCION_ETAPA ee on ee.ID_ETAPA=ep.CODIGO "
							+ " intersect  select id_pedido, id_mat, id_producto, cantidad, id_etapa, costo_unitario, fecha_inicio, fecha_fin "
							+ " from CONTENIDO_PEDIDO cp1 "
							+ " left join COMPUESTO_DE_PROD cdp1 on cp1.id_producto=cdp1.id_producto "
							+ " left join PRODUCTOS p1 on p1.CODIGO=cp1.ID_PRODUCTO "
							+ " left join COMPUESTO_DE_COMP cc1 on cdp1.ID_COMPONENTE=cc1.ID_COMP "
							+ " left join ETAPAS_PRODUCCION ep1 on ep1.ID_PRODUCTO=cp1.id_producto "
							+ " left join EJECUCION_ETAPA ee1 on ee1.ID_ETAPA=ep1.CODIGO where fecha_inicio >= TO_DATE('" + c1.get(Calendar.DAY_OF_MONTH) + "/" + month1 + "/" + c1.get(Calendar.YEAR) +"', 'DD-MM-YY') and fecha_fin <= TO_DATE('" + c2.get(Calendar.DAY_OF_MONTH) + "/" + month2 + "/" + c2.get(Calendar.YEAR) +"', 'DD-MM-YY') and cantidad='"+filtro+"' ");
				}
				else if(iFiltro.equals("idEtapa")){
					prepStmt = conexion.prepareStatement("select id_pedido, id_mat, id_producto,"
							+ " cantidad, id_etapa, costo_unitario, fecha_inicio, fecha_fin "
							+ " from CONTENIDO_PEDIDO cp "
							+ " right join COMPUESTO_DE_PROD cdp on cp.id_producto=cdp.id_producto "
							+ " right join PRODUCTOS p on p.CODIGO=cp.ID_PRODUCTO "
							+ " right join COMPUESTO_DE_COMP cc on cdp.ID_COMPONENTE=cc.ID_COMP "
							+ " right join ETAPAS_PRODUCCION ep on ep.ID_PRODUCTO=cp.id_producto "
							+ " right join EJECUCION_ETAPA ee on ee.ID_ETAPA=ep.CODIGO "
							+ " intersect  select id_pedido, id_mat, id_producto, cantidad, id_etapa, costo_unitario, fecha_inicio, fecha_fin "
							+ " from CONTENIDO_PEDIDO cp1 "
							+ " left join COMPUESTO_DE_PROD cdp1 on cp1.id_producto=cdp1.id_producto "
							+ " left join PRODUCTOS p1 on p1.CODIGO=cp1.ID_PRODUCTO "
							+ " left join COMPUESTO_DE_COMP cc1 on cdp1.ID_COMPONENTE=cc1.ID_COMP "
							+ " left join ETAPAS_PRODUCCION ep1 on ep1.ID_PRODUCTO=cp1.id_producto "
							+ " left join EJECUCION_ETAPA ee1 on ee1.ID_ETAPA=ep1.CODIGO where fecha_inicio >= TO_DATE('" + c1.get(Calendar.DAY_OF_MONTH) + "/" + month1 + "/" + c1.get(Calendar.YEAR) +"', 'DD-MM-YY') and fecha_fin <= TO_DATE('" + c2.get(Calendar.DAY_OF_MONTH) + "/" + month2 + "/" + c2.get(Calendar.YEAR) +"', 'DD-MM-YY') and id_etapa='"+filtro+"' ");
				}
				
				
				ResultSet rs = prepStmt.executeQuery();
				
				
				while (rs.next()) {
					
					String idPedido = rs.getString("ID_PEDIDO");
					String idMaterial = rs.getString("ID_MAT");
					String idProducto = rs.getString("ID_PRODUCTO");
					int cantidad = rs.getInt("CANTIDAD");
					String idEtapa = rs.getString("ID_ETAPA");
					int costo = rs.getInt("COSTO_UNITARIO");
					Date fechaInicio2 = rs.getDate("FECHA_INICIO");
					Date fechaFin2 = rs.getDate("FECHA_FIN");
					
					
					int precioFinal = cantidad * costo;
					
					etapa.setIdPedido(idPedido);
					etapa.setIdMaterial(idMaterial);
					etapa.setIdProducto(idProducto);
					etapa.setCantidad(cantidad);
					etapa.setIdEtapa(idEtapa);
					etapa.setCostoFinal(precioFinal);
					etapa.setFechaInicio(fechaInicio2);
					etapa.setFechaFin(fechaFin2);
					
					etapas.add(etapa);
					
					etapa = new ConsultaEtapaProduccion();
					precioFinal = 0;
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if (prepStmt!=null) {
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return etapas;
		}
		
		public ArrayList<String> consultaEtapaProdccion2Material(String fechaInicio, String fechaFin) throws Exception {
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<String> etapas = new ArrayList<String>();
			try {
				SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd");
				
				Calendar c1 = Calendar.getInstance();
				c1.setTime(df.parse(fechaInicio));
				
				String month1 = "";
				
				if ((c1.get(Calendar.MONTH)+1) < 10 )
					month1 = "0"+(c1.get(Calendar.MONTH)+1);
				else 
					month1 = ""+(c1.get(Calendar.MONTH)+1);
				
				Calendar c2 = Calendar.getInstance();
				c2.setTime(df.parse(fechaFin));
				
				String month2 = "";
				
				if ((c2.get(Calendar.MONTH)+1) < 10 )
					month2 = "0"+(c2.get(Calendar.MONTH)+1);
				else 
					month2 = ""+(c2.get(Calendar.MONTH)+1);
				
				prepStmt = conexion.prepareStatement("select codigo as codEtapa, descripcion, fecha_inicio, fecha_fin from ETAPAS_PRODUCCION right join EJECUCION_ETAPA on EJECUCION_ETAPA.ID_ETAPA=ETAPAS_PRODUCCION.CODIGO intersect select codigo as codEtapa, descripcion, fecha_inicio, fecha_fin from ETAPAS_PRODUCCION left join EJECUCION_ETAPA on EJECUCION_ETAPA.ID_ETAPA=ETAPAS_PRODUCCION.CODIGO order by ");
				ResultSet rs = prepStmt.executeQuery();
				
				while (rs.next()) {
					
					String idMaterial = rs.getString("ID_MAT");
					
					
					etapas.add(idMaterial);
					
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if (prepStmt!=null) {
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return etapas;
		}
		
		public ArrayList<ConsultaEtapaProduccion> consultaEtapaProdccion2(String fechaInicio, String fechaFin) throws Exception {
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<ConsultaEtapaProduccion> etapas = new ArrayList<ConsultaEtapaProduccion>();
			ConsultaEtapaProduccion etapa = new ConsultaEtapaProduccion();
			try {
				SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd");
				
				Calendar c1 = Calendar.getInstance();
				c1.setTime(df.parse(fechaInicio));
				
				String month1 = "";
				
				if ((c1.get(Calendar.MONTH)+1) < 10 )
					month1 = "0"+(c1.get(Calendar.MONTH)+1);
				else 
					month1 = ""+(c1.get(Calendar.MONTH)+1);
				
				Calendar c2 = Calendar.getInstance();
				c2.setTime(df.parse(fechaFin));
				
				String month2 = "";
				
				if ((c2.get(Calendar.MONTH)+1) < 10 )
					month2 = "0"+(c2.get(Calendar.MONTH)+1);
				else 
					month2 = ""+(c2.get(Calendar.MONTH)+1);
				
				prepStmt = conexion.prepareStatement("select codigo as codEtapa, descripcion, fecha_inicio, fecha_fin from ETAPAS_PRODUCCION right join EJECUCION_ETAPA on EJECUCION_ETAPA.ID_ETAPA=ETAPAS_PRODUCCION.CODIGO intersect select codigo as codEtapa, descripcion, fecha_inicio, fecha_fin from ETAPAS_PRODUCCION left join EJECUCION_ETAPA on EJECUCION_ETAPA.ID_ETAPA=ETAPAS_PRODUCCION.CODIGO where fecha_inicio >= TO_DATE('" + c1.get(Calendar.DAY_OF_MONTH) + "/" + month1 + "/" + c1.get(Calendar.YEAR) +"', 'DD-MM-YY') and fecha_fin <= TO_DATE('" + c2.get(Calendar.DAY_OF_MONTH) + "/" + month2 + "/" + c2.get(Calendar.YEAR) +"', 'DD-MM-YY')");

				ResultSet rs = prepStmt.executeQuery();
				
				while (rs.next()) {
					
					String idEtapa = rs.getString("CODETAPA");
					String nombre = rs.getString("DESCRIPCION");
					Date fechaInicio2 = rs.getDate("FECHA_INICIO");
					Date fechaFin2 = rs.getDate("FECHA_FIN");
					
					etapa.setIdEtapa(idEtapa);
					etapa.setDescripcion(nombre);
					etapa.setFechaInicio(fechaInicio2);
					etapa.setFechaFin(fechaFin2);
					
					etapas.add(etapa);
					
					etapa = new ConsultaEtapaProduccion();
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if (prepStmt!=null) {
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return etapas;
		}
		
		public ArrayList<String> consultarNombreMaterialPedidos2() throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<String> nombres = new ArrayList<String>();
			try {
				prepStmt = conexion.prepareStatement("select distinct codMaterial from(select id_pedido, codMaterial, codProducto,"
						+ " cantFinal, costo_unitario from productos join (select codMaterial, "
						+ "id_producto as codProducto, id_pedido, cantFinal from "
						+ " compuesto_de_prod inner join (select id_mat as codMaterial, "
						+ " id_pedido, codComponente, cantFinal from (select id_pedido, "
						+ " id_comp as codComponente, cantidad as cantFinal from contenido_pedido_mat) "
						+ " left join (select id_comp as comp, id_mat from compuesto_de_comp) "
						+ " on codComponente = comp intersect select id_mat as codMaterial, id_pedido, "
						+ " codComponente, cantFinal from (select id_pedido, id_comp as codComponente, "
						+ " cantidad as cantFinal from contenido_pedido_mat) right join "
						+ " (select id_comp as comp, id_mat from compuesto_de_comp) on "
						+ " codComponente = comp) on codComponente = compuesto_de_prod.ID_COMPONENTE) "
						+ " on codProducto=productos.CODIGO ) order by codMaterial" );
				ResultSet rs = prepStmt.executeQuery();
				while (rs.next()) {
					String nombre = rs.getString("CODMATERIAL");
					nombres.add(nombre);
					}

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				if(prepStmt!=null){
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return nombres;
		}
		
		public ArrayList<Pedidos2> consultarPedidos2(String tipoMaterial, int costo ) throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<Pedidos2> pedidos = new ArrayList<Pedidos2>();
			Pedidos2 pedido = new Pedidos2();
			
			try {
				prepStmt = conexion.prepareStatement("select id_pedido, codMaterial, codProducto,"
						+ " cantFinal, costo_unitario from productos join (select codMaterial, "
						+ "id_producto as codProducto, id_pedido, cantFinal from "
						+ " compuesto_de_prod inner join (select id_mat as codMaterial, "
						+ " id_pedido, codComponente, cantFinal from (select id_pedido, "
						+ " id_comp as codComponente, cantidad as cantFinal from contenido_pedido_mat) "
						+ " left join (select id_comp as comp, id_mat from compuesto_de_comp) "
						+ " on codComponente = comp intersect select id_mat as codMaterial, id_pedido, "
						+ " codComponente, cantFinal from (select id_pedido, id_comp as codComponente, "
						+ " cantidad as cantFinal from contenido_pedido_mat) right join "
						+ " (select id_comp as comp, id_mat from compuesto_de_comp) on "
						+ " codComponente = comp) on codComponente = compuesto_de_prod.ID_COMPONENTE) "
						+ " on codProducto=productos.CODIGO  where codMaterial='"+tipoMaterial+"' " );
				ResultSet rs = prepStmt.executeQuery();
				while (rs.next()) {
					String idPedido = rs.getString("ID_PEDIDO");
					String codMaterial = rs.getString("CODMATERIAL");
					String codProducto = rs.getString("CODPRODUCTO");
					int cantidad = rs.getInt("CANTFINAL");
					int costoUnitario = rs.getInt("COSTO_UNITARIO");
					
					int costoTotal = costoUnitario * cantidad;
					
					if( costoTotal >= costo){
					
					pedido.setIdPedido(idPedido);
					pedido.setIdMaterial(codMaterial);
					pedido.setIdProducto(codProducto);
					pedido.setCantidad(cantidad);
					pedido.setCosto(costoTotal);
					
					pedidos.add(pedido);
					pedido = new Pedidos2();
					costoTotal = 0;
					}
					
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				if(prepStmt!=null){
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return pedidos;
		}
		
		public ArrayList<EstacionProduccion> darEstacionesDeProduccion() throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<EstacionProduccion> estaciones = new ArrayList<EstacionProduccion>();
			EstacionProduccion estacion = new EstacionProduccion();
			try {
				prepStmt = conexion.prepareStatement("select codigo, capacidad, estado, ocupacion_actual from estaciones_produccion");
				ResultSet rs = prepStmt.executeQuery();
				
				while (rs.next()) {
					String id = rs.getString("CODIGO");
					int capacidad = rs.getInt("CAPACIDAD");
					String estado = rs.getString("ESTADO");
					int ocupacionActual = rs.getInt("OCUPACION_ACTUAL");
					
					estacion.setCodigo(id);
					estacion.setCapacidad(capacidad);
					estacion.setEstado(estado);
					estacion.setOcupacion_actual(ocupacionActual);
					
					estaciones.add(estacion);
					
					estacion = new EstacionProduccion();	
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if (prepStmt!=null) {
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return estaciones;
			
		}
		
		//private void extraerEtapas(String idEstacion,  )
		
		public ArrayList<String> consultarIdMaterial () throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<String> idsMaterial = new ArrayList<String>();
			
			try {
				prepStmt = conexion.prepareStatement("select distinct idMat from (select codigo, "
						+ " id_producto, num_etapa, codPedido, email_cliente, fecha_pedido, "
						+ " fecha_entrega, estado, idComp, cant, idMat from etapas_produccion "
						+ " inner join (select codPedido, email_cliente, fecha_pedido, "
						+ " fecha_entrega, estado, idComp, cant, id_mat as idMat "
						+ " from compuesto_de_comp inner join (select codPedido, email_cliente, "
						+ " fecha_pedido, fecha_entrega, estado, idComp, cant from "
						+ " (select id_pedido as codPedido, email_cliente, fecha_pedido, "
						+ " fecha_entrega, estado from pedidos_clientes) left join "
						+ " (select id_pedido as idPedido, id_comp as idComp, cantidad as "
						+ " cant from contenido_pedido_mat) on codPedido=idPedido intersect "
						+ " select codPedido, email_cliente, fecha_pedido, fecha_entrega, estado, "
						+ " idComp, cant from (select id_pedido as codPedido, email_cliente, "
						+ " fecha_pedido, fecha_entrega, estado from pedidos_clientes) right join "
						+ " (select id_pedido as idPedido, id_comp as idComp, cantidad as cant from "
						+ " contenido_pedido_mat) on codPedido=idPedido) on "
						+ " idComp=compuesto_de_comp.ID_COMP) on idComp=etapas_produccion.ID_COMP) order by idMat" );
				ResultSet rs = prepStmt.executeQuery();
				while (rs.next()) {
					String idMat = rs.getString("IDMAT");
					idsMaterial.add(idMat);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				if(prepStmt!=null){
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return idsMaterial;
		}
		
		public ArrayList<PedidoMaterial2> consultarMaterial2(String identificadorMaterial) throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<PedidoMaterial2> pedidosMaterial = new ArrayList<PedidoMaterial2>();
			PedidoMaterial2 pedidoMaterial = new PedidoMaterial2();
			
			try {
				prepStmt = conexion.prepareStatement("select codigo, id_producto, "
						+ " codPedido, email_cliente, fecha_pedido, fecha_entrega, estado,"
						+ " idComp, cant, idMat from etapas_produccion inner join"
						+ " (select codPedido, email_cliente, fecha_pedido, fecha_entrega, "
						+ "estado, idComp, cant, id_mat as idMat from compuesto_de_comp inner join "
						+ "(select codPedido, email_cliente, fecha_pedido, fecha_entrega, estado, idComp,"
						+ " cant from (select id_pedido as codPedido, email_cliente, fecha_pedido, "
						+ " fecha_entrega, estado from pedidos_clientes)"
						+ " left join (select id_pedido as idPedido, id_comp as idComp, cantidad as cant from contenido_pedido_mat) "
						+ " on codPedido=idPedido intersect select codPedido, email_cliente, fecha_pedido, fecha_entrega, estado, idComp, cant from "
						+ " (select id_pedido as codPedido, email_cliente, fecha_pedido, fecha_entrega, estado from pedidos_clientes) "
						+ " right join (select id_pedido as idPedido, id_comp as idComp, cantidad as cant from contenido_pedido_mat) on codPedido=idPedido) "
						+ " on idComp=compuesto_de_comp.ID_COMP) on idComp=etapas_produccion.ID_COMP where idMat='"+identificadorMaterial+"'" );
				ResultSet rs = prepStmt.executeQuery();
				while (rs.next()) {
					
					String codEtapa = rs.getString("CODIGO");
					String idProducto = rs.getString("ID_PRODUCTO");
					String codPedido = rs.getString("CODPEDIDO");
					String emailCliente = rs.getString("EMAIL_CLIENTE"); 
					Date fechaPedido = rs.getDate("FECHA_PEDIDO");
					Date fechaEntrega = rs.getDate("FECHA_ENTREGA");
					String estado = rs.getString("ESTADO");
					String idComponente = rs.getString("IDCOMP");
					int cantidad = rs.getInt("CANT");
					String idMaterial = rs.getString("IDMAT");
					
					
					pedidoMaterial.setCodEtapa(codEtapa);
					pedidoMaterial.setIdProducto(idProducto);
					pedidoMaterial.setIdPedido(codPedido);
					pedidoMaterial.setEmailCliente(emailCliente);
					pedidoMaterial.setFechaPedido(fechaPedido);
					pedidoMaterial.setFechaEntrega(fechaEntrega);
					pedidoMaterial.setEstado(estado);
					pedidoMaterial.setIdComponente(idComponente);
					pedidoMaterial.setCantidad(cantidad);
					pedidoMaterial.setIdMaterial(idMaterial);
					
					
					pedidosMaterial.add(pedidoMaterial);
					pedidoMaterial = new PedidoMaterial2();
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				if(prepStmt!=null){
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return pedidosMaterial;
		}
		
		public ArrayList<Cliente> consultarClienteFiltroIdPedido(String idPedido) throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<Cliente> clientes = new ArrayList<Cliente>();
			Cliente cliente = new Cliente();
			
			try {
				prepStmt = conexion.prepareStatement("select email, nacionalidad, dirreccion as direccion,"
						+ " telefono, ciudad, num_doc, id_pedido from usuarios join"
						+ " (select email_cliente as correo, id_pedido from pedidos_clientes join"
						+ "(select email as mail from clientes)on mail=pedidos_clientes.email_cliente) "
						+ "on correo=usuarios.email where id_pedido='"+idPedido+"'" );
				ResultSet rs = prepStmt.executeQuery();
				while (rs.next()) {
					String email2 = rs.getString("EMAIL");
					String nacionalidad = rs.getString("NACIONALIDAD");
					String direccion = rs.getString("DIRECCION");
					String telefono = rs.getString("TELEFONO");
					String ciudad = rs.getString("CIUDAD");
					String numDoc = rs.getString("NUM_DOC");
					String idPedido2= rs.getString("ID_PEDIDO");
					
					cliente.setCorreo(email2);
					cliente.setPais(nacionalidad);
					cliente.setDireccion(direccion);
					cliente.setTelefono(telefono);
					cliente.setCiudad(ciudad);
					cliente.setIdentificacion(numDoc);
					cliente.setPedido(idPedido2);
					
					clientes.add(cliente);
					cliente = new Cliente();
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				if(prepStmt!=null){
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return clientes;
		}
		
		public ArrayList<Cliente> consultarClienteFiltroPasi(String pais) throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<Cliente> clientes = new ArrayList<Cliente>();
			Cliente cliente = new Cliente();
			
			try {
				prepStmt = conexion.prepareStatement("select email, nacionalidad, dirreccion as direccion,"
						+ " telefono, ciudad, num_doc, id_pedido from usuarios join"
						+ " (select email_cliente as correo, id_pedido from pedidos_clientes join"
						+ "(select email as mail from clientes)on mail=pedidos_clientes.email_cliente) "
						+ "on correo=usuarios.email where NACIONALIDAD='"+pais+"'" );
				ResultSet rs = prepStmt.executeQuery();
				while (rs.next()) {
					String email2 = rs.getString("EMAIL");
					String nacionalidad = rs.getString("NACIONALIDAD");
					String direccion = rs.getString("DIRECCION");
					String telefono = rs.getString("TELEFONO");
					String ciudad = rs.getString("CIUDAD");
					String numDoc = rs.getString("NUM_DOC");
					String idPedido= rs.getString("ID_PEDIDO");
					
					cliente.setCorreo(email2);
					cliente.setPais(nacionalidad);
					cliente.setDireccion(direccion);
					cliente.setTelefono(telefono);
					cliente.setCiudad(ciudad);
					cliente.setIdentificacion(numDoc);
					cliente.setPedido(idPedido);
					
					clientes.add(cliente);
					cliente = new Cliente();
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				if(prepStmt!=null){
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return clientes;
		}
		
		public ArrayList<Cliente> consultarClienteFiltroEmail(String email) throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<Cliente> clientes = new ArrayList<Cliente>();
			Cliente cliente = new Cliente();
			
			try {
				prepStmt = conexion.prepareStatement("select email, nacionalidad, dirreccion as direccion,"
						+ " telefono, ciudad, num_doc, id_pedido from usuarios join"
						+ " (select email_cliente as correo, id_pedido from pedidos_clientes join"
						+ "(select email as mail from clientes)on mail=pedidos_clientes.email_cliente) "
						+ "on correo=usuarios.email where email='"+email+"'" );
				ResultSet rs = prepStmt.executeQuery();
				while (rs.next()) {
					String email2 = rs.getString("EMAIL");
					String nacionalidad = rs.getString("NACIONALIDAD");
					String direccion = rs.getString("DIRECCION");
					String telefono = rs.getString("TELEFONO");
					String ciudad = rs.getString("CIUDAD");
					String numDoc = rs.getString("NUM_DOC");
					String idPedido= rs.getString("ID_PEDIDO");
					
					cliente.setCorreo(email2);
					cliente.setPais(nacionalidad);
					cliente.setDireccion(direccion);
					cliente.setTelefono(telefono);
					cliente.setCiudad(ciudad);
					cliente.setIdentificacion(numDoc);
					cliente.setPedido(idPedido);
					
					clientes.add(cliente);
					cliente = new Cliente();
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				if(prepStmt!=null){
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return clientes;
		}
		
		public ArrayList<Proveedor> consultarProveedores() throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<Proveedor> proveedores = new ArrayList<Proveedor>();
			Proveedor proveedor = new Proveedor();
			
			try {
				prepStmt = conexion.prepareStatement("select email, nacionalidad, direccion, num_doc,"
						+ " id_pedido, estado, nombre  from MATERIAS_PRIMAS join "
						+ "(select email, nacionalidad, direccion, num_doc, id_pedido, estado, id_materia"
						+ " from provee_materias join(select email, nacionalidad, dirreccion as direccion,"
						+ " num_doc, id_pedido, estado from usuarios join(select id_pedido, email_proveedor "
						+ "as proveemail, estado from PEDIDOS_PROVEEDORES where estado='Pendiente')on"
						+ " email=proveemail)on email=email_proveedor)on MATERIAS_PRIMAS.CODIGO=id_materia");
				ResultSet rs = prepStmt.executeQuery();
				while (rs.next()) {
					String email = rs.getString("EMAIL");
					String nacionalidad = rs.getString("NACIONALIDAD");
					String direccion = rs.getString("DIRECCION");
					String numDoc = rs.getString("NUM_DOC");
					String idPedido= rs.getString("ID_PEDIDO");
					String estado = rs.getString("ESTADO");
					String nombreMateria = rs.getString("NOMBRE");
					
					proveedor.setCorreo(email);
					proveedor.setPais(nacionalidad);
					proveedor.setDireccion(direccion);
					proveedor.setIdentificacion(numDoc);
					proveedor.setIdPedido(idPedido);
					proveedor.setEstadoPedido(estado);
					proveedor.setNombreMateria(nombreMateria);
					
					proveedores.add(proveedor);
					proveedor = new Proveedor();
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				if(prepStmt!=null){
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return proveedores;
		}
		
		public ArrayList<Proveedor> consultarProveedoresPorEmail(String email) throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<Proveedor> proveedores = new ArrayList<Proveedor>();
			Proveedor proveedor = new Proveedor();
			
			try {
				prepStmt = conexion.prepareStatement("select email, nacionalidad, direccion, num_doc,"
						+ " id_pedido, estado, nombre  from MATERIAS_PRIMAS join "
						+ "(select email, nacionalidad, direccion, num_doc, id_pedido, estado, id_materia"
						+ " from provee_materias join(select email, nacionalidad, dirreccion as direccion,"
						+ " num_doc, id_pedido, estado from usuarios join(select id_pedido, email_proveedor "
						+ "as proveemail, estado from PEDIDOS_PROVEEDORES where estado='Pendiente')on"
						+ " email=proveemail)on email=email_proveedor)on MATERIAS_PRIMAS.CODIGO=id_materia where email='"+email+"'");
				ResultSet rs = prepStmt.executeQuery();
				while (rs.next()) {
					String email2 = rs.getString("EMAIL");
					String nacionalidad = rs.getString("NACIONALIDAD");
					String direccion = rs.getString("DIRECCION");
					String numDoc = rs.getString("NUM_DOC");
					String idPedido= rs.getString("ID_PEDIDO");
					String estado = rs.getString("ESTADO");
					String nombreMateria = rs.getString("NOMBRE");
					
					proveedor.setCorreo(email2);
					proveedor.setPais(nacionalidad);
					proveedor.setDireccion(direccion);
					proveedor.setIdentificacion(numDoc);
					proveedor.setIdPedido(idPedido);
					proveedor.setEstadoPedido(estado);
					proveedor.setNombreMateria(nombreMateria);
					
					proveedores.add(proveedor);
					proveedor = new Proveedor();
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				if(prepStmt!=null){
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return proveedores;
		}
		
		public ArrayList<Proveedor> consultarProveedoresPorDocumento(String documento) throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<Proveedor> proveedores = new ArrayList<Proveedor>();
			Proveedor proveedor = new Proveedor();
			
			try {
				prepStmt = conexion.prepareStatement("select email, nacionalidad, direccion, num_doc,"
						+ " id_pedido, estado, nombre  from MATERIAS_PRIMAS join "
						+ "(select email, nacionalidad, direccion, num_doc, id_pedido, estado, id_materia"
						+ " from provee_materias join(select email, nacionalidad, dirreccion as direccion,"
						+ " num_doc, id_pedido, estado from usuarios join(select id_pedido, email_proveedor "
						+ "as proveemail, estado from PEDIDOS_PROVEEDORES where estado='Pendiente')on"
						+ " email=proveemail)on email=email_proveedor)on MATERIAS_PRIMAS.CODIGO=id_materia where num_doc='"+documento+"'");
				ResultSet rs = prepStmt.executeQuery();
				while (rs.next()) {
					String email2 = rs.getString("EMAIL");
					String nacionalidad = rs.getString("NACIONALIDAD");
					String direccion = rs.getString("DIRECCION");
					String numDoc = rs.getString("NUM_DOC");
					String idPedido= rs.getString("ID_PEDIDO");
					String estado = rs.getString("ESTADO");
					String nombreMateria = rs.getString("NOMBRE");
					
					proveedor.setCorreo(email2);
					proveedor.setPais(nacionalidad);
					proveedor.setDireccion(direccion);
					proveedor.setIdentificacion(numDoc);
					proveedor.setIdPedido(idPedido);
					proveedor.setEstadoPedido(estado);
					proveedor.setNombreMateria(nombreMateria);
					
					proveedores.add(proveedor);
					proveedor = new Proveedor();
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				if(prepStmt!=null){
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return proveedores;
		}
		public ArrayList<Proveedor> consultarProveedoresPorMateria(String materia) throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<Proveedor> proveedores = new ArrayList<Proveedor>();
			Proveedor proveedor = new Proveedor();
			
			try {
				prepStmt = conexion.prepareStatement("select email, nacionalidad, direccion, num_doc,"
						+ " id_pedido, estado, nombre  from MATERIAS_PRIMAS join "
						+ "(select email, nacionalidad, direccion, num_doc, id_pedido, estado, id_materia"
						+ " from provee_materias join(select email, nacionalidad, dirreccion as direccion,"
						+ " num_doc, id_pedido, estado from usuarios join(select id_pedido, email_proveedor "
						+ "as proveemail, estado from PEDIDOS_PROVEEDORES where estado='Pendiente')on"
						+ " email=proveemail)on email=email_proveedor)on MATERIAS_PRIMAS.CODIGO=id_materia where nombre='"+materia+"'");
				ResultSet rs = prepStmt.executeQuery();
				while (rs.next()) {
					String email2 = rs.getString("EMAIL");
					String nacionalidad = rs.getString("NACIONALIDAD");
					String direccion = rs.getString("DIRECCION");
					String numDoc = rs.getString("NUM_DOC");
					String idPedido= rs.getString("ID_PEDIDO");
					String estado = rs.getString("ESTADO");
					String nombreMateria = rs.getString("NOMBRE");
					
					proveedor.setCorreo(email2);
					proveedor.setPais(nacionalidad);
					proveedor.setDireccion(direccion);
					proveedor.setIdentificacion(numDoc);
					proveedor.setIdPedido(idPedido);
					proveedor.setEstadoPedido(estado);
					proveedor.setNombreMateria(nombreMateria);
					
					proveedores.add(proveedor);
					proveedor = new Proveedor();
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				if(prepStmt!=null){
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return proveedores;
		}
		
		public ArrayList<Cliente> consultarClientes() throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<Cliente> clientes = new ArrayList<Cliente>();
			Cliente cliente = new Cliente();
			
			try {
				prepStmt = conexion.prepareStatement("select email, nacionalidad, dirreccion as direccion,"
						+ " telefono, ciudad, num_doc, id_pedido from usuarios join"
						+ " (select email_cliente as correo, id_pedido from pedidos_clientes join"
						+ "(select email as mail from clientes)on mail=pedidos_clientes.email_cliente) "
						+ "on correo=usuarios.email" );
				ResultSet rs = prepStmt.executeQuery();
				while (rs.next()) {
					String email = rs.getString("EMAIL");
					String nacionalidad = rs.getString("NACIONALIDAD");
					String direccion = rs.getString("DIRECCION");
					String telefono = rs.getString("TELEFONO");
					String ciudad = rs.getString("CIUDAD");
					String numDoc = rs.getString("NUM_DOC");
					String idPedido= rs.getString("ID_PEDIDO");
					
					cliente.setCorreo(email);
					cliente.setPais(nacionalidad);
					cliente.setDireccion(direccion);
					cliente.setTelefono(telefono);
					cliente.setCiudad(ciudad);
					cliente.setIdentificacion(numDoc);
					cliente.setPedido(idPedido);
					
					clientes.add(cliente);
					cliente = new Cliente();
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				if(prepStmt!=null){
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return clientes;
		}
		
		public ArrayList<ReqPedidoCliente> consultarPedidosClienteXNombre(String nombre) throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<ReqPedidoCliente> pedidos = new ArrayList<ReqPedidoCliente>();
			ReqPedidoCliente pedido = new ReqPedidoCliente();
			try {
				prepStmt = conexion.prepareStatement("select EMAIL_CLIENTE, NOMBRE, CANT, COSTO, FECHA_PEDIDO, FECHA_ENTREGA "
						+ "from PRODUCTOS JOIN (select ID_PRODUCTO AS ID_PROD, EMAIL_CLIENTE,"
						+ " CANTIDAD AS CANT, FECHA_PEDIDO, FECHA_ENTREGA from CONTENIDO_PEDIDO"
						+ " JOIN (select id_pedido AS id_ped, email_cliente, fecha_pedido, fecha_entrega"
						+ " from PEDIDOS_CLIENTES) on CONTENIDO_PEDIDO.ID_PEDIDO=id_ped) ON ID_PROD=PRODUCTOS.CODIGO where nombre='"+nombre+"'");
				ResultSet rs = prepStmt.executeQuery();
				
				while (rs.next()) {
					String emailCliente = rs.getString("EMAIL_CLIENTE");
					String nombre2 = rs.getString("NOMBRE");
					int cantidad = rs.getInt("CANT");
					int costo = rs.getInt("COSTO");
					Date fechaPedido = rs.getDate("FECHA_PEDIDO");
					Date fechaEntrega = rs.getDate("FECHA_ENTREGA");
					
					pedido.setEmailCliente(emailCliente);
					pedido.setNombreProducto(nombre2);
					pedido.setCantidad(cantidad);
					pedido.setCosto(costo);
					pedido.setFechaPedido(fechaPedido);
					pedido.setFechaEntrega(fechaEntrega);
					
					pedidos.add(pedido);
					
					pedido = new ReqPedidoCliente();	
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if (prepStmt!=null) {
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return pedidos;
		}
		
		public ArrayList<ReqPedidoCliente> consultarPedidosClienteXEmail(String email) throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<ReqPedidoCliente> pedidos = new ArrayList<ReqPedidoCliente>();
			ReqPedidoCliente pedido = new ReqPedidoCliente();
			try {
				prepStmt = conexion.prepareStatement("select EMAIL_CLIENTE, NOMBRE, CANT, COSTO, FECHA_PEDIDO, FECHA_ENTREGA "
						+ "from PRODUCTOS JOIN (select ID_PRODUCTO AS ID_PROD, EMAIL_CLIENTE,"
						+ " CANTIDAD AS CANT, FECHA_PEDIDO, FECHA_ENTREGA from CONTENIDO_PEDIDO"
						+ " JOIN (select id_pedido AS id_ped, email_cliente, fecha_pedido, fecha_entrega"
						+ " from PEDIDOS_CLIENTES) on CONTENIDO_PEDIDO.ID_PEDIDO=id_ped) ON ID_PROD=PRODUCTOS.CODIGO where email_cliente='"+email+"'");
				ResultSet rs = prepStmt.executeQuery();
				
				while (rs.next()) {
					String emailCliente = rs.getString("EMAIL_CLIENTE");
					String nombre2 = rs.getString("NOMBRE");
					int cantidad = rs.getInt("CANT");
					int costo = rs.getInt("COSTO");
					Date fechaPedido = rs.getDate("FECHA_PEDIDO");
					Date fechaEntrega = rs.getDate("FECHA_ENTREGA");
					
					pedido.setEmailCliente(emailCliente);
					pedido.setNombreProducto(nombre2);
					pedido.setCantidad(cantidad);
					pedido.setCosto(costo);
					pedido.setFechaPedido(fechaPedido);
					pedido.setFechaEntrega(fechaEntrega);
					
					pedidos.add(pedido);
					
					pedido = new ReqPedidoCliente();	
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if (prepStmt!=null) {
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return pedidos;
		}
		
		public ArrayList<ReqPedidoCliente> consultarPedidosCliente() throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<ReqPedidoCliente> pedidos = new ArrayList<ReqPedidoCliente>();
			ReqPedidoCliente pedido = new ReqPedidoCliente();
			try {
				prepStmt = conexion.prepareStatement("select EMAIL_CLIENTE, NOMBRE, CANT, COSTO, FECHA_PEDIDO, FECHA_ENTREGA "
						+ "from PRODUCTOS JOIN (select ID_PRODUCTO AS ID_PROD, EMAIL_CLIENTE,"
						+ " CANTIDAD AS CANT, FECHA_PEDIDO, FECHA_ENTREGA from CONTENIDO_PEDIDO"
						+ " JOIN (select id_pedido AS id_ped, email_cliente, fecha_pedido, fecha_entrega"
						+ " from PEDIDOS_CLIENTES) on CONTENIDO_PEDIDO.ID_PEDIDO=id_ped) ON ID_PROD=PRODUCTOS.CODIGO");
				ResultSet rs = prepStmt.executeQuery();
				
				while (rs.next()) {
					String emailCliente = rs.getString("EMAIL_CLIENTE");
					String nombre = rs.getString("NOMBRE");
					int cantidad = rs.getInt("CANT");
					int costo = rs.getInt("COSTO");
					Date fechaPedido = rs.getDate("FECHA_PEDIDO");
					Date fechaEntrega = rs.getDate("FECHA_ENTREGA");
					
					pedido.setEmailCliente(emailCliente);
					pedido.setNombreProducto(nombre);
					pedido.setCantidad(cantidad);
					pedido.setCosto(costo);
					pedido.setFechaPedido(fechaPedido);
					pedido.setFechaEntrega(fechaEntrega);
					
					pedidos.add(pedido);
					
					pedido = new ReqPedidoCliente();	
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if (prepStmt!=null) {
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return pedidos;
		}
		
		public ArrayList<ReqPedidoProveedor> consultarPedidosProveedorXEstado(String estado) throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<ReqPedidoProveedor> pedidos = new ArrayList<ReqPedidoProveedor>();
			ReqPedidoProveedor pedido = new ReqPedidoProveedor();
			try {
				prepStmt = conexion.prepareStatement("select idped, email, fechaped, fechaent, estado,"
						+ " cant, nombre from materias_primas join(select id_mat, idped, email,"
						+ " fechaped, fechaent, estado, idcomp, cant from compuesto_de_comp"
						+ " join(select idped, email, fechaped, fechaent, estado, id_comp as idcomp,"
						+ " cantidad as cant from contenido_pedido_mat join(select id_pedido as idped,"
						+ " email_proveedor as email, fecha_pedido as fechaped, fecha_entrega as fechaent,"
						+ " estado from pedidos_proveedores)on idPed=id_pedido)on id_comp=idcomp)on "
						+ "id_mat=codigo where estado ='"+estado+"'");
				ResultSet rs = prepStmt.executeQuery();
				
				while (rs.next()) {
					
					String idPedido = rs.getString("IDPED");
					String emailProveedor = rs.getString("EMAIL");
					Date fechaPedido = rs.getDate("FECHAPED");
					Date fechaEntrega = rs.getDate("FECHAENT");
					String estado2 = rs.getString("ESTADO");
					int cantidad = rs.getInt("CANT");
					String nombre = rs.getString("NOMBRE");
					
					
					pedido.setIdPed(idPedido);
					pedido.setEmail(emailProveedor);
					pedido.setFechaPedido(fechaPedido);
					pedido.setFechaEntrega(fechaEntrega);
					pedido.setEstado(estado2);
					pedido.setCantidad(cantidad);
					pedido.setNombreMaterial(nombre);
					
					pedidos.add(pedido);
					
					pedido = new ReqPedidoProveedor();	
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if (prepStmt!=null) {
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return pedidos;
		}
		
		public ArrayList<ReqPedidoProveedor> consultarPedidosProveedorXMateriaRequerida(String nameMateria) throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<ReqPedidoProveedor> pedidos = new ArrayList<ReqPedidoProveedor>();
			ReqPedidoProveedor pedido = new ReqPedidoProveedor();
			try {
				prepStmt = conexion.prepareStatement("select idped, email, fechaped, fechaent, estado,"
						+ " cant, nombre from materias_primas join(select id_mat, idped, email,"
						+ " fechaped, fechaent, estado, idcomp, cant from compuesto_de_comp"
						+ " join(select idped, email, fechaped, fechaent, estado, id_comp as idcomp,"
						+ " cantidad as cant from contenido_pedido_mat join(select id_pedido as idped,"
						+ " email_proveedor as email, fecha_pedido as fechaped, fecha_entrega as fechaent,"
						+ " estado from pedidos_proveedores)on idPed=id_pedido)on id_comp=idcomp)on "
						+ "id_mat=codigo where nombre ='"+nameMateria+"'");
				ResultSet rs = prepStmt.executeQuery();
				
				while (rs.next()) {
					
					String idPedido2 = rs.getString("IDPED");
					String emailProveedor = rs.getString("EMAIL");
					Date fechaPedido = rs.getDate("FECHAPED");
					Date fechaEntrega = rs.getDate("FECHAENT");
					String estado2 = rs.getString("ESTADO");
					int cantidad = rs.getInt("CANT");
					String nombre = rs.getString("NOMBRE");
					
					
					pedido.setIdPed(idPedido2);
					pedido.setEmail(emailProveedor);
					pedido.setFechaPedido(fechaPedido);
					pedido.setFechaEntrega(fechaEntrega);
					pedido.setEstado(estado2);
					pedido.setCantidad(cantidad);
					pedido.setNombreMaterial(nombre);
					
					pedidos.add(pedido);
					
					pedido = new ReqPedidoProveedor();	
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if (prepStmt!=null) {
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return pedidos;
		}
		
		public ArrayList<ReqPedidoProveedor> consultarPedidosProveedorXRangoFecha(String fechaPed, String fechaEnt) throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<ReqPedidoProveedor> pedidos = new ArrayList<ReqPedidoProveedor>();
			ReqPedidoProveedor pedido = new ReqPedidoProveedor();
			try {
				SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd");
				
				Calendar c1 = Calendar.getInstance();
				c1.setTime(df.parse(fechaPed));
				
				String month1 = "";
				
				if ((c1.get(Calendar.MONTH)+1) < 10 )
					month1 = "0"+(c1.get(Calendar.MONTH)+1);
				else 
					month1 = ""+(c1.get(Calendar.MONTH)+1);
				
				Calendar c2 = Calendar.getInstance();
				c2.setTime(df.parse(fechaEnt));
				
				String month2 = "";
				
				if ((c2.get(Calendar.MONTH)+1) < 10 )
					month2 = "0"+(c2.get(Calendar.MONTH)+1);
				else 
					month2 = ""+(c2.get(Calendar.MONTH)+1);
				
				prepStmt = conexion.prepareStatement("select idped, email, fechaped, fechaent, estado,"
						+ " cant, nombre from materias_primas join(select id_mat, idped, email,"
						+ " fechaped, fechaent, estado, idcomp, cant from compuesto_de_comp"
						+ " join(select idped, email, fechaped, fechaent, estado, id_comp as idcomp,"
						+ " cantidad as cant from contenido_pedido_mat join(select id_pedido as idped,"
						+ " email_proveedor as email, fecha_pedido as fechaped, fecha_entrega as fechaent,"
						+ " estado from pedidos_proveedores)on idPed=id_pedido)on id_comp=idcomp)on "
						+ "id_mat=codigo where fechaent between TO_DATE('" + c1.get(Calendar.DAY_OF_MONTH) + "/" + month1 + "/" + c1.get(Calendar.YEAR) +"', 'DD-MM-YY') AND TO_DATE('" + c2.get(Calendar.DAY_OF_MONTH) + "/" + month2 + "/" + c2.get(Calendar.YEAR) +"', 'DD-MM-YY')");
				ResultSet rs = prepStmt.executeQuery();
				
				while (rs.next()) {
					
					String idPedido = rs.getString("IDPED");
					String emailProveedor = rs.getString("EMAIL");
					Date fechaPedido = rs.getDate("FECHAPED");
					Date fechaEntrega = rs.getDate("FECHAENT");
					String estado2 = rs.getString("ESTADO");
					int cantidad = rs.getInt("CANT");
					String nombre = rs.getString("NOMBRE");
					
					
					pedido.setIdPed(idPedido);
					pedido.setEmail(emailProveedor);
					pedido.setFechaPedido(fechaPedido);
					pedido.setFechaEntrega(fechaEntrega);
					pedido.setEstado(estado2);
					pedido.setCantidad(cantidad);
					pedido.setNombreMaterial(nombre);
					
					pedidos.add(pedido);
					
					pedido = new ReqPedidoProveedor();	
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if (prepStmt!=null) {
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return pedidos;
		}
		
		public ArrayList<ReqPedidoProveedor> consultarPedidosProveedor() throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<ReqPedidoProveedor> pedidos = new ArrayList<ReqPedidoProveedor>();
			ReqPedidoProveedor pedido = new ReqPedidoProveedor();
			try {
				prepStmt = conexion.prepareStatement("select idped, email, fechaped, fechaent, estado,"
						+ " cant, nombre from materias_primas join(select id_mat, idped, email,"
						+ " fechaped, fechaent, estado, idcomp, cant from compuesto_de_comp"
						+ " join(select idped, email, fechaped, fechaent, estado, id_comp as idcomp,"
						+ " cantidad as cant from contenido_pedido_mat join(select id_pedido as idped,"
						+ " email_proveedor as email, fecha_pedido as fechaped, fecha_entrega as fechaent,"
						+ " estado from pedidos_proveedores)on idPed=id_pedido)on id_comp=idcomp)on "
						+ "id_mat=codigo");
				ResultSet rs = prepStmt.executeQuery();
				
				while (rs.next()) {
					
					String idPedido = rs.getString("IDPED");
					String emailProveedor = rs.getString("EMAIL");
					Date fechaPedido = rs.getDate("FECHAPED");
					Date fechaEntrega = rs.getDate("FECHAENT");
					String estado = rs.getString("ESTADO");
					int cantidad = rs.getInt("CANT");
					String nombre = rs.getString("NOMBRE");
					
					
					pedido.setIdPed(idPedido);
					pedido.setEmail(emailProveedor);
					pedido.setFechaPedido(fechaPedido);
					pedido.setFechaEntrega(fechaEntrega);
					pedido.setEstado(estado);
					pedido.setCantidad(cantidad);
					pedido.setNombreMaterial(nombre);
					
					pedidos.add(pedido);
					
					pedido = new ReqPedidoProveedor();	
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if (prepStmt!=null) {
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return pedidos;
		}
		
		public ArrayList<ReqPedidoCliente> consultarPedidosClienteRangoFecha(String fechaPedido, String fechaEntrega) throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<ReqPedidoCliente> pedidos = new ArrayList<ReqPedidoCliente>();
			ReqPedidoCliente pedido = new ReqPedidoCliente();
			try {
				SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd");
				
				Calendar c1 = Calendar.getInstance();
				c1.setTime(df.parse(fechaPedido));
				
				String month1 = "";
				
				if ((c1.get(Calendar.MONTH)+1) < 10 )
					month1 = "0"+(c1.get(Calendar.MONTH)+1);
				else 
					month1 = ""+(c1.get(Calendar.MONTH)+1);
				
				Calendar c2 = Calendar.getInstance();
				c2.setTime(df.parse(fechaEntrega));
				
				String month2 = "";
				
				if ((c2.get(Calendar.MONTH)+1) < 10 )
					month2 = "0"+(c2.get(Calendar.MONTH)+1);
				else 
					month2 = ""+(c2.get(Calendar.MONTH)+1);
				prepStmt = conexion.prepareStatement("select EMAIL_CLIENTE, NOMBRE, CANT, COSTO, FECHA_PEDIDO, FECHA_ENTREGA "
						+ "from PRODUCTOS JOIN (select ID_PRODUCTO AS ID_PROD, EMAIL_CLIENTE,"
						+ " CANTIDAD AS CANT, FECHA_PEDIDO, FECHA_ENTREGA from CONTENIDO_PEDIDO"
						+ " JOIN (select id_pedido AS id_ped, email_cliente, fecha_pedido, fecha_entrega"
						+ " from PEDIDOS_CLIENTES) on CONTENIDO_PEDIDO.ID_PEDIDO=id_ped) ON ID_PROD=PRODUCTOS.CODIGO where fecha_entrega between TO_DATE('" + c1.get(Calendar.DAY_OF_MONTH) + "/" + month1 + "/" + c1.get(Calendar.YEAR) +"', 'DD-MM-YY') AND TO_DATE('" + c2.get(Calendar.DAY_OF_MONTH) + "/" + month2 + "/" + c2.get(Calendar.YEAR) +"', 'DD-MM-YY')");
				ResultSet rs = prepStmt.executeQuery();
				
				while (rs.next()) {
					String emailCliente = rs.getString("EMAIL_CLIENTE");
					String nombre = rs.getString("NOMBRE");
					int cantidad = rs.getInt("CANT");
					int costo = rs.getInt("COSTO");
					Date fechaPedido2 = rs.getDate("FECHA_PEDIDO");
					Date fechaEntrega2 = rs.getDate("FECHA_ENTREGA");
					
					pedido.setEmailCliente(emailCliente);
					pedido.setNombreProducto(nombre);
					pedido.setCantidad(cantidad);
					pedido.setCosto(costo);
					pedido.setFechaPedido(fechaPedido2);
					pedido.setFechaEntrega(fechaEntrega2);
					
					pedidos.add(pedido);
					
					pedido = new ReqPedidoCliente();	
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if (prepStmt!=null) {
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return pedidos;
		}
		
		public void cancelarPedidoCliente(String idPedido) throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			conexion.setAutoCommit(false);
			prepStmt = conexion.prepareStatement("UPDATE "+usuario+".PEDIDOS_CLIENTES SET ESTADO='Cancelado' WHERE ID_PEDIDO='"+idPedido+"'");
			prepStmt.executeUpdate();
			conexion.commit();
			closeConnection(conexion);
					
		}
		
		public ArrayList<PedidoCliente> darPedidosCliente() throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<PedidoCliente> pedidos = new ArrayList<PedidoCliente>();
			PedidoCliente pedido = new PedidoCliente();
			try {
				prepStmt = conexion.prepareStatement("SELECT * FROM "+usuario+".PEDIDOS_CLIENTES WHERE ESTADO='Pendiente'");
				ResultSet rs = prepStmt.executeQuery();
				
				while (rs.next()) {
					String idPedido = rs.getString("ID_PEDIDO");
					String emailCliente = rs.getString("EMAIL_CLIENTE");
					Date fechaPedido = rs.getDate("FECHA_PEDIDO");
					Date fechaEntrega = rs.getDate("FECHA_ENTREGA");
					String estado = rs.getString("ESTADO");
					
					
					pedido.setIdPedido(idPedido);
					pedido.setEmailCliente(emailCliente);
					pedido.setFecha(fechaPedido);
					pedido.setFechaEnt(fechaEntrega);
					pedido.setEstado(estado);
					
					pedidos.add(pedido);
					
					pedido = new PedidoCliente();	
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if (prepStmt!=null) {
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return pedidos;
		}
		
		
		public ArrayList<String> consultarNombreProductos() throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<String> nombreProductos = new ArrayList<String>();
			
			try {
				prepStmt = conexion.prepareStatement("SELECT * FROM "+usuario+".PRODUCTOS ORDER BY NOMBRE" );
				ResultSet rs = prepStmt.executeQuery();
				while (rs.next()) {
					String nombre = rs.getString("NOMBRE");
					nombreProductos.add(nombre);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				if(prepStmt!=null){
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return nombreProductos;
		}
		
		public ArrayList<MateriaPrima> consultarExistenciasMata() throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<MateriaPrima> materias = new ArrayList<MateriaPrima>();
			MateriaPrima materia = new MateriaPrima();
			try {
				prepStmt = conexion.prepareStatement("SELECT * FROM "+usuario+".MATERIAS_PRIMAS");
				ResultSet rs = prepStmt.executeQuery();
				
				while (rs.next()) {
					String codigo = rs.getString("CODIGO");
					String nombre = rs.getString("NOMBRE");
					String unidadMedidad = rs.getString("UNIDAD_DE_MEDIDA");
					int cant = rs.getInt("CANTIDAD");
					
					materia.setId(codigo);
					materia.setNombre(nombre);
					materia.setUnidad(unidadMedidad);
					materia.setCantidad(cant);
					
					materias.add(materia);
					
					materia = new MateriaPrima();	
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if (prepStmt!=null) {
					try {
						prepStmt.close();
					} catch (SQLException e2) {
						throw new Exception("ERROR: ConsultaDAO: loadRow() = cerrando conexion.");
					}
				}
			}
			closeConnection(conexion);
			return materias;
		}
		
		public ArrayList<Producto> consultarExistenciasProductoPorNombre(String nombre) throws Exception{
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			PreparedStatement prepStmt = null;
			ArrayList<Producto> productos = new ArrayList<Producto>();
			Producto produc = new Producto();
			try {
				prepStmt = conexion.prepareStatement("SELECT * FROM "+usuario+".PRODUCTOS WHERE NOMBRE='"+nombre+"'");
				ResultSet rs = prepStmt.executeQuery();
				while(rs.next()){
					String codigo = rs.getString("CODIGO");
					String name = rs.getString("NOMBRE");
					int cantidad = rs.getInt("CANTIDAD");
					
					produc.setId(codigo);
					produc.setNombre(name);
					produc.setCantidad(cantidad);
					
					productos.add(produc);
					
					produc = new Producto();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				if (prepStmt!=null) {
					try
					{
						prepStmt.close();
					}
					catch(SQLException exception){
						throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexin.");
					}
				}
			}
			closeConnection(conexion);
			return productos;
			
		}
		
		public ArrayList<Producto> mostrarProductos() throws Exception
		{
			establecerConexion(RUTA_DB,USER_DB,PASS_DB);

			PreparedStatement prepStmt = null;

			ArrayList<Producto> productos = new ArrayList<Producto>();
			Producto prod = new Producto();
			try
			{
				prepStmt = conexion.prepareStatement("SELECT * FROM "+usuario+".PRODUCTOS");

				ResultSet rs = prepStmt.executeQuery();

				while(rs.next())
				{
					String id = rs.getString("CODIGO");
					String nombre = rs.getString("NOMBRE");

					prod = new Producto();
					prod.setId(id);
					prod.setNombre(nombre);
					
					System.out.println(prod.getNombre());
					System.out.println(prod.getId());
					System.out.println("-----");
					
					productos.add(prod);
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}finally 
			{
				if (prepStmt != null) 
				{
					try 
					{
						prepStmt.close();
					} 
					catch (SQLException exception) 
					{
						throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexin.");
					}
				}
			}
			closeConnection(conexion);
			return productos;
		}
		
		

		public int numEtapasProducto(String idProd) throws Exception{
			establecerConexion(RUTA_DB,USER_DB,PASS_DB);

			PreparedStatement prepStmt = null;

			String query = "SELECT COUNT(CODIGO) AS ETAPAS FROM "+usuario+".ETAPAS_PRODUCCION  WHERE ID_PRODUCTO = '"+idProd+"' GROUP BY ID_PRODUCTO";
			prepStmt = conexion.prepareStatement(query);
			ResultSet rs = prepStmt.executeQuery();

			while(rs.next()){
				int num = rs.getInt("ETAPAS");
				System.out.println(num);
				return num;
			}	
			closeConnection(conexion);
			throw new Exception("Error al leer los datos");
		}

		public int etapaActualProducto(String idProd) throws Exception{
			establecerConexion(RUTA_DB,USER_DB,PASS_DB);

			PreparedStatement prepStmt = null;

			String query = "SELECT NUM_ETAPA FROM ("+usuario+".ESTADO_PRODUCTO LEFT OUTER JOIN "+usuario+".ETAPAS_PRODUCCION ON "+usuario+".ESTADO_PRODUCTO.ID_PRODUCTO = "
					+usuario+".ETAPAS_PRODUCCION.ID_PRODUCTO) WHERE "+usuario+".Estado_Producto.Id_Producto = '"+idProd+"'";
			prepStmt = conexion.prepareStatement(query);		
			ResultSet rs = prepStmt.executeQuery();

			while(rs.next()){
				int id = rs.getInt("NUM_ETAPA");

				return id;
			}
			closeConnection(conexion);
			throw new Exception("Error al leer los datos");
		}

		public void avanzarEtapaProducto(String idProd, String idEtapa) throws Exception{
			int max = numEtapasProducto(idProd);
			int actual = etapaActualProducto(idProd);
			establecerConexion(RUTA_DB, USER_DB, PASS_DB);
			if(actual==max){
				throw new Exception("El producto esta completado");
			}
			else if(actual == max-1){
				actual++;
				PreparedStatement prepStmt = null;

				String query = "UPDATE "+usuario+".ESTADO_PRODUCTO SET ID_ETAPA = (SELECT CODIGO FROM "+usuario+".ETAPAS_PRODUCCION "
						+ "WHERE ID_PRODUCTO = '"+idProd+"' AND NUM_ETAPA = '"+actual+"') WHERE ID_PRODUCTO = '"+idProd+"'";
				prepStmt = conexion.prepareStatement(query);		
				prepStmt.executeUpdate();

				query = "UPDATE "+usuario+".PRODUCTOS SET CANTIDAD = CANTIDAD+1 WHERE CODIGO = '"+idProd+"'";
				prepStmt = conexion.prepareStatement(query);
				prepStmt.executeUpdate();

				closeConnection(conexion);
			}
			else if(actual<max-1 && actual>=0){
				actual++;
				PreparedStatement prepStmt = null;

				String query = "UPDATE "+usuario+".ESTADO_PRODUCTO SET ID_ETAPA = (SELECT CODIGO FROM "+usuario+".ETAPAS_PRODUCCION "
						+ "WHERE ID_PRODUCTO = '"+idProd+"' AND NUM_ETAPA = '"+actual+"') WHERE ID_PRODUCTO = ''";
				prepStmt = conexion.prepareStatement(query);		
				prepStmt.executeUpdate();
				closeConnection(conexion);
			}
			else{
				throw new Exception("Datos incongruentes. Revisar base de datos");
			}
		}

		public int cantidadComponente(String idComp) throws Exception{
			establecerConexion(RUTA_DB,USER_DB,PASS_DB);

			PreparedStatement prepStmt = null;

			String query = "SELECT CANTIDAD FROM "+usuario+".COMPONENTES WHERE CODIGO = '"+idComp+"'";
			prepStmt = conexion.prepareStatement(query);
			ResultSet rs = prepStmt.executeQuery();

			while(rs.next()){
				int num = rs.getInt("CANTIDAD");
				System.out.println("Comp: "+num);
				return num;
			}	
			closeConnection(conexion);
			throw new Exception("Error al leer los datos");
		}

		public int cantidadRequerida(String etapa) throws Exception{
			establecerConexion(RUTA_DB,USER_DB,PASS_DB);

			PreparedStatement prepStmt = null;

			String query = "SELECT CANTIDAD FROM "+usuario+".ETAPAS_PRODUCCION WHERE CODIGO = '"+etapa+"'";
			prepStmt = conexion.prepareStatement(query);
			ResultSet rs = prepStmt.executeQuery();

			while(rs.next()){
				int num = rs.getInt("CANTIDAD");
				System.out.println("Req: "+num);
				return num;
			}	
			closeConnection(conexion);
			throw new Exception("Error al leer los datos");
		}

		public void restarInsumos(String etapa, String comp, String idProd) throws Exception{
			int min = cantidadRequerida(etapa);
			int hay = cantidadComponente(comp);

			if(min>hay){
				throw new Exception("No hay insumos suficientes");
			}
			else if(min<=hay){
				avanzarEtapaProducto(idProd, etapa);

				PreparedStatement prepStmt = null;

				String query = "UPDATE "+usuario+".COMPONENTES SET CANTIDAD = "+(hay-min)+" WHERE CODIGO = '"+idProd+"'";
				establecerConexion(RUTA_DB, USER_DB, PASS_DB);
				prepStmt = conexion.prepareStatement(query);
				prepStmt.executeUpdate();
				closeConnection(conexion);
			}
			else{
				throw new Exception("Datos incongruentes. Revisar base de datos");
			}
		}

		public String componenteEtapa(String etapa) throws Exception{
			establecerConexion(RUTA_DB,USER_DB,PASS_DB);

			PreparedStatement prepStmt = null;

			String query = "SELECT ID_COMP FROM "+usuario+".ETAPAS_PRODUCCION WHERE CODIGO = '"+etapa+"'";
			prepStmt = conexion.prepareStatement(query);
			ResultSet rs = prepStmt.executeQuery();

			while(rs.next()){
				String num = rs.getString("ID_COMP");

				return num;
			}	
			closeConnection(conexion);
			throw new Exception("Error al leer los datos");
		}

		public String productoEtapa(String etapa) throws Exception{
			establecerConexion(RUTA_DB,USER_DB,PASS_DB);

			PreparedStatement prepStmt = null;

			String query = "SELECT ID_PRODUCTO FROM "+usuario+".ETAPAS_PRODUCCION WHERE CODIGO = '"+etapa+"'";
			prepStmt = conexion.prepareStatement(query);
			ResultSet rs = prepStmt.executeQuery();

			while(rs.next()){
				String num = rs.getString("ID_PRODUCTO");

				return num;
			}	
			closeConnection(conexion);
			throw new Exception("Error al leer los datos");
		}

		public void registrarProduccionEtapa(String idEtapa, Date fechaFin) throws Exception{
			String comp = componenteEtapa(idEtapa);
			String prod = productoEtapa(idEtapa);

			restarInsumos(idEtapa, comp, prod);
			Date myDate = new Date();
			String today = new SimpleDateFormat("YYYY-MM-dd").format(myDate);
			String fin = new SimpleDateFormat("YYYY-MM-dd").format(fechaFin);
			String query = "INSERT INTO EJECUCION_ETAPA (ID_ETAPA, FECHA_INICIO, FECHA_FIN) VALUES('"+idEtapa+"',TO_DATE('"+today+"', 'YYYY-MM-dd'), TO_DATE('"+fin+"', 'YYYY-MM-dd'))";

			establecerConexion(RUTA_DB,USER_DB,PASS_DB);

			PreparedStatement prepStmt = null;
			prepStmt = conexion.prepareStatement(query);
			prepStmt.executeUpdate();
			closeConnection(conexion);
		}

		public void registrarLlegadaMaterial(String pedido) throws Exception{
			Date myDate = new Date();
			String today = new SimpleDateFormat("YYYY-MM-dd").format(myDate);

			establecerConexion(RUTA_DB,USER_DB,PASS_DB);

			PreparedStatement prepStmt = null;

			String query = "UPDATE "+usuario+".PEDIDOS_PROVEEDORES SET FECHA_ENTREGA = TO_DATE('"+today+"', 'YYYY-MM-dd'), ESTADO = 1 WHERE ID_PEDIDO = '"+pedido+"'";
			prepStmt = conexion.prepareStatement(query);
			prepStmt.executeUpdate();

			query = "SELECT ID_COMP, CANTIDAD FROM "+usuario+".CONTENIDO_PEDIDO_MAT WHERE ID_PEDIDO = '"+pedido+"'";

			prepStmt = conexion.prepareStatement(query);
			ResultSet rs = prepStmt.executeQuery();

			PreparedStatement prepStmt2 = null;

			while(rs.next()){
				String idcomp = rs.getString("ID_COMP");
				int cantidad = rs.getInt("CANTIDAD");

				query = "UPDATE "+usuario+".PRODUCTOS SET CANTIDAD = '"+cantidad+"' WHERE CODIGO = '"+idcomp+"'";
				prepStmt2 = conexion.prepareStatement(query);
				prepStmt2.executeUpdate();
			}	
			closeConnection(conexion);
		}

		public ArrayList<EtapaProduccion> etapasProducto(String producto) throws Exception{
			establecerConexion(RUTA_DB,USER_DB,PASS_DB);

			PreparedStatement prepStmt = null;

			String query = "SELECT * FROM "+usuario+".ETAPAS_PRODUCCION WHERE ID_PRODUCTO = '"+producto+"'";
			prepStmt = conexion.prepareStatement(query);
			ResultSet rs = prepStmt.executeQuery();

			ArrayList<EtapaProduccion> respuesta = new ArrayList<EtapaProduccion>();

			while(rs.next()){
				EtapaProduccion ep = new EtapaProduccion();

				String id = rs.getString("CODIGO");
				String descripcion = rs.getString("DESCRIPCION");
				String idprod = rs.getString("ID_PRODUCTO");
				int numetapa = rs.getInt("NUM_ETAPA");
				String idcomp = rs.getString("ID_COMP");
				int cantidad = rs.getInt("CANTIDAD");

				ep.setCantidad(cantidad);
				ep.setId(id);
				ep.setIdComp(idcomp);
				ep.setDescripcion(descripcion);
				ep.setIdProd(idprod);
				ep.setNombre(numetapa);

				respuesta.add(ep);
			}
			closeConnection(conexion);
			return respuesta;
		}

		public void registrarPedidoPendiente(ArrayList<ProductoCantidad> productos, String idped, String cliente) throws Exception{
			Date myDate = new Date();
			String today = new SimpleDateFormat("YYYY-MM-dd").format(myDate);

			establecerConexion(RUTA_DB,USER_DB,PASS_DB);

			PreparedStatement prepStmt = null;

			String query = "INSERT INTO "+usuario+".PEDIDOS_CLIENTES (ID_PEDIDO, EMAIL_CLIENTE, ESTADO, FECHA_PEDIDO) "
					+ "VALUES ('"+idped+"', '"+cliente+"', 9, '"+today+"')";
			prepStmt = conexion.prepareStatement(query);
			prepStmt.executeUpdate();

			for(ProductoCantidad pc: productos){
				query = "INSERT INTO "+usuario+".CONTENIDO_PEDIDO VALUES ("+idped+","+pc.getId()+","+pc.getCantidad()+")";
				prepStmt = conexion.prepareStatement(query);
				prepStmt.executeUpdate();
			}

			closeConnection(conexion);
			throw new Exception("Error al leer los datos");
		}

		public void registrarPedidoProductos(ArrayList<ProductoCantidad> productos, String idped, String cliente) throws Exception{
			for(ProductoCantidad pc: productos){
				int cantidad = pc.getCantidad();
				String id = pc.getId();
				for(EtapaProduccion ep: etapasProducto(id)){
					int cantreq = ep.getCantidad()*cantidad;
					int cant = cantidadComponente(ep.getIdComp());

					if(cantreq>cant){
						registrarPedidoPendiente(productos, idped, cliente);
						return;
					}
				}
			}

			Date myDate = new Date();
			String today = new SimpleDateFormat("YYYY-MM-dd").format(myDate);

			establecerConexion(RUTA_DB,USER_DB,PASS_DB);

			PreparedStatement prepStmt = null;

			String query = "INSERT INTO "+usuario+".PEDIDOS_CLIENTES (ID_PEDIDO, EMAIL_CLIENTE, ESTADO, FECHA_PEDIDO) "
					+ "VALUES ('"+idped+"', '"+cliente+"', 7, TO_DATE('"+today+"', 'YYYY-MM-dd'))";
			prepStmt = conexion.prepareStatement(query);
			prepStmt.executeUpdate();

			for(ProductoCantidad pc: productos){
				query = "INSERT INTO "+usuario+".CONTENIDO_PEDIDO VALUES ("+idped+","+pc.getId()+","+pc.getCantidad()+")";
				prepStmt = conexion.prepareStatement(query);
				prepStmt.executeUpdate();
			}

			closeConnection(conexion);
		}

		public void registrarEntregaPedido(String pedido) throws Exception{
			Date myDate = new Date();
			String today = new SimpleDateFormat("YYYY-MM-dd").format(myDate);

			establecerConexion(RUTA_DB,USER_DB,PASS_DB);

			PreparedStatement prepStmt = null;

			String query = "UPDATE "+usuario+".PEDIDOS_CLIENTES SET FECHA_ENTREGA = TO_DATE('"+today+"','YYYY-MM-dd'), ESTADO = 1 WHERE ID_PEDIDO = '"+pedido+"'";
			prepStmt = conexion.prepareStatement(query);
			prepStmt.executeUpdate();

			query = "SELECT ID_PRODUCTO, CANTIDAD FROM "+usuario+".CONTENIDO_PEDIDO WHERE ID_PEDIDO = '"+pedido+"'";

			prepStmt = conexion.prepareStatement(query);
			ResultSet rs = prepStmt.executeQuery();

			PreparedStatement prepStmt2 = null;

			while(rs.next()){
				String idcomp = rs.getString("ID_PRODUCTO");
				int cantidad = rs.getInt("CANTIDAD");

				query = "UPDATE "+usuario+".PRODUCTOS SET CANTIDAD = CANTIDAD-'"+cantidad+"' WHERE CODIGO = '"+idcomp+"'";
				prepStmt2 = conexion.prepareStatement(query);
				prepStmt2.executeUpdate();
			}	
			closeConnection(conexion);
		}

		public void cancelarPedido(String pedido) throws Exception{
			establecerConexion(RUTA_DB,USER_DB,PASS_DB);

			PreparedStatement prepStmt = null;

			String query = "UPDATE "+usuario+".PEDIDOS_CLIENTES SET FECHA_ENTREGA = NULL, ESTADO = -1 WHERE ID_PEDIDO = '"+pedido+"'";
			prepStmt = conexion.prepareStatement(query);
			prepStmt.executeUpdate();

			closeConnection(conexion);
		}

		public ArrayList<Producto> consultarExistenciasProducto(int rangoMin, int rangoMax, String etapa, Date fechaPedido, Date fechaEntrega) throws Exception{
			establecerConexion(RUTA_DB,USER_DB,PASS_DB);
			PreparedStatement prepStmt = null;
			String pedido = null;
			if(fechaPedido!=null){
			pedido = new SimpleDateFormat("YYYY-MM-dd").format(fechaPedido);
			}
			String entrega = null;
			if(fechaEntrega!=null){
			entrega = new SimpleDateFormat("YYYY-MM-dd").format(fechaEntrega);
			}
			String query = consultarExistenciasProductoQuery(rangoMin, rangoMax, etapa, pedido, entrega);
			prepStmt = conexion.prepareStatement(query);
			ResultSet rs = prepStmt.executeQuery();

			ArrayList<Producto> respuesta = new ArrayList<Producto>();
			while(rs.next()){
				Producto p = new Producto();
				p.setId(rs.getString("CODIGO"));
				p.setNombre(rs.getString("NOMBRE"));
				p.setCantidad(rs.getInt("CANT"));
				System.out.println(p.getNombre());
				System.out.println(p.getId());
				System.out.println("-------");
				respuesta.add(p);
			}
			closeConnection(conexion);
			return respuesta;
		}

		private String consultarExistenciasProductoQuery(int rangoMin, int rangoMax, String etapa, String fechaPedido, String fechaEntrega)throws Exception{
			String query = "SELECT NOMBRE, CODIGO, PRODUCTOS.CANTIDAD AS CANT, FECHA_PEDIDO, FECHA_ENTREGA, ID_ETAPA FROM "
					+ "(((PRODUCTOS LEFT OUTER JOIN CONTENIDO_PEDIDO ON PRODUCTOS.CODIGO = CONTENIDO_PEDIDO.ID_PRODUCTO)"
					+ "LEFT OUTER JOIN PEDIDOS_CLIENTES ON CONTENIDO_PEDIDO.ID_PEDIDO = PEDIDOS_CLIENTES.ID_PEDIDO)"
					+ "LEFT OUTER JOIN ESTADO_PRODUCTO ON PRODUCTOS.CODIGO = ESTADO_PRODUCTO.ID_PRODUCTO)";
			String where = "";
			if(rangoMin>=0 && rangoMax>0){
				where+="WHERE PRODUCTOS.CANTIDAD BETWEEN "+rangoMin+" AND "+rangoMax;
				if(etapa!=null){
					where+=" AND ID_ETAPA='"+etapa+"'";
					if(fechaPedido!=null){
						where+=" AND FECHA_PEDIDO=TO_DATE('"+fechaPedido+"', 'YYYY-MM-dd')";
						if(fechaEntrega!=null){
							where+=" AND FECHA_ENTREGA=TO_DATE('"+fechaEntrega+"', 'YYYY-MM-dd')";
						}
					}
				}
			}
			if(etapa!=null){
				where+="WHERE ID_ETAPA='"+etapa+"'";
				if(fechaPedido!=null){
					where+=" AND FECHA_PEDIDO=TO_DATE('"+fechaPedido+"', 'YYYY-MM-dd')";
					if(fechaEntrega!=null){
						where+=" AND FECHA_ENTREGA=TO_DATE('"+fechaEntrega+"', 'YYYY-MM-dd')";
					}
				}
			}
			if(fechaPedido!=null){
				where+="WHERE FECHA_PEDIDO=TO_DATE('"+fechaPedido+"', 'YYYY-MM-dd')";
				if(fechaEntrega!=null){
					where+=" AND FECHA_ENTREGA=TO_DATE('"+fechaEntrega+"', 'YYYY-MM-dd')";
				}
			}
			if(fechaEntrega!=null){
				where+="WHERE FECHA_ENTREGA=TO_DATE('"+fechaEntrega+"', 'YYYY-MM-dd')";
			}
			query+=where;
			return query;
		}

		public ArrayList<Componente> consultarExistenciasComponente(int rangoMin, int rangoMax, String etapa, Date fechaPedido, Date fechaEntrega) throws Exception{
			establecerConexion(RUTA_DB,USER_DB,PASS_DB);

			PreparedStatement prepStmt = null;

			String pedido = null;
			if(fechaPedido!=null){
			pedido = new SimpleDateFormat("YYYY-MM-dd").format(fechaPedido);
			}
			String entrega = null;
			if(fechaEntrega!=null){
			entrega = new SimpleDateFormat("YYYY-MM-dd").format(fechaEntrega);
			}
			String query = consultarExistenciasComponenteQuery(rangoMin, rangoMax, etapa, pedido, entrega);
			prepStmt = conexion.prepareStatement(query);
			ResultSet rs = prepStmt.executeQuery();

			ArrayList<Componente> respuesta = new ArrayList<Componente>();
			while(rs.next()){
				Componente p = new Componente();
				p.setId(rs.getString("CODIGO"));
				p.setNombre(rs.getString("NOMBRE"));
				p.setCantidad(rs.getInt("CANT"));
				System.out.println(p.getNombre());
				System.out.println(p.getId());
				respuesta.add(p);
			}
			closeConnection(conexion);
			return respuesta;
		}

		private String consultarExistenciasComponenteQuery(int rangoMin, int rangoMax, String etapa, String fechaPedido, String fechaEntrega) throws Exception{
			String query = "SELECT NOMBRE, COMPONENTES.CODIGO, COMPONENTES.CANTIDAD AS CANT, FECHA_PEDIDO, FECHA_ENTREGA, "
					+ "ETAPAS_PRODUCCION.CODIGO AS ETAPA FROM (((COMPONENTES LEFT OUTER JOIN CONTENIDO_PEDIDO_MAT "
					+ "ON COMPONENTES.CODIGO = CONTENIDO_PEDIDO_MAT.ID_COMP)LEFT OUTER JOIN PEDIDOS_PROVEEDORES ON "
					+ "CONTENIDO_PEDIDO_MAT.ID_PEDIDO = PEDIDOS_PROVEEDORES.ID_PEDIDO)LEFT OUTER JOIN ETAPAS_PRODUCCION ON "
					+ "COMPONENTES.CODIGO = ETAPAS_PRODUCCION.ID_COMP)";
			String where = "";
			if(rangoMin>=0 && rangoMax>0){
				where+="WHERE COMPONENTES.CANTIDAD BETWEEN "+rangoMin+" AND "+rangoMax;
				if(etapa!=null){
					where+=" AND ETAPAS_PRODUCCION.CODIGO='"+etapa+"'";
					if(fechaPedido!=null){
						where+=" AND FECHA_PEDIDO=TO_DATE('"+fechaPedido+"', 'YYYY-MM-dd')";
						if(fechaEntrega!=null){
							where+=" AND FECHA_ENTREGA=TO_DATE('"+fechaEntrega+"', 'YYYY-MM-dd')";
						}
					}
				}
			}
			if(etapa!=null){
				where+="WHERE ETAPAS_PRODUCCION.CODIGO='"+etapa+"'";
				if(fechaPedido!=null){
					where+=" AND FECHA_PEDIDO=TO_DATE('"+fechaPedido+"', 'YYYY-MM-dd')";
					if(fechaEntrega!=null){
						where+=" AND FECHA_ENTREGA=TO_DATE('"+fechaEntrega+"', 'YYYY-MM-dd')";
					}
				}
			}
			if(fechaPedido!=null){
				where+="WHERE FECHA_PEDIDO=TO_DATE('"+fechaPedido+"', 'YYYY-MM-dd')";
				if(fechaEntrega!=null){
					where+=" AND FECHA_ENTREGA=TO_DATE('"+fechaEntrega+"', 'YYYY-MM-dd')";
				}
			}
			if(fechaEntrega!=null){
				where+="WHERE FECHA_ENTREGA=TO_DATE('"+fechaEntrega+"', 'YYYY-MM-dd')";
			}
			query+=where;
			return query;
		}

		public ArrayList<MateriaPrima> consultarExistenciasMateria(int rangoMin, int rangoMax) throws Exception{
			establecerConexion(RUTA_DB,USER_DB,PASS_DB);

			PreparedStatement prepStmt = null;

			String query = consultarExistenciasMateriaQuery(rangoMin, rangoMax);
			prepStmt = conexion.prepareStatement(query);
			ResultSet rs = prepStmt.executeQuery();

			ArrayList<MateriaPrima> respuesta = new ArrayList<MateriaPrima>();
			while(rs.next()){
				MateriaPrima p = new MateriaPrima();
				p.setId(rs.getString("CODIGO"));
				p.setNombre(rs.getString("NOMBRE"));
				p.setCantidad(rs.getInt("CANTIDAD"));
				p.setUnidad("UNIDAD_DE_MEDIDA");

				respuesta.add(p);
			}
			closeConnection(conexion);
			return respuesta;
		}

		private String consultarExistenciasMateriaQuery(int rangoMin, int rangoMax) throws Exception{
			String query = "SELECT NOMBRE, CODIGO, UNIDAD_DE_MEDIDA, CANTIDAD FROM MATERIAS_PRIMAS";
			String where = "";
			if(rangoMin>=0 && rangoMax>0){
				where+="WHERE CANTIDAD BETWEEN "+rangoMin+" AND "+rangoMax;
			}
			where+=";";
			query+=where;
			return query;
		}

		public ConsultaProducto consultarProducto(String idprod) throws Exception{
			establecerConexion(RUTA_DB,USER_DB,PASS_DB);
			PreparedStatement prepStmt = null;
			ConsultaProducto cp = new ConsultaProducto();
			
			String query = "SELECT NOMBRE, CANTIDAD FROM PRODUCTOS WHERE CODIGO = '"+idprod+"'";
			prepStmt = conexion.prepareStatement(query);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next()){
				cp.setNombre(rs.getString("NOMBRE"));
				cp.setCantidad(rs.getInt("CANTIDAD"));
			}
			
			query = "SELECT CODIGO, NOMBRE, COMPONENTES.CANTIDAD FROM (COMPUESTO_DE_PROD "
					+ "LEFT OUTER JOIN COMPONENTES ON COMPUESTO_DE_PROD.ID_COMPONENTE = COMPONENTES.CODIGO) "
					+ "WHERE COMPUESTO_DE_PROD.ID_PRODUCTO = '"+idprod+"'";
			prepStmt = conexion.prepareStatement(query);
			rs = prepStmt.executeQuery();
			ArrayList<Componente> compuesto = new ArrayList<Componente>();
			while(rs.next()){
				Componente c = new Componente();
				c.setNombre(rs.getString("NOMBRE"));
				c.setId(rs.getString("CODIGO"));
				c.setCantidad(rs.getInt("CANTIDAD"));
				compuesto.add(c);
			}
			cp.setCompuestoDe(compuesto);
			
			query = "SELECT * FROM ETAPAS_PRODUCCION WHERE ID_PRODUCTO = '"+idprod+"'";
			prepStmt = conexion.prepareStatement(query);
			rs = prepStmt.executeQuery();
			ArrayList<EtapaProduccion> proceso = new ArrayList<EtapaProduccion>();
			while(rs.next()){
				EtapaProduccion c = new EtapaProduccion();
				c.setNombre(rs.getInt("NUM_ETAPA"));
				c.setId(rs.getString("CODIGO"));
				c.setDescripcion(rs.getString("DESCRIPCION"));
				c.setIdComp(rs.getString("ID_COMP"));
				c.setCantidad(rs.getInt("CANTIDAD"));
				c.setIdProd(rs.getString("ID_PRODUCTO"));
				proceso.add(c);
			}
			cp.setProceso(proceso);

			int numEtapas = numEtapasProducto(idprod);
			query = "SELECT COUNT(ESTADO_PRODUCTO.ID_PRODUCTO) AS NUM FROM "
					+ "(ESTADO_PRODUCTO LEFT OUTER JOIN ETAPAS_PRODUCCION ON ESTADO_PRODUCTO.ID_ETAPA = ETAPAS_PRODUCCION.CODIGO)"
					+ " WHERE ESTADO_PRODUCTO.ID_PRODUCTO = '"+idprod+"' AND NUM_ETAPA < "+numEtapas;
			prepStmt = conexion.prepareStatement(query);
			rs = prepStmt.executeQuery();
			while(rs.next()){
				cp.setEnProduccion(rs.getInt("NUM"));
			}
			
			query = "SELECT PEDIDOS_CLIENTES.ID_PEDIDO, EMAIL_CLIENTE AS CLIENTE, ESTADO, FECHA_PEDIDO, FECHA_ENTREGA FROM "
					+ "(CONTENIDO_PEDIDO LEFT OUTER JOIN PEDIDOS_CLIENTES ON CONTENIDO_PEDIDO.ID_PEDIDO = PEDIDOS_CLIENTES.ID_PEDIDO) "
					+ "WHERE ID_PRODUCTO = '"+idprod+"'";
			prepStmt = conexion.prepareStatement(query);
			rs = prepStmt.executeQuery();
			ArrayList<PedidoCliente> pedidos = new ArrayList<PedidoCliente>();
			while(rs.next()){
				PedidoCliente dc = new PedidoCliente();
				dc.setCliente(rs.getString("CLIENTE"));
				//dc.setEstado(rs.getInt("ESTADO"));
				dc.setFecha(rs.getDate("FECHA_PEDIDO"));
				dc.setIdPedido(rs.getString("ID_PEDIDO"));
				dc.setFechaEnt(rs.getDate("FECHA_ENTREGA"));
				pedidos.add(dc);
			}
			cp.setPedidosInvolucrados(pedidos);
			
			closeConnection(conexion);
			return cp;
		}

		public ConsultaComponente consultarComponente(String idcomp) throws Exception{
			establecerConexion(RUTA_DB,USER_DB,PASS_DB);
			PreparedStatement prepStmt = null;
			ConsultaComponente cc = new ConsultaComponente();
			
			String query = "SELECT NOMBRE, CANTIDAD FROM COMPONENTES WHERE CODIGO = '"+idcomp+"'";
			prepStmt = conexion.prepareStatement(query);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next()){
				cc.setNombre(rs.getString("NOMBRE"));
				cc.setCantidad(rs.getInt("CANTIDAD"));
			}
			
			query = "SELECT CODIGO, NOMBRE, UNIDAD_DE_MEDIDA, MATERIAS_PRIMAS.CANTIDAD FROM"
					+ "(COMPUESTO_DE_COMP LEFT OUTER JOIN MATERIAS_PRIMAS ON COMPUESTO_DE_COMP.ID_MAT = MATERIAS_PRIMAS.CODIGO) "
					+ "WHERE COMPUESTO_DE_COMP.ID_COMP = '"+idcomp+"'";
			prepStmt = conexion.prepareStatement(query);
			rs = prepStmt.executeQuery();
			ArrayList<MateriaPrima> compuesto = new ArrayList<MateriaPrima>();
			while(rs.next()){
				MateriaPrima c = new MateriaPrima();
				c.setNombre(rs.getString("NOMBRE"));
				c.setId(rs.getString("CODIGO"));
				c.setCantidad(rs.getInt("CANTIDAD"));
				c.setUnidad("UNIDAD_DE_MEDIDA");
				compuesto.add(c);
			}
			cc.setCompuestoDe(compuesto);
			
			query = "SELECT * FROM ETAPAS_PRODUCCION WHERE ID_COMP = '"+idcomp+"'";
			prepStmt = conexion.prepareStatement(query);
			rs = prepStmt.executeQuery();
			ArrayList<EtapaProduccion> proceso = new ArrayList<EtapaProduccion>();
			while(rs.next()){
				EtapaProduccion c = new EtapaProduccion();
				c.setNombre(rs.getInt("NUM_ETAPA"));
				c.setId(rs.getString("CODIGO"));
				c.setDescripcion(rs.getString("DESCRIPCION"));
				c.setIdComp(rs.getString("ID_COMP"));
				c.setCantidad(rs.getInt("CANTIDAD"));
				c.setIdProd(rs.getString("ID_PRODUCTO"));
				proceso.add(c);
			}
			cc.setEtapas(proceso);
			
			query = "SELECT PEDIDOS_PROVEEDORES.ID_PEDIDO, EMAIL_PROVEEDOR AS PROV, ESTADO, FECHA_PEDIDO, FECHA_ENTREGA FROM "
					+ "(CONTENIDO_PEDIDO_MAT LEFT OUTER JOIN PEDIDOS_PROVEEDORES ON CONTENIDO_PEDIDO_MAT.ID_PEDIDO = PEDIDOS_PROVEEDORES.ID_PEDIDO) "
					+ "WHERE ID_COMP = '"+idcomp+"'";
			prepStmt = conexion.prepareStatement(query);
			rs = prepStmt.executeQuery();
			ArrayList<PedidoProveedor> pedidos = new ArrayList<PedidoProveedor>();
			while(rs.next()){
				PedidoProveedor dc = new PedidoProveedor();
				dc.setProveedor(rs.getString("PROV"));
				dc.setEstado(rs.getInt("ESTADO"));
				dc.setFecha(rs.getDate("FECHA_PEDIDO"));
				dc.setIdPedido(rs.getString("ID_PEDIDO"));
				dc.setFechaEnt(rs.getDate("FECHA_ENTREGA"));
				pedidos.add(dc);
			}
			cc.setPedidos(pedidos);
			
			query = "SELECT CODIGO, NOMBRE, PRODUCTOS.CANTIDAD FROM (COMPUESTO_DE_PROD "
					+ "LEFT OUTER JOIN PRODUCTOS ON COMPUESTO_DE_PROD.ID_PRODUCTO = PRODUCTOS.CODIGO) WHERE ID_COMPONENTE = '"+idcomp+"'";
			prepStmt = conexion.prepareStatement(query);
			rs = prepStmt.executeQuery();
			ArrayList<Producto> compone = new ArrayList<Producto>();
			while(rs.next()){
				Producto p = new Producto();
				p.setNombre(rs.getString("NOMBRE"));
				p.setId(rs.getString("CODIGO"));
				p.setCantidad(rs.getInt("CANTIDAD"));
				compone.add(p);
			}
			cc.setCompone(compone);
			
			closeConnection(conexion);
			return cc;
		}
		
		public ConsultaMateria consultaMateriaPrima(String idmat) throws Exception{
			establecerConexion(RUTA_DB,USER_DB,PASS_DB);
			PreparedStatement prepStmt = null;
			ConsultaMateria cm = new ConsultaMateria();
			
			String query = "SELECT NOMBRE, CANTIDAD, UNIDAD_DE_MEDIDA  FROM MATERIAS_PRIMAS WHERE CODIGO = '"+idmat+"'";
			prepStmt = conexion.prepareStatement(query);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next()){
				cm.setNombre(rs.getString("NOMBRE"));
				cm.setCantidad(rs.getInt("CANTIDAD"));
				cm.setUm(rs.getString("UNIDAD_DE_MEDIDA"));
			}
			
			query = "SELECT CODIGO, NOMBRE, COMPONENTES.CANTIDAD FROM (COMPUESTO_DE_COMP "
					+ "LEFT OUTER JOIN COMPONENTES ON COMPUESTO_DE_COMP.ID_COMP = COMPONENTES.CODIGO) WHERE ID_MAT = '"+idmat+"'";
			prepStmt = conexion.prepareStatement(query);
			rs = prepStmt.executeQuery();
			ArrayList<Componente> compone = new ArrayList<Componente>();
			while(rs.next()){
				Componente c = new Componente();
				c.setNombre(rs.getString("NOMBRE"));
				c.setId(rs.getString("CODIGO"));
				c.setCantidad(rs.getInt("CANTIDAD"));
				compone.add(c);
			}
			cm.setCompone(compone);
			
			closeConnection(conexion);
			return cm;
		}

	
	
}
