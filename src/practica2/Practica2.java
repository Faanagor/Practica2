package practica2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

class Practica2 {
    static Scanner lector = new Scanner(System.in);
    static int opcion = 0;
    static int flag = 0;    
    static String  nombre = "";
    static int cantidad = 0;
    //static String cantidadProducto = "";
    static int cantidadTotal = 0;
    static int cantidadFinal = 0;
    static double valor = 0;
    static double precioFinal;
    static double gananciaTotal;
    static double ganancia;
    
    
    public void salir()
    {
        System.out.println("Muchas gracias, que tenga un feliz dia"); 
    }
    
    public void otraOpcion()
    {
        System.out.println("Intente nuevamente, recuerde opciones de 1 a 7"); 
    }
    public void menu()
    {
        System.out.println("ADMINISTRACION PELUCHITOS");
        System.out.println("Elija una de las opciones");
        System.out.println("1. Agregar nuevo producto");
        System.out.println("2. Buscar producto");
        System.out.println("3. Eliminar producto");
        System.out.println("4. Mostrar inventario");
        System.out.println("5. Realizar venta");
        System.out.println("6. Mostrar Ganancias totales");
        System.out.println("7. SALIR");
    }
    
    public static void main(String[] args) {
        
        Practica2 Persona = new Practica2();
        
        Scanner lector = new Scanner(System.in);
        String user = "root";
        String password = "agsiscom124";
        String url = "jdbc:mysql://localhost/peluchitos";
        String user2 = "edwinacubillos";
        String password2 = "25312e";
        String url2 = "jdbc:mysql://db4free.net/productos2";
        
        try {
            //Prueba de conexion
            System.out.println("Conectando a base de datos...");
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url,user,password);
            System.out.println("Conexión exitosa...");

            Statement estado = con.createStatement();
            ResultSet resultado;
            
           do
            {
                Persona.menu();
                opcion = lector.nextInt();
                switch(opcion)
                {
                    case 1:
                         //Persona.agregar();
                         //Insertar un contacto
                        System.out.println("Digite el nombre del producto: ");
                        lector.nextLine();
                        nombre = lector.nextLine();
                        System.out.println("Digite la cantidad: ");
                        cantidad = lector.nextInt();
                        System.out.println("Digite el valor: ");
                        valor = lector.nextDouble();    
                         estado.executeUpdate("INSERT INTO `productos2` VALUES (NULL, '"+nombre+"', '"+cantidad+"', '"+valor+"', '"+ganancia+"')");
                        resultado = estado.executeQuery("SELECT * FROM `productos2`");
                        while (resultado.next())
                        {
                           System.out.println(resultado.getString("id") +"\t"+ resultado.getString("nombre")
                            +"\t"+ resultado.getString("cantidad") +"\t"+ resultado.getString("valor"));
                        }
                         break;
                     case 2:
                            //Persona.buscar();
                        // resultado.next()= false;
                         System.out.println("Digite un nombre: ");
                        lector.nextLine();
                        nombre = lector.nextLine();
                        resultado = estado.executeQuery("SELECT * FROM `productos2`WHERE `nombre` LIKE '"+nombre+"'");
                        if (resultado.next()==true) 
                        {                                
                                System.out.println(resultado.getString("id") +"\t"+ resultado.getString("nombre")
                                +"\t"+ resultado.getString("cantidad") +"\t"+ resultado.getString("valor"));
                        } else {
                                System.out.println("No existe el articulo "+nombre );
                        }
                         break;
                     case 3:
                         //Persona.eliminar();
                         //Delete o eliminar dato
                        System.out.println("Digite un nombre del articulo a eliminar: ");
                        lector.nextLine();
                        nombre = lector.nextLine();
                        resultado = estado.executeQuery("SELECT * FROM `productos2`WHERE `nombre` LIKE '"+nombre+"'");
                        if (resultado.next()==true) 
                        {                                
                            System.out.println(resultado.getString("id") +"\t"+ resultado.getString("nombre")
                            +"\t"+ resultado.getString("cantidad") +"\t"+ resultado.getString("valor"));
                            estado.executeUpdate("DELETE FROM `productos2` WHERE `nombre` LIKE '"+nombre+"'");
                            resultado = estado.executeQuery("SELECT * FROM `productos2`");
                            while (resultado.next())
                            {
                                System.out.println(resultado.getString("id") +"\t"+ resultado.getString("nombre")
                                +"\t"+ resultado.getString("cantidad") +"\t"+ resultado.getString("valor"));
                            }
                        } else {
                                System.out.println("No existe el articulo "+nombre );
                        }
                        
                         break;
                     case 4:
                            //Persona.mostrar();
                        //Cargar lista completa
                        resultado = estado.executeQuery("SELECT * FROM `productos2`");
                        while (resultado.next())
                        {
                           System.out.println(resultado.getString("id") +"\t"+ resultado.getString("nombre")
                            +"\t"+ resultado.getString("cantidad") +"\t"+ resultado.getString("valor"));
                        }
                         break;
                         
                     case 5:
                           // Persona.realizar_venta();
                        System.out.println("Articulo que desea vender: ");
                        lector.nextLine();
                        nombre = lector.nextLine();
                        resultado = estado.executeQuery("SELECT * FROM `productos2` WHERE `nombre` LIKE '"+nombre+"'");
                        if (resultado.next()==true) 
                        {
                            System.out.println(resultado.getString("id") +"\t"+ resultado.getString("nombre")
                            +"\t"+ resultado.getString("cantidad") +"\t"+ resultado.getString("valor"));
                            System.out.println("Cantidad: ");
                            cantidad = lector.nextInt();
                            cantidadTotal = resultado.getInt("cantidad");
                            
                            if (cantidad <= cantidadTotal)
                            {
                                cantidadFinal = cantidadTotal - cantidad;
                                //resultado = estado.executeQuery("SELECT ´valor´ FROM `productos2`WHERE `nombre` LIKE '"+nombre+"'");
                                precioFinal = cantidad * resultado.getDouble("valor");
                                System.out.println("Venta realizada " );
                                System.out.println("Producto: "+nombre );
                                System.out.println("Unidades vendidas:"+cantidad );
                                System.out.println("valor total:"+precioFinal );
                                gananciaTotal = resultado.getInt("ganancia");
                                gananciaTotal = gananciaTotal + precioFinal;
                                estado.executeUpdate("UPDATE `productos2` SET `ganancia` = '"+gananciaTotal+"' WHERE `nombre` = '"+nombre+"'");
                                estado.executeUpdate("UPDATE `productos2` SET `cantidad` = '"+cantidadFinal+"' WHERE `nombre` = '"+nombre+"'");
                                System.out.println(resultado.getString("id") +"\t"+ resultado.getString("nombre")
                                +"\t"+ resultado.getString("cantidad") +"\t"+ resultado.getString("valor")+"\t"+ resultado.getString("ganancia"));
                            }
                            else
                            {                               

                                System.out.println("Venta NO realizada, No existe tanta cantidad de "+nombre );
                            }
                        } else {
                                System.out.println("Venta NO realizada, No existe el articulo "+nombre );
                        }
                         break;
                     case 6:
                             resultado = estado.executeQuery("SELECT * FROM `productos2`");
                        while (resultado.next())
                        {
                           System.out.println(resultado.getString("id") +"\t"+ resultado.getString("nombre")
                            +"\t"+ resultado.getString("cantidad") +"\t"+ resultado.getString("valor")+"\t"+ resultado.getString("ganancia"));
                        }
                         break;
                     case 7:
                            Persona.salir();
                         break;
                     default:
                            Persona.otraOpcion();
                         break;
                }
            }while(opcion != 7);
            
        } catch (SQLException ex) {
            System.out.println("Error de mysql");
        } catch (Exception e) {
            System.out.println("Se ha encontrado un error de tipo: "
                    + e.getMessage());
        }
        
    }
}