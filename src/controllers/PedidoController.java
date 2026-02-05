package controllers;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

import models.Pedido;

public class PedidoController {
  
  public Stack<Pedido> filtarPorZona(List<Pedido> pedidos, int umbral){

    Stack<Pedido> resultado = new Stack<>();

    for(Pedido p : pedidos){
      if(p.getZona() > umbral){
        resultado.push(p);

      }
    }

    return resultado;

  }

  public TreeSet<Pedido> ordenarPorZona(Stack<Pedido> pila){

    TreeSet<Pedido> set = new TreeSet<>(Comparator.comparingInt(Pedido::getZona).reversed()
            .thenComparing(Pedido::getCliente));
        
        set.addAll(pila);
        return set;

  }

  public TreeMap<Integer, Queue<Pedido>> agruparPorUrgencia(List<Pedido> pedidos){

    TreeMap<Integer, Queue<Pedido>> mapa = new TreeMap<>();

        for (Pedido p : pedidos) {
            mapa.computeIfAbsent(p.getUrgencia(), k -> new LinkedList<>()).add(p);
        }
        return mapa;

  }

  public Stack<Pedido> explotarGrupo(Map<Integer, Queue<Pedido>> mapa){

    Stack<Pedido> resultado = new Stack<>();
        Queue<Pedido> grupoMax = null;
        int maxUrgencia = -1;

        for (Map.Entry<Integer, Queue<Pedido>> entry : mapa.entrySet()) {
            if (grupoMax == null || entry.getValue().size() >= grupoMax.size()) {
                if (grupoMax != null && entry.getValue().size() == grupoMax.size()) {
                    if (entry.getKey() > maxUrgencia) {
                        grupoMax = entry.getValue();
                        maxUrgencia = entry.getKey();
                    }
                } else {
                    grupoMax = entry.getValue();
                    maxUrgencia = entry.getKey();
                }
            }
        }

        if (grupoMax != null) {
            for (Pedido p : grupoMax) {
                resultado.push(p);
            }
        }
        return resultado;
  }
}
