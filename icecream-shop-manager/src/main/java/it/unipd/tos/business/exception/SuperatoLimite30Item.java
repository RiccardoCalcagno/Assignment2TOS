////////////////////////////////////////////////////////////////////
// Riccardo Calcagno 1193479
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business.exception;

public class SuperatoLimite30Item extends TakeAwayBillException {

    static final long serialVersionUID = 3L;

    public SuperatoLimite30Item(String msg) {
         super(msg);
    }  

}