package com.example.tiptime

/**
 * 입력된 값이 정상적일 때 true 를 리턴합니다.
 */
fun isNormalCost(string: String): Boolean {
    return string == "" || string.toDoubleOrNull() != null
}