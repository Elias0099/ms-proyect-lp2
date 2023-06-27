package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.commons;

public class GlobalConstants {
	
	/* APIS */

	public static final String  	API_BOLETAS			=	"/v1/boletas";
	public static final String  	API_DETALLE_BOLETA	=	"/v1/detalle_boleta";

	
	/* Mensajes*/
	
	public static final Integer  	COD_CONSULTA_EXITO				=	1;	
	public static final String  	MSG_CONSULTA_EXITO				=	"La consulta fue realizada exitosamente";
	
	public static final Integer  	COD_ERROR						=	2;	
	public static final String  	MSG_CONSULTA_ERROR				=	"La consulta no fue realizada de manera exitosa";
	
	public static final Integer  	COD_BOLETA_NOT_FOUND			=	3;	
	public static final String  	MSG_BOLETA_NOT_FOUND			=	"Boleta no encontrada";
	
	public static final Integer  	COD_DETALLE_BOLETA_NOT_FOUND	=	4;	
	public static final String  	MSG_DETALLE_BOLETA_NOT_FOUND	=	"Detalle_Boleta no encontrada";
	
}
