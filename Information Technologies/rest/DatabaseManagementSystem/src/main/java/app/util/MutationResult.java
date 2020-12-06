package app.util;

enum Status {
    SUCCESS,
    FAILURE,
}

public class MutationResult {
    Status status;
    String message;

    private MutationResult(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isSuccessful() {
        return this.status == Status.SUCCESS;
    }

    public String getMessage() {
        return this.message;
    }

    public static MutationResult Success() {
        return new MutationResult(Status.SUCCESS, "OK");
    }

    public static MutationResult Fail(String message) {
        return new MutationResult(Status.FAILURE, message);
    }
}
