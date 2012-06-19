/**
 * 
 * Could be used for runtime exceptions
 *
 */
public class TwoFourNodeException extends RuntimeException {
	public TwoFourNodeException() {
        super ("Issues with TwoFourNode");
    }
    
    public TwoFourNodeException(String anErrorMsg) {
        super (anErrorMsg);
    }
}
