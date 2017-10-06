package avion;

import java.util.HashMap;

import CustomExceptions.NumberFirstClassRowNegativeZeroException;
import CustomExceptions.SeatAlreadyOccupiedException;
import CustomExceptions.SeatNonexistentException;
import CustomExceptions.SeatsRowsNotDistributableException;
import personas.Pasajero;

public class Clase {
    private int cantidadDeFilasDeAsientos;
    private int cantidadDeColumnasDeAsientos;
    private String nombreDeClase;
    private HashMap<String, Asiento> mapaDeAsientos = new HashMap<>();

    private int ultimaFilaDeClase;
    private int primeraFilaDeClase;

    public Clase(int primerFilaDeClase, int cantidadDeAsientos, int cantidadDeFilasDeAsientos, String nombreDeClase) {
        if(primerFilaDeClase <= 0){
            throw new NumberFirstClassRowNegativeZeroException("El numero de la primer fila de clase no puede ser negativo o 0");
        }

        this.primeraFilaDeClase = primerFilaDeClase;
        this.cantidadDeFilasDeAsientos = cantidadDeFilasDeAsientos;
        this.nombreDeClase = nombreDeClase;

        if(cantidadDeAsientos % cantidadDeFilasDeAsientos == 0){

            this.cantidadDeColumnasDeAsientos = cantidadDeAsientos / cantidadDeFilasDeAsientos;
            for (int i = 0; i < cantidadDeFilasDeAsientos; i++) {
                for (int j = 0; j < cantidadDeColumnasDeAsientos; j++) {

                    Asiento asiento = new Asiento(i + primerFilaDeClase, columnaToChar(j), nombreDeClase);
                    asiento.getFilaYColumna();

                    mapaDeAsientos.put(asiento.getFilaYColumna(),asiento);

                }
                ultimaFilaDeClase = primerFilaDeClase + i;
            }
        }else{
            throw new SeatsRowsNotDistributableException("Cantidad de Asientos no distribuible en Cantidad de Filas");
        }

    }

    public void ocuparAsiento(String key, Pasajero pasajero) {
        Asiento asiento;
        if (mapaDeAsientos.containsKey(key)) {
            asiento = mapaDeAsientos.get(key);
            if (asiento.getClase().equals(nombreDeClase)) {
                if (asiento.isOcupado()) {
                    throw new SeatAlreadyOccupiedException("El asiento deseado ya esta ocupado.");
                }
                int DNI = pasajero.getDni();
                asiento.ocupar(DNI);
            } else {
                throw new SeatNonexistentException("No existe el asiento buscado.");
            }
        }
        //TODO hacer una custom exception para esto.
        throw new RuntimeException("El asiento deseado no pertenece a esta clase.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Clase)) return false;

        Clase clase = (Clase) o;

        return getNombreDeClase() != null ? getNombreDeClase().equals(clase.getNombreDeClase()) : clase.getNombreDeClase() == null;
    }


    public int getCantidadDeFilasDeAsientos() {
        return cantidadDeFilasDeAsientos;
    }

    public int getCantidadDeColumnasDeAsientos() {
        return cantidadDeColumnasDeAsientos;
    }

    public String getNombreDeClase() {
        return nombreDeClase;
    }

    public HashMap<String, Asiento> getMapaDeAsientos() {
        return mapaDeAsientos;
    }

    private char columnaToChar(int columna){
        return (char) (65 + columna);
    }

    public int getUltimaFilaDeClase() {
        return ultimaFilaDeClase;
    }

    public int getPrimeraFilaDeClase() {
        return primeraFilaDeClase;
    }
}
