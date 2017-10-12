import UI.*;
import vuelo.*;

public class Consola {
    Menu menuPrincipal;
    static Protocol protocol;
    static int DNI;
    static boolean programRun = true;
    public static void main(String[] args){

        Scanner mainScanner = new Scanner();

        System.out.println("Bienvenido a la aplicacion de Clientes de Australis Airlines");

        //TODO: Sistema de Registro de usuarios? Contraseñas?
        System.out.println("Para empezar, usted debe conectarse");

        boolean validDNI = false;
        while(!validDNI){
            DNI = mainScanner.getInt("Ingrese su DNI: ");
            if((DNI > 999999) && (DNI < 100000000)){
                validDNI = true;
            }else {
                System.out.println("DNI Invalido, vuelva a ingresarlo.");
            }
        }

        protocol = new Protocol(DNI);

        //TODO: Preguntar si el concepto de progRun esta bien.
        while(programRun){
            loopMenuPrincipal();
        }

        System.out.println("Gracias por volar con nosotros.");

    }

    public static void loopMenuPrincipal(){
        String[] opcionesMenuPrincipal = new String[3];

        opcionesMenuPrincipal[0] = "1. Ver Pasajes";
        opcionesMenuPrincipal[1] = "2. Reservar Pasajes";
        opcionesMenuPrincipal[2] = "3. Salir";

        Menu menuPrincipal = new Menu(opcionesMenuPrincipal, "Menú Principal");

        int option = menuPrincipal.pedirOpcionAlUsuario();

        switch(option){
            case 1:
                protocol.getTicketsForThisUser();
                break;
            case 2:
                break;
            case 3:
                //Prog run se controla desde el main loop.
                programRun = false;
                break;
            default:
                throw new RuntimeException("Opcion de menu invalida");
        }
    }
}