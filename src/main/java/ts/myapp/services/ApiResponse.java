package ts.myapp.services;

public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;
    private String error;

    public ApiResponse() {}

    public ApiResponse(boolean success, T data, String message, String error) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}