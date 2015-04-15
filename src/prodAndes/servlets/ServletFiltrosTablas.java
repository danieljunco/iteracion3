package prodAndes.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prodAndes.fachada.ProdAndes;
import prodAndes.vos.Cliente;
import prodAndes.vos.Proveedor;
import prodAndes.vos.ReqPedidoCliente;
import prodAndes.vos.ReqPedidoProveedor;

public class ServletFiltrosTablas extends ServletTemplate{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void escribirContenido(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter respuesta = response.getWriter();
		String emailCliente= request.getParameter("emailCliente");
		String pais = request.getParameter("nacionalidad");
		String idPedido = request.getParameter("idPedido");
		String emailProveedor = request.getParameter("emailProveedor");
		String documentoProveedor = request.getParameter("documentoProveedor");
		String nombreMateria = request.getParameter("matProvee");
		String fechaPedido = request.getParameter("fechaPedido");
		String fechaEntrega = request.getParameter("fechaEntrega");
		String nombreProducto = request.getParameter("nombreProducto");
		String emailCliente2 = request.getParameter("emailCliente2");
		String estado = request.getParameter("estado");
		String fechaPedidoProveedores = request.getParameter("fechaPedidoProveedores");
		String fechaEntregaProveedores = request.getParameter("fechaEntregaProveedores");
		String materialRequerido = request.getParameter("materialRequerido");
		if(emailCliente!=null){
			try{
				imprimirTablasClientes(respuesta, emailCliente);
			}
			catch (Exception e) {
				e.printStackTrace();
				imprimirError(response, "Error en clientes");
			}
		}
		else if(pais!=null){
			try {
				imprimiTablasClientesNacionalidad(respuesta, pais);
			} catch (Exception e) {
				e.printStackTrace();
				imprimirError(response, "Error en clientes");
			}
		}
		else if (idPedido!= null){
			try {
				imprimirTablasClientesIdPedido(respuesta, idPedido);
			} catch (Exception e) {
				e.printStackTrace();
				imprimirError(response, "Error en clientes");
			}
		}
		else if(emailProveedor!=null){
			try {
				imprimirTablasProveedorEmail(respuesta, emailProveedor);
			} catch (Exception e) {
				e.printStackTrace();
				imprimirError(response, "Error en proveedores");
			}
		}
		else if(documentoProveedor!=null){
			try {
				imprimirTablasProveedorDcumento(respuesta, documentoProveedor);
			} catch (Exception e) {
				e.printStackTrace();
				imprimirError(response, "Error en proveedores");
			}
		}
		else if(nombreMateria!=null){
			try {
				imprimirTablasProveedorMateria(respuesta, nombreMateria);
			} catch (Exception e) {
				e.printStackTrace();
				imprimirError(response, "Error en proveedores");
			}
		}
		else if(fechaPedido!=null && fechaEntrega!=null){
			try {
				imprimirTablasPedidosClientesRangoFecha(respuesta, fechaPedido, fechaEntrega);
				System.out.println(fechaPedido+"-El otro-"+fechaEntrega);
			} catch (Exception e) {
				e.printStackTrace();
				imprimirError(response, "Error en pedidosClientes");
			}
		}
		else if(nombreProducto!=null){
			try {
				imprimirTablasPedidosClientesXNombre(respuesta, nombreProducto);
			} catch (Exception e) {
				e.printStackTrace();
				imprimirError(response, "Error en pedidosClientes");
			}
		}
		else if(emailCliente2!=null){
			try {
				imprimirTablasPedidosClientesXEmail(respuesta, emailCliente2);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		else if(estado!=null){
			try {
				imprimirTablasPedidosProveedoresXEstado(respuesta, estado);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		else if(fechaPedidoProveedores!=null && fechaEntregaProveedores!=null){
			try {
				imprimirTablasPedidosProveedoresXRangoFecha(respuesta, fechaPedidoProveedores, fechaEntregaProveedores);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		else if(materialRequerido!=null){
			try {
				imprimirTablasPedidosProveedoresXMateriaRequerida(respuesta, materialRequerido);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	
}
	
	private void imprimirTablasClientes(PrintWriter respuesta, String email) throws Exception{
		
		try {
			
			ArrayList<Cliente> cliente = ProdAndes.darInstancia().consultarClienteFiltroEmail(email);
			respuesta.println("<div id=\"page-wrapper\">");
			respuesta.println("");
			respuesta.println("            <div class=\"container-fluid\">");
			respuesta.println("");
			respuesta.println("                <!-- Page Heading -->");
			respuesta.println("                <div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-12\">");
			respuesta.println("                        <h1 class=\"page-header\">");
			respuesta.println("                            RFC6 - Consultar Clientes");
			respuesta.println("                        </h1>");
			respuesta.println("                        <ol class=\"breadcrumb\">");
			respuesta.println("                            <li>");
			respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"RFC6.htm\">Atras</a>");
			respuesta.println("                            </li>");
			respuesta.println("                            <li class=\"active\">");
			respuesta.println("                                <i class=\"fa fa-table\"></i> Filtro Email");
			respuesta.println("                            </li>");
			respuesta.println("                        </ol>");
			respuesta.println("                    </div>");
			respuesta.println("                </div>");
			
			respuesta.println("<ol class=\"breadcrumb\">");
			respuesta.println("                        <h2>");
			respuesta.println("                            Filtros");
			respuesta.println("                        </h2>");
			respuesta.println("<div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-4\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <label>Email</label>");
			respuesta.println("                                <input class=\"form-control\" name=\"emailCliente\" placeholder=\"Ingrese Email del cliente\">");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                    <div class=\"col-lg-4\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletTablasProductos.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <label>Nacionalidad</label>");
			respuesta.println("                                <input class=\"form-control\" name=\"nacionalidad\" placeholder=\"Ingrese pais\">");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                    <div class=\"col-lg-4\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <label>Id Pedido</label>");
			respuesta.println("                                <input class=\"form-control\" name=\"idPedido\" placeholder=\"Ingrese pedido\">");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                            </div>");
			respuesta.println("                        </ol>");
			
			respuesta.println("<div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-12\">");
			respuesta.println("                        <h2>Tabla Clientes</h2>");
			respuesta.println("                        <div class=\"table-responsive\">");
			respuesta.println("                            <table class=\"table table-bordered table-hover\">");
			respuesta.println("                                <thead>");
			respuesta.println("                                    <tr>");
			respuesta.println("                                        <th>Email</th>");
			respuesta.println("                                        <th>Nacionalidad</th>");
			respuesta.println("                                        <th>Direccion</th>");
			respuesta.println("                                        <th>Telefono</th>");
			respuesta.println("                                        <th>Ciudad</th>");
			respuesta.println("                                        <th>Numero Documento</th>");
			respuesta.println("                                        <th>Id Pedido</th>");
			respuesta.println("                                    </tr>");
			respuesta.println("                                </thead>");
			respuesta.println("                                <tbody>");
			for (int i = 0; i < cliente.size(); i++) 
			{
				Cliente x = cliente.get(i);
				respuesta.println("                                <tr>");
				respuesta.println("                                        <td>"+x.getCorreo()+"</td>");
				respuesta.println("                                        <td>"+x.getPais()+"</td>");
				respuesta.println("                                        <td>"+x.getDireccion()+"</td>");
				respuesta.println("                                        <td>"+x.getTelefono()+"</td>");
				respuesta.println("                                        <td>"+x.getCiudad()+"</td>");
				respuesta.println("                                        <td>"+x.getIdentificacion()+"</td>");
				respuesta.println("                                        <td>"+x.getPedido()+"</td>");
				respuesta.println("                                </tr>");

			}
			
			respuesta.println("                                </tbody>");
			respuesta.println("                            </table>");
			respuesta.println("                         </div>");
			respuesta.println("                      </div>");
			respuesta.println("                 </div>");
		
	}catch (Exception e) {
		// TODO: handle exception
	}
	}
	
	private void imprimiTablasClientesNacionalidad(PrintWriter respuesta, String pais){
		try {
			
			ArrayList<Cliente> cliente = ProdAndes.darInstancia().consultarClienteFiltroNacionalidad(pais);
			respuesta.println("<div id=\"page-wrapper\">");
			respuesta.println("");
			respuesta.println("            <div class=\"container-fluid\">");
			respuesta.println("");
			respuesta.println("                <!-- Page Heading -->");
			respuesta.println("                <div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-12\">");
			respuesta.println("                        <h1 class=\"page-header\">");
			respuesta.println("                            RFC6 - Consultar Clientes");
			respuesta.println("                        </h1>");
			respuesta.println("                        <ol class=\"breadcrumb\">");
			respuesta.println("                            <li>");
			respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"RFC6.htm\">Atras</a>");
			respuesta.println("                            </li>");
			respuesta.println("                            <li class=\"active\">");
			respuesta.println("                                <i class=\"fa fa-table\"></i> Filtro Email");
			respuesta.println("                            </li>");
			respuesta.println("                        </ol>");
			respuesta.println("                    </div>");
			respuesta.println("                </div>");
			
			respuesta.println("<ol class=\"breadcrumb\">");
			respuesta.println("                        <h2>");
			respuesta.println("                            Filtros");
			respuesta.println("                        </h2>");
			respuesta.println("<div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-4\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <label>Email</label>");
			respuesta.println("                                <input class=\"form-control\" name=\"emailCliente\" placeholder=\"Ingrese Email del cliente\">");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                    <div class=\"col-lg-4\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletTablasProductos.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <label>Nacionalidad</label>");
			respuesta.println("                                <input class=\"form-control\" name=\"nacionalidad\" placeholder=\"Ingrese pais\">");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                    <div class=\"col-lg-4\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <label>Id Pedido</label>");
			respuesta.println("                                <input class=\"form-control\" name=\"idPedido\" placeholder=\"Ingrese pedido\">");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                            </div>");
			respuesta.println("                        </ol>");
			
			respuesta.println("<div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-12\">");
			respuesta.println("                        <h2>Tabla Clientes</h2>");
			respuesta.println("                        <div class=\"table-responsive\">");
			respuesta.println("                            <table class=\"table table-bordered table-hover\">");
			respuesta.println("                                <thead>");
			respuesta.println("                                    <tr>");
			respuesta.println("                                        <th>Email</th>");
			respuesta.println("                                        <th>Nacionalidad</th>");
			respuesta.println("                                        <th>Direccion</th>");
			respuesta.println("                                        <th>Telefono</th>");
			respuesta.println("                                        <th>Ciudad</th>");
			respuesta.println("                                        <th>Numero Documento</th>");
			respuesta.println("                                        <th>Id Pedido</th>");
			respuesta.println("                                    </tr>");
			respuesta.println("                                </thead>");
			respuesta.println("                                <tbody>");
			for (int i = 0; i < cliente.size(); i++) 
			{
				Cliente x = cliente.get(i);
				respuesta.println("                                <tr>");
				respuesta.println("                                        <td>"+x.getCorreo()+"</td>");
				respuesta.println("                                        <td>"+x.getPais()+"</td>");
				respuesta.println("                                        <td>"+x.getDireccion()+"</td>");
				respuesta.println("                                        <td>"+x.getTelefono()+"</td>");
				respuesta.println("                                        <td>"+x.getCiudad()+"</td>");
				respuesta.println("                                        <td>"+x.getIdentificacion()+"</td>");
				respuesta.println("                                        <td>"+x.getPedido()+"</td>");
				respuesta.println("                                </tr>");

			}
			
			respuesta.println("                                </tbody>");
			respuesta.println("                            </table>");
			respuesta.println("                         </div>");
			respuesta.println("                      </div>");
			respuesta.println("                 </div>");
	}catch (Exception e) {
		// TODO: handle exception
	}
	}

	private void imprimirTablasClientesIdPedido(PrintWriter respuesta, String idPedido) throws Exception{
try {
			
			ArrayList<Cliente> cliente = ProdAndes.darInstancia().consultarClienteFiltroIdPedido(idPedido);
			respuesta.println("<div id=\"page-wrapper\">");
			respuesta.println("");
			respuesta.println("            <div class=\"container-fluid\">");
			respuesta.println("");
			respuesta.println("                <!-- Page Heading -->");
			respuesta.println("                <div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-12\">");
			respuesta.println("                        <h1 class=\"page-header\">");
			respuesta.println("                            RFC6 - Consultar Clientes");
			respuesta.println("                        </h1>");
			respuesta.println("                        <ol class=\"breadcrumb\">");
			respuesta.println("                            <li>");
			respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"RFC6.htm\">Atras</a>");
			respuesta.println("                            </li>");
			respuesta.println("                            <li class=\"active\">");
			respuesta.println("                                <i class=\"fa fa-table\"></i> Filtro Email");
			respuesta.println("                            </li>");
			respuesta.println("                        </ol>");
			respuesta.println("                    </div>");
			respuesta.println("                </div>");
			
			respuesta.println("<ol class=\"breadcrumb\">");
			respuesta.println("                        <h2>");
			respuesta.println("                            Filtros");
			respuesta.println("                        </h2>");
			respuesta.println("<div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-4\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <label>Email</label>");
			respuesta.println("                                <input class=\"form-control\" name=\"emailCliente\" placeholder=\"Ingrese Email del cliente\">");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                    <div class=\"col-lg-4\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletTablasProductos.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <label>Nacionalidad</label>");
			respuesta.println("                                <input class=\"form-control\" name=\"nacionalidad\" placeholder=\"Ingrese pais\">");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                    <div class=\"col-lg-4\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <label>Id Pedido</label>");
			respuesta.println("                                <input class=\"form-control\" name=\"idPedido\" placeholder=\"Ingrese pedido\">");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                            </div>");
			respuesta.println("                        </ol>");
			
			respuesta.println("<div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-12\">");
			respuesta.println("                        <h2>Tabla Clientes</h2>");
			respuesta.println("                        <div class=\"table-responsive\">");
			respuesta.println("                            <table class=\"table table-bordered table-hover\">");
			respuesta.println("                                <thead>");
			respuesta.println("                                    <tr>");
			respuesta.println("                                        <th>Email</th>");
			respuesta.println("                                        <th>Nacionalidad</th>");
			respuesta.println("                                        <th>Direccion</th>");
			respuesta.println("                                        <th>Telefono</th>");
			respuesta.println("                                        <th>Ciudad</th>");
			respuesta.println("                                        <th>Numero Documento</th>");
			respuesta.println("                                        <th>Id Pedido</th>");
			respuesta.println("                                    </tr>");
			respuesta.println("                                </thead>");
			respuesta.println("                                <tbody>");
			for (int i = 0; i < cliente.size(); i++) 
			{
				Cliente x = cliente.get(i);
				respuesta.println("                                <tr>");
				respuesta.println("                                        <td>"+x.getCorreo()+"</td>");
				respuesta.println("                                        <td>"+x.getPais()+"</td>");
				respuesta.println("                                        <td>"+x.getDireccion()+"</td>");
				respuesta.println("                                        <td>"+x.getTelefono()+"</td>");
				respuesta.println("                                        <td>"+x.getCiudad()+"</td>");
				respuesta.println("                                        <td>"+x.getIdentificacion()+"</td>");
				respuesta.println("                                        <td>"+x.getPedido()+"</td>");
				respuesta.println("                                </tr>");

			}
			
			respuesta.println("                                </tbody>");
			respuesta.println("                            </table>");
			respuesta.println("                         </div>");
			respuesta.println("                      </div>");
			respuesta.println("                 </div>");
	}catch (Exception e) {
		// TODO: handle exception
	}
	}
	
	private void imprimirTablasProveedorEmail(PrintWriter respuesta, String email) throws Exception{
		try {
					
					ArrayList<Proveedor> proveedor = ProdAndes.darInstancia().consultarProveedoresPorEmail(email);
					respuesta.println("<div id=\"page-wrapper\">");
					respuesta.println("");
					respuesta.println("            <div class=\"container-fluid\">");
					respuesta.println("");
					respuesta.println("                <!-- Page Heading -->");
					respuesta.println("                <div class=\"row\">");
					respuesta.println("                    <div class=\"col-lg-12\">");
					respuesta.println("                        <h1 class=\"page-header\">");
					respuesta.println("                            RFC7 - Consultar Proveedores");
					respuesta.println("                        </h1>");
					respuesta.println("                        <ol class=\"breadcrumb\">");
					respuesta.println("                            <li>");
					respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"RFC7.htm\">Atras</a>");
					respuesta.println("                            </li>");
					respuesta.println("                            <li class=\"active\">");
					respuesta.println("                                <i class=\"fa fa-table\"></i> Filtro Email");
					respuesta.println("                            </li>");
					respuesta.println("                        </ol>");
					respuesta.println("                    </div>");
					respuesta.println("                </div>");
					
					respuesta.println("<ol class=\"breadcrumb\">");
					respuesta.println("                        <h2>");
					respuesta.println("                            Filtros");
					respuesta.println("                        </h2>");
					respuesta.println("<div class=\"row\">");
					respuesta.println("                    <div class=\"col-lg-4\">");
					respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
					respuesta.println("");
					respuesta.println("                            <div class=\"form-group\">");
					respuesta.println("                                <label>Email</label>");
					respuesta.println("                                <input class=\"form-control\" name=\"emailProveedor\" placeholder=\"Ingrese Email del proveedor\">");
					respuesta.println("                            </div>");
					respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
					respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
					respuesta.println("</form>");
					respuesta.println("                            </div>");
					respuesta.println("                    <div class=\"col-lg-4\">");
					respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
					respuesta.println("");
					respuesta.println("                            <div class=\"form-group\">");
					respuesta.println("                                <label>Numero Documento</label>");
					respuesta.println("                                <input class=\"form-control\" name=\"documentoProveedor\" placeholder=\"Ingrese documento\">");
					respuesta.println("                            </div>");
					respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
					respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
					respuesta.println("</form>");
					respuesta.println("                            </div>");
					respuesta.println("                    <div class=\"col-lg-4\">");
					respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
					respuesta.println("");
					respuesta.println("                            <div class=\"form-group\">");
					respuesta.println("                                <label>Materia que provee</label>");
					respuesta.println("                                <input class=\"form-control\" name=\"matProvee\" placeholder=\"Ingrese Materia\">");
					respuesta.println("                            </div>");
					respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
					respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
					respuesta.println("</form>");
					respuesta.println("                            </div>");
					respuesta.println("                            </div>");
					respuesta.println("                        </ol>");
					
					respuesta.println("<div class=\"row\">");
					respuesta.println("                    <div class=\"col-lg-12\">");
					respuesta.println("                        <h2>Tabla Clientes</h2>");
					respuesta.println("                        <div class=\"table-responsive\">");
					respuesta.println("                            <table class=\"table table-bordered table-hover\">");
					respuesta.println("                                <thead>");
					respuesta.println("                                    <tr>");
					respuesta.println("                                        <th>Email</th>");
					respuesta.println("                                        <th>Nacionalidad</th>");
					respuesta.println("                                        <th>Direccion</th>");
					respuesta.println("                                        <th>Numero Documento</th>");
					respuesta.println("                                        <th>Id Pedido</th>");
					respuesta.println("                                        <th>Estado</th>");
					respuesta.println("                                        <th>Materia Provee</th>");
					respuesta.println("                                    </tr>");
					respuesta.println("                                </thead>");
					respuesta.println("                                <tbody>");
					for (int i = 0; i < proveedor.size(); i++) 
					{
						Proveedor x = proveedor.get(i);
						respuesta.println("                                <tr>");
						respuesta.println("                                        <td>"+x.getCorreo()+"</td>");
						respuesta.println("                                        <td>"+x.getPais()+"</td>");
						respuesta.println("                                        <td>"+x.getDireccion()+"</td>");
						respuesta.println("                                        <td>"+x.getIdentificacion()+"</td>");
						respuesta.println("                                        <td>"+x.getIdPedido()+"</td>");
						respuesta.println("                                        <td>"+x.getIdentificacion()+"</td>");
						respuesta.println("                                        <td>"+x.getNombreMateria()+"</td>");
						respuesta.println("                                </tr>");

					}
					
					respuesta.println("                                </tbody>");
					respuesta.println("                            </table>");
					respuesta.println("                         </div>");
					respuesta.println("                      </div>");
					respuesta.println("                 </div>");
			}catch (Exception e) {
				// TODO: handle exception
			}
			}
	
	private void imprimirTablasProveedorDcumento(PrintWriter respuesta, String documento) throws Exception{
		try {
					
					ArrayList<Proveedor> proveedor = ProdAndes.darInstancia().consultarProveedoresPorDocumento(documento);
					respuesta.println("<div id=\"page-wrapper\">");
					respuesta.println("");
					respuesta.println("            <div class=\"container-fluid\">");
					respuesta.println("");
					respuesta.println("                <!-- Page Heading -->");
					respuesta.println("                <div class=\"row\">");
					respuesta.println("                    <div class=\"col-lg-12\">");
					respuesta.println("                        <h1 class=\"page-header\">");
					respuesta.println("                            RFC7 - Consultar Proveedores");
					respuesta.println("                        </h1>");
					respuesta.println("                        <ol class=\"breadcrumb\">");
					respuesta.println("                            <li>");
					respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"RFC7.htm\">Atras</a>");
					respuesta.println("                            </li>");
					respuesta.println("                            <li class=\"active\">");
					respuesta.println("                                <i class=\"fa fa-table\"></i> Filtro Documento");
					respuesta.println("                            </li>");
					respuesta.println("                        </ol>");
					respuesta.println("                    </div>");
					respuesta.println("                </div>");
					
					respuesta.println("<ol class=\"breadcrumb\">");
					respuesta.println("                        <h2>");
					respuesta.println("                            Filtros");
					respuesta.println("                        </h2>");
					respuesta.println("<div class=\"row\">");
					respuesta.println("                    <div class=\"col-lg-4\">");
					respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
					respuesta.println("");
					respuesta.println("                            <div class=\"form-group\">");
					respuesta.println("                                <label>Email</label>");
					respuesta.println("                                <input class=\"form-control\" name=\"emailProveedor\" placeholder=\"Ingrese Email del proveedor\">");
					respuesta.println("                            </div>");
					respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
					respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
					respuesta.println("</form>");
					respuesta.println("                            </div>");
					respuesta.println("                    <div class=\"col-lg-4\">");
					respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
					respuesta.println("");
					respuesta.println("                            <div class=\"form-group\">");
					respuesta.println("                                <label>Numero Documento</label>");
					respuesta.println("                                <input class=\"form-control\" name=\"documentoProveedor\" placeholder=\"Ingrese documento\">");
					respuesta.println("                            </div>");
					respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
					respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
					respuesta.println("</form>");
					respuesta.println("                            </div>");
					respuesta.println("                    <div class=\"col-lg-4\">");
					respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
					respuesta.println("");
					respuesta.println("                            <div class=\"form-group\">");
					respuesta.println("                                <label>Materia que provee</label>");
					respuesta.println("                                <input class=\"form-control\" name=\"matProvee\" placeholder=\"Ingrese Materia\">");
					respuesta.println("                            </div>");
					respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
					respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
					respuesta.println("</form>");
					respuesta.println("                            </div>");
					respuesta.println("                            </div>");
					respuesta.println("                        </ol>");
					
					respuesta.println("<div class=\"row\">");
					respuesta.println("                    <div class=\"col-lg-12\">");
					respuesta.println("                        <h2>Tabla Clientes</h2>");
					respuesta.println("                        <div class=\"table-responsive\">");
					respuesta.println("                            <table class=\"table table-bordered table-hover\">");
					respuesta.println("                                <thead>");
					respuesta.println("                                    <tr>");
					respuesta.println("                                        <th>Email</th>");
					respuesta.println("                                        <th>Nacionalidad</th>");
					respuesta.println("                                        <th>Direccion</th>");
					respuesta.println("                                        <th>Numero Documento</th>");
					respuesta.println("                                        <th>Id Pedido</th>");
					respuesta.println("                                        <th>Estado</th>");
					respuesta.println("                                        <th>Materia Provee</th>");
					respuesta.println("                                    </tr>");
					respuesta.println("                                </thead>");
					respuesta.println("                                <tbody>");
					for (int i = 0; i < proveedor.size(); i++) 
					{
						Proveedor x = proveedor.get(i);
						respuesta.println("                                <tr>");
						respuesta.println("                                        <td>"+x.getCorreo()+"</td>");
						respuesta.println("                                        <td>"+x.getPais()+"</td>");
						respuesta.println("                                        <td>"+x.getDireccion()+"</td>");
						respuesta.println("                                        <td>"+x.getIdentificacion()+"</td>");
						respuesta.println("                                        <td>"+x.getIdPedido()+"</td>");
						respuesta.println("                                        <td>"+x.getIdentificacion()+"</td>");
						respuesta.println("                                        <td>"+x.getNombreMateria()+"</td>");
						respuesta.println("                                </tr>");

					}
					
					respuesta.println("                                </tbody>");
					respuesta.println("                            </table>");
					respuesta.println("                         </div>");
					respuesta.println("                      </div>");
					respuesta.println("                 </div>");
			}catch (Exception e) {
				// TODO: handle exception
			}
			}
	private void imprimirTablasProveedorMateria(PrintWriter respuesta, String materia) throws Exception{
		try {
					
					ArrayList<Proveedor> proveedor = ProdAndes.darInstancia().consultarProveedoresPorMateria(materia);
					respuesta.println("<div id=\"page-wrapper\">");
					respuesta.println("");
					respuesta.println("            <div class=\"container-fluid\">");
					respuesta.println("");
					respuesta.println("                <!-- Page Heading -->");
					respuesta.println("                <div class=\"row\">");
					respuesta.println("                    <div class=\"col-lg-12\">");
					respuesta.println("                        <h1 class=\"page-header\">");
					respuesta.println("                            RFC7 - Consultar Proveedores");
					respuesta.println("                        </h1>");
					respuesta.println("                        <ol class=\"breadcrumb\">");
					respuesta.println("                            <li>");
					respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"RFC7.htm\">Atras</a>");
					respuesta.println("                            </li>");
					respuesta.println("                            <li class=\"active\">");
					respuesta.println("                                <i class=\"fa fa-table\"></i> Filtro Materia que provee");
					respuesta.println("                            </li>");
					respuesta.println("                        </ol>");
					respuesta.println("                    </div>");
					respuesta.println("                </div>");
					
					respuesta.println("<ol class=\"breadcrumb\">");
					respuesta.println("                        <h2>");
					respuesta.println("                            Filtros");
					respuesta.println("                        </h2>");
					respuesta.println("<div class=\"row\">");
					respuesta.println("                    <div class=\"col-lg-4\">");
					respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
					respuesta.println("");
					respuesta.println("                            <div class=\"form-group\">");
					respuesta.println("                                <label>Email</label>");
					respuesta.println("                                <input class=\"form-control\" name=\"emailProveedor\" placeholder=\"Ingrese Email del proveedor\">");
					respuesta.println("                            </div>");
					respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
					respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
					respuesta.println("</form>");
					respuesta.println("                            </div>");
					respuesta.println("                    <div class=\"col-lg-4\">");
					respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
					respuesta.println("");
					respuesta.println("                            <div class=\"form-group\">");
					respuesta.println("                                <label>Numero Documento</label>");
					respuesta.println("                                <input class=\"form-control\" name=\"documentoProveedor\" placeholder=\"Ingrese documento\">");
					respuesta.println("                            </div>");
					respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
					respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
					respuesta.println("</form>");
					respuesta.println("                            </div>");
					respuesta.println("                    <div class=\"col-lg-4\">");
					respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
					respuesta.println("");
					respuesta.println("                            <div class=\"form-group\">");
					respuesta.println("                                <label>Materia que provee</label>");
					respuesta.println("                                <input class=\"form-control\" name=\"matProvee\" placeholder=\"Ingrese Materia\">");
					respuesta.println("                            </div>");
					respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
					respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
					respuesta.println("</form>");
					respuesta.println("                            </div>");
					respuesta.println("                            </div>");
					respuesta.println("                        </ol>");
					
					respuesta.println("<div class=\"row\">");
					respuesta.println("                    <div class=\"col-lg-12\">");
					respuesta.println("                        <h2>Tabla Clientes</h2>");
					respuesta.println("                        <div class=\"table-responsive\">");
					respuesta.println("                            <table class=\"table table-bordered table-hover\">");
					respuesta.println("                                <thead>");
					respuesta.println("                                    <tr>");
					respuesta.println("                                        <th>Email</th>");
					respuesta.println("                                        <th>Nacionalidad</th>");
					respuesta.println("                                        <th>Direccion</th>");
					respuesta.println("                                        <th>Numero Documento</th>");
					respuesta.println("                                        <th>Id Pedido</th>");
					respuesta.println("                                        <th>Estado</th>");
					respuesta.println("                                        <th>Materia Provee</th>");
					respuesta.println("                                    </tr>");
					respuesta.println("                                </thead>");
					respuesta.println("                                <tbody>");
					for (int i = 0; i < proveedor.size(); i++) 
					{
						Proveedor x = proveedor.get(i);
						respuesta.println("                                <tr>");
						respuesta.println("                                        <td>"+x.getCorreo()+"</td>");
						respuesta.println("                                        <td>"+x.getPais()+"</td>");
						respuesta.println("                                        <td>"+x.getDireccion()+"</td>");
						respuesta.println("                                        <td>"+x.getIdentificacion()+"</td>");
						respuesta.println("                                        <td>"+x.getIdPedido()+"</td>");
						respuesta.println("                                        <td>"+x.getIdentificacion()+"</td>");
						respuesta.println("                                        <td>"+x.getNombreMateria()+"</td>");
						respuesta.println("                                </tr>");

					}
					
					respuesta.println("                                </tbody>");
					respuesta.println("                            </table>");
					respuesta.println("                         </div>");
					respuesta.println("                      </div>");
					respuesta.println("                 </div>");
			}catch (Exception e) {
				// TODO: handle exception
			}
			}
	private void imprimirTablasPedidosClientesRangoFecha(PrintWriter respuesta, String fechaPedido, String fechaEntrega) throws Exception{
		ArrayList<ReqPedidoCliente> pedidos = ProdAndes.darInstancia().consultarPedidosClienteRangoFecha(fechaPedido, fechaEntrega);
		respuesta.println("<div id=\"page-wrapper\">");
		respuesta.println("");
		respuesta.println("            <div class=\"container-fluid\">");
		respuesta.println("");
		respuesta.println("                <!-- Page Heading -->");
		respuesta.println("                <div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-12\">");
		respuesta.println("                        <h1 class=\"page-header\">");
		respuesta.println("                            RFC5 - Consultar Pedidos");
		respuesta.println("                        </h1>");
		respuesta.println("                        <ol class=\"breadcrumb\">");
		respuesta.println("                            <li>");
		respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"RFC5.htm\">Atras</a>");
		respuesta.println("                            </li>");
		respuesta.println("                            <li class=\"active\">");
		respuesta.println("                                <i class=\"fa fa-table\"></i> RFC5");
		respuesta.println("                            </li>");
		respuesta.println("                        </ol>");
		respuesta.println("                    </div>");
		respuesta.println("                </div>");
		
		respuesta.println("<ol class=\"breadcrumb\">");
		respuesta.println("                        <h2>");
		respuesta.println("                            Filtros");
		respuesta.println("                        </h2>");
		respuesta.println("<div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
		respuesta.println("");
		respuesta.println("                            <div class=\"form-group\">");
		respuesta.println("                                <label>Email</label>");
		respuesta.println("                                <input class=\"form-control\" name=\"emailCliente2\" placeholder=\"Ingrese Email del cliente\">");
		respuesta.println("                            </div>");
		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
		respuesta.println("</form>");
		respuesta.println("                            </div>");
		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
		respuesta.println("");
		respuesta.println("                            <div class=\"form-group\">");
		respuesta.println("                                <label>Fecha Entrega</label>");
		respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"fechaPedido\" >");
		respuesta.println("                            <br>");
		respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"fechaEntrega\" >");
		respuesta.println("                            </div>");
		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
		respuesta.println("</form>");
		respuesta.println("                            </div>");
		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
		respuesta.println("");
		respuesta.println("                            <div class=\"form-group\">");
		respuesta.println("                                <label>Nombre Producto</label>");
		respuesta.println("                                <input class=\"form-control\" name=\"nombreProducto\" placeholder=\"Ingrese nombre\">");
		respuesta.println("                            </div>");
		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
		respuesta.println("</form>");
		respuesta.println("                            </div>");
		respuesta.println("                            </div>");
		respuesta.println("                        </ol>");
		respuesta.println("<div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-12\">");
		respuesta.println("                        <h2>Tabla Pedidos Clientes</h2>");
		respuesta.println("                        <div class=\"table-responsive\">");
		respuesta.println("                            <table class=\"table table-bordered table-hover\">");
		respuesta.println("                                <thead>");
		respuesta.println("                                    <tr>");
		respuesta.println("                                        <th>Email Cliente</th>");
		respuesta.println("                                        <th>Nombre</th>");
		respuesta.println("                                        <th>Cantidad</th>");
		respuesta.println("                                        <th>Costo</th>");
		respuesta.println("                                        <th>Fecha Pedido</th>");
		respuesta.println("                                        <th>Fecha Entrega</th>");
		respuesta.println("                                    </tr>");
		respuesta.println("                                </thead>");
		respuesta.println("                                <tbody>");
		for (int i = 0; i < pedidos.size(); i++) 
		{
			ReqPedidoCliente x = pedidos.get(i);
			respuesta.println("                                <tr>");
			respuesta.println("                                        <td>"+x.getEmailCliente()+"</td>");
			respuesta.println("                                        <td>"+x.getNombreProducto()+"</td>");
			respuesta.println("                                        <td>"+x.getCantidad()+"</td>");
			respuesta.println("                                        <td>"+x.getCosto()+"</td>");
			respuesta.println("                                        <td>"+x.getFechaPedido()+"</td>");
			respuesta.println("                                        <td>"+x.getFechaEntrega()+"</td>");
			respuesta.println("                                </tr>");

		}
		
		respuesta.println("                                </tbody>");
		respuesta.println("                            </table>");
		respuesta.println("                         </div>");
		respuesta.println("                      </div>");
		respuesta.println("                 </div>");
	}
	
	
	private void imprimirTablasPedidosClientesXNombre(PrintWriter respuesta, String nombre) throws Exception{
		ArrayList<ReqPedidoCliente> pedidos = ProdAndes.darInstancia().consultarPedidoClientesXNombre(nombre);
		respuesta.println("<div id=\"page-wrapper\">");
		respuesta.println("");
		respuesta.println("            <div class=\"container-fluid\">");
		respuesta.println("");
		respuesta.println("                <!-- Page Heading -->");
		respuesta.println("                <div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-12\">");
		respuesta.println("                        <h1 class=\"page-header\">");
		respuesta.println("                            RFC5 - Consultar Pedidos");
		respuesta.println("                        </h1>");
		respuesta.println("                        <ol class=\"breadcrumb\">");
		respuesta.println("                            <li>");
		respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"RFC5.htm\">Atras</a>");
		respuesta.println("                            </li>");
		respuesta.println("                            <li class=\"active\">");
		respuesta.println("                                <i class=\"fa fa-table\"></i> RFC5");
		respuesta.println("                            </li>");
		respuesta.println("                        </ol>");
		respuesta.println("                    </div>");
		respuesta.println("                </div>");
		
		respuesta.println("<ol class=\"breadcrumb\">");
		respuesta.println("                        <h2>");
		respuesta.println("                            Filtros");
		respuesta.println("                        </h2>");
		respuesta.println("<div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
		respuesta.println("");
		respuesta.println("                            <div class=\"form-group\">");
		respuesta.println("                                <label>Email</label>");
		respuesta.println("                                <input class=\"form-control\" name=\"emailCliente2\" placeholder=\"Ingrese Email del cliente\">");
		respuesta.println("                            </div>");
		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
		respuesta.println("</form>");
		respuesta.println("                            </div>");
		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
		respuesta.println("");
		respuesta.println("                            <div class=\"form-group\">");
		respuesta.println("                                <label>Fecha Entrega</label>");
		respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"fechaPedido\" >");
		respuesta.println("                            <br>");
		respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"fechaEntrega\" >");
		respuesta.println("                            </div>");
		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
		respuesta.println("</form>");
		respuesta.println("                            </div>");
		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
		respuesta.println("");
		respuesta.println("                            <div class=\"form-group\">");
		respuesta.println("                                <label>Nombre Producto</label>");
		respuesta.println("                                <input class=\"form-control\" name=\"nombreProducto\" placeholder=\"Ingrese nombre\">");
		respuesta.println("                            </div>");
		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
		respuesta.println("</form>");
		respuesta.println("                            </div>");
		respuesta.println("                            </div>");
		respuesta.println("                        </ol>");
		respuesta.println("<div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-12\">");
		respuesta.println("                        <h2>Tabla Pedidos Clientes</h2>");
		respuesta.println("                        <div class=\"table-responsive\">");
		respuesta.println("                            <table class=\"table table-bordered table-hover\">");
		respuesta.println("                                <thead>");
		respuesta.println("                                    <tr>");
		respuesta.println("                                        <th>Email Cliente</th>");
		respuesta.println("                                        <th>Nombre</th>");
		respuesta.println("                                        <th>Cantidad</th>");
		respuesta.println("                                        <th>Costo</th>");
		respuesta.println("                                        <th>Fecha Pedido</th>");
		respuesta.println("                                        <th>Fecha Entrega</th>");
		respuesta.println("                                    </tr>");
		respuesta.println("                                </thead>");
		respuesta.println("                                <tbody>");
		for (int i = 0; i < pedidos.size(); i++) 
		{
			ReqPedidoCliente x = pedidos.get(i);
			respuesta.println("                                <tr>");
			respuesta.println("                                        <td>"+x.getEmailCliente()+"</td>");
			respuesta.println("                                        <td>"+x.getNombreProducto()+"</td>");
			respuesta.println("                                        <td>"+x.getCantidad()+"</td>");
			respuesta.println("                                        <td>"+x.getCosto()+"</td>");
			respuesta.println("                                        <td>"+x.getFechaPedido()+"</td>");
			respuesta.println("                                        <td>"+x.getFechaEntrega()+"</td>");
			respuesta.println("                                </tr>");

		}
		
		respuesta.println("                                </tbody>");
		respuesta.println("                            </table>");
		respuesta.println("                         </div>");
		respuesta.println("                      </div>");
		respuesta.println("                 </div>");
	}
	
	private void imprimirTablasPedidosClientesXEmail(PrintWriter respuesta, String mail) throws Exception{
		ArrayList<ReqPedidoCliente> pedidos = ProdAndes.darInstancia().consultarPedidosCleintesXEmail(mail);
		respuesta.println("<div id=\"page-wrapper\">");
		respuesta.println("");
		respuesta.println("            <div class=\"container-fluid\">");
		respuesta.println("");
		respuesta.println("                <!-- Page Heading -->");
		respuesta.println("                <div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-12\">");
		respuesta.println("                        <h1 class=\"page-header\">");
		respuesta.println("                            RFC5 - Consultar Pedidos");
		respuesta.println("                        </h1>");
		respuesta.println("                        <ol class=\"breadcrumb\">");
		respuesta.println("                            <li>");
		respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"RFC5.htm\">Atras</a>");
		respuesta.println("                            </li>");
		respuesta.println("                            <li class=\"active\">");
		respuesta.println("                                <i class=\"fa fa-table\"></i> RFC5");
		respuesta.println("                            </li>");
		respuesta.println("                        </ol>");
		respuesta.println("                    </div>");
		respuesta.println("                </div>");
		
		respuesta.println("<ol class=\"breadcrumb\">");
		respuesta.println("                        <h2>");
		respuesta.println("                            Filtros");
		respuesta.println("                        </h2>");
		respuesta.println("<div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
		respuesta.println("");
		respuesta.println("                            <div class=\"form-group\">");
		respuesta.println("                                <label>Email</label>");
		respuesta.println("                                <input class=\"form-control\" name=\"emailCliente2\" placeholder=\"Ingrese Email del cliente\">");
		respuesta.println("                            </div>");
		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
		respuesta.println("</form>");
		respuesta.println("                            </div>");
		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
		respuesta.println("");
		respuesta.println("                            <div class=\"form-group\">");
		respuesta.println("                                <label>Fecha Entrega </label>");
		respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"fechaPedido\" >");
		respuesta.println("                            <br>");
		respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"fechaEntrega\" >");
		respuesta.println("                            </div>");
		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
		respuesta.println("</form>");
		respuesta.println("                            </div>");
		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
		respuesta.println("");
		respuesta.println("                            <div class=\"form-group\">");
		respuesta.println("                                <label>Nombre Producto</label>");
		respuesta.println("                                <input class=\"form-control\" name=\"nombreProducto\" placeholder=\"Ingrese nombre\">");
		respuesta.println("                            </div>");
		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
		respuesta.println("</form>");
		respuesta.println("                            </div>");
		respuesta.println("                            </div>");
		respuesta.println("                        </ol>");
		respuesta.println("<div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-12\">");
		respuesta.println("                        <h2>Tabla Pedidos Clientes</h2>");
		respuesta.println("                        <div class=\"table-responsive\">");
		respuesta.println("                            <table class=\"table table-bordered table-hover\">");
		respuesta.println("                                <thead>");
		respuesta.println("                                    <tr>");
		respuesta.println("                                        <th>Email Cliente</th>");
		respuesta.println("                                        <th>Nombre</th>");
		respuesta.println("                                        <th>Cantidad</th>");
		respuesta.println("                                        <th>Costo</th>");
		respuesta.println("                                        <th>Fecha Pedido</th>");
		respuesta.println("                                        <th>Fecha Entrega</th>");
		respuesta.println("                                    </tr>");
		respuesta.println("                                </thead>");
		respuesta.println("                                <tbody>");
		for (int i = 0; i < pedidos.size(); i++) 
		{
			ReqPedidoCliente x = pedidos.get(i);
			respuesta.println("                                <tr>");
			respuesta.println("                                        <td>"+x.getEmailCliente()+"</td>");
			respuesta.println("                                        <td>"+x.getNombreProducto()+"</td>");
			respuesta.println("                                        <td>"+x.getCantidad()+"</td>");
			respuesta.println("                                        <td>"+x.getCosto()+"</td>");
			respuesta.println("                                        <td>"+x.getFechaPedido()+"</td>");
			respuesta.println("                                        <td>"+x.getFechaEntrega()+"</td>");
			respuesta.println("                                </tr>");

		}
		
		respuesta.println("                                </tbody>");
		respuesta.println("                            </table>");
		respuesta.println("                         </div>");
		respuesta.println("                      </div>");
		respuesta.println("                 </div>");
	}
	
	private void imprimirTablasPedidosProveedoresXEstado(PrintWriter respuesta, String estado) throws Exception{
		try{
			
			ArrayList<ReqPedidoProveedor> pedidos = ProdAndes.darInstancia().consultarPedidosProveedorXEstado(estado);
			respuesta.println("<div id=\"page-wrapper\">");
			respuesta.println("");
			respuesta.println("            <div class=\"container-fluid\">");
			respuesta.println("");
			respuesta.println("                <!-- Page Heading -->");
			respuesta.println("                <div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-12\">");
			respuesta.println("                        <h1 class=\"page-header\">");
			respuesta.println("                            RFC5 - Consultar Pedidos");
			respuesta.println("                        </h1>");
			respuesta.println("                        <ol class=\"breadcrumb\">");
			respuesta.println("                            <li>");
			respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"index.htm\">Dashboard</a>");
			respuesta.println("                            </li>");
			respuesta.println("                            <li class=\"active\">");
			respuesta.println("                                <i class=\"fa fa-table\"></i> RFC5");
			respuesta.println("                            </li>");
			respuesta.println("                        </ol>");
			respuesta.println("                    </div>");
			respuesta.println("                </div>");
			
			respuesta.println("<ol class=\"breadcrumb\">");
			respuesta.println("                        <h2>");
			respuesta.println("                            Filtros");
			respuesta.println("                        </h2>");
			respuesta.println("<div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-4\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <label>Estado</label>");
			respuesta.println("                                <input class=\"form-control\" name=\"estado\" placeholder=\"Ingrese estado\">");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                    <div class=\"col-lg-4\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <label>Fecha</label>");
			respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"fechaPedidoProveedores\" >");
			respuesta.println("                            <br>");
			respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"fechaEntregaProveedores\" >");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                    <div class=\"col-lg-4\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <label>Material Requerido</label>");
			respuesta.println("                                <input class=\"form-control\" name=\"materialRequerido\" placeholder=\"Ingrese material requerido\">");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                            </div>");
			respuesta.println("                        </ol>");
			respuesta.println("<div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-12\">");
			respuesta.println("                        <h2>Tabla Pedidos Proveedores</h2>");
			respuesta.println("                        <div class=\"table-responsive\">");
			respuesta.println("                            <table class=\"table table-bordered table-hover\">");
			respuesta.println("                                <thead>");
			respuesta.println("                                    <tr>");
			respuesta.println("                                        <th>Id Pedido</th>");
			respuesta.println("                                        <th>Email</th>");
			respuesta.println("                                        <th>Fecha Pedido</th>");
			respuesta.println("                                        <th>Fecha Entrega</th>");
			respuesta.println("                                        <th>Estado</th>");
			respuesta.println("                                        <th>Cantidad</th>");
			respuesta.println("                                        <th>Material</th>");
			respuesta.println("                                    </tr>");
			respuesta.println("                                </thead>");
			respuesta.println("                                <tbody>");
			for (int i = 0; i < pedidos.size(); i++) 
			{
				ReqPedidoProveedor x = pedidos.get(i);
				respuesta.println("                                <tr>");
				respuesta.println("                                        <td>"+x.getIdPed()+"</td>");
				respuesta.println("                                        <td>"+x.getEmail()+"</td>");
				respuesta.println("                                        <td>"+x.getFechaPedido()+"</td>");
				respuesta.println("                                        <td>"+x.getFechaEntrega()+"</td>");
				respuesta.println("                                        <td>"+x.getEstado()+"</td>");
				respuesta.println("                                        <td>"+x.getCantidad()+"</td>");
				respuesta.println("                                        <td>"+x.getNombreMaterial()+"</td>");
				respuesta.println("                                </tr>");

			}
			
			respuesta.println("                                </tbody>");
			respuesta.println("                            </table>");
			respuesta.println("                         </div>");
			respuesta.println("                      </div>");
			respuesta.println("                 </div>");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void imprimirTablasPedidosProveedoresXRangoFecha(PrintWriter respuesta, String fechaPedido, String fechaEntrega) throws Exception{
		try{
			
			ArrayList<ReqPedidoProveedor> pedidos = ProdAndes.darInstancia().consultarPedidosProveedorXRangoFecha(fechaPedido, fechaEntrega);
			respuesta.println("<div id=\"page-wrapper\">");
			respuesta.println("");
			respuesta.println("            <div class=\"container-fluid\">");
			respuesta.println("");
			respuesta.println("                <!-- Page Heading -->");
			respuesta.println("                <div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-12\">");
			respuesta.println("                        <h1 class=\"page-header\">");
			respuesta.println("                            RFC5 - Consultar Pedidos");
			respuesta.println("                        </h1>");
			respuesta.println("                        <ol class=\"breadcrumb\">");
			respuesta.println("                            <li>");
			respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"index.htm\">Dashboard</a>");
			respuesta.println("                            </li>");
			respuesta.println("                            <li class=\"active\">");
			respuesta.println("                                <i class=\"fa fa-table\"></i> RFC5");
			respuesta.println("                            </li>");
			respuesta.println("                        </ol>");
			respuesta.println("                    </div>");
			respuesta.println("                </div>");
			
			respuesta.println("<ol class=\"breadcrumb\">");
			respuesta.println("                        <h2>");
			respuesta.println("                            Filtros");
			respuesta.println("                        </h2>");
			respuesta.println("<div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-4\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <label>Estado</label>");
			respuesta.println("                                <input class=\"form-control\" name=\"estado\" placeholder=\"Ingrese estado\">");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                    <div class=\"col-lg-4\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <label>Fecha</label>");
			respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"fechaPedidoProveedores\" >");
			respuesta.println("                            <br>");
			respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"fechaEntregaProveedores\" >");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                    <div class=\"col-lg-4\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <label>Material Requerido</label>");
			respuesta.println("                                <input class=\"form-control\" name=\"materialRequerido\" placeholder=\"Ingrese material requerido\">");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                            </div>");
			respuesta.println("                        </ol>");
			respuesta.println("<div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-12\">");
			respuesta.println("                        <h2>Tabla Pedidos Proveedores</h2>");
			respuesta.println("                        <div class=\"table-responsive\">");
			respuesta.println("                            <table class=\"table table-bordered table-hover\">");
			respuesta.println("                                <thead>");
			respuesta.println("                                    <tr>");
			respuesta.println("                                        <th>Id Pedido</th>");
			respuesta.println("                                        <th>Email</th>");
			respuesta.println("                                        <th>Fecha Pedido</th>");
			respuesta.println("                                        <th>Fecha Entrega</th>");
			respuesta.println("                                        <th>Estado</th>");
			respuesta.println("                                        <th>Cantidad</th>");
			respuesta.println("                                        <th>Material</th>");
			respuesta.println("                                    </tr>");
			respuesta.println("                                </thead>");
			respuesta.println("                                <tbody>");
			for (int i = 0; i < pedidos.size(); i++) 
			{
				ReqPedidoProveedor x = pedidos.get(i);
				respuesta.println("                                <tr>");
				respuesta.println("                                        <td>"+x.getIdPed()+"</td>");
				respuesta.println("                                        <td>"+x.getEmail()+"</td>");
				respuesta.println("                                        <td>"+x.getFechaPedido()+"</td>");
				respuesta.println("                                        <td>"+x.getFechaEntrega()+"</td>");
				respuesta.println("                                        <td>"+x.getEstado()+"</td>");
				respuesta.println("                                        <td>"+x.getCantidad()+"</td>");
				respuesta.println("                                        <td>"+x.getNombreMaterial()+"</td>");
				respuesta.println("                                </tr>");

			}
			
			respuesta.println("                                </tbody>");
			respuesta.println("                            </table>");
			respuesta.println("                         </div>");
			respuesta.println("                      </div>");
			respuesta.println("                 </div>");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	private void imprimirTablasPedidosProveedoresXMateriaRequerida(PrintWriter respuesta, String materiaRequerida) throws Exception{
		try{
			
			ArrayList<ReqPedidoProveedor> pedidos = ProdAndes.darInstancia().consultarPedidosProveedorXMateriaRequerida(materiaRequerida);
			respuesta.println("<div id=\"page-wrapper\">");
			respuesta.println("");
			respuesta.println("            <div class=\"container-fluid\">");
			respuesta.println("");
			respuesta.println("                <!-- Page Heading -->");
			respuesta.println("                <div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-12\">");
			respuesta.println("                        <h1 class=\"page-header\">");
			respuesta.println("                            RFC5 - Consultar Pedidos");
			respuesta.println("                        </h1>");
			respuesta.println("                        <ol class=\"breadcrumb\">");
			respuesta.println("                            <li>");
			respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"index.htm\">Dashboard</a>");
			respuesta.println("                            </li>");
			respuesta.println("                            <li class=\"active\">");
			respuesta.println("                                <i class=\"fa fa-table\"></i> RFC5");
			respuesta.println("                            </li>");
			respuesta.println("                        </ol>");
			respuesta.println("                    </div>");
			respuesta.println("                </div>");
			
			respuesta.println("<ol class=\"breadcrumb\">");
			respuesta.println("                        <h2>");
			respuesta.println("                            Filtros");
			respuesta.println("                        </h2>");
			respuesta.println("<div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-4\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <label>Estado</label>");
			respuesta.println("                                <input class=\"form-control\" name=\"estado\" placeholder=\"Ingrese estado\">");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                    <div class=\"col-lg-4\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <label>Fecha</label>");
			respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"fechaPedidoProveedores\" >");
			respuesta.println("                            <br>");
			respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"fechaEntregaProveedores\" >");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                    <div class=\"col-lg-4\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <label>Material Requerido</label>");
			respuesta.println("                                <input class=\"form-control\" name=\"materialRequerido\" placeholder=\"Ingrese material requerido\">");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                            </div>");
			respuesta.println("                        </ol>");
			respuesta.println("<div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-12\">");
			respuesta.println("                        <h2>Tabla Pedidos Proveedores</h2>");
			respuesta.println("                        <div class=\"table-responsive\">");
			respuesta.println("                            <table class=\"table table-bordered table-hover\">");
			respuesta.println("                                <thead>");
			respuesta.println("                                    <tr>");
			respuesta.println("                                        <th>Id Pedido</th>");
			respuesta.println("                                        <th>Email</th>");
			respuesta.println("                                        <th>Fecha Pedido</th>");
			respuesta.println("                                        <th>Fecha Entrega</th>");
			respuesta.println("                                        <th>Estado</th>");
			respuesta.println("                                        <th>Cantidad</th>");
			respuesta.println("                                        <th>Material</th>");
			respuesta.println("                                    </tr>");
			respuesta.println("                                </thead>");
			respuesta.println("                                <tbody>");
			for (int i = 0; i < pedidos.size(); i++) 
			{
				ReqPedidoProveedor x = pedidos.get(i);
				respuesta.println("                                <tr>");
				respuesta.println("                                        <td>"+x.getIdPed()+"</td>");
				respuesta.println("                                        <td>"+x.getEmail()+"</td>");
				respuesta.println("                                        <td>"+x.getFechaPedido()+"</td>");
				respuesta.println("                                        <td>"+x.getFechaEntrega()+"</td>");
				respuesta.println("                                        <td>"+x.getEstado()+"</td>");
				respuesta.println("                                        <td>"+x.getCantidad()+"</td>");
				respuesta.println("                                        <td>"+x.getNombreMaterial()+"</td>");
				respuesta.println("                                </tr>");

			}
			
			respuesta.println("                                </tbody>");
			respuesta.println("                            </table>");
			respuesta.println("                         </div>");
			respuesta.println("                      </div>");
			respuesta.println("                 </div>");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
