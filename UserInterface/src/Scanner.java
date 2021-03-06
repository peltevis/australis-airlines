import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Scanner {

    private static final java.util.Scanner scanner = new java.util.Scanner(System.in);

    public Scanner() { }

    /** Muestra un mensaje y espera el input del usuario.
     * @param message a mostrar.
     * @return texto ingresado por el usuario.
     */
    public String getString(String message) {
        System.out.print(message);
        final String result = scanner.nextLine().trim();
        if(result.isEmpty()) {
            System.out.println("Por favor, ingrese solo texto.");
            return getString(message);
        }
        return result;
    }

    /** Muestra un mensaje y espera el input del usuario.
     * @param message a mostrar.
     * @return char ingresado por el usuario.
     */
    public char getChar(String message) {
        return getString(message).charAt(0);
    }

    /** Muestra un mensaje y espera el input del usuario.
     * @param message a mostrar.
     * @return integer ingresado por el usuario.
     */
    public int getInt(String message) {
        System.out.print(message);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese solo numeros enteros.");
            return getInt(message);
        }
    }

    /** Muestra un mensaje y espera el input del usuario.
     * @param message a mostrar.
     * @return long ingresado por el usuario.
     */
    public long getLong(String message) {
        System.out.print(message);
        try {
            return Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese solo numeros enteros.");
            return getLong(message);
        }
    }

    /** Muestra un mensaje y espera el input del usuario.
     * @param message a mostrar.
     * @return float ingresado por el usuario.
     */
    public float getFloat(String message) {
        System.out.print(message);
        try {
            return Float.parseFloat(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese solo numeros.");
            return getFloat(message);
        }
    }

    /** Muestra un mensaje y espera el input del usuario.
     * @param message a mostrar.
     * @return double ingresado por el usuario.
     */
    public double getDouble(String message) {
        System.out.print(message);
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese solo numeros.");
            return getDouble(message);
        }
    }

    public LocalDate getLocalDate(String message){
        String dateToParse = this.getString(message);
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd/MM/uu" );
            //DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            LocalDate localDate = LocalDate.parse(dateToParse, formatter);
            return localDate;
        } catch (DateTimeParseException e) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
                LocalDate localDate = LocalDate.parse(dateToParse, formatter);
                return localDate;
            } catch (DateTimeParseException ee) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/uu");
                    LocalDate localDate = LocalDate.parse(dateToParse, formatter);
                    return localDate;
                } catch (DateTimeParseException error) {
                    System.out.println("Por favor, utilice el formato dd/MM/yy");
                    return this.getLocalDate(message);
                }
            }
        }
    }

    public boolean getYesNo(String message){
        String yesno = this.getString(message).toUpperCase();
        if(yesno.equals("Y")){
            return true;
        }else if(yesno.equals("N")){
            return false;
        }else{
            return getYesNo(message);
        }
    }

    public LocalTime getHorario(String message){
        try {
            LocalTime hora = LocalTime.parse(this.getString(message));
            return hora;
        }catch (Exception e){
            return getHorario(message);
        }

    }
}
