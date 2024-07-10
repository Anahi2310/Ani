package pooparciallizg;
import java.util.ArrayList;
/*
Imports necesarios para la escritura del archivo CSV
*/
import java.io.FileWriter;
import java.io.IOException;


public class PooParcialLizG {

    public static void main(String[] args) {
        Secretario secretario = new Secretario("Juan", "Perez", "123456789", "Calle 123", 5, "555-1234", "Oficina 1", 160, 10, 20);
        secretario.calcularSalario();
        System.out.println(secretario);

        Vendedor vendedor = new Vendedor("Ana", "Gomez", "987654321", "Calle 456", 3, "555-5678", "ABC-123", "Toyota", "Corolla", "555-6789", "Zona Norte", 5, 20000, 1200);
        vendedor.calcularSalario();
        System.out.println(vendedor);

        ArrayList<Vendedor> vendedores = new ArrayList<>();
        vendedores.add(vendedor);
        JefeZona jefe = new JefeZona("Carlos", "Sanchez", "111222333", "Calle 789", 10, "555-9876", "Oficina Principal", secretario, vendedores, 300000);
        jefe.calcularSalario();
        System.out.println(jefe);
        
        // Guardar resultados en un archivo CSV
        ArrayList<Empleado> empleados = new ArrayList<>();
        empleados.add(secretario);
        empleados.add(vendedor);
        empleados.add(jefe);

        try {
            CSVWriter.writeCSV("empleados.csv", empleados);
            System.out.println("Archivo CSV generado exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al generar el archivo CSV: " + e.getMessage());
        }
    }
}

/*
CLASE PADRE ABSTRACTA CON LOS ATRIBUTOS COMUNES DE LOS EMPLEADOS
 */
abstract class Empleado {

    public String nombre;
    public String apellidos;
    public String cedula;
    public String direccion;
    public int anosAntiguedad;
    public String telefono;
    protected double salario;

    public Empleado(String nombre, String apellidos, String cedula, String direccion, int anosAntiguedad, String telefono) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.cedula = cedula;
        this.direccion = direccion;
        this.anosAntiguedad = anosAntiguedad;
        this.telefono = telefono;
    }

    /*
    Funcion abstracta a utilizar para las subclases
     */
    public abstract void calcularSalario();
    
    /*
    Funcion la cual da formato a la cadena que se guardará en el archivo CSV
    concatenando la informacion en el orden especificado en la clase CSVWriter
    y separando cada atributo con "," como seeparador
    */
    public String toCSV() {
        return nombre + "," + apellidos + "," + cedula + "," + direccion + "," + anosAntiguedad + "," + telefono + "," + salario;
    }
    

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Apellidos: " + apellidos + ", Cedula: " + cedula
                + ", Dirección: " + direccion + ", Años de Antigüedad: " + anosAntiguedad
                + ", Teléfono: " + telefono + ", Salario: " + salario;
    }
}

/*
Subclase "Secretario que hereda de Empleado"
 */
class Secretario extends Empleado {

    public String despacho;
    public double horasTrabajadas;
    public double costoHora;
    public double horasExtras;

    public Secretario(String nombre, String apellidos, String cedula, String direccion, int anosAntiguedad, String telefono,
            String despacho, double horasTrabajadas, double costoHora, double horasExtras) {
        super(nombre, apellidos, cedula, direccion, anosAntiguedad, telefono);
        this.despacho = despacho;
        this.horasTrabajadas = horasTrabajadas;
        this.costoHora = costoHora;
        this.horasExtras = horasExtras;
    }

    @Override
    public void calcularSalario() {
        salario = (horasTrabajadas * costoHora) + (horasExtras * costoHora * 1.5);
    }
    
    /*
    Usamos la misma logica que con la funcion toString para añadir a la cadena 
    del archivo CSV los demas atributos correspondientes a cada empleado
    */
    @Override
    public String toCSV() {
        return super.toCSV() + "," + despacho;
    }

    @Override
    public String toString() {
        return super.toString() + ", Despacho: " + despacho;
    }
}

/*
Subclase Vendedor que hereda de Empleado
 */
class Vendedor extends Empleado {

    public String cocheMatricula;
    public String cocheMarca;
    public String cocheModelo;
    public String telefonoMovil;
    public String areaVenta;
    public double porcentajeComision;
    public double ventasMensuales;
    public double salarioBase;

    public Vendedor(String nombre, String apellidos, String cedula, String direccion, int anosAntiguedad, String telefono,
            String cocheMatricula, String cocheMarca, String cocheModelo, String telefonoMovil, String areaVenta,
            double porcentajeComision, double ventasMensuales, double salarioBase) {
        super(nombre, apellidos, cedula, direccion, anosAntiguedad, telefono);
        this.cocheMatricula = cocheMatricula;
        this.cocheMarca = cocheMarca;
        this.cocheModelo = cocheModelo;
        this.telefonoMovil = telefonoMovil;
        this.areaVenta = areaVenta;
        this.porcentajeComision = porcentajeComision;
        this.ventasMensuales = ventasMensuales;
        this.salarioBase = salarioBase;
    }

    @Override
    public void calcularSalario() {
        salario = salarioBase + (ventasMensuales * porcentajeComision / 100);
    }
    
    /*
    Usamos la misma logica que con la funcion toString para añadir a la cadena 
    del archivo CSV los demas atributos correspondientes a cada empleado
    */
    @Override
    public String toCSV() {
        return super.toCSV() + "," + cocheMatricula + "," + cocheMarca + "," + cocheModelo + "," + telefonoMovil + "," + areaVenta;
    }

    @Override
    public String toString() {
        return super.toString() + ", Coche: " + cocheMatricula + " " + cocheMarca + " " + cocheModelo
                + ", Teléfono Móvil: " + telefonoMovil + ", Área de Venta: " + areaVenta;
    }
}

/*
Subclase Jefe de Zona que hereda de Empleado
 */
class JefeZona extends Empleado {

    private String despacho;
    private Secretario secretario;
    private ArrayList<Vendedor> vendedores;
    private double ventasGlobales;

    public JefeZona(String nombre, String apellidos, String cedula, String direccion, int anosAntiguedad, String telefono,
            String despacho, Secretario secretario, ArrayList<Vendedor> vendedores, double ventasGlobales) {
        super(nombre, apellidos, cedula, direccion, anosAntiguedad, telefono);
        this.despacho = despacho;
        this.secretario = secretario;
        this.vendedores = vendedores;
        this.ventasGlobales = ventasGlobales;
    }

    @Override
    public void calcularSalario() {
        salario = ventasGlobales * 0.1;
    }
    
    /*
    Usamos la misma logica que con la funcion toString para añadir a la cadena 
    del archivo CSV los demas atributos correspondientes a cada empleado
    */
    @Override
    public String toCSV() {
        return super.toCSV() + "," + despacho + "," + secretario.nombre + "," + vendedores.size();
    }

    @Override
    public String toString() {
        return super.toString() + ", Despacho: " + despacho + ", Secretario: " + secretario.nombre
                + ", Número de Vendedores a Cargo: " + vendedores.size();
    }
}

/*
Clase la cual se encarga de generar el archivo csv
*/
class CSVWriter {

    public static void writeCSV(String filename, ArrayList<Empleado> empleados) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            // Escribir encabezados
            writer.append("Nombre,Apellidos,Cedula,Direccion,AnosAntiguedad,Telefono,Salario,Detalles\n");

            // Escribir datos de empleados
            for (Empleado emp : empleados) {
                writer.append(emp.toCSV()).append("\n");
            }
        }
    }
}
