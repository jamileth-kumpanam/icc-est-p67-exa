#  Examen Práctico – Clase `PedidoController`

---

##  Descripción
En un sistema de gestión logística de comercio electrónico, es crucial poder filtrar, organizar y priorizar pedidos en función de características operativas como su zona de entrega y su nivel de urgencia. El método filtrarPorZona permite aislar pedidos que deben entregarse en zonas geográficas específicas, lo cual es útil para la optimización de rutas y asignación de recursos de transporte. Posteriormente, ordenarPorZona organiza dichos pedidos para facilitar la planificación de entregas, priorizando aquellas zonas con mayor código numérico. Por otro lado, agruparPorUrgencia permite identificar rápidamente grupos de pedidos con niveles similares de prioridad de entrega, y explotarGrupo se encarga de aislar de manera eficiente el conjunto de mayor urgencia, lo que resulta clave para cumplir con compromisos de entrega rápida y satisfacción del cliente. Estas operaciones están encadenadas lógicamente para simular procesos reales de análisis en centros de distribución.

##  Objetivo

Desarrollar una clase `PedidoController` que gestione objetos de tipo `Pedido` con uso de estructuras de datos como `Stack`, `Queue`, `TreeSet` y `TreeMap`, aplicando razonamiento lógico, campos calculados y métodos encadenados.

![alt text](diagramaUML_pedidos.png)
---

##  Modelo: `Pedido`

Cada objeto `Pedido` posee los siguientes atributos:

* `cliente`: nombre del cliente (puede contener espacios y números)
* `codigoPostal`: código postal en formato `"XXXXX-YYY"` (5 dígitos, guión, 3 dígitos)
* `prioridades`: lista de enteros que representan niveles de prioridad

Además, contiene **dos campos calculados**:

* `zona`: los **últimos 3 dígitos** del código postal (después del guión). TIP: **split("-");**
```java
// La zona se calcula con split:
split("-");

// Ejemplo: "28045-301" -> zona: 301
```

* `urgencia`: suma de prioridades múltiplos de 3 multiplicado por el número de vocales únicas en el nombre del cliente (sin considerar mayúsculas/minúsculas)

### Ejemplo de cálculo de `urgencia`:

**Pedido 1:**
- `cliente`: "Ana Maria"
- `prioridades`: [6, 15, 8, 12, 5]
- Múltiplos de 3: 6, 15, 12 → suma = 33
- Vocales únicas en "Ana Maria": a, i → 2 vocales únicas
- `urgencia` = 33 × 2 = **66**

**Pedido 2:**
- `cliente`: "Jose Luis"
- `prioridades`: [9, 4, 18, 7]
- Múltiplos de 3: 9, 18 → suma = 27
- Vocales únicas en "Jose Luis": o, e, u, i → 4 vocales únicas
- `urgencia` = 27 × 4 = **108**

---

##  Clase a implementar: `PedidoController`

### Método A – `filtrarPorZona(List<Pedido> pedidos, int umbral)`

* Retorna un `Stack<Pedido>`
* Filtra los pedidos con `zona > umbral`
* Mantiene el orden original en que aparecen

**Ejemplo:**
```
Entrada:
- Pedido(cliente="Ana", codigoPostal="28045-150", prioridades=[3,6])  → zona=150
- Pedido(cliente="Luis", codigoPostal="28045-200", prioridades=[9])   → zona=200
- Pedido(cliente="Maria", codigoPostal="28045-100", prioridades=[12]) → zona=100
- Pedido(cliente="Pedro", codigoPostal="28045-250", prioridades=[6])  → zona=250

umbral = 150

Salida (Stack):
[Pedro(zona=250), Luis(zona=200)]
```

---

### Método B – `ordenarPorZona(Stack<Pedido> pila)`

* Recibe la pila generada en A
* Devuelve un `TreeSet<Pedido>` ordenado por:
  * `zona` descendente
  * `cliente` ascendente
* Si cliente y zona son iguales, se considera duplicado y se descarta

**Ejemplo:**
```
Entrada (Stack):
[Pedro(zona=250), Luis(zona=200)]

Salida (TreeSet ordenado):
[Pedro(zona=250), Luis(zona=200)]
```

---

### Método C – `agruparPorUrgencia(List<Pedido> pedidos)`

* Retorna un `TreeMap<Integer, Queue<Pedido>>`
* Agrupa los pedidos por el campo `urgencia`
* El orden del mapa es ascendente según el valor de urgencia

**Ejemplo:**
```
Entrada:
- Pedido(cliente="Ana", urgencia=30)
- Pedido(cliente="Luis", urgencia=60)
- Pedido(cliente="Maria", urgencia=30)
- Pedido(cliente="Pedro", urgencia=45)

Salida (TreeMap):
{
  30: Queue[Ana, Maria],
  45: Queue[Pedro],
  60: Queue[Luis]
}
```

---

### Método D – `explotarGrupo(Map<Integer, Queue<Pedido>> mapa)`

* Encuentra el grupo con **mayor cantidad de pedidos**
* Si hay empate, selecciona el que tenga **mayor urgencia**
* Retorna un `Stack<Pedido>` con los pedidos de ese grupo en orden **LIFO**

**Ejemplo:**
```
Entrada (TreeMap):
{
  30: Queue[Ana, Maria],      ← 2 pedidos
  45: Queue[Pedro],            ← 1 pedido
  60: Queue[Luis]              ← 1 pedido
}

Salida (Stack):
[Maria, Ana]  ← Grupo de urgencia=30 porque tiene más pedidos (2)
```

---

##  Implementación y validación

Para validar tu implementación se ejecutarán pruebas unitarias sobre:

* Métodos A, B, C, D
* Cálculo de `zona` y `urgencia`

---

##  RÚBRICA DE EVALUACIÓN (9 puntos)

| **Componente Evaluado**          | **Descripción**                                                                                                | **Puntos** |
| -------------------------------- | -------------------------------------------------------------------------------------------------------------- | ---------- |
| Campo calculado: `zona`       | Extrae correctamente los últimos 3 dígitos del código postal                                                   | 1.5        |
| Campo calculado: `urgencia`   | Suma prioridades múltiplos de 3 y multiplica por vocales únicas del cliente (sin considerar mayúsculas)       | 1.5        |
|  Método A – `filtrarPorZona`   | Filtra correctamente y mantiene orden de entrada                                                               | 1.5        |
|  Método B – `ordenarPorZona`   | Ordena por zona DESC, luego cliente ASC. Elimina duplicados por zona y cliente                                 | 1.5        |
|  Método C – `agruparPorUrgencia` | Agrupa correctamente en TreeMap con Queue por urgencia                                                       | 1.5        |
|  Método D – `explotarGrupo`    | Identifica correctamente el grupo más numeroso y, en caso de empate, el de mayor urgencia. Invierte orden a LIFO | 1.5      |
| **Total**                        |                                                                                                                | **9.0**    |

---



##  Estructura esperada

```
src/
├── App.java
├── controllers/
│   └── PedidoController.java
├── models/
│   └── Pedido.java
├── test/
│   └── PedidoControllerTest.java
└── validaciones/
    └── ValidacionesPedido.java
```

---

##  Notas importantes

* El campo `zona` debe extraerse correctamente del código postal
* El campo `urgencia` debe considerar solo vocales únicas (a, e, i, o, u) sin importar mayúsculas/minúsculas
* Los múltiplos de 3 incluyen: 3, 6, 9, 12, 15, etc.
* En caso de empate en `explotarGrupo`, siempre seleccionar el grupo con mayor valor de urgencia
* El `TreeSet` debe eliminar duplicados basándose en zona Y cliente
* El orden en el `Stack` resultante de `explotarGrupo` debe ser LIFO (último en entrar, primero en salir)
