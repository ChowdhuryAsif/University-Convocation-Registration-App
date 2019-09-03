package bd.edu.seu.employee.exception;

public class ResourceDoesNotExistsException extends Exception {
    public ResourceDoesNotExistsException(String resourse) {
        super(resourse + "doesn't Exists!");
    }
}
