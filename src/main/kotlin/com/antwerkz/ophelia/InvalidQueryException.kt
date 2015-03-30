package com.antwerkz.ophelia

public class InvalidQueryException : RuntimeException {
    constructor(message: String) : super(message) {
    }

    constructor(message: String, cause: Throwable) : super(message, cause) {
    }
}