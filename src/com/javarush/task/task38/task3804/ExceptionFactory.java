package com.javarush.task.task38.task3804;

public class ExceptionFactory {

    public static Throwable getException(Enum exceptionType) {
        Throwable exception = new IllegalArgumentException();
        if (exceptionType != null) {
            String enumString = exceptionType.toString();
            String message = enumString.substring(0, 1) + enumString.substring(1).toLowerCase().replaceAll("_", " ");
            if (exceptionType instanceof ApplicationExceptionMessage)
                exception = new Exception(message);
            if (exceptionType instanceof DatabaseExceptionMessage)
                exception = new RuntimeException(message);
            if (exceptionType instanceof UserExceptionMessage)
                exception = new Error(message);
        }
        return exception;
    }

}
