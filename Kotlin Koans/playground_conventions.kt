// Comparison
data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        val yearsDiff = year - other.year
        val monthsDiff = month - other.month
        val daysDiff = dayOfMonth - other.dayOfMonth
        return if (yearsDiff != 0) yearsDiff else
            if (monthsDiff != 0) monthsDiff else daysDiff
    }
}

fun test(date1: MyDate, date2: MyDate) {
    println(date1 < date2)
}

// Ranges
fun checkInRange(date: MyDate, first: MyDate, last: MyDate): Boolean {
    return date in first..last
}

// For loop
class DateRange(val start: MyDate, val end: MyDate): Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var current = start

            override fun hasNext() = current <= end

            override fun next(): MyDate {
                if (!hasNext()) return end
                val res = current
                current = current.followingDate()
                return res
            }
        }
    }
}

fun iterateOverDateRange(firstDate: MyDate, secondDate: MyDate, handler: (MyDate) -> Unit) {
    for (date in firstDate..secondDate) {
        handler(date)
    }
}

// Operators overloading
import TimeInterval.*

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int)

data class TimeIntervalAmount(val timeInterval: TimeInterval, val amount: Int)

// Supported intervals that might be added to dates:
enum class TimeInterval { DAY, WEEK, YEAR }

operator fun MyDate.plus(timeInterval: TimeInterval): MyDate = addTimeIntervals(timeInterval, 1)

operator fun TimeInterval.times(amount: Int) = TimeIntervalAmount(this, amount)

operator fun MyDate.plus(interval: TimeIntervalAmount) = addTimeIntervals(interval.timeInterval, interval.amount)

fun task1(today: MyDate): MyDate {
    return today + YEAR + WEEK
}

fun task2(today: MyDate): MyDate {
    return today + YEAR * 2 + WEEK * 3 + DAY * 5
}

// Invoke
class Invokable {
    var numberOfInvocations: Int = 0
        private set

    operator fun invoke(): Invokable {
        numberOfInvocations++
        return this
    }
}

fun invokeTwice(invokable: Invokable) = invokable()()
