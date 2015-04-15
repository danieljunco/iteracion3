package prodAndes.fachada;


import java.util.ArrayList;
import java.util.Date;

import prodAndes.dao.ConsultaDAO;
import prodAndes.vos.Cliente;
import prodAndes.vos.ConsultaComponente;
import prodAndes.vos.ConsultaMateria;
import prodAndes.vos.ConsultaProducto;
import prodAndes.vos.MateriaPrima;
import prodAndes.vos.PedidoCliente;
import prodAndes.vos.Producto;
import prodAndes.vos.ProductoCantidad;
import prodAndes.vos.Proveedor;
import prodAndes.vos.ReqPedidoCliente;
import prodAndes.vos.ReqPedidoProveedor;

public class ProdAndes {
	
	

	/**
	 * Conexion con la clase que maneja la base de datos
	 */
	
	private ConsultaDAO dao;
	
	// -----------------------------------------------------------------
    // Singleton
    // -----------------------------------------------------------------
	
	/**
     * Instancia unica de la clase
     */
	private static ProdAndes instancia;
	
	/**
     * Devuelve la instancia unica de la clase
     * @return Instancia unica de la clase
     */
    public static ProdAndes darInstancia( )
    {
        if( instancia == null )
        {
            instancia = new ProdAndes( );
        }
        return instancia;
    }
    
    private ProdAndes()
    {
    	dao = new ConsultaDAO();
    	dao.inicializar();
    }
    
    /**
	 * inicializa el dao, dndole la ruta en donde debe encontrar
	 * el archivo properties
	 */
	public void inicializarRuta()
	{
		dao.inicializar();
	}
	
	public ArrayList<Producto> mostrarProductos() throws Exception{
		return dao.mostrarProductos();
	}
	
	public void registrarProduccion(String idetapa, Date fechaFinalizacion) throws Exception{
		dao.registrarProduccionEtapa(idetapa, fechaFinalizacion);
	}
	
	public void registrarLlegadaMaterial(String pedido) throws Exception{
		dao.registrarLlegadaMaterial(pedido);
	}
	
	public void registrarEntregaPedido(String pedido) throws Exception{
		dao.registrarEntregaPedido(pedido);
	}
	
	public void registrarPedidoProductos(ArrayList<ProductoCantidad> productos, String idped, String ecliente) throws Exception{
		dao.registrarPedidoProductos(productos, idped, ecliente);
	}
	
	public void consultarExistenciasProductos(int rangoMin, int rangoMax, String etapa, Date fechaPedido, Date fechaEntrega) throws Exception{
		dao.consultarExistenciasProducto(rangoMin, rangoMax, etapa, fechaPedido, fechaEntrega);
	}
	
	public void consultarExistenciasComponente(int rangoMin, int rangoMax, String etapa, Date fechaPedido, Date fechaEntrega) throws Exception{
		dao.consultarExistenciasComponente(rangoMin, rangoMax, etapa, fechaPedido, fechaEntrega);
	}
	
	public void consultarExistenciasMateria(int rangoMin, int rangoMax) throws Exception{
		dao.consultarExistenciasMateria(rangoMin, rangoMax);
	}
	
	public ConsultaProducto consultarProducto(String id) throws Exception{
		return dao.consultarProducto(id);
	}
	
	public ConsultaComponente consultarComponente(String id) throws Exception{
		return dao.consultarComponente(id);
	}
	
	public ConsultaMateria consultarMaterial(String id) throws Exception{
		return dao.consultaMateriaPrima(id);
	}
	public ArrayList<String> consultarNombreProductos()throws Exception{
		return dao.consultarNombreProductos();
	}
	public ArrayList<Producto> consultarExistenciasPorNombre(String nombre) throws Exception{
		return dao.consultarExistenciasProductoPorNombre(nombre);
	}
	public ArrayList<MateriaPrima> consultarExistenciasMata() throws Exception{
		return dao.consultarExistenciasMata();
	}
	public ArrayList<PedidoCliente> darPedidosClientes() throws Exception{
		return dao.darPedidosCliente();
	}
	public void cancelarPedidos(String idPedido) throws Exception{
		dao.cancelarPedidoCliente(idPedido);
	}
	public ArrayList<ReqPedidoCliente> consultarPedidosClientes() throws Exception{
		return dao.consultarPedidosCliente();
	}
	public ArrayList<Cliente> consultarClientes() throws Exception{
		return dao.consultarClientes();
	}
	public ArrayList<Cliente> consultarClienteFiltroEmail(String email) throws Exception{
		return dao.consultarClienteFiltroEmail(email);
	}
	public ArrayList<Cliente> consultarClienteFiltroNacionalidad(String pais) throws Exception{
		return dao.consultarClienteFiltroPasi(pais);
	}
	public ArrayList<Cliente> consultarClienteFiltroIdPedido(String idPedido) throws Exception{
		return dao.consultarClienteFiltroIdPedido(idPedido);
	}
	public ArrayList<Proveedor> consultarProveedores() throws Exception{
		return dao.consultarProveedores();
	}
	public ArrayList<Proveedor> consultarProveedoresPorEmail(String email) throws Exception{
		return dao.consultarProveedoresPorEmail(email);
	}
	public ArrayList<Proveedor> consultarProveedoresPorDocumento(String documento) throws Exception{
		return dao.consultarProveedoresPorDocumento(documento);
	}
	public ArrayList<Proveedor> consultarProveedoresPorMateria(String materia) throws Exception{
		return dao.consultarProveedoresPorMateria(materia);
	}
	public ArrayList<ReqPedidoCliente> consultarPedidosClienteRangoFecha(String fechaPedido, String fechaEntrega) throws Exception{
		return dao.consultarPedidosClienteRangoFecha(fechaPedido, fechaEntrega);
	}
	public ArrayList<ReqPedidoCliente> consultarPedidoClientesXNombre(String nombre) throws Exception{
		return dao.consultarPedidosClienteXNombre(nombre);
	}
	public ArrayList<ReqPedidoCliente> consultarPedidosCleintesXEmail(String email) throws Exception{
		return dao.consultarPedidosClienteXEmail(email);
	}
	public ArrayList<ReqPedidoProveedor> consultarPedidosProveedor() throws Exception{
		return dao.consultarPedidosProveedor();
	}
	public ArrayList<ReqPedidoProveedor> consultarPedidosProveedorXEstado(String estado) throws Exception{
		return dao.consultarPedidosProveedorXEstado(estado);
	}
	public ArrayList<ReqPedidoProveedor> consultarPedidosProveedorXRangoFecha(String fechaPedido, String fechaEntrega) throws Exception{
		return dao.consultarPedidosProveedorXRangoFecha(fechaPedido, fechaEntrega);
	}
	public ArrayList<ReqPedidoProveedor> consultarPedidosProveedorXMateriaRequerida(String nameMateria) throws Exception{
		return dao.consultarPedidosProveedorXMateriaRequerida(nameMateria);
	}
}
