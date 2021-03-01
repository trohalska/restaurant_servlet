package ua.servlet.restaurant.dao.entity;

// todo make it table in DB
public enum Status {
    NEW,
    PAYED,
    PAYMENT_CONFIRM,
    COOKING,
    DELIVERY,
    DONE;

    private static final Status[] statuses = values();
    public Status next() {
        return statuses[(this.ordinal() + 1) % statuses.length];
    }
}
