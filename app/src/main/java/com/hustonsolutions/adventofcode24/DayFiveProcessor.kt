package com.hustonsolutions.adventofcode24

class DayFiveProcessor {
    fun doPartOne(input: Pair<List<PageOrderRule>,List<PrintUpdate>>): String {
        val (rules, updates) = input
        val middlePageNumbers = updates.map { update ->
            rules
                .takeIf {
                    it.all { rule -> update.check(rule) ?: true }
                }
                ?.let { update.pages.middlePage }
                ?: 0
        }
        return middlePageNumbers.sum().toString()
    }

    fun doPartTwo(input: Pair<List<PageOrderRule>,List<PrintUpdate>>): String {
        val (rules, updates) = input
        val invalidOrders = updates.filter { update ->
            rules.any { rule -> update.check(rule) == false }
        }
        val middlePageNumbers = invalidOrders.map { update ->
            update.applyAll(rules).middlePage
        }
        return middlePageNumbers.sum().toString()
    }

    private val List<Int>.middlePage: Int
        get() = if (this.isEmpty()) -1 else this[this.size / 2]

    /**
     * true if valid, false if invalid, null if rule doesn't apply
     */
    private fun PrintUpdate.check(rule: PageOrderRule): Boolean? {
        val earlierPageIndex = pages.indexOf(rule.earlierPage).takeIf { it != -1 } ?: return null
        val laterPageIndex = pages.indexOf(rule.laterPage).takeIf { it != -1 } ?: return null
        return earlierPageIndex < laterPageIndex
    }

    private fun PrintUpdate.applyAll(rules: List<PageOrderRule>): List<Int> {
        val newUpdate = mutableListOf<Int>()
        val mutablePages = this.pages.toMutableList()
        val remainingRules = rules.filter { rule -> this.check(rule) != null }.toMutableList() // remove irrelevant rules
        while (mutablePages.isNotEmpty()) {
            val firstPage = mutablePages.find { page -> remainingRules.none { rule -> rule.laterPage == page } }
            if (firstPage != null) {
                newUpdate.add(firstPage)
                mutablePages.remove(firstPage)
                remainingRules.removeIf { rule -> rule.earlierPage == firstPage } // remove rules that have been satisfied
            } else {
                throw IllegalStateException("No guaranteed first page found!")
            }
        }
        return newUpdate
    }
}