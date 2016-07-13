function LoginComponent(selector) {
    Component.call(this, selector);
}

LoginComponent.prototype = Object.create(Component.prototype); // @compat es5
LoginComponent.prototype.wrap = function (selector) {
    Component.prototype.wrap.call(this, selector);
    this.email = this.get("email");
    this.password = this.get("password");
};

