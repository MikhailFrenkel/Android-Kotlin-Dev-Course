// Introduction
fun Shop.getSetOfCustomers(): Set<Customer> =
        customers.toSet()

// Sort
// Return a list of customers, sorted in the descending by number of orders they have made
fun Shop.getCustomersSortedByOrders(): List<Customer> =
        customers.sortedByDescending {it.orders.count()}

// Filter map
// Find all the different cities the customers are from
fun Shop.getCustomerCities(): Set<City> =
        customers.map { it.city }.toSet()

// Find the customers living in a given city
fun Shop.getCustomersFrom(city: City): List<Customer> =
        customers.filter { it.city.name == city.name }

// All, Any and other predicates
// Return true if all customers are from a given city
fun Shop.checkAllCustomersAreFrom(city: City): Boolean =
        customers.all { it.city.name == city.name }

// Return true if there is at least one customer from a given city
fun Shop.hasCustomerFrom(city: City): Boolean =
        customers.any { it.city.name == city.name }

// Return the number of customers from a given city
fun Shop.countCustomersFrom(city: City): Int =
        customers.count { it.city.name == city.name }

// Return a customer who lives in a given city, or null if there is none
fun Shop.findCustomerFrom(city: City): Customer? =
        customers.firstOrNull { it.city.name == city.name }

// Max min
// Return a customer who has placed the maximum amount of orders
fun Shop.getCustomerWithMaxOrders(): Customer? =
        customers.maxByOrNull {it.orders.count()}

// Return the most expensive product that has been ordered by the given customer
fun getMostExpensiveProductBy(customer: Customer): Product? =
        customer.orders.flatMap {it.products}.maxByOrNull {it.price}

// Sum
// Return the sum of prices for all the products ordered by a given customer
fun moneySpentBy(customer: Customer): Double =
        customer.orders.flatMap {it.products}.sumOf {it.price}

// Associate
// Build a map from the customer name to the customer
fun Shop.nameToCustomerMap(): Map<String, Customer> =
        customers.associateBy { it.name }

// Build a map from the customer to their city
fun Shop.customerToCityMap(): Map<Customer, City> =
        customers.associateWith { it.city }

// Build a map from the customer name to their city
fun Shop.customerNameToCityMap(): Map<String, City> =
        customers.associate { it.name to it.city }

// Group By
// Build a map that stores the customers living in a given city
fun Shop.groupCustomersByCity(): Map<City, List<Customer>> =
        customers.groupBy { it.city }

// Partition
// Return customers who have more undelivered orders than delivered
fun Shop.getCustomersWithMoreUndeliveredOrders(): Set<Customer> {
    val (more, less) = customers.partition {
        c -> c.orders.count { it.isDelivered == false } >
             c.orders.count { it.isDelivered == true }
    }
    return more.toSet()
}

// FlatMap
// Return all products the given customer has ordered
fun Customer.getOrderedProducts(): List<Product> =
        orders.flatMap { it.products }

// Return all products that were ordered by at least one customer
fun Shop.getOrderedProducts(): Set<Product> =
        customers.flatMap { it.orders }.flatMap { it.products }.toSet()

// Fold ?
// Return the set of products that were ordered by all customers
fun Shop.getProductsOrderedByAll(): Set<Product> {
    val allProducts = customers.flatMap { it.getOrderedProducts() }.toSet()
    return customers.fold(allProducts, { orderedByAll, customer ->
        orderedByAll.intersect(customer.getOrderedProducts())
    })
}

fun Customer.getOrderedProducts(): List<Product> =
        orders.flatMap { it.products }.sortedBy { it.price }

// Compound tasks
// Find the most expensive product among all the delivered products
// ordered by the customer. Use `Order.isDelivered` flag.
fun findMostExpensiveProductBy(customer: Customer): Product? {
    return customer
            .orders.filter { it.isDelivered == true }
            .flatMap { it.products }
            .maxByOrNull { it.price }
}

// Count the amount of times a product was ordered.
// Note that a customer may order the same product several times.
fun Shop.getNumberOfTimesProductWasOrdered(product: Product): Int {
    return customers
            .flatMap { it.orders }
            .flatMap { it.products }
            .count { it.name == product.name }
}

// Sequences
// Find the most expensive product among all the delivered products
// ordered by the customer. Use `Order.isDelivered` flag.
fun findMostExpensiveProductBy(customer: Customer): Product? {
    return customer
            .orders.asSequence()
            .filter { it.isDelivered }
            .flatMap { it.products }
            .maxByOrNull { it.price }
}

// Count the amount of times a product was ordered.
// Note that a customer may order the same product several times.
fun Shop.getNumberOfTimesProductWasOrdered(product: Product): Int {
    return customers
            .asSequence()
            .flatMap { it.orders }
            .flatMap { it.products }
            .count { it.name == product.name }
}

// Getting used to new style
fun doSomethingWithCollection(collection: Collection<String>): Collection<String>? {

    val groupsByLength = collection.groupBy { s -> s.length }

    val maximumSizeOfGroup = groupsByLength.values.map { group -> group.size }.maxOrNull()

    return groupsByLength.values.firstOrNull { group -> group.size == maximumSizeOfGroup }
}


