package catalogo;

import customExceptions.FlightCodeAlreadyExistsException;
import customExceptions.FlightCodeNonexistentException;
import vuelo.Vuelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class ListaDeVuelos {
    ArrayList<Vuelo> lista;
    ArrayList<String> codigosDeVuelo;

    public ListaDeVuelos() {
        lista = new ArrayList<>();
        codigosDeVuelo = new ArrayList<>();
    }

    public ArrayList<Vuelo> getLista() {
        return lista;
    }

    public void addVuelo(Vuelo vuelo){
        if(codigosDeVuelo.contains(vuelo.getCodigoDeVuelo())) {
            throw new FlightCodeAlreadyExistsException("Un vuelo con este codigo ya existe");
        }

        lista.add(vuelo);
        codigosDeVuelo.add(vuelo.getCodigoDeVuelo());
    }

    public void removeVuelo(String codigoDeVuelo){
        if(codigosDeVuelo.contains(codigoDeVuelo)){
            throw new FlightCodeNonexistentException("No existe un vuelo con este codigo");
        }
        
        for(int i = 0; i <= lista.size() - 1; i++)
        {
            if(lista.get(i).getCodigoDeVuelo().equals(codigoDeVuelo))
            {
                lista.remove(i);
                codigosDeVuelo.remove(codigoDeVuelo);
            }
        }
    }

    public ArrayList<Vuelo> getFlightsOnDate(LocalDate date){
        ArrayList<Vuelo> result = new ArrayList<>();

        for (Vuelo vuelo: lista) {
            if(vuelo.getDiaDeVuelo().equals(date.getDayOfWeek())){
                if(vuelo.getEndDate().isAfter(date)) {
                    result.add(vuelo);
                }
            }
        }
        return result;
    }

    public ArrayList<Vuelo> getFlightsOnDateFromToDestination(LocalDate date, String aeropuertoDePartida, String aeropuertoDeArribo){
        ArrayList<Vuelo> result = new ArrayList<>();

        for (Vuelo vuelo: lista) {
            if(vuelo.getDiaDeVuelo().equals(date.getDayOfWeek())){
                if(vuelo.getEndDate().isAfter(date)) {
                    if(vuelo.getAeropuertoDeArribo().equals(aeropuertoDeArribo) && vuelo.getAeropuertoDePartida().equals(aeropuertoDePartida)){
                       result.add(vuelo);
                    }
                }
            }
        }
        return result;
    }
}
