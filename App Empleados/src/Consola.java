import avion.Avion;
import avion.Clase;
import catalogo.Pricing;
import customExceptions.InvalidPassengersQuantity;
import customExceptions.MenuInvalidOptionSelectedException;
import customExceptions.SeatAlreadyOccupiedException;
import customExceptions.SeatNonexistentException;
import personas.Pasajero;
import vuelo.Vuelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Consola {
    Menu menuPrincipal;
    static Protocol protocol;
    static Scanner mainScanner;
    static int DNI;
    static boolean programRun = true;

    public static void main(String[] args) {
        mainScanner = new Scanner();

        System.out.println("Aplicacion de Empleados de Australis Airlines");

        boolean validDNI = false;
        while (!validDNI) {
            DNI = mainScanner.getInt("Ingrese su DNI:");
            if ( (DNI > 999999) && (DNI < 100000000) ) {
                validDNI = true;
            } else {
                System.out.println("DNI invalido, vuelva a ingresarlo");
            }
        }

        protocol = new Protocol(DNI);

        //Lo puse porque agus lo puso
        while (programRun) {
            try {
                loopMenuPrincipal();
            } catch (MenuInvalidOptionSelectedException e) {
                System.out.println(e.getMessage());
                System.exit(1); //End with error
            }
        }

        System.out.println("Gracias por trabajar con nosotros");
    }

    public static void loopMenuPrincipal() throws MenuInvalidOptionSelectedException {
        String[] opcionesMenuPrincipal = new String[3];

        opcionesMenuPrincipal[0] = "Vender Pasajes";
        opcionesMenuPrincipal[1] = "Ver Reservas del Cliente";
        opcionesMenuPrincipal[2] = "Salir";

        Menu menuPrincipal = new Menu(opcionesMenuPrincipal, "Menu Principal");

        System.out.println(menuPrincipal.strPrintMenu());
        int option = menuPrincipal.pedirOpcionAlUsuario();

        switch (option) {
            case 1:
                searchFlightForReservation();
                break;
            case 2:
                protocol.getTicketsForThisUser();
                break;
            case 3:
                programRun = false; //Preguntar a Agus como funciona esto que no entendi ;-;
                break;
            default:
                throw new MenuInvalidOptionSelectedException("Se selecciono una opcion invalida");
        }
    }

    //Igual

    private static void searchFlightForReservation() {
        String aeropuertoSalida;
        String aeropuertoLlegada;
        aeropuertoSalida = mainScanner.getString("Ingrese aeropuerto de origen: ");
        aeropuertoLlegada = mainScanner.getString("Ingrese aeropuerto de destino: ");
        int passangerQuantity = getPassengerQuantity();
        LocalDate fechaDeSalida;
        fechaDeSalida = mainScanner.getLocalDate("Ingrese fecha de salida (formato dd/MM/yy):");

        boolean roundTrip = false;
        roundTrip = mainScanner.getYesNo("Desea reservar ida y vuelta?");
        if(roundTrip){

            LocalDate fechaDeVuelta;
            fechaDeVuelta = mainScanner.getLocalDate("Ingrese fecha de vuelta(formato dd/MM/yy):");

            for (int i = 0; i < passangerQuantity; i++) {
                System.out.println("----- RESERVA PASAJERO "+ i +" -----");
                reserveFlight(aeropuertoSalida, aeropuertoLlegada, fechaDeSalida);
                System.out.println(" ---- Reservar vuelta pasajero "+ i +" -----");
                reserveFlight(aeropuertoLlegada, aeropuertoSalida, fechaDeVuelta);
            }
        }else{
            for (int i = 0; i < passangerQuantity; i++) {
                System.out.println("----- RESERVA PASAJERO "+ i +" -----");
                reserveFlight(aeropuertoSalida, aeropuertoLlegada, fechaDeSalida);
            }
        }

    }


    private static void reserveFlight(String aeropuertoSalida, String aeropuertoLlegada, LocalDate fechaDeSalida){


        ArrayList<Vuelo> posiblesVuelos = new ArrayList<>();
        posiblesVuelos = protocol.getPossibleFlights(aeropuertoSalida,aeropuertoLlegada,fechaDeSalida);

        if(posiblesVuelos.size() == 0){
            System.out.println("No se encontraron vuelos con los parametros indicados.");
            return;
        }

        String vuelos[] = new String[posiblesVuelos.size()];
        int i = 0;
        for (Vuelo vuelo: posiblesVuelos) {
            vuelos[i] = fechaDeSalida + "  ||  " + vuelo.getAeropuertoDePartida() + " --> " + vuelo.getAeropuertoDeArribo();
            i++;
        }

        Menu menuVuelos = new Menu(vuelos,"Vuelos encontrados");

        System.out.println(menuVuelos.strPrintMenu());
        int opcionvueloseleccionado = menuVuelos.pedirOpcionAlUsuario();

        int cantidadDePasajeros = 1;

        i = 1;
        Vuelo vueloSeleccionado = new Vuelo();
        for (Vuelo vuelo: posiblesVuelos) {
            if(i == opcionvueloseleccionado){
                vueloSeleccionado = vuelo;
                System.out.println("Selecciono el vuelo con codigo " + vueloSeleccionado.getCodigoDeVuelo());


                cantidadDePasajeros = getPassengerQuantity();

                break;
            }
            i++;
        }
        for (int j = 0; j < cantidadDePasajeros; j++) {
            Pasajero pasajero = new Pasajero(getDNI(false));

            //TODO: Esto es despues de que se seleccione un posible vuelo
            String[] opcionesCategoria = new String[3];
            opcionesCategoria[0] = "Cualquiera";

            boolean seleccionarAsiento = true;
            String asiento = "";
            while (seleccionarAsiento){
                System.out.println(vueloSeleccionado.getAsientoLayout(fechaDeSalida));
                Pricing pricing = vueloSeleccionado.getPricing();
                Avion avion = vueloSeleccionado.getAvion();

                for (Clase clase: avion.getClases()) {
                    System.out.println("Precio clase " + clase.getNombreDeClase() + ": " + pricing.getPrecioDeClase(clase.getNombreDeClase()));
                }
                asiento = mainScanner.getString("Seleccione el asiento deseado: ").toUpperCase();

                if(vueloSeleccionado.validarAsiento(asiento)){
                    try {
                        protocol.sellTicket(vueloSeleccionado.getCodigoDeVuelo(),asiento,fechaDeSalida, pasajero);
                        seleccionarAsiento = false;
                    }catch (SeatNonexistentException | SeatAlreadyOccupiedException e){
                        System.out.println(e.getMessage());
                    }
                }
            }

            System.out.println("Asiento " + asiento + " vendido a " + DNI);
            System.out.println();
        }

        System.out.println("Reservas actuales: ");
        System.out.println(protocol.getTicketsForThisUser());
        System.out.println("\n\n\n\n");

    }

    private static int getPassengerQuantity(){
        int cantidadDePasajeros;
        try {
            cantidadDePasajeros = mainScanner.getInt("Ingrese cantidad de pasajeros (max 5): ");
            if (cantidadDePasajeros < 1) {
                throw new InvalidPassengersQuantity("Cantidad de pasajeros invalida.");
            }

            if(cantidadDePasajeros > 5){
                throw new InvalidPassengersQuantity("Cantidad de pasajeros invalida.");
            }
        }catch(InvalidPassengersQuantity e){
            return getPassengerQuantity();
        }
        return cantidadDePasajeros;
    }

    private static int getDNI(boolean userDNI){
        boolean validDNI = false;
        int givenDNI = 0;
        while(!validDNI){


            if(userDNI){
                givenDNI = mainScanner.getInt("Ingrese su DNI: ");
            }else{
                givenDNI = mainScanner.getInt("Ingrese el DNI del pasajero: ");
            }

            if((givenDNI > 999999) && (givenDNI < 100000000)){
                validDNI = true;
            }else {
                System.out.println("DNI Invalido, vuelva a ingresarlo.");
            }
        }
        return givenDNI;
    }
}

