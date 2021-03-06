package prodAndes.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prodAndes.fachada.ProdAndes;
import prodAndes.vos.Producto;

public class ServletTablasProductos extends ServletTemplate{
	
	private static final long serialVersionUID = 1L;

	@Override
	public void escribirContenido(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		PrintWriter respuesta = response.getWriter();
		String namee = request.getParameter("emailCliente");
		try {
			
			ArrayList<Producto> prod = ProdAndes.darInstancia().consultarExistenciasPorNombre(namee);
			respuesta.println("<div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-12\">");
			respuesta.println("                        <h2>Tabla Productos</h2>");
			respuesta.println("                        <div class=\"table-responsive\">");
			respuesta.println("                            <table class=\"table table-bordered table-hover\">");
			respuesta.println("                                <thead>");
			respuesta.println("                                    <tr>");
			respuesta.println("                                        <th>Codigo</th>");
			respuesta.println("                                        <th>Nombre</th>");
			respuesta.println("                                        <th>Cantidad</th>");
			respuesta.println("                                    </tr>");
			respuesta.println("                                </thead>");
			respuesta.println("                                <tbody>");
			for (int i = 0; i < prod.size(); i++) 
			{
				Producto x = prod.get(i);
				respuesta.println("                                <tr>");
				respuesta.println("                                        <td>"+x.getId()+"</td>");
				respuesta.println("                                        <td>"+x.getNombre()+"</td>");
				respuesta.println("                                        <td>"+x.getCantidad()+"</td>");
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
