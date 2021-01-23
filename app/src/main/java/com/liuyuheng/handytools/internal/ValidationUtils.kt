package com.liuyuheng.handytools.internal

import com.google.android.material.textfield.TextInputLayout

private const val BILL_NAME_ERROR = "Bill name is empty"
private const val TOTAL_COSTS_ERROR = "Total costs is empty"
private const val NUMBER_OF_PEOPLE_ERROR = "Number of people is empty"
private const val PERSON_BLANK_ERROR = "Please select a person"
private const val PAID_AMOUNT_ERROR = "Paid amount is empty"

fun utilValidateBillName(name: String, layout: TextInputLayout) = validateBlank(name, layout, BILL_NAME_ERROR)
fun utilValidateTotalCosts(costs: String, layout: TextInputLayout) = validateBlank(costs, layout, TOTAL_COSTS_ERROR)
fun utilValidateNumberOfPeople(number: String, layout: TextInputLayout) = validateBlank(number, layout, NUMBER_OF_PEOPLE_ERROR)
fun utilValidatePerson(name: String, layout: TextInputLayout) = validateBlank(name, layout, PERSON_BLANK_ERROR)
fun utilValidatePaidAmount(costs: String, layout: TextInputLayout) = validateBlank(costs, layout, PAID_AMOUNT_ERROR)

private fun validateBlank(string: String, layout: TextInputLayout, errorMsg: String): Boolean {
    return string.isNotBlank().also { isValid ->
        layout.error = if (isValid) null else errorMsg
    }
}