package transaction;

/**
 * 
 * @author SergeyK
 */
public class TransactionException extends Exception {

	private static final long serialVersionUID = 1L;
	
    public TransactionException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }

    public TransactionException(String msg) {
        super(msg);
    }
}
