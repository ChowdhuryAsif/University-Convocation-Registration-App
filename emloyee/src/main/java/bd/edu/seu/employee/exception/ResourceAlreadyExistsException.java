package bd.edu.seu.employee.exception;

public class ResourceAlreadyExistsException extends Exception {
    public ResourceAlreadyExistsException(String resourse) {
        super(resourse + "already Exists!");
    }
}
