//package prodAndes.servlets;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import prodAndes.fachada.ProdAndes;
//import prodAndes.vos.ConsultaEtapaProduccion;
//
//public class ServletFinalRFC8 extends ServletTemplate{ 
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//
//	@Override
//	public void escribirContenido(HttpServletRequest request,
//			HttpServletResponse response) throws IOException {
//		PrintWriter respuesta = response.getWriter();
//		String identificadorMaterial = request.getParameter("identificadorMaterial");
//		String typeMaterial = request.getParameter("typeMaterial");
//		String costo = request.getParameter("costo");
//		String fechaInicio = request.getParameter("rango1");
//		String fechaFin = request.getParameter("rango2");
//		String fechaRFC8Inicio = request.getParameter("rangoRFC81");
//		String fechaRFC8Fin = request.getParameter("rangoRFC82");
//		String idPedido = request.getParameter("idPed");
//		String idMaterial = request.getParameter("idMat");
//		if(identificadorMaterial != null){
//			try{
//				imprimirSelects(respuesta, identificadorMaterial);
//			}
//			catch (Exception e) {
//				e.printStackTrace();
//				imprimirError(response, "Error en clientes");
//			}
//		}
//		
//		
//	}
//	
//	private void imprimirSelects(PrintWriter respuesta, String fechaInicio, String fechaFin) throws Exception{
//		ArrayList<ConsultaEtapaProduccion> etapas1 = ProdAndes.darInstancia().consultarEtapasProduccion1(fechaInicio, fechaFin, null, null);
//		
//		respuesta.println("<div id=\"page-wrapper\">");
//		respuesta.println("");
//		respuesta.println("            <div class=\"container-fluid\">");
//		respuesta.println("");
//		respuesta.println("                <!-- Page Heading -->");
//		respuesta.println("                <div class=\"row\">");
//		respuesta.println("                    <div class=\"col-lg-12\">");
//		respuesta.println("                        <h1 class=\"page-header\">");
//		respuesta.println("                            RFC9 - Consultar Ejecucion De Etapas De Produccion 2");
//		respuesta.println("                        </h1>");
//		respuesta.println("                        <ol class=\"breadcrumb\">");
//		respuesta.println("                            <li>");
//		respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"index.htm\">Dashboard</a>");
//		respuesta.println("                            </li>");
//		respuesta.println("                            <li class=\"active\">");
//		respuesta.println("                                <i class=\"fa fa-table\"></i> RFC9");
//		respuesta.println("                            </li>");
//		respuesta.println("                        </ol>");
//		respuesta.println("                    </div>");
//		respuesta.println("                </div>");
//		
//		respuesta.println("<ol class=\"breadcrumb\">");
//		respuesta.println("                        <h2>");
//		respuesta.println("                            Filtro(Solo puede seleccionar un filtro)");
//		respuesta.println("                        </h2>");
//		respuesta.println("<div class=\"row\">");
//		respuesta.println("                    <div class=\"col-lg-12\">");
//		respuesta.println("                    <form role=\"form\" action=\"ServletIteracion4.htm\" method=\"get\">");
//		respuesta.println("");
//		respuesta.println("                            <div class=\"form-group\">");
//		respuesta.println("                                <label>Rango 1</label>");
//		respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"rangoRFC81\" placeholder=\"Ingrese Rango 1\">");
//		respuesta.println("                                <label>Rango 2</label>");
//		respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"rangoRFC82\" placeholder=\"Ingrese Rango 2\">");
//		respuesta.println("                            <br>");
//		respuesta.println("<div class=\"row\">");
//		respuesta.println("                    <div class=\"col-lg-4\">");
//		respuesta.println("                                <label>Id Pedido</label>");
//		respuesta.println("                            	<select class=\"form-control\" name=\"idPed\" required>");
//		respuesta.println("                            	<option value=\"\" disabled selected> Sel. Pedido </option>");
//		for (int i = 0; i < etapas1.size(); i++) {
//			String nombre = etapas1.get(i).getIdPedido();
//			respuesta.println("                            	<option value=\""+nombre+"\">"+nombre+"</option>");
//		}
//		respuesta.println("                            	</select>");
//		respuesta.println("                            </div>");
//		
//		respuesta.println("                            </div>");
//		respuesta.println("                            </div>");
//
//		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
//		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
//		respuesta.println("</form>");
//		respuesta.println("                            </div>");
//		respuesta.println("                            </div>");
//		respuesta.println("<div class=\"row\">");
//		respuesta.println("                    <div class=\"col-lg-12\">");
//		respuesta.println("                        <h2>Tabla Estaciones Produccion</h2>");
//		respuesta.println("                        <div class=\"table-responsive\">");
//		respuesta.println("                            <table class=\"table table-bordered table-hover\">");
//		respuesta.println("                                <thead>");
//		respuesta.println("                                    <tr>");
//		respuesta.println("<th>");
//		respuesta.println("                    <a name\"material\" href=\"index.htm\">Id Material</a>");
//		respuesta.println("                </th>");
//		respuesta.println("                                        <th>Id Material</th>");
//		respuesta.println("                                        <th>Id Producto</th>");
//		respuesta.println("                                        <th>Cantidad</th>");
//		respuesta.println("                                        <th>Id Etapa</th>");
//		respuesta.println("                                        <th>Costo</th>");
//		respuesta.println("                                        <th>Fecha Inicio</th>");
//		respuesta.println("                                        <th>Fecha Fin</th>");
//		respuesta.println("                                    </tr>");
//		respuesta.println("                                </thead>");
//		respuesta.println("                                <tbody>");
//		for (int i = 0; i < etapas1.size(); i++) 
//		{
//			ConsultaEtapaProduccion x = etapas1.get(i);
//			respuesta.println("                                <tr>");
//			respuesta.println("                                        <td>"+x.getIdPedido()+"</td>");
//			respuesta.println("                                        <td>"+x.getIdMaterial()+"</td>");
//			respuesta.println("                                        <td>"+x.getIdProducto()+"</td>");
//			respuesta.println("                                        <td>"+x.getCantidad()+"</td>");
//			respuesta.println("                                        <td>"+x.getIdEtapa()+"</td>");
//			respuesta.println("                                        <td>"+x.getCostoFinal()+"</td>");
//			respuesta.println("                                        <td>"+x.getFechaInicio()+"</td>");
//			respuesta.println("                                        <td>"+x.getFechaFin()+"</td>");
//			respuesta.println("                                </tr>");
//
//		}
//		
//		respuesta.println("                                </tbody>");
//		respuesta.println("                            </table>");
//		respuesta.println("                         </div>");
//		respuesta.println("                      </div>");
//		respuesta.println("                 </div>");
//	}
//}
