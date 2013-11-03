Crafty.c('Unit', {
    init: function() {
        this.requires("UnitBehavior");
        this.requires("UnitAnimate");
    },

    isCanMove: function() {
        return false;
    },

});
