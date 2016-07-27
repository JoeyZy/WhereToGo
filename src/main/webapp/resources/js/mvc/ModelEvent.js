/**
 * Model and View communicate firing ("notifying") and receiving ("observing") various ModelEvent's
 * such as SET, SUBMIT (for forms), and the like.
 *
 * @property {string} SET    - to be fired when the value of the MVC entity is set
 * @property {string} ADD    - to be fired when an item is added to the compound (e.g. array) model
 *                             (in principle, may be used whenever anything is added to an MVC entity)
 * @property {string} SUBMIT - to be fired when the form component is submitted
 */
var ModelEvent = {
    SET   : "set",
    ADD   : "add",
    SUBMIT: "submit"
};
