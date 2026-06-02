import models.Persona;
import models.Resultado;
import utils.Benchmarking;
import java.util.concurrent.Callable;
import controllers.SortPersonaMethods;

public class App {

    public static void main(String[] args) throws Exception {
        SortPersonaMethods metodos = new SortPersonaMethods();

        ejecutarEscenarioDesordenado(50000, metodos);
        ejecutarEscenarioCasiOrdenado(50001, metodos);

        System.out.println();

        ejecutarEscenarioDesordenado(50000, metodos);
        ejecutarEscenarioCasiOrdenado(50001, metodos);
    }

    public static Persona[] generarPersonas(int cantidad) {
        Persona[] personas = new Persona[cantidad];

        for (int i = 0; i < cantidad; i++) {
            String nombre = "Persona " + (i + 1);
            int edad = (int) (Math.random() * 101);
            personas[i] = new Persona(nombre, edad);
        }

        return personas;
    }

    public static void ejecutarEscenarioDesordenado(int size, SortPersonaMethods metodos) throws Exception {
        Persona[] base = generarPersonas(size);

        Persona[] copiaInsercion = base.clone();
        Persona[] copiaQuickSort = base.clone();

        Callable<Void> insercion = () -> {
            metodos.insertionSort(copiaInsercion);
            return null;
        };

        Callable<Void> quick = () -> {
            metodos.quickSort(copiaQuickSort, 0, copiaQuickSort.length - 1);
            return null;
        };

        Resultado r1 = Benchmarking.medirTiempo(insercion, "Inserción", "Desordenado", size);
        Resultado r2 = Benchmarking.medirTiempo(quick, "QuickSort", "Desordenado", size);

        System.out.println(r1);
        System.out.println(r2);
    }

    public static void ejecutarEscenarioCasiOrdenado(int size, SortPersonaMethods metodos) throws Exception {
        Persona[] base = generarPersonas(size);

        metodos.quickSort(base, 0, base.length - 1);

        Persona[] nuevo = java.util.Arrays.copyOf(base, size + 1);
        nuevo[size] = new Persona("Nueva Persona", 50);

        Persona[] copiaInsercion = nuevo.clone();
        Persona[] copiaQuickSort = nuevo.clone();

        Callable<Void> insercion = () -> {
            metodos.insertionSort(copiaInsercion);
            return null;
        };

        Callable<Void> quick = () -> {
            metodos.quickSort(copiaQuickSort, 0, copiaQuickSort.length - 1);
            return null;
        };

        Resultado r1 = Benchmarking.medirTiempo(insercion, "Inserción", "Casi ordenado + 1 persona", size + 1);
        Resultado r2 = Benchmarking.medirTiempo(quick, "QuickSort", "Casi ordenado + 1 persona", size + 1);

        System.out.println(r1);
        System.out.println(r2);
    }
}