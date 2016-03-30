package business.api.exceptions;

public class FullTrainingException extends ApiException {
	
	private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "El entrenamiento está lleno";

    public static final int CODE = 333;

    public FullTrainingException() {
        this("");
    }

    public FullTrainingException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
