package bd.edu.seu.wcuniprogram.exception;

public class ResourceAlreadyExistsException extends Exception {
    public ResourceAlreadyExistsException(String resourse) {
        super(resourse + "already Exists!");
    }
}
