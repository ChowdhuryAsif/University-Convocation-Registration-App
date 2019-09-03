package bd.edu.seu.student.exception;

public class ResourceDoesNotExistsException extends Exception {
    public ResourceDoesNotExistsException(String resourse) {
        super(resourse + "doesn't Exists!");
    }
}
