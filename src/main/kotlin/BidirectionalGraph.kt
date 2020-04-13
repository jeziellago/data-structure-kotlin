import kotlin.system.measureNanoTime

data class Graph(
    val name: String,
    val connections: MutableMap<String, Connection> = mutableMapOf()
) {

    override fun toString(): String {
        return "Graph($name):${connections.keys}"
    }
}

data class Connection(
    val graph1: Graph,
    val graph2: Graph
)

fun insertGraph(
    head: Graph,
    graph: Graph,
    parent: String,
    visited: MutableSet<String> = mutableSetOf()
): Boolean {

    if (head.name == parent) {
        val conn = Connection(head, graph)
        val key = head.name + graph.name
        head.connections[key] = conn
        graph.connections[key] = conn
        return true
    } else {
        visited.add(head.name)
        for ((graph1, graph2) in head.connections.values) {
            val result = when {
                !visited.contains(graph1.name) -> insertGraph(graph1, graph, parent, visited)
                !visited.contains(graph2.name) -> insertGraph(graph2, graph, parent, visited)
                else -> false
            }

            if (result) return true
        }

        return false
    }
}

fun printGraph(root: Graph, visited: MutableSet<String> = mutableSetOf()) {
    println(root)
    visited.add(root.name)
    root.connections.values.forEach {
        if (!visited.contains(it.graph1.name))
            printGraph(it.graph1, visited)
        if (!visited.contains(it.graph2.name))
            printGraph(it.graph2, visited)
    }
}

fun main() {

    // region - create
    val graphA = Graph(name = "A")
    val graphB = Graph(name = "B")
    val graphC = Graph(name = "C")
    val graphD = Graph(name = "D")
    val graphE = Graph(name = "E")
    val graphF = Graph(name = "F")

    val head: Graph = graphA
    // endregion - create

    // region - insert
    insertGraph(head, graphB, "A")

    insertGraph(head, graphD, "A")

    insertGraph(head, graphD, "B")

    insertGraph(head, graphC, "B")

    insertGraph(head, graphC, "D")

    insertGraph(head, graphE, "C")

    insertGraph(head, graphF, "E")
    // endregion - insert

    // region - print
    var time = measureNanoTime { printGraph(graphA) }
    println("Start [A] Measure Time Nano: $time\n")

    time = measureNanoTime { printGraph(graphB) }
    println("Start [B] Measure Time Nano: $time\n")

    time = measureNanoTime { printGraph(graphC) }
    println("Start [C] Measure Time Nano: $time\n")

    time = measureNanoTime { printGraph(graphD) }
    println("Start [D] Measure Time Nano: $time\n")

    time = measureNanoTime { printGraph(graphE) }
    println("Start [E] Measure Time Nano: $time\n")

    time = measureNanoTime { printGraph(graphF) }
    println("Start [F] Measure Time Nano: $time\n")
    // endregion - print

}
