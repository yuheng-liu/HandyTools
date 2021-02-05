package com.liuyuheng.handytools.internal

import com.google.android.material.textfield.TextInputLayout

private const val ITEM_NAME_ERROR = "Item name is empty"
private const val ITEM_COST_ERROR = "Item cost is empty"
private const val NUMBER_OF_PEOPLE_ERROR = "Number of people is empty"
private const val PERSON_BLANK_ERROR = "Please select a person"
private const val PAID_AMOUNT_ERROR = "Paid amount is empty"

fun utilValidateItemName(name: String, layout: TextInputLayout) = validateBlank(name, layout, ITEM_NAME_ERROR)
fun utilValidateItemCost(costs: String, layout: TextInputLayout) = validateBlank(costs, layout, ITEM_COST_ERROR)
fun utilValidateNumberOfPeople(number: String, layout: TextInputLayout) = validateBlank(number, layout, NUMBER_OF_PEOPLE_ERROR)
fun utilValidatePerson(name: String, layout: TextInputLayout) = validateBlank(name, layout, PERSON_BLANK_ERROR)
fun utilValidatePaidAmount(costs: String, layout: TextInputLayout) = validateBlank(costs, layout, PAID_AMOUNT_ERROR)

private fun validateBlank(string: String, layout: TextInputLayout, errorMsg: String): Boolean {
    return string.isNotBlank().also { isValid ->
        layout.error = if (isValid) null else errorMsg
    }
}