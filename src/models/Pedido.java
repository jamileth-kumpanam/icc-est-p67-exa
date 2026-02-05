package models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pedido {

    private String cliente;
    private String codigoPostal;
    private List<Integer> prioridades;
    private int zona;
    private int urgencia;

    public Pedido(String cliente, String codigoPostal, List<Integer> prioridades) {

        this.cliente = cliente;
        this.codigoPostal = codigoPostal;
        this.prioridades = prioridades;
        this.zona = calcularZona(codigoPostal);
        this.urgencia = calcularUrgencia(cliente, prioridades);

    }

    private int calcularZona(String cp) {

        String[] partes = cp.split("-");
        return Integer.parseInt(partes[1]);

    }

    private int calcularUrgencia(String nombre, List<Integer> lista) {

        int sumaMultiplos = 0;
        for (int p : lista) {
            if (p % 3 == 0) sumaMultiplos += p;
        }

        Set<Character> vocalesUnicas = new HashSet<>();
        String n = nombre.toLowerCase();
        
        for (char c : n.toCharArray()) {
            if ("aeiou".indexOf(c) != -1) vocalesUnicas.add(c);
        }

        return sumaMultiplos * vocalesUnicas.size();
    }

    public String getCliente() {
      return cliente; 
    }

    public int getZona() { 
      return zona; 
    }
    public int getUrgencia() { 
      return urgencia; 
    }

    @Override
    public String toString() {
        return cliente + "(zona = " + zona + ", urgencia = " + urgencia + ")";
    }
}