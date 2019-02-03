package ru.rapidadocs.model

import java.math.BigDecimal
import java.time.LocalDate

class Doc() {

    var date: LocalDate? = null

    var number: Int? = null

    var debet: String = ""

    var kredit: String = ""

    var sum: BigDecimal? = null

    var purpose: String = ""
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Doc

        if (date != other.date) return false
        if (number != other.number) return false
        if (debet != other.debet) return false
        if (kredit != other.kredit) return false
        if (sum != other.sum) return false

        return true
    }

    override fun hashCode(): Int {
        var result = date?.hashCode() ?: 0
        result = 31 * result + (number ?: 0)
        result = 31 * result + debet.hashCode()
        result = 31 * result + kredit.hashCode()
        result = 31 * result + (sum?.hashCode() ?: 0)
        return result
    }


}