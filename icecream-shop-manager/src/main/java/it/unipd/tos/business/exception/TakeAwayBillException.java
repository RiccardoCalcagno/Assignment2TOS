////////////////////////////////////////////////////////////////////
// Riccardo Calcagno 1193479
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business.exception;

public class TakeAwayBillException extends RuntimeException {

    static final long serialVersionUID = 1L;

    public TakeAwayBillException(){}

    public TakeAwayBillException(String msg) {
         super(msg);
    }
}
