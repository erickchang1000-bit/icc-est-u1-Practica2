import models.Persona;
import models.Resultado;
import utils.Benchmarking;

import java.util.concurrent.Callable;

import controllers.SortPersonaMethods;

public class App {

    public static void main(String[] args) throws Exception {
        SortPersonaMethods metodos = new SortPersonaMethods();

        ejecutarEscenarioDesordenado(10000, metodos);
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

        Resultado r1 = Benchmarking.medirTiempo(insercion, "Insertion Sort", "Desordenado", size);
        Resultado r2 = Benchmarking.medirTiempo(quick, "Quick Sort", "Desordenado", size);

        System.out.println(r1);
        System.out.println(r2);
    }
}
