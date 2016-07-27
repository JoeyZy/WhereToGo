/**
 * Model and View communicate firing ("notifying") and receiving ("observing") various ModelEvent's
 * such as SET, SUBMIT (for forms), and the like.
 */
var ModelEvent = {
    /**
     * To be fired when the value of the MVC entity is set
     */
    SET: "set",

    /**
     * To be fired when an item is added to the compound (e.g. array) model
     * (in principle, may be used whenever anything is added to an MVC entity)
     */
    ADD: "add",

    /**
     * To be fired when the form component is submitted
     */
    SUBMIT: "submit"
};
