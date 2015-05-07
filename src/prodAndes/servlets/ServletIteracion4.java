package prodAndes.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prodAndes.fachada.ProdAndes;
import prodAndes.vos.ConsultaEtapaProduccion;
import prodAndes.vos.PedidoMaterial2;
import prodAndes.vos.Pedidos2;
import prodAndes.vos.ReqPedidoProveedor;

public class ServletIteracion4 extends ServletTemplate{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void escribirContenido(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter respuesta = response.getWriter();
		String identificadorMaterial = request.getParameter("identificadorMaterial");
		String typeMaterial = request.getParameter("typeMaterial");
		String costo = request.getParameter("costo");
		String fechaInicio = request.getParameter("rango1");
		String fechaFin = request.getParameter("rango2");
		String fechaRFC8Inicio = request.getParameter("rangoRFC81");
		String fechaRFC8Fin = request.getParameter("rangoRFC82");
		String idMaterial = request.getParameter("idMat");
		if(identificadorMaterial != null){
			try{
				imprimirTablasClientes(respuesta, identificadorMaterial);
			}
			catch (Exception e) {
				e.printStackTrace();
				imprimirError(response, "Error en clientes");
			}
		}
		else if(costo != null && typeMaterial != null){
			int costoT = Integer.parseInt(costo);
			try{
				imprimirTablasPedidos(respuesta, typeMaterial, costoT);
			}
			catch (Exception e) {
				e.printStackTrace();
				imprimirError(response, "Error en clientes");
			}
		}
		else if(fechaInicio != null && fechaFin != null){
			try{
				imprimirTablasEtapasProduccion(respuesta, fechaInicio, fechaFin);
			}
			catch (Exception e) {
				e.printStackTrace();
				imprimirError(response, "Error en clientes");
			}
		}
		else if(fechaRFC8Fin != null && fechaRFC8Inicio != null){
			try{
				imprimirSelects(respuesta, fechaRFC8Inicio, fechaRFC8Fin);
			}
			catch (Exception e) {
				e.printStackTrace();
				imprimirError(response, "Error en clientes");
			}
		}
		else if(fechaRFC8Fin != null && fechaRFC8Inicio != null && idMaterial != null ){
			try{
				imprimirTablaEtapas(respuesta, fechaRFC8Inicio, fechaRFC8Fin, idMaterial);
			}
			catch (Exception e) {
				e.printStackTrace();
				imprimirError(response, "Error en clientes");
			}
		}
		
	}
	
	private void imprimirTablasClientes(PrintWriter respuesta, String identificadorMaterial) throws Exception{
		ArrayList<PedidoMaterial2> pedidosMaterial = ProdAndes.darInstancia().consultarMaterial2(identificadorMaterial);
		ArrayList<String> idsMaterial = ProdAndes.darInstancia().consultarIdMaterial();
		respuesta.println("<div id=\"page-wrapper\">");
		respuesta.println("");
		respuesta.println("            <div class=\"container-fluid\">");
		respuesta.println("");
		respuesta.println("                <!-- Page Heading -->");
		respuesta.println("                <div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-12\">");
		respuesta.println("                        <h1 class=\"page-header\">");
		respuesta.println("                            RFC11 - Consultar Material 2");
		respuesta.println("                        </h1>");
		respuesta.println("                        <ol class=\"breadcrumb\">");
		respuesta.println("                            <li>");
		respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"index.htm\">Dashboard</a>");
		respuesta.println("                            </li>");
		respuesta.println("                            <li class=\"active\">");
		respuesta.println("                                <i class=\"fa fa-table\"></i> RFC11");
		respuesta.println("                            </li>");
		respuesta.println("                        </ol>");
		respuesta.println("                    </div>");
		respuesta.println("                </div>");
		
		respuesta.println("<ol class=\"breadcrumb\">");
		respuesta.println("                        <h2>");
		respuesta.println("                            Consulta");
		respuesta.println("                        </h2>");
		respuesta.println("<div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                            </div>");
		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                    <form role=\"form\" action=\"ServletIteracion4.htm\" method=\"get\">");
		respuesta.println("");
		respuesta.println("                            <div class=\"form-group\">");
		respuesta.println("                                <label>Identificador Material</label>");
		respuesta.println("                            	<select class=\"form-control\" name=\"identificadorMaterial\" required>");
		respuesta.println("                            	<option value=\"\" disabled selected> Sel. Producto </option>");
		for (int i = 0; i < idsMaterial.size(); i++) {
			String nombre = idsMaterial.get(i);
			respuesta.println("                            	<option value=\""+nombre+"\">"+nombre+"</option>");
		}
		respuesta.println("                            	</select>");	
		respuesta.println("                            </div>");
		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
		respuesta.println("</form>");
		respuesta.println("                            </div>");
		respuesta.println("                            </div>");
		respuesta.println("                        </ol>");
		respuesta.println("<div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-12\">");
		respuesta.println("                        <h2>Tabla Pedidos Material 2</h2>");
		respuesta.println("                        <div class=\"table-responsive\">");
		respuesta.println("                            <table class=\"table table-bordered table-hover\">");
		respuesta.println("                                <thead>");
		respuesta.println("                                    <tr>");
		respuesta.println("                                        <th>Id Material</th>");
		respuesta.println("                                        <th>Id Pedido</th>");
		respuesta.println("                                        <th>Email Cliente</th>");
		respuesta.println("                                        <th>Fecha Pedido</th>");
		respuesta.println("                                        <th>Fecha Entrega</th>");
		respuesta.println("                                        <th>Estado</th>");
		respuesta.println("                                        <th>Cantidad</th>");
		respuesta.println("                                    </tr>");
		respuesta.println("                                </thead>");
		respuesta.println("                                <tbody>");
		for (int i = 0; i < pedidosMaterial.size(); i++) 
		{
			PedidoMaterial2 x = pedidosMaterial.get(i);
			respuesta.println("                                <tr>");
			respuesta.println("                                        <td>"+x.getIdMaterial()+"</td>");
			respuesta.println("                                        <td>"+x.getIdPedido()+"</td>");
			respuesta.println("                                        <td>"+x.getEmailCliente()+"</td>");
			respuesta.println("                                        <td>"+x.getFechaPedido()+"</td>");
			respuesta.println("                                        <td>"+x.getFechaEntrega()+"</td>");
			respuesta.println("                                        <td>"+x.getEstado()+"</td>");
			respuesta.println("                                        <td>"+x.getCantidad()+"</td>");
			respuesta.println("                                </tr>");

		}
		
		respuesta.println("                                </tbody>");
		respuesta.println("                            </table>");
		respuesta.println("                         </div>");
		respuesta.println("                      </div>");
		respuesta.println("                 </div>");
	}
	
	private void imprimirTablasPedidos(PrintWriter respuesta, String material, int costo) throws Exception{
		ArrayList<Pedidos2> pedidos = ProdAndes.darInstancia().consultarPedidos2(material, costo);
		respuesta.println("<div id=\"page-wrapper\">");
		respuesta.println("");
		respuesta.println("            <div class=\"container-fluid\">");
		respuesta.println("");
		respuesta.println("                <!-- Page Heading -->");
		respuesta.println("                <div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-12\">");
		respuesta.println("                        <h1 class=\"page-header\">");
		respuesta.println("                            RFC10 - Consultar Pedidos 2");
		respuesta.println("                        </h1>");
		respuesta.println("                        <ol class=\"breadcrumb\">");
		respuesta.println("                            <li>");
		respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"index.htm\">Dashboard</a>");
		respuesta.println("                            </li>");
		respuesta.println("                            <li class=\"active\">");
		respuesta.println("                                <i class=\"fa fa-table\"></i> RFC10");
		respuesta.println("                            </li>");
		respuesta.println("                        </ol>");
		respuesta.println("                    </div>");
		respuesta.println("                </div>");
		
		respuesta.println("<ol class=\"breadcrumb\">");
		respuesta.println("                        <h2>");
		respuesta.println("                            Consulta");
		respuesta.println("                        </h2>");
		respuesta.println("<div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-6\">");
		respuesta.println("                    <form role=\"form\" action=\"ServletIteracion4.htm\" method=\"get\">");
		respuesta.println("");
		respuesta.println("                            <div class=\"form-group\">");
		respuesta.println("                                <label>Tipo de Material</label>");
		respuesta.println("                                <input class=\"form-control\" name=\"typeMaterial\" placeholder=\"Ingrese Tipo Material\">");
		respuesta.println("                                <label>Costo</label>");
		respuesta.println("                                <input class=\"form-control\" name=\"costo\" placeholder=\"Ingrese costo\">");
		respuesta.println("                            </div>");
		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
		respuesta.println("</form>");
		respuesta.println("                            </div>");
		respuesta.println("                            </div>");
		respuesta.println("                        </ol>");
		respuesta.println("<div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-12\">");
		respuesta.println("                        <h2>Tabla Pedidos 2</h2>");
		respuesta.println("                        <div class=\"table-responsive\">");
		respuesta.println("                            <table class=\"table table-bordered table-hover\">");
		respuesta.println("                                <thead>");
		respuesta.println("                                    <tr>");
		respuesta.println("                                        <th>Id Pedido</th>");
		respuesta.println("                                        <th>Id Producto</th>");
		respuesta.println("                                        <th>Id Material</th>");
		respuesta.println("                                        <th>Tipo Material</th>");
		respuesta.println("                                        <th>Cantidad</th>");
		respuesta.println("                                        <th>Costo</th>");
		respuesta.println("                                    </tr>");
		respuesta.println("                                </thead>");
		respuesta.println("                                <tbody>");
		for (int i = 0; i < pedidos.size(); i++) 
		{
			Pedidos2 x = pedidos.get(i);
			respuesta.println("                                <tr>");
			respuesta.println("                                        <td>"+x.getIdPedido()+"</td>");
			respuesta.println("                                        <td>"+x.getIdProducto()+"</td>");
			respuesta.println("                                        <td>"+x.getIdMaterial()+"</td>");
			respuesta.println("                                        <td>"+x.getNombre()+"</td>");
			respuesta.println("                                        <td>"+x.getCantidad()+"</td>");
			respuesta.println("                                        <td>"+x.getCosto()+"</td>");
			respuesta.println("                                </tr>");

		}
		
		respuesta.println("                                </tbody>");
		respuesta.println("                            </table>");
		respuesta.println("                         </div>");
		respuesta.println("                      </div>");
		respuesta.println("                 </div>");
	}
	
	private void imprimirTablasEtapasProduccion(PrintWriter respuesta, String fechaInicio, String fechaFin) throws Exception{
		ArrayList<ConsultaEtapaProduccion> etapas = ProdAndes.darInstancia().consultaEtapasProduccion(fechaInicio, fechaFin);
		respuesta.println("<div id=\"page-wrapper\">");
		respuesta.println("");
		respuesta.println("            <div class=\"container-fluid\">");
		respuesta.println("");
		respuesta.println("                <!-- Page Heading -->");
		respuesta.println("                <div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-12\">");
		respuesta.println("                        <h1 class=\"page-header\">");
		respuesta.println("                            RFC9 - Consultar Ejecucion De Etapas De Produccion 2");
		respuesta.println("                        </h1>");
		respuesta.println("                        <ol class=\"breadcrumb\">");
		respuesta.println("                            <li>");
		respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"index.htm\">Dashboard</a>");
		respuesta.println("                            </li>");
		respuesta.println("                            <li class=\"active\">");
		respuesta.println("                                <i class=\"fa fa-table\"></i> RFC9");
		respuesta.println("                            </li>");
		respuesta.println("                        </ol>");
		respuesta.println("                    </div>");
		respuesta.println("                </div>");
		respuesta.println("<ol class=\"breadcrumb\">");
		respuesta.println("                        <h2>");
		respuesta.println("                            Rango de Tiempo");
		respuesta.println("                        </h2>");
		respuesta.println("<div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-6\">");
		respuesta.println("                    <form role=\"form\" action=\"ServletIteracion4.htm\" method=\"get\">");
		respuesta.println("");
		respuesta.println("                            <div class=\"form-group\">");
		respuesta.println("                                <label>Rango 1</label>");
		respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"rango1\" placeholder=\"Ingrese Rango 1\">");
		respuesta.println("                                <label>Rango 2</label>");
		respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"rango2\" placeholder=\"Ingrese Rango 2\">");
		respuesta.println("                            </div>");
		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
		respuesta.println("</form>");
		respuesta.println("                            </div>");
		respuesta.println("                            </div>");
		respuesta.println("                        </ol>");
		respuesta.println("<div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-12\">");
		respuesta.println("                        <h2>Tabla Pedidos 2</h2>");
		respuesta.println("                        <div class=\"table-responsive\">");
		respuesta.println("                            <table class=\"table table-bordered table-hover\">");
		respuesta.println("                                <thead>");
		respuesta.println("                                    <tr>");
		respuesta.println("                                        <th>Id Etapa</th>");
		respuesta.println("                                        <th>Id Producto</th>");
		respuesta.println("                                        <th>Id Pedido</th>");
		respuesta.println("                                        <th>Id Material</th>");
		respuesta.println("                                        <th>Num Etapa</th>");
		respuesta.println("                                        <th>Cantidad</th>");
		respuesta.println("                                        <th>Fecha Inicio</th>");
		respuesta.println("                                        <th>Fecha Fin</th>");
		respuesta.println("                                    </tr>");
		respuesta.println("                                </thead>");
		respuesta.println("                                <tbody>");
		for (int i = 0; i < etapas.size(); i++) 
		{
			ConsultaEtapaProduccion x = etapas.get(i);
			respuesta.println("                                <tr>");
			respuesta.println("                                        <td>"+x.getIdEtapa()+"</td>");
			respuesta.println("                                        <td>"+x.getIdProducto()+"</td>");
			respuesta.println("                                        <td>"+x.getIdPedido()+"</td>");
			respuesta.println("                                        <td>"+x.getIdMaterial()+"</td>");
			respuesta.println("                                        <td>"+x.getNumEtapa()+"</td>");
			respuesta.println("                                        <td>"+x.getCantidad()+"</td>");
			respuesta.println("                                        <td>"+x.getFechaInicio()+"</td>");
			respuesta.println("                                        <td>"+x.getFechaFin()+"</td>");
			respuesta.println("                                </tr>");

		}
		
		respuesta.println("                                </tbody>");
		respuesta.println("                            </table>");
		respuesta.println("                         </div>");
		respuesta.println("                      </div>");
		respuesta.println("                 </div>");
		
	}
	
	private void imprimirSelects(PrintWriter respuesta, String fechaInicio, String fechaFin) throws Exception{
		ArrayList<ConsultaEtapaProduccion> etapas = ProdAndes.darInstancia().consultaEtapasProduccion(fechaInicio, fechaFin);
		ArrayList<String> idsMaterial = ProdAndes.darInstancia().consultarMaterialEtapasProduccion(fechaInicio, fechaFin);
		ArrayList<String> idsPedidos = new ArrayList<String>();
		
		for (int i = 0; i < etapas.size(); i++) {
			String idPed = etapas.get(i).getIdPedido();
			idsPedidos.add(idPed);
		}
		
		respuesta.println("<div id=\"page-wrapper\">");
		respuesta.println("");
		respuesta.println("            <div class=\"container-fluid\">");
		respuesta.println("");
		respuesta.println("                <!-- Page Heading -->");
		respuesta.println("                <div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-12\">");
		respuesta.println("                        <h1 class=\"page-header\">");
		respuesta.println("                            RFC9 - Consultar Ejecucion De Etapas De Produccion 2");
		respuesta.println("                        </h1>");
		respuesta.println("                        <ol class=\"breadcrumb\">");
		respuesta.println("                            <li>");
		respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"index.htm\">Dashboard</a>");
		respuesta.println("                            </li>");
		respuesta.println("                            <li class=\"active\">");
		respuesta.println("                                <i class=\"fa fa-table\"></i> RFC9");
		respuesta.println("                            </li>");
		respuesta.println("                        </ol>");
		respuesta.println("                    </div>");
		respuesta.println("                </div>");
		
		respuesta.println("<ol class=\"breadcrumb\">");
		respuesta.println("                        <h2>");
		respuesta.println("                            Filtro");
		respuesta.println("                        </h2>");
		respuesta.println("<div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-12\">");
		respuesta.println("                    <form role=\"form\" action=\"ServletIteracion4.htm\" method=\"get\">");
		respuesta.println("");
		respuesta.println("                            <div class=\"form-group\">");
		respuesta.println("                                <label>Rango 1</label>");
		respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"rangoRFC81\" placeholder=\"Ingrese Rango 1\">");
		respuesta.println("                                <label>Rango 2</label>");
		respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"rangoRFC82\" placeholder=\"Ingrese Rango 2\">");
		respuesta.println("                            <br>");
		respuesta.println("<div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                                <label>Id Pedido</label>");
		respuesta.println("                            	<select class=\"form-control\" name=\"idPed\" required>");
		respuesta.println("                            	<option value=\"\" disabled selected> Sel. Pedido </option>");
		for (int i = 0; i < idsPedidos.size(); i++) {
			String nombre = idsPedidos.get(i);
			respuesta.println("                            	<option value=\""+nombre+"\">"+nombre+"</option>");
		}
		respuesta.println("                            	</select>");
		respuesta.println("                            </div>");
		

		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                                <label>Id Material</label>");
		respuesta.println("                            	<select class=\"form-control\" name=\"idMat\" required>");
		respuesta.println("                            	<option value=\"\" disabled selected> Sel. Material </option>");
		for (int i = 0; i < idsMaterial.size(); i++) {
			String nombre = idsMaterial.get(i);
			respuesta.println("                            	<option value=\""+nombre+"\">"+nombre+"</option>");
		}
		respuesta.println("                            	</select>");
		respuesta.println("                            </div>");
		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                                <label>Cantidad</label>");
		respuesta.println("                                <input class=\"form-control\" name=\"cantidad\" placeholder=\"Ingrese cantidad\">");
		respuesta.println("                            </div>");
		respuesta.println("                            </div>");
		respuesta.println("                            </div>");

		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
		respuesta.println("</form>");
		respuesta.println("                            </div>");
		respuesta.println("                            </div>");
		respuesta.println("<div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-12\">");
		respuesta.println("                        <h2>Tabla Estaciones Produccion</h2>");
		respuesta.println("                        <div class=\"table-responsive\">");
		respuesta.println("                            <table class=\"table table-bordered table-hover\">");
		respuesta.println("                                <thead>");
		respuesta.println("                                    <tr>");
		respuesta.println("                                        <th>Id Etapa</th>");
		respuesta.println("                                        <th>Id Producto</th>");
		respuesta.println("                                        <th>Id Pedido</th>");
		respuesta.println("                                        <th>Id Material</th>");
		respuesta.println("                                        <th>Num Etapa</th>");
		respuesta.println("                                        <th>Cantidad</th>");
		respuesta.println("                                        <th>Fecha Inicio</th>");
		respuesta.println("                                        <th>Fecha Fin</th>");
		respuesta.println("                                    </tr>");
		respuesta.println("                                </thead>");
		respuesta.println("                                <tbody>");
		for (int i = 0; i < etapas.size(); i++) 
		{
			ConsultaEtapaProduccion consulta = etapas.get(i);
			respuesta.println("                                <tr>");
			respuesta.println("                                        <td>"+consulta.getIdEtapa()+"</td>");
			respuesta.println("                                        <td>"+consulta.getIdProducto()+"</td>");
			respuesta.println("                                        <td>"+consulta.getIdPedido()+"</td>");
			respuesta.println("                                        <td>"+consulta.getIdMaterial()+"</td>");
			respuesta.println("                                        <td>"+consulta.getNumEtapa()+"</td>");
			respuesta.println("                                        <td>"+consulta.getCantidad()+"</td>");
			respuesta.println("                                        <td>"+consulta.getFechaInicio()+"</td>");
			respuesta.println("                                        <td>"+consulta.getFechaFin()+"</td>");
			respuesta.println("                                </tr>");

		}
		
		respuesta.println("                                </tbody>");
		respuesta.println("                            </table>");
		respuesta.println("                         </div>");
		respuesta.println("                      </div>");
		respuesta.println("                 </div>");
	}
	
	private void imprimirTablaEtapas(PrintWriter respuesta, String fechaInicio, String fechaFin, String flitro) throws Exception{
		ArrayList<ConsultaEtapaProduccion> etapas = ProdAndes.darInstancia().consultaEtapasProduccion(fechaInicio, fechaFin);
		ArrayList<ConsultaEtapaProduccion> etapas2 = ProdAndes.darInstancia().consultarEtapasProduccion1(fechaInicio, fechaFin, flitro);
		ArrayList<String> idsMaterial = ProdAndes.darInstancia().consultarMaterialEtapasProduccion(fechaInicio, fechaFin);
		ArrayList<String> idsPedidos = new ArrayList<String>();
		
		for (int i = 0; i < etapas.size(); i++) {
			String idPed = etapas.get(i).getIdPedido();
			idsPedidos.add(idPed);
		}
		
		respuesta.println("<div id=\"page-wrapper\">");
		respuesta.println("");
		respuesta.println("            <div class=\"container-fluid\">");
		respuesta.println("");
		respuesta.println("                <!-- Page Heading -->");
		respuesta.println("                <div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-12\">");
		respuesta.println("                        <h1 class=\"page-header\">");
		respuesta.println("                            RFC9 - Consultar Ejecucion De Etapas De Produccion 2");
		respuesta.println("                        </h1>");
		respuesta.println("                        <ol class=\"breadcrumb\">");
		respuesta.println("                            <li>");
		respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"index.htm\">Dashboard</a>");
		respuesta.println("                            </li>");
		respuesta.println("                            <li class=\"active\">");
		respuesta.println("                                <i class=\"fa fa-table\"></i> RFC9");
		respuesta.println("                            </li>");
		respuesta.println("                        </ol>");
		respuesta.println("                    </div>");
		respuesta.println("                </div>");
		
		respuesta.println("<ol class=\"breadcrumb\">");
		respuesta.println("                        <h2>");
		respuesta.println("                            Filtro");
		respuesta.println("                        </h2>");
		respuesta.println("<div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-12\">");
		respuesta.println("                    <form role=\"form\" action=\"ServletIteracion4.htm\" method=\"get\">");
		respuesta.println("");
		respuesta.println("                            <div class=\"form-group\">");
		respuesta.println("                                <label>Rango 1</label>");
		respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"rangoRFC81\" placeholder=\"Ingrese Rango 1\">");
		respuesta.println("                                <label>Rango 2</label>");
		respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"rangoRFC82\" placeholder=\"Ingrese Rango 2\">");
		respuesta.println("                            </div>");
		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
		respuesta.println("</form>");
		respuesta.println("                            </div>");
		respuesta.println("                            </div>");
		respuesta.println("                            <br>");
		respuesta.println("<div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                    <form role=\"form\" action=\"ServletIteracion4.htm\" method=\"get\">");
		respuesta.println("");
		respuesta.println("                            <div class=\"form-group\">");
		respuesta.println("                                <label>Id Pedido</label>");
		respuesta.println("                            	<select class=\"form-control\" name=\"idPed\" required>");
		respuesta.println("                            	<option value=\"\" disabled selected> Sel. Pedido </option>");
		for (int i = 0; i < idsPedidos.size(); i++) {
			String nombre = idsPedidos.get(i);
			respuesta.println("                            	<option value=\""+nombre+"\">"+nombre+"</option>");
		}
		respuesta.println("                            	</select>");
		respuesta.println("                            </div>");
		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
		respuesta.println("</form>");
		respuesta.println("                            </div>");
		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                    <form role=\"form\" action=\"ServletIteracion4.htm\" method=\"get\">");
		respuesta.println("");
		respuesta.println("                            <div class=\"form-group\">");
		respuesta.println("                                <label>Material</label>");
		respuesta.println("                            	<select class=\"form-control\" name=\"idMat\" required>");
		respuesta.println("                            	<option value=\"\" disabled selected> Sel. Material </option>");
		for (int i = 0; i < idsMaterial.size(); i++) {
			String nombre = idsMaterial.get(i);
			respuesta.println("                            	<option value=\""+nombre+"\">"+nombre+"</option>");
		}
		respuesta.println("                            	</select>");
		respuesta.println("                            </div>");
		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
		respuesta.println("</form>");
		respuesta.println("                            </div>");
		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                    <form role=\"form\" action=\"ServletIteracion4.htm\" method=\"get\">");
		respuesta.println("");
		respuesta.println("                            <div class=\"form-group\">");
		respuesta.println("                                <label>Cantidad</label>");
		respuesta.println("                                <input class=\"form-control\" name=\"cantidad\" placeholder=\"Ingrese cantidad\">");
		respuesta.println("                            </div>");
		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
		respuesta.println("</form>");
		respuesta.println("                            </div>");
		respuesta.println("<div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-12\">");
		respuesta.println("                        <h2>Tabla Pedidos 2</h2>");
		respuesta.println("                        <div class=\"table-responsive\">");
		respuesta.println("                            <table class=\"table table-bordered table-hover\">");
		respuesta.println("                                <thead>");
		respuesta.println("                                    <tr>");
		respuesta.println("                                        <th>Id Etapa</th>");
		respuesta.println("                                        <th>Id Producto</th>");
		respuesta.println("                                        <th>Id Pedido</th>");
		respuesta.println("                                        <th>Id Material</th>");
		respuesta.println("                                        <th>Num Etapa</th>");
		respuesta.println("                                        <th>Cantidad</th>");
		respuesta.println("                                        <th>Fecha Inicio</th>");
		respuesta.println("                                        <th>Fecha Fin</th>");
		respuesta.println("                                    </tr>");
		respuesta.println("                                </thead>");
		respuesta.println("                                <tbody>");
		for (int i = 0; i < etapas2.size(); i++) 
		{
			ConsultaEtapaProduccion consulta = etapas2.get(i);
			respuesta.println("                                <tr>");
			respuesta.println("                                        <td>"+consulta.getIdEtapa()+"</td>");
			respuesta.println("                                        <td>"+consulta.getIdProducto()+"</td>");
			respuesta.println("                                        <td>"+consulta.getIdPedido()+"</td>");
			respuesta.println("                                        <td>"+consulta.getIdMaterial()+"</td>");
			respuesta.println("                                        <td>"+consulta.getNumEtapa()+"</td>");
			respuesta.println("                                        <td>"+consulta.getCantidad()+"</td>");
			respuesta.println("                                        <td>"+consulta.getFechaInicio()+"</td>");
			respuesta.println("                                        <td>"+consulta.getFechaFin()+"</td>");
			respuesta.println("                                </tr>");

		}
		
		respuesta.println("                                </tbody>");
		respuesta.println("                            </table>");
		respuesta.println("                         </div>");
		respuesta.println("                      </div>");
		respuesta.println("                 </div>");
	}

}
