package business.api.exceptions;

public class NotFoundUserNameException extends ApiException {

	private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "No se encuentra el nombre de usuario utilizado";

    public static final int CODE = 333;

    public NotFoundUserNameException() {
        this("");
    }

    public NotFoundUserNameException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }
	
}
