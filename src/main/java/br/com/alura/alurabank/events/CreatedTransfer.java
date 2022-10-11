package br.com.alura.alurabank.events;

public class CreatedTransfer {
    private Integer id;
    private CreatedTransferStatus status;
    private Exception error;

    CreatedTransfer() {
    }

    public CreatedTransfer(Integer id, CreatedTransferStatus status, Exception error) {
        this.id = id;
        this.status = status;
        this.error = error;
    }

    public CreatedTransfer(Integer id, CreatedTransferStatus status) {
        this(id, status, null);
    }

    public Integer getId() {
        return id;
    }

    public CreatedTransferStatus getStatus() {
        return status;
    }

    public Exception getError() {
        return error;
    }
}
