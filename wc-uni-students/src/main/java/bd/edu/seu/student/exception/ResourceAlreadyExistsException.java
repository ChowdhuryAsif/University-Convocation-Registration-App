package bd.edu.seu.student.exception;

public class ResourceAlreadyExistsException extends Exception {
    public ResourceAlreadyExistsException(String resourse) {
        super(resourse + "already Exists!");
    }
}
