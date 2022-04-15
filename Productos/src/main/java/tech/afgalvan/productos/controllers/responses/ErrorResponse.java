package tech.afgalvan.productos.controllers.responses;

public class ErrorResponse {
    private int error;
    private String message;

    public ErrorResponse(String message) {
        this.error = 404;
        this.message = message;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
