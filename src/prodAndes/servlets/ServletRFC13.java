package prodAndes.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prodAndes.fachada.ProdAndes;
import prodAndes.vos.Producto;
import prodAndes.vos.RFC13;

public class ServletRFC13 extends ServletTemplate{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void escribirContenido(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		PrintWriter respuesta = response.getWriter();
		
		try {
			
			ArrayList<RFC13> prod = ProdAndes.darInstancia().consultarMaterialesMasUtilizados();
			respuesta.println("<div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-12\">");
			respuesta.println("                        <h2>Tabla Productos</h2>");
			respuesta.println("                        <div class=\"table-responsive\">");
			respuesta.println("                            <table class=\"table table-bordered table-hover\">");
			respuesta.println("                                <thead>");
			respuesta.println("                                    <tr>");
			respuesta.println("                                        <th>Id Material</th>");
			respuesta.println("                                        <th>Cuenta</th>");
			respuesta.println("                                    </tr>");
			respuesta.println("                                </thead>");
			respuesta.println("                                <tbody>");
			for (int i = 0; i < prod.size(); i++) 
			{
				RFC13 x = prod.get(i);
				respuesta.println("                                <tr>");
				respuesta.println("                                        <td>"+x.getIdMaterial()+"</td>");
				respuesta.println("                                        <td>"+x.getCuenta()+"</td>");
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
